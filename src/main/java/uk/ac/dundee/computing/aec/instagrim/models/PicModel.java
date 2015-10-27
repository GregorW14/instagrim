package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.CommentBean;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user, String filter) {
        try {
            System.out.println("FILTER IN Insert 1----------"+filter);
            Convertors convertor = new Convertors();
            System.out.println("FILTER IN Insert 2----------"+filter);
            String types[]=Convertors.SplitFiletype(type);
            System.out.println("FILTER IN Insert 3----------"+filter);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            System.out.println("FILTER IN Insert 4----------"+filter);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();
            System.out.println("FILTER IN Insert 5----------"+filter);
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picid));
            
            System.out.println("FILTER IN Insert--xx--------"+filter);

            output.write(b);
            byte []  thumbb = picresize(picid.toString(),types[1], filter);
            int thumblength= thumbb.length;
            ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(),types[1], filter);
            ByteBuffer processedbuf=ByteBuffer.wrap(processedb);
            int processedlength=processedb.length;
            Session session = cluster.connect("instagrim");

            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid, buffer, thumbbuf,processedbuf, user, DateAdded, length,thumblength,processedlength, type, name));
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
            session.close();

        } catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }
    }

    public byte[] picresize(String picid,String type, String filter) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            System.out.println("FILTER IN Resize----------"+filter);
            BufferedImage thumbnail = createThumbnail(BI, filter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }
    
    public byte[] picdecolour(String picid,String type, String filter) {
        try {
            System.out.println("FILTER IN Decolour----------"+filter);
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage processed = createProcessed(BI, filter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnail(BufferedImage img, String filter) {
         // Used for filter colour
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        int col = 0;
        
        System.out.println("FILTER IN THUMB----------"+filter);
        
        switch (filter)
        {
            case "No Filter":
            {
                img = resize(img, Method.SPEED, 250);
                return pad(img, 2);
            }
            case "GrayScale":
            {
                img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
                return pad(img, 2);
            }
            case "Hot":
            {
                img = resize(img, Method.SPEED, 250);
                
                r = 255;
                g = 128;
                b = 0;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            case "Cold":
            {
                img = resize(img, Method.SPEED, 250);
                
                r = 0;
                g = 20;
                b = 255;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }           
            case "Bright":
            {
                img = resize(img, Method.SPEED, 250);
                
                r = 255;
                g = 255;
                b = 0;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            case "Dark":
            {
                img = resize(img, Method.SPEED, 250);
                
                r = 32;
                g = 32;
                b = 32;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }            
            default:
            {
                return null;
            }
        }
        
        // For every second pixel, colour it
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                img.setRGB(i, j, col);
                j++;
            }
        }
        return pad(img, 2);
    }
    
   public static BufferedImage createProcessed(BufferedImage img, String filter) {
         // Used for filter colour
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        int col = 0;
        System.out.println("FILTER IN PROCESSED----------"+filter);
        
        switch (filter)
        {
            case "No Filter":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width);
                return pad(img, 4);
            }
            case "GrayScale":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
                return pad(img, 4);
            }
            case "Bright":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_BRIGHTER);
                
                r = 255;
                g = 255;
                b = 255;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            case "Dark":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_BRIGHTER);
                
                r = 0;
                g = 0;
                b = 0;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            case "Hot":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_BRIGHTER);
                
                r = 255;
                g = 128;
                b = 0;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            case "Cold":
            {
                int Width=img.getWidth()-1;
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_BRIGHTER);
                
                r = 0;
                g = 20;
                b = 255;
                col = (a << 24) | (r << 16) | (g << 8) | b;
                break;
            }
            default:
            {
                return null;
            }
        }
        
        // For every second pixel, colour it
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                img.setRGB(i, j, col);
                j++;
            }
        }
        return pad(img, 4);
    }
   
    public java.util.LinkedList<Pic> getPicsForUser(String User) {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);

            }
        }
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE) {
                
                ps = session.prepare("select image,imagelength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }
                    
                    type = row.getString("type");

                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }
    
    public java.util.LinkedList<CommentBean> getComments() 
     {
         java.util.LinkedList<CommentBean> commentList = new java.util.LinkedList<>();
         Session session = cluster.connect("instagrim");
        try 
        {
            PreparedStatement ps = session.prepare("select * from commentlist");
            BoundStatement bs = new BoundStatement(ps);
            ResultSet rs = null;
            rs = session.execute(bs);
            if(rs.isExhausted())
            {
            return null;
            }else{
                for (Row row : rs) 
                {
                    CommentBean commentBean = new CommentBean();
                    commentBean.setPicID(row.getUUID("picid"));
                    commentBean.setCommentID(row.getUUID("commentid"));
                    commentBean.setUser(row.getString("user"));
                    commentBean.setComment(row.getString("comment"));
                    commentBean.setCommentDate(row.getDate("comment_added"));
                    commentList.add(commentBean);
                }
            }
        }
        catch (Exception e) 
        {      
         return null;
        }
        return commentList;
    }
    
    public void addComment(UUID picID, String user, String comment)
    {
        Session session = cluster.connect("instagrim");
        try 
        {
            UUID commentId = UUID.randomUUID();
            Date dateAdded = new Date();     
            PreparedStatement ps = session.prepare("insert into commentlist (commentid, picid, user, comment_added, comment) values (?, ?, ?, ?, ?)");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(commentId, picID, user, dateAdded, comment));
            
            session.close();
     
        } catch (Exception e) {
            System.out.println("Error --> " + e);
        }
    }
    


}

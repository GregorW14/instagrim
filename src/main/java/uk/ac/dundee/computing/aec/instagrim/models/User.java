/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.stores.ProfileBean;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Greg
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
        /**
         * 
         * @param username
         * @param Password
         * @param profile
         * @return 
         */
        public String RegisterUser(String username, String Password, ProfileBean profile){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't encrypt your password");
            return "PasswordFail";
        }
        //connecting to the keyspace in cassandra so we know which database to reference
        Session session = cluster.connect("instagregor");
        //selecting all userprofiles from the database that have the login provided by the user
        PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
        //selecting all userprofiles from the database that have the email provided by the user
        PreparedStatement ps1 = session.prepare("select * from userprofiles");
        //two result sets for the two different statements
        ResultSet rs = null;
        ResultSet rs1 = null;
        //executing the boundstatement to check if the login is already in use
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        //executing the boundstatement to check if the email is already in use
       BoundStatement boundStatement1 = new BoundStatement(ps1);
       rs1 = session.execute(boundStatement1);
       
        if(rs.isExhausted())
        {
            UserType addressType = cluster.getMetadata().getKeyspace("instagregor").getUserType("address");
            UDTValue address = addressType.newValue()
                                          .setString("street", profile.getStreet())
                                          .setString("city", profile.getCity())
                                          .setInt("zip", profile.getZip());
            HashMap addressMap = new HashMap();
            addressMap.put("Home", address);
            ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email,addresses) Values(?,?,?,?,?,?)");
            boundStatement = new BoundStatement(ps);
            session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            username,EncodedPassword,profile.getFirstName(),profile.getLastName(),profile.getEmail(), addressMap));
            //We are assuming this always works.  Also a transaction would be good here !
            return "Success";
        }
        //if the checks came back with results allerting the user to the problem
        else{
            if(!rs.isExhausted())
            {
                return "UsernameFail";
            }
            return "False";
        }
      }
        
    /**
     * 
     * @param username
     * @param Password
     * @return 
     */
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagregor");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {      
            System.out.println("Username doesn't exist");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
    
    return false;  
    }
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

       /**
        * 
        * @param profile
        * @param user
        * @return
        * @throws Exception 
        */
       public ProfileBean getProfile(ProfileBean profile, String user) throws Exception
    {
        Session session = cluster.connect("instagregor");
        PreparedStatement ps = session.prepare("select * from userprofiles where login=?");
        ResultSet rs = null;
        BoundStatement bs = new BoundStatement(ps);
        rs = session.execute(bs.bind(user));
        Row row = rs.one();
        //set values from returned data
        profile.setUsername(row.getString("login"));
        profile.setFirstName(row.getString("first_name"));
        profile.setLastName(row.getString("last_name"));
        profile.setEmail(row.getString("email"));
        
        Object[] objAddress = row.getMap("addresses", String.class, UDTValue.class).values().toArray();
        String[] strAddress = new String[3];
        int zip;
        
        try {
            UDTValue address = (UDTValue)objAddress[0];
            strAddress[0] = address.getString("street");
            strAddress[1] = address.getString("city");
            zip = address.getInt("zip");
            String zipcode;
            zipcode = Integer.toString(zip);
            strAddress[2] = zipcode;
        }
        catch (Exception e) {
            System.out.println("Error --> "+e);
        }
        
        profile.setAddress(strAddress);
        
        return profile;
    }
       
     /**
      * 
      * @param username 
      */  
     public void deleteUser(String username)
     {
        Session session = cluster.connect("instagregor");

        PreparedStatement ps = session.prepare("DELETE from userprofiles WHERE login=?");
        BoundStatement selectUser = new BoundStatement(ps);
        session.execute(selectUser.bind(username));
        
    }
     
     /**
      * 
      * @param username
      * @param firstname
      * @param lastname
      * @param email
      * @param street
      * @param city
      * @param zip 
      */
     public void updateProfile(String username, String firstname, String lastname, String email, String street, String city, int zip)
     {
        Session session = cluster.connect("instagregor");

        PreparedStatement ps = session.prepare("update userprofiles set first_name= ? WHERE login= ?");
        BoundStatement bs = new BoundStatement(ps);
        session.execute(bs.bind(firstname, username));
        
        ps = session.prepare("update userprofiles set last_name= ? WHERE login= ?");
        bs = new BoundStatement(ps);
        session.execute(bs.bind(lastname, username));
        
        ps = session.prepare("update userprofiles set email= ? WHERE login= ?");
        bs = new BoundStatement(ps);
        session.execute(bs.bind(email, username));
        
        ps = session.prepare("update userprofiles set addresses= ? WHERE login= ?");
        bs = new BoundStatement(ps);
        UserType addressType = cluster.getMetadata().getKeyspace("instagregor").getUserType("address");
            UDTValue address = addressType.newValue()
                                          .setString("street", street)
                                          .setString("city", city)
                                          .setInt("zip", zip);
            
            HashMap addressMap = new HashMap();
            addressMap.put("Home", address);
        session.execute(bs.bind(addressMap, username));

      
    }
     
     /**
      * 
      * @param profile
      * @param username
      * @return
      * @throws Exception 
      */
     public Pic getProfilePic(ProfileBean profile, String username) throws Exception
    {        
        try {
            Session session = cluster.connect("instagregor");
            ByteBuffer bImage = null;
            String type = null;
            int length = 0;
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
            ps = session.prepare("select * from profilepics where user =?");
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(username));
            
            if (rs.isExhausted()) {
                System.out.println("No Images returned" +username);
                return null;
            } else {
                for (Row row : rs) {
                    bImage = row.getBytes("thumb");
                    length = row.getInt("thumblength");
                    type = row.getString("type");
                }
            }
            session.close();
            Pic p = new Pic();
            p.setPic(bImage, length, type);
            return p;
        } catch (Exception et) {
            System.out.println("Error retriving profile picture" + et);
            return null;
        }
    }
     
      /**
       * 
       * @param b
       * @param type
       * @param name
       * @param user
       * @throws IOException 
       */
      public void setProfilePicture(byte[] b, String type, String name, String user) throws IOException {
        try 
        {
            String types[] = Convertors.SplitFiletype(type);
            int length = b.length;
            java.util.UUID picid = Convertors.getTimeUUID();
            byte[] thumbb = profilePicresize(picid.toString(), types[1], b, "normal");
            int thumblength = thumbb.length;
            ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
            Session session = cluster.connect("instagregor");//Connect to Instagrim db
            ByteBuffer buffer = ByteBuffer.wrap(b);
            PreparedStatement psInsertProfilePicture = session.prepare("insert into profilepics (picid, image, thumb, user, imagelength, thumblength, type, name) values(?,?,?,?,?,?,?,?)");
            BoundStatement bsInsertProfilePicture = new BoundStatement(psInsertProfilePicture);
            session.execute(bsInsertProfilePicture.bind(picid, buffer, thumbbuf, user, length, thumblength, type, name));
            session.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error --> " + e);
            throw new IOException();
        }
    }
     
      /**
       * 
       * @param b
       * @param type
       * @param name
       * @param username
       * @throws IOException 
       */
     public void updateProfilePic(byte[] b, String type, String name, String username) throws IOException
     {
        //Session session = cluster.connect("instagrim");

        //PreparedStatement ps = session.prepare("update profilepics set picid= ? WHERE login= ?");
        //BoundStatement bs = new BoundStatement(ps);
        //session.execute(bs.bind(profilepic, username));
         
         try 
        {
            String types[] = Convertors.SplitFiletype(type);
            int length = b.length;
            java.util.UUID picid = Convertors.getTimeUUID();
            byte[] thumbb = profilePicresize(picid.toString(), types[1], b, "normal");
            int thumblength = thumbb.length;
            ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
            Session session = cluster.connect("instagregor");//Connect to Instagrim db
            ByteBuffer buffer = ByteBuffer.wrap(b);
            
            PreparedStatement ps = session.prepare("update profilepics set image=?, thumb=?, imagelength =?, thumblength =?, type =?, name =? where user =?");
            BoundStatement bsInsertProfilePicture = new BoundStatement(ps);
            session.execute(bsInsertProfilePicture.bind(buffer, thumbbuf, length, thumblength, type, name, username));
            session.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error --> " + e);
            throw new IOException();
        }
        
     }
     
     /**
      * 
      * @param picid
      * @param type
      * @param b
      * @param filter
      * @return
      * @throws IOException 
      */
     public byte[] profilePicresize(String picid, String type, byte[] b, String filter) throws IOException {
        try {
            InputStream bais = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(bais);
            BufferedImage thumbnail = createProfileThumbnail(BI, filter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {
            throw new IOException();
        }
    }
     
     /**
      * 
      * @param img
      * @param filter
      * @return 
      */
     public static BufferedImage createProfileThumbnail(BufferedImage img, String filter) {
        
        switch (filter)
        {
            case "normal":
            {
                img = resize(img, Method.SPEED, 250);
                // Let's add a little border before we return result.
                return pad(img, 2);
            }
            default:
            {
                img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
                // Let's add a little border before we return result.
                return pad(img, 2);
            }
        }
    }
     
     
     /**
      * 
      * @param search
      * @return 
      */
     public LinkedList<ProfileBean> searchUserProfiles(String search)
     {
         
      return null;   
     }
    
}

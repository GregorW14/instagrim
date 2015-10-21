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
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.ProfileBean;

/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
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
        Session session = cluster.connect("instagrim");
        //selecting all userprofiles from the database that have the login provided by the user
        PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
        //selecting all userprofiles from the database that have the email provided by the user
        PreparedStatement ps1 = session.prepare("select * from userprofiles");
        //two result sets for the two different statements
        ResultSet rs = null;
        ResultSet rs1 = null;
        //exeuting the boundstatement to check if the login is already in use
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        //executing the boundstatement to check if the email is already in use
       BoundStatement boundStatement1 = new BoundStatement(ps1);
       rs1 = session.execute(boundStatement1);
       
        if(rs.isExhausted())
        {
            UserType addressType = cluster.getMetadata().getKeyspace("instagrim").getUserType("address");
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
            if(rs.isExhausted())
            {
                return "UsernameFail";
            }
            return "False";
        }
      }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
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

       public ProfileBean getProfile(ProfileBean profile, String user) throws Exception
    {
        Session session = cluster.connect("instagrim");
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
        }
        
        profile.setAddress(strAddress);
        
        return profile;
    }
    
}

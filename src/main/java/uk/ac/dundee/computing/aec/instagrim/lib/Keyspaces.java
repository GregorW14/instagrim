package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            String createkeyspace = "create keyspace if not exists instagregor  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instagregor.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + "  processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ")";
            String Createuserpiclist = "CREATE TABLE if not exists instagregor.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (user,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            String Createcommentlist = "CREATE TABLE if not exists instagregor.commentlist (\n"
                    + "commentid uuid,\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "comment varchar,\n"
                    + "comment_added timestamp,\n"
                    + "PRIMARY KEY (picid,commentid)\n"
                    + ") WITH CLUSTERING ORDER BY (commentid desc);";
            String CreateAddressType = "CREATE TYPE if not exists instagregor.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip int\n"
                    + "  );";
            String CreateUserProfile = "CREATE TABLE if not exists instagregor.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                     + "     password text,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      email text,\n"
                    + "      addresses  map<text, frozen <address>>\n"
                    + "  );";
            String CreateProfilePic = "CREATE TABLE if not exists instagregor.profilepics (\n"
                    + " user varchar,\n"
                    + " picid uuid,\n"
                    + " image blob,\n"
                    + " thumb blob,\n"
                    + " imagelength int,\n"
                    + " thumblength int,\n"
                    + " type varchar,\n"
                    + " name varchar,\n"
                    + " PRIMARY KEY (user)"
                    + ")";    
            Session session = c.connect();
            try {
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("created instagregor ");
            } catch (Exception et) {
                System.out.println("Can't create instagregor " + et);
            }

            //now add some column families 
            System.out.println("" + CreatePicTable);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            System.out.println("" + Createuserpiclist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            System.out.println("" + Createcommentlist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createcommentlist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create comment list table " + et);
            }
            System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address Profile " + et);
            }
            System.out.println("" + CreateProfilePic);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateProfilePic);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create ProfilePic " + et);
            }
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
}

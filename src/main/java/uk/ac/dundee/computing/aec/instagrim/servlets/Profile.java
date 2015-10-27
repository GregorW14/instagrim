/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;
import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.ProfileBean;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Greg
 */
@WebServlet(name = "Profile", urlPatterns = 
        {
            "/Profile",
            "/Profile/*",
            "/DeleteProfile",
            "/UpdateProfile",
            "/ProfilePic"
        })
@MultipartConfig

public class Profile extends HttpServlet {
    
    
    private Cluster cluster = null;
    HashMap optionsMap = new HashMap();
    
    
    public Profile()
    {
        super();
        // TODO Auto-generated constructor stub
        optionsMap.put("Profile", 1);
        optionsMap.put("ProfilePic", 2);
        optionsMap.put("UpdateProfile", 3);
        optionsMap.put("DeleteProfile", 4);
    }
    
      @Override
    public void init(ServletConfig config) throws ServletException 
    {
        cluster = CassandraHosts.getCluster();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @param name
     * @throws ServletException
     * @throws IOException 
     */
    protected void deleteProfile(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException 
    {   
        String username = name;
        User us=new User();
        us.setCluster(cluster);
        us.deleteUser(username);
    }
    
    /**
     * 
     * @param request
     * @param response
     * @param name
     * @throws ServletException
     * @throws IOException 
     */
    protected void updateUserProfile(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException 
    {   
        String username = name;
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String email = request.getParameter("email");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        int zipcode = Integer.parseInt(zip);
        User us=new User();
        us.setCluster(cluster);
        us.updateProfile(username, firstname, lastname, email, street, city, zipcode);
       
        
    }
    
    /**
     * 
     * @param request
     * @param response
     * @param name
     * @throws ServletException
     * @throws IOException
     * @throws Exception 
     */
    protected void updateProfilePic(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException, Exception 
    {   
        
       // String username = name;
        //User us=new User();
        //Pic profilePic = null;
        //us.setCluster(cluster);
        //us.updateProfilePic(profilePic, username);
        
        for (Part part : request.getParts()) 
        {
            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            HttpSession session=request.getSession();
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            ProfileBean pb = new ProfileBean();
            String username = "";
            if (lg.getlogedin())
            {
                username=lg.getUsername();
            }
            if (i > 0) 
            {
                byte[] b = new byte[i + 1];
                is.read(b);
                User us = new User();
                us.setCluster(cluster);
                us.updateProfilePic(b, type, filename, username);
                Pic pic = us.getProfilePic(pb, username);
                pb.setProfilePic(pic);
                is.close();
            }
        
       
        
        }
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Declare local variables
        User user = new User();
        user.setCluster(cluster);
        String args[] = Convertors.SplitRequestPath(request);
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        ProfileBean profile = new ProfileBean();
        
        // Is user logged in?
        if ( (lg != null) && (lg.getlogedin() == true) ) 
        {   
                if (args[1].equals("ProfilePic"))
                {
                    Pic profilepic;
                    try {
                        profilepic = user.getProfilePic(profile, lg.getUsername());
                        displayProfilePicture(profilepic, request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
                
                try {
                    profile = user.getProfile(profile, lg.getUsername());
                    request.setAttribute("ProfileBean", profile);
                RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
                rd.forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            else
        {
            response.sendRedirect("index.jsp");
        }
    
    }   
        
    
   
   /**
    * 
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException 
    */
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String args[] = Convertors.SplitRequestPath(request);
        HttpSession session = request.getSession();    
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        String username = lg.getUsername();
        
        
      
        if (args[2].equals("Profile"))
        {
        }    
        else if (args[2].equals("ProfilePic"))
        {
            try {
                updateProfilePic(request, response, username);
                response.sendRedirect("/Instagrim/Profile/"+lg.getUsername());
            } catch (Exception ex) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (args[2].equals("UpdateProfile"))
        {
            updateUserProfile(request, response, username);
            response.sendRedirect("/Instagrim/Profile/"+lg.getUsername());
        }
        else if (args[2].equals("DeleteProfile"))
        {
            deleteProfile(request, response, username);
            response.sendRedirect("/Instagrim/Logout");
        }
        
    }
    
    /**
     * 
     * @param profilepic
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    public void displayProfilePicture(Pic profilepic, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pic p = profilepic;
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType(p.getType());
            response.setContentLength(p.getLength());
            InputStream is = new ByteArrayInputStream(p.getBytes());
            BufferedInputStream input = new BufferedInputStream(is);
            byte[] buffer = new byte[p.getLength()];
            for (int length; (length = input.read(buffer)) > 0;) {
                out.write(buffer, 0, length);
                out.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}

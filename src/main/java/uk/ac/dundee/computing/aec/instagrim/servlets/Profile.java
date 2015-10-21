/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;
import com.datastax.driver.core.Cluster;
import java.io.IOException;
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
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.ProfileBean;
import uk.ac.dundee.computing.aec.instagrim.exceptions.*;

/**
 *
 * @author Greg
 */
@WebServlet(name = "Profile", urlPatterns = 
        {
            "/Profile", 
            "/ProfilePic"
        })
@MultipartConfig

public class Profile extends HttpServlet {
    
    
    private Cluster cluster = null;
    
      @Override
    public void init(ServletConfig config) throws ServletException 
    {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Declare local variables
        String args[] = Convertors.SplitRequestPath(request);
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        
        // Is user logged in?
        if ( (lg != null) && (lg.getlogedin() == true) ) 
        {   
            
            try {
                // Declare local variables
                User user = new User();
                user.setCluster(cluster);
                ProfileBean profileBean = new ProfileBean();
                profileBean = user.getProfile(profileBean, lg.getUsername());
                request.setAttribute("ProfileBean", profileBean);
                
                // Go to profile page
                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            }
            catch (ServletException | IOException | ProfileEx e) 
            {
                        
            } catch (Exception ex) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }   
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.ProfileBean;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {
    Cluster cluster=null;
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
       rd.forward(request, response);
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
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String street=request.getParameter("street");
        String city=request.getParameter("city");
        int zip=Integer.parseInt(request.getParameter("zip"));
        String[] address = {street, city, Integer.toString(zip)};
        
        ProfileBean profile = new ProfileBean();
        profile.setUsername(username);
        profile.setFirstName(firstname);
        profile.setLastName(lastname);
        profile.setEmail(email);
        profile.setAddress(address);
        
   
        //check if fields have data
            if(password.isEmpty() || username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || street.isEmpty() || city.isEmpty())
            {
              response.sendRedirect("register.jsp");
            }
            else
            {
            User us= new User();
            us.setCluster(cluster);
            //sets boolean to result of register user
            String result= us.RegisterUser(username, password, profile);
            uploadDefaultProfilePicture(username);
            //if the result was true user was registered and directed to home page and logged in automatically
                if(result.equals("Success"))
                {
                    HttpSession session = request.getSession();
                    session.setAttribute("ProfileBean", profile);
                    RequestDispatcher rd = request.getRequestDispatcher("/");
                    rd.forward(request, response);
                    
                }else{
                    response.sendRedirect("register.jsp");
                }
            }
        
    }
    
    /**
     * 
     * @param username
     * @throws IOException 
     */
    public void uploadDefaultProfilePicture(String username) throws IOException {
        try 
        {
            String profilePictureName = "mario.png";
            Path path = Paths.get("/images/" + profilePictureName);
            String type = Files.probeContentType(path);
           
            InputStream bais = getClass().getResourceAsStream("/images/" + profilePictureName);
            
            BufferedImage bufferedImage = ImageIO.read(bais);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageInByte = baos.toByteArray();
            User us = new User();
            us.setCluster(cluster);
            us.setProfilePicture(imageInByte, type, "ProfilePicture", username);
            bais.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

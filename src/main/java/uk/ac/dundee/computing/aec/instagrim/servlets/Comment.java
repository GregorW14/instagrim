/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.CommentBean;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Greg
 */
@WebServlet (name = "Comment", urlPatterns = {"/Comment"})
public class Comment extends HttpServlet 
{
    private Cluster cluster;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() 
    {
    }

    /**
     * 
     * @param config
     * @throws ServletException 
     */
    @Override
    public void init(ServletConfig config) throws ServletException 
    {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
             throws ServletException, IOException {
        String comment = request.getParameter("comment");
        String username = request.getParameter("username");
        String picid = request.getParameter("picid");
        PicModel pm = new PicModel();
        pm.setCluster(cluster);
        pm.addComment(UUID.fromString(picid), username, comment);
        
        response.sendRedirect("/Instagregor/Images/"+username);
    }
   
    
    /**
     * 
     * @return 
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

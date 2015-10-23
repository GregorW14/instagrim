/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;

/**
 *
 * @author Greg
 */
@WebServlet (name = "Comments", urlPatterns = {"/Comments"})
public class Comment extends HttpServlet 
{
    private Cluster cluster;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() 
    {
    }

    public void init(ServletConfig config) throws ServletException 
    {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
             throws ServletException, IOException {
        String comment = request.getParameter("comment");
        String username = request.getParameter("username");
        String picid = request.getParameter("picid");
        
        
   
        
    }
    
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

<%-- 
    Document   : samplePics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="Style.css" />
        <link href='https://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div class="row">
                    <div class="col-md-12">
                        <h1>InstaGrim !</h1>
                    </div>
                </div>
                <h2>Your world in Black and White</h2>
            </div>
        
        <div class="row">
            <div class="col-md-3 navItem">
                <a href="/Instagrim/">Home</a>
            </div>
            
            <div class="col-md-3 navItem">
                <a href="/Instagrim/upload.jsp">Upload</a>
            </div>
<%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>
                    <div class="col-md-3 navItem">
                    <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a>
                    </div>
                    <%}
                            }else{
                                %>
             <div class="col-md-3 navItem">
                 <a href="/Instagrim/Register">Register</a>
             </div>
             <div class="col-md-3 navItem">
                <a href="/Instagrim/Login">Login</a>
             </div>
                <%
                                        
                            
                    }%>
                 
        </div>
            
 
        <article>
            <h1>Sample Pics</h1>
        
        </article>
      </div>
    </body>
</html>

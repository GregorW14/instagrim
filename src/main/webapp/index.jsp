<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
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
                                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>
                <form action="/Instagrim/Search" method="POST">
                    Search: <input type="text" name="searchTerm">
                    <input type="submit" value="Submit">
                  </form>
                    <%
                            }else{
                                
                            }
                        }
                                
                                %>
            </div>
        <div class="row">
            <div class="col-md-3 navItem">
                <a href="/Instagrim/">Home</a>
            </div>
                    <%
                        
                        if (lg != null) {
                            if (lg.getlogedin()) {
                    %>
                    
                    
                <div class="col-md-2 navItem">
                    <a href="/Instagrim/upload.jsp">Upload</a>
                </div>
                <div class="col-md-2 navItem">
                    <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a>
                </div>
                
                <div class="col-md-2 navItem">
                 <a href="/Instagrim/Profile/<%=lg.getUsername()%>">Profile</a>
                </div>
                <div class="col-md-2 navItem">
                    <a href="/Instagrim/Logout" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
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
            <br>
            <div class="main">
                <div class="row">
                    <div class="col-md-6">
                      <img src="photo2.jpg" class="largeImg" alt="Bridge"/>
                    </div>
                    <div class="col-md-6">
                       <p class="largeText">Share your photos with friends.</p>
                    </div>
            
                </div>
               
            </div>
        <footer> 
                <p>&COPY; Andy Cobley + Gregor Whyte</p> 
        </footer>
       </div>
    </body>
</html>

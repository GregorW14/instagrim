<%-- 
    Document   : profile
    Created on : 05-Oct-2015, 15:34:43
    Author     : Greg
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
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
                <a href="index.jsp">Home</a>
            </div>
            
            <div class="col-md-3 navItem">
              <a href="settings.jsp">Settings</a>
            </div> 
            
            <div class="col-md-3 navItem">
              <a href="/Instagrim/Logout" onclick="return confirm('Are you sure you would like to logout?')">Logout</a>
            </div>   
        </div>
            
        <h1><%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String userName = lg.getUsername();
                            session.setAttribute("Name", userName);
                    %>
                    ${Name}'s Profile</h1>
                    <%
                       session.setAttribute("Name", null);
                        }
                        %>
        
        
        <footer>
                
                <p>&COPY; Gregor Whyte</p>    
        </footer>
        </div>
    </body>
</html>

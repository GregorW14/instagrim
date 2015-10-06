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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <header>
            <h1>InstaGrim ! </h1>
            <h2>Your world in Black and White</h2>
        </header>
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
           <p><a href="/Instagrim">Home</a></p>
                
            <form method="GET"  action="Logout">
                <br/>
                <input type="submit" value="Logout"> 
            </form>
                
                <p>&COPY; Gregor Whyte</p>    
        </footer>
    </body>
</html>

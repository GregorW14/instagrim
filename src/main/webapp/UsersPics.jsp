<%-- 
    Document   : UsersPics
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
        <link href="/Instagrim/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="/Instagrim/Style.css" />
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
            
            <div class="col-md-3 navItem">
                <a href="/Instagrim/samplePics.jsp">Sample Images</a>
            </div>
            <div class="col-md-3 navItem">
                <a href="/Instagrim/Logout" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
            </div>
            
        </div>
 
        <article>
            <h1>Your Pictures</h1>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();

        %>
        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a><br/><br><%

            }
            }
        %>
        </article>
      </div>
    </body>
</html>

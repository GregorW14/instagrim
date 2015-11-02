<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style.css" />
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
                                                <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>
                <form action="${pageContext.request.contextPath}/Search" method="POST">
                    Search: <input type="text" name="searchTerm">
                    <input type="submit" value="Submit">
                  </form>
                    <%
                            }else{
                                
                            }
                        }
                                
                                %>
            <div class="row">
            <div class="col-md-3 navItem">
                <a href="${pageContext.request.contextPath}/">Home</a>
            </div>
                <%
                        
                    if (lg != null) 
                    {
                        if (lg.getlogedin()) 
                        {
                %>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/upload.jsp">Upload</a>
            </div>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Images/<%=lg.getUsername()%>">Your Images</a>
            </div>
            <div class="col-md-2 navItem">
                 <a href="${pageContext.request.contextPath}/Profile/<%=lg.getUsername()%>">Profile</a>
            </div>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Logout" onclick="return confirm('Are you sure want to logout?')">Logout</a>
            </div>
            
                <%  
                        }
                    }
                    else
                    {
                %>
                <div class="col-md-4 navItem">
                    <a href="${pageContext.request.contextPath}/Login">Login</a>
                </div>
                <div class="col-md-4 navItem">
                    <a href="${pageContext.request.contextPath}/Register">Register</a>
                </div>
                <%
                    }
                %>
        </div>
        <br>
        <article>
            <h3>File Upload</h3>
            <h4>Select a Filter..</h4>
            <div class="row">
            <div class="col-md-3 form-group">
                <form method="POST" enctype="multipart/form-data" action="Image">
                 File to upload: <input type="file" name="upfile"><br/>
                <label for="filterSel">Image Filters:</label>
                <select class="form-control" name="filterSel">
                    <option value="No Filter">No Filter</option>
                    <option value="GrayScale">GrayScale</option>
                    <option value="Red">Red</option>
                    <option value="Blue">Blue</option>
                    <option value="Green">Green</option>
                    <option value="Bright">Bright</option>
                    <option value="Dark">Dark</option>
                </select>
                <br>

                <br/>
                <input type="submit" class="btn btn-primary" value="Press"> to upload the file!
            </form>
            </div>
            </div>

        </article>
        <footer>              
                <p>&COPY; Andy Cobley + Gregor Whyte</p>    
        </footer>
        </div>
    </body>
</html>

<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

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
        
            <div class="row">
                <div class="col-md-3 navItem">
                    <a href="${pageContext.request.contextPath}/">Home</a>
                </div>
                <div class="col-md-3 navItem">
                   <a href="${pageContext.request.contextPath}/Login">Login</a>
                </div>
            </div>
       
            
        <article>
            <div class="row">
                <div class="col-md-4">
                <h3 style="text-align: left;">Register as user</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
            <form method="POST"  action="Register">
                <ul>
                    <li>User Name <input type="text" name="username"></li>
                    <li>Password <input type="password" name="password"></li>
                    <li>First Name <input type="text" name="firstname"></li>
                    <li>Last Name <input type="text" name="lastname"></li>
                    <li>Email <input type ="email" name="email"></li>
                    <li>Street <input type ="text" name="street"></li>
                    <li>City <input type="text" name="city"></li>
                    <li>Zip <input type="number" name="zip"></li>
                </ul>
                <br/>
                <input type="submit" value="Register"> 
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

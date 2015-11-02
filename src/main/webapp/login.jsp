<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
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
                <a href="${pageContext.request.contextPath}/">Home</a>
            </div>
            <div class="col-md-3 navItem">
                <a href="${pageContext.request.contextPath}/">Register</a>
            </div>
        </div>
       
        <article>
            <div class="row">
                <div class="col-md-2">
                    <h3>Login</h3>
                </div>
            </div>
            
            <form method="POST"  action="Login">
                 <div class="row form-group">
                  <label class="col-md-1 control-label">Username:</label>
                  <div class="col-md-2">
                    <input type ="text" name="username" class="form-control">
                  </div>
                 </div>
                <div class="row form-group">
                  <label class="col-md-1 control-label">Password:</label>
                  <div class="col-md-2">
                    <input type ="password" name="password" class="form-control">
                  </div>
                 </div>
                <br/>
                <input type="submit" value="Login"> 
            </form>

        </article>
        </div>
    </body>
</html>

<%-- 
    Document   : 404
    Created on : 17-Oct-2015, 15:02:00
    Author     : Greg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="Style.css" />
        <link href='https://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>404: Page not found</title>
    
        <script>
        function jumpBack() {
            window.history.back();
        }
        </script>
        
    </head>
    <body>
        <h1>Error 404: This Page does not exist</h1>
        
        <br>
        <br>
        <ul>
            <li><a href = "/${pageContext.request.contextPath}">Home</a></li>
            <li><a href="javascript:history.back();">Previous Page</a></li>
        </ul>
        
    </body>
</html>
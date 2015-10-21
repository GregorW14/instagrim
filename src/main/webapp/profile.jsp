<%-- 
    Document   : profile
    Created on : 05-Oct-2015, 15:34:43
    Author     : Greg
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*"%>

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
              <a href="/Instagrim/Logout" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
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
                        
        <div class="main">
               <div class="col-md-4">
                  <img src="photo2.jpg" class="profileImg" alt="Bridge"/>
               </div>
        </div>
                        <%
                String username = "";
                String firstName = "";
                String lastName = "";
                String email = "";
                String[] address = new String[3];
                        
                        
                ProfileBean pb = new ProfileBean();
                pb = (ProfileBean) request.getAttribute("ProfileBean");
                if (pb != null) {
                    username = pb.getUsername();
                    firstName = pb.getFirstName();
                    lastName = pb.getLastName();
                    email = pb.getEmail();
                    address = pb.getAddress();
                }
                else {
                    username = "Not Logged In";
                }
                
                %>
                 <div class="col-md-8">
              <h3>User Details</h3>
              <form method="POST" class="form-horizontal" role="form">
                  <div class="form-group">
                  <label class="col-md-3 control-label">Username:</label>
                  <div class="col-md-8">
                    <input name="username" class="form-control" value="<%=username%>" type="text" readonly>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">First name:</label>
                  <div class="col-lg-8">
                    <input name="firstName" class="form-control" value="<%=firstName%>" type="text">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">Last name:</label>
                  <div class="col-lg-8">
                    <input name="lastName" class="form-control" value="<%=lastName%>" type="text">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">Email:</label>
                  <div class="col-lg-8">
                    <input name="email" class="form-control" value="<%=email%>" type="text">
                  </div>
                </div>
                  
                <div class="form-group">
                  <label class="col-md-3 control-label">Street:</label>
                  <div class="col-md-8">
                    <input name="street" id="street" class="form-control" value="<%=address[0]%>" type="text">
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">City:</label>
                  <div class="col-md-8">
                    <input name="city" id="city" class="form-control" value="<%=address[1]%>" type="text">
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">Zip:</label>
                  <div class="col-md-8">
                    <input name="zip" id="zip" class="form-control" value="<%=address[2]%>" type="text">
                  </div>
                </div>
              </form>
                       
        <footer>               
                <p>&COPY; Gregor Whyte</p>    
        </footer>
        </div>
        </div>
    </body>
</html>

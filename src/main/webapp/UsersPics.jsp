<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.lib.Convertors"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <div id="main">
        <div class="row">
            <div class="col-md-3 navItem">
                <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
            </div>
            
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Upload">Upload</a>
            </div>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Images/<%=lg.getUsername()%>">Your Images</a>
            </div>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Profile/<%=lg.getUsername()%>">Profile</a>
            </div>
            <div class="col-md-2 navItem">
                <a href="${pageContext.request.contextPath}/Logout" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
            </div>
            
        </div>
 
        <article>
            <%
            String user = (String)request.getAttribute("Username");
            
            
            %>
            <h1><%=user%>'s Pictures</h1>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            java.util.LinkedList<CommentBean> lsComments = (java.util.LinkedList<CommentBean>) request.getAttribute("Comments");
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            for (int i =0; i<lsPics.size(); i++ ){ 
            Pic p = lsPics.get(i);
            %>           
            <div class="row">
                <div class="col-md-1"></div>
            <div class="col-md-2">                
             <a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>" ><img class="userPic" src="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>"></a><br/><br>
            </div>
            </div>
             <%
                    if (lsComments == null) 
                    {
                        
                    } 
                    else 
                    {
                       Iterator<CommentBean> iterator2;
                        iterator2 = lsComments.iterator();
                    while (iterator2.hasNext()) {
                        CommentBean cb = (CommentBean) iterator2.next();
                        if(cb.getPicID().toString().equals(p.getSUUID()))
                {
                    %>
                    <div class="row">
                        <div class="col-md-1"></div>
                        <div class="col-md-3">
                            <div class="commentContainer">
                                <a href="${pageContext.request.contextPath}/Images/<%=cb.getUser()%>"><%=cb.getUser()%></a> :
                            <div class="comment">
                                <%=cb.getComment()%> 
                            </div>
                            <div class="commentdate">
                                at <%=cb.getCommentDate()%>
                            </div>    
                         
                            </div>
                        </div>
                    </div>
        <div class="col-md-1"></div>
       
                        
                        <%
                        }
                    }
                    }
                        %>
        <form method="POST"  action="${pageContext.request.contextPath}/Comment">
            <div class="row form-group">
                  <label class="col-md-1 control-label">Comment:</label>
                  <div class="col-md-3">
                        <input type="text" name="username" value="<%=lg.getUsername()%>" hidden>
                        <input type="text" name="picid" value="<%=p.getSUUID()%>" hidden>
                        <input type="text" name="comment">
                  </div>
            </div>
            <div class="row form-group">
                    <div class="col-md-1"></div>
                    <div class="col-md-3">
                        <input type ="submit" value="Comment" name="CommentButton" class="form-control">
                    </div>      
            </div>
        </form>
        <br>
        <br>
        <%
                       
                        

            
            }
            }
            
        %>
        
       
        </article>
      </div>
      </div>
    </body>
</html>

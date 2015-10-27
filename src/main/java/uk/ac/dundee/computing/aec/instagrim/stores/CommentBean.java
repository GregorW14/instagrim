/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Greg
 */
public class CommentBean {
    UUID commentID = null;
    String comment = "";
    String user = "";
    UUID picid = null;
    Date commentDate = null;
    
    public void CommentBean(){
        
    }
    
    public void setCommentID(UUID commentID){
        
        this.commentID = commentID;        
    }
    
    
    public void setComment(String comment){
        
        this.comment = comment;
    }
    
    
    public void setUser(String user){
        
        this.user = user;
    }
    
    public void setPicID(UUID picid){
        
        
        this.picid = picid;
    }
    
    public void setCommentDate(Date commentDate){
        
        this.commentDate = commentDate;
    }
    
    //Getters
    public UUID getCommentID(){
        
        return commentID;
    }
    
    
    public String getComment(){
              
        
        return comment;
    }
    
    
    public String getUser(){
        
        
        
        return user;
    }
    
    
    public UUID getPicID(){
        
         
     return picid; 
     
    }
    
    public Date getCommentDate(){
        
        
     return commentDate;   
    }
    
    
}
            
    

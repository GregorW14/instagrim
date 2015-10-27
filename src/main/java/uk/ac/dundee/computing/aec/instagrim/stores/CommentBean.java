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
    
    /**
     * 
     * @param commentID 
     */
    public void setCommentID(UUID commentID){
        
        this.commentID = commentID;        
    }
    
    
    /**
     * 
     * @param comment 
     */
    public void setComment(String comment){
        
        this.comment = comment;
    }
    
    /**
     * 
     * @param user 
     */
    public void setUser(String user){
        
        this.user = user;
    }
    
    /**
     * 
     * @param picid 
     */
    public void setPicID(UUID picid){
        
        
        this.picid = picid;
    }
    
    /**
     * 
     * @param commentDate 
     */
    public void setCommentDate(Date commentDate){
        
        this.commentDate = commentDate;
    }
    
    //Getters
    /**
     * 
     * @return 
     */
    public UUID getCommentID(){
        
        return commentID;
    }
    
    /**
     * 
     * @return 
     */
    public String getComment(){
              
        
        return comment;
    }
    
    
    /**
     * 
     * @return 
     */
    public String getUser(){
        
        
        
        return user;
    }
    
    
    /**
     * 
     * @return 
     */
    public UUID getPicID(){
        
         
     return picid; 
     
    }
    
    /**
     * 
     * @return 
     */
    public Date getCommentDate(){
        
        
     return commentDate;   
    }
    
    
}
            
    

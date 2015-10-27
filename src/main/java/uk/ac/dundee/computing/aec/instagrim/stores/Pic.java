/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;

/**
 *
 * @author Administrator
 */
public class Pic {

    private ByteBuffer bImage = null;
    private int length;
    private String type;
    private java.util.UUID UUID=null;
    
    public void Pic() {

    }
    
    /**
     * 
     * @param UUID 
     */
    public void setUUID(java.util.UUID UUID){
        this.UUID =UUID;
    }
    
    /**
     * 
     * @return 
     */
    public String getSUUID(){
        return UUID.toString();
    }
    
    /**
     * 
     * @param bImage
     * @param length
     * @param type 
     */
    public void setPic(ByteBuffer bImage, int length,String type) {
        this.bImage = bImage;
        this.length = length;
        this.type=type;
    }

    /**
     * 
     * @return 
     */
    public ByteBuffer getBuffer() {
        return bImage;
    }

    /**
     * 
     * @return 
     */
    public int getLength() {
        return length;
    }
    
    /**
     * 
     * @return 
     */
    public String getType(){
        return type;
    }

    /**
     * 
     * @return 
     */
    public byte[] getBytes() {
         
        byte image[] = Bytes.getArray(bImage);
        return image;
    }

}

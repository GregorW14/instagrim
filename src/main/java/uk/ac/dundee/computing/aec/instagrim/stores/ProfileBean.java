/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

/**
 *
 * @author Greg
 */
public class ProfileBean {
    private String username = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String street = null;
    private String city = null;
    private String[] address = {"","",""};
    private int zip = 0;
    private Pic profilePic = null;
    
    public void ProfileBean(){
        
    }
    
    /**
     * 
     * @param username 
     */
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 
     * @param firstName 
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    /**
     * 
     * @param lastName 
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    /**
     * 
     * @param email 
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * 
     * @param address 
     */
    public void setAddress(String address[])
    {
        this.street = address[0];
        this.city = address[1];
        this.zip = Integer.parseInt(address[2]);
        this.address = address;
    }
    

    
    /**
     * 
     * @return 
     */
     public String getUsername()
    {
        return username;
    }
     
     /**
      * 
      * @return 
      */
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * 
     * @return 
     */
    public String getLastName()
    {
        return lastName;
    }
    
    /**
     * 
     * @return 
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * 
     * @return 
     */
    public String[] getAddress()
    {
        return address;
    }
    
    /**
     * 
     * @return 
     */
    public String getStreet()
    {
        return street;
    }
    
    /**
     * 
     * @return 
     */
    public String getCity()
    {
        return city;
    }
    
    /**
     * 
     * @return 
     */
    public int getZip()
    {
        return zip;
    }
    
    /**
     * 
     * @param pic 
     */
    public void setProfilePic(Pic pic)
    {
        this.profilePic = pic;
    }
    
    /**
     * 
     * @return 
     */
    public Pic getProfilePic()
    {
        return profilePic;
    }
        
}   


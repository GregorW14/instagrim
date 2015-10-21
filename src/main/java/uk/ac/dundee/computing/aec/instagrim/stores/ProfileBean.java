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
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setAddress(String address[])
    {
        this.street = address[0];
        this.city = address[1];
        this.zip = Integer.parseInt(address[2]);
        this.address = address;
    }
    
    public void setProfilePic(Pic profilePic)
    {
        this.profilePic = profilePic;
    }
    
     public String getUsername()
    {
        return username;
    }
     
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String[] getAddress()
    {
        return address;
    }
    
    public String getStreet()
    {
        return street;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public int getZip()
    {
        return zip;
    }
    
    public Pic getProfilePic()
    {
        return profilePic;
    }
        
}   


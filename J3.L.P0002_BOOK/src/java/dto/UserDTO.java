/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author ADMIN
 */
public class UserDTO {

    private String userID;
    private String fullName;
    private boolean isAdmin;

    public UserDTO() {
    }

    public UserDTO(String userID, boolean isAdmin) {
        this.userID = userID;
        this.isAdmin = isAdmin;
    }

    public UserDTO(String userID, String fullName, boolean isAdmin) {
        this.userID = userID;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}

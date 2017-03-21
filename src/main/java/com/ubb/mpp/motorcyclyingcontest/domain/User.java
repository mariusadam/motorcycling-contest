package com.ubb.mpp.motorcyclyingcontest.domain;

import java.util.Date;

/**
 * @author Marius Adam
 */
//@DatabaseTable(tableName = "users")
public class User extends Entity<Integer> {
//    @DatabaseField(columnName = "last_name")
//    @NotNull
//    @Size(max = 30)
    private String lastName;

//    @DatabaseField(columnName = "first_name")
//    @NotNull
//    @Size(max = 30)
    private String firstName;

//    @DatabaseField(columnName = "is_active")
//    @NotNull
    private Boolean isActive;

//    @DatabaseField(columnName = "logged_in")
//    @NotNull
    private Boolean loggedIn;

//    @DatabaseField(columnName = "last_login")
    private Date lastLogin;

//    @DatabaseField
//    @NotNull
//    @Size(max = 255)
    private String email;

//    @DatabaseField
//    @NotNull
//    @Size(max = 128)
    private String password;

//    @DatabaseField
//    @NotNull
//    @Size(max = 32)
    private String salt;

    private String plainPassword;

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
        
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
        
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;

    }

    public void eraseCredentials() {
        this.plainPassword = null;

    }

    @Override
    public String toString() {
        return String.format(
                "User: [%d, %s, %s, %s, %s]",
                getId(),
                getEmail(),
                getFirstName(),
                getLastName(),
                getLastLogin()
        );
    }
}

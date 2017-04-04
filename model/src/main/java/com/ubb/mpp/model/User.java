package com.ubb.mpp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Marius Adam
 */
public class User extends Entity<Integer> {
    @NotNull
    @Size(max = 30)
    private String lastName;

    @NotNull
    @Size(max = 30)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 128)
    private String password;

    @NotNull
    @Size(max = 32)
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
                "User: [%d, %s, %s, %s]",
                getId(),
                getEmail(),
                getFirstName(),
                getLastName()
        );
    }
}

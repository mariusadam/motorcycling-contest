package com.ubb.mpp.networking.dto;

import java.io.Serializable;

/**
 * @author Marius Adam
 */
public class UserDTO implements Serializable {
    private Integer id;
    private String password;
    private String salt;

    private String email;

    public UserDTO(Integer id, String email, String password, String salt) {
        this.id = id;
        this.password = password;
        this.salt = salt;
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserDTO[" + id + ' ' + password + "]";
    }
}

package com.ubb.mpp.motorcyclyingcontest.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by marius on 11.12.2016.
 */
@DatabaseTable(tableName = "users")
public class User extends Entity<Integer> {
    @DatabaseField(columnName = "last_name")
    @NotNull
    @Size(max = 30)
    private String lastName;

    @DatabaseField(columnName = "first_name")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @DatabaseField(columnName = "is_active")
    @NotNull
    private Boolean isActive;

    @DatabaseField(columnName = "logged_in")
    @NotNull
    private Boolean loggedIn;

    @DatabaseField(columnName = "last_login")
    private Date lastLogin;

    @DatabaseField
    @NotNull
    @Size(max = 255)
    private String email;

    @DatabaseField
    @NotNull
    @Size(max = 128)
    private String password;

    @DatabaseField
    @NotNull
    @Size(max = 32)
    private String salt;

    private String plainPassword;

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public User setIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public User setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public User setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public User setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public User setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public User eraseCredentials() {
        this.plainPassword = null;

        return this;
    }
}

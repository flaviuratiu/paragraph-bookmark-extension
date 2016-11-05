package com.permanentMarker.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 13 Oct 2016
 */
public class UserAuthenticationRequest {

    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

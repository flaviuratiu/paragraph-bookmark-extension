package com.permanentMarker.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class ResetPasswordRequest {

    long userId;
    String newPassword;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

package com.permanentMarker.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class UpdatePasswordRequest extends ResetPasswordRequest {

    private String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

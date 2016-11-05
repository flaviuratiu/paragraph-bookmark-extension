package com.permanentMarker.web;

import com.permanentMarker.service.UserService;
import com.permanentMarker.transfer.exception.GenericException;
import com.permanentMarker.transfer.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Flaviu Ratiu
 * @since 13 Oct 2016
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserTO createUser(UserAuthenticationRequest request) throws GenericException {
        return userService.createUser(request.getEmail(), request.getPassword());
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserTO logIn(UserAuthenticationRequest request) throws GenericException {
        return userService.logIn(request.getEmail(), request.getPassword());
    }

    @RequestMapping(value = "/{token}/password/reset", method = RequestMethod.POST)
    public void resetPassword(ResetPasswordRequest request, String token) {
        userService.resetPassword(request.getUserId(), request.getNewPassword(), token);
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    public void updatePassword(UpdatePasswordRequest request) throws GenericException {
        userService.updatePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
    }

}

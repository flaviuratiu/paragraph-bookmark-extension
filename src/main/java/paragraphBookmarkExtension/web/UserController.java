package paragraphBookmarkExtension.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import paragraphBookmarkExtension.service.UserService;
import paragraphBookmarkExtension.transfer.exception.GenericException;
import paragraphBookmarkExtension.transfer.user.*;

/**
 * @author Flaviu Ratiu
 * @since 13 Oct 2016
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sing-up", method = RequestMethod.POST)
    public UserTO createUser(CreateUserRequest request) throws GenericException {
        return userService.createUser(request.getEmail(), request.getPassword(), request.isBookmarkEmailReminder());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserTO logIn(UserAuthenticationRequest request) throws GenericException {
        return userService.logIn(request.getEmail(), request.getPassword());
    }

    @RequestMapping(value = "/preferences", method = RequestMethod.POST)
    public void updatePreferences(UserPreferencesRequest request) {
        userService.updateBookmarkEmailReminderFlag(request.getUserId(), request.isBookmarkEmailReminder());
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

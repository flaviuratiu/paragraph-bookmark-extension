package paragraphBookmarkExtension.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import paragraphBookmarkExtension.service.UserService;
import paragraphBookmarkExtension.transfer.reports.GenericCountResponse;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registered-count", method = RequestMethod.GET)
    public GenericCountResponse getRegisteredUsersCount() {
        long count = userService.countRegistered();
        return new GenericCountResponse("registeredUsers", count);
    }

    @RequestMapping(value = "/anonymous-count", method = RequestMethod.GET)
    public GenericCountResponse geAnonymousUsersCount() {
        long count = userService.countAnonymous();
        return new GenericCountResponse("anonymousUsers", count);
    }
}

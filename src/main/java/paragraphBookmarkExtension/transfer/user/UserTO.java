package paragraphBookmarkExtension.transfer.user;

import java.util.Date;

/**
 * @author Flaviu Ratiu
 * @since 20 Oct 2016
 */
public class UserTO {

    private long id;
    private String email;
    private Date creationDate;
    private boolean receiveEmailReminders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isReceiveEmailReminders() {
        return receiveEmailReminders;
    }

    public void setReceiveEmailReminders(boolean receiveEmailReminders) {
        this.receiveEmailReminders = receiveEmailReminders;
    }
}

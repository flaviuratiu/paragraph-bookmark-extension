package paragraphBookmarkExtension.dao.domain;

import paragraphBookmarkExtension.transfer.user.UserTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email")
    private String email;

    @GeneratedValue
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "iterations")
    private int iterations;

    @Column(name = "salt")
    private String salt;

    @Column(name = "hash")
    private String hash;

    @Column(name = "bookmarks_email_reminder")
    private boolean bookmarksEmailReminder;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isBookmarksEmailReminder() {
        return bookmarksEmailReminder;
    }

    public void setBookmarksEmailReminder(boolean bookmarksEmailReminder) {
        this.bookmarksEmailReminder = bookmarksEmailReminder;
    }

    public UserTO asUserTO() {
        UserTO userTO = new UserTO();
        userTO.setId(getId());
        userTO.setEmail(getEmail());
        userTO.setCreationDate(getCreationDate());
        userTO.setReceiveEmailReminders(isBookmarksEmailReminder());
        return userTO;
    }
}

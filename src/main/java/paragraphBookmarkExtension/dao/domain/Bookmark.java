package paragraphBookmarkExtension.dao.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
@Entity
@Table(name = "bookmarks")
public class Bookmark implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "document_url", nullable = false)
    private String documentUrl;

    @Column(name = "text_first_part")
    private String textFirstPart;

    @Column(name = "text_last_part")
    private String textLastPart;

    @GeneratedValue
    @Column(name = "creation_date")
    private Date creationDate;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getTextFirstPart() {
        return textFirstPart;
    }

    public void setTextFirstPart(String textFirstPart) {
        this.textFirstPart = textFirstPart;
    }

    public String getTextLastPart() {
        return textLastPart;
    }

    public void setTextLastPart(String textLastPart) {
        this.textLastPart = textLastPart;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

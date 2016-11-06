package com.permanentMarker.dao.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
@Entity
@Table(name = "marks")
public class Mark implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "document_url", nullable = false)
    private String documentUrl;

    @Column(name = "text")
    private String text;

    @Column(name = "preceding_text")
    private String precedingText;

    @Column(name = "trailing_text")
    private String trailingText;

    @Column(name = "email_reminder")
    private boolean emailReminderEnabled;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrecedingText() {
        return precedingText;
    }

    public void setPrecedingText(String precedingText) {
        this.precedingText = precedingText;
    }

    public String getTrailingText() {
        return trailingText;
    }

    public void setTrailingText(String trailingText) {
        this.trailingText = trailingText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isEmailReminderEnabled() {
        return emailReminderEnabled;
    }

    public void setEmailReminderEnabled(boolean emailReminderEnabled) {
        this.emailReminderEnabled = emailReminderEnabled;
    }

    @Override
    public String toString() {
        return super.toString() + ":\n" +
                "id: " + id + ",\n" +
                "userId: " + userId + ",\n" +
                "documentUrl: " + documentUrl + ",\n" +
                "text: " + text + ",\n" +
                "precedingText: " + precedingText + ",\n" +
                "trailingText: " + trailingText + ",\n" +
                "emailReminderEnabled: " + emailReminderEnabled + ",\n" +
                "creationDate: " + creationDate + "\n";
    }
}

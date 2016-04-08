package io.realm.handson2.twitter.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import twitter4j.Status;

public class Tweet extends RealmObject {
    @PrimaryKey
    private long id;
    private Date createdAt;
    private String screenName;
    private String text;
    private String iconUrl;

    public Tweet() {
    }

    public Tweet(Status status) {
        //https://dev.twitter.com/overview/api/twitter-ids-json-and-snowflake
        setId(status.getId());
        setCreatedAt(status.getCreatedAt());
        setScreenName(status.getUser().getScreenName());
        setText(status.getText());
        setIconUrl(status.getUser().getProfileImageURLHttps());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}

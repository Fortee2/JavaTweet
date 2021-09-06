package com.fenchurchtech.messaging.twitter.common.Friends;

import java.time.LocalDateTime;

public class Friend {
    private String id_str;
    private String name;
    private String location ;
    private String description;
    private String url;
    private Status status;
    private int followers_count;
    private int friends_count;
    private int statuses_count;
    private String created_at; //UTC
    private boolean following;
    private boolean verified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getFollowerCount() {
        return followers_count;
    }

    public void setFollowerCount(int followerCount) {
        this.followers_count = followerCount;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public int getStatusCount() {
        return statuses_count;
    }

    public void setStatusCount(int statusCount) {
        statuses_count = statusCount;
    }

    public String getCreated() {
        return created_at;
    }

    public void setCreated(String created) {
        this.created_at = created;
    }

    public boolean isFollowingMe() {
        return following;
    }

    public void setFollowingMe(boolean followingMe) {
        this.following = followingMe;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}


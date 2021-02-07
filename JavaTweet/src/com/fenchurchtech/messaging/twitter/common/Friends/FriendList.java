package com.fenchurchtech.messaging.twitter.common.Friends;

import java.util.ArrayList;

public class FriendList {
    private ArrayList<Friend> users = new ArrayList<>();

    public ArrayList<Friend> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Friend> users) {
        this.users = users;
    }
}

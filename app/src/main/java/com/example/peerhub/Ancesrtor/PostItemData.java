package com.example.peerhub.Ancesrtor;

import android.graphics.Bitmap;

public class PostItemData {
    private String postId; // Added postId field
    private  String userId;
    private  String username;
    private String name;
    private Bitmap profilePictureBitmap; // Changed to Bitmap for profile picture
    private String imageUrl;
    private String text;
    private boolean followed;
    private String profilePictureDrawable;

    public PostItemData() {
        // Default constructor required for Firebase
    }

    public PostItemData(String postId, String userId, String profilePictureDrawable, String username, String name, String imageUrl, String text) {
        this.postId = postId;
        this.userId = userId;
        this.profilePictureDrawable = profilePictureDrawable;
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.text = text;
         this.followed = false;
    }

    // Getters and setters
    public String getPostId() {
        return postId;
    }
    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public  String getUserId () {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public  String getUsername () {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getProfilePictureBitmap() {
        return profilePictureBitmap;
    }

    public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
        this.profilePictureBitmap = profilePictureBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

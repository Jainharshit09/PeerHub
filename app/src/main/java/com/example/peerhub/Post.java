package com.example.peerhub;

import android.net.Uri;

import com.example.peerhub.Ancesrtor.PostItemData;

public class Post extends PostItemData {
    private String content;
    private String handle;
    private Uri imageUri; // Optional for image posts

    public Post(String postId, String userId, String profilePictureDrawable, String username, String name, String imageUrl, String text) {
        super(postId, userId, profilePictureDrawable, username, name, imageUrl, text);
    }
}

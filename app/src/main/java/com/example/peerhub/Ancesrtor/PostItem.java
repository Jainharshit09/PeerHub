    package com.example.peerhub.Ancesrtor;

    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.util.AttributeSet;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.example.peerhub.CommentsActivity;
    import com.example.peerhub.R;

    public class PostItem extends LinearLayout implements View.OnClickListener {

        private Context context;
        private String userId;
        private String postId;
        private TextView nameTextView;
        private TextView usernameTextView;
        private TextView postContentTextView;
        private ImageView profilePictureImageView;
        private ImageView userImageView; // Changed to userImageView for uploaded image
        private PostItemData postItemData;

        public PostItem(Context context) {
            super(context);
            initializeViews(context);
        }

        public PostItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            initializeViews(context);
        }

        private void initializeViews(Context context) {
            this.context = context;

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.post_item, this, true);

            profilePictureImageView = view.findViewById(R.id.profile_picture);
            nameTextView = view.findViewById(R.id.name);
            usernameTextView = view.findViewById(R.id.username);
            postContentTextView = view.findViewById(R.id.post_content);
            ImageButton likeButton = view.findViewById(R.id.like_button);
            ImageButton commentButton = view.findViewById(R.id.comment_button);
            ImageButton shareButton = view.findViewById(R.id.share_button);
            ImageButton saveButton = view.findViewById(R.id.save_button);
            userImageView = view.findViewById(R.id.selected_image);

            // Set click listeners
            commentButton.setOnClickListener(this);
            shareButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.comment_button) {
                handleCommentClick();
            } else if (id == R.id.share_button) {
                handleShareClick();
            } else {
                // Handle other button clicks if needed
            }
        }

        public void setProfilePicture(Bitmap bitmap) {
            profilePictureImageView.setImageBitmap(bitmap);
        }

        public void setName(String name) {
            nameTextView.setText(name);
        }

        public void setUsername(String username) {
            usernameTextView.setText(username);
        }

        public void setPostContent(String content) {
            postContentTextView.setText(content);
        }

      public void setPostData(PostItemData postItemData) {
        this.postItemData = postItemData;
        nameTextView = findViewById(R.id.name);
        usernameTextView = findViewById(R.id.username);
        postContentTextView = findViewById(R.id.post_content);
        profilePictureImageView.setImageBitmap(postItemData.getProfilePictureBitmap());
        nameTextView.setText(postItemData.getName());
        usernameTextView.setText(postItemData.getUsername());
        postContentTextView.setText(postItemData.getText());
        if(postContentTextView!=null){
            Log.d("SetPostdata","PostContent View Exists");
        }
        // Set uploaded image if available
        String imageUrl = postItemData.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            userImageView.setVisibility(View.VISIBLE); // Make sure the imageView is visible
            Glide.with(context)
                 .load(imageUrl)
                 .into(userImageView);
        } else {
            userImageView.setVisibility(View.GONE); // Hide the imageView if no image available
        }
    }


        public void setUploadedImage(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(context)
                    .load(imageUrl)
                    .into(userImageView);
        }
            else {
            userImageView.setVisibility(View.GONE); // Hide the imageView
                }
        }

        private void handleCommentClick() {
            Intent intent = new Intent(context, CommentsActivity.class);
            context.startActivity(intent);
        }

        void handleShareClick () {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this post by " + postItemData.getUsername() + ": " + postItemData.getText());
        context.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

        public void setUserid (String userId) {
             this.userId = userId;
        }


        public void setPostid (String postId) {
            this.postId = postId;
        }
    }


package com.example.peerhub.Ancesrtor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.peerhub.CommentsActivity;
import com.example.peerhub.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public interface OnLikeClickListener {
        void onLikeClick(int position);
    }

    private final List<PostItemData> postItems;
    private final Context context;
    private final OnLikeClickListener likeClickListener;  // Interface for like click handling
     private List<PostItemData> filteredList;

    public PostAdapter(List<PostItemData> postItems, Context context, OnLikeClickListener likeClickListener) {
        this.postItems = postItems;
        this.context = context;
        this.likeClickListener = likeClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostItemData postItemData = postItems.get(position);
        holder.bind(postItemData);
        holder.nameTextView.setText(postItemData.getName());
        holder.usernameTextView.setText(postItemData.getUsername());
        holder.postContentTextView.setText(postItemData.getText());
        holder.postItem.setPostData(postItemData);

       String imageUrl = postItemData.getImageUrl();
    if (imageUrl != null && !imageUrl.isEmpty()) {
        holder.image.setVisibility(View.VISIBLE);
        Glide.with(context)
             .load(imageUrl)
             .into(holder.image);
    } else {
        holder.image.setVisibility(View.GONE);
    }
}

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private final PostItem postItem;
        private final ImageButton likeButton;
        private final ImageButton commentButton;
        private final ImageButton shareButton;
        private TextView usernameTextView;
        private TextView nameTextView;
        private TextView postContentTextView;
        private ImageView image;

        private ImageView profilePictureImageView;
    private ImageView userImageView;
    private Button follow;
    private ImageButton save;
        public PostViewHolder(View itemView) {
            super(itemView);
            postItem = new PostItem(itemView.getContext());
            save=itemView.findViewById(R.id.save_button);
            likeButton = itemView.findViewById(R.id.like_button);
            commentButton = itemView.findViewById(R.id.comment_button);
            usernameTextView = itemView.findViewById(R.id.username);
            nameTextView = itemView.findViewById(R.id.name);
            postContentTextView = itemView.findViewById(R.id.post_content);
            image = itemView.findViewById(R.id.selected_image);
            shareButton = itemView.findViewById(R.id.share_button);
            shareButton.setOnClickListener(v -> handleShareButtonClick());
            follow=itemView.findViewById(R.id.follow);
            follow.setOnClickListener(v -> {
                if (follow.getText().toString().equals("Follow")) {
                    follow.setText("Following");
                } else {
                    follow.setText("Follow");
                }
            });
            final boolean[] isCommented = {false};
            commentButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    PostItemData postItemData = postItems.get(position);
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("Post ID", postItemData.getPostId());
                    intent.putExtra("Username", postItemData.getUsername());
                    context.startActivity(intent);
                    if (isCommented[0]) {
                        commentButton.setImageResource(R.drawable.message);
                    } else {
                        commentButton.setImageResource(R.drawable.message_cyan);
                    }
                    isCommented[0] = !isCommented[0];;
                }
            });
            final boolean[] isSaved = {false};
            save.setOnClickListener(v -> {
                if (isSaved[0]) {
                    save.setImageResource(R.drawable.save);
                } else {
                    save.setImageResource(R.drawable.save_blue);
                }
                isSaved[0] = !isSaved[0];
            });
            final boolean[] isLiked = {false};
            likeButton.setOnClickListener(v -> {
                if (isLiked[0]) {
                    likeButton.setImageResource(R.drawable.heart);
                } else {
                    likeButton.setImageResource(R.drawable.heart_red);
                }
                isLiked[0] = !isLiked[0];
            });

        }

        public void bind(PostItemData postItemData) {
            Log.d("PostAdapter", "Username: " + postItemData.getUsername());
            Log.d("PostAdapter", "Name: " + postItemData.getName());
            Log.d("PostAdapter", "Post Content: " + postItemData.getText());
            // Bind data to the PostItem view
             String imageUrl = postItemData.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            image.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                 .load(imageUrl)
                 .placeholder(R.drawable.image)
                 .into(image);
        } else {
            image.setVisibility(View.GONE);
        }
            postItem.setProfilePicture(postItemData.getProfilePictureBitmap());
            postItem.setName(postItemData.getName());
            postItem.setUsername(postItemData.getUsername());
            postItem.setPostContent(postItemData.getText());

            postItem.setUserid(postItemData.getUserId());
            postItem.setPostid(postItemData.getPostId());
        }
        final boolean[] isShared = {false};
        private void handleShareButtonClick() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                PostItemData postItemData = postItems.get(position);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("Text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this post by " + postItemData.getUsername() + ": " + postItemData.getText());
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
                 if (isShared[0]) {
                     shareButton.setImageResource(R.drawable.share);
                 } else {
                     shareButton.setImageResource(R.drawable.share_blue);
                 }
                 isShared[0] = !isShared[0];
            }
        }
    }
}

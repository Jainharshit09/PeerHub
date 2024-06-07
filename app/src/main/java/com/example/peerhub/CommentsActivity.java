package com.example.peerhub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    public static final String EXTRA_POST_ID = "Post ID";
    RecyclerView recyclerViewComments;
    EditText commentText;
    ImageView sendButton, closeButton;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    DatabaseReference commentsReference;
    String currentUserUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_page);

        recyclerViewComments = findViewById(R.id.recyclerview_comments);
        commentText = findViewById(R.id.et_comment);
        sendButton = findViewById(R.id.btn_send_message);
        closeButton = findViewById(R.id.btn_close);

        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentAdapter);

        String postId = getIntent().getStringExtra(EXTRA_POST_ID);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
  if (currentUser != null) {
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                String username = dataSnapshot.child("username").getValue(String.class);
                if (username != null && !username.isEmpty()) {
                    currentUserUsername = username;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("CommentsActivity", "Failed to retrieve current user's username: " + databaseError.getMessage());
        }
    });
}


        if (postId != null && !postId.isEmpty()) {
            DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);
            commentsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        comments.clear();
                        for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                            Comment comment = commentSnapshot.getValue(Comment.class);
                            if (comment != null) {
                                comments.add(comment);
                            }
                        }
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CommentsActivity", "No comments found for postId: " + postId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("CommentsActivity", "Failed to read comments.", databaseError.toException());
                }
            });

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commentContent = commentText.getText().toString().trim();
                    if (!commentContent.isEmpty()) {
                        if (currentUserUsername != null && !currentUserUsername.isEmpty()) {
                            postComment(postId, currentUserUsername, commentContent);
                        } else {
                            Log.e("CommentsActivity", "Current user's username is null or empty");
                        }
                    }
                }
            });
        } else {
            Log.e("CommentsActivity", "postId is null or empty");
        }
    }

    private void postComment(String postId, String username, String commentContent) {
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);
        String commentId = commentsRef.push().getKey();
        Comment newComment = new Comment(commentId, username, commentContent);
        commentsRef.child(commentId).setValue(newComment)
                .addOnSuccessListener(aVoid -> {
                    commentText.setText("");
                })
                .addOnFailureListener(e -> {
                    Log.e("CommentsActivity", "Failed to post comment: " + e.getMessage());
                });
    }
}

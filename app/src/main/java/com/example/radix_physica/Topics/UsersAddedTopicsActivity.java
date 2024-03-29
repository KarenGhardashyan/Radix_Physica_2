package com.example.radix_physica.Topics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.radix_physica.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsersAddedTopicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_added_topics);

        TextView titleTextView = findViewById(R.id.title);
        TextView contentTextView = findViewById(R.id.content);
        TextView linkTextView = findViewById(R.id.web);

        DatabaseReference topicRef = FirebaseDatabase.getInstance().getReference().child("topics").child("Nu3kizm1nZxbyt5n6Gw");

        topicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String topicHead = dataSnapshot.child("topicHead").getValue(String.class);
                    String topicContent = dataSnapshot.child("topicContent").getValue(String.class);
                    String webLinks = dataSnapshot.child("webLinks").getValue(String.class);

                    titleTextView.setText(topicHead);
                    contentTextView.setText(topicContent);
                    linkTextView.setText(Html.fromHtml("<a href=\"" + webLinks + "\">" + webLinks + "</a>"));
                    linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

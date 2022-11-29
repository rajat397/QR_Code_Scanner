package com.example.android.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

import com.example.android.qrcodescanner.Models.Record;
import com.google.firebase.database.ValueEventListener;

public class ResultDisplayActivity extends AppCompatActivity {
    private  FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        database=FirebaseDatabase.getInstance();
        Intent intent = getIntent();

        String result = intent.getStringExtra("Result");
        String recordId = intent.getStringExtra("recordId");
        Date date = (Date) this.getIntent().getExtras().get("date");

        TextView restext = findViewById(R.id.tvResult);
        ImageView search = findViewById(R.id.search);
        ImageView share = findViewById(R.id.share);
        ImageView save = findViewById(R.id.save);
        ImageView copy = findViewById(R.id.copy);

        restext.setText(result);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, result);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
//                    finish();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Create an ACTION_SEND Intent*/
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("Result", result);
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_SHORT).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.setImageResource(R.drawable.ic_baseline_favorite_24);
                TextView tvSave = findViewById(R.id.tvSave);
                tvSave.setText("Saved");




                Record record = new Record(result,"SAVED", FirebaseAuth.getInstance().getUid(), date);

                database.getReference().child("Saved").child(FirebaseAuth.getInstance().getUid()).push().setValue(record);


                HashMap<String, Object> obj = new HashMap<>();
                obj.put("state","SAVED");
                FirebaseDatabase.getInstance().getReference().child("Records").child(recordId).updateChildren(obj);

            }
        });
    }
}
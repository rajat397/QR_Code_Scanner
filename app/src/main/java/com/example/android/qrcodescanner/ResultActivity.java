package com.example.android.qrcodescanner;

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

import com.example.android.qrcodescanner.Models.Record;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Record model = (Record) intent.getSerializableExtra("Model");


        TextView tvSave = findViewById(R.id.tvSave);


        TextView restext = findViewById(R.id.tvResult);
        ImageView search = findViewById(R.id.search);
        ImageView share = findViewById(R.id.share);
        ImageView save = findViewById(R.id.save);
        ImageView copy = findViewById(R.id.copy);


        if (model.getState().equalsIgnoreCase("SAVED")) {

            save.setImageResource(R.drawable.ic_baseline_favorite_24);
            tvSave.setText("Saved");

        }
        String result = model.getData();
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
                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });


        if (model.getState().equalsIgnoreCase("UNSAVED")) {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    save.setImageResource(R.drawable.ic_baseline_favorite_24);

                    tvSave.setText("Saved");
                    Record record = new Record(result, "SAVED", FirebaseAuth.getInstance().getUid(), new Date());
//                        record.setData(s);
//                        record.setState("UNSAVED");
//                        record.setRecordedAt(new Date());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Saved").child(FirebaseAuth.getInstance().getUid()).push();


                    ref.setValue(record);

                    HashMap<String, Object> obj = new HashMap<>();
                    obj.put("state", "SAVED");
                    FirebaseDatabase.getInstance().getReference().child("Records").child(model.getRecordId()).updateChildren(obj);


                }
            });
        }



    }
}
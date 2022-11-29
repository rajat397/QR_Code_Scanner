package com.example.android.qrcodescanner.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.qrcodescanner.Models.Record;
import com.example.android.qrcodescanner.R;
import com.example.android.qrcodescanner.ResultActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;

public class SavedAdapter extends FirebaseRecyclerAdapter<Record,SavedAdapter.viewHolder> {

    Context context;

    public SavedAdapter(Context context, @NonNull FirebaseRecyclerOptions<Record> options) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SavedAdapter.viewHolder holder, int position, @NonNull Record model) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra("Model",  model);
                context.startActivity(intent);

            }
        });

        holder.heading.setText(model.getData());

        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd-MM-yyyy            hh:mm:ss a");

//        "yyyy-MM-dd'T'HH:mm:ss'Z'"
        String date = dateFormat.format(model.getRecordedAt());
        holder.date.setText(date);

//        SimpleDateFormat timeFormat = new SimpleDateFormat("");
//        String time = dateFormat.format(model.getRecordedAt());
//        holder.time.setText(time);
//        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z").format(model.getData()).toString());
    }

    @NonNull
    @Override
    public SavedAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_record,parent,false);
        return  new viewHolder(view);
    }



    public class viewHolder extends RecyclerView.ViewHolder{

        TextView heading , date;

        View view;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            date=itemView.findViewById(R.id.date);

            view = itemView;

        }
    }
}

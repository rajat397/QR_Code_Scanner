package com.example.android.qrcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Date;

import com.example.android.qrcodescanner.Models.Record;

public class ScanFragment extends Fragment {
    private final int CAMERA_REQUEST_CODE = 101;

    private CodeScanner mCodeScanner;

    public ScanFragment() {
        // Required empty public constructor
    }

    TextView tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_scan, container, false);
         tv1 = root.findViewById(R.id.tv1);
         setupPermissions();
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFlashEnabled(false);
        mCodeScanner.setTouchFocusEnabled(true);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                          String s = result.getText().toString() ;


                        Date date = new Date();

                        Record record = new Record(s,"UNSAVED", FirebaseAuth.getInstance().getUid(), date);

                       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Records").push();
                       record.setRecordId(reference.getKey());
                       reference.setValue(record);

                        Intent intent = new Intent(getContext(),ResultDisplayActivity.class);
                        intent.putExtra("Result", String.valueOf((s)));
                        intent.putExtra("recordId",reference.getKey());
                        intent.putExtra("date", date);
                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCodeScanner.startPreview();
            }
        });


        return root;


    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();
    }

    private void setupPermissions(){
        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }

    private void makeRequest(){
        String [] permissions= new String[1];
        permissions[0]=Manifest.permission.CAMERA;
//        permissions[1]=Manifest.permission_group.CAMERA;
        ActivityCompat.requestPermissions(getActivity(),permissions,CAMERA_REQUEST_CODE);
    }


    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        // PERMISSION GRANTED
                    } else {
                        // PERMISSION NOT GRANTED

                        Toast.makeText(getContext(),"You Need to provide camera permissions to be able to use this app",Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


}
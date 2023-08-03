package com.example.udate_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  UpdateHelper.onUpdateCheckListener{
    private static final String APK_URL = "copy your link here .."; // Replace with your APK URL
    private ApkDownloader apkDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apkDownloader = new ApkDownloader(this);
        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();
    }



   // https://drive.google.com/file/d/1-5hybLlsPlL22w1IHViRabueRpG7s-zJ/view?usp=sharing

    @Override
    public void onUpdateCheckListener(String urlapp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Download App");
        builder.setMessage("Do you want to download and install the app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Initiate APK download and installation when the positive button is clicked

                apkDownloader.downloadApkFromUrl(APK_URL);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Handle the case when the user clicks the negative button (e.g., show a message or do nothing)
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }




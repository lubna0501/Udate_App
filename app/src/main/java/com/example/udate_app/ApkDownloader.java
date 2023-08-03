package com.example.udate_app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.ContextCompat;

public class ApkDownloader {
    private final Context context;
    private long downloadId = -1;

    public ApkDownloader(Context context) {
        this.context = context;
    }

    public void downloadApkFromUrl(String apkUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "app-debug.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);

        // Register a broadcast receiver to listen for download completion
        context.registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long completedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (completedDownloadId == downloadId) {
                // Install the downloaded APK
                installDownloadedApk();
            }
            context.unregisterReceiver(this);
        }
    };

    public void installDownloadedApk() {
        Uri apkUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "app_update.apk"));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(apkUri, "copy your link here...");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ContextCompat.startActivity(context, intent, null);
    }
}

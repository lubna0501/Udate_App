package com.example.udate_app;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        Map<String,Object> defaultvalue= new HashMap<>();
        defaultvalue.put(UpdateHelper.KEY_UPDATE,false);
        defaultvalue.put(UpdateHelper.KEY_UPDATE_VERSION,"1.5");
        defaultvalue.put(UpdateHelper.KEY_UPDATE_URL, "copy your link here");

        firebaseRemoteConfig.setDefaultsAsync(defaultvalue);
        firebaseRemoteConfig.fetch(5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    firebaseRemoteConfig.fetchAndActivate();
                }
            }
        });
    }
}

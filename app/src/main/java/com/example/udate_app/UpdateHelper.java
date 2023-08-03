package com.example.udate_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {
    public  static  String KEY_UPDATE= "isUpdate";
    public  static  String KEY_UPDATE_VERSION= "Version";
    public  static  String KEY_UPDATE_URL= "Update_url";



    public interface  onUpdateCheckListener
     {
         void onUpdateCheckListener(String urlapp);
     }
     public  static  Builder with(Context context)
     {
         return  new Builder(context);
     }
     private  onUpdateCheckListener onUpdateCheckListener;
     private  Context context;

    public UpdateHelper(Context context,onUpdateCheckListener onUpdateCheckListener) {
        this.context = context;
        this.onUpdateCheckListener= onUpdateCheckListener;
    }

    public void check()
    {
        FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        if(firebaseRemoteConfig.getBoolean(KEY_UPDATE))
        {
            String currentversion= firebaseRemoteConfig.getString(KEY_UPDATE_VERSION);
            String appversion= getAppVersion(context);
            String updateurl= firebaseRemoteConfig.getString(KEY_UPDATE_URL);

            if(!TextUtils.equals(currentversion,appversion) && onUpdateCheckListener!=null)
                onUpdateCheckListener.onUpdateCheckListener(updateurl);
        }
    }

    private String getAppVersion(Context context) {
        String  result = " ";
        try
        {
            result= context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
            result=result.replaceAll("[a-zA-Z]|-","");
        } catch (PackageManager.NameNotFoundException e) {
             e.printStackTrace();
        }
        return  result;
    }

    public static  class Builder{
         private Context context;
         private  onUpdateCheckListener onUpdateCheckListener;

         public  Builder(Context context)
         {
             this.context= context;
         }
         public  Builder onUpdateCheck(onUpdateCheckListener onUpdateCheckListener)
         {
             this.onUpdateCheckListener= onUpdateCheckListener;
             return this;
         }
        public UpdateHelper build()
        {
            return new UpdateHelper(context,onUpdateCheckListener);
        }
        public  UpdateHelper check()
        {
            UpdateHelper updateHelper= build();
            updateHelper.check();
            return  updateHelper;
        }
     }
}

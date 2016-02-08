package de.sevenfactory.helium.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import de.sevenfactory.helium.model.DeviceInfo;

public class DeviceInfoFactory {
    public static final String PLATFORM_NAME = "Android";
    public static final String APP_NAME      = "7TVAndroid";

    private DeviceInfoFactory() {
        // Remove ability to instantiate class
    }

    public static DeviceInfo create(Context context) {
        String versionName = getVersionName(context);
        return new DeviceInfo(APP_NAME, versionName, Build.MODEL, PLATFORM_NAME, String.valueOf(Build.VERSION.SDK_INT));
    }
    
    private static String getVersionName(Context inContext) {
        try {
            String versionName = inContext.getPackageManager()
                    .getPackageInfo(inContext.getPackageName(), 0).versionName;
            
            // Strip version name suffix
            if (versionName.contains("-")) {
                versionName = versionName.substring(0, versionName.indexOf("-"));
            }
            
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        
        return "";
    }
}

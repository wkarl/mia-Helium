package de.prosiebensat1digital.middleware.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import de.prosiebensat1digital.middleware.model.DeviceInfo;

public class DeviceInfoFactory {
	public static final String PLATFORM_NAME = "Android";
	public static final String APP_NAME = "7TVAndroid";
	
	public static DeviceInfo create(Context inContext) {
		String versionName = getVersionName(inContext);
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

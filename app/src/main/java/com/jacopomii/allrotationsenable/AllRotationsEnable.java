package com.jacopomii.allrotationsenable;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Jacopo Mii on 02/01/2018.
 */

public class AllRotationsEnable implements IXposedHookLoadPackage {
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("android") && lpparam.processName.equals("android")) {
            final Class<?> classPhoneWindowManager = XposedHelpers.findClass("com.android.server.policy.PhoneWindowManager", lpparam.classLoader);

            XposedHelpers.findAndHookMethod(classPhoneWindowManager, "init", Context.class, "android.view.IWindowManager", "android.view.WindowManagerPolicy.WindowManagerFuncs", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    try {
                        XposedHelpers.setIntField(param.thisObject, "mAllowAllRotations", 1);
                    } catch (Throwable t) {
                        XposedBridge.log("AllRotationsEnable: error settings PhoneWindowManager.mAllowAllRotations: " + t);
                    }
                }
            });
        }
    }
}

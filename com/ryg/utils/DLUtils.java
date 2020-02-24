/*
 * Copyright (C) 2014 singwhatiwanna(任玉刚) <singwhatiwanna@gmail.com>
 *
 * collaborator:田啸,宋思宇,Mr.Simple
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ryg.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.Closeable;
import java.io.IOException;

public class DLUtils {
    private static final String TAG = "DLUtils";

    public static PackageInfo getPackageInfo(Context context, String apkFilepath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageArchiveInfo(apkFilepath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        } catch (Exception e) {
            // should be something wrong with parse
            e.printStackTrace();
        }

        return pkgInfo;
    }

    public static CharSequence getAppLabel(Context context, String apkFilepath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = getPackageInfo(context, apkFilepath);
        if (pkgInfo == null) {
            return null;
        }

        // Workaround for http://code.google.com/p/android/issues/detail?id=9151
        ApplicationInfo appInfo = pkgInfo.applicationInfo;
        if (Build.VERSION.SDK_INT >= 8) {
            appInfo.sourceDir = apkFilepath;
            appInfo.publicSourceDir = apkFilepath;
        }

        return pm.getApplicationLabel(appInfo);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

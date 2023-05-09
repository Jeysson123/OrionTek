package com.orientek.app.Utils;

import android.content.Context;
import android.content.res.Resources;

import com.orientek.app.R;

public class UrlUtils {

    private static Resources resources;

    public static String GetUrl(Context context, String env){

        resources = context.getResources();

        return env.equalsIgnoreCase("clients") ? resources.getString(R.string.url_api).replace("environment","clients")
                : resources.getString(R.string.url_api).replace("environment","directions");
    }
}

package com.zhy.changeskin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.core.content.res.ResourcesCompat;

import com.zhy.changeskin.utils.L;

public class ResourceManager {
    private static final String TYPE_DRAWABLE = "drawable";
    private static final String TYPE_COLOR = "color";
    private Resources mResources;
    private String mPluginPackageName;
    private String mSuffix;

    public ResourceManager(Resources res, String pluginPackageName, String suffix) {
        mResources = res;
        mPluginPackageName = pluginPackageName;

        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    public Drawable getDrawableByName(String name) {
        try {
            name = appendSuffix(name);
            L.e("name = " + name + " , " + mPluginPackageName);
            int id = mResources.getIdentifier(name, TYPE_DRAWABLE, mPluginPackageName);
            return ResourcesCompat.getDrawable(mResources, id, null);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getColor(String name) throws Resources.NotFoundException {
        name = appendSuffix(name);
        L.e("name = " + name);
        int id = mResources.getIdentifier(name, TYPE_COLOR, mPluginPackageName);
        return ResourcesCompat.getColor(mResources, id, null);
    }

    public ColorStateList getColorStateList(String name) {
        try {
            name = appendSuffix(name);
            L.e("name = " + name);
            int id = mResources.getIdentifier(name, TYPE_COLOR, mPluginPackageName);
            return ResourcesCompat.getColorStateList(mResources, id, null);

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix))
            return name += "_" + mSuffix;
        return name;
    }
}

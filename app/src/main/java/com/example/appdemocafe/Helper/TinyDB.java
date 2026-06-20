package com.example.appdemocafe.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class TinyDB {

    private SharedPreferences preferences;
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY = "saved_images";
    private String lastImagePath = "";

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public Bitmap getImage(String path) {
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapFromPath;
    }

    public String getSavedImagePath() {
        return lastImagePath;
    }

    public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null)
            return null;

        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFullPath(theImageName);

        if (!mFullPath.equals("")) {
            lastImagePath = mFullPath;
            saveBitmap(mFullPath, theBitmap);
        }

        return mFullPath;
    }

    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap) {
        return fullPath != null && theBitmap != null && saveBitmap(fullPath, theBitmap);
    }

    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGEDATA_DIRECTORY);

        if (mFolder.exists() || mFolder.mkdirs()) {
            File mFile = new File(mFolder, imageName);
            return mFile.getPath();
        } else {
            return "";
        }
    }

    private boolean saveBitmap(String fullPath, Bitmap bitmap) {
        if (fullPath == null || bitmap == null)
            return false;

        boolean fileCreated = false;
        boolean bitmapCompressed = false;
        boolean streamClosed = false;

        File imageFile = new File(fullPath);
        if (imageFile.exists())
            if (!imageFile.delete())
                return false;

        try {
            fileCreated = imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
            bitmapCompressed = false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    streamClosed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    streamClosed = false;
                }
            }
        }

        return (fileCreated && bitmapCompressed && streamClosed);
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public ArrayList<Integer> getListInt(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayList) {
            if (!item.isEmpty()) {
                newList.add(Integer.parseInt(item));
            }
        }
        return newList;
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public double getDouble(String key) {
        String number = preferences.getString(key, "");
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public ArrayList<Double> getListDouble(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Double> newList = new ArrayList<Double>();

        for (String item : arrayList) {
            if (!item.isEmpty()) {
                newList.add(Double.parseDouble(item));
            }
        }
        return newList;
    }

    public ArrayList<Long> getListLong(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Long> newList = new ArrayList<Long>();

        for (String item : arrayList) {
            if (!item.isEmpty()) {
                newList.add(Long.parseLong(item));
            }
        }
        return newList;
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList<Boolean>();

        for (String item : myList) {
            if (item.equals("true")) {
                newList.add(true);
            } else {
                newList.add(false);
            }
        }
        return newList;
    }

    public <T> T getObject(String key, Class<T> classOfT) {
        String json = getString(key);
        Object value = new Gson().fromJson(json, classOfT);
        if (value == null)
            return null;
        return (T) value;
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void putListInt(String key, ArrayList<Integer> intList) {
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public void putListLong(String key, ArrayList<Long> longList) {
        Long[] myLongList = longList.toArray(new Long[longList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myLongList)).apply();
    }

    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public void putDouble(String key, double value) {
        putString(key, String.valueOf(value));
    }

    public void putListDouble(String key, ArrayList<Double> doubleList) {
        Double[] myDoubleList = doubleList.toArray(new Double[doubleList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myDoubleList)).apply();
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void putListString(String key, ArrayList<String> stringList) {
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public void putListBoolean(String key, ArrayList<Boolean> boolList) {
        Boolean[] myBoolList = boolList.toArray(new Boolean[boolList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myBoolList)).apply();
    }

    public void putObject(String key, Object obj) {
        Gson gson = new Gson();
        putString(key, gson.toJson(obj));
    }

    public void putListObject(String key, ArrayList<?> list) {
        Gson gson = new Gson();
        putString(key, gson.toJson(list));
    }

    public <T> ArrayList<T> getListObject(String key, Class<T> mClass) {
        Gson gson = new Gson();
        String json = getString(key);
        if (json == null || json.isEmpty() || json.equals("null")) {
            return new ArrayList<T>();
        }

        try {
            JsonArray array = gson.fromJson(json, JsonArray.class);
            ArrayList<T> list = new ArrayList<T>();
            for (JsonElement element : array) {
                list.add(gson.fromJson(element, mClass));
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public boolean deleteImage(String path) {
        return new File(path).delete();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}

package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import hcmute.edu.vn.mssv18110251.Model.Account;

public class SharePreferenceClass {

    private static SharePreferenceClass instance;

    private static SharedPreferences sharedPreferences;

    private static final Object lock = new Object();

    private Context context;

    public static SharePreferenceClass getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new SharePreferenceClass(context);
            }
            return instance;
        }
    }
    private SharePreferenceClass(Context context){
        this.context = context;
    }

    public void set(String name, Object object){
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(object);
        editor.putString(name, json);
        editor.commit();
    }

    public Object get(String name){
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(name, "");
        return new Gson().fromJson(json, Account.class);
    }
}

package Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LogInHandler {
    private static LogInHandler logIn_instance;
    private static final String loginCreden= "credentials";
    private String name,number;
    private boolean check;

    SharedPreferences sharedPreferences;


    public static synchronized LogInHandler getInstance(Context context) {
        if (logIn_instance == null) {
            logIn_instance = new LogInHandler(context.getApplicationContext());
        }
        return logIn_instance;
    }
    public LogInHandler(Context context){
        sharedPreferences = context.getSharedPreferences(loginCreden,MODE_PRIVATE);
    }
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(String name,String number,boolean check) {

        sharedPreferences.edit().putString("name",name).apply();
        sharedPreferences.edit().putString("number",number).apply();
        sharedPreferences.edit().putBoolean("check",check).apply();

    }
    public void setLayoutSpan(int option){
        sharedPreferences.edit().putInt("option",option).apply();
    }
     public int getLayoutSpan()
     {
         return sharedPreferences.getInt("option",1);

     }
    public void logOut()
    {
        sharedPreferences.edit().clear().apply();

    }

    public boolean checkCredentials()
    {
        return sharedPreferences.getBoolean("check",false);

    }
}
package kapadokia.nyandoro.books.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    private SpUtil(){}

    //preference name
    public static final String PREF_NAME= "booksPreference";
    public static final String POSITION= "position";
    public static final String QUERY= "query";

    // creating a shared preference instance
    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // When you want to get preference String you use this method
    public  static  String getPreferenceString(Context context, String key){

        //if it is empty it will return a default empty string, given the key
        return getPrefs(context).getString(key, "");
    }

    public static int getPreferenceInt(Context context, String key){
        return getPrefs(context).getInt(key, 0);
    }

    // we need two methods, one to write the string and one to write the int

    public static void setPrefferenceString(Context context, String key, String value){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setPrefferenceInt(Context context, String key, int value){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }
}

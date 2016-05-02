package example.git.com.gitintegration;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Rajasudhakar on 01/05/16.
 */
public class sharedprefen {
    public static String USERNAME="user_name";

    public static String PREF_NAME="Myprefence";
    public static String USER_DE="user_details";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Activity activity;


    public sharedprefen(Activity ac) {
        preferences = ac.getSharedPreferences(PREF_NAME, 0);
         editor = preferences.edit();

    }




    public void save(String key, String s) {
        editor.putString(key,s);
        editor.commit();

    }

    public String getvalue(String key){
        String value=null;
        value=preferences.getString(key,null);

        return value;

    }
    public void clearpref(){
   preferences.edit().clear().commit();
    }
}

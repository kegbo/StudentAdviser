package uk.ac.anglia.student.hilda.studentadviser.util;

/**
 * Created by Hilda on 11/04/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import	java.io.*;
import android.util.Log;
import android.view.*;

import android.app.Activity;
import android.content.res.*;

import uk.ac.anglia.student.hilda.studentadviser.model.*;

public class Parser extends Activity {
    JSONObject json;
    String jsonString = loadJSONFromAsset();
    View vw;

    public Parser(View vw){
        this.vw = vw;
    }

    public User findByEmail(String email,String password,int id){

        //System.out.println(email);
        //System.out.println(password);
        //System.out.println(id);
        User user = readUserFromAsset(email,password, id);
        return user;
    }

    public Answer findbyKey(String key){
        System.out.print(key);
        Answer answer = readAnswerFromAsset(key);
        return answer;
    }
    //Warch  pass json tutorial
    private String loadJSONFromAsset() {
        String json = null;
        try {
            //AssetManager assetManager = getAssets();
            InputStream is = getAssets().open("sa.json");
            //InputStream is = getResources().openRawResource("sa.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            Log.i("Json",json);


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    private User readUserFromAsset(String email,String password,int id) {
        String jsonStr = jsonString;
        User user = null;

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            //JSONObject sys  = jsonObj.getJSONObject("sys");
            // Getting JSON Array node
            JSONArray users = jsonObj.getJSONArray("people");


            // looping through All Contacts
            for (int i = 0; i < users.length(); i++) {
                JSONObject c = users.getJSONObject(i);

                //System.out.println(c.getString("FirstName"));

                if(id == c.getInt("id")){

                    user = new User();
                    user.setId(c.getInt("id"));
                    user.setEmail(c.getString("Email"));
                    user.setFirstName(c.getString("Firstname"));
                    user.setLastName(c.getString("Lastname"));
                    user.setPassword(c.getString("password"));

                    break;
                }


            }
        } catch (final JSONException e) {
            System.out.println("Json parsing error: " + e.getMessage());

        }
        return  user;
    }

    private Answer readAnswerFromAsset(String key) {
        String jsonStr = jsonString;
        Answer answers = null;
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            //JSONObject sys  = jsonObj.getJSONObject("sys");
            // Getting JSON Array node
            JSONArray answer = jsonObj.getJSONArray("answer");


            // looping through All Contacts
            for (int i = 0; i < answer.length(); i++) {
                JSONObject c = answer.getJSONObject(i);

                if(key == c.getString("key")){

                    answers = new Answer();
                    answers.setKey(c.getString("key"));
                    answers.setR_how(c.getString("r_how"));
                    answers.setR_how_much(c.getString("r_how_much"));
                    answers.setR_links(c.getString("r_links"));
                    answers.setR_what(c.getString("r_what"));
                    answers.setR_when(c.getString("r_when"));
                    answers.setR_where(c.getString("r_where"));
                    answers.setR_who(c.getString("r_who"));
                    break;
                }


            }
        } catch (final JSONException e) {
            System.out.println("Json parsing error: " + e.getMessage());

        }
        return answers;
    }
}

package uk.ac.anglia.student.hilda.studentadviser.app;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import uk.ac.anglia.student.hilda.studentadviser.*;
import uk.ac.anglia.student.hilda.studentadviser.model.Answer;
import uk.ac.anglia.student.hilda.studentadviser.model.User;
import uk.ac.anglia.student.hilda.studentadviser.controller.*;


public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView textView;
    JSONObject student_adviser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Testing applicatioon section by section.
        //Parser class is currently giving us errors when try to reaad from json file
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadJSONFromAsset();
                //UserController userController = new UserController(view);

               String username = "kmi10";
                String password = "hi";
                int id = 10;
                String key = "benefits";


                //When login button is clicked connect to db and validate user details
                //User user = findByEmail(username,password,id);
                Answer answer = findbyKey(key);

               // System.out.println(user.toString());
                //Log.w("Users",user.toString());

            }
        });

    }




    private User findByEmail(String email,String password,int id){

        //System.out.println(email);
        //System.out.println(password);
        //System.out.println(id);
        User user = readUserFromAsset(email,password, id);
        return user;
    }

    private Answer findbyKey(String key){
        System.out.print(key);
        Answer answer = readAnswerFromAsset(key);
        return answer;
    }
    //Warch  pass json tutorial
    private void loadJSONFromAsset() {

        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.sa);

        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        parseJson(builder.toString());
    }

    private void parseJson(String s){

        try{
            JSONObject jsonObj = new JSONObject(s);
            student_adviser = jsonObj.getJSONObject("StudentAdviser");

        }catch(Exception ex){
            System.out.println(ex);
        }



    }


    private User readUserFromAsset(String email,String password,int id) {

        User user = null;

        try {

            JSONArray users = student_adviser.getJSONArray("people");


            // looping through All Contacts
            for (int i = 0; i < users.length(); i++) {
                JSONObject c = users.getJSONObject(i);

                System.out.println(c.getString("Firstname"));

                if(id == c.getInt("ID")){

                    user = new User();
                    user.setId(c.getInt("ID"));
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
        textView = (TextView) findViewById(R.id.txtview);
        Answer answers = null;
        try {

            JSONArray answer = student_adviser.getJSONArray("answer");



            // looping through All Contacts
            for (int i = 0; i < answer.length(); i++) {
                JSONObject c = answer.getJSONObject(i);
                //System.out.println(c.getString("key"));
                //textView.setText(c.getString("r_how_much"));

                if(key.equals(c.getString("key"))){
                    System.out.println(key);
                    textView.setText(c.getString("r_how_much"));
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

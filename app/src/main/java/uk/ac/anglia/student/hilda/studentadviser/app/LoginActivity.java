package uk.ac.anglia.student.hilda.studentadviser.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    //private Button signUpButton;
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    private String password;
    private Intent intent;
    private Intent serviceIntent;
    static JSONObject student_adviser;
    User currentuser;
    //public static UserController userController = new UserController();
    //Parser parse = new Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        //serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        //Check if user os already logged in
        //ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentuser != null) {
            startActivity(intent);
            //startService(serviceIntent);
        }

        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        //signUpButton = (Button) findViewById(R.id.signupButton);
        usernameField = (EditText) findViewById(R.id.loginUsername);
        passwordField = (EditText) findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int num;

                try{
                    username = usernameField.getText().toString();
                    password = passwordField.getText().toString();
                    String id = username.substring(username.length() - 2);
                    num = Integer.parseInt(id);
                    System.out.println(num);
                    loadJSONFromAsset();
                    User user = findByEmail(username,password,num);
                    if(user == null || username.equals("") || password.equals("") ){
                        Toast.makeText(getApplicationContext(),
                                "Wrong username/password combo",
                                Toast.LENGTH_LONG).show();
                    }else{
                        currentuser = user;
                        startActivity(intent);
                        //startService(serviceIntent);
                    }
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),
                            "Invalid username",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        //stopService(new Intent(this, MessageService.class));
        super.onDestroy();
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
        int ID = id;
        if(id > 30){
            ID = 10;
        }
        try {

            JSONArray users = student_adviser.getJSONArray("people");


            // looping through All Contacts
            for (int i = 0; i < users.length(); i++) {
                JSONObject c = users.getJSONObject(i);

                System.out.println(c.getString("Firstname"));

                if(ID == c.getInt("ID")){
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

    public static  Answer readAnswerFromAsset(String key) {

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

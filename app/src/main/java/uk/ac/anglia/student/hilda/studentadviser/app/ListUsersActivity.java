package uk.ac.anglia.student.hilda.studentadviser.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import uk.ac.anglia.student.hilda.studentadviser.model.Advisers;
import uk.ac.anglia.student.hilda.studentadviser.model.Answer;
import uk.ac.anglia.student.hilda.studentadviser.model.User;
import uk.ac.anglia.student.hilda.studentadviser.controller.*;


public class ListUsersActivity extends Activity {


    private ArrayAdapter<Advisers> namesArrayAdapter;
    private ListView usersListView;
    private Button logoutButton;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        showSpinner();
        //Log out user
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //display clickable a list of all users
    private void setConversationsList() {

        usersListView = (ListView)findViewById(R.id.usersListView);
        namesArrayAdapter = new ArrayAdapter<Advisers>(this, R.layout.user_list_item,Advisers.values());
        usersListView.setAdapter(namesArrayAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                openConversation(i);
            }
        });


    }

    //open a conversation with one person

    public  void openConversation(int pos) {


        Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
        startActivity(intent);
    }



    //show a loading spinner while the sinch client starts
    private void showSpinner() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        try{
            Thread.sleep(200);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        progressDialog.dismiss();

    }



    @Override
    public void onResume() {
        setConversationsList();
        super.onResume();
    }
}



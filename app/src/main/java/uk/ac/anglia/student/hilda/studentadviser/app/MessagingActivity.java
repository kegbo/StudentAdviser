package uk.ac.anglia.student.hilda.studentadviser.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import uk.ac.anglia.student.hilda.studentadviser.model.Answer;
import uk.ac.anglia.student.hilda.studentadviser.util.Extractor;

public class MessagingActivity extends Activity {

    private String recipientId;
    private EditText messageBodyField;
    private String messageBody;
    private MessageAdapter messageAdapter;
    private ListView messagesList;
    private String currentUserId;
    private static ArrayList<String> incomingAnswer = new ArrayList<String>();
    private static ArrayList<String> outgoingQuestion = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        Intent intent = getIntent();

        messagesList = (ListView) findViewById(R.id.listMessages);
        messageAdapter = new MessageAdapter(this);
        messagesList.setAdapter(messageAdapter);
        populateMessageHistory();

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    //get previous messages from parse & display
    private void populateMessageHistory() {

        ArrayList<String> temp = incomingAnswer;
        if(incomingAnswer.size() < outgoingQuestion.size()){
            temp = outgoingQuestion;
        }

        for (int i = 0; i < temp.size(); i++) {

            try{
                messageAdapter.addMessage(outgoingQuestion.get(i), MessageAdapter.DIRECTION_OUTGOING);
                messageAdapter.addMessage(incomingAnswer.get(i), MessageAdapter.DIRECTION_INCOMING);

            }catch(Exception ex){
                System.out.println(ex);
            }

        }
    }
    @TargetApi(9)
    private void sendMessage() {
        messageBody = messageBodyField.getText().toString();
        if (messageBody.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_LONG).show();
            return;
        }
        produceAnswer(messageBody);
        messageBodyField.setText("");
    }

    public void produceAnswer(String question){
        outgoingQuestion.add(question);
        messageAdapter.addMessage(question,MessageAdapter.DIRECTION_OUTGOING);
        String response = "";
        Extractor extractor = new Extractor(question);
        if(extractor.isContext && extractor.isKeyWord){
            String key = extractor.getKeyword();
            String context = extractor.getContext();
            Answer answer = LoginActivity.readAnswerFromAsset(key);
            response = answer.getByContext(context) + "\n" + "More info: "+ answer.getR_links();
        }
        else{
            response = "Sorry we currently cannot answer your question now "+ "\n" + "Contact us for a meeting on http://web.anglia.ac.uk/anet/student_services/student_advisers/contact.phtml ";
        }
        incomingAnswer.add(response);
        messageAdapter.addMessage(response, MessageAdapter.DIRECTION_INCOMING);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

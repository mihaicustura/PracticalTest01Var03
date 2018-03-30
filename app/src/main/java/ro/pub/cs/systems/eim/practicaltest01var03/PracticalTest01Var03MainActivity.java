package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    CheckBox nameBox, groupBox;
    EditText nameText, groupText;
    TextView textView;
    ButtonClickListener buttonClickListener;
    Button displayButton, navigateButton;
    IntentFilter intentFilter;

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button button = (Button)view;
            int id = button.getId();

            switch (id) {
                case R.id.Display:
                    if (nameBox.isChecked()) {
                        if (nameText.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "No name was entered", Toast.LENGTH_LONG).show();
                        } else {
                            textView.setText(nameText.getText());
                        }
                    } else {
                        textView.setText("");
                    }
                    if (groupBox.isChecked()) {
                        if (groupText.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "No group was entered", Toast.LENGTH_LONG).show();
                        }
                        else {
                            textView.setText(textView.getText() + " " + groupText.getText());
                        }
                    }
                    if (nameText.getText().toString().length() != 0 && groupText.getText().toString().length() != 0) {
                        Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                        serviceIntent.putExtra("Name text", nameText.getText().toString());
                        serviceIntent.putExtra("Group text", groupText.getText().toString());
                        getApplicationContext().startService(serviceIntent);
                        //serviceStatus = Constants.SERVICE_STARTED;
                    }
                    break;

                case R.id.Navigate:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                    intent.putExtra("Name text", nameText.getText().toString());
                    intent.putExtra("Group text", groupText.getText().toString());
                    startActivityForResult(intent, 1);
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action string")) {
                Log.d("Broadcast receiver", intent.getStringExtra("string"));
            } else if (intent.getAction().equals("action integer")) {
                Log.d("Broadcast receiver", intent.getStringExtra("integer"));
            } else if (intent.getAction().equals("action boolean")) {
                Log.d("Broadcast receiver", intent.getStringExtra("boolean"));
            } else if (intent.getAction().equals("action default")) {
                Log.d("Broadcast receiver", intent.getStringExtra("default"));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        navigateButton = findViewById(R.id.Navigate);
        nameBox = findViewById(R.id.checkBoxName);
        nameText = findViewById(R.id.Name);
        groupBox = findViewById(R.id.checkBoxGroup);
        groupText = findViewById(R.id.Group);
        displayButton = findViewById(R.id.Display);
        textView = findViewById(R.id.textView);
        intentFilter = new IntentFilter();

        buttonClickListener = new ButtonClickListener();
        displayButton.setOnClickListener(buttonClickListener);
        navigateButton.setOnClickListener(buttonClickListener);

        intentFilter.addAction("action string");
        intentFilter.addAction("action integer");
        intentFilter.addAction("action boolean");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Name edit text", nameText.getText().toString());
        savedInstanceState.putString("Group edit text", groupText.getText().toString());
        savedInstanceState.putString("Text view", textView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("Name edit text") != null) {
            nameText.setText(savedInstanceState.getString("Name edit text"));
        }
        if (savedInstanceState.getString("Group edit text") != null) {
            groupText.setText(savedInstanceState.getString("Group edit text"));
        }
        if (savedInstanceState.getString("Text view") != null) {
            textView.setText(savedInstanceState.getString("Text view"));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 1:
                String result = intent.getStringExtra("Response");
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                // process information from data ...
                break;

            // process other request codes
        }
    }
}

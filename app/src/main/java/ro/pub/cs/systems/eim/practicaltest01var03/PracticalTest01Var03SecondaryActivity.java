package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    TextView nameText, groupText;
    Button ok, cancel;
    ButtonClickListener buttonClickListener;

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button button = (Button)view;
            int id = button.getId();
            Intent intent;

            switch (id) {
                case R.id.Ok:
                    intent = new Intent();
                    intent.putExtra("Response", "You received an ok response");
                    setResult(1, intent);
                    finish();
                    break;
                case R.id.Cancel:
                    intent = new Intent();
                    intent.putExtra("Response", "You received an error");
                    setResult(-1, intent);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        nameText = findViewById(R.id.textViewName);
        groupText = findViewById(R.id.textViewGroup);
        ok = findViewById(R.id.Ok);
        cancel = findViewById(R.id.Cancel);
        buttonClickListener = new ButtonClickListener();
        Intent intent = getIntent();
        if (intent != null) {
            nameText.setText(intent.getStringExtra("Name text"));
            groupText.setText(intent.getStringExtra("Group text"));
        }

        ok.setOnClickListener(buttonClickListener);
        cancel.setOnClickListener(buttonClickListener);
    }
}

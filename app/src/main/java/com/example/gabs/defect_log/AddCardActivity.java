package com.example.gabs.defect_log;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qStr = ((EditText) findViewById(R.id.questionEdit)).getText().toString();
                String aStr = ((EditText) findViewById(R.id.answerEdit)).getText().toString();
                Intent data = new Intent();
                data.putExtra("defect", qStr);
                data.putExtra("defectFix", aStr);
                setResult(RESULT_OK,data);
                finish();
            }
        });

    }
}

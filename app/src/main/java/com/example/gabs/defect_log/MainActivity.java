package com.example.gabs.defect_log;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.defect_fix).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
               findViewById(R.id.defect_fix).setVisibility(View.INVISIBLE);
               findViewById(R.id.defect).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.defect).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                findViewById(R.id.defect_fix).setVisibility(View.VISIBLE);
                findViewById(R.id.defect).setVisibility(View.INVISIBLE);
            }
        });


//        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View v) {
//                ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_marketing_33);
//
//
//            }
//        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 100 && data != null){
            String defect = data.getExtras().getString("defect");
            String defectFix = data.getExtras().getString("defectFix");

            ((TextView)findViewById(R.id.defect)).setText(defect);
            ((TextView)findViewById(R.id.defect_fix)).setText(defectFix);
        }
    }
}

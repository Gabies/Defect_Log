package com.example.gabs.defect_log;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Instantiate the DB variable in onCreate becasue it guarantees the creation of the application to avoid getApplicationContext() pointing to null*/
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if(allFlashcards != null && allFlashcards.size() >0){
            int lastQidx = allFlashcards.size()-1;
            ((TextView) findViewById(R.id.defect)).setText(allFlashcards.get(lastQidx).getQuestion());
            ((TextView) findViewById(R.id.defect_fix)).setText(allFlashcards.get(lastQidx).getAnswer());
        }


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

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            /*Grab text from the both text field.*/
                String defect = ((TextView) findViewById(R.id.defect)).getText().toString();
                String defectFix = ((TextView)findViewById(R.id.defect_fix)).getText().toString();

                Intent edit = new Intent();
                edit.putExtra("editDefect", defect);
                edit.putExtra("editDefectFix", defectFix);

            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.defect)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.defect_fix)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 100 && data != null){
            String defect = data.getExtras().getString("defect");       //grab text from EditText
            String defectFix = data.getExtras().getString("defectFix");

            ((TextView)findViewById(R.id.defect)).setText(defect);
            ((TextView)findViewById(R.id.defect_fix)).setText(defectFix);

            flashcardDatabase.insertCard(new Flashcard(defect,defect));
            allFlashcards = flashcardDatabase.getAllCards();

            /*once the new text is displayed, the following will show the successful addition of the card*/
            Snackbar.make(findViewById(R.id.defect),
                    "Defect and Defect fix are successfully added to the Defect log recorder.",
                    Snackbar.LENGTH_SHORT).show();
        }




    }
}

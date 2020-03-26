package com.example.gabs.defect_log;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        /*Instantiate the DB variable in onCreate because it guarantees the creation of C:\Users\Gabs\AppData\Local\Android\Sdkthe application
         to avoid getApplicationContext() pointing to null*/
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if(allFlashcards != null && allFlashcards.size() >0){
            ((TextView) findViewById(R.id.defect)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.defect_fix)).setText(allFlashcards.get(0).getAnswer());
        }


        findViewById(R.id.defect_fix).setOnClickListener(new View.OnClickListener()
        {
            View answerSideView = findViewById(R.id.defect_fix);
            View questionSideView = findViewById(R.id.defect);
            public void onClick(View v) {
                questionSideView.setVisibility(View.VISIBLE);
                answerSideView.setVisibility(View.INVISIBLE);

            }
        });

        findViewById(R.id.defect).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                View answerSideView = findViewById(R.id.defect_fix);
                View questionSideView = findViewById(R.id.defect);
                /*Add circular animation based on the length and width of the card*/
                int cx = answerSideView.getWidth()/2;
                int cy = answerSideView.getHeight()/2;
                float radius = (float) Math.hypot(cx, cy);
                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, radius);
                // hide the question and show the answer to prepare for playing the animation!
                answerSideView.setVisibility(View.VISIBLE);
                questionSideView.setVisibility(View.INVISIBLE);
                anim.setDuration(2000);
                anim.start();
            }
        });


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
                /*adds transitioning animation when a new card is added*/
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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

                final Animation leftOutAnim =  AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.defect)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.defect_fix)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                flashcardDatabase.deleteCard(((TextView)findViewById(R.id.defect)).getText().toString());
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

            flashcardDatabase.insertCard(new Flashcard(defectFix,defect));
            allFlashcards = flashcardDatabase.getAllCards();

            /*once the new text is displayed, the following will show the successful addition of the card*/
            Snackbar.make(findViewById(R.id.defect),
                    "Defect and Defect fix are successfully added to the Defect log recorder.",
                    Snackbar.LENGTH_SHORT).show();
        }




    }
}

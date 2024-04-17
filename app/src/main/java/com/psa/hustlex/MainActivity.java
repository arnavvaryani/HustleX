//Designed and Implemented by:
// Chiragkumar Maisuria - 002813605

package com.psa.hustlex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.psa.hustlex.aakanksha_jobtracker.screens.JobTracker;
import com.psa.hustlex.arnav.screens.ReminderScreen;
import com.psa.hustlex.chirag.DailyTaskTracker;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

//    Button to go to Daily Task Tracker Screen.
    CardView gotoTaskTrackerBtn;
//    Button to go to Reminders Screen.
    CardView gotoRemindersBtn;
//    Button to go to Job Tracker Screen.
    CardView gotoJobTrackerBtn;

    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);

//        Set View as HomePage.
        setContentView(R.layout.homepage);



//        LinkSequence<Integer> ll = new LinkSequence<>();
//        ll.add(1);
//        ll.add(3);
//        ll.add(5);
//        ll.add(7);
//        Integer[] arr = ll.toArray();
//        Log.d("LinkedSequence: ", "" + ll.isEmpty());
//        for(int i=0; i<arr.length; i++) {
//            Log.d("LinkedSequence: ", "" + arr[i]);
//            ll.removeFirst();
//        }
//        Log.d("LinkedSequence: ", "" + ll.isEmpty());


//        Finding all id's of the buttons and initializing.
        gotoTaskTrackerBtn = findViewById(R.id.cvFeature1);
        gotoRemindersBtn = findViewById(R.id.cvFeature2);
        gotoJobTrackerBtn = findViewById(R.id.cvFeature3);

//        Initialize onClick on Daily Task Tracker Button.
        gotoTaskTrackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Create Intent to go to Daily Task Tracker Screen.
                Intent intent = new Intent(MainActivity.this, DailyTaskTracker.class);
                startActivity(intent);
            }
        });

        gotoRemindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Create Intent to go to Daily Task Tracker Screen.
                Intent intent = new Intent(MainActivity.this, ReminderScreen.class);
                startActivity(intent);
            }
        });

        gotoJobTrackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Create Intent to go to Daily Task Tracker Screen.
                Intent intent = new Intent(MainActivity.this, JobTracker.class);
                startActivity(intent);
            }
        });

    }

}

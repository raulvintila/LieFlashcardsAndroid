package com.raulvintila.app.lieflashcards.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.DrawingView;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.R;


public class StatisticsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private IDatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        databaseManager = ((MyApplication)getApplicationContext()).databaseManager;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Statistics");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout main_linear_layout = (LinearLayout) findViewById(R.id.main_linear_layout);

        main_linear_layout.addView(new DrawingView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

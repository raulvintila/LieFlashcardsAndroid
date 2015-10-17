package com.raulvintila.app.lieflashcards.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.raulvintila.app.lieflashcards.Adapters.CardRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.Adapters.DeckSpinnerAdapter;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Fragments.CardRecyclerFragment;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.RecyclerItems.CardRecyclerViewItem;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.SyncClasses.Card;

import java.util.ArrayList;
import java.util.List;

public class CardCollectionActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomModel.getInstance().setContext(this);
        setContentView(R.layout.activity_card_collection);

    }

    public void suportActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Collection");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed(){
        CustomModel.getInstance().setDeck_id(null);
        super.onBackPressed();
    }




/*    @Override
    public void onStop() {
    if (databaseManager != null) {
        databaseManager.closeDbConnections();
        // CustomModel.getInstance().setDatabaseManager(null);
    }
    super.onStop();
    }*/
}

package com.raulvintila.app.lieflashcards.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.R;

import java.util.List;

public class DeckSettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DBDeck deck;
    private IDatabaseManager databaseManager;

    public void onClick(View v){
        final DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
        switch (v.getId()) {
            case R.id.rename:
                new MaterialDialog.Builder(this)
                        .title("Rename Deck")
                        .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                        .input(deck.getName(), "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                deck.setName(input.toString());
                                /*deckRecyclerViewItem.setName(input.toString());
                                getSupportActionBar().setTitle(input.toString())*/;
                                /*DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
                                deck.setName(input.toString());*/
                                databaseManager.insertOrUpdateDeck(deck);
                                //CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                            }
                        })
                                //.inputMaxLength(15)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.choose)
                        .show();
                return;
            case R.id.stack:
                new MaterialDialog.Builder(this)
                        .title("Change stack")
                                //.content("Current stack: " + deck)
                        .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                            }
                        })
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.choose)
                        .show();
                return;
            case R.id.cards:
                new MaterialDialog.Builder(this)
                        .title("Cards per day")
                                //.content("Current number of cards: " + 20)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("Curent number of cards "+deck.getNumber_of_cards_per_day(),"", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                deck.setNumber_of_cards_per_day(Integer.parseInt(input.toString()));
                                databaseManager.insertOrUpdateDeck(deck);
/*                                deckRecyclerViewItem.setCards(Integer.parseInt(input.toString())+" / 25 / 122");
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();*/
                            }
                        })
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.choose)
                        .show();
                return;
            case R.id.hints:
                new MaterialDialog.Builder(this)
                        .title("Hints")
                        .items(R.array.hints)
                        .itemsCallbackMultiChoice(new Integer[1], new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                /**
                                 * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected check box to actually be selected.
                                 * See the limited multi choice dialog example in the sample project for details.
                                 **/
/*                                hints_used = which;
                                deckRecyclerViewItem.setHints_used(which);
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();*/
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
                return;
            case R.id.archive:
                new MaterialDialog.Builder(this)
                        .title("Archive")
                        .content("Archive deck?")
                        .positiveText(R.string.yes)
                        .negativeText("No")
                        .show();
                return;
            case R.id.remove:
                new MaterialDialog.Builder(this)
                        .title("Remove Deck")
                        .content("Are you sure you want to remove '" + deck.getName() + "'?")
                        .positiveText(R.string.yes)
                        .negativeText("No")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                List<DBCard> cardList = deck.getCards();
                                for (int i = 0; i < cardList.size(); i++) {
                                    databaseManager.deleteCardById(cardList.get(i).getId());
                                }
                                databaseManager.deleteDeckById(deck.getId());
/*                                data.remove(deckRecyclerViewItem);
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged()*/
                                ;
                                //finish();
                                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
                return;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_settings);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        databaseManager = new DatabaseManager(this);
        deck = databaseManager.getDeckById(extras.getLong("deck_id"));
        System.out.println("sal");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_settings, menu);
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

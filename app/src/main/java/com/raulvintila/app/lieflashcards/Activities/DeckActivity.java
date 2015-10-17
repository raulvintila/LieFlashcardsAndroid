package com.raulvintila.app.lieflashcards.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Fragments.DeckSettingsFragment;
import com.raulvintila.app.lieflashcards.Fragments.DeckStatisticsFragment;
import com.raulvintila.app.lieflashcards.Fragments.DeckOverviewFragment;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.Utils.Algorithms.SpacedLearningAlgoUtils;

import java.util.List;

public class DeckActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    SectionPagerAdapter mAdapter;

    IDatabaseManager databaseManager;
    ViewPager mPager;
    List<DeckRecyclerViewItem> data;
    DeckRecyclerViewItem deckRecyclerViewItem;
    String deck_name;
    Integer[] hints_used;
    String stack = "Current stack";

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }

    public void onClick(View v){
        final DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
        switch (v.getId()) {
            case R.id.play_button:
                if (new SpacedLearningAlgoUtils().getTodayList(databaseManager.getDeckById(deckRecyclerViewItem.getDeckId()).getCards(), (int)databaseManager.getDeckById(deckRecyclerViewItem.getDeckId()).getNumber_of_cards_per_day()).size() > 0) {
                    final Intent intent = new Intent(this, PlayDeckActivity.class);
                    intent.putExtra("is_preview", false);
                    startActivity(intent);
                } else
                    Toast.makeText(this,"No cards left to study today",Toast.LENGTH_SHORT).show();
                return;
            case  R.id.goals_button:
                Intent intent = new Intent(this, GoalsActivity.class);
                startActivity(intent);
                return;
            case R.id.card_button:
                final Intent intent2 = new Intent(this,CardCollectionActivity.class);
                databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
                intent2.putExtra("deck_id",databaseManager.getDeckById(CustomModel.getInstance().getDeckId()).getId());
                startActivity(intent2);
                return;
            case R.id.rename:
                new MaterialDialog.Builder(this)
                        .title("Rename Deck")
                        .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                        .input(deck_name, "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                deck_name = input.toString();
                                deckRecyclerViewItem.setName(input.toString());
                                getSupportActionBar().setTitle(input.toString());
                                DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
                                deck.setName(input.toString());
                                databaseManager.insertOrUpdateDeck(deck);
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();
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
                        .input(stack, "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                stack = input.toString();
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
                                deckRecyclerViewItem.setCards(Integer.parseInt(input.toString())+" / 25 / 122");
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();
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
                        .itemsCallbackMultiChoice(hints_used, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                /**
                                 * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected check box to actually be selected.
                                 * See the limited multi choice dialog example in the sample project for details.
                                 **/
                                hints_used = which;
                                deckRecyclerViewItem.setHints_used(which);
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();
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
                        .content("Are you sure you want to remove '"+deck_name+"'?")
                        .positiveText(R.string.yes)
                        .negativeText("No")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                List<DBCard> cardList = deck.getCards();
                                for(int i = 0; i < cardList.size(); i++) {
                                    databaseManager.deleteCardById(cardList.get(i).getId());
                                }
                                databaseManager.deleteDeckById(CustomModel.getInstance().getDeckId());
                                data.remove(deckRecyclerViewItem);
                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                                finish();
                            }
                        })
                        .show();
                return;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        databaseManager = ((MyApplication)getApplicationContext()).databaseManager;

        data = CustomModel.getInstance().getList();
        deckRecyclerViewItem = CustomModel.getInstance().getDeckRecyclerViewItem();
        deck_name = deckRecyclerViewItem.getName();
        hints_used = deckRecyclerViewItem.getHints_used();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new SectionPagerAdapter(getSupportFragmentManager(),DeckActivity.this);
        mPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mPager);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(deck_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // May produce exception
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        static final int NUM_ITEMS = 3;
        private String tabTitles[] = new String[] { "Overview", "Statistics", "Settings" };
        private Context context;


        public SectionPagerAdapter(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DeckOverviewFragment.newInstance(position + 1);
                case 1:
                    return DeckStatisticsFragment.newInstance(position + 1);
                case 2:
                    return DeckSettingsFragment.newInstance(position + 1);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Fragment #" + mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add) {
            final Intent intent = new Intent(this,AddCardActivity.class);
            // Add deck name for autoselect in AddCardActivity
            //intent.putStringArrayListExtra("deck_names", deck_names);
            intent.putExtra("deck", deck_name);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}



package com.raulvintila.app.lieflashcards.Activities;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.Utils.Algorithms.SpacedLearningAlgoUtils;

import java.util.Arrays;
import java.util.List;

public class DeckActivity extends AppCompatActivity {

    Toolbar toolbar;

    //private SectionPagerAdapter mAdapter;

    private IDatabaseManager databaseManager;
    //private ViewPager mPager;
    private List<DeckRecyclerViewItem> data;
    private DeckRecyclerViewItem deckRecyclerViewItem;
    private String deck_name;
    //DBDeck deck;
    private Integer[] hints_used;

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }

    private void showMeDialog(int which)
    {
        final DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
        switch (which)
        {
            case 0:
                new MaterialDialog.Builder(this)
                    .title("Rename")
                    .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                    .input(deck.getName(), "", false, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            deck.setName(input.toString());
                            deckRecyclerViewItem.setName(input.toString());
                            getSupportActionBar().setTitle(input.toString());
                            databaseManager.insertOrUpdateDeck(deck);
                            CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                        }
                    })
                            //.inputMaxLength(15)
                    .negativeText(R.string.cancel)
                    .positiveText(R.string.choose)
                    .show();
                return;
            case 1:
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
            case 2:
                new MaterialDialog.Builder(this)
                    .title("Cards per day")
                            //.content("Current number of cards: " + 20)
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input("Curent number of cards " + deck.getNumber_of_cards_per_day(), "", false, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            deck.setNumber_of_cards_per_day(Integer.parseInt(input.toString()));
                            databaseManager.insertOrUpdateDeck(deck);
                            deckRecyclerViewItem.setCards(Integer.parseInt(input.toString()) + " / 25 / 122");
                            CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                        }
                    })
                    .negativeText(R.string.cancel)
                    .positiveText(R.string.choose)
                    .show();
                return;
            case 3:
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
            case 4:
                new MaterialDialog.Builder(this)
                    .title("Archive")
                    .content("Archive deck?")
                    .positiveText(R.string.yes)
                    .negativeText("No")
                    .show();
                return;
            case 5:
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
                            data.remove(deckRecyclerViewItem);
                            CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                            //finish();
                            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();
                return;
        }

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.play:
                if (new SpacedLearningAlgoUtils().getTodayList(databaseManager.getDeckById(deckRecyclerViewItem.getDeckId()).getCards(), (int)databaseManager.getDeckById(deckRecyclerViewItem.getDeckId()).getNumber_of_cards_per_day()).size() > 0) {
                    final Intent intent = new Intent(this, PlayDeckActivity.class);
                    intent.putExtra("is_preview", false);
                    startActivity(intent);
                } else
                    Toast.makeText(this,"No cards left to study today",Toast.LENGTH_SHORT).show();
                return;
            case  R.id.goals:
                Intent intent = new Intent(this, GoalsActivity.class);
                startActivity(intent);
                return;
            case R.id.cards_icon:
                final Intent intent2 = new Intent(this,CardCollectionActivity.class);
                databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
                intent2.putExtra("deck_id",databaseManager.getDeckById(CustomModel.getInstance().getDeckId()).getId());
                startActivity(intent2);
                return;
            case R.id.settings:
/*                final Intent intent3 = new Intent(this,DeckSettingsActivity.class);
                databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
                intent3.putExtra("deck_id",databaseManager.getDeckById(CustomModel.getInstance().getDeckId()).getId());
                startActivity(intent3);*/
                List<String> settings = Arrays.asList("Rename","Stack","Cards per day","Hints","Archive","Remove");
                new MaterialDialog.Builder(this)
                    .title("Settings")
                    .items(R.array.deck_settings)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            showMeDialog(which);
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

        DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());


        TextView deck_total_new_cards = (TextView)findViewById(R.id.total_new_cards_stats);
        deck_total_new_cards.setText(""+deck.getTotal_new_cards());

        TextView deck_total_cards = (TextView)findViewById(R.id.total_cards_stats);
        deck_total_cards.setText(""+deck.getNumber_of_cards());

        TextView deck_due_today_stats = (TextView) findViewById(R.id.due_today_stats);
        String deck_cards_per_day = ""+deck.getNumber_of_cards_per_day();
        String text = "<font color=#1976D2>"+deck_cards_per_day+"</font> <font color=#008800> 2</font> <font color=#bb0000> 6</font>";
        deck_due_today_stats.setText(Html.fromHtml(text));

        /*mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new SectionPagerAdapter(getSupportFragmentManager(),DeckActivity.this);
        mPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mPager);*/

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

/*    public class SectionPagerAdapter extends FragmentPagerAdapter {

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
    }*/

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



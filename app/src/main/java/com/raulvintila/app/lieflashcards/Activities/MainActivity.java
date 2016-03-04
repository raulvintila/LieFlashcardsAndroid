package com.raulvintila.app.lieflashcards.Activities;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.raulvintila.app.lieflashcards.AsyncLogin;
import com.raulvintila.app.lieflashcards.AsyncSynchronize;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Communication.TaskHelper;
import com.raulvintila.app.lieflashcards.Communication.UserId;
import com.raulvintila.app.lieflashcards.Fragments.TaskFragment;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.Password;
import com.raulvintila.app.lieflashcards.PcCard;
import com.raulvintila.app.lieflashcards.PcDeck;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.SyncClasses.Card;
import com.raulvintila.app.lieflashcards.SyncClasses.CreateCardWrapper;
import com.raulvintila.app.lieflashcards.SyncClasses.Deck;
import com.raulvintila.app.lieflashcards.SyncClasses.RequestReturnedMessage;
import com.raulvintila.app.lieflashcards.SyncClasses.UserData;
import com.raulvintila.app.lieflashcards.SyncClasses.WrappedCardRRM;
import com.raulvintila.app.lieflashcards.SyncClasses.WrappedDeckRRM;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Utils.AsyncTaskUtils;
import com.raulvintila.app.lieflashcards.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String user_id = new UserId().user_id;

    public List<Deck> decks;

    private boolean accounts_expanded;

    private Toolbar toolbar;
    private NavigationView navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FloatingActionsMenu menuMultipleActions;
    private LinearLayout rootLayout;
    private FloatingActionButton card_button;
    private RecyclerView rv;
    List<DeckRecyclerViewItem> data = new ArrayList<>();
    private IDatabaseManager databaseManager;

    boolean PUSH_ACTION = false;
    boolean PULL_ACTION = false;

    @Override
    public void postponeEnterTransition() {
        super.postponeEnterTransition();
    }

    public void attemptSync() {
        PUSH_ACTION = true;
        if(new NetworkUtils().isNetworkAvailable(this)) {
            /*AsyncGetUserData getUserData = new AsyncGetUserData();
            getUserData.execute();
            Toast.makeText(this,"Synced",Toast.LENGTH_SHORT).show();*/
            //new AsyncTaskUtils().attemptSync(user_id, "PUSH_ACTION");

            // start the task
            AsyncSynchronize task = new AsyncSynchronize(user_id, "PUSH_ACITON");
            TaskHelper.getInstance().addTask("task", task, this);
            task.execute();
        } else {
            Toast.makeText(this,"No internet connection available",Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(List<DeckRecyclerViewItem> list){
        data = list;
    }

    public void expandNavigationView() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.navigation);
        Menu menu = nav_view.getMenu();
        //SubMenu topChannelMenu = menu.addSubMenu("Accounts");
        //topChannelMenu.add("Login");
        //topChannelMenu.add("Register");
        MenuItem item;
        if(accounts_expanded) {
            item = menu.findItem(R.id.login);
            item.setVisible(false);
            item = menu.findItem(R.id.register);
            item.setVisible(false);
            accounts_expanded = false;
        } else {
            item = menu.findItem(R.id.login);
            item.setVisible(true);
            item = menu.findItem(R.id.register);
            item.setVisible(true);
            accounts_expanded = true;
        }
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.create_card:
                menuMultipleActions.collapse();
                final Intent intent = new Intent(this,AddCardActivity.class);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 125);
                /*Snackbar.make(findViewById(R.id.drawerLayout), "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
                menuMultipleActions.animate().translationYBy(-80);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        menuMultipleActions.animate().translationYBy(80);
                    }
                }, 1750);*/
                /*final RecyclerListViewFragment fragment = (RecyclerListViewFragment) getSupportFragmentManager().findFragmentById(R.id.recycler_view);
                EventBus.getDefault().post(new yolo());*//*
                DBDeck deck = new DBDeck();
                deck.setName("Pascal");
                deck = databaseManager.insertDeck(deck);*/

                /*DBCard card = new DBCard();
                card.setAnswer("Te rog");
                card.setQuestion("Merci");
                card.setDeckId(deck.getId());

                databaseManager.insertCard(card);*/
                return ;
            /*case R.id.rl2:
                if (menuMultipleActions.isExpanded())
                    menuMultipleActions.collapse();
                return ;*/
            case R.id.create_deck:
                new MaterialDialog.Builder(this)
                        .title("New Deck")
                        .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                        .input(R.string.input_hint, R.string.input_prefill, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                                DBDeck deck = new DBDeck();
                                deck.setName(input.toString());
                                deck.setNumber_of_cards_per_day(20);
                                deck.setNumber_of_cards(0);
                                deck.setTotal_new_cards(new Long(0));
                                deck.setDate_created(new Date());

                                deck = databaseManager.insertDeck(deck);
                                data.add(0, new DeckRecyclerViewItem(0, input.toString(), "20 / 25 / 122", R.drawable.dog, "0m", new Integer[]{0}, 20, deck.getId()));

                                CustomModel.getInstance().getAdapter().notifyDataSetChanged();
                            }
                        })
                        //.inputMaxLength(15)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.choose)
                        .show();
                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuMultipleActions.collapse();
                    }
                }, 125);
                return ;
        }
    }



    // Animates the screen when the FloatingActionMenu is opened/closed
    public void fam_animator(Boolean x) {

        //CoordinatorLayout lol = (CoordinatorLayout) findViewById(R.id.rootLayout);
        if (x) {
            AlphaAnimation alpha = new AlphaAnimation(1F, 0.1F);
            alpha.setDuration(100);
            alpha.setFillAfter(true);
            rootLayout.startAnimation(alpha);
        }
        else {
            rootLayout.clearAnimation();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_lab);
        CustomModel.getInstance().setContext(this);
        initInstances();
        CustomModel.getInstance().setList(data);
        // re-attach the activity if the task is still available
        TaskHelper.getInstance().attach("task", this);

        loadRaw();
    }


    private void initInstances() {

        databaseManager = ((MyApplication)getApplicationContext()).databaseManager;

        View v = findViewById(R.id.account_expand);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandNavigationView();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_sync:
                        //attemptSync();

                        loadRaw();

                        /*Toast.makeText(getApplicationContext(),"mer",Toast.LENGTH_SHORT).show();
                        AsyncDeleteDeck del_task = new AsyncDeleteDeck();
                        del_task.execute("55c6189198de630300aad6f1");*/
                        return true;
                    default:
                        return false;
                }
           }
       });
        getSupportActionBar().setTitle("Lie Flashcards");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);
        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        RelativeLayout rela =(RelativeLayout)findViewById(R.id.rl);
        card_button = (FloatingActionButton)findViewById(R.id.create_deck);
        final RelativeLayout relativeLayout= (RelativeLayout)findViewById(R.id.rl2);



        menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fam_animator(true);
                //disable(rootLayout);
                relativeLayout.setVisibility(RelativeLayout.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                fam_animator(false);
                //relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
                relativeLayout.setVisibility(RelativeLayout.INVISIBLE);

            }
        });


        DrawerLayout drawerView = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawerView != null && drawerView instanceof DrawerLayout) {
            drawerView.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {
                    menuMultipleActions.collapse();
                }

                @Override
                public void onDrawerOpened(View view) {}

                @Override
                public void onDrawerClosed(View view) {
                    // your refresh code can be called from here
                }

                @Override
                public void onDrawerStateChanged(int i) {

                }
            });
        }



        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.register:
                        //showRegister();
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.login:
                        showLogin();
                        return true;
                    case R.id.navItem2_collections:
                        Intent intent1 = new Intent(getApplicationContext(), CardCollectionActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navItem3_statistics:
                        /*Intent intent2 = new Intent(getApplicationContext(), StatisticsActivity.class);
                        startActivity(intent2);*/
                        Toast.makeText(getApplicationContext(), "statistics menu", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navItem4_store:
                        Toast.makeText(getApplicationContext(), "Store, a place where users will find what they're looking for.", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navItem5_settings:
                        /*Intent intent3 = new Intent(getApplicationContext(), SettingsActivity.class);
                        //intent.putExtra("deck",mProvider.get(vh.getAdapterPosition()).name);
                        startActivity(intent3);*/
                        Toast.makeText(getApplicationContext(), "general app settings", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navItem6_about:
                        new MaterialDialog.Builder(MainActivity.this)
                                .title(R.string.about_title)
                                .content(R.string.about_content)
                                .positiveText(R.string.ok)
                                .show();
                        return true;
                }
                return false;
            }
        });


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean attemptRegister(String password, String confirm_password) {
        if (!password.equals(confirm_password)) {
            return false;
        }
        return true;
    }

    public void showRegister() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.register_dialog, null);
        alertDialog.setView(convertView);

        final EditText password = (EditText) convertView.findViewById(R.id.password);
        final EditText confirm_password = (EditText) convertView.findViewById(R.id.confirm_password);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = alertDialog.create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                // #ToChange replace this hardcoded Color
                positiveButton.setTextColor(Color.parseColor("#ff1ca337"));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attemptRegister(password.getText().toString(), confirm_password.getText().toString())) {
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                            password.setText("");
                            confirm_password.setText("");
                        }
                    }
                });


                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEGATIVE);
                // #ToChange replace this hardcoded Color
                negativeButton.setTextColor(Color.parseColor("#ff1ca337"));
            }
        });

        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    public void showLogin() {
        /*final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.login_dialog, null);
        alertDialog.setView(convertView);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();

        //alertDialog.show(); //.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                // #ToChange replace this hardcoded Color
                positiveButton.setTextColor(Color.parseColor("#ff1ca337"));

                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEGATIVE);
                // #ToChange replace this hardcoded Color
                negativeButton.setTextColor(Color.parseColor("#ff1ca337"));
            }
        });

        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);*/

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.login_dialog, null);
        alertDialog.setView(convertView);

        final EditText email = (EditText) convertView.findViewById(R.id.email);
        final EditText password = (EditText) convertView.findViewById(R.id.password);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = alertDialog.create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                // #ToChange replace this hardcoded Color
                positiveButton.setTextColor(Color.parseColor("#ff1ca337"));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsyncLogin login = new AsyncLogin(email.getText().toString(), password.getText().toString());
                        login.execute();
                    }
                });


                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEGATIVE);
                // #ToChange replace this hardcoded Color
                negativeButton.setTextColor(Color.parseColor("#ff1ca337"));
            }
        });

        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code_lab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()) {
            case R.id.action_sync:
                loadRaw();
                //attemptSync();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRaw()
    {
        Gson gson = new Gson();

        int[] intArray = new int[3];
        intArray[1] = R.raw.caini;
        intArray[0] = R.raw.medicina;
        intArray[2] = R.raw.semneauto;

        databaseManager.deleteDecks();
        data.clear();

        for(int i = 0; i < intArray.length; i++)
        {
            try
            {
                Resources res = getResources();
                InputStream in_s = res.openRawResource(intArray[i]);

                byte[] b = new byte[in_s.available()];
                in_s.read(b);
                String fisier = new String(b);

                PcDeck deck = new Gson().fromJson(fisier, PcDeck.class);

                addDeck(deck);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addDeck(PcDeck deck)
    {
        DBDeck dbDeck = new DBDeck();
        dbDeck.setName(deck.getName());
        dbDeck.setDate_created(new Date());
        dbDeck.setNumber_of_cards_per_day(20);
        dbDeck.setNumber_of_cards(0);
        dbDeck.setTotal_new_cards(new Long(0));
        dbDeck.setDate_created(new Date());

        dbDeck = databaseManager.insertDeck(dbDeck);
        data.add(0, new DeckRecyclerViewItem(0, dbDeck.getName(), "20 / 25 / 122", R.drawable.dog, "0m", new Integer[]{0}, 20, dbDeck.getId()));

        if(deck.getName().equals("Rase Caini") )
            addCardsToDeck(dbDeck, deck, "switched");
        else
            addCardsToDeck(dbDeck, deck, "normal");
    }

    private void addCardsToDeck(DBDeck dbDeck, PcDeck deck, String qaType)
    {

        for(PcCard card : deck.getCards())
        {
            DBCard dbCard = new DBCard();
            String card_type;
            int size = card.getBack().length();
            if(qaType.equals("normal"))
            {
                if (size > 2000000)
                {
                    continue;
                }
                dbCard.setQuestion(card.getFront());
                dbCard.setAnswer(card.getBack());
                card_type = card.getFrontType().toLowerCase() + "_" + card.getBackType().toLowerCase();
            }
            else
            {
                dbCard.setQuestion(card.getBack());
                dbCard.setAnswer(card.getFront());
                card_type = card.getBackType().toLowerCase()+ "_" + card.getFrontType().toLowerCase();
            }
            dbCard.setDeckId(dbDeck.getId());

            dbCard.setDifficulty(card_type);
            dbCard.setDate_creted(new Date());
            dbCard.setLast_study(new Date());
            dbCard.setCurrent_level(0.0);
            dbCard.setVolatility(1.0);
            dbCard.setTimes_studied(0);

            databaseManager.insertOrUpdateCard(dbCard);
        }

        CustomModel.getInstance().getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaskHelper.getInstance().detach("task");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }
}

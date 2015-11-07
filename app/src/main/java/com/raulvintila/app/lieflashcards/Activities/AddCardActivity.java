package com.raulvintila.app.lieflashcards.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.ImagePathBUS;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Utils.CardTypesPlaceholderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.raulvintila.app.lieflashcards.AudioEncodedBUS;

import de.greenrobot.event.EventBus;


class TagString {
    private ArrayList<String> ar;
    private ArrayList<String> ch;

    public TagString() {
        ar = new ArrayList<String>();
        ch = new ArrayList<String>();
    }

    public void wtf(Boolean val) {
        if (val)
            ch.add(0, "true");
        else
            ch.add(0, "false");
    }

    public int len() {
        return ar.size();
    }

    public void add(String str) {
        ar.add(0, str);
    }

    public void setCheckState(int position, String state) {
        ch.set(position, state);
    }

    public void add1(String str) {
        ar.add(0, str);
        ch.add(0, "false");
    }

    public String get(int i) {
        return ar.get(i);
    }

    public String getB(int i) {
        if (ch.size() > 0 && i < ch.size())
            return ch.get(i);
        return "";
    }
}

public class AddCardActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    private Toolbar toolbar;
    private TagString ar;
    private List<String> deck_names = new ArrayList<String>();
    private List<Long> deckIds = new ArrayList<Long>();
    private List<DeckRecyclerViewItem> data;
    private String[] sir;
    private String deck;
    private int index;
    private String encodedAudio;
    private ImageView mImagePreview;
    private IDatabaseManager databaseManager;
    private String difficulty;
    private String accept_state;
    private Long card_id;
    private String card_type = "text_text";
    private String front_path, back_path;
    private boolean back_from_multimedia;
    private boolean back_from_audio;
    private String multimedia_path;
    private boolean restarted;
    private int diff_index = 2;

    public void showDif(View v) {
        new MaterialDialog.Builder(this)
                .title("Choose difficulty")
                .items(R.array.dif)
                .itemsCallbackSingleChoice(diff_index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        difficulty = text.toString();
                        diff_index = which;
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    public void showPreview(View v) {
        final EditText back = (EditText) findViewById(R.id.back);
        final EditText front = (EditText) findViewById(R.id.front);
        final Intent intent = new Intent(this, PlayDeckActivity.class);
        intent.putExtra("is_preview", true);
        intent.putExtra("card_type", card_type);
        if ( card_type.equals("text_text"))
        {
            intent.putExtra("front", front.getText().toString());
            intent.putExtra("back",back.getText().toString());
        }
        else if ( card_type.equals("text_image") || card_type.equals("text_audio"))
        {
            intent.putExtra("front", front.getText().toString());
            intent.putExtra("back",back_path);
        }
        else if ( card_type.equals("image_text") || card_type.equals("audio_text"))
        {
            intent.putExtra("front", front_path);
            intent.putExtra("back",back.getText().toString());
        }
        else
        {
            intent.putExtra("front", front_path);
            intent.putExtra("back",back_path);
        }

        //intent.putExtra("front", front.getText().toString());
        startActivity(intent);
    }

    public void showTag(View v) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCardActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = inflater.inflate(R.layout.my_dialog, null);
        alertDialog.setView(convertView);
        final ListView lv = (ListView) convertView.findViewById(R.id.listView1);


        lv.setAdapter(new yourAdapter(this, ar));


        EditText input = (EditText) convertView.findViewById(R.id.ed);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ar.add(v.getText().toString());
                    v.setText(null);
                    v.post(new Runnable() { // ListView not drawned yet, so we have to wait
                        @Override
                        public void run() {
                            ar.wtf(true);
                            lv.setAdapter(new yourAdapter(AddCardActivity.this, ar)); // Redrawn the ListViev
                        }
                    });
                    return true;
                }
                return false;
            }
        });

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
        alertDialog.show().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showDeck(View v) {
        sir = new String[deck_names.size()];
        sir = deck_names.toArray(sir);
        new MaterialDialog.Builder(this)
                .title("Choose deck")
                .items(sir)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        TextView v = (TextView) findViewById(R.id.deck_name);
                        Paint textPaint = v.getPaint();
                        String deck_name = sir[which];
                        float width = textPaint.measureText(deck_name);
                        int widthint = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
                        float maxwidth = widthint/3;
                        int i = deck_name.length();
                        while (width > maxwidth )
                        {
                            deck_name = deck_name.substring(0, i)+"...";
                            width = textPaint.measureText(deck_name);
                            i -= 1;
                        }
                        v.setText(deck_name);
                        index = which;
                        return true;
                    }
                })
                .show();
    }

    public void showPopupBack(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_back, popup.getMenu());
        popup.show();
        if (popup.getDragToOpenListener() instanceof ListPopupWindow.ForwardingListener) {
            ListPopupWindow.ForwardingListener listener = (ListPopupWindow.ForwardingListener) popup.getDragToOpenListener();
            listener.getPopup().setVerticalOffset(-v.getHeight());
            listener.getPopup().show();
        }
    }

    public void showPopupFront(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_front, popup.getMenu());
        popup.show();
        if (popup.getDragToOpenListener() instanceof ListPopupWindow.ForwardingListener) {
            ListPopupWindow.ForwardingListener listener = (ListPopupWindow.ForwardingListener) popup.getDragToOpenListener();
            listener.getPopup().setVerticalOffset(-v.getHeight());
            listener.getPopup().show();
        }
    }


    private void createOrUpdateCard(DBCard card,DBDeck deck , String back , String front , String card_type, Date date_created){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);

        if (card_type.equals("text_text")) {
            card.setQuestion(front);
            card.setAnswer(back);
        } else if (card_type.equals("image_text")) {
            card.setQuestion(front_path);
            card.setAnswer(back);
        } else if (card_type.equals("text_image")) {
            card.setQuestion(front);
            card.setAnswer(back_path);
        } else if (card_type.equals("image_image")) {
            card.setQuestion(front_path);
            card.setAnswer(back_path);
        }
        else if (card_type.equals("text_audio")) {
            card.setQuestion(front);
            card.setAnswer(back_path);
        } else if (card_type.equals("audio_text")) {
            card.setQuestion(front_path);
            card.setAnswer(back);
        }
        else if (card_type.equals("image_audio")) {
            card.setQuestion(front_path);
            card.setAnswer(back_path);
        } else if (card_type.equals("audio_image")) {
            card.setQuestion(front_path);
            card.setAnswer(back_path);
        }
        else if (card_type.equals("audio_audio")) {
            card.setQuestion(front_path);
            card.setAnswer(back_path);
        }
        card.setDeckId(deck.getId());
        // difficulty is type "frontType_backType"
        card.setDifficulty(card_type);
        card.setDate_creted(date_created);
        card.setLast_study(calendar.getTime());
        card.setCurrent_level(0.0);
        card.setVolatility(1.0);
        card.setTimes_studied(0);

        databaseManager.insertOrUpdateCard(card);

    }

    private void updateDeck(DBDeck deck){

        deck.setNumber_of_cards(deck.getNumber_of_cards() + 1);

        deck.setTotal_new_cards(deck.getTotal_new_cards() + 1);
        databaseManager.insertOrUpdateDeck(deck);

    }

    public void onEventMainThread(ImagePathBUS event) {
        multimedia_path = event.getPath();

        if (CustomModel.getInstance().getFromImage() == 1)
        {
            back_from_multimedia = true;

            if (restarted)
            {
                changeEditTextFields();
            }
        }

    }

    public void onEventMainThread(AudioEncodedBUS event) {
        encodedAudio = event.getEncodedAudio();
        if (CustomModel.getInstance().getFromImage() == 2)
        {
            back_from_audio = true;

            if (restarted)
            {
                changeEditTextFieldsIntoAudio();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        data = CustomModel.getInstance().getList();
        databaseManager = new DatabaseManager(this);
        for (int i = 0; i < data.size(); i++) {
            deck_names.add(data.get(i).getName());
            deckIds.add(data.get(i).getDeckId());
        }

        mImagePreview = (ImageView) findViewById(R.id.gallery_try);
        final EditText back = (EditText) findViewById(R.id.back);
        final EditText front = (EditText) findViewById(R.id.front);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final TextView deckTextView = (TextView) findViewById(R.id.deck_name);
        Paint textPaint = deckTextView.getPaint();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getString("deck") != null){
                deck = extras.getString("deck");
                getSupportActionBar().setTitle("Create card");
                accept_state = "Create";
            }
            if(extras.getLong("card_id") != 0){
                card_id = extras.getLong("card_id");
                getSupportActionBar().setTitle("Edit Card");
                accept_state = "Update";
                DBCard card = databaseManager.getCardById(card_id);
                card_type = card.getDifficulty();

                CardTypesPlaceholderUtils type = new CardTypesPlaceholderUtils(card.getDifficulty());;

                TextView textView = (TextView) findViewById(R.id.tv1);
                TextView textView2 = (TextView) findViewById(R.id.tv2);

                String[] types = card.getDifficulty().split("_");

                if( types[0].equals("text"))
                {
                    front.setText(card.getQuestion());
                    type.setFrontType("text");
                    textView2.setText("Text");
                }
                else if (types[0].equals("image"))
                {
                    front.setText("[Image]");
                    type.setFrontType("image");
                    textView2.setText("Image");
                    front_path = card.getQuestion();
                }
                else
                {
                    front.setText("[Audio]");
                    type.setFrontType("audio");
                    textView2.setText("Audio");
                    front_path = card.getQuestion();
                }

                if( types[1].equals("text"))
                {
                    back.setText(card.getAnswer());
                    type.setBackType("text");
                    textView.setText("Text");
                }
                else if (types[1].equals("image"))
                {
                    back.setText("[Image]");
                    type.setBackType("image");
                    textView.setText("Image");
                    back_path = card.getAnswer();
                }
                else
                {
                    back.setText("[Audio]");
                    type.setBackType("audio");
                    textView.setText("Audio");
                    back_path = card.getAnswer();
                }
                deck = databaseManager.getDeckById(databaseManager.getCardById(card_id).getDeckId()).getName();
            }
            index = deck_names.indexOf(deck);

            float width = textPaint.measureText(deck);
            int widthint = this.getResources().getDisplayMetrics().widthPixels;
            float maxwidth = widthint/3;
            int i = deck.length();
            while(width > maxwidth)
            {

                deck = deck.substring(0, i) + "...";
                width = textPaint.measureText(deck);
                i -= 1;
            }
            deckTextView.setText(deck);

        }
        else
        {
            String deck_name = deck_names.get(0);
            float width = textPaint.measureText(deck_name);
            int widthint = this.getResources().getDisplayMetrics().widthPixels;
            float maxwidth = widthint/3;
            int i = deck_name.length();
            while (width > maxwidth )
            {
                deck_name = deck_name.substring(0, i)+"...";
                width = textPaint.measureText(deck_name);
                i -= 1;
            }
            deckTextView.setText(deck_name);


            index = 0;
            getSupportActionBar().setTitle("Create card");
            accept_state = "Create";
        }

        ar = new TagString();
        ar.add1("red");
        ar.add1("green");


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_done:
                        Date date = new Date();


                        if (front.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Please set a front", Toast.LENGTH_SHORT).show();
                        }
                        else if (back.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Please set a back", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (accept_state.equals("Create")) {

                                if (back != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(back.getWindowToken(), 0);
                                }
                                DBDeck db_deck = databaseManager.getDeckById(deckIds.get(index));
                                DBCard db_card = new DBCard();

                                createOrUpdateCard(db_card, db_deck, back.getText().toString(), front.getText().toString(), card_type, date);
                                updateDeck(db_deck);

                                Toast.makeText(getApplicationContext(), "Card created", Toast.LENGTH_SHORT).show();

                                back.setText("");
                                front.setText("");
                                back.clearFocus();
                                front.clearFocus();
                                //View view = this.getCurrentFocus();

                            } else if (accept_state.equals("Update")) {

                                DBCard card = databaseManager.getCardById(card_id);
                                DBDeck db_deck = databaseManager.getDeckById(deckIds.get(index));
                                DBDeck old_deck = databaseManager.getDeckById(card.getDeckId());
                                if (db_deck.getId() != old_deck.getId()) {

                                    databaseManager.deleteCardById(card_id);
                                    old_deck.setNumber_of_cards(old_deck.getNumber_of_cards() - 1);
                                    old_deck.setTotal_new_cards(old_deck.getTotal_new_cards() - 1);
                                    databaseManager.insertOrUpdateDeck(old_deck);
                                }

                                createOrUpdateCard(card, db_deck, back.getText().toString(), front.getText().toString(), card_type, date);
                                databaseManager.insertOrUpdateDeck(db_deck);


                                Toast.makeText(getApplicationContext(), "Card updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        CardTypesPlaceholderUtils type = new CardTypesPlaceholderUtils(card_type);
                        type.setBackType("text");
                        type.setFrontType("text");
                        card_type = type.getType();
                        TextView textView = (TextView) findViewById(R.id.tv1);
                        textView.setText("Text");
                        TextView textView2 = (TextView) findViewById(R.id.tv2);
                        textView2.setText("Text");
                }
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // May produce exception
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (back_from_multimedia) {
            changeEditTextFields();
        }
        if (back_from_audio)
        {
            changeEditTextFieldsIntoAudio();
        }

    }

    public void changeEditTextFields() {
        EditText text_input;
        TextView textView;
        CardTypesPlaceholderUtils type = new CardTypesPlaceholderUtils(card_type);
        if (CustomModel.getInstance().getLast_multimedia().equals("back"))
        {

            text_input = (EditText)findViewById(R.id.back);
            text_input.setText("[Image]");

            type.setBackType("image");
            card_type = type.getType();

            textView = (TextView) findViewById(R.id.tv1);
            textView.setText("Image");


            back_path = multimedia_path;
        }
        else
        {
            text_input = (EditText)findViewById(R.id.front);
            text_input.setText("[Image]");;

            type.setFrontType("image");
            card_type = type.getType();

            textView = (TextView) findViewById(R.id.tv2);
            textView.setText("Image");

            front_path = multimedia_path;
        }
    }

    public void changeEditTextFieldsIntoAudio() {
        EditText text_input;
        TextView textView;
        CardTypesPlaceholderUtils type = new CardTypesPlaceholderUtils(card_type);
        if (CustomModel.getInstance().getLast_multimedia().equals("back"))
        {
            text_input = (EditText)findViewById(R.id.back);
            text_input.setText("[Audio]");

            type.setBackType("audio");
            card_type = type.getType();

            textView = (TextView) findViewById(R.id.tv1);
            textView.setText("Audio");

            back_path = encodedAudio;
        }
        else
        {
            text_input = (EditText)findViewById(R.id.front);
            text_input.setText("[Audio]");

            type.setFrontType("audio");
            card_type = type.getType();

            textView = (TextView) findViewById(R.id.tv2);
            textView.setText("Audio");

            front_path = encodedAudio;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            // Do Nothing.
        } else if (requestCode == 1) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            if (filePath != null && !filePath.equals("")) {
                File f = new File(filePath);

                Bitmap bmp = null;
                try {
                    // Decode image size
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;

                    FileInputStream fis = new FileInputStream(f);
                    BitmapFactory.decodeStream(fis, null, o);
                    fis.close();

                    int scale = 1;
                    if (o.outHeight > 600 || o.outWidth > 600) {
                        scale = (int) Math.pow(
                                2,
                                (int) Math.round(Math.log(600 / (double) Math.max(o.outHeight, o.outWidth))
                                        / Math.log(0.5)));
                    }

                    // Decode with inSampleSize
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    fis = new FileInputStream(f);
                    bmp = BitmapFactory.decodeStream(fis, null, o2);

                    fis.close();
                }
                catch (IOException e)
                {
                }

                mImagePreview.setImageBitmap(bmp);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView textView;
        CardTypesPlaceholderUtils type = new CardTypesPlaceholderUtils(card_type);
        switch (item.getItemId()) {
            case R.id.text_b_id:
                type.setBackType("text");
                card_type = type.getType();
                CustomModel.getInstance().setLast_multimedia("back");

                textView = (TextView) findViewById(R.id.tv1);
                textView.setText("Text");
                return true;
            case R.id.image_b_id:
                /*type.setBackType("image");
                card_type = type.getType();*/
                CustomModel.getInstance().setLast_multimedia("back");

               /* textView = (TextView) findViewById(R.id.tv1);
                textView.setText("Image");*/
                Intent intent = new Intent(this, AddImageActivity.class);
                startActivity(intent);
                return true;
            case R.id.audio_b_id:
                /*type.setBackType("audio");
                card_type = type.getType();*/
                CustomModel.getInstance().setLast_multimedia("back");

                /*textView = (TextView) findViewById(R.id.tv1);
                textView.setText("Audio");*/
                Intent i = new Intent(this, AudioRecordActivity.class);
                startActivity(i);
                return true;
            case R.id.text_f_id:
                type.setFrontType("text");
                card_type = type.getType();
                CustomModel.getInstance().setLast_multimedia("front");

                textView = (TextView) findViewById(R.id.tv2);
                textView.setText("Text");
                return true;
            case R.id.image_f_id:
                /*type.setFrontType("image");
                card_type = type.getType();*/
                CustomModel.getInstance().setLast_multimedia("front");

                /*textView = (TextView) findViewById(R.id.tv2);
                textView.setText("Image");*/
                Intent intent1 = new Intent(this, AddImageActivity.class);
                startActivity(intent1);
                return true;
            case R.id.audio_f_id:
                /*type.setFrontType("audio");
                card_type = type.getType();*/
                CustomModel.getInstance().setLast_multimedia("front");

                /*textView = (TextView) findViewById(R.id.tv2);
                textView.setText("Audio");*/
                Intent i1 = new Intent(this, AudioRecordActivity.class);
                startActivity(i1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
            //CustomModel.getInstance().setDatabaseManager(null);
        }
        super.onStop();
    }

    @Override
    protected void onRestart() {
        databaseManager = new DatabaseManager(this);
        restarted = true;
        super.onRestart();
    }


    @Override
    protected void onDestroy() {
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
        }
        super.onStop();
    }

}


class yourAdapter extends BaseAdapter {

    Context context;
    TagString data;
    private static LayoutInflater inflater = null;

    public yourAdapter(Context context, TagString data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.len();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        CheckBox text = (CheckBox) vi.findViewById(R.id.checkbox);
        text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    data.setCheckState(position, "false");
                }
            }
        });
        text.setText(data.get(position));
        switch (data.getB(position)) {
            case "true":
                text.setChecked(true);
                break;
            case "false":
                text.setChecked(false);
                break;
        }

        return vi;
    }
}

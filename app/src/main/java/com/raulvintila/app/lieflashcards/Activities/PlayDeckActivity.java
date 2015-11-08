package com.raulvintila.app.lieflashcards.Activities;

import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardContent;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardProgress;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.DrawingView;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.SetImageTask;
import com.raulvintila.app.lieflashcards.Utils.Algorithms.SpacedLearningAlgoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PlayDeckActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private IDatabaseManager databaseManager;
    private List<DBCard> card_list;
    private TextView front_text;
    private MediaPlayer mPlayer;
    private TextView back_text;
    private boolean do_switch;
    private TextToSpeech text_to_speech;
    private DrawingView drawing_view;
    private boolean whiteboard_enabled;
    private RelativeLayout main_linear_layout;
    private boolean preview;

    private void showCardFace(String type ) {
        //String[] types = card_list.get(0).getDifficulty().split("_");
        List<String> types = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for( int i = 0 ; i < card_list.get(0).getCardContents().size(); i++)
        {
            values.add(card_list.get(0).getCardContents().get(i).getValue());
            types.add(card_list.get(0).getCardContents().get(i).getType());
        }

        TextView text_view_back = (TextView) findViewById(R.id.back_text);
        ImageView image_view_back = (ImageView) findViewById(R.id.back_image);
        ImageView audio_view_back = (ImageView) findViewById(R.id.back_audio);

        if (type.equals("front")) {
            TextView text_view = (TextView) findViewById(R.id.front_text);
            ImageView image_view = (ImageView) findViewById(R.id.front_image);
            ImageView audio_view = (ImageView) findViewById(R.id.front_audio);

            text_view_back.setVisibility(View.GONE);
            image_view_back.setVisibility(View.GONE);
            audio_view_back.setVisibility(View.GONE);

            if (types.get(0).equals("text")) {
                //text_to_speech.speak(card_list.get(0).getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                text_to_speech.speak(values.get(0), TextToSpeech.QUEUE_FLUSH, null);
                text_view.setVisibility(View.VISIBLE);
                /*text_view.setText(card_list.get(0).getQuestion());*/
                text_view.setText(values.get(0));
                image_view.setVisibility(View.GONE);
                audio_view.setVisibility(View.GONE);
            } else if (types.get(0).equals("image")) {

                text_view.setVisibility(View.GONE);
                image_view.setVisibility(View.VISIBLE);

                /*new SetImageTask(image_view).execute(card_list.get(0).getQuestion());*/
                new SetImageTask(image_view).execute(values.get(0));

                audio_view.setVisibility(View.GONE);
            } else if (types.get(0).equals("audio")) {
                text_view.setVisibility(View.GONE);
                image_view.setVisibility(View.GONE);
                audio_view.setVisibility(View.VISIBLE);

                /*playAudio(card_list.get(0).getQuestion());*/
                playAudio(values.get(0));

            }
        } else {

            if (types.get(1).equals("text")) {
                text_to_speech.speak(values.get(1), TextToSpeech.QUEUE_FLUSH, null);
                text_view_back.setVisibility(View.VISIBLE);
                text_view_back.setText(values.get(1));
                image_view_back.setVisibility(View.GONE);
                audio_view_back.setVisibility(View.GONE);
            } else if (types.get(1).equals("image")) {

                text_view_back.setVisibility(View.GONE);
                image_view_back.setVisibility(View.VISIBLE);

                new SetImageTask(image_view_back).execute(values.get(1));

                audio_view_back.setVisibility(View.GONE);
            } else if (types.get(1).equals("audio")) {
                text_view_back.setVisibility(View.GONE);
                image_view_back.setVisibility(View.GONE);
                audio_view_back.setVisibility(View.VISIBLE);

                playAudio(values.get(1));
            }

        }
    }

    private void playAudio(String outputfile){


        try
        {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(outputfile);
            mPlayer.prepare();
            mPlayer.start();
            Toast.makeText(getApplicationContext(), "Audio started", Toast.LENGTH_SHORT).show();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getApplicationContext(), "Audio finished", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void switchToAnswer(){
        RelativeLayout show_answer_rl = (RelativeLayout) findViewById(R.id.show_answer_layout);
        RelativeLayout dificullty_rl = (RelativeLayout) findViewById(R.id.difficulty_layout);
        show_answer_rl.setVisibility(View.INVISIBLE);
        dificullty_rl.setVisibility(View.VISIBLE);
        showCardFace("back");
    }

    private void switchToQuestion(){
        if (mPlayer != null ){
            mPlayer.release();
            mPlayer = null;
        }
        if (whiteboard_enabled)
            drawing_view.clear();
        if(do_switch) {
            switchCardFaces();
        }
        RelativeLayout show_answer_rl = (RelativeLayout) findViewById(R.id.show_answer_layout);
        RelativeLayout dificullty_rl = (RelativeLayout) findViewById(R.id.difficulty_layout);
        dificullty_rl.setVisibility(View.INVISIBLE);
        show_answer_rl.setVisibility(View.VISIBLE);
        if(card_list.size() > 0)
            study();
        else
            onBackPressed();
    }

    private void shuffleCard() {
        if(new SpacedLearningAlgoUtils().readyToRestudy(card_list.get(0))) {
            int size = card_list.size();
            int position_field;
            if(card_list.size() == 1)
                position_field = 1;
            else
                position_field = (int)(size * 0.7);
            Random rand = new Random();
            int index = size - position_field + rand.nextInt(position_field);
            DBCard card = card_list.remove(0);
            card_list.add(index, card);
        } else {
            card_list.remove(0);
        }
    }

    public void onClick(View view){

        DBCard card;
        DBCardProgress cardProgress;

        switch (view.getId()){
            case R.id.front_audio:
                if (mPlayer != null ){
                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getApplicationContext(), "Audio stoped", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ( !preview)
                    {
                        playAudio(card_list.get(0).getCardContents().get(0).getValue());
                    }

                }
                return;

            case R.id.back_audio:
                if (mPlayer != null ){
                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getApplicationContext(), "Audio stoped", Toast.LENGTH_SHORT).show();
                } else {
                    if ( !preview)
                    {
                        playAudio(card_list.get(0).getCardContents().get(1).getValue());
                    }
                }
                return;
            case R.id.show_answer:
                if (!preview)
                {
                    switchToAnswer();
                }
                return;
            case R.id.hard:
                if (preview) {

                } else {
                    card = card_list.get(0);
                    cardProgress = databaseManager.getCardProgressById(card.getId());
                    double level = cardProgress.getLevel();//.getCurrent_level();
                    double volatility = cardProgress.getVolatility();
                    int times = cardProgress.getTimesStudied();//.getTimes_studied();

                    cardProgress.setLastStudyDate(new Date().getTime());//.setLast_study(new Date());
                    cardProgress.setLevel(new SpacedLearningAlgoUtils()
                            .updateLevel(level, 0, volatility, times));
                    cardProgress.setTimesStudied(times + 1);//.setTimes_studied(times + 1);
                    databaseManager.insertOrUpdateCardProgress(cardProgress);
                   // databaseManager.insertOrUpdateCard(card);
                    shuffleCard();
                    switchToQuestion();
                }
                return;
            case R.id.normal:
                if (preview) {

                } else {
                    card = card_list.get(0);
                    cardProgress = databaseManager.getCardProgressById(card.getId());
                    cardProgress.setLastStudyDate(new Date().getTime());//.setLast_study(new Date());
                    cardProgress.setVolatility(new SpacedLearningAlgoUtils()
                            .getVolatility(cardProgress.getVolatility(), cardProgress.getTimesStudied()));
                    cardProgress.setLevel(new SpacedLearningAlgoUtils()
                            .updateLevel(cardProgress.getLevel(), 1, cardProgress.getVolatility(),
                                    cardProgress.getTimesStudied()));
                    //card_list.get(0).setTimes_studied(0);
                    cardProgress.setTimesStudied(0);
                    databaseManager.insertOrUpdateCardProgress(cardProgress);
                    //databaseManager.insertOrUpdateCard(card);
                    shuffleCard();
                    switchToQuestion();
                }
                return;
            case R.id.easy:
                if (preview) {

                } else {
                    card = card_list.get(0);
                    cardProgress = databaseManager.getCardProgressById(card.getId());
                    cardProgress.setLastStudyDate(new Date().getTime());//.setLast_study(new Date());
                    cardProgress.setVolatility(new SpacedLearningAlgoUtils()
                            .getVolatility(cardProgress.getVolatility(), cardProgress.getTimesStudied()));
                    cardProgress.setLevel(new SpacedLearningAlgoUtils()
                            .updateLevel(cardProgress.getLevel(), 2, cardProgress.getVolatility(),
                                    cardProgress.getTimesStudied()));
                    cardProgress.setTimesStudied(0);//.setTimes_studied(0);
                    databaseManager.insertOrUpdateCardProgress(cardProgress);
                    shuffleCard();
                    switchToQuestion();
                }
                return;
        }
    }

    private void study() {
        //text_to_speech.speak(card_list.get(0).getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
        //front.setText(card_list.get(0).getQuestion());
        showCardFace("front");
    }

    private void showpreviewface( String type, String path){

        final TextView text_view_front = (TextView) findViewById(R.id.front_text);
        final ImageView image_view_front = (ImageView) findViewById(R.id.front_image);
        final ImageView audio_view_front = (ImageView) findViewById(R.id.front_audio);
        final TextView text_view_back = (TextView) findViewById(R.id.back_text);
        final ImageView image_view_back = (ImageView) findViewById(R.id.back_image);
        final ImageView audio_view_back = (ImageView) findViewById(R.id.back_audio);

        text_view_back.setVisibility(View.GONE);
        image_view_back.setVisibility(View.GONE);
        audio_view_back.setVisibility(View.GONE);



        if (type.equals("text")) {
            //text_to_speech.speak(front, TextToSpeech.QUEUE_FLUSH, null);
            text_view_front.setVisibility(View.VISIBLE);
            text_view_front.setText(path);
            image_view_front.setVisibility(View.GONE);
            audio_view_front.setVisibility(View.GONE);
        } else if (type.equals("image")) {

            text_view_front.setVisibility(View.GONE);
            image_view_front.setVisibility(View.VISIBLE);

            new SetImageTask(image_view_front).execute(path);

            audio_view_front.setVisibility(View.GONE);
        } else if (type.equals("audio")) {
            text_view_front.setVisibility(View.GONE);
            image_view_front.setVisibility(View.GONE);
            audio_view_front.setVisibility(View.VISIBLE);

            playAudio(path);

        }
    }

    private void showpreviewback(String type, String path){
        final TextView text_view_back = (TextView) findViewById(R.id.back_text);
        final ImageView image_view_back = (ImageView) findViewById(R.id.back_image);
        final ImageView audio_view_back = (ImageView) findViewById(R.id.back_audio);

        if (type.equals("text")) {
            // text_to_speech.speak(back, TextToSpeech.QUEUE_FLUSH, null);
            text_view_back.setVisibility(View.VISIBLE);
            text_view_back.setText(path);
            image_view_back.setVisibility(View.GONE);
            audio_view_back.setVisibility(View.GONE);
        } else if (type.equals("image")) {

            text_view_back.setVisibility(View.GONE);
            image_view_back.setVisibility(View.VISIBLE);

            new SetImageTask(image_view_back).execute(path);

            audio_view_back.setVisibility(View.GONE);
        } else if (type.equals("audio")) {
            text_view_back.setVisibility(View.GONE);
            image_view_back.setVisibility(View.GONE);
            audio_view_back.setVisibility(View.VISIBLE);

            playAudio(path);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        front_text = (TextView) findViewById(R.id.front_text);
        back_text = (TextView) findViewById(R.id.back_text);
        main_linear_layout = (RelativeLayout) findViewById(R.id.whiteboard_layout);
        drawing_view = new DrawingView(this);
        databaseManager = ((MyApplication) getApplicationContext()).databaseManager;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                if (mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }
                finish();
            }
        });

        preview = getIntent().getExtras().getBoolean("is_preview");

        if (preview) {


            getSupportActionBar().setTitle("Preview");

            String card_type = getIntent().getExtras().getString("card_type");
            final String[] types = card_type.split("_");

            String front = getIntent().getExtras().getString("front");


            showpreviewface(types[0], front);


            Button placeholder = ((Button) findViewById(R.id.show_answer));
            placeholder.setText("Placeholder");
            //CustomModel.getInstance().setRefresh(false);
            placeholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String back = getIntent().getExtras().getString("back");
                    showpreviewback(types[1],back);
                    /*if (CustomModel.getInstance().getRefresh()) {
                        showpreviewface(types[0], front);
                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                        CustomModel.getInstance().setRefresh(false);
                    } else {
                        showpreviewback(types[1], back);
                        CustomModel.getInstance().setRefresh(true);
                    }*/

                }
            });

        } else {


            text_to_speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        text_to_speech.setLanguage(Locale.ENGLISH);
                    }
                }
            });

            DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());
            card_list = new SpacedLearningAlgoUtils().getTodayList(deck.getCards(), 25);


            //drawing_view = new DrawingView(this);

            //main_linear_layout = (RelativeLayout) findViewById(R.id.whiteboard_layout);

            /*main_linear_layout.addView(drawing_view);*/

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(card_list.get(0).getCardContents().get(0).getType().equals("text")) {
                        text_to_speech.speak(card_list.get(0).getCardContents().get(0).getValue(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }, 125);
            study();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_swap) {
            if (preview) {
                String aux = front_text.getText().toString();
                front_text.setText(back_text.getText().toString());
                back_text.setText(aux);
            } else {
                main_linear_layout.removeView(drawing_view);
                do_switch = true;
                return true;
            }
        }

        if(id == R.id.action_whiteboard) {
            //MenuItem clear = (MenuItem) findViewById(R.id.action_clear_whiteboard);
            if (whiteboard_enabled) {
                item.setTitle("Enable whiteboard");
                //clear.setVisible(false);
                whiteboard_enabled = false;
                main_linear_layout.removeView(drawing_view);
            }
            else {
                item.setTitle("Disable whiteboard");
                //clear.setVisible(true);
                whiteboard_enabled = true;
                main_linear_layout.addView(drawing_view);
            }
        }

        if(id == R.id.action_clear_whiteboard) {
            drawing_view.clear();
            // Here the color should change, maybe the icon should be faded off
            //item.setVisible(false);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchCardFaces() {
        String aux;
        for (int i = 0; i < card_list.size(); i++) {
            DBCard card = card_list.get(i);
            aux = card.getCardContents().get(0).getValue();
            card.getCardContents().get(0).setValue(card.getCardContents().get(1).getValue());//setQuestion(card_list.get(i).getAnswer());
            card.getCardContents().get(1).setValue(aux);//.setAnswer(aux);
        }
        do_switch = false;
    }
}

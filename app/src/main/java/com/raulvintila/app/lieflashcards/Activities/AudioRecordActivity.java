package com.raulvintila.app.lieflashcards.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import com.raulvintila.app.lieflashcards.AudioEncodedBUS;
import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.ImagePathBUS;
import com.raulvintila.app.lieflashcards.R;

import de.greenrobot.event.EventBus;

public class AudioRecordActivity extends ActionBarActivity {


    Button play,stop_playing,record,stop_recording,pause_playing;
    private MediaRecorder myAudioRecorder;
    private MediaPlayer mPlayer;
    private String outputFile = null;
    private String encodedAudio = null;
    int play_state = 1;
    boolean record_state = true;
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";


/*    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record_test);

        play=(Button)findViewById(R.id.play_button);
        record=(Button)findViewById(R.id.record_button);
        stop_playing=(Button)findViewById(R.id.stop_playing);

        play.setEnabled(false);
        stop_playing.setEnabled(false);

       /* try{
            File file2 = new File(Environment.getExternalStorageDirectory() + "/recording.3gp");
            FileOutputStream os = new FileOutputStream(file2, false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record_state == true)
                {
                    try {

                        outputFile = getExternalFilesDir(null).getAbsolutePath() + "/lieFlashcardsAudio_"+ System.currentTimeMillis() +".3gp";




                        myAudioRecorder = new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(outputFile);
                        myAudioRecorder.prepare();
                        File file = new File(outputFile);
                        myAudioRecorder.start();


                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    play.setEnabled(false);

                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
                    record.setText("Stop Recording");
                    record_state = false;
                }
                else if(record_state == false)
                {

                    record_state = true;
                    try
                    {
                        myAudioRecorder.stop();
                        myAudioRecorder.release();
                        myAudioRecorder = null;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    //stop_recording.setEnabled(false);
                    play.setEnabled(true);

                    Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_SHORT).show();
                    record.setText("Start Recording");

                   /* try {
                        File outFile = new File(outputFile);
                        byte[] bytes = loadFile(outFile);
                        encodedAudio = Base64.encodeToString(bytes , 0);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }*/
                }
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                if (play_state == 1) {
                    mPlayer = new MediaPlayer();

                    try {
                        mPlayer.setDataSource(outputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    stop_playing.setEnabled(true);
                    record.setEnabled(false);
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_SHORT).show();
                    play_state = 2;
                    play.setText("Pause");
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            stop_playing.setEnabled(false);
                            play.setEnabled(true);
                            play.setText("Start Playing");
                            play_state = 1;
                            record.setEnabled(true);
                            stop_playing.setEnabled(false);
                            mPlayer.release();
                            mPlayer = null;
                            Toast.makeText(getApplicationContext(), "Audio finished", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else if(play_state == 2){
                    play.setEnabled(true);
                    record.setEnabled(false);
                    mPlayer.pause();
                    play_state =3;
                    play.setText("Resume");
                }
                else if(play_state == 3){
                    mPlayer.start();
                    play_state = 2;
                    play.setText("Pause");
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            stop_playing.setEnabled(false);
                            play.setEnabled(true);
                            play.setText("Start Playing");
                            play_state = 1;
                            record.setEnabled(true);
                            stop_playing.setEnabled(false);
                            mPlayer.release();
                            mPlayer = null;
                            Toast.makeText(getApplicationContext(), "Audio finished", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        stop_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {

                play.setEnabled(true);
                play.setText("Start Playing");
                play_state = 1;
                record.setEnabled(true);
                stop_playing.setEnabled(false);
                mPlayer.release();
                mPlayer = null;
                Toast.makeText(getApplicationContext(), "Audio stoped", Toast.LENGTH_SHORT).show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Record Audio");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomModel.getInstance().setFromImage(0);
                onBackPressed();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio_record, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(mPlayer !=null){
            mPlayer.release();
            mPlayer=null;
        }
        if(myAudioRecorder != null){
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
        }

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

        if (id == R.id.action_done) {
            if(outputFile != null) {
                //CustomModel.getInstance().setEncodedAudio(encodedAudio);
                EventBus.getDefault().postSticky(new AudioEncodedBUS(outputFile));
                CustomModel.getInstance().setFromImage(2);

            }
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

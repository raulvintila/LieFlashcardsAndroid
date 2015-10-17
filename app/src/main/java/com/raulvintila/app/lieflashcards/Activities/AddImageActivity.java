package com.raulvintila.app.lieflashcards.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.ImagePathBUS;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.SearchAPI.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import de.greenrobot.event.EventBus;


public class AddImageActivity extends ActionBarActivity {

    protected static final int ACTIVITY_SELECT_IMAGE = 1;
    protected static final int ACTIVITY_TAKE_PICTURE = 2;
    private ImageView mImagePreview;
    protected String mTempCameraImagePath;
    private String mCurrentPhotoPath;

    private ContentValues values;
    private Uri imageUri;
    private Bitmap thumbnail;
    private String image_url;
    private int preview_index = 0;
    private Response api_call_result;
    private ProgressDialog dialog;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void avenit(Response result){

        api_call_result = result;

        final ImageView prev = (ImageView) findViewById(R.id.prev);
        final ImageView next = (ImageView) findViewById(R.id.next);


        final String url,url_fail;

        url_fail = "http://www.quinl.com/img/no-image.jpg";
        url = result.getResponseData().getResults().get(0).getUrl();
        Picasso.with(this).load(url).error(R.drawable.eroare).into(mImagePreview, new Callback() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                //dialog.dismiss();
                /*prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);*/
                api_call_result.getResponseData().getResults().remove(0);
                //avenit(api_call_result);
                ImageView prext = (ImageView) findViewById(R.id.next);
                prext.performClick();
            }
        });

    }

    private void refresh_preview() {
        String url = api_call_result.getResponseData().getResults().get(preview_index).getUrl();
        Picasso.with(this).load(url).error(R.drawable.eroare).into(mImagePreview, new Callback() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                final ImageView prev = (ImageView) findViewById(R.id.prev);
                final ImageView next = (ImageView) findViewById(R.id.next);
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                //dialog.dismiss();
                /*prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);*/
                api_call_result.getResponseData().getResults().remove(preview_index);
                ImageView prext = (ImageView) findViewById(R.id.next);
                prext.performClick();
            }
        });

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.google_search:
                EditText input = (EditText) findViewById(R.id.input);
                AsyncCallAPI lol = new AsyncCallAPI();
                lol.setQuery(input.getText().toString());
                lol.execute();
                /*InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);*/

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }


                dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading Images. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                return ;
            case R.id.gallery_button:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                return;
            case R.id.camera_button:
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        startActivityForResult(intent, ACTIVITY_TAKE_PICTURE);
                    }
                }
                return;
            case R.id.prev:
                if(preview_index == 0)
                    preview_index = api_call_result.getResponseData().getResults().size() - 1;
                else
                    preview_index--;
                refresh_preview();
                return ;
            case R.id.next:
                if(preview_index == (api_call_result.getResponseData().getResults().size() - 1))
                    preview_index = 0;
                else
                    preview_index++;
                refresh_preview();
                return ;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Bitmap bmp = null;*/
        if (requestCode == Activity.RESULT_CANCELED) {
            // Do Nothing.
        } else if (requestCode == ACTIVITY_SELECT_IMAGE) {
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
                    image_url = filePath;
                    fis.close();
                } catch (IOException e) {
                }
                Bitmap b = bmp;

                ImageView prev = (ImageView) findViewById(R.id.prev);
                if(prev.getVisibility() == View.VISIBLE) {
                    ImageView next = (ImageView) findViewById(R.id.next);
                    prev.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }


                mImagePreview.setImageBitmap(b);
            }

        }else if (requestCode == ACTIVITY_TAKE_PICTURE) {
            try {

                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                image_url = getRealPathFromURI(imageUri);


                float scale = Math.min(((float)600 / thumbnail.getWidth()), ((float)600 / thumbnail.getHeight()));

                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);


                Bitmap b = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);


                ImageView prev = (ImageView) findViewById(R.id.prev);
                if(prev.getVisibility() == View.VISIBLE) {
                    ImageView next = (ImageView) findViewById(R.id.next);
                    prev.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }

                mImagePreview.setImageBitmap(b);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private String rotateAndCompress(String inPath) {
        // Set the rotation of the camera image and save as png
        Bitmap bmp = null;
        File f = new File(inPath);
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
        } catch (IOException e) {
        }
        // use same filename but with png extension for output file
        String outPath = inPath.substring(0, inPath.lastIndexOf(".")) + ".png";
        // Load into a bitmap with max size of 1920 pixels and rotate if necessary
        Bitmap b = bmp;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outPath);
            //b = ExifUtil.rotateFromCamera(f, b);
            //b.compress(Bitmap.CompressFormat.PNG, 90, out);
            f.delete();
            return outPath;
        } catch (FileNotFoundException e) {
            //Timber.e("Error in BasicImageFieldController.rotateAndCompress() : " + e.getMessage());
            return inPath;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        preview_index = 0;

        EditText input = (EditText) findViewById(R.id.input);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Button button =(Button) findViewById(R.id.google_search);
                    button.performClick();
                    return true;
                }
                return false;
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Image");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                CustomModel.getInstance().setFromImage(0);
            }
        });

        mImagePreview = (ImageView) findViewById(R.id.preview_image);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_done) {
            if(image_url != null) {
                EventBus.getDefault().postSticky(new ImagePathBUS(image_url));
                CustomModel.getInstance().setFromImage(1);
            }
            onBackPressed();
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncCallAPI extends AsyncTask<Void, Void, Response> {

        private String mQuery;


        @Override
        protected Response doInBackground(Void... params) {
            try {
                String ip, encoded_ip;
                encoded_ip = null;
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ip = inetAddress.getHostAddress();
                            encoded_ip = URLEncoder.encode(ip, "utf-8");
                        }
                    }
                }

                //encoded_ip = URLEncoder.encode(ip, "utf-8");

                String query = getQuery().replace(" ","+");

                URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?"
                        + "v=1.0&q=Q&userip=IP&imgsz=medium".replaceAll("Q", query).replaceAll("IP", encoded_ip));
                ///URL url = new URL("http://lie-server.herokuapp.com/users/get");

                URLConnection connection = url.openConnection();
                connection.addRequestProperty("Referer", "LieFlashcards");

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                Gson gson = new Gson();
                Response resp = gson.fromJson(builder.toString(), Response.class);

                return resp;

            } catch (Exception e) {
                return new Response();
            }
        }


        @Override
        protected void onPostExecute(Response result) {
            avenit(result);
        }

        public void setQuery(String query) {
            mQuery = query;
        }

        public String getQuery() {
            return mQuery;
        }

    }
}

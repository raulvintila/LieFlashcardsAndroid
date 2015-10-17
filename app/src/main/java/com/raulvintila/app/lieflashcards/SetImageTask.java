package com.raulvintila.app.lieflashcards;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

public class SetImageTask extends AsyncTask<String, Void, Bitmap>
{

    private final WeakReference<ImageView> imageViewWeakReference;

    public SetImageTask(ImageView imageView)
    {
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        return BitmapFactory.decodeFile(new File(params[0]).getAbsolutePath());
    }

    @Override
    protected  void onPostExecute(Bitmap bitmap)
    {
        if (imageViewWeakReference != null && bitmap != null)
        {
            final ImageView imageView = imageViewWeakReference.get();
            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}

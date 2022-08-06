package com.ss_technology.hdmobilebackground.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.snackbar.Snackbar;
import com.ss_technology.hdmobilebackground.Container.ImageContainer;
import com.ss_technology.hdmobilebackground.Databases.DB_Helper;
import com.ss_technology.hdmobilebackground.FragmentClasses.Randoms;
import com.ss_technology.hdmobilebackground.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Search extends AppCompatActivity {

    ImageView image;
    boolean check=false;
    final int PERMISSION_REQUEST_CODE=1000;
    Bitmap maps;
    ImageContainer container;
    DB_Helper helper;
    private FrameLayout adContainerView;
    AdView adView;
    private InterstitialAd interstitialAd;
    ImageView background;
    TextView download,view,like,fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        image=findViewById(R.id.image);
        helper=new DB_Helper(Search.this);
        background=findViewById(R.id.back);
        download=findViewById(R.id.download);
        view=findViewById(R.id.view);
        like=findViewById(R.id.like);
        fav=findViewById(R.id.fav);


             container= (ImageContainer) getIntent().getSerializableExtra("obj");

             download.setText(container.getDownload());
             view.setText(container.getView());
             like.setText(container.getLike());
             fav.setText(container.getFav());

            Glide.with(Search.this).load(container.getLargUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    check=false;
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                    check=true;
                    return false;
                }
            }).into(image);

            Glide.with(getApplicationContext()).load(container.getLargUrl()).into(background);
    }

    public void ShareNow(View view) {
       // favourite image
       byte[] ar=makebyte(container);
      boolean ch=helper.setData(Integer.parseInt(container.getId()),ar);
      if (ch)
      {
          Toast.makeText(this, "Successfully added.", Toast.LENGTH_SHORT).show();
      }
      else
      {
          Toast.makeText(this, "Photo already added!", Toast.LENGTH_SHORT).show();
      }
    }

    public byte[] makebyte(ImageContainer modeldata) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(modeldata);
            byte[] employeeAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
            return employeeAsBytes;
        } catch (IOException e) {
            Toast.makeText(Search.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public void SetBackground(View view) {
       try {
        AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
        final LayoutInflater inflater = (Search.this).getLayoutInflater();
        View viewss = inflater.inflate(R.layout.option, null);
        builder.setView(viewss);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        Button home,lock,both;
        home=viewss.findViewById(R.id.home);
        lock=viewss.findViewById(R.id.lock);
        both=viewss.findViewById(R.id.both);
        final TextView loading=viewss.findViewById(R.id.loading);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                loading.setVisibility(View.VISIBLE);
                SetHome();

            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                loading.setVisibility(View.VISIBLE);
                SetLockScreen();

            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                loading.setVisibility(View.VISIBLE);
                SetHome();
                SetLockScreen();

            }
        });
       }
       catch (Exception e)
       {

       }

    }

    public void Download(View view) {

        if(check)
        {
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            maps=drawable.getBitmap();

            if (Build.VERSION.SDK_INT >= 23)
            {
                if (checkPermission())
                {
                    SaveImage(bitmap);
                } else {
                    requestPermission(); // Code for permission
                }
            }
            else
            {
                SaveImage(bitmap);
            }

        }
        else {
            Toast.makeText(this, "Please wait for image to loaded.", Toast.LENGTH_SHORT).show();
        }

    }


    private void SaveImage(Bitmap finalBitmap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
        final LayoutInflater inflater = (Search.this).getLayoutInflater();
        View viewss = inflater.inflate(R.layout.loading, null);
        builder.setView(viewss);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/HD_Mobile_Wallpaper");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();
            alertDialog.dismiss();
            Toast.makeText(Search.this, "Download Successfull!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            alertDialog.dismiss();
            Toast.makeText(this, "Something went try again.", Toast.LENGTH_SHORT).show();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        alertDialog.dismiss();
                    }
                });
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Search.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Search.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Search.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(Search.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(maps != null)
                    {
                        SaveImage(maps);
                    }
                } else {
                    requestPermission();
                }
                break;
        }
    }

    private void SetHome()
    {
        if(check)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
            final LayoutInflater inflater = (Search.this).getLayoutInflater();
            View viewss = inflater.inflate(R.layout.loading, null);
            builder.setView(viewss);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            try{
                manager.setBitmap(bitmap);
                alertDialog.dismiss();
                Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        }
        else {
            Toast.makeText(this, "Please wait for image to loaded.", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SetLockScreen()
    {
        if(check)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
            final LayoutInflater inflater = (Search.this).getLayoutInflater();
            View viewss = inflater.inflate(R.layout.loading, null);
            builder.setView(viewss);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            try{
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                alertDialog.dismiss();
                Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        }
        else {
            Toast.makeText(this, "Please wait for image to loaded.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}

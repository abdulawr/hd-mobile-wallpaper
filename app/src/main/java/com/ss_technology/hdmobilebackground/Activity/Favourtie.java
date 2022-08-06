package com.ss_technology.hdmobilebackground.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.ss_technology.hdmobilebackground.Adapter.ImageView_Adapter;
import com.ss_technology.hdmobilebackground.Container.ImageContainer;
import com.ss_technology.hdmobilebackground.Databases.DB_Helper;
import com.ss_technology.hdmobilebackground.Manager.AutoFitGridLayoutManager;
import com.ss_technology.hdmobilebackground.R;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Favourtie extends AppCompatActivity {

    ArrayList<ImageContainer> list;
    RecyclerView recyclerView;
    ImageView_Adapter adapter;
    AutoFitGridLayoutManager manager;
    TextView noresult;
    private FrameLayout adContainerView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourtie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "376231873578012_376233740244492", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.rec);
        noresult=findViewById(R.id.error);

        DB_Helper helper=new DB_Helper(Favourtie.this);
        Cursor cursor=helper.getData();
        if (cursor.moveToFirst()){
            do{
                byte[] data=cursor.getBlob(1);
              ImageContainer obj=read(data);
                list.add(obj);
            }while(cursor.moveToNext());
        }
        cursor.close();

        if(!list.isEmpty())
        {
            noresult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            manager=new AutoFitGridLayoutManager(getApplicationContext(),300);
            adapter=new ImageView_Adapter(getApplicationContext(),list);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            noresult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    public ImageContainer read(byte[] data) {
        try {
            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            ImageContainer dataobj = (ImageContainer ) ois.readObject();
            return dataobj ;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        else if (item.getItemId() == R.id.delete)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(Favourtie.this)
            .setTitle("Do you want to delete")
                    .setMessage("Are you sure to delete all item from the favourite")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                DB_Helper helper=new DB_Helper(getApplicationContext());
                                helper.delete();
                                list.clear();
                                adapter.notifyDataSetChanged();
                                helper.close();
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(Favourtie.this, "Nothing to delete the list is empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           //just cancel
                        }
                    });
            builder.show();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}

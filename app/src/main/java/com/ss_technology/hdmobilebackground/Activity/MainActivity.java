package com.ss_technology.hdmobilebackground.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ss_technology.hdmobilebackground.Adapter.ViewPagerAdapter;
import com.ss_technology.hdmobilebackground.FragmentClasses.Animals;
import com.ss_technology.hdmobilebackground.FragmentClasses.Backgrounds;
import com.ss_technology.hdmobilebackground.FragmentClasses.Buildings;
import com.ss_technology.hdmobilebackground.FragmentClasses.Business;
import com.ss_technology.hdmobilebackground.FragmentClasses.Computer;
import com.ss_technology.hdmobilebackground.FragmentClasses.Education;
import com.ss_technology.hdmobilebackground.FragmentClasses.Fashion;
import com.ss_technology.hdmobilebackground.FragmentClasses.Feelings;
import com.ss_technology.hdmobilebackground.FragmentClasses.Flowers;
import com.ss_technology.hdmobilebackground.FragmentClasses.Food;
import com.ss_technology.hdmobilebackground.FragmentClasses.Galaxy;
import com.ss_technology.hdmobilebackground.FragmentClasses.Girls;
import com.ss_technology.hdmobilebackground.FragmentClasses.Health;
import com.ss_technology.hdmobilebackground.FragmentClasses.IPhone;
import com.ss_technology.hdmobilebackground.FragmentClasses.Industry;
import com.ss_technology.hdmobilebackground.FragmentClasses.Kids;
import com.ss_technology.hdmobilebackground.FragmentClasses.Love;
import com.ss_technology.hdmobilebackground.FragmentClasses.Music;
import com.ss_technology.hdmobilebackground.FragmentClasses.Nature;
import com.ss_technology.hdmobilebackground.FragmentClasses.People;
import com.ss_technology.hdmobilebackground.FragmentClasses.Places;
import com.ss_technology.hdmobilebackground.FragmentClasses.Randoms;
import com.ss_technology.hdmobilebackground.FragmentClasses.Science;
import com.ss_technology.hdmobilebackground.FragmentClasses.Sports;
import com.ss_technology.hdmobilebackground.FragmentClasses.Technology;
import com.ss_technology.hdmobilebackground.FragmentClasses.Transportation;
import com.ss_technology.hdmobilebackground.FragmentClasses.Travel;
import com.ss_technology.hdmobilebackground.FragmentClasses.Trees;
import com.ss_technology.hdmobilebackground.R;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tableLayout;
    ViewPager pager;
    AdView adView;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tableLayout=findViewById(R.id.tabs);
        pager=findViewById(R.id.pager);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupViewPager(pager);
        tableLayout.setupWithViewPager(pager);

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "376231873578012_376233740244492", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

        //376231873578012_376929916841541
        interstitialAd = new InterstitialAd(this, "376231873578012_376929916841541");
        interstitialAd.loadAd();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Randoms(), "Random");
        adapter.addFragment(new Nature(), "Nature");
        adapter.addFragment(new Trees(), "Trees");
        adapter.addFragment(new Love(), "Love");
        adapter.addFragment(new Girls(), "Girls");
        adapter.addFragment(new Flowers(), "Flowers");
        adapter.addFragment(new Fashion(), "Fashion");
        adapter.addFragment(new Technology(), "Technology");
        adapter.addFragment(new Kids(), "Kids");
        adapter.addFragment(new Animals(), "Animals");
        adapter.addFragment(new Sports(), "Sports");
        adapter.addFragment(new Backgrounds(), "Backgrounds");
        adapter.addFragment(new Feelings(), "Feelings");
        adapter.addFragment(new Science(), "Science");
        adapter.addFragment(new Education(), "Education");
        adapter.addFragment(new Music(), "Music");
        adapter.addFragment(new Business(), "Business");
        adapter.addFragment(new Health(), "Health");
        adapter.addFragment(new People(), "People");
        adapter.addFragment(new Places(), "Places");
        adapter.addFragment(new Industry(), "Industry");
        adapter.addFragment(new Computer(), "Computer");
        adapter.addFragment(new Food(), "Food");
        adapter.addFragment(new Transportation(), "Transportation");
        adapter.addFragment(new Travel(), "Travel");
        adapter.addFragment(new Buildings(), "Buildings");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Ratting_Us();
        } else {
            Ratting_Us();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
         if (id == R.id.clear)
         {
             WallpaperManager manager= WallpaperManager.getInstance(getApplicationContext());
             try {
              manager.clearWallpaper();
                 Toast.makeText(this, "Successfully cleared!", Toast.LENGTH_SHORT).show();
             }
             catch (Exception e)
             {
                 Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
             }
         }

         else if (id == R.id.cache)
         {
             if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                 ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE))
                         .clearApplicationUserData();
                 Toast.makeText(this, "Successfully cleared!", Toast.LENGTH_SHORT).show();
             }
         }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if (id == R.id.fav)
        {
            if(interstitialAd != null && interstitialAd.isAdLoaded())
            {
             interstitialAd.show();
             interstitialAd.setAdListener(new InterstitialAdListener() {
                 @Override
                 public void onInterstitialDisplayed(Ad ad) {

                 }

                 @Override
                 public void onInterstitialDismissed(Ad ad) {
                     startActivity(new Intent(getApplicationContext(),Favourtie.class));
                     interstitialAd.loadAd();
                 }

                 @Override
                 public void onError(Ad ad, AdError adError) {
                     startActivity(new Intent(getApplicationContext(),Favourtie.class));
                     interstitialAd.loadAd();
                 }

                 @Override
                 public void onAdLoaded(Ad ad) {

                 }

                 @Override
                 public void onAdClicked(Ad ad) {

                 }

                 @Override
                 public void onLoggingImpression(Ad ad) {

                 }
             });
            }
            else {
                startActivity(new Intent(getApplicationContext(),Favourtie.class));
            }

        }
        
        else if(id == R.id.clearcache)
        {
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE))
                        .clearApplicationUserData();
                Toast.makeText(this, "Successfully cleared!", Toast.LENGTH_SHORT).show();
            }
        }

        else if(id == R.id.clearwallpaper)
        {
            WallpaperManager manager= WallpaperManager.getInstance(getApplicationContext());
            try {
                manager.clearWallpaper();
                Toast.makeText(this, "Successfully cleared!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
            }
        }

        else if(id == R.id.rateus)
        {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ss_technology.hdmobilebackground")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ss_technology.hdmobilebackground")));
            }
        }

        else if(id == R.id.moreapps)
        {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=7653535067649018697")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=7653535067649018697")));
            }
        }

        else if(id == R.id.contact)
        {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"tcomprog@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "HD Mobile Wallpaper");
            email.putExtra(Intent.EXTRA_TEXT, "Write your problem here in details if need please attach screenshot....");
            email.setType("message/rfc822");
            try {
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Email client is not installed.", Toast.LENGTH_SHORT).show();
            }

        }

        else if(id == R.id.share)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = "app link here";
            String sub = "Your Subject";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void Ratting_Us()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater =LayoutInflater.from(MainActivity.this);
        View viewss=inflater.inflate(R.layout.rating_activity, null);
        builder.setCancelable(true);
        builder.setView(viewss);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button save=(Button) viewss.findViewById(R.id.rating);
        Button close=(Button) viewss.findViewById(R.id.close);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ss_technology.hdmobilebackground")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ss_technology.hdmobilebackground")));
                }
                alertDialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

}

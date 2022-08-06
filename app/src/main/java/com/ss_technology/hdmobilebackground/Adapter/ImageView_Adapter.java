package com.ss_technology.hdmobilebackground.Adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.ss_technology.hdmobilebackground.Activity.Favourtie;
import com.ss_technology.hdmobilebackground.Activity.MainActivity;
import com.ss_technology.hdmobilebackground.Activity.Search;
import com.ss_technology.hdmobilebackground.Container.ImageContainer;
import com.ss_technology.hdmobilebackground.R;

import java.util.List;


public class ImageView_Adapter extends RecyclerView.Adapter<ImageView_Adapter.viewHolder> {

    Context context;
    List<ImageContainer> containers;
    InterstitialAd interstitialAd;

    public ImageView_Adapter(Context context, List<ImageContainer> containers) {
        this.context = context;
        this.containers = containers;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageView_Adapter.viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.imageivew_adapter,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
     ImageContainer data=containers.get(position);
     Glide.with(context).load(data.getUrl()).listener(new RequestListener<Drawable>() {
         @Override
         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
             holder.loading.setVisibility(View.GONE);
             return false;
         }

         @Override
         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
          holder.loading.setVisibility(View.GONE);
             return false;
         }
     }).into(holder.imageView);

        interstitialAd = new InterstitialAd(context, "376231873578012_376929916841541");
        interstitialAd.loadAd();

    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView loading;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image);
            loading=itemView.findViewById(R.id.loading);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id=getLayoutPosition();

            if(id % 2 == 0)
            {
                if (interstitialAd.isAdLoaded() && interstitialAd != null) {
                    interstitialAd.show();
                    interstitialAd.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            Intent intent=new Intent(context, Search.class);
                            intent.putExtra("obj",containers.get(getLayoutPosition()));
                            context.startActivity(intent);
                         interstitialAd.loadAd();
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {
                            Intent intent=new Intent(context, Search.class);
                            intent.putExtra("obj",containers.get(getLayoutPosition()));
                            context.startActivity(intent);
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
                } else {
                    Intent intent=new Intent(context, Search.class);
                    intent.putExtra("obj",containers.get(id));
                    context.startActivity(intent);
                }
            }
            else
            {
                Intent intent=new Intent(context, Search.class);
                intent.putExtra("obj",containers.get(id));
                context.startActivity(intent);
            }
            }
    }
}

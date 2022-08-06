package com.ss_technology.hdmobilebackground.FragmentClasses;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ss_technology.hdmobilebackground.Adapter.ImageView_Adapter;
import com.ss_technology.hdmobilebackground.Container.ImageContainer;
import com.ss_technology.hdmobilebackground.Manager.AutoFitGridLayoutManager;
import com.ss_technology.hdmobilebackground.R;
import com.ss_technology.hdmobilebackground.RequestApi.ApiCall;
import com.ss_technology.hdmobilebackground.RequestApi.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class People extends Fragment {

    ArrayList<ImageContainer> list;
    RecyclerView recyclerView;
    ImageView_Adapter adapter;
    LinearLayout error_layout,progress;
    Button refresh;
    Context context;
    AutoFitGridLayoutManager manager;
    ApiCall call;
    String url;

    public People() {
        url="";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list=new ArrayList<>();
        recyclerView=getView().findViewById(R.id.rec);
        error_layout=getView().findViewById(R.id.error_layout);
        refresh=getView().findViewById(R.id.refresh);
        progress=getView().findViewById(R.id.progess);
        call=new ApiCall(getActivity());

        url="https://pixabay.com/api/?key=15433919-001b4345b49fd54cb4fdb3423&orientation=vertical&per_page=200&page=3&image_type=photo&order=popular&safesearch=true&category=people";
        makeCall(url);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    list.clear();
                    makeCall(url);
                }
                catch (Exception e)
                {
                    makeCall(url);
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.people, container, false);
        return view;

    }

    private void makeCall(String query)
    {
        progress.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.GONE);
        error_layout.setVisibility(View.GONE);

        call.MakeCall(query,new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    JSONArray array=object.getJSONArray("hits");
                    for (int i=0; i<array.length(); i++)
                    {
                         JSONObject res=array.getJSONObject(i);
                        ImageContainer con=new ImageContainer();
                        con.setId(res.getString("id"));
                        con.setUrl(res.getString("webformatURL"));
                        con.setLargUrl(res.getString("largeImageURL"));
                        con.setDownload(res.getString("downloads"));
                        con.setView(res.getString("views"));
                        con.setFav(res.getString("favorites"));
                        con.setLike(res.getString("likes"));
                        list.add(con);
                    }
                }
                catch (Exception e)
                {
                    refresh.setVisibility(View.VISIBLE);
                    error_layout.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }

                if(!list.isEmpty())
                {
                    progress.setVisibility(View.GONE);
                    manager=new AutoFitGridLayoutManager(getContext(),300);
                    adapter=new ImageView_Adapter(getActivity(),list);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }

            }
        });
    }



}

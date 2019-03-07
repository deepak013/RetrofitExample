package com.dginc.deepak.retrofitexample;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  RecyclerViewAdapter adapter;
  List<SuperHero> dataList;
  BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        getData();
        recyclerView = findViewById(R.id.rvSuperHeros);
        adapter = new RecyclerViewAdapter(dataList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {

        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        Api api = retrofit.create(Api.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<SuperHero>> call = api.getSuperHeros();

        //then finallly we are making the call using enqueue()
        //it takes callback interface as an argument
        //and callback is having two methods onRespnose() and onFailure
        //if the request is successfull we will get the correct response and onResponse will be executed
        //if there is some error we will get inside the onFailure() method
        call.enqueue(new Callback<List<SuperHero>>() {
            @Override
            public void onResponse(Call<List<SuperHero>> call, Response<List<SuperHero>> response) {

                //In this point we got our hero list
                //thats damn easy right ;)
                List<SuperHero> heroList = response.body();
                adapter.setDataList(heroList);
                adapter.notifyDataSetChanged();

                //now we can do whatever we want with this list

            }

            @Override
            public void onFailure(Call<List<SuperHero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(receiver);
        unregisterNetworkChanges();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkBroadcastForNougat();
    }

    private void registerNetworkBroadcastForNougat() {
        br = new MyBroadCastReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(br);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }



}

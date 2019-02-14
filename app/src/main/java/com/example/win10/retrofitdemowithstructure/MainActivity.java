package com.example.win10.retrofitdemowithstructure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import com.example.win10.retrofitdemowithstructure.Api.AddonTrackApi;
import com.example.win10.retrofitdemowithstructure.listener.DataResponseListener;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDemo = findViewById(R.id.btnDemo);

        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddonTrackApi.getInstance().postExample("sydney@fife", "pistol", new DataResponseListener() {
                    @Override
                    public void onData_SuccessfulResponse(String stringResponse) {
                        Log.e("Post", stringResponse);
                    }

                    @Override
                    public void onData_FailureResponse(String errorResponse) {

                    }
                });
            }
        });

        AddonTrackApi.getInstance().getExample(new DataResponseListener() {
            @Override
            public void onData_SuccessfulResponse(String stringResponse) {
                Log.e("Res", stringResponse);
            }

            @Override
            public void onData_FailureResponse(String errorResponse) {

            }
        });
    }
}

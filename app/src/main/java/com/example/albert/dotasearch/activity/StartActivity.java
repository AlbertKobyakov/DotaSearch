package com.example.albert.dotasearch.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.viewModel.StartActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_start;
    private static final String TAG = "StartActivity";
    private static final String KEY = "C35559BD1BEA3B0DC2F958ADF8B7E484";

    public static AppDatabase db;
    private StartActivityViewModel viewModel;

    @BindView(R.id.loading)
    TextView textViewLoading;
    @BindView(R.id.btn_exit)
    Button btnExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        viewModel = ViewModelProviders.of(this).get(StartActivityViewModel.class);
        viewModel.getIsLoadMainActivity().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, aBoolean + " 44444444444444444444444");
                if (aBoolean) {
                    loadMainActivity(aBoolean);
                } else {
                    textViewLoading.setText("Интернет соединение отсутствует...");
                }
            }
        });
    }

    public void loadMainActivity(boolean isRun) {
        if (isRun) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel = null;
    }

    @OnClick(R.id.btn_exit)
    public void onClick(View view) {
        finish();
    }
}
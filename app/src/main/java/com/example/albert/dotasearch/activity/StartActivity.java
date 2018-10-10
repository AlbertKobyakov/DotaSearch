package com.example.albert.dotasearch.activity;

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

    public static AppDatabase db;
    private StartActivityViewModel viewModel;

    @BindView(R.id.loading)
    TextView textViewLoading;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        viewModel = ViewModelProviders.of(this).get(StartActivityViewModel.class);
        viewModel.getIsLoadMainActivity().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                loadMainActivity();
            } else {
                textViewLoading.setText(R.string.no_internet);
                //btnExit.setVisibility(View.VISIBLE);
                btnRefresh.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.refresh();
        btnExit.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        textViewLoading.setText(R.string.loading);
    }
}
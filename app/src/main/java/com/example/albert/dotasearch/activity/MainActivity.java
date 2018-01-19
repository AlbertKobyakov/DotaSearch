package com.example.albert.dotasearch.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentAdapter;
import com.example.albert.dotasearch.tabs.TabProPlayers;

import butterknife.ButterKnife;

import static com.example.albert.dotasearch.tabs.TabProPlayers.mAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    public Toolbar toolbar;
    private static final String TAG = "MainActivity";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initTabs();
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                Toast.makeText(MainActivity.this, position + " " + tab.getText().toString(), Toast.LENGTH_LONG).show();
                if(position == 1){
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.menu_main);

                    MenuItem item = toolbar.getMenu().findItem(R.id.menu_search);

                    SearchView searchView = (SearchView)item.getActionView();

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            mAdapter.filter(query);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if(mAdapter != null){
                                mAdapter.filter(newText);
                            }
                            return true;
                        }
                    });
                } else {
                    toolbar.getMenu().clear();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        //enable search to toolbar
        //toolbar.inflateMenu(R.menu.menu_main);
    }
}

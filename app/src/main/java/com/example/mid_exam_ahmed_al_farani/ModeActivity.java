package com.example.mid_exam_ahmed_al_farani;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ModeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button btnSetMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Mode Activity");
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnSetMode = findViewById(R.id.btnSetMode);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        String[] tabTitles = {"First", "Second", "Third"};

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();

        btnSetMode.setOnClickListener(this::showModePopup);

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        if (viewPager.getCurrentItem() != 0) {
                            viewPager.setCurrentItem(0, true);
                        } else {
                            finish();
                        }
                    }
                });
    }

    private void showModePopup(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view);

        popupMenu.getMenuInflater().inflate(
                R.menu.mode_menu,
                popupMenu.getMenu()
        );

        popupMenu.setOnMenuItemClickListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.mode_light) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
                return true;

            } else if (itemId == R.id.mode_night) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );
                return true;

            } else if (itemId == R.id.mode_system) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                );
                return true;
            }

            return false;
        });

        popupMenu.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

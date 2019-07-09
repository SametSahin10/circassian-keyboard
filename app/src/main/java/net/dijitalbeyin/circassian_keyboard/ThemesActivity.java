package net.dijitalbeyin.circassian_keyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ThemesActivity extends AppCompatActivity implements ThemeAdapter.OnThemeListener {
    private final static String LOG_TAG = ThemesActivity.class.getSimpleName();

    RecyclerView rw_themes;
    RecyclerView.Adapter themeAdapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;

    Theme[] themes = new Theme[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        rw_themes = findViewById(R.id.rw_themes);
        rw_themes.setHasFixedSize(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedThemeName = sharedPreferences.getString("selectedThemeName", "Green");

        int greenThemeImageResourceId = getResources().getIdentifier("green_theme_preview", "drawable", this.getPackageName());
        Theme greenTheme = new Theme("Green", greenThemeImageResourceId, false);
        int lightThemeImageResourceId = getResources().getIdentifier("green_theme_preview", "drawable", this.getPackageName());
        Theme lightTheme = new Theme("Light", lightThemeImageResourceId, false);
        int darkThemeImageResourceId = getResources().getIdentifier("green_theme_preview", "drawable", this.getPackageName());
        Theme darkTheme = new Theme("Dark", darkThemeImageResourceId, false);

        if (selectedThemeName.equals("Green")) {
            greenTheme.setActive(true);
        } else if (selectedThemeName.equals("Light")) {
            lightTheme.setActive(true);
        } else if (selectedThemeName.equals("Dark")) {
            lightTheme.setActive(true);
        } else {
            Log.e(LOG_TAG, "Unknown theme name");
        }

        themes[0] = greenTheme;
        themes[1] = lightTheme;
        themes[2] = darkTheme;

        themeAdapter = new ThemeAdapter(themes, this);
        rw_themes.setAdapter(themeAdapter);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rw_themes.setLayoutManager(layoutManager);
    }

    @Override
    public void onThemeClick(int position) {
        Theme currentTheme = themes[position];
        if (!currentTheme.isActive()) {
            String curentThemeName = currentTheme.getThemeName();
            for (int i = 0; i < themes.length; i++) {
                if (themes[i].getThemeName().equals(curentThemeName)) {
                    themes[i].setActive(true);
                } else {
                    themes[i].setActive(false);
                }
            }
            themeAdapter.notifyDataSetChanged();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedThemeName", curentThemeName);
            Log.d(LOG_TAG, "selected theme name: " + curentThemeName);
            editor.apply();
            stopService(new Intent(this, MyInputMethodService.class));
            startService(new Intent(this, MyInputMethodService.class));
        }
    }
}

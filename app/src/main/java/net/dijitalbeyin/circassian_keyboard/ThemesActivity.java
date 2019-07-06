package net.dijitalbeyin.circassian_keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ThemesActivity extends AppCompatActivity {
    RecyclerView rw_themes;
    RecyclerView.Adapter themeAdapter;
    RecyclerView.LayoutManager layoutManager;

    Theme[] themes = new Theme[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        rw_themes = findViewById(R.id.rw_themes);
        rw_themes.setHasFixedSize(true);

        int greenThemeImageResourceId = getResources().getIdentifier("green_theme_preview", "drawable", this.getPackageName());
        Theme greenTheme = new Theme("Green", greenThemeImageResourceId, true);
        themes[0] = greenTheme;
        themes[1] = greenTheme;
        themes[2] = greenTheme;

        themeAdapter = new ThemeAdapter(themes);
        rw_themes.setAdapter(themeAdapter);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rw_themes.setLayoutManager(layoutManager);
    }
}

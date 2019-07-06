package net.dijitalbeyin.circassian_keyboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeHolder> {
    private Theme[] themes;

    public static class ThemeHolder extends RecyclerView.ViewHolder {
        public ImageView iv_themePreviewImage;
        public Button btn_selectTheme;
        public ImageView iv_themeStatus;

        public ThemeHolder(@NonNull View itemView) {
            super(itemView);
            iv_themePreviewImage = itemView.findViewById(R.id.iv_themePreviewImage);
            btn_selectTheme = itemView.findViewById(R.id.btn_select_theme);
            iv_themeStatus = itemView.findViewById(R.id.iv_themeStatus);
        }
    }

    public ThemeAdapter(Theme[] themes) {
        this.themes = themes;
    }

    @NonNull
    @Override
    public ThemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_theme, parent, false);
        ThemeHolder themeHolder = new ThemeHolder(itemView);
        return themeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeHolder themeHolder, int position) {
        Theme currentTheme = themes[position];
        themeHolder.iv_themePreviewImage.setImageResource(currentTheme.getThemeImageResourceId());
        Log.d("TAG", "" + currentTheme.getThemeImageResourceId());
        if (currentTheme.isActive()) {
            themeHolder.iv_themeStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return themes.length;
    }
}

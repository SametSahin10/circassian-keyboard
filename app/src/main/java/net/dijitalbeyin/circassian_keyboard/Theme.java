package net.dijitalbeyin.circassian_keyboard;

public class Theme {
    private String themeName;
    private int themeImageResourceId;
    private boolean isActive;

    public Theme(String themeName, int themeImageResourceId, boolean isActive) {
        this.themeName = themeName;
        this.themeImageResourceId = themeImageResourceId;
        this.isActive = isActive;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemeImageResourceId() {
        return themeImageResourceId;
    }

    public void setThemeImageResourceId(int themeImageResourceId) {
        this.themeImageResourceId = themeImageResourceId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

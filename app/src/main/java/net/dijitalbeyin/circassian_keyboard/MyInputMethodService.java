package net.dijitalbeyin.circassian_keyboard;

import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private final static String LOG_TAG = MyInputMethodService.class.getSimpleName();

    private KeyboardView keyboardView;
    private Keyboard qwertyKeyboard;
    private Keyboard symbolsKeyboard;
    private SharedPreferences sharedPreferences;
    private boolean isJustStarted = true;
    private boolean isCaps = false;
    private boolean isAfterDot = false;

    @Override
    public View onCreateInputView() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedThemeName = sharedPreferences.getString("selectedThemeName", "Green");
        setKeyboardTheme(selectedThemeName);
        keyboardView.setOnKeyboardActionListener(this);
        shiftKeyboard();
        return keyboardView;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        setInputView(onCreateInputView());
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            playSound(primaryCode);
            switch (primaryCode) {
                case Keyboard.KEYCODE_MODE_CHANGE:
                    Keyboard currentKeyboard = keyboardView.getKeyboard();
                    if (currentKeyboard == qwertyKeyboard) {
                        currentKeyboard = symbolsKeyboard;
                    } else {
                        currentKeyboard = qwertyKeyboard;
                    }
                    keyboardView.setKeyboard(currentKeyboard);
                    break;
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    isCaps = !isCaps;
                    qwertyKeyboard.setShifted(isCaps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                default:
                    char code = (char) primaryCode;
                    if (Character.isLetter(code) && isCaps) {
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);
                    if (isJustStarted) {
                        unshiftKeyboard();
                        isJustStarted = false;
                    }
                    if (isAfterDot) {
                        unshiftKeyboard();
                    }
                    if (code == 46) {
                        isAfterDot = true;
                        inputConnection.commitText(" ", 1);
                        shiftKeyboard();
                    }
            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void playSound(int primaryCode) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (primaryCode) {
            case 32:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    private void shiftKeyboard() {
        qwertyKeyboard.setShifted(true);
        keyboardView.invalidateAllKeys();
        isCaps = true;
    }

    private void unshiftKeyboard() {
        if (isCaps) {
            qwertyKeyboard.setShifted(false);
            keyboardView.invalidateAllKeys();
            isCaps = false;
        }
    }

    private void setKeyboardTheme(String selectedThemeName) {
        if (selectedThemeName.equals("Green")) {
            keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_green_theme, null);
            qwertyKeyboard = new Keyboard(this, R.xml.keys_layout_green_theme);
            symbolsKeyboard = new Keyboard(this, R.xml.symbols_green_theme);
            keyboardView.setKeyboard(qwertyKeyboard);
        } else if (selectedThemeName.equals("Light")) {
            keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_light_theme, null);
            qwertyKeyboard = new Keyboard(this, R.xml.keys_layout_light_theme);
            symbolsKeyboard = new Keyboard(this, R.xml.symbols_light_theme);
            keyboardView.setKeyboard(qwertyKeyboard);
        } else if (selectedThemeName.equals("Dark")) {
            keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_dark_theme, null);
            qwertyKeyboard = new Keyboard(this, R.xml.keys_layout_dark_theme);
            symbolsKeyboard = new Keyboard(this, R.xml.symbols_dark_theme);
            keyboardView.setKeyboard(qwertyKeyboard);
        } else {
            keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_green_theme, null);
            qwertyKeyboard = new Keyboard(this, R.xml.keys_layout_green_theme);
            symbolsKeyboard = new Keyboard(this, R.xml.symbols_green_theme);
            keyboardView.setKeyboard(qwertyKeyboard);
            Log.e("TAG", "Unknown theme selection: " + selectedThemeName);
        }
    }
}

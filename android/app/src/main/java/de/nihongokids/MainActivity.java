package de.nihongokids;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Locale;

/**
 * Zeigt die Lern-App (index.html aus den Assets) in einer WebView.
 * Die Sprachausgabe kommt von der Android-Sprachausgabe (Japanisch).
 * Es gibt keinen Internetzugriff und es wird nichts nachgeladen.
 */
public class MainActivity extends Activity {

    private static final String START_URL = "file:///android_asset/index.html";

    private WebView web;
    private TextToSpeech tts;
    private boolean ttsReady = false;
    private boolean warned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#E63946"));

        web = new WebView(this);
        web.setBackgroundColor(Color.parseColor("#E63946"));
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setTextZoom(100);

        // Nur die eigene Seite darf angezeigt werden (Kinderschutz).
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !request.getUrl().toString().startsWith("file:///android_asset/");
            }
        });

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int r = tts.setLanguage(Locale.JAPAN);
                ttsReady = (r != TextToSpeech.LANG_MISSING_DATA && r != TextToSpeech.LANG_NOT_SUPPORTED);
                tts.setSpeechRate(0.8f);
            }
        });
        web.addJavascriptInterface(new Bridge(), "AndroidTTS");

        web.loadUrl(START_URL);
    }

    /** Wird von der Seite aufgerufen: AndroidTTS.speak("あいうえお") */
    private class Bridge {
        @JavascriptInterface
        public void speak(String text) {
            if (text == null || text.isEmpty()) return;
            if (ttsReady) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "nk");
            } else if (!warned) {
                warned = true;
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Kein japanischer Sprecher gefunden. Bitte in den Einstellungen unter Sprachausgabe Japanisch installieren.",
                        Toast.LENGTH_LONG).show());
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        // Erst die App selbst zurückgehen lassen (Menü -> Start), dann beenden.
        web.evaluateJavascript("(window.appBack ? window.appBack() : false)", value -> {
            if (!"true".equals(value)) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.onPause();
        if (tts != null) tts.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (web != null) web.destroy();
        super.onDestroy();
    }
}

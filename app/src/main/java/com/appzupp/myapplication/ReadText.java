package com.appzupp.myapplication;

import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.Locale;

public class ReadText implements Runnable {
    @Override
    public void run() {
        Intent rec = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        rec.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
        startActivityForResult(rec, 10);
    }

    private void startActivityForResult(Intent rec, int i) {
    }
}

package com.appzupp.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CopyText implements Runnable {
    String txt;
    Context ctx;
    public CopyText(String txt, Context ctx) {
        this.txt =txt;
        this.ctx=ctx;
    }

    @Override
    public void run() {
        ClipboardManager clipboard= (ClipboardManager) this.ctx.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip= ClipData.newPlainText("EditText",this.txt);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(ctx, this.txt+ " is copied", Toast.LENGTH_LONG).show();
    }
}

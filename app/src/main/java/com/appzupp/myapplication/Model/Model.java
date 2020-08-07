package com.appzupp.myapplication.Model;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.appzupp.myapplication.CopyText;
import com.appzupp.myapplication.R;

public class Model{
    Context ctx;
    TextView txt;
    EditText etx;

    public Model(Context ctx,TextView txt, EditText etx){
    this.ctx=ctx;
    this.txt=txt;
    this.etx=etx;
    }

    public void  copyText(){

        CopyText tx= new CopyText(this.txt.toString(),this.ctx);
        tx.run();
    }
    public void showText() {
       if(txt.getVisibility()==View.VISIBLE)
           txt.setVisibility(View.INVISIBLE);
       if(etx.getVisibility()==View.INVISIBLE)
           etx.setVisibility(View.VISIBLE);
        etx.setText(txt.getText().toString());

    }
    public void clearText() {

        if(txt!=null)
            txt.setText("");
    }
    public void shareText(){

        ApplicationInfo api= ctx.getApplicationInfo();
        String apipath= api.sourceDir;
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,txt.getText().toString());
        Intent shareIntent = Intent.createChooser(intent, null);
        ctx.startActivity(shareIntent);


    }
    public void openCal(){

        long startMillis = System.currentTimeMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
        this.ctx.startActivity(intent);
    }
}

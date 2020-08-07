package com.appzupp.myapplication;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.appzupp.myapplication.Model.MyListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener,Animator.AnimatorListener{
    private static final String KEY_TEXT_VALUE = "textValue";
    private static final String KEY_VISIBLE_TRUE = "false";
    private static final String KEY_ETX_TRUE = "false";
    private static final String TAG = "MainActivity";

    TextToSpeech tts;
    TextView text ;
    CharSequence  visible;
    CharSequence savedText;


    FloatingActionButton btnRead;
    FloatingActionButton btnCopy;
    FloatingActionButton btnCal;
    FloatingActionButton btnSearch;
    FloatingActionButton btnEdit ;
    FloatingActionButton btnDel;
    FloatingActionButton btnRec ;
    FloatingActionButton btnShare ;
    FloatingActionButton btnClr ;
    FloatingActionButton btnOpen;
    StringBuilder stringBuilder = new StringBuilder();
    Locale loc;
    int count=0;


    final Float translationY=100f;
    FloatingActionButton  btnAdd;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    boolean isMenuOpen =false;
    boolean isMain=false;
    boolean showMain=false;
    boolean showMenu=false;
    boolean recording=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loc=Locale.getDefault();

        if(savedInstanceState!=null){

                 savedText = savedInstanceState.getCharSequence(KEY_TEXT_VALUE);

                  visible=savedInstanceState.getCharSequence(KEY_ETX_TRUE);


        }
        langs=new ArrayList<>();
        langs.add("Tamil");
        langs.add("French");
        langs.add("English");
        langs.add("Chinese");
        langs.add("German");
        langs.add("Spanish");
        langs.add("Jananese");
        langs.add("Russian");

try {

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setExpanded(true);
    fab.show();
    if(savedInstanceState==null){
    this.fadeAnimation(findViewById(R.id.fab));
    this.scaleAnimation(findViewById(R.id.fab));
    }
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideTexts();
            recording=true;
            setTextFromEdit();

            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //   .setAction("Action", null).show();
            Intent rec = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE,loc);
            rec.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
            initEditMenu();
            closeMenu();
            closeMain();


            try {
                startActivityForResult(rec, 10);
            } catch (ActivityNotFoundException c) {
                Snackbar.make(view, "The Device is not supported", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        }
    });

}catch(Exception e){
    Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
}
/*
if(findViewById(R.id.FirstFragment)!=null){
    if(savedInstanceState!=null){
        return;
    }else{
        FirstFragment ff=new FirstFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction().add(ff,null);
    }

}*/
    }
    public void hideTexts(){
        TextView txt=findViewById(R.id.t1);

        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        txt=findViewById(R.id.t2);

        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        txt=findViewById(R.id.t3);

        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        txt=findViewById(R.id.t4);

        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        txt=findViewById(R.id.t5);

        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        txt=findViewById(R.id.t6);
        ;
        txt.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    void initEditMenu(){
        isMenuOpen=false;
         btnRead = findViewById(R.id.btnRead);
         btnCopy= findViewById(R.id.btnCopy);
         btnCal = findViewById(R.id.btnCalendar);
         btnSearch = findViewById(R.id.btnSearch);
         btnEdit = findViewById(R.id.btnEdit);
         btnDel = findViewById(R.id.btnDel);
         btnRec = findViewById(R.id.btnRec);
         btnShare = findViewById(R.id.btnShare);
         btnAdd=findViewById(R.id.btnAppend);
        btnClr=findViewById(R.id.btnClr);
        btnOpen=findViewById(R.id.btnOpen);
//set invisible


        btnEdit.setAlpha(0f);
        btnDel.setAlpha(0f);
        btnRec.setAlpha(0f);
        btnRead.setAlpha(0f);
        btnCopy.setAlpha(0f);
        btnCal.setAlpha(0f);
        btnSearch.setAlpha(0f);
        btnShare.setAlpha(0f);
        btnClr.setAlpha(0f);

        btnEdit.setTranslationY(translationY);
        btnDel.setTranslationY(translationY);
        btnRec.setTranslationY(translationY);

        btnRead.setTranslationY(translationY);
        btnCopy.setTranslationY(translationY);
        btnCal.setTranslationY(translationY);
        btnSearch.setTranslationY(translationY);
        btnShare.setTranslationY(translationY);
        btnClr.setTranslationY(translationY);

        btnRead.setOnClickListener(this);
        btnRec.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnCal.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnClr.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        text=findViewById(R.id.txtResult);
        setTextFromEdit();
        switch (view.getId()){
            case R.id.btnOpen:
                if(stringBuilder.length()>0) {
                openItems();
                }else{
                    Toast.makeText(this, "Record text to Share", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAppend: //plus
                if(stringBuilder.length()>0) {
                    appendText();
                }else{
                    Toast.makeText(this, "Record text to edit", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnRead:
                if(text!=null && stringBuilder.length()>0 && showMain)
                    readText(view);
                    isMain=true;
                    openItems();
                break;
            case R.id.btnCopy:
                if(text!=null && stringBuilder.length()>0 && showMain)
                    copyText();
                    isMain=true;
                    openItems();
                break;
            case R.id.btnSearch:
                if(text!=null && stringBuilder.length()>0 && showMain)
                    searchText(view);
                break;
            case R.id.btnCalendar:
                if(text!=null && stringBuilder.length()>0 && showMain)
                    copyText();
                    openCal();
                break;
            case R.id.btnEdit:
                if(text!=null && stringBuilder.length()>0 &&showMenu)
                    showText();
                break;
            case R.id.btnDel:
                if(text!=null && stringBuilder.length()>0 && showMenu)
                    deleteText();
                break;
            case R.id.btnShare:
                if(text!=null  && stringBuilder.length()>0 && showMain)
                    shareText();
                break;
            case R.id.btnRec:
                if((text!=null)&& showMenu)
                    stringBuilder.append(' ').append('\n');
                     text.setText(stringBuilder.toString());
                break;
            case R.id.btnClr:
                if(text!=null && stringBuilder.length()>0 && showMain){
                    clearText();}
                break;


            default:
            throw new IllegalStateException("Unexpected value: " + view.getId());
    }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);



            // Checks whether a hardware keyboard is available
            if (newConfig.keyboardHidden==Configuration.KEYBOARDHIDDEN_NO) {
                Toast.makeText(ctx, "Keyboard open", Toast.LENGTH_SHORT).show();
            } else if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
                Toast.makeText(ctx, "Keyboard open", Toast.LENGTH_SHORT).show();
                text.setVisibility(View.VISIBLE);
                text.setText(etx.getText().toString());
                etx.setVisibility(View.INVISIBLE);

            }

    }

    private void reRecText(View view) {
        Intent rec = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        rec.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");


        try {

            startActivityForResult(rec, 20);
        } catch (ActivityNotFoundException c) {
            Snackbar.make(view, "The Device is not supported", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }

    private void deleteText() {
        int i;
        try{
        for (i = stringBuilder.length(); i >0 ; i--) {

            // get char at position i
            char ch = stringBuilder.charAt(i-1);
            if (ch==' ') {
                if (i - 1 != stringBuilder.length()-1) {
                    break;
                }
            }
        }

          stringBuilder.delete(i-1,stringBuilder.length());


        text= findViewById(R.id.txtResult);
        text.setText(stringBuilder.toString());
        }catch(StringIndexOutOfBoundsException e){
            clearText();
        }
    }


    private void openItems(){

        if (isMain) {
            closeMain();
            showMain=false;
        } else {
            openMain();
            showMain=true;
        }

    }
    private  void appendText(){

        if (isMenuOpen) {
           closeMenu();
           showMenu=false;
        } else {
           openMenu();
           showMenu=true;
        }

    }

    private  void openMain() {
        isMain = !isMain;


        btnOpen.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        btnShare.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnSearch.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnClr.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnCopy.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnCal.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnRead.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();



    }

    private  void closeMain() {
        isMain=!isMain;

        btnOpen.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        btnShare.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnSearch.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnRead.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnCal.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnCopy.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnClr.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;


        btnAdd.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        btnEdit.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnDel.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnRec.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }

    private  void closeMenu() {
        isMenuOpen = !isMenuOpen;

        btnAdd.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        btnEdit.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnDel.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnRec.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TEXT_VALUE,stringBuilder.toString());


        if(etx.getVisibility()==View.VISIBLE)
            outState.putCharSequence(KEY_ETX_TRUE,"true");
        else
            outState.putCharSequence(KEY_ETX_TRUE,"false");


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }
public void setTextFromEdit(){

    text=findViewById(R.id.txtResult);
    etx=findViewById(R.id.editText);
    if(etx.getVisibility()==View.VISIBLE){
    text.setVisibility(View.VISIBLE);
    text.setText(etx.getText().toString());
    etx.setVisibility(View.INVISIBLE);
    stringBuilder.delete(0,stringBuilder.length());
    stringBuilder.append(etx.getText().toString()).append(' ');
    etx.setText("");
    }
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();

        setTextFromEdit();

        if(savedText!=null &&savedText.length()>0 && !recording) {
            hideTexts();
            makeVisible();
            text = findViewById(R.id.txtResult);
            text.setText(savedText);
            count=1;
            stringBuilder.append(savedText).append(' ');


            initEditMenu();
            if(!isMain)
                isMain=true;
            if(!isMenuOpen)
                isMenuOpen=true;
            openItems();
            appendText();

            if(visible.equals("true")){
                showText();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            setTextFromEdit();

            switch (requestCode) {
                case 10: {
                    if (resultCode == RESULT_OK && null != data) {

                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        stringBuilder.append(result.get(0)).append(' ');
                        text=findViewById(R.id.txtResult);
                        text.setText(stringBuilder.toString());
                        makeVisible();
                        if(isMain)
                        isMain=false;
                        if(isMenuOpen)
                        isMenuOpen=false;
                        count++;
                        
                    }
                    break;

                }


                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }

    public void makeVisible(){
        if(text.getVisibility()== View.INVISIBLE)
            text.setVisibility(View.VISIBLE);
      
    }




    ArrayList<String> langs;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //TODO write checkable menu items for languages
        //app:actionViewClass="android.widget.CheckBox"
        //menuItem.setChecked(true/false);
        //menuItem.isChecked()
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search =menu.findItem(R.id.app_bar_search);
        SearchView searchView   =(SearchView) search.getActionView();
        searchView.setQueryHint("Search Language");
       // searchView.setOnQueryTextListener(this);
       // final ListView lang=findViewById(R.id.langlist);

       // adapter= new MyListAdapter(this,langs);

        return true;
    }

    MyListAdapter adapter;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO write checkable menu items for languages
        //https://developer.android.com/training/data-storage/app-specific#internal-access-files
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sinhala:

                return true;
            case R.id.action_settings:

                return true;
            case R.id.app_bar_search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void searchText(View view){

      // Button search =(Button) findViewById(R.id.button_first);
     //  search.callOnClick();
       // SearchView searchView = (SearchView)search;
        try {
            text=(TextView)findViewById(R.id.txtResult);
            CharSequence input= text.getText().toString();
            String download_link="http://www.google.com/search?q="+input;
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(download_link));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
            }



    Context ctx;
public void readText(View view){

    ctx=this.getApplicationContext();



    new Thread(new Runnable() {
        @Override
        public void run() {

            tts=new TextToSpeech(ctx, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(Locale.getDefault());
                            if (!(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)) {
                                tts.speak(stringBuilder.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                            }


                        }
                    }
                });
        }
                }).start();




    }
    public void  copyText(){
        text=findViewById(R.id.txtResult);
        CopyText tx= new CopyText(text.getText().toString(),getApplicationContext());
        tx.run();
    }

    EditText etx;

    public void showText() {
        if(text.getVisibility()==View.VISIBLE)
            text.setVisibility(View.INVISIBLE);
        etx=findViewById(R.id.editText);
        if(etx.getVisibility()==View.INVISIBLE)
        etx.setVisibility(View.VISIBLE);
        etx.setText(text.getText().toString());

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    public void clearText() {

        if (stringBuilder.length() > 0)
            text = findViewById(R.id.txtResult);
        stringBuilder.delete(0, stringBuilder.length());
        text.setText("");
        text.setVisibility(View.INVISIBLE);
        if (isMain) {
            openItems();
        }
        if (showMenu){
            isMenuOpen=true;
            appendText();
    }
    }
    public void shareText(){
        text=findViewById(R.id.txtResult);
        ApplicationInfo api= this.getApplicationContext().getApplicationInfo();
        String apipath= api.sourceDir;
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text.getText().toString());
        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);


    }
    public void openCal(){

        long startMillis = System.currentTimeMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
       startActivity(intent);
    }


public void fadeAnimation(View view){
        //Animator fadeAnimator= AnimatorInflater.loadAnimator(this,R.animator.alpha);
       // fadeAnimator.setTarget(view);
      //  fadeAnimator.start();
    Animator colAnimator=ObjectAnimator.ofFloat(view,"translationY", -500f,0f,-400f,0f,-300f,0f,-200f,0f,-100f,0f,-50f,0f);
    colAnimator.setDuration(6000);

    colAnimator.start();


}
    public void scaleAnimation(View view){
        Animator scaleAnimator= AnimatorInflater.loadAnimator(this,R.animator.scale);
        scaleAnimator.setTarget(view);
        scaleAnimator.start();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}



package com.example.jokegenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView generatedJokeTV;
    SharedPreferences preferences;
    List<String> jokeSet;
    private static final String genJokeKey = "generated_joke";
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getJokesFromPref();
        generatedJokeTV = findViewById(R.id.generatedJoke);
        generatedJokeTV.setText(R.string.test_string);
        getString(genJokeKey);
    }

    private String getString(String key){
        SharedPreferences genJokePrefs = getApplicationContext().getSharedPreferences("genJokePrefs", android.content.Context.MODE_PRIVATE);
        return genJokePrefs.getString(key, String.valueOf(R.string.test_string));
    }

    private void saveString(String key, String value){
        SharedPreferences genJokePrefs = getApplicationContext().getSharedPreferences("genJokePrefs", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = genJokePrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()== R.id.menu_item_settings){
            goToSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void getJokesFromPref(){
        jokeSet = new ArrayList<>();
        if(preferences.getBoolean(getString(R.string.dad_joke_pref_key), true)){
            for(String joke : JokeDB.dadJokes){
                jokeSet.add(joke);}
        }
        if(preferences.getBoolean(getString(R.string.anti_joke_pref_key), true)){
            for(String joke : JokeDB.antiJokes){
            jokeSet.add(joke);}
        }
        if(preferences.getBoolean(getString(R.string.oneliner_pref_key), true)){
            for(String joke: JokeDB.oneLiners){
            jokeSet.add(joke);}
        }



    }

   public void onClick(View view){
        int randomJokePosition = (int)(Math.random()*jokeSet.size());
        generatedJokeTV.setText(jokeSet.get(randomJokePosition));

        saveString(genJokeKey, (String) generatedJokeTV.getText());
    }
}
package com.example.notes2_lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    TextView message;
    SharedPreferences sharedPreferences;
    DBHelper dbHelper;

    private static final String SHARED_PREF_NAME = "notes2_lab5";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";

    public static ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        message = (TextView) findViewById(R.id.message);
        Intent intent = getIntent();
        String name = intent.getStringExtra("message");
        name = sharedPreferences.getString(KEY_NAME, null);
        message.setText("Welcome " + name + '!');

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        dbHelper = new DBHelper(sqLiteDatabase);

        notes = dbHelper.readNotes(sharedPreferences.getString(KEY_NAME, null));

        ArrayList<String> displayNotes = new ArrayList<>();

        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent (getApplicationContext(), AddNote.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater()   ;
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                goToMain();
                return true;

            case R.id.addNote:
                goToEditNote();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
    private void goToEditNote() {
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }
    private void goToMain() {

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        finish();

        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
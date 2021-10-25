package com.example.notes2_lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity {
    Button saveButton;
    EditText noteText;

    private static final String SHARED_PREF_NAME = "notes2_lab5";
    private static final String KEY_NAME = "username";

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteText = findViewById(R.id.noteTxt);
        Intent intent = getIntent();

        int s = intent.getIntExtra("noteid", -1);
        noteid = s;

        if (noteid!= -1) {
            Note note = HomePage.notes.get(noteid);
            String noteContent = note.getContent();
            noteText.setText(noteContent);
        }

        saveButton = findViewById(R.id.buttonSave);

    }

    public void saveMethod(View view) {
        String msg = noteText.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String username = sharedPreferences.getString(KEY_NAME, null);
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (HomePage.notes.size() + 1);
            dbHelper.saveNotes(username, title, msg, date);
        } else {
            title = "NOTE_" + (noteid+1);
            dbHelper.updateNote(title, date, msg, username);
        }

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);



    }
}
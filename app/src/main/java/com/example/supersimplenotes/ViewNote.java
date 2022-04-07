package com.example.supersimplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewNote extends AppCompatActivity {
    private int get_nid;
    private TextView tv_note_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        get_nid = getIntent().getIntExtra("note_id",0);
        tv_note_full = findViewById(R.id.note_viewer);

        Note_Database ndb = new Note_Database(this);

        tv_note_full.setText(ndb.get_note_contents(get_nid + ""));
    }
}
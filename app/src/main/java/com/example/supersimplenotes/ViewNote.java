package com.example.supersimplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewNote extends AppCompatActivity implements View.OnClickListener {
    private int get_nid;
    private Button btn_edit, btn_del;
    private TextView tv_note_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        get_nid = getIntent().getIntExtra("note_id",0);
        tv_note_full = findViewById(R.id.note_viewer);
        btn_edit = findViewById(R.id.btn_edit_note);
        btn_del = findViewById(R.id.btn_delete_note);

        Note_Database ndb = new Note_Database(this);

        tv_note_full.setText(ndb.get_note_contents(get_nid + ""));

        btn_edit.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit_note){
            Intent edit_note_i = new Intent(getApplicationContext(),EditNote.class);

            edit_note_i.putExtra("note2edit", get_nid);

            startActivity(edit_note_i);
        }
        else if(view.getId() == R.id.btn_delete_note){
            // TODO: add delete current/all notes feature later.
            Toast.makeText(getApplicationContext(),"Feature not implemented",Toast.LENGTH_LONG).show();
        }
    }
}
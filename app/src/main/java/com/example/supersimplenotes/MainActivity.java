package com.example.supersimplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_new, btn_del;
    private ListView lv_note_listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_new = findViewById(R.id.btn_new_note);
        btn_del = findViewById(R.id.btn_delete_all);
        lv_note_listing = findViewById(R.id.list_notes);

        btn_new.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        Note_Database ndb = new Note_Database(this);

        List<Note> notes_list_import = ndb.list_notes();

        String[] nli_arr = new String[notes_list_import.size()];

        for(int r = 0; r < notes_list_import.size(); r++){
            nli_arr[r] = notes_list_import.get(r).getContents();
        }

        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nli_arr);

        lv_note_listing.setAdapter(aa);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_new_note){
            Intent i = new Intent(this,EditNote.class);

            startActivity(i);
        }
        if(view.getId()==R.id.btn_delete_all){
            Toast.makeText(getApplicationContext(),"Not implemented yet.",Toast.LENGTH_LONG).show();
        }
    }
}
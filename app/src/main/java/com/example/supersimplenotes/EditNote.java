package com.example.supersimplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNote extends AppCompatActivity {
    private Button btn_save;
    private EditText et_note_compose;
    private int id_note = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        et_note_compose = findViewById(R.id.note_editor);
        btn_save = findViewById(R.id.btn_save_note);

        Note_Database ndb = new Note_Database(this);

        // If the user came from a ViewNote activity while viewing a note, fetch its ID and fill the text area with the contents of the note.
        id_note = getIntent().getIntExtra("note2edit",0);
        if(id_note != -1)
            et_note_compose.setText(ndb.get_note_contents(String.valueOf(id_note)));

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Note n = new Note();

                n.setContents(et_note_compose.getText().toString());

                // Checks if the note is a new one or if the user is editing an existing one.
                // TODO: fix update feature since app crashes.
                if(id_note == -1)
                    ndb.register_data(n);
                else ndb.update_note(String.valueOf(id_note), String.valueOf(n));

                Toast.makeText(getApplicationContext(),"Saved note",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
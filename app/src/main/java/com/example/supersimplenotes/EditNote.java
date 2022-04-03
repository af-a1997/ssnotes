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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        et_note_compose = findViewById(R.id.note_editor);
        btn_save = findViewById(R.id.btn_save_note);

        Note_Database ndb = new Note_Database(this);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Note n = new Note();

                n.setContents(et_note_compose.getText().toString());

                ndb.register_data(n);

                Toast.makeText(getApplicationContext(),"Saved note",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
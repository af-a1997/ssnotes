package com.example.supersimplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_new, btn_del;
    private ListView lv_note_listing;
    private TextView tv_curr_datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_new = findViewById(R.id.btn_new_note);
        btn_del = findViewById(R.id.btn_delete_all);
        lv_note_listing = findViewById(R.id.list_notes);
        tv_curr_datetime = findViewById(R.id.wsapi_datetime);

        btn_new.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        Note_Database ndb = new Note_Database(this);

        List<Note> notes_list_import = ndb.list_notes();

        String[] nli_arr = new String[notes_list_import.size()];

        // Gets the first letters from stored notes to show as preview, if the note is longer than 20 characters, cut it off at that and add three dots to note there's more content in the note.
        for(int r = 0; r < notes_list_import.size(); r++){
            if(notes_list_import.get(r).getContents().length() >20)
               nli_arr[r] = notes_list_import.get(r).getContents().substring(0,20) + " ...";

            else nli_arr[r] = notes_list_import.get(r).getContents();
        }

        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nli_arr);

        // Sets an event to do when clicking an item on the list to view the contents of the note.
        lv_note_listing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note n = notes_list_import.get(i);

                int nid = n.getIdno();

                Intent intent_note = new Intent(getApplicationContext(),ViewNote.class);
                intent_note.putExtra("note_id",nid);

                startActivity(intent_note);
            }
        });

        lv_note_listing.setAdapter(aa);

        // API details: http://worldtimeapi.org/
        String api_url = "https://worldtimeapi.org/api/timezone/America/Montevideo";
        GetCurrentTime gct = new GetCurrentTime();
        gct.execute(api_url);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_new_note){
            Intent i = new Intent(this,EditNote.class);

            i.putExtra("note2edit",-1);

            startActivity(i);
        }
        if(view.getId()==R.id.btn_delete_all){
            Toast.makeText(getApplicationContext(),"Not implemented yet.",Toast.LENGTH_LONG).show();
        }
    }

    class GetCurrentTime extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer sb = new StringBuffer();

            try{
                URL addr = new URL(strings[0]);
                HttpsURLConnection conn = (HttpsURLConnection) addr.openConnection();
                InputStream v_is = conn.getInputStream();
                InputStreamReader v_isr = new InputStreamReader(v_is);
                BufferedReader v_bufferedreader = new BufferedReader(v_isr);
                String lin;

                while((lin=v_bufferedreader.readLine())!=null){
                    sb.append(lin);
                }

            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                // Having one heck of a rough time trying to figure out how to properly format data, I've decided I'll just retrieve the [datetime] value from the API response and chop out any other bit of info using substring() and two auxiliary variables. Also, self-reminder to always add the internet connection permission to the manifest each time I want to consume a Web Service...

                JSONObject json_out_timeinfo = new JSONObject(s);
                String str_datetime = json_out_timeinfo.getString("datetime").trim();

                String str_date, str_time = "";
                str_date = str_datetime.substring(0,10);
                str_time = str_datetime.substring(11,19);
                str_datetime = "The time is: " + str_date + " " + str_time;

                System.out.println(str_datetime);

                tv_curr_datetime.setText(str_datetime);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }
}
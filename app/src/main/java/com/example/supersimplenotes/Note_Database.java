package com.example.supersimplenotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Note_Database extends SQLiteOpenHelper {
    public static final String nametag = "sql";
    public static final String file_ddbb = "notes_reg.sqlite";
    public static final int version_no = 1;

    public Note_Database(Context c) {
        super(c, file_ddbb, null, version_no);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(nametag,"Creating DDBB...");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes (idno INTEGER PRIMARY KEY AUTOINCREMENT, contents TEXT);");
        Log.d(nametag,"DDBB was created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Won't fix: implement onUpgrade() code.
    }

    public long register_data(Note note_data){
        long idno = note_data.getIdno();
        SQLiteDatabase ddbb = getWritableDatabase();
        try{
            ContentValues cvs = new ContentValues();

            cvs.put("contents",note_data.getContents());

            if(idno!=0){
                // UPDATE

                String id2s = String.valueOf(note_data.getIdno());
                // Array for WHERE conditionals.
                String[] where_conditionals = new String[]{id2s};

                int update_reg = ddbb.update("notes",cvs,"idno=?",where_conditionals);

                return update_reg;
            }
            else{
                // INSERT

                idno = ddbb.insert("notes","",cvs);

                return idno;
            }
        }
        finally {
            ddbb.close();
        }
    }

    public List<Note> list_notes(){
        SQLiteDatabase ddbb_ro = getReadableDatabase();

        try{
            Cursor crs = ddbb_ro.rawQuery("SELECT * FROM notes;",null);
            return return_list(crs);
        }
        finally{
            ddbb_ro.close();
        }
    }

    @SuppressLint("Range")
    public String get_note_contents(String p_idno){
        SQLiteDatabase ddbb_ro = getReadableDatabase();

        try{
            Cursor crs = ddbb_ro.rawQuery("SELECT * FROM notes WHERE idno=" + p_idno +";",null);

            if(crs.moveToFirst())
                return crs.getString(crs.getColumnIndex("contents"));

            return null;
        }
        finally{
            ddbb_ro.close();
        }
    }

    // TODO: fix update note feature.
    @SuppressLint("Range")
    public void update_note(String p_idno, String p_newcont){
        SQLiteDatabase ddbb_ro = getReadableDatabase();

        try{
            ddbb_ro.rawQuery("UPDATE notes SET contents=" + p_newcont +" WHERE idno=" + p_idno +";",null);
        }
        finally{
            ddbb_ro.close();
        }
    }

    @SuppressLint("range")
    public List<Note> return_list(Cursor i_crs){
        List<Note> notes_listing = new ArrayList<Note>();

        if(i_crs.moveToNext()){
            do{
                Note note_obj = new Note();

                note_obj.setIdno(i_crs.getInt((i_crs.getColumnIndex("idno"))));
                note_obj.setContents(i_crs.getString((i_crs.getColumnIndex("contents"))));

                notes_listing.add(note_obj);
            }while (i_crs.moveToNext());
        }

        return notes_listing;
    }
}

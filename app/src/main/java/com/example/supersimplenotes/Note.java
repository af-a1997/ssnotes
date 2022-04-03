package com.example.supersimplenotes;

import java.io.Serializable;

public class Note implements Serializable {
    private int idno;
    private String contents;

    public Note() {
        this.idno = idno;
        this.contents = contents;
    }

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
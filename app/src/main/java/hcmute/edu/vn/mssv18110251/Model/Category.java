package hcmute.edu.vn.mssv18110251.Model;

import android.database.Cursor;

public class Category {
    private int id;
    private String name;

    public Category() {
    }

    public Category(final String name) {
        this.name = name;
    }

    public Category(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

package hcmute.edu.vn.mssv18110251.Model;

import android.database.Cursor;

public class Category {
    private int id;
    private String name;
    private byte[] image;



    public Category(final String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Category(final int id, final String name, final byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Category(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public Category(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id_category"));
        this.name = cursor.getString(cursor.getColumnIndex("name_category"));
        this.image = cursor.getBlob(cursor.getColumnIndex("image_category"));
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

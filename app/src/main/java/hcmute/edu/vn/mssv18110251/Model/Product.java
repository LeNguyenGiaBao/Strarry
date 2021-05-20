package hcmute.edu.vn.mssv18110251.Model;

import android.database.Cursor;

public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private byte[] image;
    private int id_category;

    public Product(String name, String description, int price, int quantity, byte[] image, int id_category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.id_category = id_category;
    }

    public Product(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id_product"));
        this.id_category = cursor.getInt(cursor.getColumnIndex("id_category"));
        this.name = cursor.getString(cursor.getColumnIndex("name_product"));
        this.image = cursor.getBlob(cursor.getColumnIndex("image_product"));
        this.price = cursor.getInt(cursor.getColumnIndex("price_product"));
        this.quantity = cursor.getInt(cursor.getColumnIndex("quantity_product"));
        this.description = cursor.getString(cursor.getColumnIndex("description_product"));
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

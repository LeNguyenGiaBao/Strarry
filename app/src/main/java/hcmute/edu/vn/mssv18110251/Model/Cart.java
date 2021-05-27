package hcmute.edu.vn.mssv18110251.Model;

import android.database.Cursor;

public class Cart {
    private int id;
    private int id_account;
    private int id_product;
    private int amount;

    public Cart(int id_account, int id_product, int amount) {
        this.id_account = id_account;
        this.id_product = id_product;
        this.amount = amount;
    }

    public Cart(int id, int id_account, int id_product, int amount) {
        this.id = id;
        this.id_account = id_account;
        this.id_product = id_product;
        this.amount = amount;
    }

    public Cart(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("id_cart"));
        this.id_account = cursor.getInt(cursor.getColumnIndex("id_account"));
        this.id_product = cursor.getInt(cursor.getColumnIndex("id_product"));
        this.amount = cursor.getInt(cursor.getColumnIndex("amount_product"));
    }

    public static Cart fromCursor(Cursor cursor){
        Cart cart = new Cart(cursor);
        return  cart;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

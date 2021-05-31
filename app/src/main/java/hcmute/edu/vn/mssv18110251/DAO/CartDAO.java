package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class CartDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_CART,
            DatabaseHelper.COLUMN_ID_ACCOUNT_CART,
            DatabaseHelper.COLUMN_ID_PRODUCT_CART,
            DatabaseHelper.COLUMN_AMOUNT_PRODUCT_CART};

    public CartDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean add_to_cart(Cart cart){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_ACCOUNT_CART, cart.getId_account());
        values.put(DatabaseHelper.COLUMN_ID_PRODUCT_CART, cart.getId_product());
        values.put(DatabaseHelper.COLUMN_AMOUNT_PRODUCT_CART, cart.getAmount());
        long success = database.insert(DatabaseHelper.TABLE_CART, null, values);
        if(success!=-1){
            return true;
        }
        return false;
    }


    public List<Cart> getCart(int id_account){
        List<Cart> carts = new ArrayList<Cart>();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CART + " where " + DatabaseHelper.COLUMN_ID_ACCOUNT_CART + " = " + id_account;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Cart cart = new Cart(cursor);
                carts.add(cart);
            }while (cursor.moveToNext());
        }
        return carts;
    }

    public Cursor getInfoCart(int id_account){
        String query = "SELECT cart.id_cart as id_cart, cart.id_product as id_product, product.name_product as name_product, product.price_product as price_product, product.image_product as image_product, cart.amount_product as amount_product from Cart, Product where cart.id_product = product.id_product and cart.id_account = " + id_account;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public void remove(Cart cart){
        this.open();
        database.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.COLUMN_ID_CART +"=?", new String[]{String.valueOf(cart.getId())});
    }

    public boolean update(Cart cart){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_ACCOUNT_CART, cart.getId_account());
        values.put(DatabaseHelper.COLUMN_ID_PRODUCT_CART, cart.getId_product());
        values.put(DatabaseHelper.COLUMN_AMOUNT_PRODUCT_CART, cart.getAmount());
        long success =  database.update(DatabaseHelper.TABLE_CART, values, DatabaseHelper.COLUMN_ID_CART + "=?", new String[]{String.valueOf(cart.getId())});
        Log.d("CartDAO", String.valueOf(success));
        if(success!=-1){
            return true;
        }
        return false;
    }
}

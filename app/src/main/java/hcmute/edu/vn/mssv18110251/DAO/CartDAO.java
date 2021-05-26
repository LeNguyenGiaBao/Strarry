package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Cart;

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
}

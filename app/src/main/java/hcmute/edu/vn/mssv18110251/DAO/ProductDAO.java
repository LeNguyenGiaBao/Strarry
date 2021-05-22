package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class ProductDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_PRODUCT,
            DatabaseHelper.COLUMN_NAME_PRODUCT,
            DatabaseHelper.COLUMN_DESCRIPTION_PRODUCT,
            DatabaseHelper.COLUMN_PRICE_PRODUCT,
            DatabaseHelper.COLUMN_QUANTITY_PRODUCT,
            DatabaseHelper.COLUMN_IMAGE_PRODUCT,
            DatabaseHelper.COLUMN_ID_CATEGORY_PRODUCT};

    public ProductDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean addProduct(Product product){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_PRODUCT, product.getName());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION_PRODUCT, product.getDescription());
        values.put(DatabaseHelper.COLUMN_PRICE_PRODUCT, product.getPrice());
        values.put(DatabaseHelper.COLUMN_QUANTITY_PRODUCT, product.getQuantity());
        values.put(DatabaseHelper.COLUMN_IMAGE_PRODUCT, product.getImage());
        values.put(DatabaseHelper.COLUMN_ID_CATEGORY, product.getId_category());
        long success = database.insert(DatabaseHelper.TABLE_PRODUCT, null, values);
        if(success!=-1){
            return true;
        }
        return false;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<Product>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCT;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Product product = new Product(cursor);
                products.add(product);
            }while (cursor.moveToNext());
        }
        return products;
    }

    public int updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_PRODUCT, product.getName());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION_PRODUCT, product.getDescription());
        values.put(DatabaseHelper.COLUMN_PRICE_PRODUCT, product.getPrice());
        values.put(DatabaseHelper.COLUMN_QUANTITY_PRODUCT, product.getQuantity());
        values.put(DatabaseHelper.COLUMN_IMAGE_PRODUCT, product.getImage());
        values.put(DatabaseHelper.COLUMN_ID_CATEGORY, product.getId_category());
        database.insert(DatabaseHelper.TABLE_PRODUCT, null, values);
        return database.update(DatabaseHelper.TABLE_PRODUCT, values, DatabaseHelper.COLUMN_ID_PRODUCT + "=?", new String[]{String.valueOf(product.getId())});
    }

    public void delete(int id){
        database.delete(DatabaseHelper.TABLE_PRODUCT, DatabaseHelper.COLUMN_ID_PRODUCT + "=?", new String[]{String.valueOf(id)});
        database.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCT;
        Cursor cursor = database.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}

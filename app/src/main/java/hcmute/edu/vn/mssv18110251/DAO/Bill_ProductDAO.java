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
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;

public class Bill_ProductDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_BILL,
            DatabaseHelper.COLUMN_ID_PRODUCT,
            DatabaseHelper.COLUMN_AMOUNT_PRODUCT_BILL};

    public Bill_ProductDAO(Context context){dbHelper = new DatabaseHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean addBill_Product(Bill_Product bill_product){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_BILL, bill_product.getId_bill());
        values.put(DatabaseHelper.COLUMN_ID_PRODUCT, bill_product.getId_product());
        values.put(DatabaseHelper.COLUMN_AMOUNT_PRODUCT_BILL, bill_product.getAmount_product());
        long success = database.insert(DatabaseHelper.TABLE_BILL_PRODUCT, null, values);
        if(success!=-1){
            return true;
        }
        return false;
    }

    public List<Bill_Product> getBillProduct(int id_bill){
        List<Bill_Product> list_bill_product = new ArrayList<Bill_Product>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_BILL_PRODUCT + " where " + DatabaseHelper.COLUMN_ID_BILL + " = " + id_bill;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Log.d("CHECK", "account.getEmail()");
                Bill_Product bill_product = new Bill_Product(cursor);
                list_bill_product.add(bill_product);
            }while (cursor.moveToNext());
        }
        return list_bill_product;
    }
}

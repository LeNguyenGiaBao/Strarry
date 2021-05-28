package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Bill;
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
}

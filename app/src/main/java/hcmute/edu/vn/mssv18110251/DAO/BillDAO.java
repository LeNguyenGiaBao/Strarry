package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Bill;

public class BillDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_BILL,
            DatabaseHelper.COLUMN_ID_ACCOUNT_BILL,
            DatabaseHelper.COLUMN_PRICE_BILL,
            DatabaseHelper.COLUMN_DISCOUNT_BILL,
            DatabaseHelper.COLUMN_PHONE_BILL,
            DatabaseHelper.COLUMN_ADDRESS_BILL};

    public BillDAO(Context context){dbHelper = new DatabaseHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean addBill(Bill bill){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_ACCOUNT_BILL, bill.getId_account());
        values.put(DatabaseHelper.COLUMN_PRICE_BILL, bill.getPrice());
        values.put(DatabaseHelper.COLUMN_DISCOUNT_BILL, bill.getDiscount());
        values.put(DatabaseHelper.COLUMN_PHONE_BILL, bill.getPhone());
        values.put(DatabaseHelper.COLUMN_ADDRESS_BILL, bill.getAddress());
        long success = database.insert(DatabaseHelper.TABLE_BILL, null, values);
        if(success!=-1){
            return true;
        }
        return false;
    }
}


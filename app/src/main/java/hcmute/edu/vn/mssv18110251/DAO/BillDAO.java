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
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Product;

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

    public int get_last_inserted_id(int id_account){
        String query = "SELECT last_insert_rowid() from " + DatabaseHelper.TABLE_BILL + " where " + DatabaseHelper.COLUMN_ID_ACCOUNT_BILL + " = " + id_account;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    public Bill getBills(int id_bill){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_BILL + " where " + DatabaseHelper.COLUMN_ID_BILL + " = " + id_bill;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Log.d("CHECK", "account.getEmail()");
                Bill bill = new Bill(cursor);

                return bill;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public List<Bill> get_list_bill_by_id(int id_account){
        List<Bill> billList = new ArrayList<Bill>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_BILL + " WHERE " + DatabaseHelper.COLUMN_ID_ACCOUNT_BILL + " = " + id_account;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Bill bill = new Bill(cursor);
                billList.add(bill);
            }while (cursor.moveToNext());
        }
        return billList;
    }

    public boolean update(int id_bill, int total_price){
        this.open();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_ID_ACCOUNT_BILL, bill.getId_account());
//        values.put(DatabaseHelper.COLUMN_PRICE_BILL, bill.getPrice());
//        values.put(DatabaseHelper.COLUMN_DISCOUNT_BILL, bill.getDiscount());
//        values.put(DatabaseHelper.COLUMN_PHONE_BILL, bill.getPhone());
//        values.put(DatabaseHelper.COLUMN_ADDRESS_BILL, bill.getAddress());
//        long success = database.update(DatabaseHelper.TABLE_BILL, values, DatabaseHelper.COLUMN_ID_BILL + "=?", new String[]{String.valueOf(id_bill)});
//        if(success!=-1){
//            return true;
//        }
//        return false;
        String query = "UPDATE " + DatabaseHelper.TABLE_BILL + " SET " + DatabaseHelper.COLUMN_PRICE_BILL + " = " + total_price + " WHERE " + DatabaseHelper.COLUMN_ID_BILL + " = " + id_bill;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }
}


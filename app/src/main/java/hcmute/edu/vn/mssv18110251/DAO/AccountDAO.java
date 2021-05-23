package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class AccountDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_ACCOUNT,
            DatabaseHelper.COLUMN_NAME_ACCOUNT,
            DatabaseHelper.COLUMN_PASSWORD_ACCOUNT,
            DatabaseHelper.COLUMN_PHONE_ACCOUNT,
            DatabaseHelper.COLUMN_ADDRESS_ACCOUNT,
            DatabaseHelper.COLUMN_ROLE_ACCOUNT,
            DatabaseHelper.COLUMN_EMAIL_ACCOUNT,
            DatabaseHelper.COLUMN_GENDER_ACCOUNT};

    public AccountDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean addAccount(Account account){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_ACCOUNT, account.getName());
        values.put(DatabaseHelper.COLUMN_PASSWORD_ACCOUNT, account.getPassword());
        values.put(DatabaseHelper.COLUMN_PHONE_ACCOUNT, account.getPhone());
        values.put(DatabaseHelper.COLUMN_ADDRESS_ACCOUNT, account.getAddress());
        values.put(DatabaseHelper.COLUMN_ROLE_ACCOUNT, account.getRole());
        values.put(DatabaseHelper.COLUMN_EMAIL_ACCOUNT, account.getEmail());
        values.put(DatabaseHelper.COLUMN_GENDER_ACCOUNT, account.getGender());
        long success = database.insert(DatabaseHelper.TABLE_ACCOUNT, null, values);
        if(success!=-1){
            return true;
        }
        return false;
    }

    public Account checkAccount(String email, String password){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_ACCOUNT + " WHERE email_account ='" + email + "' and password_account ='" + password + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Account account = new Account(cursor);
                Log.d("CHECK", account.getEmail());
                return account;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public void Reset(){
        dbHelper.onReset(database);
    }

    public void CreateDB(){
        dbHelper.onCreate(database);
    }


}

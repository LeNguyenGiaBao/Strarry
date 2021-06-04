package hcmute.edu.vn.mssv18110251.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Category;

public class CategoryDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID_CATEGORY,
            DatabaseHelper.COLUMN_NAME_CATEGORY};

    public CategoryDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_CATEGORY, category.getName());
        database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
    }

    public void Reset(){
        dbHelper.onReset(database);
    }

    public List<Category> getCategory(){
        this.open();
        List<Category> categories = new ArrayList<Category>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Category category = new Category(cursor);
                categories.add(category);
            }while (cursor.moveToNext());
        }
        return categories;
    }
}

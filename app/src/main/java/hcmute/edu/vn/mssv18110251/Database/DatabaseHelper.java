package hcmute.edu.vn.mssv18110251.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "strarry.db";
    private static final int DATABASE_VERSION = 1;


    // category
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_ID_CATEGORY = "id_category";
    public static final String COLUMN_NAME_CATEGORY = "name_category";
    private static final String DATABASE_CREATE_CATEGORY = "create table " + TABLE_CATEGORY + "( "
            + COLUMN_ID_CATEGORY + " integer primary key autoincrement, "
            + COLUMN_NAME_CATEGORY + " text not null);";


    // product
    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_ID_PRODUCT = "id_product";
    public static final String COLUMN_NAME_PRODUCT = "name_product";
    public static final String COLUMN_DESCRIPTION_PRODUCT = "description_product";
    public static final String COLUMN_PRICE_PRODUCT = "price_product";
    public static final String COLUMN_QUANTITY_PRODUCT = "quantity_product";
    public static final String COLUMN_IMAGE_PRODUCT = "image_product";
    public static final String COLUMN_ID_CATEGORY_PRODUCT = "id_category";
    private static final String DATABASE_CREATE_PRODUCT = "create table " + TABLE_PRODUCT + "( "
            + COLUMN_ID_PRODUCT + " integer primary key autoincrement, "
            + COLUMN_NAME_PRODUCT + " text not null, "
            + COLUMN_DESCRIPTION_PRODUCT + " text, "
            + COLUMN_PRICE_PRODUCT + " int, "
            + COLUMN_QUANTITY_PRODUCT + " int, "
            + COLUMN_IMAGE_PRODUCT + " blob, "
            + COLUMN_ID_CATEGORY_PRODUCT + " int, "
            + " CONSTRAINT fk_product_category FOREIGN KEY(" + COLUMN_ID_CATEGORY_PRODUCT + ") REFERENCES " + TABLE_CATEGORY+" ( "+COLUMN_ID_CATEGORY+" ));";

    // account
    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ID_ACCOUNT = "id_account";
    public static final String COLUMN_NAME_ACCOUNT = "name_account";
    public static final String COLUMN_PASSWORD_ACCOUNT = "password_account";
    public static final String COLUMN_PHONE_ACCOUNT = "phone_account";
    public static final String COLUMN_ADDRESS_ACCOUNT = "address_account";
    public static final String COLUMN_ROLE_ACCOUNT = "role_account";
    public static final String COLUMN_EMAIL_ACCOUNT = "email_account";
    public static final String COLUMN_GENDER_ACCOUNT = "gender_account";
    private static final String DATABASE_CREATE_ACCOUNT = "create table " + TABLE_ACCOUNT + "( "
            + COLUMN_ID_ACCOUNT + " integer primary key autoincrement, "
            + COLUMN_NAME_ACCOUNT + " text not null, "
            + COLUMN_PASSWORD_ACCOUNT + " text not null, "
            + COLUMN_PHONE_ACCOUNT + " text, "
            + COLUMN_ADDRESS_ACCOUNT + " text, "
            + COLUMN_ROLE_ACCOUNT + " integer, "
            + COLUMN_EMAIL_ACCOUNT + " text, "
            + COLUMN_GENDER_ACCOUNT + " text);";

    // bill
    public static final String TABLE_BILL = "bill";
    public static final String COLUMN_ID_BILL = "id_bill";
    public static final String COLUMN_ID_ACCOUNT_BILL = "id_account";
    public static final String COLUMN_PRICE_BILL = "price";
    public static final String COLUMN_DISCOUNT_BILL = "discount";
    public static final String COLUMN_PHONE_BILL = "phone";
    public static final String COLUMN_ADDRESS_BILL = "address";
    private static final String DATABASE_CREATE_BILL =  "create table " + TABLE_BILL + "( "
            + COLUMN_ID_BILL + " integer primary key autoincrement, "
            + COLUMN_ID_ACCOUNT_BILL + " integer not null, "
            + COLUMN_PRICE_BILL + " integer, "
            + COLUMN_DISCOUNT_BILL + " integer default(0), "
            + COLUMN_PHONE_BILL + " text, "
            + COLUMN_ADDRESS_BILL + " text, "
            + "CONSTRAINT fk_bill_account FOREIGN KEY(" + COLUMN_ID_ACCOUNT_BILL + ") REFERENCES " + TABLE_ACCOUNT+" ( "+COLUMN_ID_ACCOUNT+" ));";

    // bill + product
    public static final String TABLE_BILL_PRODUCT = "bill_product";
    public static final String COLUMN_AMOUNT_PRODUCT_BILL = "amount_product";
    private static final String DATABASE_CREATE_BILL_PRODUCT = "create table " + TABLE_BILL_PRODUCT + "( "
            + COLUMN_ID_BILL + " integer, "
            + COLUMN_ID_PRODUCT + " integer, "
            + COLUMN_AMOUNT_PRODUCT_BILL + " integer default(0), "
            + "primary key (" + COLUMN_ID_BILL+ " , " + COLUMN_ID_PRODUCT + " ), "
            + "CONSTRAINT fk_bill FOREIGN KEY ( " + COLUMN_ID_BILL + ") REFERENCES " + TABLE_BILL+"("+COLUMN_ID_BILL+"), "
            + "CONSTRAINT fk_product FOREIGN KEY ( " + COLUMN_ID_PRODUCT + ") REFERENCES " + TABLE_PRODUCT+" ( "+COLUMN_ID_PRODUCT+" ));";


    // cart +  product
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID_CART = "id_cart";
    public static final String COLUMN_ID_ACCOUNT_CART = "id_account";
    public static final String COLUMN_ID_PRODUCT_CART = "id_product";
    public static final String COLUMN_AMOUNT_PRODUCT_CART = "amount_product";
    private static final String DATABASE_CREATE_CART =  "create table " + TABLE_CART + "( "
            + COLUMN_ID_CART + " integer primary key autoincrement, "
            + COLUMN_ID_ACCOUNT_CART + " integer not null, "
            + COLUMN_ID_PRODUCT_CART + " integer not null, "
            + COLUMN_AMOUNT_PRODUCT_CART + " integer default(0), "
            + "CONSTRAINT fk_cart_product FOREIGN KEY( " + COLUMN_ID_PRODUCT_CART + ") REFERENCES " + TABLE_PRODUCT+"("+COLUMN_ID_PRODUCT+"), "
            + "CONSTRAINT fk_cart_account FOREIGN KEY(" + COLUMN_ID_ACCOUNT_CART + ") REFERENCES " + TABLE_ACCOUNT+" ( "+COLUMN_ID_ACCOUNT+" ));";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_CATEGORY);
        db.execSQL(DATABASE_CREATE_PRODUCT);
        db.execSQL(DATABASE_CREATE_ACCOUNT);
        db.execSQL(DATABASE_CREATE_BILL);
        db.execSQL(DATABASE_CREATE_BILL_PRODUCT);
        db.execSQL(DATABASE_CREATE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public void onReset(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

}

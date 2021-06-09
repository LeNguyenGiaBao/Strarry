package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.Adapter.CartAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.CartAdapter2;
import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.BillDAO;
import hcmute.edu.vn.mssv18110251.DAO.Bill_ProductDAO;
import hcmute.edu.vn.mssv18110251.DAO.CartDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;

import java.util.Calendar;

public class CartActivity extends AppCompatActivity {

    CartDAO cartDAO;
    RecyclerView recyclerView;
    CartAdapter2 product_cart_adapter;
    BillDAO billDAO;
    Bill_ProductDAO bill_productDAO;
    ProductDAO productDAO;
    TextView txt_total_price;
    ImageView back_button;


    int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    int month = Calendar.getInstance().get(Calendar.MONTH);


    Button btn_purchase;
    private SharePreferenceClass SharedPreferenceClass;


    Locale locale = new Locale("vn", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = this.getIntent();
        Integer intent_from = intent.getIntExtra("FROM", 0);
        Log.d("DATE", String.valueOf(date));

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        txt_total_price = findViewById(R.id.total_price);
        txt_total_price.setText(currencyFormatter.format(0));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_2);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_2:
                        return true;
                    case R.id.navigation_1:
                        if(intent_from==1000) {
                            finish();
                        }
                        if(intent_from==1300 || intent_from == 1400){
                            setResult(1000);
                            finish();
                        }

                        if(intent_from==1340 || intent_from == 1430){
                            setResult(1100);
                            finish();
                        }
//                        if(intent_from==4444){
//                            setResult(4444);
//                            finish();
//                        }
                        return true;
                    case R.id.navigation_3:
                        if(intent_from==1000) {
                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
                            bill_intent.putExtra("FROM", 1200);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1400) {
                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
                            bill_intent.putExtra("FROM", 1420);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1300 || intent_from==1430){
                            finish();
                        }
                        if(intent_from ==1340){
                            setResult(1000);
                            finish();
                        }

//                        if(intent_from==4444){
//                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
//                            bill_intent.putExtra("FROM", 2222);
//                            startActivityForResult(bill_intent, 2222);
//                        }
                        return true;
                    case R.id.navigation_4:
                        if(intent_from==1000){
                            Intent profile_intent = new Intent(getApplicationContext(), AccountActivity.class);
                            profile_intent.putExtra("FROM", 1200);
                            startActivityForResult(profile_intent, 2222);
                        }
                        if(intent_from==1300){
                            Intent profile_intent = new Intent(getApplicationContext(), AccountActivity.class);
                            profile_intent.putExtra("FROM", 1320);
                            startActivityForResult(profile_intent, 2222);
                        }
                        if(intent_from==1400 || intent_from==1340){
                            finish();
                        }
                        if(intent_from==1430){
                            setResult(1000);
                            finish();
                        }
//                        if(intent_from==1111) {
//                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
//                            bill_intent.putExtra("FROM", 2222);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==3333){
//                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
//                            bill_intent.putExtra("FROM", 2222);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==4444){
//                            finish();
//                        }
                        return true;
                }
                return false;
            }
        });

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cartDAO = new CartDAO(this);
        cartDAO.open();

        productDAO = new ProductDAO(this);
        productDAO.open();

//        Cursor cursor = cartDAO.getInfoCart(1);

//        product_cart_adapter = new CartAdapter(getApplicationContext(), cursor);
        List<Cart> listCart = cartDAO.getCart(account.getId());
        product_cart_adapter = new CartAdapter2(getApplicationContext(), listCart);



        recyclerView=findViewById(R.id.recyclerViewCart);
        recyclerView.setAdapter(product_cart_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        billDAO = new BillDAO(this);
        billDAO.open();

        bill_productDAO = new Bill_ProductDAO(this);
        bill_productDAO.open();

        Button btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Cart> list_purchase = product_cart_adapter.get_product_to_purchase();
                int total_price = 0;
                if(list_purchase.size()>0) {
                    for (Cart cart : list_purchase) {
                        Integer id_product = cart.getId_product();
                        Integer amount = cart.getAmount();
                        Product product = productDAO.get_product_by_id(id_product);
//                        if(amount>product.getQuantity())
//                        {
//                            Toast.makeText(getBaseContext(), "Sorry. This product only has " + String.valueOf(product.getQuantity()) + " pieces", Toast.LENGTH_SHORT).show();
//                        }
                        total_price += product.getPrice() * amount;
                    }
                    txt_total_price.setText(currencyFormatter.format(total_price));
                } else {
                    txt_total_price.setText(currencyFormatter.format(0));
                }
            }
        });

        btn_purchase = findViewById(R.id.btn_purchase);
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total_price = 0;
                int total_quantity = 0;
                List<Cart> list_purchase = product_cart_adapter.get_product_to_purchase();
                Log.d("Length of list purchase", String.valueOf(list_purchase.size()));
                if(list_purchase.size()>0){
                    Bill bill = new Bill(account.getId(), 10000, (float) 0.0, String.valueOf(month), String.valueOf(date));
                    if(billDAO.addBill(bill)){
                        Toast.makeText(getBaseContext(), "Add Bill Successfully", Toast.LENGTH_SHORT).show();
                    }
                    Integer id_bill = billDAO.get_last_inserted_id(1);
                    for(Cart cart: list_purchase){
                        Integer id_product = cart.getId_product();
                        Integer amount = cart.getAmount();
                        Log.d("ID_BILL INSERTED", String.valueOf(id_bill));
                        Bill_Product bill_product = new Bill_Product(id_bill, id_product, amount);
                        bill_productDAO.addBill_Product(bill_product);
                        Product product = productDAO.get_product_by_id(id_product);
                        total_price += product.getPrice() * amount;
                        total_quantity+= amount;

                        cartDAO.remove(cart);
                        product.setQuantity(product.getQuantity() - amount);
                        int success = productDAO.updateProduct(product);
                    }
                    Log.d("CART ACTIVITY", String.valueOf(bill.getPrice()));
                    if(billDAO.update(id_bill, total_price)){
                        Log.d("CART ACTIVITY---------------------------------------------------------------------------------------", String.valueOf(bill.getPrice()));
                        Log.d("CART ACTIVITY-----------aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa----------------------------------------", String.valueOf(bill.getId()));
                    }
//                    finish();
//                    startActivity(getIntent());

                    Intent bill_intent = new Intent(getApplicationContext(), BillInfoActivity.class);
                    bill_intent.putExtra("ID_BILL", id_bill);
                    bill_intent.putExtra("TOTAL_PRICE", total_price);
                    bill_intent.putExtra("TOTAL_QUANTITY", total_quantity);
                    startActivity(bill_intent);
                    Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getBaseContext(), "Unable to pay", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_2);
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        List<Cart> listCart = cartDAO.getCart(account.getId());
        product_cart_adapter = new CartAdapter2(getApplicationContext(), listCart);

        recyclerView.setAdapter(product_cart_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        txt_total_price.setText(currencyFormatter.format(0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            finish();
        }
        if(resultCode==1100){
            setResult(1000);
            finish();
        }
    }


}
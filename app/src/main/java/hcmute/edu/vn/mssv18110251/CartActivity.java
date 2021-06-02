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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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

public class CartActivity extends AppCompatActivity {

    CartDAO cartDAO;
    RecyclerView recyclerView;
    CartAdapter2 product_cart_adapter;
    BillDAO billDAO;
    Bill_ProductDAO bill_productDAO;


    Button btn_purchase;
    private SharePreferenceClass SharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_notification);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_product:
                        return true;
                    case R.id.navigation_event:
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_notification:
                        return true;
//                    case R.id.history:
//                        intentNext = new Intent(getApplicationContext(), HistoryActivity.class);
//                        startActivity(intentNext);
//                        overridePendingTransition(0, 0);
//                        return true;
                }
                return false;
            }
        });

        cartDAO = new CartDAO(this);
        cartDAO.open();

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

        btn_purchase = findViewById(R.id.btn_purchase);
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cart> list_purchase = product_cart_adapter.get_product_to_purchase();
                Log.d("Length of list purchase", String.valueOf(list_purchase.size()));
                if(list_purchase.size()>0){
                    Bill bill = new Bill(account.getId(), 10000, (float) 0.0, "12345", "Đồng Nai");
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

                        cartDAO.remove(cart);
                    }
//                    finish();
//                    startActivity(getIntent());

                    Intent bill_intent = new Intent(getApplicationContext(), BillInfoActivity.class);
                    bill_intent.putExtra("ID_BILL", id_bill);
                    startActivity(bill_intent);
                    Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getBaseContext(), "Unable to pay", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
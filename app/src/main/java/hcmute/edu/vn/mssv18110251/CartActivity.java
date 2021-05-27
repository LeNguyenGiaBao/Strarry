package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.CartAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.DAO.CartDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class CartActivity extends AppCompatActivity {

    CartDAO cartDAO;
    RecyclerView recyclerView;
    CartAdapter product_cart_adapter;

    Button btn_purchase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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

        Cursor cursor = cartDAO.getInfoCart(1);
        recyclerView=findViewById(R.id.recyclerViewCart);
        product_cart_adapter = new CartAdapter(getApplicationContext(), cursor);
        recyclerView.setAdapter(product_cart_adapter);


        btn_purchase = findViewById(R.id.btn_purchase);
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
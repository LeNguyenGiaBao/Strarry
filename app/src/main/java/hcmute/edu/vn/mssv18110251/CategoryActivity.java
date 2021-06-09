package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class CategoryActivity extends AppCompatActivity {

    private SharePreferenceClass SharedPreferenceClass;

    CategoryDAO categoryDAO;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
        recyclerView=findViewById(R.id.recyclerView);


        List<Category> categoryList = categoryDAO.getCategory();
        categoryAdapter = new CategoryAdapter(getBaseContext(), categoryList);
        recyclerView.setAdapter(categoryAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_1);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_1:
                        return true;
                    case R.id.navigation_2:
                        Intent cart_intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                        cart_intent.putExtra("FROM", 1000);
                        startActivity(cart_intent);
                        return true;
                    case R.id.navigation_3:
                        Intent bill_intent = new Intent(getApplicationContext(), ProductManage.class);
                        bill_intent.putExtra("FROM", 1000);
                        startActivity(bill_intent);
                        return true;
                    case R.id.navigation_4:
                        Intent profile_intent = new Intent(getApplicationContext(), AccountActivity.class);
                        profile_intent.putExtra("FROM", 1000);
                        startActivity(profile_intent);
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        
    }

    @Override
    protected void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_1);
    }
}
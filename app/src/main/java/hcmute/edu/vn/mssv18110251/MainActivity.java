package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_add, btn_del;
//    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;
    private ActionBar toolbar;

    Button product_manager;
    Intent event_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_product);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_product:
                        return true;
                    case R.id.navigation_event:
                        event_intent = new Intent(getApplicationContext(), ProductManage.class);
                        startActivity(event_intent);
                        overridePendingTransition(0, 0);
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
        // for hiding navigation bar when scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

//        toolbar.setTitle("Shop");

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
//        Category category = new Category("Water");
//        Category category2 = new Category("Milk");
//        Category category3 = new Category("Noddle");
//        Category category4 = new Category("Sandwich");
//        Category category5 = new Category("FastFood");
//        categoryDAO.addCategory(category);
//        categoryDAO.addCategory(category2);
//        categoryDAO.addCategory(category3);
//        categoryDAO.addCategory(category4);
//        categoryDAO.addCategory(category5);



//        Product product = new Product("Cocacola", "Nước giải khác hàng đầu thế giới", 10000, 0, null, 1);
//        Product product2 = new Product("Aquafina", "Nước suối đóng chai tinh khiết", 8000, 0, null, 1);
//        Product product3 = new Product("Pepsi", "Khởi nguồn cùng Pepsi", 10000, 0, null, 1);
//        Product product4 = new Product("Vinamilk", "Tiệt trùng, tinh khiết", 11000, 0, null, 2);
//        Product product5 = new Product("Yomost", "Vị ngon đầy phấn chấn", 12000, 0, null, 2);
//        Product product6 = new Product("Kokomi", "Dai ngon từng sợi", 4000, 0, null, 3);
//        Product product7 = new Product("Omachi", "Sợi khoai tây ngon tuyệt, Rất ngon mà không sợ nóng", 8000, 0, null, 3);
//        Product product8 = new Product("Shin Ramyun", "Đệ nhất mì cay Hàn Quốc", 22000, 0, null, 3);
//        Product product9 = new Product("Samyang", "Mì khô gà cay Hàn Quốc", 25000, 0, bitmapdata, 3);
//
        productDAO = new ProductDAO(this);
        productDAO.open();
//        productDAO.addProduct(product);
//        productDAO.addProduct(product2);
//        productDAO.addProduct(product3);
//        productDAO.addProduct(product4);
//        productDAO.addProduct(product5);
//        productDAO.addProduct(product6);
//        productDAO.addProduct(product7);
//        productDAO.addProduct(product8);
//        productDAO.addProduct(product9);

//        categoryDAO.Reset();

        recyclerView=findViewById(R.id.recyclerView);

        List<Product> products =  productDAO.getProducts();
        productAdapter = new ProductAdapter(getApplicationContext(), (ArrayList<Product>) products);

        recyclerView.setAdapter(productAdapter);

//        product_manager = (Button)findViewById(R.id.btn_event);
//        product_manager.setWidth(100);
//        product_manager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), ProductManage.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_product);
        List<Product> products =  productDAO.getProducts();
        productAdapter = new ProductAdapter(getApplicationContext(), (ArrayList<Product>) products);

        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onBackPressed() {

    }


}
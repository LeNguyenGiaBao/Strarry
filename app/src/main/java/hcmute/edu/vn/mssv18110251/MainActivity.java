package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_add, btn_del;
//    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;
    private ActionBar toolbar;

    ImageView back_button;
    Intent event_intent, cart_intent, profile_intent;
    private SharePreferenceClass SharedPreferenceClass;
    Integer id_category;
    String name_category;
    EditText edt_search;
//    private List<Product> products = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id_category = intent.getIntExtra("ID_CATEGORY", 0);
        name_category = intent.getStringExtra("NAME_CATEGORY");


        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_search = findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> products = null;
                if(s!=null) {
                    products = productDAO.getProducts(id_category, s.toString());
                } else {
                    products = productDAO.getProducts(id_category);
                }
                productAdapter = new ProductAdapter(getApplicationContext(), (ArrayList<Product>) products);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_1);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_1:
                        return true;
                    case R.id.navigation_2:
                        Intent cart_intent = new Intent(getApplicationContext(), CartActivity.class);
                        cart_intent.putExtra("FROM", 1000);
                        startActivity(cart_intent);
                        return true;
                    case R.id.navigation_3:
                        Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
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



//        Product product = new Product("Cocacola", "N?????c gi???i kh??c h??ng ?????u th??? gi???i", 10000, 0, null, 1);
//        Product product2 = new Product("Aquafina", "N?????c su???i ????ng chai tinh khi???t", 8000, 0, null, 1);
//        Product product3 = new Product("Pepsi", "Kh???i ngu???n c??ng Pepsi", 10000, 0, null, 1);
//        Product product4 = new Product("Vinamilk", "Ti???t tr??ng, tinh khi???t", 11000, 0, null, 2);
//        Product product5 = new Product("Yomost", "V??? ngon ?????y ph???n ch???n", 12000, 0, null, 2);
//        Product product6 = new Product("Kokomi", "Dai ngon t???ng s???i", 4000, 0, null, 3);
//        Product product7 = new Product("Omachi", "S???i khoai t??y ngon tuy???t, R???t ngon m?? kh??ng s??? n??ng", 8000, 0, null, 3);
//        Product product8 = new Product("Shin Ramyun", "????? nh???t m?? cay H??n Qu???c", 22000, 0, null, 3);
//        Product product9 = new Product("Samyang", "M?? kh?? g?? cay H??n Qu???c", 25000, 0, bitmapdata, 3);
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

        List<Product> products =  productDAO.getProducts(id_category);
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
        navigation.setSelectedItemId(R.id.navigation_1);

        edt_search.setText("");
        List<Product> products =  productDAO.getProducts(id_category);
        productAdapter = new ProductAdapter(getApplicationContext(), (ArrayList<Product>) products);

        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
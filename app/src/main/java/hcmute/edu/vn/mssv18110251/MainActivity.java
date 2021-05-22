package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_add, btn_del;
//    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;

    Button product_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        product_manager = (Button)findViewById(R.id.btn_event);
        product_manager.setWidth(100);
        product_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProductManage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Product> products =  productDAO.getProducts();
        productAdapter = new ProductAdapter(getApplicationContext(), (ArrayList<Product>) products);

        recyclerView.setAdapter(productAdapter);
    }
}
package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Product;


public class DetailProduct extends AppCompatActivity {

    private ProductDAO productDAO;
    TextView productName;
    TextView productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("POSITION");
        Log.d("CREATION", message);


        productDAO = new ProductDAO(this);
        productDAO.open();
        List<Product> products =  productDAO.getProducts();

        Integer position = Integer.parseInt(message);
        Product product = products.get(position);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);


        productName.setText(product.getName());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        productPrice.setText(currencyFormatter.format(product.getPrice()));

    }
}
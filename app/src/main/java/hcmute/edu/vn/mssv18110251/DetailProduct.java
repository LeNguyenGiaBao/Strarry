package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.CartDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Login.RegisterActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;


public class DetailProduct extends AppCompatActivity {

    private ProductDAO productDAO;
    TextView productName;
    TextView productPrice;
    TextView quantity_number;
    TextView description;
    ImageView imageView, back_button;
    ImageButton plus_quantity, minus_quantity;
    Button add_to_cart;
    int quantity = 1, price;
    private SharePreferenceClass SharedPreferenceClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");


        CartDAO cartDAO = new CartDAO(this);
        cartDAO.open();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Integer id_product = intent.getIntExtra("ID_PRODUCT", 0);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        productDAO = new ProductDAO(this);
        productDAO.open();
//        List<Product> products =  productDAO.getProducts();

        Product product = productDAO.get_product_by_id(id_product);
        Integer quantity_product = product.getQuantity();
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        quantity_number = findViewById(R.id.quantity);
        description = findViewById(R.id.descriptioninfo);

        Log.d("Detail_Product", String.valueOf(id_product));
        productName.setText(product.getName());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        productPrice.setText(currencyFormatter.format(product.getPrice()));
        price = product.getPrice();
        description.setText(product.getDescription());
        imageView = findViewById(R.id.productImage);
        if(product.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            imageView.setImageBitmap(bitmap);
        }


        plus_quantity = findViewById(R.id.addquantity);
        minus_quantity  = findViewById(R.id.subquantity);

        plus_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>=quantity_product){
                    Toast.makeText(getBaseContext(), "Sorry. This product only has " + String.valueOf(quantity_product) + " pieces", Toast.LENGTH_SHORT).show();
                } else {
                    quantity++;
                    displayQuantity();
                }
            }
        });

        minus_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity == 1){
                    Toast.makeText(getBaseContext(), "Can't decrease quantity < 1", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();

                }
            }
        });
        add_to_cart = findViewById(R.id.addtocart);
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id_account  = account.getId();
                Integer id_cart = cartDAO.get_id_cart(id_account, id_product);
                Log.d("ID_CART", String.valueOf(id_cart));
                if(id_cart>0) {
                    Cart cart = cartDAO.get_cart_by_id(id_cart);
                    int init_amount = cart.getAmount();
                    if(init_amount + quantity > quantity_product){
                        cart.setAmount(quantity_product);
                    } else {
                        cart.setAmount(init_amount + quantity);
                    }
                    boolean success = cartDAO.update(cart);
                    if (success) {
                        Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "False", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Cart cart = new Cart(id_account, id_product, quantity);
                    boolean success = cartDAO.add_to_cart(cart);
                    if (success) {
                        Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "False", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void displayQuantity() {
        quantity_number.setText(String.valueOf(quantity));
    }
}
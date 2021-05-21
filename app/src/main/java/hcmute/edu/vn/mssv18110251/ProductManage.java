package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class ProductManage extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    Button addProduct, openGallery;
    EditText name_product, description_product, price, quantity;
    ProductDAO productDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);

        name_product = (EditText)findViewById(R.id.name_product);
        description_product = (EditText)findViewById(R.id.description_product);
        price = (EditText)findViewById(R.id.price_product);
        quantity = (EditText)findViewById(R.id.quantity_product);

        openGallery = (Button)findViewById(R.id.btn_load_image);
        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        productDAO = new ProductDAO(this);
        productDAO.open();

        addProduct = (Button)findViewById(R.id.btn_add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameProduct = name_product.getText().toString();
                String descriptionProduct = description_product.getText().toString();
                Integer priceProduct = Integer.parseInt(price.getText().toString());
                Integer quantityProduct = Integer.parseInt(quantity.getText().toString());
                Product product = new Product(nameProduct, descriptionProduct, priceProduct, quantityProduct, null, 1);
                productDAO.addProduct(product);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {


            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
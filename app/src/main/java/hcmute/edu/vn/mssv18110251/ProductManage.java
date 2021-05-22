package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class ProductManage extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1000;
    public static int PERMISSION_CODE = 1001;

    Button addProduct, openGallery;
    EditText name_product, description_product, price, quantity;
    ProductDAO productDAO;
    Bitmap image_product;
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
            public void onClick(View v) {
                // check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        // permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        // permission already granted
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                }
                else {
                    // system os is less then marshmallow
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
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
                if(image_product!=null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image_product.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    image_product.recycle();
                    Product product = new Product(nameProduct, descriptionProduct, priceProduct, quantityProduct, byteArray, 1);
                    productDAO.addProduct(product);
                }
                else{
                    Product product = new Product(nameProduct, descriptionProduct, priceProduct, quantityProduct, null, 1);
                    productDAO.addProduct(product);
                }

            }
        });
    }

    private void pickImageFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    // handle result of runtiem permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1001:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied ...", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
            image_product = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
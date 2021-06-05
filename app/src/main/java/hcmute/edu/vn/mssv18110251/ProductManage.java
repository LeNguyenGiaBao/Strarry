package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.CategorySpinnerAdapter;
import hcmute.edu.vn.mssv18110251.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.Model.Product;

public class ProductManage extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1000;
    public static int PERMISSION_CODE = 1001;

    Button addProduct, go_to_home;
    EditText name_product, description_product, price, quantity;
    ProductDAO productDAO;
    Bitmap image_product;
    ImageView imageView;
    Spinner categorySpinner;
    private List<Category> categories;
    CategoryDAO categoryDAO;

    Intent product_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);

        name_product = (EditText)findViewById(R.id.name_product);
        description_product = (EditText)findViewById(R.id.description_product);
        price = (EditText)findViewById(R.id.price_product);
        quantity = (EditText)findViewById(R.id.quantity_product);
        imageView = (ImageView) findViewById(R.id.imgView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
        categories = categoryDAO.getCategory();
        categorySpinner = (Spinner)findViewById(R.id.spnCategory);
        set_value_spinner();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(ProductManage.this);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_1);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_1:
                        return true;
                    case R.id.navigation_2:
//                        product_intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(product_intent);
                        finish();
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_3:
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



//        openGallery = (Button)findViewById(R.id.btn_load_image);
//        openGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // check runtime permission
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                        // permission not granted, request it
//                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, PERMISSION_CODE);
//                    } else {
//                        // permission already granted
//                        Intent i = new Intent(
//                                Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(i, RESULT_LOAD_IMAGE);
//                    }
//                }
//                else {
//                    // system os is less then marshmallow
//                    Intent i = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(i, RESULT_LOAD_IMAGE);
//                }
//            }
//        });


        productDAO = new ProductDAO(this);

        addProduct = (Button)findViewById(R.id.btn_add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct = name_product.getText().toString();
                String descriptionProduct = description_product.getText().toString();
                Integer categoryId = categories.get(categorySpinner.getSelectedItemPosition()).getId();
                Integer priceProduct = Integer.parseInt(price.getText().toString());
                Integer quantityProduct = Integer.parseInt(quantity.getText().toString());
                byte[] byteArray = new byte[0];
//                Log.d("CREATION", image_product.toString());
                if(image_product!=null) {
                    Log.d("CREATION", "Co hinh nha");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image_product.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    image_product.recycle();
                }
                Product product = new Product(nameProduct, descriptionProduct, priceProduct, quantityProduct, byteArray, categoryId);
                if(productDAO.addProduct(product)){
                    Toast.makeText(ProductManage.this, "Product has been added successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProductManage.this, "Error!", Toast.LENGTH_LONG).show();
                }
                name_product.setText("");
                description_product.setText("");
                price.setText("");
                quantity.setText("");
                imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                categorySpinner.setSelection(0);

            }
        });
    }

    private void set_value_spinner() {
        SpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(getApplicationContext(), categories);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(0);

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//
//
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            image_product = BitmapFactory.decodeFile(picturePath);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        image_product = selectedImage;
                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                image_product = BitmapFactory.decodeFile(picturePath);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
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
                            startActivityForResult(i, 1);
                        }
                    }
                    else {
                        // system os is less then marshmallow
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 1);
                    }
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_capture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
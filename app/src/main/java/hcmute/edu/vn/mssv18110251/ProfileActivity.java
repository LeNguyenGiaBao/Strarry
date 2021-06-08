package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.Login.LoginActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;

public class ProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1002;
    public static int PERMISSION_CODE = 1001;

    ImageView btn_logout, back_button;
    SharePreferenceClass SharedPreferenceClass;
    EditText account_name, account_email, account_phone, account_address;
    Button btn_update;
    AccountDAO accountDAO;
    Bitmap image_product = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        account_name = findViewById(R.id.account_name);
        account_name.setText(account.getName());

        account_email = findViewById(R.id.account_email);
//        account_email.setEnabled(false);
        account_email.setText(account.getEmail());
//        account_email.i

        account_phone = findViewById(R.id.account_phone);
        account_phone.setText(account.getPhone());

        account_address = findViewById(R.id.account_address);
        account_address.setText(account.getAddress());

//        imageView = findViewById(R.id.imgView);

        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!isValidEmail(account.getEmail()))
//                {
//                    Toast.makeText(getBaseContext(), "Email is invalid", Toast.LENGTH_SHORT);
//                    return;
//                }
                if(account_address.getText().toString() !="") {
                    account.setAddress(account_address.getText().toString());
                } else {
                    account.setAddress("");
                }
                if(account_name.getText().toString() !="") {
                    account.setName(account_name.getText().toString());
                } else {
                    account.setName("");
                }
                if(account_phone.getText().toString()!="") {
                    account.setPhone(account_phone.getText().toString());
                } else {
                    account.setPhone("");
                }
                if(account_phone.getText().toString()!="") {
                    account.setPhone(account_phone.getText().toString());
                } else {
                    account.setPhone("");
                }

                if(image_product!=null) {
                    byte[] byteArray = new byte[0];
                    Log.d("CREATION", image_product.toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image_product.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    image_product.recycle();
                    account.setImage(byteArray);
                }

                if(accountDAO.update(account)){
                    Toast.makeText(getBaseContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                    Log.d("ProfileActivity", "Update successfully");
                    SharedPreferenceClass.getInstance(getBaseContext()).clear("account");
                    SharedPreferenceClass.getInstance(getBaseContext()).set("account", account);
                }
            }
        });

        imageView = findViewById(R.id.imgView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(ProfileActivity.this);
            }
        });
        if(account.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(account.getImage(), 0, account.getImage().length);
            imageView.setImageBitmap(bitmap);
        }
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setSelectedItemId(R.id.navigation_4);
//        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.navigation_4:
//                        return true;
//                    case R.id.navigation_1:
//                        if(intent_from==1000) {
//                            finish();
//                        }
//                        if(intent_from==1200 || intent_from == 1300){
//                            setResult(1000);
//                            finish();
//                        }
//
//                        if(intent_from==1230 || intent_from == 1320){
//                            setResult(1100);
//                            finish();
//                        }
////                        if(intent_from==4444){
////                            setResult(4444);
////                            finish();
////                        }
//                        return true;
//                    case R.id.navigation_2:
//                        if(intent_from==1000) {
//                            Intent bill_intent = new Intent(getApplicationContext(), CartActivity.class);
//                            bill_intent.putExtra("FROM", 1400);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==1300) {
//                            Intent bill_intent = new Intent(getApplicationContext(), CartActivity.class);
//                            bill_intent.putExtra("FROM", 1340);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==1200 || intent_from==1320){
//                            finish();
//                        }
//                        if(intent_from ==1230){
//                            setResult(1000);
//                            finish();
//                        }
//                        return true;
//                    case R.id.navigation_3:
//                        if(intent_from==1000) {
//                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
//                            bill_intent.putExtra("FROM", 1400);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==1200) {
//                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
//                            bill_intent.putExtra("FROM", 1240);
//                            startActivityForResult(bill_intent, 2222);
//                        }
//                        if(intent_from==1300 || intent_from==1230){
//                            finish();
//                        }
//                        if(intent_from ==1320){
//                            setResult(1000);
//                            finish();
//                        }
//                        return true;
//                }
//                return false;
//            }
//        });

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==1000){
//            finish();
//        }
//        if(resultCode==1100){
//            setResult(1000);
//            finish();
//        }
//    }


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

    private void pickImageFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

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
//
//        if(resultCode==1000){
//            finish();
//        }
//        if(resultCode==1100){
//            setResult(1000);
//            finish();
//        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
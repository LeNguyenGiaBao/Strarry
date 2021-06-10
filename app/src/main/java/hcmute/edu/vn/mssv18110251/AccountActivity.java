package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.Login.LoginActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;

public class AccountActivity extends AppCompatActivity {

    ImageView btn_logout, profile, changepassword, support, image_account, back_button;
    TextView name_account;
    private SharePreferenceClass SharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = this.getIntent();
        Integer intent_from = intent.getIntExtra("FROM", 0);
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        image_account = findViewById(R.id.image_account);
        name_account = findViewById(R.id.name_account);
        set_info_account(account);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            private SharePreferenceClass SharedPreferenceClass;

            @Override
            public void onClick(View v) {
                SharedPreferenceClass.getInstance(getBaseContext()).clear("account");

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profile_intent);
            }
        });

        changepassword = findViewById(R.id.change_password);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change_password_intent = new Intent(getBaseContext(), ChangePasswordActivity.class);
                startActivity(change_password_intent);
            }
        });

        support = findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent support_intent = new Intent(getBaseContext(), SupportActivity.class);
                startActivity(support_intent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_4);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_4:
                        return true;
                    case R.id.navigation_1:
                        if(intent_from==1000) {
                            finish();
                        }
                        if(intent_from==1200 || intent_from == 1300){
                            setResult(1000);
                            finish();
                        }

                        if(intent_from==1230 || intent_from == 1320){
                            setResult(1100);
                            finish();
                        }
//                        if(intent_from==4444){
//                            setResult(4444);
//                            finish();
//                        }
                        return true;
                    case R.id.navigation_2:
                        if(intent_from==1000) {
                            Intent bill_intent = new Intent(getApplicationContext(), CartActivity.class);
                            bill_intent.putExtra("FROM", 1400);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1300) {
                            Intent bill_intent = new Intent(getApplicationContext(), CartActivity.class);
                            bill_intent.putExtra("FROM", 1340);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1200 || intent_from==1320){
                            finish();
                        }
                        if(intent_from ==1230){
                            setResult(1000);
                            finish();
                        }
                        return true;
                    case R.id.navigation_3:
                        if(intent_from==1000) {
                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
                            bill_intent.putExtra("FROM", 1400);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1200) {
                            Intent bill_intent = new Intent(getApplicationContext(), BillActivity.class);
                            bill_intent.putExtra("FROM", 1240);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1300 || intent_from==1230){
                            finish();
                        }
                        if(intent_from ==1320){
                            setResult(1000);
                            finish();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void set_info_account(Account account) {
        name_account.setText(account.getName());
        if(account.getImage()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(account.getImage(), 0, account.getImage().length);
            image_account.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            finish();
        }
        if(resultCode==1100){
            setResult(1000);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        set_info_account(account);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        set_info_account(account);
    }
}
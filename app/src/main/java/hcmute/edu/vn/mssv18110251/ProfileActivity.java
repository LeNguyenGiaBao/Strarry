package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.Login.LoginActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;

public class ProfileActivity extends AppCompatActivity {

    ImageView btn_logout, back_button;
    SharePreferenceClass SharedPreferenceClass;
    EditText account_name, account_email, account_phone, account_address, account_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = this.getIntent();
        Integer intent_from = intent.getIntExtra("FROM", 0);


        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        account_name = findViewById(R.id.account_name);
        account_name.setText(account.getName());

        account_email = findViewById(R.id.account_email);
        account_email.setText(account.getEmail());

        account_phone = findViewById(R.id.account_phone);
        account_phone.setText(account.getPhone());

        account_address = findViewById(R.id.account_address);
        account_address.setText(account.getAddress());
//
//        account_password = findViewById(R.id.account_password);
//        account_password.setText(account.());


        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferenceClass.getInstance(getBaseContext()).clear("account");

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
    protected void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_4);
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

    }
}
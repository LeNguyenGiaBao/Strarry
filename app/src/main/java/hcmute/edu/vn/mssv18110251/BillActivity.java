package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.BillActivityAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.CartAdapter2;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.BillDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Cart;

public class BillActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BillDAO billDAO;
    BillActivityAdapter  billActivityAdapter;
    private SharePreferenceClass SharedPreferenceClass;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = this.getIntent();
        Integer intent_from = intent.getIntExtra("FROM", 0);

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        Log.d("BILL_ACTIVITY_______________________________________________________________________________", String.valueOf(account.getId()));

        billDAO = new BillDAO(this);
        billDAO.open();

        List<Bill> list_bill = billDAO.get_list_bill_by_id(account.getId());


        billActivityAdapter = new BillActivityAdapter(getApplicationContext(), list_bill);

        recyclerView=findViewById(R.id.recyclerViewBill);
        recyclerView.setAdapter(billActivityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BillActivity.this));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_3);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_3:
                        return true;
                    case R.id.navigation_1:
                        if(intent_from==1000) {
                            finish();
                        }
                        if(intent_from==1200 || intent_from == 1400){
                            setResult(1000);
                            finish();
                        }

                        if(intent_from==1240 || intent_from == 1420){
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
                            bill_intent.putExtra("FROM", 1300);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1400) {
                            Intent bill_intent = new Intent(getApplicationContext(), CartActivity.class);
                            bill_intent.putExtra("FROM", 1430);
                            startActivityForResult(bill_intent, 2222);
                        }
                        if(intent_from==1200 || intent_from==1420){
                            finish();
                        }
                        if(intent_from ==1240){
                            setResult(1000);
                            finish();
                        }
//                        if(intent_from == 1111){
//                            finish();
//                        }
//                        if(intent_from == 2222){
//                            setResult(2222);
//                            finish();
//                        }
                        return true;
                    case R.id.navigation_4:
                        if(intent_from==1000){
                            Intent profile_intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            profile_intent.putExtra("FROM", 1300);
                            startActivityForResult(profile_intent, 2222);
                        }
                        if(intent_from==1200){
                            Intent profile_intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            profile_intent.putExtra("FROM", 1230);
                            startActivityForResult(profile_intent, 2222);
                        }
                        if(intent_from==1400 || intent_from==1240){
                            finish();
                        }
                        if(intent_from==1420){
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
    protected void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_3);
        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

    }
}
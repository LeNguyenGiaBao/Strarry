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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import hcmute.edu.vn.mssv18110251.Adapter.BillActivityAdapter;
import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.BillDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Bill;

public class BillActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BillDAO billDAO;
    BillActivityAdapter  billActivityAdapter;
    private SharePreferenceClass SharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);


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
                    case R.id.navigation_2:
                        setResult(0);
                        finish();
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_1:
                        setResult(2222);
                        finish();
                        return true;
                    case R.id.navigation_4:
                        Intent profile_intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profile_intent);
//                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}
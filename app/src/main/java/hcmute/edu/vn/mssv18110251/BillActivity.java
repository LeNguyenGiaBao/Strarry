package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
    }
}
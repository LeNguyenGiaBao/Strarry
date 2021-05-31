package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import hcmute.edu.vn.mssv18110251.DAO.BillDAO;
import hcmute.edu.vn.mssv18110251.DAO.Bill_ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;

public class BillInfoActivity extends AppCompatActivity {

    BillDAO billDAO;
    Bill_ProductDAO bill_product_dao;
    List<Bill_Product> list_bill_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        billDAO = new BillDAO(this);
        billDAO.open();

        bill_product_dao = new Bill_ProductDAO(this);
        bill_product_dao.open();

        Integer id_bill = billDAO.get_last_inserted_id(1);
        list_bill_product = bill_product_dao.getBillProduct(id_bill);
        Log.d("BillInfo", String.valueOf(id_bill));
    }
}
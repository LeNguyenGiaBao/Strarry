package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import hcmute.edu.vn.mssv18110251.Adapter.BillAdapter;
import hcmute.edu.vn.mssv18110251.DAO.BillDAO;
import hcmute.edu.vn.mssv18110251.DAO.Bill_ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;
import androidmads.library.qrgenearator.QRGEncoder;

public class BillInfoActivity extends AppCompatActivity {

    BillDAO billDAO;
    Bill_ProductDAO bill_product_dao;
    List<Bill_Product> list_bill_product;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        Button btn_finish = findViewById(R.id.finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        billDAO = new BillDAO(this);
        billDAO.open();

        bill_product_dao = new Bill_ProductDAO(this);
        bill_product_dao.open();

        listView = findViewById(R.id.listview);

        Integer id_bill = getIntent().getIntExtra("ID_BILL", 0);
        Integer quantity = getIntent().getIntExtra("TOTAL_QUANTITY", 0);
        Integer total_price = getIntent().getIntExtra("TOTAL_PRICE", 0);
        Bill bill = billDAO.getBills(id_bill);
        list_bill_product = bill_product_dao.getBillProduct(id_bill);

        TextView txt_date = findViewById(R.id.date);
        TextView txt_total_price = findViewById(R.id.total_price);
        TextView txt_total_quantity = findViewById(R.id.total_quantity);
        txt_date.setText("Date: "+ String.valueOf(bill.getAddress()) + "/" + String.valueOf(bill.getPhone()));
        txt_total_quantity.setText("SL: " + String.valueOf(quantity));
        txt_total_price.setText(String.valueOf(total_price));

        ImageView qrCodeIV = findViewById(R.id.qrcode);
        // this is a small sample use of the QRCodeEncoder class from zxing
        qrgEncoder = new QRGEncoder(String.valueOf(id_bill), null, QRGContents.Type.TEXT, 500);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }

        BillAdapter adapter = new BillAdapter(this, list_bill_product);
        listView.setAdapter(adapter);
    }
}
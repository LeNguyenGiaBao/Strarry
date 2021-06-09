package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.BillInfoActivity;
import hcmute.edu.vn.mssv18110251.DAO.Bill_ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;
import hcmute.edu.vn.mssv18110251.R;

public class BillActivityAdapter extends RecyclerView.Adapter<BillActivityAdapter.ViewHolder>{

    private List<Bill> listBill;
    List<Bill_Product> bill_productList;
    Bill_ProductDAO bill_productDAO;
    Context context;
    Locale locale = new Locale("vn", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    public BillActivityAdapter(Context context, List<Bill> listBill){
        this.context = context;
        this.listBill = listBill;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bill_productDAO = new Bill_ProductDAO(this.context);
        bill_productDAO.open();

        Bill bill = listBill.get(position);
        holder.date.setText("Date: " + String.valueOf(bill.getAddress()) + "/" + String.valueOf(bill.getPhone()));
        holder.id_bill.setText("ID Bill: " + String.valueOf(bill.getId()));
        holder.total_price.setText(currencyFormatter.format(bill.getPrice()));
        bill_productList = bill_productDAO.getBillProduct(bill.getId());
        int total_quantity = 0;
        for(Bill_Product bill_product : bill_productList){
            total_quantity += bill_product.getAmount_product();
        }

        int finalTotal_quantity = total_quantity;
        holder.go_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillInfoActivity.class);
                intent.putExtra("ID_BILL", bill.getId());
                intent.putExtra("TOTAL_PRICE", bill.getPrice());
                intent.putExtra("TOTAL_QUANTITY", finalTotal_quantity);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        int total_quantity = 0;
//        for (Bill_Product bill_product : bill_productList){
//            int id_product = bill_product.getId_product();
//
//        }
    }

    @Override
    public int getItemCount() {
        return this.listBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_bill, date, total_price, total_quantity;
        ImageView go_to;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            go_to = itemView.findViewById(R.id.go_to);
            id_bill = itemView.findViewById(R.id.id_bill);
            date = itemView.findViewById(R.id.date_bill);
            total_price = itemView.findViewById(R.id.bill_price);
//            total_quantity = itemView.findViewById(R.id.bill_quantity);
        }
    }
}

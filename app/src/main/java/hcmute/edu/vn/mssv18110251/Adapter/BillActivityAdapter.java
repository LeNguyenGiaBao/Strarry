package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.mssv18110251.DAO.Bill_ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Bill;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;
import hcmute.edu.vn.mssv18110251.R;

public class BillActivityAdapter extends RecyclerView.Adapter<BillActivityAdapter.ViewHolder>{

    private List<Bill> listBill;
    List<Bill_Product> bill_productList;
    Bill_ProductDAO bill_productDAO;
    Context context;

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
        holder.id_bill.setText(String.valueOf(bill.getId()));
        holder.total_price.setText(String.valueOf(bill.getPrice()));
        bill_productList = bill_productDAO.getBillProduct(bill.getId());
        for (Bill_Product bill_product : bill_productList){
            int id_product = 0;
        }
    }

    @Override
    public int getItemCount() {
        return this.listBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_bill, date, total_price, total_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_bill = itemView.findViewById(R.id.id_bill);
            date = itemView.findViewById(R.id.date_bill);
            total_price = itemView.findViewById(R.id.bill_price);
            total_quantity = itemView.findViewById(R.id.bill_quantity);
        }
    }
}

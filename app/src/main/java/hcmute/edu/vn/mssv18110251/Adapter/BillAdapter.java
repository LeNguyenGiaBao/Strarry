package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Bill_Product;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class BillAdapter extends BaseAdapter {

    List<Bill_Product> listBillProduct;
    Context context;
    ProductDAO productDAO;

    public BillAdapter(Context context, List<Bill_Product> listBillProduct) {
        this.context = context;
        this.listBillProduct = listBillProduct;
    }


    @Override
    public int getCount() {
        return listBillProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listBillProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_bill, parent, false);

        productDAO = new ProductDAO(context);
        productDAO.open();

        Bill_Product bill_product = listBillProduct.get(position);
        Product product = productDAO.get_product_by_id(bill_product.getId_product());

        TextView product_name = view.findViewById(R.id.product_name);
        TextView product_price = view.findViewById(R.id.productPrice);
        TextView product_quantity = view.findViewById(R.id.quantityinOrderSummary);
        TextView total_price = view.findViewById(R.id.total_price_product);

        product_name.setText(product.getName());
        product_price.setText(String.valueOf(product.getPrice()));
        product_quantity.setText(String.valueOf(bill_product.getAmount_product()));

        int int_total_price = product.getPrice()  * bill_product.getAmount_product();
        total_price.setText(String.valueOf(int_total_price));
        return view;
    }
}

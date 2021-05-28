package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class CartAdapter2 extends RecyclerView.Adapter<CartAdapter2.ViewHolder>  {

    Context context;
    List<Cart> listCart;
    ProductDAO productDAO;
    List<Integer> product_to_purchase = new ArrayList<Integer>();

    public CartAdapter2(Context context, List<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    public List<Cart> get_product_to_purchase(){
        List<Cart> carts = new ArrayList<Cart>();
        for(int i=0; i<product_to_purchase.size(); i++){
            Cart cart = listCart.get(product_to_purchase.get(i));
            carts.add(cart);
        }
        return carts;
    }
    @NonNull
    @Override
    public CartAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productDAO = new ProductDAO(context);
        productDAO.open();
        // gán view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cart, parent, false);
        return new CartAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter2.ViewHolder holder, int position) {
        // Gán dữ liêuk
        Cart cart = listCart.get(position);
        Product product = productDAO.get_product_by_id(cart.getId_product());
        holder.productName.setText(product.getName());
        Log.d("ProductName", product.getName());
        holder.productQuantity.setText(String.valueOf(cart.getAmount()));
        holder.productName.setText(product.getName());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.productPrice.setText(currencyFormatter.format(product.getPrice()));
        if(product.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        }

        Integer total_price = product.getPrice() + cart.getAmount();
        holder.totalPrice.setText(String.valueOf(total_price));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!product_to_purchase.contains(position)){
                        product_to_purchase.add(position);
                        Log.d("CHECKED", String.valueOf(position));
                    }
                } else {
                    if(product_to_purchase.contains(position)){
                        product_to_purchase.remove(new Integer(position));
                        Log.d("UNCHECKED", String.valueOf(position));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity, totalPrice;
        ImageView imageView;

        CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.productNameCart);
            productPrice = view.findViewById(R.id.productPriceCart);
            productQuantity = view.findViewById(R.id.quantity);
            imageView = view.findViewById(R.id.image_cartlist);

            totalPrice = view.findViewById(R.id.total_price);
            checkBox = view.findViewById(R.id.checkbox_id);
        }
    }
}

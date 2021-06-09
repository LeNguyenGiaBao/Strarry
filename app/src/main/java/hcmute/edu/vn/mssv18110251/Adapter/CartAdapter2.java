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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.DAO.CartDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class CartAdapter2 extends RecyclerView.Adapter<CartAdapter2.ViewHolder>  {

    Context context;
    List<Cart> listCart;
    ProductDAO productDAO;
    CartDAO cartDAO;
    List<Integer> product_to_purchase = new ArrayList<Integer>();
    int quantity;

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

        cartDAO = new CartDAO(context);
        cartDAO.open();
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

        quantity = cart.getAmount();
        Integer total_price = product.getPrice() * cart.getAmount();
        holder.totalPrice.setText(currencyFormatter.format(total_price));

        if(product.getQuantity()<quantity){
            holder.checkBox.setEnabled(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!product_to_purchase.contains(position)) {
                        product_to_purchase.add(position);
                        Log.d("CHECKED", String.valueOf(position));
                    }
                } else {
                    if (product_to_purchase.contains(position)) {
                        product_to_purchase.remove(new Integer(position));
                        Log.d("UNCHECKED", String.valueOf(position));
                    }
                }

            }
        });

        holder.add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("CartAdapter2_Position", String.valueOf(position));
                Cart cart = listCart.get(position);
                Product product = productDAO.get_product_by_id(cart.getId_product());
                quantity = cart.getAmount();
                Integer quantity_product = product.getQuantity();
//                quantity++;
                if(quantity>=quantity_product){
                    Toast.makeText(context, "Sorry. This product only has " + String.valueOf(quantity_product) + " pieces", Toast.LENGTH_SHORT).show();
                } else {
                    quantity++;
                }
                holder.productQuantity.setText(String.valueOf(quantity));
                Integer total_price = product.getPrice() * quantity;
                holder.totalPrice.setText(currencyFormatter.format(total_price));
                cart.setAmount(quantity);
                boolean success = cartDAO.update(cart);
                if(!success){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.minus_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("CartAdapter2_Position", String.valueOf(position));
                Cart cart = listCart.get(position);
                Product product = productDAO.get_product_by_id(cart.getId_product());
                quantity = cart.getAmount();

                if(quantity == 1){
                    Toast.makeText(context, "Can't decrease quantity < 1", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    holder.productQuantity.setText(String.valueOf(quantity));
                    Integer total_price = product.getPrice() * quantity;
                    holder.totalPrice.setText(currencyFormatter.format(total_price));
                    cart.setAmount(quantity);

                    boolean success = cartDAO.update(cart);
                    if(!success){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("CartAdapter2_Position", String.valueOf(position));
                Cart cart = listCart.get(position);
                cartDAO.remove(cart);
                removeItem(position);


            }
        });


    }

    private void removeItem(int position) {
        listCart.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listCart.size());
    }


    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity, totalPrice;
        ImageView imageView, add_amount, minus_amount, remove_from_cart;

        CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.productNameCart);
            productPrice = view.findViewById(R.id.productPriceCart);
            productQuantity = view.findViewById(R.id.quantity);
            imageView = view.findViewById(R.id.image_cartlist);

            totalPrice = view.findViewById(R.id.total_price);
            checkBox = view.findViewById(R.id.checkbox_id);

            add_amount = view.findViewById(R.id.addquantity);
            minus_amount = view.findViewById(R.id.subquantity);

            remove_from_cart = view.findViewById(R.id.deletecart);
        }
    }
}



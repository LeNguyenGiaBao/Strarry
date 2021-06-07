package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.DAO.CartDAO;
import hcmute.edu.vn.mssv18110251.DetailProduct;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.ProfileActivity;
import hcmute.edu.vn.mssv18110251.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> listProduct;
    private SharePreferenceClass SharedPreferenceClass;
    Account account;


    public ProductAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        account = (Account)SharedPreferenceClass.getInstance(context).get("account");

        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêuk
        Product product = listProduct.get(position);
        Log.d("Product_adapter", String.valueOf(product.getId()));
        holder.productName.setText(product.getName());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.productPrice.setText(currencyFormatter.format(product.getPrice()));
        holder.productDescription.setText(product.getDescription());
        if(product.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView, add_to_cart;
        TextView productName, productPrice, productDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
//            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            productPrice = itemView.findViewById(R.id.productPrice);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            imageView = itemView.findViewById(R.id.productImage);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);

            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartDAO cartDAO = new CartDAO(context);
                    cartDAO.open();

                    Integer id_account  = account.getId();
                    Cart cart = new Cart(id_account, listProduct.get(getBindingAdapterPosition()).getId(), 1);
                    boolean success = cartDAO.add_to_cart(cart);
                    if(success) {
                        Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "False", Toast.LENGTH_LONG).show();
                    }
                }
            });

            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // lets get the position of the view in list and then work on it
            int position = getAbsoluteAdapterPosition();
            Product product = listProduct.get(position);
            Intent intent = new Intent(context, DetailProduct.class);
            intent.putExtra("ID_PRODUCT", product.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class CartAdapter extends CursorRecyclerViewAdapter<CartAdapter.ViewHolder> {
    Integer id_cart;
    ArrayList<Integer> product_to_purchase = new ArrayList<Integer>();
    public CartAdapter(Context context, Cursor cursor) {
        super(context,cursor);
    }



    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, Cursor cursor) {
        final Integer id_cart2 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id_cart")));
        Log.d("ID_Cart", String.valueOf(id_cart));
        viewHolder.productName.setText(cursor.getString(cursor.getColumnIndex("name_product")));
        viewHolder.productPrice.setText(cursor.getString(cursor.getColumnIndex("price_product")));
        viewHolder.productQuantity.setText(cursor.getString(cursor.getColumnIndex("amount_product")));
        if(cursor.getBlob(cursor.getColumnIndex("image_product")) != null){
            byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("image_product"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            viewHolder.imageView.setImageBitmap(bitmap);
        }

        Integer total_price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amount_product"))) * Integer.parseInt(cursor.getString(cursor.getColumnIndex("price_product")));
        viewHolder.totalPrice.setText(String.valueOf(total_price));

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!product_to_purchase.contains(buttonView.getTag())){
                        product_to_purchase.add(cursor.getPosition());
                        Log.d("CHECKED", String.valueOf(id_cart2));
                    }
                } else {
                    if(product_to_purchase.contains(cursor.getPosition())){
                        product_to_purchase.remove(cursor.getPosition());
                        Log.d("UNCHECKED", String.valueOf(cursor.getPosition()));
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
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

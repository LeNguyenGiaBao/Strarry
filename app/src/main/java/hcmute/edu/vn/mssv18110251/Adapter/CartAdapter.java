package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hcmute.edu.vn.mssv18110251.Model.Cart;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class CartAdapter extends CursorRecyclerViewAdapter<CartAdapter.ViewHolder> {


    public CartAdapter(Context context, Cursor cursor) {
        super(context,cursor);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, Cursor cursor) {
        viewHolder.productName.setText(cursor.getString(cursor.getColumnIndex("name_product")));
        viewHolder.productPrice.setText(cursor.getString(cursor.getColumnIndex("price_product")));
        viewHolder.productQuantity.setText(cursor.getString(cursor.getColumnIndex("amount_product")));
        if(cursor.getBlob(cursor.getColumnIndex("image_product")) != null){
            byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("image_product"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            viewHolder.imageView.setImageBitmap(bitmap);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.productNameCart);
            productPrice = view.findViewById(R.id.productPriceCart);
            productQuantity = view.findViewById(R.id.cart_quantity);
            imageView = view.findViewById(R.id.image_cartlist);
        }
    }
}

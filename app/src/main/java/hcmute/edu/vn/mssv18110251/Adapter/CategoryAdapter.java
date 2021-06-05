package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.DetailProduct;
import hcmute.edu.vn.mssv18110251.MainActivity;
import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.Model.Product;
import hcmute.edu.vn.mssv18110251.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categoryList;
    Context context;

    public CategoryAdapter(Context context, List<Category> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category category = this.categoryList.get(position);
        holder.textView.setText(category.getName());
        if(category.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

            public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.categoryName);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    int id_category = categoryList.get(position).getId();
                    String name_category = categoryList.get(position).getName();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("ID_CATEGORY", id_category);
                    intent.putExtra("NAME_CATEGORY", name_category);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}

package hcmute.edu.vn.mssv18110251.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.R;

public class CategorySpinnerAdapter extends BaseAdapter {

    List<Category> categoryList;
    Context context;
    LayoutInflater inflater;

    public CategorySpinnerAdapter(Context context, List<Category> categoryList){
        this.context = context;
        this.categoryList = categoryList;
        inflater = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return this.categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categoryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spn_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.txtViewText);
        name.setText(categoryList.get(position).getName());
        return convertView;
    }
}

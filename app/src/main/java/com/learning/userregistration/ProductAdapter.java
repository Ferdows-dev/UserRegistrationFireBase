package com.learning.userregistration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class ProductAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Product>productArrayList;

    public ProductAdapter(@NonNull Context context, ArrayList<Product>productArrayList) {
        super(context, R.layout.single_item_row,productArrayList);

        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.single_item_row,parent,false);
        TextView productName = convertView.findViewById(R.id.productList);
        productName.setText(productArrayList.get(position).getProductName());

        return convertView;
    }
}

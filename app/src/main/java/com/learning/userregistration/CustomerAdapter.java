package com.learning.userregistration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class CustomerAdapter extends ArrayAdapter<Customer> {

    private Context context;
    private ArrayList<Customer>customerArrayList;
    public CustomerAdapter(@NonNull Context context, @NonNull ArrayList<Customer>customerArrayList ) {
        super(context, R.layout.single_row, customerArrayList);

        this.context = context;
        this.customerArrayList = customerArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.single_row,parent,false);

        TextView customerName = convertView.findViewById(R.id.cName);
        TextView customerAddress = convertView.findViewById(R.id.cAddress);
        customerName.setText(customerArrayList.get(position).getCustomerName());
        customerAddress.setText(customerArrayList.get(position).getCustomerAddress());

        return convertView;
    }
}

package com.learning.userregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference customerReference;
    private EditText name,address;
    private String id;
    private ListView listView;
    private ArrayList<Customer> customerArrayList;

    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_ID = "customer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        customerReference = databaseReference.child("Customer");


        name = findViewById(R.id.customerName);
        address = findViewById(R.id.customerAddress);

        listView = findViewById(R.id.listView);
        customerArrayList = new ArrayList<>();

        customerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerArrayList.clear();

                for (DataSnapshot mSnapshot: dataSnapshot.getChildren()){

                     Customer customer =  mSnapshot.getValue(Customer.class);
//                    Customer customer1 = new Customer(customer.getCustomerID(),customer.getCustomerName(),customer.getCustomerAddress());
                     customerArrayList.add(customer);

                     CustomerAdapter customerAdapter = new CustomerAdapter(DatabaseActivity.this,customerArrayList);
                     listView.setAdapter(customerAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Customer customer = customerArrayList.get(position);

                Intent intent = new Intent(DatabaseActivity.this,AddProduct.class);
                intent.putExtra(CUSTOMER_NAME,customer.getCustomerName());
                intent.putExtra(CUSTOMER_ID,customer.getCustomerID());
                startActivity(intent);


            }
        });



    }

    public void addCustomer(View view) {
        id = customerReference.push().getKey();
        Customer customer = new Customer(id,name.getText().toString(),address.getText().toString());
        customerReference.child(id).setValue(customer);
    }
}
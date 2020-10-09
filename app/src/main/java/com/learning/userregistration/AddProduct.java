package com.learning.userregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    String name,id;
    TextView nameTV;
    EditText productNameET,productPriceET;
    DatabaseReference product_databaseReference;
    private ListView listView;
    private ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameTV = findViewById(R.id.customer_name);
        productNameET = findViewById(R.id.productName);
        productPriceET = findViewById(R.id.productPrice);
        listView = findViewById(R.id.productLV);
        productArrayList = new ArrayList<>();


       name = getIntent().getStringExtra(DatabaseActivity.CUSTOMER_NAME);
        id = getIntent().getStringExtra(DatabaseActivity.CUSTOMER_ID);

        nameTV.setText(name);

        product_databaseReference = FirebaseDatabase.getInstance().getReference("product").child(id);
        product_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productArrayList.clear();
                for (DataSnapshot mSnapshot: dataSnapshot.getChildren()){

                    Product product =  mSnapshot.getValue(Product.class);
//                    Customer customer1 = new Customer(customer.getCustomerID(),customer.getCustomerName(),customer.getCustomerAddress());
                    productArrayList.add(product);

                    ProductAdapter productAdapter = new ProductAdapter(AddProduct.this,productArrayList);
                    listView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addProducts(View view) {

        if (!TextUtils.isEmpty(productNameET.getText().toString())) {
            String productID = product_databaseReference.push().getKey();
            Product product = new Product(productID, productNameET.getText().toString(), productPriceET.getText().toString());
            product_databaseReference.child(productID).setValue(product);

            Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Failed,Please try again", Toast.LENGTH_SHORT).show();
        }

    }
}
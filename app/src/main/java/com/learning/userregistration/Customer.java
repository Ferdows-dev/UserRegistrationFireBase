package com.learning.userregistration;

import android.widget.EditText;

class Customer {
   private String customerID;
   private String customerName;
   private String customerAddress;

   public Customer(){

   }


    public Customer(String customerID, String customerName, String customerAddress) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}

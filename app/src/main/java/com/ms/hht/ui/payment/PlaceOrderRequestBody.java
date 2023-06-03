package com.ms.hht.ui.payment;

public class PlaceOrderRequestBody {
    int quote_id;
    int measurement_id;
    private PlaceOrder_billing_address billing_address;
    private PlaceOrder_shipping_address shipping_address;
    int quote_total;
    int discount_amount;
    int shipping_amount;
    int grand_total;
    int total_paid;
    double weight;
    String payment_method;

    public PlaceOrderRequestBody(int quote_id, int measurement_id, PlaceOrder_billing_address billing_address, PlaceOrder_shipping_address shipping_address, int quote_total, int discount_amount, int shipping_amount, int grand_total, int total_paid, double weight, String payment_method) {
        this.quote_id = quote_id;
        this.measurement_id = measurement_id;
        this.billing_address = billing_address;
        this.shipping_address = shipping_address;
        this.quote_total = quote_total;
        this.discount_amount = discount_amount;
        this.shipping_amount = shipping_amount;
        this.grand_total = grand_total;
        this.total_paid = total_paid;
        this.weight = weight;
        this.payment_method = payment_method;
    }

}


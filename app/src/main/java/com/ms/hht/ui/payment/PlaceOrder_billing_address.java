package com.ms.hht.ui.payment;

public class PlaceOrder_billing_address {
    int id;
    String city;
    String country_id;
    String postcode;
    String region;
    int region_id;
    String street;
    String telephone;
    int same_as_billing;

    public PlaceOrder_billing_address(int id, String city, String country_id, String postcode, String region, int region_id, String street, String telephone, int same_as_billing) {
        this.id = id;
        this.city = city;
        this.country_id = country_id;
        this.postcode = postcode;
        this.region = region;
        this.region_id = region_id;
        this.street = street;
        this.telephone = telephone;
        this.same_as_billing = same_as_billing;
    }
}

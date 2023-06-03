package com.ms.hht.data.request;

public class AddAndUpdateAddressRequest {
    String firstname;
    String lastname;
    String city;
    String country_id;
    String postcode;
    String region;
    int region_id;
    String street;
    String telephone;
    Boolean is_default_shipping;

    public AddAndUpdateAddressRequest(String firstname, String lastname, String city, String country_id, String postcode, String region, int region_id, String street, String telephone, Boolean is_default_shipping) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country_id = country_id;
        this.postcode = postcode;
        this.region = region;
        this.region_id = region_id;
        this.street = street;
        this.telephone = telephone;
        this.is_default_shipping = is_default_shipping;
    }
}
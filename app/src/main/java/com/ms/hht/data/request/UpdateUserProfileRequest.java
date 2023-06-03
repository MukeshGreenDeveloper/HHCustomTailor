package com.ms.hht.data.request;

public class UpdateUserProfileRequest {
    String firstname;
    String lastname;
    String email;
    String alternate_email;
    String gender;

    public UpdateUserProfileRequest(String firstname, String lastname, String email, String alternate_email, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.alternate_email = alternate_email;
        this.gender = gender;
    }
}

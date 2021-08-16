package com.example.clgproject1.Admin.Model;

public class AuthorityDataModel {
   AuthorityDataModel(){

   }
    String designation;

    String phone;
    String email;
    String image;
    String name;

    public AuthorityDataModel(String designation, String phone, String email, String image, String name, String organization, String uid) {
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.name = name;
        this.organization = organization;
        this.uid = uid;
    }

    String organization;
    String uid;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public AuthorityDataModel(String designation, String email, String image, String name, String organization, String uid) {
        this.designation = designation;
        this.email = email;
        this.image = image;
        this.name = name;
        this.organization = organization;
        this.uid = uid;
    }




    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }





}

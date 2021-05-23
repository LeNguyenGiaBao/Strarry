package hcmute.edu.vn.mssv18110251.Model;

import android.database.Cursor;

public class Account {
    private int id;
    private String name;
    private String password;
    private String phone;
    private String address;
    private int role;
    private String email;
    private String gender;


    public Account(String name, String password, String phone, String address, int role, String email, String gender) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.email = email;
        this.gender = gender;
    }

    public Account(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("id_account"));
        this.name = cursor.getString(cursor.getColumnIndex("name_account"));
        this.password = cursor.getString(cursor.getColumnIndex("password_account"));
        this.phone = cursor.getString(cursor.getColumnIndex("phone_account"));
        this.address = cursor.getString(cursor.getColumnIndex("address_account"));
        this.role = cursor.getInt(cursor.getColumnIndex("role_account"));
        this.email = cursor.getString(cursor.getColumnIndex("email_account"));
        this.gender = cursor.getString(cursor.getColumnIndex("gender_account"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}

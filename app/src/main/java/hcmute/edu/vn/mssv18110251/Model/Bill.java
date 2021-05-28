package hcmute.edu.vn.mssv18110251.Model;

public class Bill {
    private int id;
    private int id_account;
    private int price;
    private float discount;
    private String phone;
    private String address;

    public Bill(int id, int id_account, int price, float discount, String phone, String address) {
        this.id = id;
        this.id_account = id_account;
        this.price = price;
        this.discount = discount;
        this.phone = phone;
        this.address = address;
    }

    public Bill(int id_account, int price, float discount, String phone, String address) {
        this.id_account = id_account;
        this.price = price;
        this.discount = discount;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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
}

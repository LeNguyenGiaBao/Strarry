package hcmute.edu.vn.mssv18110251.Model;

public class Bill_Product {
    private int id_bill;
    private int id_product;
    private int amount_product;


    public Bill_Product(int id_bill, int id_product, int amount_product) {
        this.id_bill = id_bill;
        this.id_product = id_product;
        this.amount_product = amount_product;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getAmount_product() {
        return amount_product;
    }

    public void setAmount_product(int amount_product) {
        this.amount_product = amount_product;
    }
}

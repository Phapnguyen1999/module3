package model;

public class Product {
    private int idProduct;
    private String nameProduct;
    private int quantity;
    private double price;
    private String category;
    public Product(){

    }
    public Product(int idProduct, String nameProduct,int quantity, double price, String category){
        this.idProduct=idProduct;
        this.nameProduct=nameProduct;
        this.quantity=quantity;
        this.price=price;
        this.category=category;
    }
    public Product( String nameProduct,int quantity, double price, String category){
        this.nameProduct=nameProduct;
        this.quantity=quantity;
        this.price=price;
        this.category=category;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package model;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private int id;
    @NotEmpty(message = "Name not null")
    @Length(min=3, max=30, message = "Length of name from 3 - 30 character")
    private String name;
    private String img;
    @Min(1)
    @Max(10000000)
    private int quantity;
    @Min(1)
    @Max(1000000000)
    private double price;
    private int idCategory;
    private int deleted;
    public Product(String name,String img,int quantity,double price, int idCategory,int deleted){
        this.name=name;
        this.img=img;
        this.quantity=quantity;
        this.price=price;
        this.idCategory=idCategory;
        this.deleted=deleted;
    }


    public Product(String name, String img, int quantity, double price, int idCategory) {
        this.name=name;
        this.img=img;
        this.quantity=quantity;
        this.price=price;
        this.idCategory=idCategory;
    }
}

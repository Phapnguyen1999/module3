package model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private int id;
    @NotEmpty(message = "Name not null")
    @Length(max = 40,min = 3)
    private String name;
    @Max(1000000000)
    @Min(1)
    private double price;
    @Max(1000000000)
    @Min(1)
    private int quantity;
    private String color;
    private String description;
    private int idCategory;
    public Product(String name, double price, int quantity, String color, String description, int idCategory) {
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.color=color;
        this.description=description;
        this.idCategory=idCategory;
    }
}

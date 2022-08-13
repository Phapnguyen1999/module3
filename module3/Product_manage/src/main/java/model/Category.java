package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private int id;
    private String name;
    public Category(String name){
        this.name=name;
    }
}

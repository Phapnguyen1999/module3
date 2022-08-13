package model;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Country {
    @NotEmpty(message = "Id not empty")
    private int id;
    @NotEmpty(message = "Name not empty")
    private String name;
}

package model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    protected int id;

    @NotEmpty(message = "Name not empty")
    @Length(min = 3, max = 10, message = "Lenght of Name form 3 - 10 character ")
    protected String name;

    @NotEmpty(message = "Email not empty")
    @Email(message = "Email format not right")
    protected String email;
    protected int idCountry;

    @NotEmpty(message = "Password not empty")
    //@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Format password not right")
    protected String password;


    public User(String name, String email, int idCountry, String password) {
        this.name = name;
        this.email = email;
        this.idCountry = idCountry;
        this.password = password;
    }
}

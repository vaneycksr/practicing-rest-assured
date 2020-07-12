package domain;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    // atributos que podem vim no JSON
    private int id;
    private String email;
    @JsonAlias("name")
    private String first_name;
    private String last_name;
    private String avatar;
    private String job;
    private String password;

}

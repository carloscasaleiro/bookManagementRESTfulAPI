package CC.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private int yearPublished;
    private String isbn;
    private double price;
}
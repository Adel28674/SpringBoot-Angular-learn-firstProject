package com.example.firstProject.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String author;

  @Temporal(TemporalType.TIMESTAMP)
  @Column
    private Date published_date;

    @Column
    private Long price;

    @Column
    private String genre;

    public BookEntity(String name, String author, Date published_date, Long price, String genre){
        this.name = name;
        this.author = author;
        this.published_date = published_date;
        this.price = price;
        this.genre = genre;
    }

}

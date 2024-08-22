package com.example.firstProject.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter

@Table(name="book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private Date published_date;

    @Column
    private Long price;

    @Column
    private String genre;

    public BookEntity(){}

    public BookEntity(String name, String author, Date published_date, Long price, String genre){
        this.name = name;
        this.author = author;
        this.published_date = published_date;
        this.price = price;
        this.genre = genre;
    }

}

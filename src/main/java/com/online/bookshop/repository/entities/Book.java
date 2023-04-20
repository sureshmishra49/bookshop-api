package com.online.bookshop.repository.entities;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BOOK")
@SequenceGenerator(name = "default_gen", sequenceName = "BOOKS_SEQ", allocationSize = 1)
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Column
    private Long id;
    @Column(name = "NAME", length = 255)
    private String name;
    @Column(name = "DESCRIPTION", length = 400)
    private String description;
    @Column(name = "AUTHOR", length = 255)
    private String author;
    @Column(name = "BOOKTYPE", length = 255)
    private String bookType;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "ISBN", length = 255)
    private String isbn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Book() {
    }

    public Book(String name, String description, String author, String bookType, BigDecimal price, String isbn, Date createdDate, Date updatedDate) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.bookType = bookType;
        this.price = price;
        this.isbn = isbn;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Books [id=" + id + ", name=" + name + ", desc=" + description + ", author=" + author + "]";
    }

}

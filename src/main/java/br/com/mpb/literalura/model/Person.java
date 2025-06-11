package br.com.mpb.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @JsonAlias("birth_year")
    private Integer birthYear;
    @JsonAlias("death_year")
    private Integer deathYear;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Person() {

    }

    public Person(Long id, String name, Integer birthYear, Integer deathYear, Book book) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer death_year) {
        this.deathYear = death_year;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return String.format(
                "🧑 Autor:\n" +
                        "               ---------------------------\n" +
                        "               📛 Nome: %s\n" +
                        "               🎂 Ano de Nascimento: %s\n" +
                        "               🕊️ Ano de Falecimento: %s\n" +
                        "               ---------------------------\n           ",
                        name,
                        birthYear != null ? birthYear : "Desconhecido",
                        deathYear != null ? deathYear : "Vivo!"
        );
    }
}

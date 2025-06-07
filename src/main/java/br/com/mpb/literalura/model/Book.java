package br.com.mpb.literalura.model;

import java.util.List;

public class Book {
    private Long id;
    private String title;
    private List<Person> authors;
    private List<String> languages;
    private int downloadCount;
}

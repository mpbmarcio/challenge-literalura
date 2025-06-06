package br.com.mpb.literalura.model;

import java.util.List;
import java.util.Map;

public class Book {
    private Long id;
    private String title;
    private List<Person> authors;
    private List<String> summaries;
    private List<Person> translators;
    private List<String> subjects;
    private List<String>  bookshelves;
    private List<String> languages;
    private boolean copyright;
    private String mediaType;
    private Map<String, String> formats;
    private int downloadCount;
}

package br.com.mpb.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Person> authors;
    private List<String> languages;
    @JsonAlias("download_count")  private int downloadCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
        authors.forEach(e -> e.setBook(this));
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return String.format(
                "📚 Livro encontrado:\n" +
                        "------------------------------------------------\n" +
                        "📖 Título: %s\n" +
                        "✍️ Autores: %s\n" +
                        "🌍 Idiomas: %s\n" +
                        "📥 Nº Downloads: %d\n" +
                        "------------------------------------------------\n",
                //title, authors.stream().map(Person::getName).collect(Collectors.toList()), languages, downloadCount
                title, authors, languages, downloadCount
        );
    }
}

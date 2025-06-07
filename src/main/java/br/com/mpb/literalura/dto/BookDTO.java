package br.com.mpb.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(String title,
                      List<PersonDTO> authors,
                      List<String> languages,
                      @JsonAlias("download_count") int downloadCount) {

    @Override
    public String toString() {
        return String.format(
                "📚 Livro:\n" +
                        "--------------------------------\n" +
                        "📖 Título: %s\n" +
                        "✍️ Autores: %s\n" +
                        "🌍 Idiomas: %s\n" +
                        "📥 Nº Downloads: %d\n" +
                        "--------------------------------",
                title, authors.stream().map(PersonDTO::name).collect(Collectors.toList()), languages, downloadCount
        );
    }


}

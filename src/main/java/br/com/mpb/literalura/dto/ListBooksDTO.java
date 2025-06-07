package br.com.mpb.literalura.dto;

import java.util.List;

public record ListBooksDTO(Long count,
                           String next,
                           String previous,
                           List<BookDTO> results) {

}
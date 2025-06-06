package br.com.mpb.literalura.principal;

import br.com.mpb.literalura.service.ConsumoApi;
import br.com.mpb.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    String json = "";

    public void exibir() {
        System.out.println("Digite o nome do livro");
        String livro = leitura.nextLine();
        json = consumoApi.obterDados(ENDERECO + livro);
        System.out.println(json);
    }
}

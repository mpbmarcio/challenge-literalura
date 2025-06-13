package br.com.mpb.literalura.principal;

import br.com.mpb.literalura.model.Book;
import br.com.mpb.literalura.model.ListBooks;
import br.com.mpb.literalura.model.Person;
import br.com.mpb.literalura.repository.BookRepository;
import br.com.mpb.literalura.repository.PersonRepository;
import br.com.mpb.literalura.service.ConsumoApi;
import br.com.mpb.literalura.service.ConverteDados;

import java.util.*;

public class Principal {
    private BookRepository bookRepository;
    private PersonRepository personRepository;
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final String ENDERECOALL = "https://gutendex.com/books/?page=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    String json = "";
    String confirmacao = "N";

    public Principal(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            menu();
            desenhaSeta();

            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); // Limpa buffer após leitura numérica
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida! Digite apenas números.");
                leitura.nextLine(); // Limpa entrada incorreta
                pausa();
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarLivroNaAPIEGravarBD();
                    pausa();
                    break;
                case 2:
                    listarLivrosCadastrados();
                    pausa();
                    break;
                case 3:
                    listarAutoresCadastrados();
                    pausa();
                    break;
                case 4:
                    buscarAutoresPorAno();
                    pausa();
                    break;
                case 5:
                    menuIdioma();
                    desenhaSeta();
                    buscarAutoresPorIdioma();
                    pausa();
                    break;
                case 6:
                    buscarEstatisticasLivrosAPI();
                    pausa();
                    break;
                case 7:
                    buscarTop10LivrosBD();
                    pausa();
                    break;
                case 8:
                    buscarAutorPorNomeBD();
                    pausa();
                    break;
                case 9:
                    buscarEstatisticasAutoresAno();
                    pausa();
                    break;
                case 0:
                    System.out.println("Saindo do Sistema...");
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente.");
                    pausa();
            }
        } while (opcao != 0);
    }

    private void buscarEstatisticasAutoresAno() {
        System.out.println("Estatísticas dos Anos de Nasc. e Morte:");
        List<Person> lista = personRepository.findAllByOrderByNameAsc();

        IntSummaryStatistics statistics1 = lista.stream()
                .mapToInt(Person::getBirthYear)
                .summaryStatistics();
        desenhaLinha();
        IntSummaryStatistics statistics2 = lista.stream()
                .mapToInt(Person::getDeathYear)
                .summaryStatistics();

        System.out.println("📊 Estatísticas dos Autores:");
        desenhaLinha();
        System.out.println("Total de " + statistics1.getCount() + " Autores analisados.");
        desenhaLinha();
        System.out.println("📅 ANO DE NASCIMENTO:");
        System.out.printf("📊 Média de Nasc.   : %.2f%n", statistics1.getAverage());
        System.out.printf("📉 Mínimo           : %d%n", statistics1.getMin());
        System.out.printf("📈 Máximo           : %d%n", statistics1.getMax());
        desenhaLinha();
        System.out.println("⚰️ ANO DE MORTE:");
        System.out.printf("📊 Média de Morte   : %.2f%n", statistics2.getAverage());
        System.out.printf("📉 Mínimo           : %d%n", statistics2.getMin());
        System.out.printf("📈 Máximo           : %d%n", statistics2.getMax());
        desenhaLinha();

        desenhaLinha();
    }

    private void buscarEstatisticasLivrosAPI() {
        int totalPaginas = -1;

        while (totalPaginas <= 0) {
            try {
                System.out.println("📄 Digite a quantidade de páginas (1 até ...):");
                System.out.println("📚 Atualmente a API conta com 76076 livros");
                System.out.println("🗂️ cadastrados, gerando aprox. 2377 páginas.");
                System.out.println("⏳ Isso levaria horas! 😅");

                desenhaSeta();
                totalPaginas = Integer.parseInt(leitura.nextLine().trim());

                if (totalPaginas <= 0) {
                    System.out.println("❌ Número inválido! Digite um valor maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Digite apenas números inteiros.");
            }
        }
        List<Book> books = buscarTodosOsLivros(totalPaginas);
        IntSummaryStatistics statistics = books.stream()
                .mapToInt(Book::getDownloadCount)
                .summaryStatistics();
        desenhaLinha();
        System.out.println("📊 Estatísticas de Downloads:");
        desenhaLinha();
        System.out.printf("📚 Qt. de livros analisados : %d%n", statistics.getCount());
        System.out.printf("📥 Total de downloads       : %d%n", statistics.getSum());
        System.out.printf("📊 Média de downloads       : %.2f%n", statistics.getAverage());
        System.out.printf("📉 Menor número de downloads: %d%n", statistics.getMin());
        System.out.printf("📈 Maior número de downloads: %d%n", statistics.getMax());
        desenhaLinha();
    }

    private void buscarAutoresPorIdioma() {
        String idioma = leitura.nextLine().trim().replaceAll("[^a-zA-Z\\-]", "");
        ;
        List<Book> lista = bookRepository.buscarPorIdioma(idioma);
        if (lista.isEmpty()) {
            System.out.println("❌ Nenhum livro encontrado para o idioma: " + idioma);
            return;
        }
        System.out.println(lista);
        System.out.println("Quantidade de Livros 📚: " + lista.size());
        desenhaLinha();
    }

    private void buscarAutoresPorAno() {
        int ano = -1;

        while (ano < 0) {
            try {
                System.out.println("Digite um ano para pesquisa: 📅");
                desenhaSeta();
                ano = Integer.parseInt(leitura.nextLine().trim());

                if (ano < 0) {
                    System.out.println("❌ Ano inválido! Digite um valor maior ou igual a zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Digite apenas números inteiros.");
            }
        }
        desenhaLinha();
        List<Person> lista = personRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(ano, ano);
        if (lista.size() == 0) {
            System.out.println("❌ Nenhum Autor encontrado!");
        } else {
            System.out.println(lista);
        }
    }

    private void listarAutoresCadastrados() {
        List<Person> lista = personRepository.findAll();
        System.out.println(lista);
    }

    private void listarLivrosCadastrados() {
        List<Book> lista = bookRepository.findAll();
        System.out.println(lista);
    }

    private void buscarLivroNaAPIEGravarBD() {
            System.out.print("📚 Digite o nome do livro: ");
            desenhaSeta(); String livro = leitura.nextLine().trim().replaceAll(" ", "%20");
            desenhaLinha();
            json = consumoApi.obterDados(ENDERECO + livro);
            ListBooks listBook = converteDados.obterDados(json, ListBooks.class);

            if (listBook.getResults() != null && !listBook.getResults().isEmpty()) {
                Book book = listBook.getResults().get(0);
                System.out.println(book);
                pausa();

                System.out.println("📚 Deseja gravar livro no Banco de Dados? 💾 (S/N)");
                desenhaSeta();  confirmacao = leitura.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    // Verifica se o livro já está no banco antes de salvar
                    Optional<Book> bookExistente = bookRepository.findByTitle(book.getTitle());
                    if (bookExistente.isPresent()) {
                        System.out.println("ℹ️ Livro já cadastrado! Atualizando informações...");
                        Book livroAtualizado = bookExistente.get();
                        livroAtualizado.setDownloadCount(book.getDownloadCount());
                        bookRepository.save(livroAtualizado);
                        System.out.println("✅ Dados atualizados com sucesso! 💾");
                    } else {
                        bookRepository.save(book);
                        System.out.println("✅ Livro gravado com sucesso! 💾");
                    }
                }
            } else {
                System.out.println("❌ Nenhum livro encontrado com esse nome.");
            }
    }

    private void menu() {
        String menu = """
        ------------------------------------------------
        📖 Menu de Opções:
        ------------------------------------------------
        1️⃣ - Buscar Livro pelo Título (API Gutendex)
        2️⃣ - Listar Livros cadastrados 📚
        3️⃣ - Listar Autores cadastrados ✍️
        4️⃣ - Listar Autores vivos em determinado ano 🏛️
        5️⃣ - Listar Livros em um determinado idioma 🌎
        6️⃣ - Estatísticas de Livros (API Gutendex) 📊
        7️⃣ - Top 10 Livros (Mais baixados) (BD) 🔥
        8️⃣ - Buscar Autor por nome (BD) 🔍
        9️⃣ - Estatísticas Autor (BD) 📈
        
        🚪 0 - Sair
        ------------------------------------------------
        📌 Escolha uma opção:
        ------------------------------------------------
        """;
        System.out.println(menu);
    }

    private void menuIdioma() {
        String msg = """
        ------------------------------------------------
        🌍 Digite a abreviação do idioma:
        ------⬇️----------------------------------------
            🇧🇷 pt - Português
            🇪🇸 es - Espanhol
            🇫🇷 fr - Francês
            🇺🇸 en - Inglês
        ------------------------------------------------
        ✏️ Escolha uma opção:
        ------------------------------------------------
        """;
        System.out.println(msg);
    }

    private void desenhaSeta() {
        System.out.print("➡️ ");
    }

    private void desenhaLinha() {
        System.out.println("------------------------------------------------");
    }

    private void pausa() {
        System.out.print("Pressione Enter para continuar...");
        leitura.nextLine();
    }

    private List<Book> buscarTodosOsLivros(int totalPaginas) {
        List<Book> allBooks = new ArrayList<>();

        for (int i = 1; i <= totalPaginas; i++) {
            json = consumoApi.obterDados(ENDERECOALL + i);
            ListBooks listBook = converteDados.obterDados(json, ListBooks.class);
            desenhaLinha();
            System.out.println("Página atual: " + i + "\n");
            System.out.println(listBook);
            allBooks.addAll(listBook.getResults());
        }

        return allBooks;
    }

    private void buscarAutorPorNomeBD() {
        System.out.println("Digite o nome do Autor:");
        desenhaSeta();
        String nomeAutor = leitura.nextLine();
        List<Person> lista = personRepository.findByNameContainingIgnoreCaseOrderByNameAsc(nomeAutor);
        System.out.println(lista);
    }

    private void buscarTop10LivrosBD() {
        List<Book> lista = bookRepository.findTop10ByOrderByDownloadCountDesc();
        System.out.println(lista);
    }

}

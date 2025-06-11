# Literalura 📚

Projeto para busca, listagem e análise estatística de livros e autores usando a API Gutendex.

## 🚀 Tecnologias

Este projeto utiliza as seguintes tecnologias:
- **Java** 🟦
- **Spring Boot** 🌱
- **JPA/Hibernate** 🗄️
- **API Gutendex** 🌍

## 📖 Funcionalidades

- Buscar livro pelo título via API Gutendex
- Listar livros e autores cadastrados no banco de dados
- Filtrar autores por ano de nascimento/morte
- Buscar livros por idioma
- Obter estatísticas sobre livros e autores
- Top 10 livros mais baixados

## 🛠️ Como executar

1. 📥 Clone o repositório
   ```bash
   git clone git@github.com:mpbmarcio/challenge-literalura.git
   cd literalura
   mvn spring-boot:run
   ```
2. ⚙️ Configure o banco de dados no `application.properties`
   ```properties
   spring.application.name=literalura
   spring.datasource.url=jdbc:postgresql://${DB_HOST}/literalura
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

## 📖 Documentação das Classes

## 🏛️ Classe: `Principal`

A classe `Principal` é a responsável pelo fluxo do sistema e interação com o usuário, oferecendo um menu com diversas funcionalidades para busca, listagem e análise de livros e autores.

### 🔹 Principais características

- **Gerenciamento do menu**
    - Exibe um menu interativo com opções de consulta e análise.
    - Captura e trata entradas do usuário para evitar erros.

- **Integração com APIs externas**
    - Utiliza a API Gutendex para buscar livros por título e idioma.
    - Converte dados JSON recebidos em objetos Java.

- **Manipulação do banco de dados**
    - Registra e consulta livros e autores armazenados.
    - Obtém estatísticas sobre autores e livros cadastrados.

- **Métodos principais**
    - `exibirMenu()`: Controla o fluxo da aplicação e gerencia as opções do usuário.
    - `buscarLivroNaAPIEGravarBD()`: Obtém dados da API e armazena no banco.
    - `buscarEstatisticasLivrosAPI()`: Analisa estatísticas de livros.
    - `buscarAutoresPorAno()`: Filtra autores vivos em determinado ano.
    - `buscarEstatisticasAutoresAno()`: Retorna estatísticas de nascimento e falecimento dos autores.

### 💡 Importância

Essa classe é o coração da aplicação, conectando a interface do usuário com a lógica de manipulação de dados e integração com APIs externas.

---

## 📖 Classe: `Book`

A classe `Book` representa um livro dentro do sistema, armazenando informações essenciais sobre título, autores, idiomas e número de downloads.

### 🔹 Principais características

- **Mapeamento JPA**
   - Definida como entidade do banco de dados com `@Entity`.
   - Associada à tabela `"book"` por meio de `@Table(name = "book")`.

- **Identificação única**
   - Atributo `id` como chave primária (`@Id`).
   - O título (`title`) é único, garantindo que não haja duplicatas.

- **Relacionamento com `Person`**
   - Um livro pode ter múltiplos autores (`@OneToMany`), com carregamento `EAGER`.

- **Idiomas disponíveis**
   - Lista de idiomas (`languages`), armazenada como `List<String>`.

- **Estatísticas**
   - Número de downloads (`downloadCount`), obtido da API via `@JsonAlias("download_count")`.

### 🔧 Métodos da classe

- **Getters e Setters**
   - Permitem manipulação dos atributos da classe.

- **Método `toString()`**
   - Retorna uma representação formatada do livro, incluindo título, autores, idiomas e número de downloads.

---

## 🧑 Classe: `Person`

A classe `Person` representa um autor dentro do sistema, armazenando informações como nome, ano de nascimento, ano de falecimento e sua relação com um livro.

### 🔹 Principais características

- **Mapeamento JPA**
   - Definida como entidade do banco de dados com `@Entity`.
   - Associada à tabela `"person"` por meio de `@Table(name = "person")`.

- **Identificação única**
   - Atributo `id` como chave primária (`@Id`) com geração automática de valores (`@GeneratedValue(strategy = GenerationType.IDENTITY)`).
   - O nome (`name`) é único para evitar duplicações.

- **Associação com `Book`**
   - Um autor está vinculado a um livro (`@ManyToOne` com `@JoinColumn(name = "book_id")`).

- **Campos de nascimento e morte**
   - Armazena o ano de nascimento (`birthYear`) e ano de falecimento (`deathYear`).
   - Usa `@JsonAlias("birth_year")` e `@JsonAlias("death_year")` para mapear esses valores ao receber dados da API.

### 🔧 Métodos da classe

- **Construtores**
   - Um construtor padrão e outro para inicialização com todos os atributos.

- **Getters e Setters**
   - Permitem a manipulação dos dados do autor.

- **Método `toString()`**
   - Retorna uma representação formatada do autor, incluindo nome, ano de nascimento e ano de falecimento.
   - Se o ano de falecimento for `null`, assume que o autor ainda está vivo.

---
## 🗄️ Interface: `PersonRepository`

A interface `PersonRepository` é responsável pela manipulação dos dados da entidade `Person` no banco de dados, permitindo operações de consulta específicas.

### 🔹 Características principais

- **Extende `JpaRepository<Person, Long>`**
   - Herda métodos padrão para operações básicas de CRUD (Create, Read, Update, Delete).

- **Consultas personalizadas**
   - `findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int ano, int ano1)`:
      - Retorna autores que estavam vivos em um determinado ano.
   - `findByNameContainingIgnoreCaseOrderByNameAsc(String nomeAutor)`:
      - Busca autores por nome, ignorando maiúsculas/minúsculas e ordenando alfabeticamente.
   - `findAllByOrderByNameAsc()`:
      - Retorna todos os autores, ordenados pelo nome em ordem crescente.

### 💡 Importância

Essa interface facilita a comunicação com o banco de dados ao utilizar **Spring Data JPA**, tornando as consultas mais eficientes e eliminando a necessidade de implementar manualmente métodos de busca.

---

## 📚 Interface: `BookRepository`

A interface `BookRepository` é responsável pela manipulação dos dados da entidade `Book` no banco de dados, permitindo operações de consulta específicas.

### 🔹 Características principais

- **Extende `JpaRepository<Book, Long>`**
   - Herda métodos padrão para operações de CRUD (Create, Read, Update, Delete).

- **Consultas personalizadas**
   - `findByTitle(String title)`:
      - Busca um livro pelo título, retornando um `Optional<Book>` para evitar `null`.
   - `buscarPorIdioma(String languages)`:
      - Utiliza uma consulta SQL nativa (`@Query`) para encontrar livros que contenham um determinado idioma na lista de idiomas armazenada no banco.
   - `findTop10ByOrderByDownloadCountDesc()`:
      - Retorna os 10 livros mais baixados, ordenados em ordem decrescente com base no número de downloads.

### 💡 Importância

Essa interface simplifica o acesso ao banco de dados com **Spring Data JPA**, permitindo buscas eficientes e eliminando a necessidade de implementar métodos manualmente.

---

## 📂 Estrutura do Projeto

📂 src  
┣ 📂 br.com.mpb.literalura.assets  
┣ 📂 br.com.mpb.literalura.controller  
┣ 📂 br.com.mpb.literalura.dto  
┣ 📂 br.com.mpb.literalura.model  
┣ 📂 br.com.mpb.literalura.principal  
┣ 📂 br.com.mpb.literalura.repository  
┗ 📂 br.com.mpb.literalura.service

# Conclusão
Este programa é um excelente exemplo para o aprendizado de integração Sprint Boot, JPA e API Online. 

# Guia de contribuição:

Qualquer melhoria e correções serão bem vindas, entre em contato.

# Contato:

[![imagem linkedin](src/main/java/br/com/mpb/literalura/assets/linkedin_small.png)](https://www.linkedin.com/in/mpbmarcio-dev/)

# Contribuidores:

[![imagem git](src/main/java/br/com/mpb/literalura/assets/github_small.png)](https://github.com/mpbmarcio/)

# Agradecimentos Especiais:

[![imagem alura](src/main/java/br/com/mpb/literalura/assets/alura.jpg)](https://www.alura.com.br/)
[![imagem one](src/main/java/br/com/mpb/literalura/assets/one.png)](https://www.oracle.com/br/education/oracle-next-education/)
package br.com.mpb.literalura.dto;

public record PersonDTO (String name,
                         Integer birth_year,
                         Integer death_year) {

    @Override
    public String toString() {
        return String.format(
                "🧑 Autor:\n" +
                        "---------------------------\n" +
                        "📛 Nome: %s\n" +
                        "🎂 Ano de Nascimento: %s\n" +
                        "🕊️ Ano de Falecimento: %s\n" +
                        "---------------------------",
                name,
                birth_year != null ? birth_year : "Desconhecido",
                death_year != null ? death_year : "Vivo!"
        );
    }

}

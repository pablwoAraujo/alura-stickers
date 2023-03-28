import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

	public static final String ANSI_RED = "\u001B[41m";
	public static final String ANSI_REGULAR = "\u001B[m";
	public static final String ANSI_BOLD = "\u001B[1m";

	public static void main(String[] args) throws Exception {
		// fazer uma conexão http e buscar o top 250
		String urlTopMovies = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
		HttpClient client = HttpClient.newHttpClient();
		URI address = URI.create(urlTopMovies);

		HttpRequest request = HttpRequest.newBuilder(address).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		// extrair so os dados que interessam (titulo, poster, classificação)
		String body = response.body();
		JsonParser parser = new JsonParser();
		List<Map<String, String>> filmList = parser.parse(body);

		// exibir e manipular os dados
		for (Map<String, String> film : filmList) {
			System.out.println(ANSI_BOLD + "Title: " + ANSI_REGULAR + film.get("title"));
			System.out.println(ANSI_BOLD + "URL image: " + ANSI_REGULAR + film.get("image"));

			double rating = Double.parseDouble(film.get("imDbRating"));
			System.out.print(ANSI_BOLD + "Rating: " + rating + " ");

			int stars = (int) rating;
			for (int i = 0; i < stars; i++) {
				System.out.print(ANSI_RED + "❤️");
			}

			int emptyStars = 10 - stars;
			for (int i = 0; i < emptyStars; i++) {
				System.out.print("🤍");
			}

			System.out.println(ANSI_REGULAR);
			System.out.println();
		}

	}
}

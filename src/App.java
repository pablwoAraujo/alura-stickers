import java.io.InputStream;
import java.net.URL;
import java.util.List;

import extractors.ContentExtractor;
import extractors.ImDBContentExtractor;
import extractors.NASAContentExtractor;
import http.Client;
import models.Content;
import stickers.StickerGenerator;

public class App {

	public static void main(String[] args) throws Exception {
		String urlTopMovies = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
		String nasaURL = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/NASA-APOD.json";

		var httpClient = new Client();
		String imdbBody = httpClient.getBody(urlTopMovies);
		String nasaBody = httpClient.getBody(nasaURL);

		ContentExtractor nasaExtractor = new NASAContentExtractor();
		ContentExtractor imdbExtractor = new ImDBContentExtractor();

		List<Content> imdbContentList = imdbExtractor.extractor(imdbBody);
		List<Content> nasaContentList = nasaExtractor.extractor(nasaBody);

		StickerGenerator stickerGenerator = new StickerGenerator();

		for (Content content : nasaContentList) {
			String title = content.title();
			String image = content.urlImage();
			System.out.println(title + " | " + image);

			String titleWithoutSpecialCharacters = title.replaceAll("[^a-zA-Z0-9]+", "");

			InputStream inputStream = new URL(image).openStream();
			stickerGenerator.generator(inputStream, "TOPZERA", "assets/" + titleWithoutSpecialCharacters + ".png");
		}
	}
}

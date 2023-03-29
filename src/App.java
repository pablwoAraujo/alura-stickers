import java.io.InputStream;
import java.net.URL;
import java.util.List;

import enums.API;
import extractors.ContentExtractor;
import http.Client;
import models.Content;
import stickers.StickerGenerator;

public class App {

	public static void main(String[] args) throws Exception {
		API api = API.NASA;

		String url = api.getUrl();
		Client httpClient = new Client();

		String body = httpClient.getBody(url);
		ContentExtractor extractor = api.getExtractor();

		List<Content> contentList = extractor.extractor(body);
		StickerGenerator stickerGenerator = new StickerGenerator();

		for (Content content : contentList) {
			String title = content.title();
			String image = content.urlImage();
			System.out.println(title + " | " + image);

			String titleWithoutSpecialCharacters = title.replaceAll("[^a-zA-Z0-9]+", "");

			InputStream inputStream = new URL(image).openStream();
			stickerGenerator.generator(inputStream, "TOPZERA", "assets/" + titleWithoutSpecialCharacters + ".png");
		}
	}
}

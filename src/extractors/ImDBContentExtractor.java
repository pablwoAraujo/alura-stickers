package extractors;

import java.util.List;
import java.util.Map;

import models.Content;
import parser.JsonParser;

public class ImDBContentExtractor implements ContentExtractor {

	public List<Content> extractor(String json) {
		JsonParser parser = new JsonParser();
		List<Map<String, String>> dataList = parser.parse(json);

		return dataList.stream()
			.map(data -> new Content(data.get("title"), data.get("image")))
			.toList();
	}
}

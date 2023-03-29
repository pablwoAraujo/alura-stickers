package extractors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Content;
import parser.JsonParser;

public class NASAContentExtractor implements ContentExtractor {

	public List<Content> extractor(String json) {
		List<Content> contentList = new ArrayList<Content>();

		JsonParser parser = new JsonParser();
		List<Map<String, String>> dataList = parser.parse(json);

		for (Map<String, String> data : dataList) {
			String title = data.get("title");
			String urlImage = data.get("url");
			contentList.add(new Content(title, urlImage));
		}
		return contentList;
	}
}

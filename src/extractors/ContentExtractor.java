package extractors;

import java.util.List;

import models.Content;

public interface ContentExtractor {

	public List<Content> extractor(String json);

}

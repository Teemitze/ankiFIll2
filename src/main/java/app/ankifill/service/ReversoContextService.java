package app.ankifill.service;

import app.ankifill.model.Examples;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ReversoContextService {

    public Examples getExamples(String word) {
        final Document doc;
        try {
            doc = Jsoup.connect("https://context.reverso.net/перевод/английский-русский/" + word).get();
            final Element elementWithExample = getFirstExampleElement(doc);
            return new Examples(parseEngExample(elementWithExample), parseRusExample(elementWithExample));
        } catch (IOException e) {
            return new Examples(null, null);
        }
    }


    private Element getFirstExampleElement(Document doc) {
        return doc.getElementsByClass("example").first();
    }

    private String parseEngExample(Element element) {
        return element.getElementsByClass("src ltr").text();
    }

    private String parseRusExample(Element element) {
        return element.getElementsByClass("trg ltr").text();
    }
}

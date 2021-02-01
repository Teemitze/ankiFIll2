package app.ankifill.service;

import app.ankifill.model.Examples;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReversoContextService {

    public List<Examples> getExamples(String word) {
        final Document doc;
        try {
            doc = Jsoup.connect("https://context.reverso.net/перевод/английский-русский/" + word).get();
            final List<Element> elementsWithExample = getExamplesElement(doc);
            return elementsWithExample.stream().map(e -> new Examples(parseEngExample(e), parseRusExample(e))).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }


    private List<Element> getExamplesElement(Document doc) {
        return new ArrayList<>(doc.getElementsByClass("example"));
    }

    private String parseEngExample(Element element) {
        return element.getElementsByClass("src ltr").text();
    }

    private String parseRusExample(Element element) {
        return element.getElementsByClass("trg ltr").text();
    }
}

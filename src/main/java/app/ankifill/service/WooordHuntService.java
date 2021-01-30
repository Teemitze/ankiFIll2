package app.ankifill.service;

import app.ankifill.model.AnkiCard;
import app.ankifill.model.Examples;
import app.ankifill.utill.AnkiFillUtil;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WooordHuntService {


    private static final String URL = "https://wooordhunt.ru";

    @SneakyThrows
    public AnkiCard fillAnkiCard(String word) {
        final Document doc = Jsoup.connect(URL + "/word/" + word).get();

        final AnkiCard ankiCard = new AnkiCard();
        ankiCard.setWord(word);

        ankiCard.setTranscription(
                deletePipeLineFromTranscription(parseTranscription(doc))
        );

        ankiCard.setTranslation(parseTranslate(doc));


        final String engExample = deleteENSP(parseEngExample(doc));
        final String rusExample = deleteENSP(
                deleteTrigramFromRusExample(parseRusExample(doc))
        );

        Examples examples = new Examples(engExample, rusExample);


        ankiCard.setExamples(examples);

        ankiCard.setSoundURL(
                AnkiFillUtil.buildSoundURL(URL + parseUrlSound(doc))
        );

        return ankiCard;
    }

    private String parseTranscription(Document doc) {
        return Optional.ofNullable(doc.getElementById("uk_tr_sound"))
                .map(e -> e.getElementsByClass("transcription"))
                .map(Elements::text).orElse(null);
    }

    private String parseTranslate(Document doc) {
        return Optional.ofNullable(doc.getElementsByClass("tr"))
                .map(Elements::first)
                .map(e -> e.getElementsByTag("span"))
                .map(Elements::first)
                .map(Element::text).orElse(null);
    }

    private String parseEngExample(Document doc) {
        return Optional.ofNullable(doc.getElementsByClass("ex_o").first()).map(Element::text).orElse(null);
    }

    private String parseRusExample(Document doc) {
        return Optional.ofNullable(doc.getElementsByClass("ex_t").first()).map(Element::text).orElse(null);
    }

    private String parseUrlSound(Document doc) {
        return Optional.ofNullable(doc.getElementById("audio_uk"))
                .map(e -> e.getElementsByAttributeValue("type", "audio/mpeg"))
                .map(e -> e.attr("src")).orElse(null);
    }

    private String deletePipeLineFromTranscription(String transcription) {
        return AnkiFillUtil.isNotNull(transcription) ? transcription.replaceAll("\\|", "") : null;
    }

    //   без понятия, что это за символ, но выглядит как пробел
    private String deleteENSP(String field) {
        return AnkiFillUtil.isNotNull(field) ? field.replaceAll(" ", "") : null;
    }

    private String deleteTrigramFromRusExample(String example) {
        return AnkiFillUtil.isNotNull(example) ? example.replaceAll("☰", "") : null;
    }
}

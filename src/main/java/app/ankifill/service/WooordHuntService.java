package app.ankifill.service;

import app.ankifill.model.AnkiCardDto;
import app.ankifill.utill.AnkiFillUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WooordHuntService {

    private final ReversoContextService reversoContextService;

    private static final String URL = "https://wooordhunt.ru";

    @SneakyThrows
    public AnkiCardDto fillAnkiCard(String word) {
        final Document doc = Jsoup.connect(URL + "/word/" + word).get();

        final AnkiCardDto ankiCardDto = new AnkiCardDto();
        ankiCardDto.setWord(word);

        ankiCardDto.setTranscription(
                deletePipeLineFromTranscription(parseTranscription(doc))
        );

        ankiCardDto.setTranslation(parseTranslate(doc));

        ankiCardDto.setExamples(reversoContextService.getExamples(word));

        ankiCardDto.setSoundURL(URL + parseUrlSound(doc));

        return ankiCardDto;
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

    private String parseUrlSound(Document doc) {
        return Optional.ofNullable(doc.getElementById("audio_uk"))
                .map(e -> e.getElementsByAttributeValue("type", "audio/mpeg"))
                .map(e -> e.attr("src")).orElse(null);
    }

    private String deletePipeLineFromTranscription(String transcription) {
        return AnkiFillUtil.isNotNull(transcription) ? transcription.replaceAll("\\|", "") : null;
    }
}

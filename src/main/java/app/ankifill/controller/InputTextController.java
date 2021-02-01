package app.ankifill.controller;

import app.ankifill.model.AnkiCardDto;
import app.ankifill.service.LingualeoClient;
import app.ankifill.service.WooordHuntService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class InputTextController {

    private final WooordHuntService wooordHuntService;

    private final LingualeoClient lingualeoClient;

    final List<AnkiCardDto> ankiCardsDto = new ArrayList<>();

    @PostMapping("/words")
    @SneakyThrows
    public void inputText(String text, HttpServletResponse response) {
        ankiCardsDto.clear();
        final List<String> words = parseStringByLines(text);


        for (String word : words) {
            System.out.println(word);
            AnkiCardDto ankiCardDto = wooordHuntService.fillAnkiCard(word);


            if (checkAnkiNull(ankiCardDto)) {
                ankiCardDto = lingualeoClient.fillAnkiCard(word, ankiCardDto);
            }

            ankiCardsDto.add(ankiCardDto);
        }

        response.sendRedirect("/result");
    }

    @GetMapping("/result")
    public String all(Model model) {
        model.addAttribute("ankiCards", ankiCardsDto);
        return "result";
    }

    private boolean checkAnkiNull(AnkiCardDto ankiCard) {
        return ankiCard.getTranslation() == null || ankiCard.getTranscription() == null || ankiCard.getSoundURL() == null;
    }

    private List<String> parseStringByLines(String text) {
        return text.lines().map(String::toLowerCase)
                .map(String::trim)
                .map(w -> w.replaceAll(" ", ""))
                .distinct()
                .collect(Collectors.toList());
    }

}

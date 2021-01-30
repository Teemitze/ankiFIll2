package app.ankifill.controller;

import app.ankifill.model.AnkiCard;
import app.ankifill.model.Examples;
import app.ankifill.service.LingualeoClient;
import app.ankifill.service.ReversoContextService;
import app.ankifill.service.WooordHuntService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class InputTextController {

    private final WooordHuntService wooordHuntService;

    private final LingualeoClient lingualeoClient;

    List<AnkiCard> ankiCards = new ArrayList<>();

    @PostMapping("/words")
    @SneakyThrows
    public void inputText(String text, HttpServletResponse response) {
        Set<String> words = text.lines().map(String::trim).collect(Collectors.toSet());


        for (String word : words) {
            System.out.println(word);
            AnkiCard ankiCard = wooordHuntService.fillAnkiCard(word);

            if (checkAnkiNull(ankiCard)) {
                ankiCard = lingualeoClient.fillAnkiCard(word);
            }

            if (ankiCard.getExamples() == null || ankiCard.getExamples().getRusExample() == null
                    || ankiCard.getExamples().getEngExample() == null) {
                Examples examples = new ReversoContextService().getExamples(word);
                ankiCard.setExamples(examples);
            }


            ankiCards.add(ankiCard);
        }

        response.sendRedirect("/result");
    }

    @GetMapping("/result")
    public String all(Model model) {
        model.addAttribute("ankiCards", ankiCards);
        return "result";
    }


    private boolean checkAnkiNull(AnkiCard ankiCard) {
        return ankiCard.getTranslation() == null || ankiCard.getTranscription() == null || ankiCard.getSoundURL() == null;
    }

}

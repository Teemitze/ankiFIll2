package app.ankifill.controller;

import app.ankifill.model.AnkiCard;
import app.ankifill.model.AnkiCardDto;
import app.ankifill.model.AnkiCardsList;
import app.ankifill.model.Examples;
import app.ankifill.service.LingualeoClient;
import app.ankifill.service.ReversoContextService;
import app.ankifill.service.WooordHuntService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    List<AnkiCardDto> ankiCardsDto = new ArrayList<>();

    @PostMapping("/words")
    @SneakyThrows
    public void inputText(String text, HttpServletResponse response) {
        List<String> words = text.lines().map(String::toLowerCase)
                .map(String::trim)
                .map(w -> w.replaceAll(" ", ""))
                .distinct()
                .collect(Collectors.toList());


        for (String word : words) {
            System.out.println(word);
            AnkiCardDto ankiCardDto = wooordHuntService.fillAnkiCard(word);


            if (checkAnkiNull(ankiCardDto)) {
                ankiCardDto = lingualeoClient.fillAnkiCard(word);
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

    @PostMapping("/save")
    public String save(@RequestBody List<AnkiCard> ankiCards) {

        System.out.println(ankiCards);
        return "redirect:/result";
    }

    private boolean checkAnkiNull(AnkiCardDto ankiCard) {
        return ankiCard.getTranslation() == null || ankiCard.getTranscription() == null || ankiCard.getSoundURL() == null;
    }

}

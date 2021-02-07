package app.ankifill.service;

import app.ankifill.model.AnkiCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillAnkiCardService {

    private final WooordHuntService wooordHuntService;

    private final LingualeoClient lingualeoClient;

    public List<AnkiCardDto> fillAnkiCards(final List<String> words) {
        return words.stream().distinct().map(this::fillAnkiCard).collect(Collectors.toList());
    }

    private AnkiCardDto fillAnkiCard(String word) {
        AnkiCardDto ankiCardDto = checkReadyAnkiCardDto(wooordHuntService.fillAnkiCard(word));

        if (!ankiCardDto.isReady()) {
            ankiCardDto = checkReadyAnkiCardDto(lingualeoClient.fillAnkiCard(word, ankiCardDto));
        }

        return ankiCardDto;
    }


    private AnkiCardDto checkReadyAnkiCardDto(AnkiCardDto ankiCardDto) {
        if (ankiCardDto.getTranslation() != null
                && ankiCardDto.getTranscription() != null
                && ankiCardDto.getSoundURL() != null
                && !ankiCardDto.getExamples().isEmpty()) {
            ankiCardDto.setReady(true);
        }

        return ankiCardDto;
    }
}

package app.ankifill.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnkiCardDto {
    private String word;
    private String transcription;
    private String translation;
    private List<Examples> examples;
    private String soundURL;
}

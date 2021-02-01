package app.ankifill.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnkiCard {
    private String word;
    private String transcription;
    private String translation;
    private Examples examples;
    private String soundURL;

    @Override
    public String toString() {
        String sep = "&";
        return word + sep + transcription + sep + translation + sep
                + examples.getEngExample() + sep + examples.getRusExample() + sep + soundURL + "\n";
    }
}

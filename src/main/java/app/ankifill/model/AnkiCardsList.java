package app.ankifill.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AnkiCardsList {
    private List<AnkiCard> ankiCards = new ArrayList<>();
}

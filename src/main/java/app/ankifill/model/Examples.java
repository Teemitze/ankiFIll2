package app.ankifill.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Examples {
    private String engExample;
    private String rusExample;

    public Examples(String engExample, String rusExample) {
        this.engExample = engExample;
        this.rusExample = rusExample;
    }
}

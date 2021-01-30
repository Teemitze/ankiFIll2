package app.ankifill.utill;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnkiFillUtil {

    public String buildSoundURL(String soundURL) {
        return isNotNull(soundURL) ? "[sound:" + soundURL + "]" : null;
    }

    public boolean isNotNull(String field) {
        return field != null;
    }
}

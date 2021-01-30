package app.ankifill.service;


import app.ankifill.model.AnkiCard;
import app.ankifill.model.AnkiCardDto;
import app.ankifill.utill.AnkiFillUtil;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class LingualeoClient {

    public AnkiCardDto fillAnkiCard(String word) {
        final JSONObject jsonObject = sendRequest(word);

        final AnkiCardDto ankiCard = new AnkiCardDto();
        ankiCard.setWord(word);
        ankiCard.setTranscription(getTranscriptionFromJSON(jsonObject));
        ankiCard.setTranslation(getTranslationFromJSON(jsonObject));

        ankiCard.setSoundURL(AnkiFillUtil.buildSoundURL(
                getSoundUrlFromJSON(jsonObject))
        );

        return ankiCard;
    }


    @SneakyThrows
    private JSONObject sendRequest(String word) {
        final HttpGet request = new HttpGet("http://api.lingualeo.com/gettranslates?word=" + replaceSpace(word));

        final CloseableHttpResponse response = HttpClients.createDefault().execute(request);

        final HttpEntity content = response.getEntity();

        return new JSONObject(EntityUtils.toString(content));
    }


    private String getTranscriptionFromJSON(JSONObject jsonObject) {
        return jsonObject.getString("transcription");
    }

    private String getTranslationFromJSON(JSONObject jsonObject) {
        return jsonObject.getString("translation");
    }

    private String getSoundUrlFromJSON(JSONObject jsonObject) {
        return jsonObject.getString("sound_url");
    }

    private String replaceSpace(String word) {
        return word.replaceAll(" ", "%20");
    }
}
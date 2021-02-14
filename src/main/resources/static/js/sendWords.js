function getWords() {
    let textarea = document.getElementById('words').value;
    return textarea.split('\n');
}

function sendWords() {
    let words = getWords();

    let sendWordsFromTextArea = new XMLHttpRequest();

    let body = JSON.stringify(words);

    sendWordsFromTextArea.open("POST", '/words', false);
    sendWordsFromTextArea.setRequestHeader('Content-Type', 'application/json');

    sendWordsFromTextArea.send(body);

    let response = sendWordsFromTextArea.response;
    deleteSendWordsForm();
    createTable(response);
    createSaveFileForm();

    createMainButton();
}

function deleteSendWordsForm() {
    document.querySelector("#sendWordsForm").remove();
}

function createSaveFileForm() {
    let form = `<form action="/saveFile" method="get">
                    <input value="Сохранить" onclick="sendTable()" type="submit">
                </form>`;


    document.getElementById("saveFileForm").innerHTML = form;
}

function createMainButton() {
    let button = `<button onclick="location.reload()">На главную</button>`
    document.getElementById("redirectMain").innerHTML = button;
}

function createSoundPlayer(word, soundUrl) {
    let player = `<span class="soundIcon" style="float: right">
                    <audio id="${word}" src="${soundUrl}"></audio>
                    <img src="images/voise.png" width="25px" onclick="document.getElementById('${word}').play()">
                  </span>`
    return player;
}

function createTable(ankiCards) {
    let ankiCardsJson = JSON.parse(ankiCards);

    let table = "<table id=\"table_words\">";

    table += "    <thead>\n" +
        "    <tr>\n" +
        "        <th class=\"count\">№</th>\n" +
        "        <th class=\"word\">Word</th>\n" +
        "        <th class=\"transcription\">Transcription</th>\n" +
        "        <th class=\"translation\">Translation</th>\n" +
        "        <th class=\"engExample\">Eng Example</th>\n" +
        "        <th class=\"rusExample\">Rus Example</th>\n" +
        "        <th hidden>Sound</th>\n" +
        "    </tr>\n" +
        "    </thead>" +
        "<tbody>";


    for (let i = 0; i < ankiCardsJson.length; i++) {
        let ankiCard = ankiCardsJson[i];
        let count = i + 1;

        table += `<tr>
                          <td class="count">${count}</td>
                          <td class="word">${ankiCard.word}</td>
                          <td class="transcription">${ankiCard.transcription}`

        table += createSoundPlayer(ankiCard.word, ankiCard.soundURL);

        table += `</td>
                          <td class="translation">
                            <input class="translation" value="${ankiCard.translation}"/>
                          </td>`;

        table += `<td class="engExample">`;
        table += ` <select class="engExample">`;


        for (let a = 0; a < ankiCard.examples.length; a++) {
            let example = ankiCard.examples[a];

            table += `<option value="${example.engExample}" class="engExample">${example.engExample}</option>`
        }
        table += `</select>`;
        table += `</td>`;


        table += `<td class="rusExample">`;
        table += ` <select class="rusExample">`;


        for (let a = 0; a < ankiCard.examples.length; a++) {
            let example = ankiCard.examples[a];

            table += `<option value="${example.rusExample}" class="rusExample">${example.rusExample}</option>`
        }
        table += `</select>`;
        table += `</td>`;

        table += `<td hidden>${ankiCard.soundURL}</td>`;
    }


    table += "</tbody>";
    table += "</table>";

    document.getElementById("div_table_words").innerHTML = table;
}
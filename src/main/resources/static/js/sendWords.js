function getWords() {
    let textarea = document.getElementById('words').value;
    return textarea.split('\n');
}

function createTable() {
    let words = getWords();

    console.log(words);

    // window.location.replace("/result.html");



    window.addEventListener("load", function () {
        let ankiCardsJson = JSON.parse(sendWords(words));

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


        console.log(table);

        ankiCardsJson.forEach(function (ankiCard, i) {
            table += `<tr>
                          <td class="count">${i}</td>
                          <td class="word">${ankiCard.word}</td>
                          <td class="transcription">${ankiCard.transcription}</td>
                          <td class="translation">
                            <input class="translation" value="${ankiCard.translation}"/>
                          </td>`;


            let examples = ankiCard.examples;


            for (let example in examples) {
                table += `<td class="engExample">
                            <select class="engExample">
                                <option value="${example.engExample}" class="engExample"></option>
                            </select>
                          </td>`;

                table += `<td class="rusExample">
                            <select class="rusExample">
                                <option value="${example.rusExample}" class="rusExample"></option>
                            </select>
                          </td>`
            }

            table += `<td class="transcription">${ankiCard.soundURL} hidden</td>`;

            table += "</tbody>";
            table += "</table>";
        })
        document.getElementById("div_table_words").innerHTML = table;

        console.log(table);
    });
}


function sendWords(words) {
    let xhr = new XMLHttpRequest();

    let body = JSON.stringify(words);

    xhr.open("POST", '/words', false);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.send(body);

    return xhr.response;
}

createTable();
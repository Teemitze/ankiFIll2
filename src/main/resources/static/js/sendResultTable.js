function AnkiCard(word, transcription, translation, examples, soundURL) {
    this.word = word;
    this.transcription = transcription;
    this.translation = translation;
    this.examples = examples;
    this.soundURL = soundURL;
}

function Examples(engExample, rusExample) {
    this.engExample = engExample;
    this.rusExample = rusExample;
}


function sendTable() {
    let cells = document.getElementsByTagName("tr");

    let ankiCards = [];

    for (let i = 1; i < cells.length; i++) {
        let word = cells[i].getElementsByTagName("td").item(1).innerText;
        let transcription = cells[i].getElementsByTagName("td").item(2).innerText;
        let translation = cells[i].getElementsByTagName("td").item(3).getElementsByTagName("input").item(0).value;
        let engExample = cells[i].getElementsByTagName("td").item(4).getElementsByTagName("option");
        let selectEngExample = findSelectExample(engExample);

        let rusExamples = cells[i].getElementsByTagName("td").item(5).getElementsByTagName("option");
        let selectRusExample = findSelectExample(rusExamples);

        let examples = new Examples(selectEngExample, selectRusExample);


        let soundURL = cells[i].getElementsByTagName("td").item(6).innerText;

        const ankiCard = new AnkiCard(word, transcription, translation, examples, soundURL);

        ankiCards.push(ankiCard);
    }

    sendRequest(ankiCards);
}

function findSelectExample(examples) {
    for (let i = 0; i < examples.length; i++) {
        if (examples[i].selected === true) {
            return examples[i].innerText;
        }
    }
}

function sendRequest(ankiCards) {
    var xhr = new XMLHttpRequest();

    var body = JSON.stringify(ankiCards);

    xhr.open("POST", '/saveAnkiCards', false);
    xhr.setRequestHeader('Content-Type', 'application/json');

    console.log(body)
    xhr.send(body);
}
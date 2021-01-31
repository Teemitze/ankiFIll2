function AnkiCard(word, transcription, translation, selectEngExample, selectRusExample, soundURL) {
    this.word = word;
    this.transcription = transcription;
    this.translation = transcription;
    this.selectEngExample = selectEngExample;
    this.selectRusExample = selectRusExample;
    this.soundURL = soundURL;
}



function sendTable() {
    let cells = document.getElementsByTagName("tr");

    let ankiCards = [];

    for (let i = 1; i < cells.length; i++) {
        let word = cells[i].getElementsByTagName("td").item(0).innerText;
        let transcription = cells[i].getElementsByTagName("td").item(1).innerText;
        let translation = cells[i].getElementsByTagName("td").item(2).getElementsByTagName("input").item(0).value;
        let engExample = cells[i].getElementsByTagName("td").item(3).getElementsByTagName("option");
        let selectEngExample = findSelectExample(engExample);
        let rusExamples = cells[i].getElementsByTagName("td").item(4).getElementsByTagName("option");
        let selectRusExample = findSelectExample(rusExamples);

        let soundURL = cells[i].getElementsByTagName("td").item(5).innerText;

        const ankiCard = new AnkiCard(word, transcription, translation, selectEngExample, selectRusExample, soundURL);

        ankiCards.push(ankiCard);
    }

}

function findSelectExample(examples) {
    for (let i = 0; i < examples.length; i++) {
        if (examples[i].selected === true) {
           return examples[i].innerText;
        }
    }
}

sendTable();
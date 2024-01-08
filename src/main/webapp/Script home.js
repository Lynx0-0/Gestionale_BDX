script.js

document.addEventListener("DOMContentLoaded", function () {
    var selectAzione = document.getElementById('azione');
    var container = document.getElementById('dynamicFields');

    function updateFields() {
        container.innerHTML = '';
        if (selectAzione.value === 'allinea') {
            container.innerHTML = `
                        <label for="nomeFile">Nome Tabella da Controllare:</label>
                        <input type="text" id="nomeFile" name="nomeFile"><br><br>
                        <label for="nomeTabella">Nome Tabella di Confronto:</label>
                        <input type="text" id="nomeTabella" name="nomeTabella"><br><br>
                    `;
        } else {
            container.innerHTML = `
                        <label for="nomeFile">Nome File:</label>
                        <input type="file" id="nomeFile" name="nomeFile"><br><br>
                        <label for="nomeTabella">Nome Tabella:</label>
                        <input type="text" id="nomeTabella" name="nomeTabella"><br><br>
                    `;
        }
    }

    selectAzione.addEventListener('change', updateFields);

    updateFields();
});

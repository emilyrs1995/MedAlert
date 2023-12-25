import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import MedicationClient from "../api/medicationClient.js";

/**
 * Logic needed for the view playlist page of the website.
 */
class MedicationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        /*document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);*/
        document.getElementById('FormId').addEventListener('submit', this.onCreate);
        this.client = new MedicationClient();

       /* this.dataStore.addChangeListener(this.renderExample)*/
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const example = this.dataStore.get("example");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div>
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("example", null);

        let result = await this.client.getExample(id, this.errorHandler);
        this.dataStore.set("example", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {

        event.preventDefault();
        this.dataStore.set("createMedication", null);

                   var select_opt = 0;

                   var timeOfDay = document.querySelector('#action_select').value,
                       dosage = document.querySelector('.input_description').value,
                       name = document.querySelector('.input_title_desc').value,
                       alertTime = document.getElementById('time_input').value;
                   var selectElement = document.getElementById('choices-multiple-remove-button');
                   var alertDays = [];

                   for (var i = 0; i < selectElement.options.length; i++) {
                       if (selectElement.options[i].selected) {
                           alertDays.push(selectElement.options[i].value);
                       }
                   }
                   var alertDaysFormatted = alertDays.join(', ');

                   switch (timeOfDay) {
                       case "MORNING":
                           select_opt = 0;
                           break;
                       case "EVENING":
                           select_opt = 1;
                           break;
                       case "AFTERNOON":
                           select_opt = 2;
                           break;
                   }

                   const createdMedication = await this.client.createMedication(name, timeOfDay, dosage, alertTime, alertDays);
                   this.dataStore.set("createMedication", createdMedication);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const medicationPage = new MedicationPage();
    medicationPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

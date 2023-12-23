import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import MedicationClient from "../api/medicationClient.js";

/**
 * Logic needed for the view playlist page of the website.
 */
class MedicationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        /*document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);*/
        document.getElementById('yourFormId').addEventListener('submit', this.onCreate);
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

     async add_to_list() {

           var action = document.querySelector('#action_select').value,
               description = document.querySelector('.input_description').value,
               title = document.querySelector('.input_title_desc').value,
               time = document.getElementById('time_input').value;
           var selectElement = document.getElementById('choices-multiple-remove-button');
           var selectedValues = [];

           for (var i = 0; i < selectElement.options.length; i++) {
               if (selectElement.options[i].selected) {
                   selectedValues.push(selectElement.options[i].value);
               }
           }
           var resultString = selectedValues.join(', ');

           switch (action) {
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

           var class_li = ['list_morning list_dsp_none', 'list_evening list_dsp_none', 'list_afternoon list_dsp_none'];

           var cont = '<div class="col_md_1_list">    <p>' + action + '</p>    </div> <div class="col_md_2_list"> <h4>' + title + '</h4> <p>' + description + '</p> </div>    <div class="col_md_3_list"> <div class="cont_text_date"> <p>' + time + '</p> <p>' + resultString + '</p> </div>  <div class="cont_btns_options">    <ul>  <li><a href="#finish" onclick="finish_action(' + select_opt + ',' + contador + ');" ><i class="material-icons">&#xE5CA;</i></a></li>   </ul>  </div>    </div>';

           var li = document.createElement('li')
           li.className = class_li[select_opt] + " li_num_" + contador;

           li.innerHTML = cont;
           document.querySelectorAll('.cont_princ_lists > ul')[0].appendChild(li);

           setTimeout(function() {
               document.querySelector('.li_num_' + contador).style.display = "block";
           }, 100);

           setTimeout(function() {
               document.querySelector('.li_num_' + contador).className = "list_dsp_true " + class_li[select_opt] + " li_num_" + contador;
               contador++;
           }, 200);



           // Use the variables where needed or pass them to another function
           await handleMedicationCreation(timeOfDay, dosage, name, alertTime, alertDays);
     }

    async handleMedicationCreation(timeOfDay, dosage, name, alertTime, alertDays) {
        const createdMedication = await this.client.createMedication(name, timeOfDay, dosage, alertTime, alertDays, this.errorHandler);
        this.dataStore.set("medication", createdMedication);

        if (createdMedication) {
            this.showMessage(`Created ${createdMedication.name}!`);
        } else {
            this.errorHandler("Error creating! Try again...");
        }
    }

    async onCreate(event) {
        event.preventDefault();
        this.dataStore.set("medication", null);

        var contador = 0,
            select_opt = 0;

        add_to_list();
        handleMedicationCreation(timeOfDay, dosage, name, alertTime, alertDays);
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

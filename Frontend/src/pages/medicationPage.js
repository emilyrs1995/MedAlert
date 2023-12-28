import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import MedicationClient from "../api/medicationClient.js";

/**
 * Logic needed for the view playlist page of the website.
 */
class MedicationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderTodaysMedication'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        /*document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);*/
        document.getElementById('FormId').addEventListener('submit', this.onCreate);
        this.client = new MedicationClient();

        await this.getAllMedication();

       this.dataStore.addChangeListener(this.renderTodaysMedication);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderTodaysMedication() {
        //let resultArea = document.getElementById("result-info");

        //final DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();

        //const day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);

//        var selectElement = document.getElementById('choices-multiple-remove-button');
//        var alertDays = [];
//
//        for (var i = 0; i < selectElement.options.length; i++) {
//            if (selectElement.options[i].selected) {
//                alertDays.push(selectElement.options[i].value);
//            }
//        }

        const allMedications = this.dataStore.get("allMedications");
        console.log("allMedications:", allMedications);
            if (Array.isArray(allMedications)) {
            for (const medication of allMedications) {
                var contador = 0,
                select_opt = 0;

                var class_li = ['list_morning list_dsp_none', 'list_evening list_dsp_none', 'list_afternoon list_dsp_none'];

                var cont = '<div class="col_md_1_list">    <p>' + medication.timeOfDay + '</p>    </div> <div class="col_md_2_list"> <h4>' + medication.name + '</h4> <p>' + medication.dosage + '</p> </div>    <div class="col_md_3_list"> <div class="cont_text_date"> <p>' + medication.alertTime + '</p> <p>' + medication.alertDays + '</p> </div>  <div class="cont_btns_options">    <ul>  <li><a href="#finish" onclick="finish_action(' + select_opt + ',' + contador + ');" ><i class="material-icons">&#xE5CA;</i></a></li>   </ul>  </div>    </div>';

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

//                const separateDiv = document.getElementById('listPage');
//
//                var lis = document.createElement('li')
//
//                var cont2 = '<div class="col_md_1_list">    <p>' + medication.timeOfDay + '</p>    </div> <div class="col_md_2_list"> <h4>' + medication.name + '</h4> <p>' + medication.dosage + '</p> </div>    <div class="col_md_3_list"> <div class="cont_text_date"> <p>' + medication.alertTime + '</p> <p>' + medication.alertDays + '</p> </div>  <div class="cont_btns_options">    <ul>  <li><a href="#" id="test-link" onclick="" ><i class="material-icons">&#xe5cd;</i></a></li>   </ul>  </div>    </div>';
//                        // Update the content of the div
//                        lis.className = class_li[select_opt] + " li_num_" + contador;
//
//                                        lis.innerHTML = cont2;
//                                        separateDiv.appendChild(lis);
            }
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

    async getAllMedication() {
        const allMedications = await this.client.getMedicationList()

        this.dataStore.set("allMedications", allMedications);
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

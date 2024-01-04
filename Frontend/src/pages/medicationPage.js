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
        document.getElementById('FormId').addEventListener('submit', (event) => this.onCreate(event));
        this.client = new MedicationClient();

        try {
            const allMedications = await this.client.getMedicationList();
            console.log('Fetched medication list:', allMedications);

            this.dataStore.set("allMedications", allMedications);

            this.renderTodaysMedication();
        } catch (error) {
            console.error('Error fetching medication list:', error);
        }

        window.addEventListener('load', () => {
            console.log('Page loaded or refreshed');
            this.getAllMedication().then(() => {
                const refreshedMedications = this.dataStore.get("allMedications");
                console.log('Refreshed medication list:', refreshedMedications);
                this.renderTodaysMedication();
            });
        });
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderTodaysMedication() {
        const currentDate = new Date();
        const dayOfWeek = currentDate.toLocaleDateString('en-US', { weekday: 'short' });

        var selectElement = document.getElementById('choices-multiple-remove-button');
        var alertDays = [];

        for (var i = 0; i < selectElement.options.length; i++) {
            if (selectElement.options[i].selected) {
                alertDays.push(selectElement.options[i].value);
            }
        }

        const allMedications = this.dataStore.get("allMedications");
        console.log("allMedications:", allMedications);

        if (Array.isArray(allMedications)) {
            var contador = 0,
                select_opt = 0;

            var class_li = ['list_morning list_dsp_none', 'list_evening list_dsp_none', 'list_afternoon list_dsp_none'];

            for (const medication of allMedications) {
                const medicationDays = Array.isArray(medication.alertDays) ? medication.alertDays : [medication.alertDays];
                const formattedDays = medicationDays.map(day => day.trim());

                if (formattedDays.includes(dayOfWeek)) {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        <div class="col_md_1_list">
                            <p>${medication.timeOfDay}</p>
                        </div>
                        <div class="col_md_2_list">
                            <h4>${medication.name}</h4>
                            <p>${medication.dosage}</p>
                        </div>
                        <div class="col_md_3_list">
                            <div class="cont_text_date">
                                <p>${medication.alertTime}</p>
                                <p>${medication.alertDays}</p>
                            </div>
                            <div class="cont_btns_options">
                                <ul>
                                    <li><a href="#finish" onclick="finish_action(${select_opt}, ${contador});"><i class="material-icons">&#xE5CA;</i></a></li>
                                </ul>
                            </div>
                        </div>
                    `;

                    li.className = `list_${medication.timeOfDay.toLowerCase()} li_num_${contador}`;
                    document.querySelector('.cont_princ_lists > ul').appendChild(li);

                    // Using closures to capture the current value of contador
                    (function (currentContador) {
                        setTimeout(function () {
                            document.querySelector(`.li_num_${currentContador}`).style.display = "block";
                        }, 100);

                        setTimeout(function () {
                            document.querySelector(`.li_num_${currentContador}`).className = `list_dsp_true list_${medication.timeOfDay.toLowerCase()} li_num_${currentContador}`;
                        }, 200);
                    })(contador);

                    contador++;
                }
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
        var alertDaysFormatted = alertDays.map(day => day.trim());

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

        try {
            const createdMedication = await this.client.createMedication(name, timeOfDay, dosage, alertTime, alertDaysFormatted);
            this.dataStore.set("createMedication", createdMedication);

            // Immediately render the medication after it's created
            const currentDate = new Date();
                    const dayOfWeek = currentDate.toLocaleDateString('en-US', { weekday: 'short' });

                    var selectElement = document.getElementById('choices-multiple-remove-button');
                    var alertDays = [];

                    for (var i = 0; i < selectElement.options.length; i++) {
                        if (selectElement.options[i].selected) {
                            alertDays.push(selectElement.options[i].value);
                        }
                    }


                        var contador = 0,
                            select_opt = 0;

                        var class_li = ['list_morning list_dsp_none', 'list_evening list_dsp_none', 'list_afternoon list_dsp_none'];


                            const medicationDays = Array.isArray(createdMedication.alertDays) ? createdMedication.alertDays : [createdMedication.alertDays];
                            const formattedDays = medicationDays.map(day => day.trim());

                            if (formattedDays.includes(dayOfWeek)) {
                                const li = document.createElement('li');
                                li.innerHTML = `
                                    <div class="col_md_1_list">
                                        <p>${createdMedication.timeOfDay}</p>
                                    </div>
                                    <div class="col_md_2_list">
                                        <h4>${createdMedication.name}</h4>
                                        <p>${createdMedication.dosage}</p>
                                    </div>
                                    <div class="col_md_3_list">
                                        <div class="cont_text_date">
                                            <p>${createdMedication.alertTime}</p>
                                            <p>${createdMedication.alertDays}</p>
                                        </div>
                                        <div class="cont_btns_options">
                                            <ul>
                                                <li><a href="#finish" onclick="finish_action(${select_opt}, ${contador});"><i class="material-icons">&#xE5CA;</i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                `;

                                li.className = `list_${createdMedication.timeOfDay.toLowerCase()} li_num_${contador}`;
                                document.querySelector('.cont_princ_lists > ul').appendChild(li);

                                // Using closures to capture the current value of contador
                                (function (currentContador) {
                                    setTimeout(function () {
                                        document.querySelector(`.li_num_${currentContador}`).style.display = "block";
                                    }, 100);

                                    setTimeout(function () {
                                        document.querySelector(`.li_num_${currentContador}`).className = `list_dsp_true list_${createdMedication.timeOfDay.toLowerCase()} li_num_${currentContador}`;
                                    }, 200);
                                })(contador);

                                contador++;
                            }


        } catch (error) {
            console.error('Error creating medication:', error);
        }
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

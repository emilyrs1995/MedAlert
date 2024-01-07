import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import MedicationClient from "../api/medicationClient.js";


let h4Text;
/**
 * Logic needed for the view playlist page of the website.
 */
class MedicationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderTodaysMedication', 'renderAllMedicationList', 'onDelete'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('FormId').addEventListener('submit', (event) => this.onCreate(event));
        const listPage = document.getElementById('listPage');

        // Event listener for clicks on dynamically generated elements
        document.addEventListener('click', (event) => {
            const clickedElement = event.target;

            // Check if the clicked element has a specific class or ID
            if (clickedElement.id && clickedElement.id.startsWith('modal-button-yes')) {
                console.log('Clicked on dynamically generated element with ID:', clickedElement.id);

                // Call onDelete method directly
                this.onDelete(event);
            }
        });

        this.client = new MedicationClient();

        try {
            const allMedications = await this.client.getMedicationList();
            console.log('Fetched medication list:', allMedications);

            this.dataStore.set("allMedications", allMedications);

            this.renderTodaysMedication();
            await this.renderAllMedicationList();
        } catch (error) {
            console.error('Error fetching medication list:', error);
        }

        window.addEventListener('load', () => {
            console.log('Page loaded or refreshed');
            this.getAllMedication().then(() => {
                const refreshedMedications = this.dataStore.get("allMedications");
                console.log('Refreshed medication list:', refreshedMedications);
                this.renderTodaysMedication();
                this.renderAllMedicationList();
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
                const formattedDaysList = medicationDays.join(', ');

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
                                <p>${formattedDaysList}</p>
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

    async renderAllMedicationList() {
        const allMedications = this.dataStore.get("allMedications");
        console.log("allMedications:", allMedications);

        if (Array.isArray(allMedications)) {
            const listPage = document.getElementById('listPage');
            console.log("listPage:", listPage);
            var contador = 0,
                select_opt = 0;

            const modalId = 'medicationModal';  // Unique ID for the modal

            for (const medication of allMedications) {
                const medicationDays = Array.isArray(medication.alertDays) ? medication.alertDays : [medication.alertDays];
                const formattedDays = medicationDays.map(day => day.trim());
                const formattedDaysList = medicationDays.join(', ');

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
                            <p>${formattedDaysList}</p>
                        </div>
                        <div class="cont_btns_options">
                            <ul>
                                <li><a href="#" class="test-link" data-modal-id="${modalId}" style="background-color: #A52A2A;"><i class="material-icons">&#xe5cd;</i></a></li>
                            </ul>
                        </div>
                    </div>
                `;

                li.className = `list_${medication.timeOfDay.toLowerCase()} li_num_${contador}`;
                listPage.querySelector('.cont_princ_lists > ul').appendChild(li);

                // Using closures to capture the current value of contador
                (function (currentContador) {
                    setTimeout(function () {
                        listPage.querySelector(`.li_num_${currentContador}`).style.display = "block";
                    }, 100);

                    setTimeout(function () {
                        listPage.querySelector(`.li_num_${currentContador}`).className = `list_dsp_true list_${medication.timeOfDay.toLowerCase()} li_num_${currentContador}`;
                    }, 200);
                })(contador);

                contador++;
            }

            // Function to handle click events on child elements
                function handleClick(event) {
                  // Access the clicked child element using event.target
                  const clickedChildElement = event.target;

                  // Traverse up the DOM to find the parent list item (or any desired parent)
                  const parentListItem = clickedChildElement.closest('.list_dsp_true');

                  if (parentListItem) {
                    // Extract the text content from the parent list item
                    const h4Element = parentListItem.querySelector('h4');

                    if (h4Element) {
                        const h4Text = h4Element.textContent.trim();
                        console.log('Text content of <h4>:', h4Text);
                    } else {
                        console.error('No <h4> element found in the parentListItem.');
                    }
                  }
                }

                // Attach the handleClick function to the click event of child elements
                const childElements = document.querySelectorAll('.test-link');
                childElements.forEach(child => {
                  child.addEventListener('click', handleClick);
                });

            // Event binding outside the loop
            $(document).ready(function() {
                $('.test-link').click(function(e) {
                    e.preventDefault();

                    const modalId = $(this).data('modal-id');
                    const userResult = function(result) {
                        if (result === 1) {
                            $('#test-text').text('The user confirm!');
                        } else {
                            $('#test-text').text('The user did not confirm!');
                        }
                    }

                    toggleModal(`Are you sure you want to delete this? This action cannot be undone.`, userResult, modalId);
                });
            });

            console.log("Finished rendering");
        }
        function toggleModal(text, callback, modalId) {
                const $wrapper = $(`<div id="modal-wrapper"></div>`).appendTo('body');
                const $modal = $(`
                    <div id="modal-confirmation" class="modal-confirmation">
                        <div id="modal-header">
                            <h3><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Confirm Delete</h3>
                            <span data-confirm=0 class="modal-action" id="modal-close"><i class="fa fa-times" aria-hidden="true"></i></span>
                        </div>
                        <div id="modal-content">
                            <p>${text}</p>
                        </div>
                        <div id="modal-buttons">
                            <button class="modal-action" data-confirm=0 id="modal-button-no">Cancel</button>
                            <button class="modal-action" data-confirm=1 id="modal-button-yes">Delete</button>
                        </div>
                    </div>`
                ).appendTo($wrapper);

                setTimeout(function() {
                    $wrapper.addClass('active');
                }, 100);

                $wrapper.find('.modal-action').click(function() {
                    const result = $(this).data('confirm');
                    $wrapper.removeClass('active').delay(500).queue(function() {
                        $wrapper.remove();
                        callback(result);
                    });
                });
            }
    }






    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.querySelector('.input_title_desc').value;
        this.dataStore.set("medication", null);

        let result = await this.client.getMedication(id, this.errorHandler);
        this.dataStore.set("medication", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onDelete(event) {
            // Prevent the page from refreshing on form submit
            event.preventDefault();

            const medicationNameToDelete = h4Text;

            try {
                const response = await this.client.deleteMedication(medicationNameToDelete);
                this.dataStore.set("medication", response);
                console.log("Medication deleted successfully:", response);
                // Handle success, e.g., update UI or show a success message
            } catch (error) {
                console.error('Error deleting medication:', error);
                // Handle error, e.g., show an error message
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

                        var contador = 0,
                            select_opt = 0;

                        var class_li = ['list_morning list_dsp_none', 'list_evening list_dsp_none', 'list_afternoon list_dsp_none'];


                            const medicationDays = Array.isArray(createdMedication.alertDays) ? createdMedication.alertDays : [createdMedication.alertDays];
                            const formattedDays = medicationDays.map(day => day.trim());
                            const formattedDaysList = medicationDays.join(', ');

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
                                            <p>${formattedDaysList}</p>
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

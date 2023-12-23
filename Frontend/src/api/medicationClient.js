import BaseClass from "../util/baseClass.js";
import axios from '../../node_modules/axios/lib/axios.js'

/**
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class MedicationClient extends BaseClass {
    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getMedication', 'createMedication', 'updateMedication', 'getMedicationList', 'deleteMedication'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
        }

/**
* Run any functions that are supposed to be called once the client has loaded successfully.
* @param client The client that has been successfully loaded.
*/
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }


    async getMedication(id, errorCallback) {
        try {
            const response = await this.client.get(`/medication/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getMedication", error, errorCallback)
        }
    }

    async createMedication(name, timeOfDay, dosage, alertTime, alertDays, errorCallback) {
        try {
            const response = await this.client.get(`/medication`, {
                name: name,
                timeOfDay: timeOfDay,
                dosage: dosage,
                alertTime: alertTime,
                alertDays: alertDays,
            });
            return response.data;
        } catch (error) {
            this.handleError("createMedication", error, errorCallback)
        }
    }


    // '/medication/all' or '/medication'
    async getAllMedications(errorCallback){
        try {
            const response = await this.client.get(`/medication/all`)
            return response.data
        } catch (error) {
            this.handleError("getAllMedications", error, errorCallback)
        }
    }

    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " +error);
        }
    }
}
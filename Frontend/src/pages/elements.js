var contador = 0,
    select_opt = 0;

function add_to_list() {
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

}

function finish_action(num, num2) {

    var class_li = ['list_morning list_dsp_true', 'list_evening  list_dsp_true', 'list_afternoon list_dsp_true'];
    console.log('.li_num_' + num2);
    document.querySelector('.li_num_' + num2).className = class_li[num] + " list_finish_state";
    setTimeout(function() {
        del_finish();
    }, 500);
}

function del_finish() {
    var li = document.querySelectorAll('.list_finish_state');
    for (var e = 0; e < li.length; e++) {
        /* li[e].style.left = "-100px"; */
        li[e].style.opacity = "0";
        li[e].style.height = "0px";
        li[e].style.margin = "0px";
    }

    setTimeout(function() {
        var li = document.querySelectorAll('.list_finish_state');
        for (var e = 0; e < li.length; e++) {
            li[e].parentNode.removeChild(li[e]);
        }
    }, 500);


}
var t = 0;

function add_new() {
    if (t % 2 == 0) {
        document.querySelector('.cont_crear_new').className = "cont_crear_new cont_crear_new_active";

        document.querySelector('.cont_add_titulo_cont').className = "cont_add_titulo_cont cont_add_titulo_cont_active";
        t++;
    } else {
        document.querySelector('.cont_crear_new').className = "cont_crear_new";
        document.querySelector('.cont_add_titulo_cont').className = "cont_add_titulo_cont";
        t++;
    }
}

//----------------- navigation bar -----------------------

// array describing the options of the navigation elements
// specifying the lower case option and the matching color
const navigationOptions = [{
        name: 'home',
        color: '#0048c0'
    },
    {
        name: 'list',
        color: '#0048c0'
    }
];

// target all anchor link elements
const links = document.querySelectorAll('nav a');

// function called in response to a click event on the anchor link
function handleClick(e) {
    // prevent the default behavior, but most importantly remove the class of .active from those elements with it
    e.preventDefault();
    links.forEach(link => {
        if (link.classList.contains('active')) {
            link.classList.remove('active');
        }
    });

    // retrieve the option described the link element
    const name = this.textContent.trim().toLowerCase();
    // find in the array the object with the matching name
    // store a reference to its color
    const {
        color
    } = navigationOptions.find(item => item.name === name);

    // retrieve the custom property for the --hover-c property, to make it so that the properties are updated only when necessary
    const style = window.getComputedStyle(this);
    const hoverColor = style.getPropertyValue('--hover-c');
    // if the two don't match, update the custom property to show the hue with the text and the semi transparent background
    if (color !== hoverColor) {
        this.style.setProperty('--hover-bg', `${color}20`);
        this.style.setProperty('--hover-c', color);
    }

    // apply the class of active to animate the svg an show the span element
    this.classList.add('active');
}
// Handle page change based on the clicked link
switch (name) {
    case 'home':
        // Add logic for the "home" page
        console.log('Switching to Home Page');
        break;
    case 'list':
        // Add logic for the "list" page
        console.log('Switching to List Page');
        break;
        // Add more cases for additional pages if needed
}
// listen for a click event on each and every anchor link
links.forEach(link => link.addEventListener('click', handleClick));

$(document).ready(function() {

    var multipleCancelButton = new Choices('#choices-multiple-remove-button', {
        removeItemButton: true,

    });


});
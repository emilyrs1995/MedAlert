var contador = 0,
    select_opt = 0;

window.add_to_list = function() {
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

window.finish_action = function(num, num2) {

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

window.add_new = function() {
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
const homePage = document.getElementById('homePage');
const listPage = document.getElementById('listPage');
const links = document.querySelectorAll('nav a');

var z = 0;
var listPageContainer = document.getElementById('listPage');

window.add_search = function() {
    if (z % 2 == 0 && listPage) {

        listPageContainer.querySelector('.cont_crear_new').className = "cont_crear_new cont_crear_new_active";

        listPageContainer.querySelector('.cont_add_titulo_cont').className = "cont_add_titulo_cont";
        z++;
    } else {
        listPageContainer.querySelector('.cont_crear_new').className = "cont_crear_new";
        listPageContainer.querySelector('.cont_add_titulo_cont').className = "cont_add_titulo_cont";
        z++;
    }
}

// function called in response to a click event on the anchor link
document.addEventListener('DOMContentLoaded', function() {
    // Show the home page as the default
    showPage(homePage);

    // Add event listeners to each anchor link
    links.forEach(link => link.addEventListener('click', handleClick));
});

function handleClick(e) {
    e.preventDefault();

    // Remove 'active' class from all links
    links.forEach(link => {
        if (link.classList.contains('active')) {
            link.classList.remove('active');
        }
    });

    // Set 'active' class on the clicked link
    this.classList.add('active');

    const name = this.textContent.trim().toLowerCase();
    const {
        color
    } = navigationOptions.find(item => item.name === name);

    const style = window.getComputedStyle(this);
    const hoverColor = style.getPropertyValue('--hover-c');

    if (color !== hoverColor) {
        this.style.setProperty('--hover-bg', `${color}20`);
        this.style.setProperty('--hover-c', color);
    }

    // Handle page change based on the clicked link
    switch (name) {
        case 'home':
            showPage(homePage);
            console.log('Switching to Home Page');
            break;
        case 'list':
            showPage(listPage);
            console.log('Switching to List Page');
            break;
            // Add more cases for additional pages if needed
    }
}

function showPage(pageElement) {
    // Hide all pages
    const allPages = document.querySelectorAll('.page');
    allPages.forEach(page => {
        page.style.opacity = 0;
        page.style.pointerEvents = 'none';
    });

    // Show the selected page with a fade-in effect
    pageElement.style.opacity = 1;
    pageElement.style.pointerEvents = 'auto';
}

$(document).ready(function() {

    var multipleCancelButton = new Choices('#choices-multiple-remove-button', {
        removeItemButton: true,

    });
});

$(document).ready(function() {
$('#test-link').click(function(e) {
    e.preventDefault();

    var userResult = function(result) {
        if (result === 1) {
            $('#test-text').text('The user confirmed!');
        } else {
            $('#test-text').text('The user did not confirm!');
        }
    }

    toggleModal('Are you sure you want to delete this? This action cannot be undone.', userResult);
});

function toggleModal(text, callback) {

    var $wrapper = $('<div id="modal-wrapper"></div>').appendTo('body');

    var $modal = $('<div id="modal-confirmation"><div id="modal-header"><h3><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Confirm Delete</h3><span data-confirm=0 class="modal-action" id="modal-close"><i class="fa fa-times" aria-hidden="true"></i></span></div><div id="modal-content"><p>' + text + '</p></div><div id="modal-buttons"><button class="modal-action" data-confirm=0 id="modal-button-no">Cancel</button><button class="modal-action" data-confirm=1 id="modal-button-yes">Delete</button></div></div>').appendTo($wrapper);

    setTimeout(function() {
        $wrapper.addClass('active');
    }, 100);

    $wrapper.find('.modal-action').click(function() {
        var result = $(this).data('confirm');
        $wrapper.removeClass('active').delay(500).queue(function() {
            $wrapper.remove();
            callback(result);
        });
    });

}
})
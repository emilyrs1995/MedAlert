
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
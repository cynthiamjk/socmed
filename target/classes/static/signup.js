


//displays message when password is <= 8 if the user clicks outside the form
function rmMessOnBlur() {
	var myInput = document.getElementById("pw");

	if( myInput.value.length >= 8) {
		document.getElementById("message").style.display = "none";
	} else {
		document.getElementById("message").style.display = "block";
	}
}


// displays message when user clicks inside de form and pw isnt 8 length
function addMessOnFocus() {
	var myInput = document.getElementById("pw");

	if( myInput.value.length < 8 ) {
		document.getElementById("message").style.display = "block";
	} else {
		document.getElementById("message").style.display = "none";
	}
}

// displays border style according to the length
function lengthValidation() {
	var myInput = document.getElementById("pw");
	var length = document.getElementById("length");
	if ( myInput.value.length < 0 ) {
		myInput.style.display = "none";
	}

	if( myInput.value.length < 8) {
		myInput.classList.remove("valid");
		myInput.classList.add("notvalid");
	} else {
		myInput.classList.remove("notvalid");
		myInput.classList.add("valid");
	}
	if(myInput.value.length >= 8) {
		document.getElementById("message").style.display = "none";
		myInput.classList.remove("invalid");
		myInput.classList.add("valid");
	} else {
		document.getElementById("message").style.display = "block";
		myInput.classList.remove("valid");
		myInput.classList.add("invalid");
	}

}

function disbutton() {
	var email = document.getElementById("fname");
var btnSubmit = document.getElementById("loginbtn");
if (email.value != " ") {
            //Enable the TextBox when TextBox has value.
            btnSubmit.disabled = false;
        } else {
            //Disable the TextBox when TextBox is empty.
            btnSubmit.disabled = true;
        }
    
}


function check1() {
  var checkBox = document.getElementById("1");
  var text = document.getElementById("pv1");

  if (checkBox.checked == true){
   text.innerHTML = "private";
  } else {
    text.innerHTML = "public";
  }
}

function check2() {
  var checkBox = document.getElementById("2");
  var text = document.getElementById("pv2");

  if (checkBox.checked == true){
   text.innerHTML = "private";
  } else {
    text.innerHTML = "public";
  }
}

function check3() {
  var checkBox = document.getElementById("3");
  var text = document.getElementById("pv3");

  if (checkBox.checked == false){
   text.innerHTML = "public";
  } else {
    text.innerHTML = "private";
  }
}


function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
} 
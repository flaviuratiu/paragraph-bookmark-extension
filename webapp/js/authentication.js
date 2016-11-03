var auth_token;
var current_user;

function signUp() {
    email = $("#sign_up_email").val();
    password = $("#sign_up_password").val();
    emailReminder = $("#email_notifications").val();
    validateSignUpForm(email, password, emailReminder);

    $.post(
        window.apiHost + "/users/create",
        {
            email: email,
            password: password,
            bookmarkEmailReminder: emailReminder
        },
        function(user) {
            window.current_user = user;
        }
    )
}

function login() {
    email = $("#sign_in_email").val();
    password = $("#sign_in_password").val();
    validateLoginForm(email, password);

    $.ajax({
        method: 'POST',
        url: window.apiHost + "/users/login",
        data: {
            email: email,
            password: password
        },
        dataType: 'json',
        crossDomain: true
    })
    .done(function(user) {
        window.current_user = user;
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        alert("Login failed.");
    })
}

function validateSignUpForm(email, password, emailReminder) {
    // TODO
    alert(email + "\n" + password + "\n" + emailReminder);
}

function validateLoginForm(email, password) {
    // TODO
}

$(document).ready(function() {
    $("#sign_up").click(
        function() {
            e.preventDefault();
            signUp(e);
        }
    );


    $("#sign_in").click(
        function(e) {
            e.preventDefault();
            login();
        }
    );
});
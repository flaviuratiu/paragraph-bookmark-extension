/**
 * Created by Flaviu Ratiu on 03/11/2016.
 */

var auth_token;
var current_user;

function signUp() {
    email = $("#sign_up_email").val();
    password = $("#sign_up_password").val();
    validateSignUpForm(email, password);

    $.ajax({
        method: 'POST',
        url: window.apiHost + "/users/create",
        data: {
            email: email,
            password: password
        },
        dataType: 'json',
        crossDomain: true
    })
    .done(function(user) {
        window.current_user = user;
        Cookies.set('permanent-marker', {
                user: user.id
            });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        alert("Sign-up failed.");
        // TODO: sign up failure message
    })
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
        Cookies.set(
            'permanent-marker', {
             user: user.id
        });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        alert("Login failed.");
        // TODO: login failure message
    })
}

function validateSignUpForm(email, password) {
    // TODO: signup validations
}

function validateLoginForm(email, password) {
    // TODO: login validations
}

$(document).ready(function() {
    $("#sign_up").click(
        function(e) {
            e.preventDefault();
            signUp();
        }
    );

    $("#sign_in").click(
        function(e) {
            e.preventDefault();
            login();
        }
    );
});
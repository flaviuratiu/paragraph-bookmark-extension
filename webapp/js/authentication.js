/**
 * Created by Flaviu Ratiu on 03/11/2016.
 */

function signUp() {
    console.debug("Sign up flow started");
    email = $("#email").val();
    password = $("#password").val();
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
        window.location.href = window.uiBasePath + "/account.html";
        Cookies.set(window.appCookieName, {
                user: user.id
            });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.debug("Sign up failed.\n" + jqXHR + " " + textStatus + "\n" + errorThrown);
    })
}

function login() {
    email = $("#email").val();
    password = $("#password").val();
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
        window.location.href = window.uiBasePath + "/account.html";
        Cookies.set(
             window.appCookieName, {
             user: user.id
        });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.debug("Login failed.\n" + jqXHR + " " + textStatus + "\n" + errorThrown);
    })
}

function validateSignUpForm(email, password) {
    // TODO: signup validations
}

function validateLoginForm(email, password) {
    // TODO: login validations
}

$(document).ready(function() {
    $("#display-sign-up").click(function(e) {
        e.preventDefault();
        displaySignUpForm();
    });

    $("#sign-up").click(
        function(e) {
            e.preventDefault();
            signUp();
        }
    );

    $("#sign-in").click(
        function(e) {
            e.preventDefault();
            login();
        }
    );
});

function displaySignUpForm() {
    $("#sign-in").hide();
    $("#display-sign-up").hide();

    $("#confirm-password").show();
    $("#sign-up").show();
    $("#display-sign-in").show();
}
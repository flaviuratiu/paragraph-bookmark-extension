/**
 * Created by Flaviu Ratiu on 03/11/2016.
 */

function signUp() {
    console.debug("Sign up flow started");
    email = $("#email").val();
    password = $("#password").val();
    var errorMessage = $(".error-message");

    if (validateSignUpForm(email, password)) {
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
        .fail(function(xhr, textStatus, errorThrown) {
            var text = "An error has occurred while registering your new account. Please try again later.";
            var error = JSON.parse(xhr.responseText);
            if (error.message == "Email " + email + " is already in use.") {
                text = "The email " + email + " is already in use."
            }
            errorMessage.text(text);
            errorMessage.show();
        })
    }
}

function login() {
    email = $("#email").val();
    password = $("#password").val();
    var errorMessage = $(".error-message");

    if (validateLoginForm(email, password)) {
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
        .fail(function(xhr, textStatus, errorThrown) {
            var text = "An error has occurred while signing you in to your account. Please try again later.";
            var error = JSON.parse(xhr.responseText);
            if (error.message == "Invalid username or password") {
                text = "Invalid username or password."
            }
            errorMessage.text(text);
            errorMessage.show();
        })
    }
}

function validateSignUpForm(email, password) {
    var errorMessage = $(".error-message");
    var confirmPasswordField = $("#confirm-password");

    if (!validateLoginForm(email, password)) {
        return false;
    }

    if (confirmPasswordField.val() != password) {
        errorMessage.text("Passwords do not match.");
        errorMessage.show();
        return false;
    }
    return true;
}

function validateLoginForm(email, password) {
    var errorMessage = $(".error-message");

    $("div > .required").click(function() {
        errorMessage.hide();
    })

    var validEmail = email.match("^(\\w+)([\\-+.\\'][\\w]+)*@(\\w[\\-\\w]*\\.){1,5}([A-Za-z]){2,6}$");
    if (!validEmail) {
        errorMessage.text("Please fill in a valid e-mail address.");
        errorMessage.show();
        return false;
    }

    if (!password || password == "") {
        errorMessage.text("Please fill in your password.");
        errorMessage.show();
        return false;
    }
    return true;
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
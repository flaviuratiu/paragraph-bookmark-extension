/**
 * Created by Flaviu Ratiu on 03/11/2016.
 */

function signUp() {
    console.debug("Sign up flow started");
    email = $("#email").val();
    password = $("#password").val();

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
            showMessage("error", text);
        })
    }
}

function login() {
    email = $("#email").val();
    password = $("#password").val();

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
            showMessage("error", text);
        })
    }
}

function showMessage(className, text) {
    var errorMessage = $(".message");
    $(errorMessage).attr("class", "message");
    $(errorMessage).addClass(className);
    $(errorMessage).empty();
    $(errorMessage)
        .append($("<p>")
            .text(text)
        );
    errorMessage.show();
}

function validateSignUpForm(email, password) {
    var confirmPasswordField = $("#confirm-password");

    if (!validateLoginForm(email, password)) {
        return false;
    }

    if (confirmPasswordField.val() != password) {
        showMessage("error", "Passwords do not match.");
        return false;
    }
    return true;
}

function validateLoginForm(email, password) {
    var errorMessage = $(".message");

    $(".auth-field").click(function() {
        errorMessage.hide();
    })

    var validEmail = email.match("^(\\w+)([\\-+.\\'][\\w]+)*@(\\w[\\-\\w]*\\.){1,5}([A-Za-z]){2,6}$");
    if (!validEmail) {
        showMessage("error", "Please fill in a valid e-mail address.");
        return false;
    }

    if (!password || password == "") {
        showMessage("error", "Please fill in your password.");
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
"use strict";

$(document).ready(function() {
  const ref = new Firebase('https://remote-hound.firebaseio.com/')

  if (ref.getAuth()) window.location.href = "logout.html";

  document.getElementById("login").addEventListener("click", function() {
    let email = document.getElementById("email").value;
    let pw = document.getElementById("pw").value;
    ref.authWithPassword({
      email    : email,
      password : pw
    }, function(error, authData) {
      if (error) {
        console.log("Login Failed!", error);
      } else {
        console.log("Authenticated successfully with payload:", authData);
        console.log(ref.getAuth());
        window.location.href = "logout.html";
      }
    });
  });

  document.getElementById("create-acct").addEventListener("click", function() {
    console.log(ref.getAuth());
    let email = document.getElementById("email").value;
    let pw = document.getElementById("pw").value;
    ref.createUser({
      email    : email,
      password : pw
    }, function(error, userData) {
      if (error) {
        console.log("Error creating user:", error);
      } else {
        console.log("Successfully created user account with uid:", userData.uid);
        window.location.href = "logout.html";
      }
    });
  });
});

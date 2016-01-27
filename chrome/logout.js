"use strict";

$(document).ready(function() {
  const ref = new Firebase('https://remote-hound.firebaseio.com/')

  document.getElementById("logout").addEventListener("click", function() {
    ref.unauth();
    window.location.href = "login.html";
  });

});

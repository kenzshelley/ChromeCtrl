"use strict";

var s = document.createElement("script");
s.type = "text/javascript";
s.src = "https://cdn.firebase.com/v0/firebase.js";
$("head").append(s);

const ref = new Firebase('https://remote-hound.firebaseio.com/')

let userid = "test_user";
const tasksRef = ref.child("users").child(userid).child("tasks");

tasksRef.on("value", function(snapshot) {
  console.log("something changed!")
  let data = snapshot.val();
  for(var id in data) {
    let task = data[id];
    let text = task.text; 
    console.log(task);
    console.log(text);

    // Get array of words
    text = text.split(" ");
    // Delete the task
//    tasksRef.child(id).remove();

      chrome.tabs.getSelected(function(tab) {
        console.log(tab.id); 
        console.log("sending tab");
        chrome.tabs.sendMessage(tab.id, {function_name: "play"}, function(response) {
          console.log(response);
        });
      });
  }
});



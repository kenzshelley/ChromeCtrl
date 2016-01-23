"use strict";

var s = document.createElement("script");
s.type = "text/javascript";
s.src = "https://cdn.firebase.com/v0/firebase.js";
$("head").append(s);

const ref = new Firebase('https://remote-hound.firebaseio.com/')

let userid = "test_user";
const tasksRef = ref.child("users").child(userid).child("tasks");

function nextTab() {
  // first, get currently active tab
  chrome.tabs.query({active: true}, function (tabs) {
    if (tabs.length) {
      var activeTab = tabs[0],
      tabId = activeTab.id,
      currentIndex = activeTab.index;
      // next, get number of tabs in the window, in order to allow cyclic next
      chrome.tabs.query({currentWindow: true}, function (tabs) {
        var numTabs = tabs.length;
        // finally, get the index of the tab to activate and activate it
        chrome.tabs.query({index: (currentIndex+1) % numTabs}, function (tabs){
          if (tabs.length) {
            var tabToActivate = tabs[0],
            tabToActivate_Id = tabToActivate.id;
            chrome.tabs.update(tabToActivate_Id, {active: true});
          }
        });
      });
    }
  });
}

function newTab() {
    chrome.tabs.create({}, function (tab) {
      console.log(tab);
    });
}

newTab();

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



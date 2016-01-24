"use strict";

var s = document.createElement("script");

const ref = new Firebase('https://remote-hound.firebaseio.com/')
ref.unauth();

chrome.extension.onConnect.addListener(function(port) {
  console.log("Connected .....");
  port.onMessage.addListener(function(msg) {
    console.log("message recieved "+ msg);
    let uid = msg;
    if (ref.getAuth()) uid = ref.getAuth().uid;
    console.log("test");
    const tasksRef = ref.child("users").child(uid).child("tasks");
    tasksRef.on("value", function(snapshot) {
      let data = snapshot.val();
      for(var id in data) {
        let taskItem = data[id];
        let text = taskItem.text; 
        text = text.toLowerCase();

        // Get array of words
        text = text.split(" ");
        let task = getTaskName(text);
        if (task.name == null) {
          console.log("null task!");
          console.log(text);
          tasksRef.child(id).remove();
          return;
        }

        console.log("grabbed a task!");
        console.log(task);

        if (task.data.scope === "content") {
          chrome.tabs.getSelected(function(tab) {
            chrome.tabs.sendMessage(tab.id,
                                    {function_name: task.name, params: task.params},
                                    function(response) {
                                      console.log(response);
                                    });
          });
        } else if (task.data.scope === "browser") {
          let fn = window[task.name];
          fn(task.params);
        }

        // Delete the task
        tasksRef.child(id).remove();
      }
    });
  });
});

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
  console.log("new tab");
    chrome.tabs.create({}, function (tab) {
      console.log(tab);
      console.log("is tab open?")

    });
}

function createBookmark() {
     var url = "";
      chrome.tabs.query({'active': true, 'lastFocusedWindow': true}, function (tabs) {
        url = tabs[0].url;
        console.log(url);
        chrome.bookmarks.create({'title': url, 'url': url}, function(newFolder) {
          console.log("adding bookmark!");
        });
}




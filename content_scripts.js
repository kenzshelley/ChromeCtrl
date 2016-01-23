function pausePlay(isPlay) {
	var videoTagElements = document.getElementsByTagName("video");
	if (videoTagElements.length != 0) {
		if (isPlay == true) {
			videoTagElements[0].play();
		} else {
			videoTagElements[0].pause();
		}		
	} else if (typeof ifp !== undefined) {
		if (isPlay == true) {	
			ifp.play();
		} else {
			ifp.pause();
		}
	} else {
		console.log("wrong move, try again");
	}
}

function switchTab() {
  // first, get currently active tab
  chrome.tabs.query({active: true}, function(tabs) {
    if (tabs.length) {
      var activeTab = tabs[0],
      tabId = activeTab.id,
      currentIndex = activeTab.index;
      // next, get number of tabs in the window, in order to allow cyclic next
      chrome.tabs.query({currentWindow: true}, function (tabs) {
        var numTabs = tabs.length;
        // finally, get the index of the tab to activate and activate it
        chrome.tabs.query({index: (currentIndex+1) % numTabs}, function(tabs){
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
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

function volumeUp() {
	var vid = document.getElementsByTagName("video");
	if (vid.length != 0 & vid[0].volume <= .98) vid[0].volume += .02
}

function volumeDown() {
	var vid = document.getElementsByTagName("video");
	if (vid.length != 0 & vid[0].volume >= 0.02) vid[0].volume -= .02
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
  console.log("Received message!");


  sendResponse("received the message!");
  return true;
});
	

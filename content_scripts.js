function pause() {
	var videoTagElements = document.getElementsByTagName("video");
	if (videoTagElements.length != 0) {
    videoTagElements[0].pause();
	} else if (typeof ifp !== undefined) {
    ifp.pause();
	} else {
		console.log("wrong move, try again");
	}
}

function play() {
  var videoTagElements = document.getElementsByTagName("video");
  if (videoTagElements.length != 0) {
    videoTagElements[0].play();
  } else if (typeof ifp !== undefined) {
    ifp.play();
  } else {
    console.log("wrong move, try again");
  }
}

function volumeUp() {
	var vid = document.getElementsByTagName("video");
	if (vid.length != 0 & vid[0].volume <= .98) vid[0].volume += .02;
}

function volumeDown() {
	var vid = document.getElementsByTagName("video");
	if (vid.length != 0 & vid[0].volume >= 0.02) vid[0].volume -= .02;
}

function scrollDown() {
  window.scrollBy(0, 200);
}

function scrollUp() {
  window.scrollBy(0, -200);
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
  console.log("Received message!");


  sendResponse("received the message!");
  return true;
});
	

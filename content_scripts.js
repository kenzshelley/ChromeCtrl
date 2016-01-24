"use strict";

function pause() {
console.log("Pause");
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
  console.log("Play");
  document.getElementsByTagName("video")[0].playbackRate = 1.0;
  var videoTagElements = document.getElementsByTagName("video");
  if (videoTagElements.length != 0) {
    videoTagElements[0].play();
  } else if (typeof ifp !== undefined) {
    ifp.play();
  } else {
    console.log("wrong move, try again");
  }
}

function volume(params) {
  console.log("changing volume!");
  const vids = document.getElementsByTagName("video");
  if (vids.length === 0) return; 
  const vid = vids[0];
  if (!params || !params.direction) return;

  if (params.direction == "up" || params.direction == "higher") {
    var dir = "up";
  } else if (params.direction == "down" || params.direction == "lower") {
    var dir = "down";
  }

  let amount = .1;
  if (params.amount == "lot") {
    amount = .2; 
  } else if (params.amount == "little") {
    amount = .05;
  }

  if (dir == "up") { 
    if (vid.volume <= (1 - amount)) vid.volume += amount;
  } else {
    if (vid.volume >= amount) vid.volume -= amount;
  }
}

function scroll(params) {
  if (!params) return;
  if (!params.direction) return;
  const dir = params.direction;
  
  let amount = 400;
  if (params.amount == "lot") {
    amount = 600;
  } else if (params.amount == "little") {
    amount = 200; 
  }

  if (dir == "up") amount = amount * -1;
  window.scrollBy(0, amount);
}

function fastForward() {
 document.getElementsByTagName("video")[0].playbackRate = 5.0;
}

function reload() {
  location.reload();
}

function back() {
  history.back();

}

/* 
  Expects a 
*/
function click(params) {
  let text = params.text;
  // Attempt to find a link that matches text
  let linkQuery = [];
  // if the query contains 'on'
  if (text.indexOf("on") >= 0) {
    
  }

}

function search(params) {
  var str = "https://www.google.com/#q=" + params.query[0];
  for(var x = 1; x++; x < params.query.length) {
    str += "+" + params.query[x];
  }
  console.log(str);
  window.location.href = str;
}
console.log("test");
search({query: ["hi", "hello"]});

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
  console.log("Received message!");

  let fn = window[request.function_name];
  console.log(request.function_name);
  fn(request.params);

  sendResponse("received the message!");
  return true;
});
	

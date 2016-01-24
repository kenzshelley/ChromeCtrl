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
  console.log("scrolling!");
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

function click(params) {
  console.log("click!");
  let text = params.text;
  
  // Attempt to seperate the link querey from the text:
  let linkQuery = text;
  let onInd = linkQuery.indexOf("on"); 
  if (onInd >= 0 && linkQuery[onInd - 1] == "click") {
    linkQuery.splice(0, onInd); 
  } else {
    let clickInd = linkQuery.indexOf("click");
    let selectInd = linkQuery.indexOf("select");
    if (clickInd) {
      linkQuery.splice(0, clinkInd);
    } else if (selectInd) {
      linkQuery.splice(0, selectInd);
    }
  } 
  let querySet = new Set(linkQuery);
  
  // Get the links: 
  const links = document.getElementsByTagName("a");
  // Get the buttons:
  const buttons = document.getElementsByTagName("button"); 
  
  // Figure out which link/button is the closest match:
  let best = {
    score: 0,
    element: null
  }
    for (let index in links) {
      let link = links[index];
      let score = 0;
      if (!link.innerHTML) continue;
      let text = link.innerText.toLowerCase().split(" ");
      for (let index in text) {
        let word = text[index];
        if (querySet.has(word)) score += 1;
      }
      if (score > best.score) {
      // If every word matches just quit.
      best = {
        score: score,
        element: link,
        name: link.innerHTML
      } 
      if (score == querySet.size) break;
    }
  }

  best.element.click();
}

function test() {
  console.log("in test!");
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
  console.log("Received message!");

  let fn = window[request.function_name];
  console.log(request.function_name);
  fn(request.params);

  sendResponse("received the message!");
  return true;
});
	

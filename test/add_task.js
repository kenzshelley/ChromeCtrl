"use strict";

const ref = new Firebase('https://remote-hound.firebaseio.com/')

const userid = "efec5b71-0bb2-4463-aabc-4f22a9319804";
let task = {
  "text": "play"
}
ref.child("users").child(userid).child("tasks").push(task);

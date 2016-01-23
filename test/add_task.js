"use strict";

const ref = new Firebase('https://remote-hound.firebaseio.com/')

const userid = "test_user";
let task = {
  "text": "volume down a lot"
}
ref.child("users").child(userid).child("tasks").push(task);

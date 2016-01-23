"use strict";

const ref = new Firebase('https://remote-hound.firebaseio.com/')

const userid = "test_user";
const task = {
  "text": "next tab"
}
ref.child("users").child(userid).child("tasks").push(task);

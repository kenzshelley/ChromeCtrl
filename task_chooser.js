// Assumed that tasks_list.js is included before this file. 
// tasks is defined in tasks_list.js

"use strict";

function getTaskName(wordList) {
  let wordSet = new Set(wordList);
  let bestTask = {"name": null, "score": 0, "data": null}; 

  for (var taskName in tasks) {
    let taskData = tasks[taskName];
    // compute score
    let score = 0;
    for (var index in taskData.keywords) {
      let word = taskData.keywords[index];
      if (wordSet.has(word)) score += 1; 
    }

    // if score is higher than best, replace it
    if (score > bestTask.score) {
      bestTask = {
        "name": taskName, 
        "score": score,
        "data": taskData
      }
    }
  } 

  return bestTask;
}

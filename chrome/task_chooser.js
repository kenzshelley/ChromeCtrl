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
    for (let index in taskData.keywords) {
      let word = taskData.keywords[index];
      if (wordSet.has(word)) score += 1; 
    }

    // if score is higher than best, replace it
    if (score > bestTask.score) {
      // Find params
      let params = {};
      for (var param in taskData.params) {
        for (let index in taskData.params[param]) {
          let value = taskData.params[param][index];
          if (wordSet.has(value)) {
            params[param] = value; 
            break;
          } 
        } 
      }
      bestTask = {
        "name": taskData.function, 
        "score": score,
        "data": taskData,
        "params": params
      }
      // Check if plaintext should be sent
      if (taskData.sendText) {
        bestTask.params.text = wordList; 
      }
    }
  } 

  return bestTask;
}

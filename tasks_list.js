const tasks = {
  "play": {
    "function": "play",
    "keywords": [
      "play", 
      "start"
    ],
    "scope": "content"
  }, 
  "pause": {
    "function": "pause",
    "keywords": [
      "pause",
      "stop"
    ],
    "scope": "content"
  },
  "nextTab": {
    "function": "nextTab",
    "keywords": [
      "next", 
      "tab", 
      "page"
    ],
    "scope": "browser"
  }, 
  "scroll": {
    "function": "scroll", 
    "keywords": [
      "scroll", 
      "move"
    ], 
    "scope": "content",
    "params": {
      "direction": [
        "up",
        "down"
      ], 
      "amount": [
        "lot", 
        "little"
      ]
    }
  },
  "volume": {
    "function": "volume",
    "keywords": [
      "volume",
      "sound",
      "turn"
    ],
    "scope": "content",
    "params": {
      "direction": [
        "up", 
        "down",
        "higher",
        "lower"
      ], 
      "amount": [
        "lot", 
        "little"
      ]
    }
  },
  "fastforward": {
    "function": "fastForward",
    "keywords": [
      "fastforward",
      "fast",
      "forward",
      "ahead"
    ],
    "scope": "content",
  },
  "reload": {
    "function": "reload",
    "keywords": [
      "reload",
      "refresh",
      "load"
    ],
    "scope": "content"
  },
  "newTab": {
    "function": "newTab",
    "keywords": [
      "new",
      "tab", 
      "create"
    ],
    "scope": "browser"
  },
  "back": {
    "function": "back",
    "keywords": [ 
      "back" 
    ],
    "scope": "content"
  },
  "click": {
    "function": "click",
    "keywords": [
      "select",
      "click",
    ],
    "scope": "content",
    "sendText": "true"
  },
  "search": {
    "function": "search",
    "keywords": [
      "search",
      "find",
    ],
    "scope": "content",
    "sendText": "true"
  },
  "bookmark": {
    "function": "createBookmark",
    "keywords": [
      "bookmark",
      "favorite",
      "save"
    ],
    "scope": "browser"
  }
}

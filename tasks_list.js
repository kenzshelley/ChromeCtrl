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
    "function": "pause",
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
    "scope": "background"
  },
  "back": {
    "function": "back",
    "keywords" [ 
      "back" 
    ],
    "scope": "content"
  }
}

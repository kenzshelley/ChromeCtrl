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
  }
}

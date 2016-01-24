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
  "test": {
    "function": "test",
    "keywords": [
      "test" 
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
  "previousTab": {
    "function": "previousTab",
    "keywords": [
      "previous", 
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
      "ahead",
      "movie",
      "film",
      "show"
    ],
    "scope": "content",
  },
  "rewind": {
    "function": "rewind",
    "keywords": [
      "rewind",
      "movie",
      "film",
      "show"
    ],
    "scope": "content",
  },
  "restart": {
    "function": "restart",
    "keywords": [
      "restart",
      "start",
      "over"
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
  "closeTab": {
    "function": "closeTab",
    "keywords": [
      "close",
      "tab", 
      "remove",
      "exit"
    ],
    "scope": "browser"
  },
  "back": {
    "function": "back",
    "keywords": [ 
      "back",
      "go",
      "page"
    ],
    "scope": "content"
  },
  "forward": {
    "function": "forward",
    "keywords": [ 
      "forward",
      "forwards",
      "go",
      "page"
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
      "find"
    ],
    "scope": "browser",
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
  },
  "fullScreen": {
    "function": "fullScreen",
    "keywords": [
    "fullscreen",
    "full",
    "screen",
    "maximize"
    ],
    "scope": "browser"
  },
  "standardScreen": {
    "function": "standardScreen",
    "keywords": [
      "escape",
      "normal",
      "minimize",
      "escape"
    ],
    "scope": "browser"
  }
}

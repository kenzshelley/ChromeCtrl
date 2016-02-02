### About
ChromeCtrl is an application that allows you to control your Chrome browser by speaking into an app on your phone. 

#### Entertainment
* Play/pause video
* Fastforward
* Adjust volume
* Switch to full screen

#### Navigation
* Scroll up and down on a page
* Move between tabs
* Close a tab
* Create a new tab
* Select links & buttons
* Search for a phrase
* Go directly to common sites (e.g. Facebook)

### Setup
You will need to [install the ChromeCtrl chrome extension](https://chrome.google.com/webstore/detail/chromectrl/oigibapalikpbhaoekiggmdljlgjefkl) to use the system.

#### iOS Instructions
* You will need to [create your own Houndify account](http://houndify.com/)
* Register a new client and activate the Speech-to-Text domain.
* In Xcode create a new header file called 'Secrets.h' with the following two
  lines:  
    ```NSString * const houndifyClientID = @"<INSERT HOUNDIFY CLIENT ID HERE>";```  
    ```NSString * const houndifyClientKey = @"<INSERT HOUNDIFY CLIENT KEY HERE>";```
* Build and install the app onto your phone.

#### Android Instructions
* You will need to [create your own Houndify account](http://houndify.com/)
* Create a file called "Constants.java" in java/jamn/ and set public static variables "CLIENT_ID" and "CLIENT_KEY" to the id/key for your Houndify app.
* Create a houndify.properties file and set username=[your Houndify username] and password=[your Houndify password].

### Penn Apps Winter 2016 Team:  
Nastasia Efremkina  
Addison Shelley  
Mackenzie Shelley  
Jamie Sookprasong  

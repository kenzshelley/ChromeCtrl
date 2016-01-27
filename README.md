Penn Apps Winter 2016 Team:
Nastasia Efremkina
Addison Shelley
Mackenzie Shelley
Jamie Sookprasong

#### iOS Instructions:
* You will need to [create your own Houndify account](http://houndify.com/)
* Register a new client and activate the Speech-to-Text domain.
* In Xcode create a new header file called 'Secrets.h' with the following two
  lines:  
    ```NSString * const houndifyClientID = @"<INSERT HOUNDIFY CLIENT ID HERE>";```  
    ```NSString * const houndifyClientKey = @"<INSERT HOUNDIFY CLIENT KEY HERE>";```
* Build and install the app onto your phone.

#### ChromeCtrl Android
ChromeCtrl is an application that allows you to control your Chrome browser by speaking into an app on your phone. You will need to install the ChromeCtrl chrome extension to use the system: https://chrome.google.com/webstore/ [currently not possible because Google is a fuck].

## Setup
1) Create a houndify developer account
2) Create a file called "Constants.java" in java/jamn/ and set public static variables "CLIENT_ID" and "CLIENT_KEY" to the id/key for your Houndify app.
3) Create a houndify.properties file and set username=[your Houndify username] and password=[your Houndify password].

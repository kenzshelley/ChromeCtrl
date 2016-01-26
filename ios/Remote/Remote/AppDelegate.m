//
//  AppDelegate.m
//  HoundifySDK Test Application
//
//  Created by Cyril Austin on 10/29/15.
//  Copyright © 2015 SoundHound, Inc. All rights reserved.
//

// Above is the original attribution.
// The original code has since been modified by Jamei Sookprasong

#import "AppDelegate.h"
#import "Secrets.h"
#import <HoundSDK/HoundSDK.h>

#pragma mark - AppDelegate

@interface AppDelegate()

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    [Hound setClientID:houndifyClientID];
    [Hound setClientKey:houndifyClientKey];
    
    return YES;
}

@end

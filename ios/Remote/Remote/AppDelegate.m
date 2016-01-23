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
#import <HoundSDK/HoundSDK.h>

#pragma mark - AppDelegate

@interface AppDelegate()

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    [Hound setClientID:@"ARcRubGNfzswcdjnzNu4XQ=="];
    [Hound setClientKey:@"O3N403Y8EDIga45xfJBSr7z7JiW4DxNHDP-5Jz5G9UopblroqcvrRx_ufFDW4E8oWGBIIywdPwKUCor4JtKCaQ=="];
    
    return YES;
}

@end

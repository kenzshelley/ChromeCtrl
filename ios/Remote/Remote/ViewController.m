//
//  ViewController.m
//  HoundifySDK Test Application
//
//  Created by Cyril Austin on 10/29/15.
//  Copyright © 2015 SoundHound, Inc. All rights reserved.
//

// Above is the original attribution.
// The original code has since been modified by Jamei Sookprasong

#import "ViewController.h"
#import <HoundSDK/HoundSDK.h>
#import <Firebase/Firebase.h>

#define VOICE_SEARCH_END_POINT @"https://api.houndify.com/v1/audio"

#pragma mark - ViewController

@interface ViewController()

@property(nonatomic, strong) IBOutlet UITextView* responseTextView;

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
//    // Setup UI
//    
//    self.responseTextView.text = nil;
//    
//    UIImage* image = [UIImage imageNamed:@"ic-hound-small"];
//    
//    UIImageView* imageView = [[UIImageView alloc] initWithImage:image];
//    
//    imageView.contentMode = UIViewContentModeScaleAspectFit;
//    
//    imageView.frame = CGRectMake(0, 0, 32, 32);
//    
//    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]
//        initWithCustomView:imageView];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // Turn on battery monitoring
    UIDevice *device = [UIDevice currentDevice];
    device.batteryMonitoringEnabled = YES;
    
    // Check current charging state to detect if iPhone is connected to a power source
    UIDeviceBatteryState currentState = [[UIDevice currentDevice] batteryState];
    if (currentState == UIDeviceBatteryStateCharging || currentState == UIDeviceBatteryStateFull) {
        [self turnOnHotPhraseListener];
    } else {
        [self turnOffHotPhraseListener];
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(pluggedDetected:) name:UIDeviceBatteryStateDidChangeNotification object:nil];
}

- (void)pluggedDetected:(NSNotification*)notification
{
    UIDeviceBatteryState currentState = [[UIDevice currentDevice] batteryState];
    if (currentState == UIDeviceBatteryStateCharging || currentState == UIDeviceBatteryStateFull) {
        self.responseTextView.text = @"plugInDetected";
        [self turnOnHotPhraseListener];
    } else {
        self.responseTextView.text = @"unplugDetected";
        [self turnOffHotPhraseListener];
    }

    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(pluggedDetected:) name:UIDeviceBatteryStateDidChangeNotification object:nil];
    
}

- (void)turnOnHotPhraseListener
{
    self.responseTextView.text = @"turn on hot";
    HoundVoiceSearch.instance.enableHotPhraseDetection = YES;
    [HoundVoiceSearch.instance startListeningWithCompletionHandler:^(NSError* error) {
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error)
            {
                self.responseTextView.text = error.localizedDescription;
            }
        });
    }];
    
    [NSNotificationCenter.defaultCenter addObserver:self selector:@selector(hotPhrase) name:HoundVoiceSearchHotPhraseNotification object:nil];
}

- (void)turnOffHotPhraseListener
{
    self.responseTextView.text = @"turn off hot";
    HoundVoiceSearch.instance.enableHotPhraseDetection = NO;
    [HoundVoiceSearch.instance stopListeningWithCompletionHandler:^(NSError* error) {
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error)
            {
                self.responseTextView.text = error.localizedDescription;
            }
        });
    }];
}

- (void)hotPhrase
{
    // "OK Hound" detected
    [self search:nil];
}

- (IBAction)search:(UIButton*)button
{
    self.responseTextView.text = nil;
    
    NSURL* URL = [NSURL URLWithString:VOICE_SEARCH_END_POINT];
    
    NSDictionary* requestInfo = @{
    
        // Insert request parameters
    };
    
    // Show listening screen
    [Houndify.instance
        presentListeningViewControllerInViewController:self.navigationController
        fromView:button
        requestInfo:requestInfo
        endPointURL:URL
        responseHandler:^(NSError* error, HoundDataHoundServer* response, NSDictionary* dictionary) {
            
            if (error)
            {
                if ([error.domain isEqualToString:HoundVoiceSearchErrorDomain]
                    && error.code == HoundVoiceSearchErrorCodeCancelled)
                {
                    self.responseTextView.text = @"Search cancelled";
                }
                else if ([error.domain isEqualToString:HoundVoiceSearchErrorDomain]
                    && error.code == HoundVoiceSearchErrorCodeAuthenticationFailed)
                {
                    self.responseTextView.text = @"Authentication failed";
                }
                else
                {
                    self.responseTextView.text = @"Search failed";
                }
            }
            else
            {
                // Display written response in UI
                HoundDataCommandResult* commandResult = response.allResults.firstObject;
                self.responseTextView.text = commandResult.writtenResponse;
                
                // Post the task to Firebase
                Firebase *usersRef = [[Firebase alloc] initWithUrl:@"https://remote-hound.firebaseio.com/users"];
                NSString* username = @"test_user";
                Firebase* userRef = [usersRef childByAppendingPath:username];
                Firebase* userTasksRef = [userRef childByAppendingPath:@"tasks"];
                Firebase* taskRef = [userTasksRef childByAutoId];
                NSDictionary* task = @{ @"text": commandResult.writtenResponse };
                [taskRef setValue:task];
            }
            
            [self dismissSearch];
        }
    ];
}

- (void)dismissSearch
{
    [Houndify.instance dismissListeningViewControllerAnimated:YES completionHandler:^{}];
}

- (IBAction)logoutAction:(UIButton *)sender {
    NSLog(@"logout");
    Firebase *ref = [[Firebase alloc] initWithUrl:@"https://remote-hound.firebaseio.com"];
    [ref unauth];
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end

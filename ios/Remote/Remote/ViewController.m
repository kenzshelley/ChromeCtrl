//
//  ViewController.m
//  Remote
//
//  Created by Jamie Sookprasong on 23/1/16.
//  Copyright © 2016 Jamie Sookprasong. All rights reserved.
//

// adapted from SoundHound's Houndify Demo

#import "ViewController.h"
#import <HoundSDK/HoundSDK.h>

#define VOICE_SEARCH_END_POINT       @"https://api.houndify.com/v1/audio"

@interface ViewController ()

@property (strong, nonatomic) IBOutlet UILabel *textFromSpeech;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    // Setup UI
    
    self.textFromSpeech.text = nil;
    
    UIImage* image = [UIImage imageNamed:@"ic-hound-small"];
    
    UIImageView* imageView = [[UIImageView alloc] initWithImage:image];
    
    imageView.contentMode = UIViewContentModeScaleAspectFit;
    
    imageView.frame = CGRectMake(0, 0, 32, 32);
    
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]
                                             initWithCustomView:imageView];
}
- (IBAction)listen:(UIButton *)sender {
    self.textFromSpeech.text = nil;
    
    NSURL* URL = [NSURL URLWithString:VOICE_SEARCH_END_POINT];
    
    NSDictionary* requestInfo = @{
                                  
                                  // Insert request parameters
                                  };
    
    // Show listening screen
    
    [Houndify.instance
     presentListeningViewControllerInViewController:self.navigationController
     fromView:sender
     requestInfo:requestInfo
     endPointURL:URL
     responseHandler:^(NSError* error, HoundDataHoundServer* response, NSDictionary* dictionary) {
         
         if (error)
         {
             // Check for errors
             
             if ([error.domain isEqualToString:HoundVoiceSearchErrorDomain]
                 && error.code == HoundVoiceSearchErrorCodeCancelled)
             {
                 self.textFromSpeech.text = @"Search cancelled";
             }
             else if ([error.domain isEqualToString:HoundVoiceSearchErrorDomain]
                      && error.code == HoundVoiceSearchErrorCodeAuthenticationFailed)
             {
                 self.textFromSpeech.text = @"Authentication failed";
             }
             else
             {
                 self.textFromSpeech.text = @"Search failed";
             }
         }
         else
         {
             // Display written response in UI
             
             HoundDataCommandResult* commandResult = response.allResults.firstObject;
             
             self.textFromSpeech.text = commandResult.writtenResponse;
             
             // Any properties from the documentation can be accessed through the keyed accessors, e.g.:
             
             NSDictionary* nativeData = commandResult[@"NativeData"];
             
             NSLog(@"NativeData: %@", nativeData);
         }
         
         [self dismissSearch];
     }];
}

- (void)dismissSearch
{
    [Houndify.instance dismissListeningViewControllerAnimated:YES completionHandler:^{}];
}

@end

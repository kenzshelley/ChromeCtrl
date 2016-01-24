//
//  LoginViewController.m
//  Remote
//
//  Created by Jamie Sookprasong on 23/1/16.
//  Copyright © 2016 Jamie Sookprasong. All rights reserved.
//

#import "LoginViewController.h"
#import <Firebase/Firebase.h>

#pragma mark - LoginViewController

@interface LoginViewController()

@property (weak, nonatomic) IBOutlet UITextField *emailTextFieldInput;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextFieldInput;
@property (strong, nonatomic) UITapGestureRecognizer *tap;

@end

@implementation LoginViewController

Firebase *ref = nil;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    ref = [[Firebase alloc] initWithUrl:@"https://remote-hound.firebaseio.com/"];
    
    _emailTextFieldInput.delegate = self;
    _passwordTextFieldInput.delegate = self;
    _tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyboard)];
    _tap.enabled = NO;
    [self.view addGestureRecognizer:_tap];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    
    [ref observeAuthEventWithBlock:^(FAuthData *authData) {
        if (authData) {
            [self performSegueWithIdentifier:@"LoginSegue" sender:nil];
        }
    }];
}

- (IBAction)loginAction:(UIButton *)sender {
    [ref authUser:self.emailTextFieldInput.text password:self.passwordTextFieldInput.text withCompletionBlock:^(NSError *error, FAuthData *authData) {
        if (error) {

            UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"Login unsuccessful." message:nil preferredStyle:UIAlertControllerStyleAlert];
            
            UIAlertAction *ok = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {                                     [alert dismissViewControllerAnimated:YES completion:nil];
            }];
            
            [alert addAction:ok];
            
            [self presentViewController:alert animated:YES completion:nil];

        } else {
            NSLog(@"Login successful");
        }
    }];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    _tap.enabled = YES;
    return YES;
}

- (void)hideKeyboard
{
    [_emailTextFieldInput resignFirstResponder];
    [_passwordTextFieldInput resignFirstResponder];
    _tap.enabled = NO;
}

@end
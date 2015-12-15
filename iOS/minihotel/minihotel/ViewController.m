//
//  ViewController.m
//  minihotel
//
//  Created by Arjun Shukla on 12/2/15.
//  Copyright © 2015 cmpe275. All rights reserved.
//

#import "ViewController.h"
#import "MTBBarcodeScanner.h"
#import "AFNetworking.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UIView *viewScanner;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *spinner;

@end

@implementation ViewController
MTBBarcodeScanner * scanner;
AFHTTPRequestOperationManager *manager;
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    self.viewScanner.hidden=true;
    self.spinner.hidden = true;
    manager = [AFHTTPRequestOperationManager manager];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)onCheckinGuest:(id)sender {
    
    self.viewScanner.hidden=false;
    [self.view endEditing:YES];
    
    scanner = [[MTBBarcodeScanner alloc] initWithPreviewView:self.viewScanner];
    [MTBBarcodeScanner requestCameraPermissionWithSuccess:^(BOOL success) {
        if (success) {
            [scanner startScanningWithResultBlock:^(NSArray *codes) {
                AVMetadataMachineReadableCodeObject *code = [codes firstObject];
                NSLog(@"Found code: %@", code.stringValue);
                
                self.viewScanner.hidden=true;
                
                [self sendReservation:code.stringValue];
                [scanner stopScanning];
                [self.spinner startAnimating];
                self.spinner.hidden=false;
            }];
        } else {
            // The user denied access to the camera
        }
    }];
}

-(void) sendReservation:(NSString*)code{
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
//    [request setValue:@"image/png" forHTTPHeaderField:@"Content-Type"];
//    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
    [manager GET:[@"https://3449fe57.ngrok.io/CmpE275Team07Fall2015TermProject/v1/reservationcheck/" stringByAppendingString:code] parameters:nil success:^(AFHTTPRequestOperation * _Nonnull operation, id  _Nonnull responseObject) {
        NSLog(@"JSON: %@", responseObject);
        
        
//        [self checkinGuest:responseObject];
        [self makeRequest:(NSDictionary*)responseObject];
        
        
    } failure:^(AFHTTPRequestOperation * _Nullable operation, NSError * _Nonnull error) {
        NSLog(@"Error: %@", error);
    }];
    
}

-(void)checkinGuest:(NSDictionary*)arr{
    
    
    [manager PUT:@"https://3449fe57.ngrok.io/CmpE275Team07Fall2015TermProject/v1/checkinGuest" parameters:arr success:^(AFHTTPRequestOperation *operation, id jsonData) {
        NSLog(@"JSON: %@", jsonData);
        manager.responseSerializer = [AFHTTPResponseSerializer serializer];
        UIAlertView *alert = [[UIAlertView alloc
                               ] initWithTitle:@"Success" message:@"Guest successfully checkedin" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
        [alert show];
        [self.spinner stopAnimating];
        self.spinner.hidden = true;
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
    }];
    
}


-(void)makeRequest:(NSDictionary*)tmp{
    @try
    {
        NSURL *url=[NSURL URLWithString:@"https://3449fe57.ngrok.io/CmpE275Team07Fall2015TermProject/v1/checkinGuest"];
//        NSDictionary *tmp = [[NSDictionary alloc] initWithObjectsAndKeys:
//                             txtAmount.text, @"amount",
//                             nil];
        NSError *error = [[NSError alloc] init];
        NSData *postData = [NSJSONSerialization dataWithJSONObject:tmp options:0 error:&error];
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
        [request setURL:url];
        [request setHTTPMethod:@"PUT"];
        [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
        [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        [request setHTTPBody:postData];
        NSHTTPURLResponse *response = nil;
        NSData *urlData=[NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
        NSLog(@"Response code: %ld", (long)[response statusCode]);
        if ([response statusCode] >= 200 && [response statusCode] < 300)
        {
            NSString *responseData = [[NSString alloc]initWithData:urlData encoding:NSUTF8StringEncoding];
            NSLog(@"Response ==> %@", responseData);
            
            
            NSError *error = nil;
            NSDictionary *jsonData = [NSJSONSerialization
                                      JSONObjectWithData:urlData
                                      options:NSJSONReadingMutableContainers
                                      error:&error];
            
            UIAlertView *alert = [[UIAlertView alloc
                                   ] initWithTitle:@"Success" message:@"Guest successfully checkedin" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
            [alert show];
            [self.spinner stopAnimating];
            self.spinner.hidden = true;
        }
        else
        {
            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Network Error" message:@"Please check your network connection and try again" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
            [alert show];
        }
    }
    @catch (NSException * e) {
        NSLog(@"Exception: %@", e);
    }
}
@end

# MTBBarcodeScanner

[![Version](https://img.shields.io/cocoapods/v/MTBBarcodeScanner.svg?style=flat)](http://cocoadocs.org/docsets/MTBBarcodeScanner)
[![License](https://img.shields.io/cocoapods/l/MTBBarcodeScanner.svg?style=flat)](http://cocoadocs.org/docsets/MTBBarcodeScanner)
[![Platform](https://img.shields.io/cocoapods/p/MTBBarcodeScanner.svg?style=flat)](http://cocoadocs.org/docsets/MTBBarcodeScanner)

A lightweight, easy-to-use barcode scanning library for iOS 7+. This library is built on top of Apple's excellent AVFoundation framework, and will continue to receive updates as Apple releases them.

With this library you can:

- Supply a custom UIView for displaying camera input
- Read any number of barcodes before stopping
- Read multiple codes on the screen at the same time (2D barcodes only)
- Easily read codes with a block, including the string value and position in the preview
- Easily flip from the back to the front camera
- Toggle the device's torch on and off
- Freeze and unfreeze capture to display a still image from the camera

See demo project for examples of capturing one code, multiple codes, or highlighting codes as valid or invalid in the live preview.

---

<img src="https://raw.githubusercontent.com/mikebuss/MTBBarcodeScanner/develop/Assets/MTBBarcodeScanner.png" width=100% height=100%>

#### Sample Barcodes

<img src="https://raw.githubusercontent.com/mikebuss/MTBBarcodeScanner/develop/Assets/sample-barcodes.png" width=50% height=50%>

## Installation

MTBBarcodeScanner can be installed via [CocoaPods](http://cocoapods.org) by adding the following line to your Podfile:

`pod "MTBBarcodeScanner"`

If you'd prefer not to use CocoaPods, you can download [these two files](https://github.com/mikebuss/MTBBarcodeScanner/tree/master/Classes/ios/Scanners) and add them to your project:

[`MTBBarcodeScanner.h`](https://github.com/mikebuss/MTBBarcodeScanner/blob/master/Classes/ios/Scanners/MTBBarcodeScanner.h)

[`MTBBarcodeScanner.m`](https://github.com/mikebuss/MTBBarcodeScanner/blob/master/Classes/ios/Scanners/MTBBarcodeScanner.m)

## Usage

To import the library: `#import "MTBBarcodeScanner.h"`

#### Initialization

To initialize an instance of `MTBBarcodeScanner`:

```objective-c
scanner = [[MTBBarcodeScanner alloc] initWithPreviewView:self.previewView];
```

Where `previewView` is the `UIView` in which the camera input will be displayed.

If you only want to scan for certain MetaObjectTypes, you can initialize with the `initWithMetadataObjectTypes:previewView:` method:

```objective-c
s = [[MTBBarcodeScanner alloc] initWithMetadataObjectTypes:@[AVMetadataObjectTypeQRCode]
                                               previewView:self.previewView];
```

#### Scanning

To read the first code and stop scanning:

```objective-c
[MTBBarcodeScanner requestCameraPermissionWithSuccess:^(BOOL success) {
    if (success) {
        
        [self.scanner startScanningWithResultBlock:^(NSArray *codes) {
            AVMetadataMachineReadableCodeObject *code = [codes firstObject];
            NSLog(@"Found code: %@", code.stringValue);
            
            [self.scanner stopScanning];
        }];
        
    } else {
        // The user denied access to the camera
    }
}];
```

If the camera is pointed at more than one 2-dimensional code, you can read all of them:

```objective-c
[self.scanner startScanningWithResultBlock:^(NSArray *codes) {
    for (AVMetadataMachineReadableCodeObject *code in codes) {
        NSLog(@"Found code: %@", code.stringValue);
    }
    [self.scanner stopScanning];
}];
```

**Note:** This only applies to 2-dimensional barcodes as 1-dimensional barcodes can only be read one at a time. See [relevant Apple document](https://developer.apple.com/library/ios/technotes/tn2325/_index.html).

To continuously read and only output unique codes: 

```objective-c
[self.scanner startScanningWithResultBlock:^(NSArray *codes) {
    for (AVMetadataMachineReadableCodeObject *code in codes) {
        if ([self.uniqueCodes indexOfObject:code.stringValue] == NSNotFound) {
            [self.uniqueCodes addObject:code.stringValue];
            NSLog(@"Found unique code: %@", code.stringValue);
        }
    }
}];
```

#### Callback Blocks

An alternative way to setup MTBBarcodeScanner is to configure the blocks directly, like so:

```objective-c
self.scanner.didStartScanningBlock = ^{
    NSLog(@"The scanner started scanning! We can now hide any activity spinners.");
};

self.scanner.resultBlock = ^(NSArray *codes){
    NSLog(@"Found these codes: %@", codes);
};

[self.scanner startScanning];
```

This is useful if you would like to present a spinner while MTBBarcodeScanner is initializing.

If you would like to reference `self` in one of these blocks, remember to use a weak reference to avoid a retain cycle:

```objective-c
__weak MyViewController *weakSelf = self;
self.scanner.resultBlock = ^(NSArray *codes){
    [weakSelf drawOverlaysOnCodes:codes];
};
```

---

## Switching Cameras

Switch to the opposite camera with the `flipCamera` method on the scanner:

```objective-c

- (IBAction)switchCameraTapped:(id)sender {
    [self.scanner flipCamera];
}

```


Or specify the camera directly using the `camera` property, like so:

```objective-c

MTBBarcodeScanner *scanner = [[MTBBarcodeScanner alloc] initWithPreviewView:_previewView];
scanner.camera = MTBCameraFront;

```

Examples for these are in the demo project. 

---

## Freezing Capture

Under some circumstances you may want to freeze the video feed when capturing barcodes. To do this, call the `freezeCapture` and `unfreezeCapture` methods.

---

## Limiting the Scan Zone

To limit the section of the screen that barcodes can be scanned in, set the `scanRect` property on MTBBarcodeScanner. See `MTBAdvancedExampleViewController` for an example of this.

---

## Controlling the Torch

To control the torch, set the `torchMode` property or call the `toggleTorch` method.

Available values include:

```objective-c
MTBTorchModeOff,
MTBTorchModeOn,
MTBTorchModeAuto
```

---

## Capturing Still Images

To capture a still image, call the `captureStillImage:` method after you've started scanning.

---

## Design Considerations

The primary goals of this library are to:

- Provide an easy-to-use interface for barcode scanning
- Make as few assumptions about the scanning process as possible
	- Don't assume the user wants to scan one code at a time
	- Don't assume the camera input view should be a particular size
	- Don't assume the scanning process will have it's own view controller

## Developer

Mike Buss
- [Website](http://mikebuss.com)
- [GitHub](https://github.com/mikebuss)
- [Twitter](https://twitter.com/michaeltbuss)
- [Email](mailto:mike@mikebuss.com)

## License

MTBBarcodeScanner is available under the MIT license. See the LICENSE file for more info.

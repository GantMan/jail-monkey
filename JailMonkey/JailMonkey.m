//
//  JailMonkey.m
//  Trackops
//
//  Created by Gant Laborde on 7/19/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "JailMonkey.h"
#import "RCTLog.h"
@import UIKit;

@implementation JailMonkey

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (BOOL)canViolateSandbox{
	NSError *error;
	NSString *stringToBeWritten = @"This is an anti-spoofing test.";
	[stringToBeWritten writeToFile:@"/private/jailbreak.txt" atomically:YES
						  encoding:NSUTF8StringEncoding error:&error];
	if(error==nil){
		//Device is jailbroken
		return YES;
	} else {
		//Device seems clean
		[[NSFileManager defaultManager] removeItemAtPath:@"/private/jailbreak.txt" error:nil];
		return NO;
	}
}

- (BOOL)isJailBroken{
	if ([[NSFileManager defaultManager] fileExistsAtPath:@"/Applications/Cydia.app"]){
		return YES;
	}else if([[NSFileManager defaultManager] fileExistsAtPath:@"/Library/MobileSubstrate/MobileSubstrate.dylib"]){
		return YES;
	}else if([[NSFileManager defaultManager] fileExistsAtPath:@"/bin/bash"]){
		return YES;
	}else if([[NSFileManager defaultManager] fileExistsAtPath:@"/usr/sbin/sshd"]){
		return YES;
	}else if([[NSFileManager defaultManager] fileExistsAtPath:@"/etc/apt"]){
		return YES;
	}else if([[NSFileManager defaultManager] fileExistsAtPath:@"/private/var/lib/apt"]){
		return YES;
	}else if(self.canViolateSandbox){
		return YES;
	}else if([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"cydia://package/com.example.package"]]){
		return YES;
	}

	return NO;
}

- (NSDictionary *)constantsToExport
{

	return @{
			 @"isJailBroken": @(self.isJailBroken),
			 @"canMockLocation": @(self.isJailBroken)
			 };
}

@end



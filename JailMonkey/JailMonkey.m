//
//  JailMonkey.m
//  Trackops
//
//  Created by Gant Laborde on 7/19/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "JailMonkey.h"
#include <TargetConditionals.h>
@import UIKit;
@import Darwin.sys.sysctl;

static NSString * const JMJailbreakTextFile = @"/private/jailbreak.txt";
static NSString * const JMisJailBronkenKey = @"isJailBroken";
static NSString * const JMisDebuggedKey = @"isDebugged";
static NSString * const JMCanMockLocationKey = @"canMockLocation";

@implementation JailMonkey

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (NSArray *)pathsToCheck
{
    return @[
             @"/Applications/Cydia.app",
             @"/Library/MobileSubstrate/MobileSubstrate.dylib",
             @"/bin/bash",
             @"/usr/sbin/sshd",
             @"/etc/apt",
             @"/private/var/lib/apt",
             @"/usr/sbin/frida-server",
             @"/usr/bin/cycript",
             @"/usr/local/bin/cycript",
             @"/usr/lib/libcycript.dylib",
             @"/Applications/FakeCarrier.app",
             @"/Applications/Icy.app",
             @"/Applications/IntelliScreen.app",
             @"/Applications/MxTube.app",
             @"/Applications/RockApp.app",
             @"/Applications/SBSettings.app",
             @"/Applications/WinterBoard.app",
             @"/Applications/blackra1n.app",
             @"/Library/MobileSubstrate/DynamicLibraries/LiveClock.plist",
             @"/Library/MobileSubstrate/DynamicLibraries/Veency.plist",
             @"/System/Library/LaunchDaemons/com.ikey.bbot.plist",
             @"/System/Library/LaunchDaemons/com.saurik.Cydia.Startup.plist",
             @"/bin/sh",
             @"/etc/ssh/sshd_config",
             @"/private/var/lib/cydia",
             @"/private/var/mobile/Library/SBSettings/Themes",
             @"/private/var/stash",
             @"/private/var/tmp/cydia.log",
             @"/usr/bin/sshd",
             @"/usr/libexec/sftp-server",
             @"/usr/libexec/ssh-keysign",
             @"/var/cache/apt",
             @"/var/lib/apt",
             @"/var/lib/cydia",
             ];
}

- (NSArray *)schemesToCheck
{
    return @[
             @"cydia://package/com.example.package",
             ];
}

- (BOOL)checkPaths
{
    BOOL existsPath = NO;

    for (NSString *path in [self pathsToCheck]) {
        if ([[NSFileManager defaultManager] fileExistsAtPath:path]){
            existsPath = YES;
            break;
        }
    }

    return existsPath;
}

- (BOOL)checkSchemes
{
    BOOL canOpenScheme = NO;

    for (NSString *scheme in [self schemesToCheck]) {
        if([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:scheme]]){
            canOpenScheme = YES;
            break;
        }
    }

    return canOpenScheme;
}

- (BOOL)canViolateSandbox{
	NSError *error;
    BOOL grantsToWrite = NO;
	NSString *stringToBeWritten = @"This is an anti-spoofing test.";
	[stringToBeWritten writeToFile:JMJailbreakTextFile atomically:YES
						  encoding:NSUTF8StringEncoding error:&error];
	if(!error){
		//Device is jailbroken
		grantsToWrite = YES;
	}

    [[NSFileManager defaultManager] removeItemAtPath:JMJailbreakTextFile error:nil];

    return grantsToWrite;
}

- (BOOL)isDebugged{
    struct kinfo_proc info;
    size_t info_size = sizeof(info);
    int name[4];

    name[0] = CTL_KERN;
    name[1] = KERN_PROC;
    name[2] = KERN_PROC_PID;
    name[3] = getpid();

    if (sysctl(name, 4, &info, &info_size, NULL, 0) == -1) {
        NSLog(@"sysctl() failed: %s", strerror(errno));
        return false;
    }

    if ((info.kp_proc.p_flag & P_TRACED) != 0) {
        return true;
	}

    return false;
}

- (BOOL)isJailBroken{
    #if TARGET_OS_SIMULATOR
      return NO;
    #endif
    return [self checkPaths] || [self checkSchemes] || [self canViolateSandbox];
}

- (NSDictionary *)constantsToExport
{
	return @{
			 JMisJailBronkenKey: @(self.isJailBroken),
			 JMisDebuggedKey: @(self.isDebugged),
			 JMCanMockLocationKey: @(self.isJailBroken)
			 };
}

@end

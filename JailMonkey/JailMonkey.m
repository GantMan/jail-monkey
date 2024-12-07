//
//  JailMonkey.m
//  Trackops
//
//  Created by Gant Laborde on 7/19/16.
//  Copyright © 2016 Facebook. All rights reserved.
//
#import <mach-o/dyld.h>
#import "JailMonkey.h"
#include <TargetConditionals.h>
@import UIKit;
@import Darwin.sys.sysctl;

static NSString * const JMJailbreakTextFile = @"/private/jailbreak.txt";
static NSString * const JMisJailBronkenKey = @"isJailBroken";
static NSString * const JMCanMockLocationKey = @"canMockLocation";
static NSString * const JMJailBrokenMessageKey = @"jailBrokenMessage";

@implementation JailMonkey

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (NSArray *)pathsToCheck
{
    return @[
            @"/.bootstrapped_electra",
            @"/.cydia_no_stash",
            @"/.installed_unc0ver",
            @"/Applications/Cydia.app",
            @"/Applications/FakeCarrier.app",
            @"/Applications/Icy.app",
            @"/Applications/IntelliScreen.app",
            @"/Applications/MxTube.app",
            @"/Applications/RockApp.app",
            @"/Applications/SBSettings.app",
            @"/Applications/Sileo.app",
            @"/Applications/Snoop-itConfig.app",
            @"/Applications/WinterBoard.app",
            @"/Applications/blackra1n.app",
            @"/Library/MobileSubstrate/CydiaSubstrate.dylib",
            @"/Library/MobileSubstrate/DynamicLibraries/LiveClock.plist",
            @"/Library/MobileSubstrate/DynamicLibraries/Veency.plist",
            @"/Library/MobileSubstrate/MobileSubstrate.dylib",
            @"/Library/PreferenceBundles/ABypassPrefs.bundle",
            @"/Library/PreferenceBundles/FlyJBPrefs.bundle",
            @"/Library/PreferenceBundles/LibertyPref.bundle",
            @"/Library/PreferenceBundles/ShadowPreferences.bundle",
            @"/System/Library/LaunchDaemons/com.ikey.bbot.plist",
            @"/System/Library/LaunchDaemons/com.saurik.Cydia.Startup.plist",
            @"/bin/bash",
            @"/bin/sh",
            @"/etc/apt",
            @"/etc/apt/sources.list.d/electra.list",
            @"/etc/apt/sources.list.d/sileo.sources",
            @"/etc/apt/undecimus/undecimus.list",
            @"/etc/ssh/sshd_config",
            @"/jb/amfid_payload.dylib",
            @"/jb/jailbreakd.plist",
            @"/jb/libjailbreak.dylib",
            @"/jb/lzma",
            @"/jb/offsets.plist",
            @"/private/etc/apt",
            @"/private/etc/dpkg/origins/debian",
            @"/private/etc/ssh/sshd_config",
            @"/private/var/Users/",
            @"/private/var/cache/apt/",
            @"/private/var/lib/apt",
            @"/private/var/lib/cydia",
            @"/private/var/log/syslog",
            @"/private/var/mobile/Library/SBSettings/Themes",
            @"/private/var/stash",
            @"/private/var/tmp/cydia.log",
	        @"/var/tmp/cydia.log",
            @"/usr/bin/cycript",
            @"/usr/bin/sshd",
            @"/usr/lib/libcycript.dylib",
            @"/usr/lib/libhooker.dylib",
            @"/usr/lib/libjailbreak.dylib",
            @"/usr/lib/libsubstitute.dylib",
            @"/usr/lib/substrate",
            @"/usr/lib/TweakInject",
            @"/usr/libexec/cydia",
            @"/usr/libexec/cydia/firmware.sh",
            @"/usr/libexec/sftp-server",
            @"/usr/libexec/ssh-keysign",
            @"/usr/local/bin/cycript",
            @"/usr/sbin/frida-server",
            @"/usr/sbin/sshd",
            @"/usr/share/jailbreak/injectme.plist",
            @"/var/binpack",
            @"/var/cache/apt",
            @"/var/checkra1n.dmg",
            @"/var/lib/apt",
            @"/var/lib/cydia",
            @"/var/lib/dpkg/info/mobilesubstrate.md5sums",
            @"/var/log/apt",
            @"/var/mobile/Library/Preferences/me.jjolano.shadow.plist"
            @"/var/mobile/Library/Preferences/ABPattern"
            ];
}

- (NSArray *)schemesToCheck
{
    return @[
            @"activator://package/com.example.package",
            @"cydia://package/com.example.package",
            @"filza://package/com.example.package",
            @"sileo://package/com.example.package",
            @"undecimus://package/com.example.package",
            @"zbra://package/com.example.package"
            ];
}

- (NSArray *)symlinksToCheck
{
    return @[
            @"/var/lib/undecimus/apt",
            @"/Applications",
            @"/Library/Ringtones",
            @"/Library/Wallpaper",
            @"/usr/arm-apple-darwin9",
            @"/usr/include",
            @"/usr/libexec",
            @"/usr/share"
            ];
}

- (NSArray *)dylibsToCheck
{
    return @[
            @"...!@#",
            @"/.file",
            @"/usr/lib/Cephei.framework/Cephei",
            @"0Shadow.dylib",
            @"ABypass",
            @"AppSyncUnified-FrontBoard.dylib",
            @"Cephei",
            @"CustomWidgetIcons",
            @"CydiaSubstrate",
            @"Electra",
            @"FlyJB",
            @"FridaGadget",
            @"MobileSubstrate.dylib",
            @"PreferenceLoader",
            @"RocketBootstrap",
            @"Shadow",
            @"SSLKillSwitch.dylib",
            @"SSLKillSwitch2.dylib",
            @"Substitute",
            @"SubstrateBootstrap",
            @"SubstrateInserter",
            @"SubstrateInserter.dylib",
            @"SubstrateLoader.dylib",
            @"TweakInject.dylib",
            @"WeeLoader",
            @"cyinject",
            @"cynject",
            @"frida",
            @"systemhook.dylib", // Dopamine - hide jailbreak detection https://github.com/opa334/Dopamine/blob/dc1a1a3486bb5d74b8f2ea6ada782acdc2f34d0a/Application/Dopamine/Jailbreak/DOEnvironmentManager.m#L498
            @"libcycript",
            @"libhooker",
            @"libsparkapplist.dylib",
            @"zzzzLiberty.dylib",
            @"zzzzzzUnSub.dylib"
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

- (BOOL)checkDylibs
{
    NSString *imagePath;

    for (int i=0; i < _dyld_image_count(); i++) {
        imagePath = [NSString stringWithUTF8String:_dyld_get_image_name(i)];

        for (NSString *dylibPath in [self dylibsToCheck]) {
            if([imagePath localizedCaseInsensitiveContainsString:dylibPath]) {
                return YES;
            }
        }
    }

    return NO;
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

- (BOOL)canFork
{
    int pid = fork();
    if(!pid) {
        exit(1);
    }
    if(pid >= 0) {
        return YES;
    }

    return NO;
}

- (BOOL)checkSymlinks
{
    for (NSString *symlink in [self symlinksToCheck]) {
        NSString* result = [[NSFileManager defaultManager] destinationOfSymbolicLinkAtPath:symlink error:nil];
        if([result length] > 0) {
            return YES;
        }
    }

    return NO;
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

RCT_EXPORT_METHOD(isDebuggedMode:(RCTPromiseResolveBlock) resolve
    rejecter:(RCTPromiseRejectBlock) __unused reject) {
    BOOL *isDebuggedModeActived = [self isDebugged];
    resolve(isDebuggedModeActived ? @YES : @NO);
}

- (BOOL)isJailBroken{
    #if TARGET_OS_SIMULATOR
      return NO;
    #endif
    BOOL isiOSAppOnMac = false;

    #if defined(__IPHONE_14_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= 140000
        if (@available(iOS 14.0, *)) {
            // Early iOS 14 betas do not include isiOSAppOnMac
            isiOSAppOnMac = (
                [[NSProcessInfo processInfo] respondsToSelector:@selector(isiOSAppOnMac)] &&
                [NSProcessInfo processInfo].isiOSAppOnMac
            );
        }
    #endif // defined(__IPHONE_14_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= 140000

    if (isiOSAppOnMac) {
        return false;
    }
    return [self checkPaths] || [self checkSchemes] || [self canViolateSandbox] || [self canFork] || [self checkSymlinks] || [self checkDylibs];
}

- (BOOL)canMockLocation{
    #if TARGET_OS_SIMULATOR
      return YES;
    #endif

    return [self isJailBroken];
}

-(NSString *)jailBrokenMessage{
    NSString *errorMessage = @"";

    if([self isJailBroken])
    {
        if ([self checkPaths]) {
        errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"checkPaths"];
            errorMessage =[NSString stringWithFormat:@"%@,[%@]", errorMessage, [self checkPathsMessage]];
        }
        if ([self checkSchemes]) {
        errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"checkSchemes"];
            errorMessage =[NSString stringWithFormat:@"%@,[%@]", errorMessage, [self checkSchemesMessage]];
        }
        if ([self canViolateSandbox]) {
        errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"canViolateSandbox"];
        }
        if ([self canFork]) {
        errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"canFork"];
        }
        if ([self checkSymlinks]) {
            errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"checkSymlinks"];
            errorMessage =[NSString stringWithFormat:@"%@,[%@]", errorMessage, [self checkSymlinksMessage]];
        }
        if ([self checkDylibs]) {
            errorMessage = [NSString stringWithFormat:@"%@,#%@", errorMessage, @"checkDylibs"];
            errorMessage =[NSString stringWithFormat:@"%@,[%@]", errorMessage, [self checkDylibsMessage]];
        }
    }
   return errorMessage;
}

- (NSString *)checkSchemesMessage
{
    NSString *schemeMessage = @"";
    for (NSString *scheme in [self schemesToCheck]) {
        if([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:scheme]]){
            schemeMessage = [NSString stringWithFormat:@"%@,%@", schemeMessage, scheme];
            break;
        }
    }
    return schemeMessage;
}

- (NSString *)checkPathsMessage
{
    NSString *existsPath = @"";
    for (NSString *path in [self pathsToCheck]) {
        if ([[NSFileManager defaultManager] fileExistsAtPath:path]){
            existsPath = [NSString stringWithFormat:@"%@,%@", existsPath, path];
            break;
        }
    }
    return existsPath;
}


- (NSString *)checkSymlinksMessage
{
    NSString *symLinkMessage = @"";
    for (NSString *symlink in [self symlinksToCheck]) {
        NSString* result = [[NSFileManager defaultManager] destinationOfSymbolicLinkAtPath:symlink error:nil];
        if([result length] > 0) {
            symLinkMessage = [NSString stringWithFormat:@"%@,%@", symLinkMessage, symlink];
        }
    }
    return symLinkMessage;
}

- (NSString *)checkDylibsMessage
{
    NSString *imagePath = @"";

    for (int i=0; i < _dyld_image_count(); i++) {
        imagePath = [NSString stringWithUTF8String:_dyld_get_image_name(i)];

        for (NSString *dylibPath in [self dylibsToCheck]) {
            if([imagePath localizedCaseInsensitiveContainsString:dylibPath]) {
                imagePath = [NSString stringWithFormat:@"%@,%@", imagePath, dylibPath];
            }
        }
    }
    return imagePath;
}

- (NSDictionary *)constantsToExport
{
	return @{
		JMisJailBronkenKey: @(self.isJailBroken),
		JMCanMockLocationKey: @(self.canMockLocation),
        JMJailBrokenMessageKey : [self jailBrokenMessage]
	};
}

@end

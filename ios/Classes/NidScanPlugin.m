#import "NidScanPlugin.h"
#if __has_include(<nid_scan/nid_scan-Swift.h>)
#import <nid_scan/nid_scan-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "nid_scan-Swift.h"
#endif

@implementation NidScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNidScanPlugin registerWithRegistrar:registrar];
}
@end

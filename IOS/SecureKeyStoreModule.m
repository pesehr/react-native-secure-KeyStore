//
//  SecureKeyStore.m
//  SecureKeyStore
//
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>


@interface RCT_EXTERN_MODULE(SecureKeyStore, NSObject)

RCT_EXTERN_METHOD(generateKeys: (RCTResponseSenderBlock *)errorCallback)

RCT_EXTERN_METHOD(getPublicCerts:(RCTResponseSenderBlock *)successCallback callback:(RCTResponseSenderBlock *)errorCallback)

RCT_EXTERN_METHOD(getMD5:(RCTResponseSenderBlock *)successCallback)

RCT_EXTERN_METHOD(signData:(NSString *)data callback:(RCTResponseSenderBlock *)errorCallback ecallback:(RCTResponseSenderBlock *)successCallback)


@end

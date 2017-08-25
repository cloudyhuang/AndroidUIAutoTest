# iOS SDK 使用文档 #

## 依赖库 ##

* AdSupport.framework - 启用 Apple ADID 支持
* CoreTelephony.framework - 获取运营商
* Security.framework - 加密支持
* SystemConfiguration.framework - 获取联网方式(wifi, cellular)
* libsqlite3.dylib - sqlite 支持
* libz.dylib - gzip 压缩支持

("TARGETS - Build Phases - Link Binary With Libraries" 中添加)

## 启用 API ##

1. 将下载包里面 DATracker.h, libMobilytics.a 文件添加到 App 项目中

2. 在 `*AppDelegate.m` `application:didFinishLaunchingWithOptions` 方法中调用如下方法，参数依次为 app key，版本和来源渠道。

`
[[DATracker sharedTracker] startTrackerWithAppKey:@"app-key" appVersion:@"0.1" appChannel:@"AppStore"];
`

## 事件捕捉 ##

调用如下方法进行事件捕捉，eventId 为事件标识，如 "login", "buy"

`
[[DATracker sharedTracker] trackEvent:@"login"];
`

如还需要对事件发生时的其他信息进行捕捉，调用如下方法，
attributes 为自定义字段名称，格式如 "{@"user":@"Lee", @"timestamp":@"1357872572"}"

`
[[DATracker sharedTracker] trackEvent:@"login"
	withAttributes:[NSDictionary dictionaryWithObjectsAndKeys:@"lee", @"user", nil]];
`

**注意：虽然可以使用在任何地方，但最好不要在较多循环内或者非主线程中调用**

## 异常捕捉 ##

可以在 try catch block 中进行异常捕捉，传入参数为 NSException (含子类)实例

`
@try {
    [@"str" characterAtIndex:10];
}
@catch (NSException *exception) {
    [[DATracker sharedTracker] trackException:exception];
}
`

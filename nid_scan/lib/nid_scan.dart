
import 'dart:async';

import 'package:flutter/services.dart';

class NidScan {
  static const MethodChannel _channel =
      const MethodChannel('nid_scan');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}

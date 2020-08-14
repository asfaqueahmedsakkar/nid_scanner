import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:nid_scan/nid_scan.dart';

void main() {
  const MethodChannel channel = MethodChannel('nid_scan');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await NidScan.platformVersion, '42');
  });
}

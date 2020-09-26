import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';

import 'screens/welcome_page.dart';

void main() => runApp(DRS());

void forcePortraitMode() {
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
    DeviceOrientation.portraitDown,
  ]);
}

class DRS extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    forcePortraitMode();

    return FlutterEasyLoading(
      child: MaterialApp(
        title: 'Digital Restaurant System',
        theme: ThemeData.dark(),
        home: WelcomeMenu(),
      ),
    );
  }
}
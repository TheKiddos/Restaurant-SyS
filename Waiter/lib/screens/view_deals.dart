import 'package:Waiter/services/auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

class ViewDeals extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //AppBar section
        appBar: AppBar(
          backgroundColor: Colors.black12,
          centerTitle: true,
          title: Text(
            'Digital Restaurant System™',
            style: TextStyle(fontSize: 22.0),
          ),
        ),
        backgroundColor: Colors.white12,
        //Body section
        body: WebView( initialUrl: website + 'home/main/deals/',)
    );
  }
}
import 'dart:convert';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

import 'item.dart';
import 'type.dart';


String _website = 'http://192.168.1.101:8000/';
String _username = 'waiter';
String _password = 'code594-B'; // TODO save in a file
String _basicAuth =
    'Basic ' + base64Encode(utf8.encode('$_username:$_password'));

var _headers = {HttpHeaders.authorizationHeader: _basicAuth};

Future<bool> tableExists( int tableId ) async {
  try {
    var response = await http.get( 'http://192.168.1.101:8080/api/tables/$tableId', headers: _headers ).timeout( Duration( seconds: 3 ) );

    if ( response.statusCode == HttpStatus.ok )
      return true;

    return false;
  }
  catch ( e ) {
    return false;
  }
}

Future<List<Item>> fetchItems() async {
  try {
    List<Item> items = [];
    var response = await http.get( 'http://192.168.1.101:8080/api/items/', headers: _headers ).timeout( Duration( seconds: 3 ) );

    if ( response.statusCode != HttpStatus.ok )
      return items;

    List<dynamic> responseItems = jsonDecode(response.body)['items'];
    responseItems.forEach( (item) {
      items.add( Item( item[ 'id' ],
        item[ 'name' ],
        item[ 'price' ],
        item[ 'description' ],
        getItemImage( item[ 'imagePath' ] ),
        item[ 'calories' ],
        item[ 'carbohydrates' ],
        item[ 'fat' ],
        item[ 'protein' ],
        getItemTypes( item[ 'types' ] ),) );
    });

    return items;
  }
  catch ( e ) {
    return [];
  }
}

Image getItemImage( item ) {
  String imagePath = item.toString();
  int lastSlash = imagePath.lastIndexOf( '/' );
  if ( lastSlash == -1 )
    return null;

  int imageNameIndex = lastSlash + 1;
  String imageName = imagePath.substring( imageNameIndex );

  String networkImagePath = _website + imageName;
  return Image.network( networkImagePath );
}

List<Type> getItemTypes( List<dynamic> itemTypes ) {
  List<Type> types = [];
  
  itemTypes.forEach( (type) {
    types.add( Type( type[ 'name' ] ) );
  });

  return types;
}

Future<List<Type>> fetchTypes() async {
  try {
    List<Type> types = [];
    var response = await http.get( 'http://192.168.1.101:8080/api/types/', headers: _headers ).timeout( Duration( seconds: 3 ) );

    if ( response.statusCode != HttpStatus.ok )
      return types;

    List<dynamic> responseTypes = jsonDecode(response.body)['types'];
    responseTypes.forEach( (type) {
      types.add( Type( type['name'] ) );
    });
    return types;
  }
  catch ( e ) {
    return [];
  }
}

/*
  void setItems() async {
    List<DropdownMenuItem<int>> newItems;
    newItems = await updateActiveTables().then( (onValue) => newItems = onValue );
    setState(() {
      items = newItems;
    });
  }

  Future<List<DropdownMenuItem<int>>> updateActiveTables() async {
    String username = 'waiter';
    String password = 'code594-B'; // TODO save in a file
    String basicAuth =
        'Basic ' + base64Encode(utf8.encode('$username:$password'));
    var response = await http.get('http://192.168.1.101:8080/api/tables', headers: {HttpHeaders.authorizationHeader: basicAuth});

    List<dynamic> tables = jsonDecode(response.body)['tables'];
    List<DropdownMenuItem<int>> activeTables = [];

    tables.forEach( (table) {
      activeTables.add(DropdownMenuItem(
        child: Text(table['id']?.toString()),
        value: table['id'],
      ));
    });
    return activeTables;
  }
 */
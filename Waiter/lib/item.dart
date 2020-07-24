import 'dart:ui';

import 'package:flutter/cupertino.dart';

import 'type.dart';

class Item {
  int _id;
  String _name;
  String _description;
  Image _image;
  double _price;
  double _calories;
  double _fat;
  double _protein;
  double _carbohydrates;

  List<Type> _types;

  Item(this._id, this._name, this._price, this._description,
      this._image, this._calories, this._carbohydrates,
      this._fat, this._protein, this._types );

  int get id => _id;

  String get name => _name;

  String get description => _description;

  Image get image => _image;

  double get price => _price;

  double get calories => _calories;

  double get fat => _fat;

  double get protein => _protein;

  double get carbohydrates => _carbohydrates;

  List<Type> get types {
    List<Type> typesClone = [];
    typesClone.addAll( types );
    return typesClone;
  }
}
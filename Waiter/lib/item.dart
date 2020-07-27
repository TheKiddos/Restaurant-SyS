import 'type.dart';

class Item {
  int _id;
  String _name;
  String _description;
  String _imagePath;
  double _price;
  double _calories;
  double _fat;
  double _protein;
  double _carbohydrates;

  List<Type> _types;

  Item(this._id, this._name, this._price, this._description,
      this._imagePath, this._calories, this._carbohydrates,
      this._fat, this._protein, this._types );

  int get id => _id;

  String get name => _name;

  String get description => _description;

  String get imagePath => _imagePath;

  double get price => _price;

  double get calories => _calories;

  double get fat => _fat;

  double get protein => _protein;

  double get carbohydrates => _carbohydrates;

  List<Type> get types {
    List<Type> typesClone = [];
    typesClone.addAll( _types );
    return typesClone;
  }
}
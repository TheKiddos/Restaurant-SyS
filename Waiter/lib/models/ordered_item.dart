import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/quantity.dart';

class OrderedItem {
  final Item _item;
  final Quantity _quantity;

  OrderedItem(this._item, this._quantity);

  Quantity get quantity => _quantity;

  Item get item => _item;
}
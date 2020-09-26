import 'package:Waiter/models/item.dart';

class OrderedItemsList {
  int tableId;
  List<Item> items;


  OrderedItemsList(this.tableId, this.items);

  Map<String, dynamic> toJson() =>
      {
        "table": createTableJson(),
        "items": items
      };

  createTableJson() {
    Map<String, dynamic> table =
        {
          "id": tableId,
          "maxCapacity": 4,
          "fee": 0
        };

    return table;
  }
}
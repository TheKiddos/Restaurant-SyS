import 'item.dart';

class OrderedItemsList {
  int tableId;
  List<Item> items;


  OrderedItemsList(this.tableId, this.items);

  Map<String, dynamic> toJson() =>
      {
        "table": createTableJson(),
        "itemList": createItemList()
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

  createItemList() {
    Map<String, dynamic> itemList =
    {
      "items": items
    };

    return itemList;
  }
}
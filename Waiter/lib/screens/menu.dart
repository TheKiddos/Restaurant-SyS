import 'dart:collection';

import 'package:Waiter/const.dart';
import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/ordered_item.dart';
import 'package:Waiter/models/type.dart';
import 'package:Waiter/screens/view_order.dart';
import 'package:Waiter/widgets/item_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

double sizeWidth;
Map<int, ItemWidget> _itemWidgetsMap = HashMap<int, ItemWidget>();

void fillItemWidgets(List<Item> items) {
  items.forEach((item) {
    _itemWidgetsMap[item.id] = ItemWidget( item: item, sizeBox: sizeWidth,);
  });
}

class Menu extends StatefulWidget {
  final int _tableId;
  final List<Item> _items;
  final List<Type> _types;

  Menu( this._tableId, this._items, this._types );

  @override
  _MenuState createState() => _MenuState();
}

class _MenuState extends State<Menu> {
  Widget build(BuildContext context) {
    sizeWidth = MediaQuery.of(context).size.width;
    fillItemWidgets( widget._items );

    var viewOrderButton = RawMaterialButton(
      padding: EdgeInsets.all(10),
      child: Text("View Order", style: etextstyle,),
      shape: RoundedRectangleBorder(),
      onPressed: () {
        Navigator.push(
            context,
            MaterialPageRoute(builder:(context) =>  ViewOrder(
              widget._tableId, convertToOrderedItems( _itemWidgetsMap.values.toList() ),
            ),)
        );
        },
    );

    return DefaultTabController(
        length: widget._types.length + 1, // + 1 for All tab
        child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.black,
              centerTitle: true,
              title: Text('Menu', style: ktextstyle,),
              actions: <Widget>[
                viewOrderButton
              ],
              bottom: TabBar(
                isScrollable: true,
                labelColor: Colors.white,
                labelPadding: EdgeInsets.fromLTRB(10, 0, 0, 0),
                tabs: buildTypesTabs(),
              ),
            ),
            backgroundColor: Colors.white12,
            body: TabBarView(
              children: fillTabsWithItems(),
            )
        ),
      );
  }

  List<Widget> buildTypesTabs() {
    List<Widget> typesTabs = [ Tab( text: 'All' ) ];

    widget._types.forEach( (type) {
      typesTabs.add( Tab( text: type.name ) );
    });

    return typesTabs;
  }

  List<Widget> fillTabsWithItems() {
    List<Widget> tabItems = [
      ListView(
        children: _itemWidgetsMap.values.toList(), // All items in the All tab
      )
    ];

    widget._types.forEach((type) {
      tabItems.add( ListView(
        children: getItemWidgetsForType( type, widget._items ),
      ) );
    });
    // ListView, ItemWidget

    return tabItems;
  }

  List<ItemWidget> getItemWidgetsForType(Type type, List<Item> items) {
    List<Item> itemsOfType = filterItemsByType( type, items );

    List<ItemWidget> itemWidgetsOfType = [];
    itemsOfType.forEach((item) {
      itemWidgetsOfType.add( _itemWidgetsMap[ item.id ] );
    });

    return itemWidgetsOfType;
  }

  List<Item> filterItemsByType(Type type, List<Item> items) {
    List<Item> itemsOfType = [];

    items.forEach((item) {
      if ( item.types.contains( type ) )
        itemsOfType.add( item );
    });

    return itemsOfType;
  }

  List<OrderedItem> convertToOrderedItems( List<ItemWidget> itemWidgets ) {
    List<OrderedItem> orderedItems = [];

    itemWidgets.forEach( (itemWidget) {
      if ( itemWidget.quantity.get() > 0 )
        orderedItems.add( OrderedItem( itemWidget.item, itemWidget.quantity ) );
    });

    return orderedItems;
  }
}
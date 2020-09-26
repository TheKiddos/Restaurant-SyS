import 'package:Waiter/const.dart';
import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/ordered_item.dart';
import 'package:Waiter/services/manager_api.dart';
import 'package:Waiter/widgets//ordered_item_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';

class ViewOrder extends StatelessWidget {
  final int _tableId;
  final List<OrderedItem> _orderedItems;

  ViewOrder( this._tableId, this._orderedItems );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text( "Your Order" ),
      ),
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(20.0),
              child: Text( "Order for table: " + _tableId.toString() ),
            ),
            Expanded(
              child: ListView (
                  children: getOrderedItems(),
              ),
            ),
            RaisedButton(
              materialTapTargetSize: MaterialTapTargetSize.padded,
              //button color
              color: Colors.white12,
              //The highlight color of the button's
              highlightColor: Colors.grey,
              //The color to use for this button's text.
              textColor: Colors.white,
              //We have a problem here
              padding: EdgeInsets.all(30),
              //The splash color of the button's
              splashColor: Colors.deepPurple,
              onPressed: () {
                sendOrder( _tableId, _orderedItems );
              },
              child: Text(
                "Send Order",
                style: ktextstyle,
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<OrderedItemWidget> getOrderedItems() {
    List<OrderedItemWidget> orderedItemsWidget = [];

    _orderedItems.forEach((orderedItem) {
      orderedItemsWidget.add( OrderedItemWidget( item: orderedItem.item, quantity: orderedItem.quantity,) );
    });
    return orderedItemsWidget;
  }

  static void sendOrder( int tableId, List<OrderedItem> orderedItems ) async {
    EasyLoading.show( status: "Sending to manager..." );
    List<Item> items = [];
    orderedItems.forEach((orderedItem) {
      for ( int i = 0; i < orderedItem.quantity.get(); ++i )
        items.add( orderedItem.item );
    });

    bool requestSuccess = await postOrder( tableId, items );
    requestSuccess ? EasyLoading.showSuccess( "Order sent successfully!" ) : EasyLoading.showError( "Something went wrong. Please check the manager." );
  }
}

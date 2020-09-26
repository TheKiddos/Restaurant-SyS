import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/quantity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class OrderedItemWidget extends StatelessWidget {
  final Item item;
  final Quantity quantity;

  OrderedItemWidget( {@required this.item, @required this.quantity,} );

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white10,
      height: 50,
      margin:EdgeInsets.fromLTRB(0, 10, 0, 10),
      child: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          Container(
            padding: EdgeInsets.only(top: 20),
            child: Text( item.name ),
          ),
          Container(
            padding: EdgeInsets.only(top: 20),
            child: Text( "Unit Price: " + item.price.toString() ),
          ),
          Container(
            padding: EdgeInsets.only(top: 20),
            child: Text( "Quantity: " + quantity.toString() ),
          )
        ],
      ),
    );
  }
}
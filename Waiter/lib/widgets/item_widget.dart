import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/quantity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Button extends StatelessWidget {
  const Button({@required this.icon, @required  this.onPressed}) ;
  final IconData icon;
  final Function onPressed;
  @override
  Widget build(BuildContext context) {
    return RawMaterialButton(
      elevation: 0.0,
      child: Icon(icon),
      onPressed: onPressed,
      constraints:BoxConstraints.tightFor(
          width: 56,
          height: 56
      ),
      shape: CircleBorder(),
      fillColor: Colors.white54,
    );
  }
}

class ItemWidget extends StatefulWidget {
  final double sizeBox;
  final Item item;
  final Quantity quantity = Quantity();

  ItemWidget( {@required this.item, @required this.sizeBox,} );

  @override
  _ItemWidgetState createState() => _ItemWidgetState();
}

class _ItemWidgetState extends State<ItemWidget> {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white10,
      height: 150,
      margin:EdgeInsets.fromLTRB(0, 10, 0, 10),
      width: widget.sizeBox,
      child: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          Image.network( widget.item.imagePath, width: widget.sizeBox / 4 , fit: BoxFit.fill,),
          getItemDataWidget(),
          getItemControlsWidget(),
        ],
      ),
    );
  }

  Widget getItemDataWidget() {
    return Container(
      margin: EdgeInsets.fromLTRB(2, 0, 10, 0),
      padding: EdgeInsets.fromLTRB(2, 0, 20, 0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text( widget.item.name ),
          Container(
            padding: EdgeInsets.only(top: 20),
            child: Text( "Price: " + widget.item.price.toString() ),
          )
        ],
      ),
    );
  }

  Widget getItemControlsWidget() {
    var removeItemButton = Button(
      icon: Icons.remove,
      onPressed: (){
        setState(() {
            widget.quantity.removeOne();
        });
      },
    );

    var addItemButton = Button(
      icon: Icons.add,
      onPressed: (){
        setState(() {
          widget.quantity.addOne();
        });
      },
    );

    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text( widget.quantity.toString() ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              addItemButton,
              Padding(
                padding:  EdgeInsets.only(left: 45),
                child: removeItemButton,
              )
            ],
          ),
        ],
      ),
    );
  }
}
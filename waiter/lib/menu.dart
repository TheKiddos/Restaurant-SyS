import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'Item.dart';
import 'Type.dart';
import 'const.dart';
import 'send.dart';

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
    var sizeWidth=MediaQuery.of(context).size.width;
    //  List_photo for  Page view
    List <Widget> lis1=new List<Widget>();
    List <Widget> lis2=new List<Widget>();
    List <Widget> lis4=new List<Widget>();
    List <Widget> lis5=new List<Widget>();

    void f( var img,var liss){
      for(int i=0;i<img.length;i++){
        var imgg=Padding(
          padding: EdgeInsets.fromLTRB(5, 2, 15, 10),
          child: Container(
            child: Stack(
              children: <Widget>[
                ClipRRect(
                    borderRadius: BorderRadius.all(
                        Radius.circular(50.0)),
                    child:
                    Image.asset(
                      img[i],fit:BoxFit.fitWidth,height: 150,
                    )
                ),
              ],
            ),
          ),
        );
        liss.add(imgg);
      }


    }
    f(img1,lis1);
    f(img2,lis2);
    f(img4,lis4);
    f(img5,lis5);
    //////////////////////////////////////////////////////////
    Tab tab(var t) {
      return Tab(
        text: t,
      );
    }
//    PageController controller=PageController(viewportFraction: 0.8,initialPage: 1);
    return
      DefaultTabController(
        length: 4,
        child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.black,
              centerTitle: true,
              title: Text('Menu',style: ktextstyle,),
              actions: <Widget>[
                RawMaterialButton(
                  constraints:BoxConstraints.tightFor(
                    width: 46,
                  ),
                  shape: CircleBorder(),
                  fillColor: Colors.white12,
                  child: Text("Next",style: etextstyle,),
                  onPressed: (){
                    Navigator.push(context,
                        MaterialPageRoute(builder:(context) =>  Send()));
                  },
                )
              ],
              bottom: TabBar(
                labelColor: Colors.white,
                labelPadding: EdgeInsets.fromLTRB(10, 0, 0, 0),
                tabs: <Widget>[
                  tab('Main Meal'),tab('Drinks'),tab('Dessert'),tab('Appetizers')
                ],
              ),
            ),
            drawer: Drawer(
              child: Text('LOL'),
            ),
            backgroundColor: Colors.white12,
            body: TabBarView(
              children: <Widget>[
                //////////////////////////////////////////////
                ListView(
                  children: <Widget>[
                    PageView(lis: lis1),
                    Column(
                      children: <Widget>[
                        Image.asset('images/c.png',
                          fit: BoxFit.fitWidth,
                          width: sizeWidth,
                          height: 200,
                        ),
                        SizedBox(height: 10,),
                        RealMenu(
                          sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/Pizza.png',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('Pizza ',style: etextstyle,),
                          t2: Text('price is 50',style: etextstyle),

                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/Burgers.png',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('Burger',style: etextstyle,),
                          t2: Text('price is 50',style: etextstyle),
                        ),
                        SizedBox(height: 10,),
                        Image.asset('images/back.jpg',
                          fit: BoxFit.fitWidth,
                          width: sizeWidth,
                          height: 200,
                        ),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/Sushi.png',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('Sushi',style: etextstyle),
                          t2: Text('price is 50',style: etextstyle),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/Spaghetti.png',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('Spaghetti',style: etextstyle),
                          t2: Text('price is 50',style: etextstyle),

                        ),
                        Image.asset('images/bb.png',
                          fit: BoxFit.fitWidth,
                          width: sizeWidth,
                          height: 200,
                        ),

                      ],
                    )
                  ],
                ),
                //////////////////////////////////////////////
                ListView(
                  children: <Widget>[
                    PageView(lis: lis2),
                    Column(
                      children: <Widget>[
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/drink1.jpg',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/drink2.png',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/hot_chocolate.png',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth , im:
                        Image.asset(
                          'images/milkshake.jpg',
                          fit: BoxFit.cover,
                          width: 100,
                          height: 150,
                        ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                      ],
                    )
                  ],
                ),
                //////////////////////////////////////////////
                ListView(
                  children: <Widget>[
                    PageView(lis: lis4),
                    Column(
                      children: <Widget>[
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/des1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/pan1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/cheescake.png',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/knaf.png',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                      ],
                    )
                  ],
                ),
                //////////////////////////////////////////////
                ListView(
                  children: <Widget>[
                    PageView(lis: lis1),
                    Column(
                      children: <Widget>[
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/des1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/des1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/des1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                        SizedBox(height: 10,),
                        RealMenu(sizeBox: sizeWidth ,
                          im: Image.asset(
                            'images/des1.jpg',
                            fit: BoxFit.cover,
                            width: 100,
                            height: 150,
                          ),
                          t1: Text('assaassasa'),
                          t2: Text('assaassasa'),
                        ),
                      ],
                    )
                  ],
                ),
                //////////////////////////////////////////////
              ],
            )
        ),
      );
  }
}

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
// ignore: must_be_immutable
class RealMenu extends StatefulWidget {
  RealMenu({@required this.sizeBox,this.im,this.t1,this.t2,}) ;
  final double sizeBox;
  final Widget im;
  final Widget t1;
  final Widget t2;
  @override
  _RealMenuState createState() => _RealMenuState();
}
class _RealMenuState extends State<RealMenu> {
  int quantity = 0;
  @override
  Widget build(BuildContext context) {
    return Cont(
      size: widget.sizeBox,
      single: Single(
          ro: Roro(
            imge: widget.im,
            tex1: widget.t1,
            tex2: widget.t2,
            tex3: Text(quantity.toString(),style: ktextstyle,),
            but1: Button(
              icon: Icons.remove,
              onPressed: (){
                setState(() {
                  if(quantity>0)
                    --quantity;
                });
              },
            ),
            but2: Button(
              icon: Icons.add,
              onPressed: (){
                setState(() {
                  ++this.quantity;
                });
              },
            ),



          )
      ),
    );
  }
}
class Roro extends StatelessWidget {
  const Roro({@required  this.imge ,this.tex1,this.tex2,this.but1,this.but2,this.tex3}) ;
  final imge,tex1,tex2,tex3,but1,but2;


  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.max,
      crossAxisAlignment: CrossAxisAlignment.stretch,
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: <Widget>[
        imge,
        Container(
          margin: EdgeInsets.fromLTRB(2, 0, 10, 0),
          padding: EdgeInsets.fromLTRB(2, 0, 20, 0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              tex1,
              Container(
                padding: EdgeInsets.only(top: 20),
                child: tex2,
              )

            ],
          ),
        ),
        Container(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              tex3,
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  but1,
                  Padding(
                      padding:  EdgeInsets.only(left: 45),
                      child: but2
                  )

                ],
              ),
            ],
          ),
        )




      ],
    );
  }
}
class Single extends StatelessWidget {
  const Single({@required this.ro}) ;
  final Widget ro;
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView
      (
      scrollDirection: Axis.horizontal,
      child: ro,
    );
  }
}
class Cont extends StatelessWidget {
  const Cont({ @required this.size,this.single}) ;

  final double size;
  final Widget single;

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white10,
      width:size ,
      height: 150,
      margin:EdgeInsets.all(10),
      child: single,
    );
  }
}
class PageView extends StatelessWidget {
  const PageView({ @required this.lis,}) ;

  final List<Widget> lis;
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: lis,
      ),
    );
  }
}
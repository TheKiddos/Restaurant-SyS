import 'package:Waiter/view_deals.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';

import 'const.dart';
import 'item.dart';
import 'manager_api.dart';
import 'menu.dart';
import 'type.dart';

class WelcomeMenu extends StatefulWidget {
  @override
  _WelcomeMenuState createState() => _WelcomeMenuState();
}

class _WelcomeMenuState extends State<WelcomeMenu> {
  final _formKey = GlobalKey<FormState>();
  Widget dealsButton;

  Widget build(BuildContext context) {
    dealsButton = RawMaterialButton(
      padding: EdgeInsets.all(10),
      child: Text("View Deals", style: etextstyle,),
      shape: RoundedRectangleBorder(),
      onPressed: () {
        Navigator.push( context, MaterialPageRoute( builder:(context) =>  ViewDeals(), ) );
      },
    );

    return Scaffold(
        //AppBar section
        appBar: AppBar(
          backgroundColor: Colors.black12,
          centerTitle: true,
          title: Text(
            'Digital Restaurant System™',
            style: TextStyle(fontSize: 28.0),
          ),
        ),
        backgroundColor: Colors.white12,
        //Body section
        body: buildMainBody()
    );
  }

  Widget buildMainBody() {
    return SafeArea(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Expanded(
                child: ListView(
                  children: <Widget>[
                    welcomeImage,
                    welcomeMessage,
                  ],
                ),
              ),
              TableIdForm(formKey: _formKey),
              dealsButton
            ],
      ));
  }

  final Image welcomeImage = Image.asset('images/im1.png', height: 300 ,fit: BoxFit.fitWidth,);

  final welcomeMessage = Padding(
    padding: const EdgeInsets.all(20.0),
    child: Text(
      'Welcome to the restaurant'
          ' please put your table number',
      style: ktextstyle,
    ),
  );
}

class TableIdForm extends StatelessWidget {
  const TableIdForm({
    @required GlobalKey<FormState> formKey,
  })  : _formKey = formKey;

  final GlobalKey<FormState> _formKey;

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          //TextFormField
          TableIdField(),
          //Button section
          GoToMenuButton(formKey: _formKey),
        ],
      ),
    );
  }
}

class TableIdField extends StatefulWidget {
  @override
  _TableIdFieldState createState() => _TableIdFieldState();
}

int tableId;
class _TableIdFieldState extends State<TableIdField> {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: 10),
      child: TextFormField(
        showCursor: true,
        cursorColor: Colors.white,
        maxLength: 5,
        keyboardType: TextInputType.number,
        validator: validateAndSetTableId,
        decoration: InputDecoration(
            hintStyle: ktextformstyle,
            hintText: 'Table Number:',
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(50.0),
            )
        ),
        style: ktextstyle,
      ),
    );
  }

  String validateAndSetTableId(value) {
        if ( value.isEmpty )
          return 'Please enter the tableId';

        tableId = int.tryParse( value );
        if ( tableId == null )
          return 'That\'s not a number dumb ass';

        return null;
      }
}

class GoToMenuButton extends StatelessWidget {
  const GoToMenuButton({
    Key key,
    @required GlobalKey<FormState> formKey,
  })  : _formKey = formKey,
        super(key: key);

  final GlobalKey<FormState> _formKey;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: RaisedButton(
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
          goToMenu( context );
        },
        child: Text(
          "Go to menu",
          style: ktextstyle,
        ),
      ),
    );
  }

  goToMenu( BuildContext context ) async {
      EasyLoading.show(status: "Checking table status..." );
      bool requestSuccess = false;
      await tableExists( tableId ).then( (value) => requestSuccess = value );
      if ( !_formKey.currentState.validate() || !requestSuccess ) {
        EasyLoading.showError( "Make sure the table exists and that it's active" );
        return;
      }

      EasyLoading.show(status: "Fetching Menu..." );
      List<Item> items = [];
      List<Type> types = [];
      await fetchItems().then( (value) => items = value );
      await fetchTypes().then( (value) => types = value );
      if ( items.isEmpty ) {
        EasyLoading.showError( "Something went wrong while fetching the items. Please check with the manager" );
        return;
      }

      EasyLoading.dismiss();
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => Menu( tableId, items, types )));
  }
}


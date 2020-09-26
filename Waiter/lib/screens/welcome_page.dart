import 'package:Waiter/const.dart';
import 'package:Waiter/models/item.dart';
import 'package:Waiter/models/type.dart';
import 'package:Waiter/screens/menu.dart';
import 'package:Waiter/screens/view_deals.dart';
import 'package:Waiter/services//manager_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';

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
  TableIdForm({
    @required GlobalKey<FormState> formKey,
  })  : _formKey = formKey;

  final tableIdFieldController = TextEditingController();
  final GlobalKey<FormState> _formKey;


  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          //TextFormField
          TableIdField(tableIdFieldController: tableIdFieldController,),
          //Button section
          GoToMenuButton(formKey: _formKey, tableIdFieldController: tableIdFieldController,),
        ],
      ),
    );
  }
}

class TableIdField extends StatefulWidget {
  final TextEditingController tableIdFieldController;

  const TableIdField({Key key, this.tableIdFieldController}) : super(key: key);

  @override
  _TableIdFieldState createState() => _TableIdFieldState();
}

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
        validator: validateInteger,
        controller: widget.tableIdFieldController,
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

  String validateInteger(value) {
        if ( value.isEmpty )
          return 'Please enter the tableId';

        if ( int.tryParse( value ) == null )
          return 'That\'s not a integer dumb ass';

        return null;
  }
}

class GoToMenuButton extends StatelessWidget {
  final TextEditingController tableIdFieldController;

  const GoToMenuButton({
    Key key,
    @required GlobalKey<FormState> formKey,
    this.tableIdFieldController
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

      if ( !_formKey.currentState.validate() ) {
        EasyLoading.showError( "Please enter a number asshole." );
        return;
      }

      int tableId = int.tryParse( tableIdFieldController.text );
      bool requestSuccess = await tableExists( tableId );
      if ( !requestSuccess ) {
        EasyLoading.showError( "Make sure the table exists and that it's active" );
        return;
      }

      EasyLoading.show(status: "Fetching Menu..." );
      List<Item> items = await fetchItems();
      List<Type> types = await fetchTypes();

      if ( items.isEmpty ) {
        EasyLoading.showError( "Something went wrong while fetching the items. Please check with the manager" );
        return;
      }

      EasyLoading.dismiss();
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => Menu( tableId, items, types )));
  }
}


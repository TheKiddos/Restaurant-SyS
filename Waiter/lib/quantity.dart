class Quantity {
  int _quantity = 0;

  void add() {
    ++_quantity;
  }

  void remove() {
    --_quantity;
  }

  int get() {
    return _quantity;
  }

  @override
  String toString() {
    return _quantity.toString();
  }
}
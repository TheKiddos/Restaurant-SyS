class Quantity {
  int _quantity = 0;

  void addOne() {
    if ( _quantity < 10000 ) // Preventing Overflow
      ++_quantity;
  }

  void removeOne() {
    if ( _quantity > 0 )
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
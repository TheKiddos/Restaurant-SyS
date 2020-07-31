class Type {
  String _name;

  Type( this._name );

  String get name => _name;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Type && runtimeType == other.runtimeType && _name == other._name;

  @override
  int get hashCode => _name.hashCode;

  Map<String, dynamic> toJson() =>
  {
    "name": _name
  };
}
@extends('homegernal')

@section('content')
    <div style="float: right;position: relative;top: -150px"><a href="" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Choose your order</button></a></div>

    <div class="w3-light-grey">
    <!-- Right Column -->
        <div class="w3-twothird" >

        <div class="w3-container w3-card w3-white w3-margin-bottom" >

            <h2 class="w3-text-grey w3-padding-16"><i class="fa fa-suitcase fa-fw w3-margin-right w3-xxlarge w3-text-teal"></i>Menu food</h2>



                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a  style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse1">Beverages</a>
                        </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse2">Break fast</a>
                        </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($br as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse3">Burgers</a>
                        </h4>
                    </div>
                    <div id="collapse3" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($h as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse4">Chicken & Sandwiches</a>
                        </h4>
                    </div>
                    <div id="collapse4" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse5">Combo Meal</a>
                        </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse6">Desserts</a>
                        </h4>
                    </div>
                    <div id="collapse6" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>


                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse7">Happy Meal</a>
                        </h4>
                    </div>
                    <div id="collapse7" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($h as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse8">Drinks</a>
                        </h4>
                    </div>
                    <div id="collapse8" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>

                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse9">Salads</a>
                        </h4>
                    </div>
                    <div id="collapse9" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add Food</td>
                                    <td>evaluation Food</td>
                                </tr>
                                @foreach($be as $n)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $n->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$n->name}}</td>
                                        <td>{{$n->describe}}</td>
                                        <td>{{$n->price}}</td>
                                        <td><a href="" id="{{$n->id}}" data-name="{{$n->name}}" data-id="{{$n->id}}"
                                               data-price="{{$n->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="http://localhost:8000/en/home/main/orderpage/{{$x}}/{{$n->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>

            </div>
            <!-- End Right Column -->
         </div>


        <div class="w3-container w3-card w3-white" style="float: left" >
            <h2 class="w3-text-grey w3-padding-16"><i class="fa fa-certificate fa-fw w3-margin-right w3-xxlarge w3-text-teal"></i>Your bill
            </h2>
            <form action="" method="post">
                @csrf
                @foreach($site as $n)
                    <input type="hidden" name="site" value="{{$n->city}},{{$n->region}}">
                @endforeach
                <table class="table table-striped addt">

                    <tr>
                        <th>name</th>
                        <th>quntity</th>
                        <th>price</th>
                        <th>del</th>
                    </tr>


                </table>
                <p>total price:</p>
                <div class="total" style="color: #e3342f;font-size: 20px">
                    0
                </div>
                <button  class="btn btn-default btn-sm but"> add product</button>
            </form>
        </div>






@endsection

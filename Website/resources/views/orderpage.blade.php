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
                            <a  style="text-decoration: none" data-toggle="collapse" data-parent="#accordion" href="#collapse1">Menu</a>
                        </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td>Item</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>Add</td>
                                    <td>Rate</td>
                                </tr>
                                @foreach($items as $item)

                                    <tr>
                                        <td> <img src="{{ substr($item->image, strrpos($item->image, '/')) }}" alt="{{$item->name}}" width="70" height="70"></td>
                                        <td>{{$item->name}}</td>
                                        <td>{{$item->describe}}</td>
                                        <td>{{$item->price}}</td>
                                        <td><a href="#" id="{{$item->id}}" data-name="{{$item->name}}" data-id="{{$item->id}}"
                                               data-price="{{$item->price}}" class="btn btn-warning btn-sm add">+</a></td>
                                        <td><a href="/home/main/rate/{{$item->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Right Column -->
         </div>


        <div class="w3-container w3-card w3-white" style="float: left;" >
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
                        <th>quantity</th>
                        <th>price</th>
                        <th>remove</th>
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

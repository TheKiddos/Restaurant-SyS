@extends('home_general')

@section('content')
    <div class="w3-light-grey" style="margin-top: 10%">
        <!-- Right Column -->
        <div class="w3-twothird" style="width: 100%">
            <div class="w3-container w3-card w3-white" >
                <h2 class="w3-text-grey w3-padding-16"><i class="fa fa-certificate fa-fw w3-margin-right w3-xxlarge w3-text-teal"></i>Nutrition Calculator</h2>
                <form action="" method="post">
                    @csrf
                    <table class="table table-striped addt">

                        <tr>
                            <th>name</th>
                            <th>quantity</th>
                            <th>price</th>
                            <td>calories</td>
                            <td>fat</td>
                            <td>protein</td>
                            <td>carbohydrates</td>
                            <th>del</th>
                        </tr>


                    </table>
                    <br><br><hr>
                    <p>محتويات الوجبة</p>

                    <table class="table table-striped">

                        <tr>
                            <th>total price</th>
                            <td>total calories</td>
                            <td>total fat</td>
                            <td>total protein</td>
                            <td>total carbohydrates</td>

                        </tr>
                        <tr>
                            <td class="totalprice" style="color: #e3342f;font-size: 20px">0</td>
                            <td class="totalcalories" style="color:blue;font-size: 20px">0</td>
                            <td class="totalfat" style="color: #636b6f;font-size: 20px" >0</td>
                            <td class="totalbrotien" style="color: chartreuse;font-size: 20px">0</td>
                            <td class="totalkerbohedrat" style="color: darkred;font-size: 20px">0</td>

                        </tr>


                    </table>

                </form>
            </div>
            <br><br>
            <div class="w3-container w3-card w3-white w3-margin-bottom">

                <h2 class="w3-text-grey w3-padding-16"><i class="fa fa-suitcase fa-fw w3-margin-right w3-xxlarge w3-text-teal"></i>Menu</h2>
                <div class="panel panel-warning">
                    <div id="collapse1" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <table class="table table-striped table-hover">
                                <tr>
                                    <td></td>
                                    <td> Food</td>
                                    <td>Description</td>
                                    <td>Price</td>
                                    <td>calories</td>
                                    <td>fat</td>
                                    <td>protein</td>
                                    <td>carbohydrates</td>
                                    <td>Add Food</td>
                                </tr>
                                @foreach($items as $item)

                                    <tr>
                                        <td> <img src="{{ asset('uploads/' . $item->image) }}" alt="Cinque Terre" width="70" height="70"></td>
                                        <td>{{$item->name}}</td>
                                        <td>{{$item->description}}</td>
                                        <td>{{$item->price}}</td>
                                        <td>{{$item->calories}}</td>
                                        <td>{{$item->fat}}</td>
                                        <td>{{$item->protein}}</td>
                                        <td>{{$item->carbohydrates}}</td>
                                        <td><a href="" id="{{$item->id}}" data-name="{{$item->name}}" data-id="{{$item->id}}"
                                               data-price="{{$item->price}}" data-calories="{{$item->calories}}"
                                               data-fat="{{$item->fat}}" data-brotien="{{$item->protein}}"  data-kerbohedrat="{{$item->carbohydrates}}" class="btn btn-warning btn-sm add">+</a></td>
                                    </tr>

                                @endforeach
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Right Column -->
        </div>
        <!-- End Grid -->
    </div>
@endsection





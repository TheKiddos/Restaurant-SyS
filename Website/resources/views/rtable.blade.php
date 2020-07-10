@extends('homegernal')

@section('content')
    @if(app()->getLocale()=='en')
    <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="http://localhost:8000/en/home/main/rtable/{{$x}}" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
    <div class="container-fluid " id="boxx">
        <h1>Enter date reservation to display table:</h1>
        <form action="" class="was-validated"  method="POST">
            @csrf

            <input type="date" id="start" style="width: 100%;height: 50px;font-size: 20px" class= form-control" name="date"
                   min="2020-01-01" max="2020-12-31" required>


            <button type="submit" class="btn btn-warning" style="float: right"> Search</button>
        </form>
        <br><br>
         <hr>

    @foreach($m as $n)
            <div  style="float: left;padding: 20px;font-size: 20px ">
                <div class="w3-col l3 m6 w3-margin-bottom">
                    <img src="{{asset('table.jpg')}}" alt="John">
                    <hr>
                    <p >number table: {{$n->id}}</p>
                    <p>capacity table :{{$n->capacity}} person</p>
                    <p>price reservation table :{{$n->tablefee}} $</p>
                    <p><a href="http://localhost:8000/en/home/main/rtable/reserve/{{$x}}/{{$d}}/{{$n->id}}"><button class="w3-button btn btn-danger w3-block">Select</button></a></p>
                </div>
            </div>
    @endforeach
    </div>

   @else
        <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="http://localhost:8000/ar/home/main/rtable/{{$x}}" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
        <div class="container-fluid " id="boxx">
            <h1>ادخل التاريخ لعرض الطاولات المتوافرة:</h1>
            <form action="" class="was-validated"  method="POST">
                @csrf
                <input type="date" id="start" style="width: 100%;height: 50px;font-size: 20px" class= form-control" name="date"
                       placeholder="2020-07-22"
                       min="2020-01-01" max="2020-12-31" required>
                <button type="submit" class="btn btn-warning" style="float: right"> ابحث</button>
            </form>
            <br><br>
            <hr>
            @foreach($m as $n)
                <div  style="float: left;padding: 20px;font-size: 20px ">
                    <div class="w3-col l3 m6 w3-margin-bottom">
                        <img src="{{asset('table.jpg')}}" alt="John">
                        <hr>
                        <p >رقم الطاولة {{$n->id}}</p>
                        <p>سعة الطاولة {{$n->capacity}} شخص</p>
                        <p>سعر حجز الطاولة${{$n->tablefee}} </p>
                        <p><a href="http://localhost:8000/ar/home/main/rtable/reserve/{{$x}}/{{$d}}/{{$n->id}}"><button class="w3-button btn btn-danger w3-block">اختر</button></a></p>
                    </div>
                </div>
            @endforeach
        </div>
   @endif
@endsection

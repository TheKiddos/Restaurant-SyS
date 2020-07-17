@extends('homegernal')

@section('content')
    @if(app()->getLocale()=='en')
    <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
    <div class="container-fluid " id="boxx">
        <h1>Enter date reservation to display table:</h1>
        <form action="" class="was-validated"  method="POST">
            @csrf
            <input type="date" id="start" style="width: 100%;height: 50px;font-size: 20px" class= "form-control" name="date"
                   min="{{date("yy-m-d")}}" required>
            <button type="submit" class="btn btn-warning" style="float: right"> Search</button>
        </form>
        <br><br>
         <hr>

        @if($reservation_date != null)
        @foreach($free_tables as $table)
            <div  style="float: left;padding: 20px;font-size: 20px ">
                <div class="w3-col l3 m6 w3-margin-bottom">
                    <img src="{{asset('table.jpg')}}" alt="John">
                    <hr>
                    <p >number table: {{$table->id}}</p>
                    <p>capacity table :{{$table->capacity}} person</p>
                    <p>price reservation table :{{$table->fee}} $</p>
                    <p><a href="/home/main/tables/reserve/{{$table->id}}/{{$reservation_date}}/"><button class="w3-button btn btn-danger w3-block">Select</button></a></p>
                </div>
            </div>
        @endforeach
        @endif
    </div>

   @else
        <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
        <div class="container-fluid " id="boxx">
            <h1>ادخل التاريخ لعرض الطاولات المتوافرة:</h1>
            <form action="" class="was-validated"  method="POST">
                @csrf
                <input type="date" id="start"" style="width: 100%;height: 50px;font-size: 20px" class= "form-control" name="date"
                       min="{{date("yy-m-d")}}" required>
                <button type="submit" class="btn btn-warning" style="float: right"> ابحث</button>
            </form>
            <br><br>
            <hr>
            @if($reservation_date != null)
            @foreach($free_tables as $table)
                <div  style="float: left;padding: 20px;font-size: 20px ">
                    <div class="w3-col l3 m6 w3-margin-bottom">
                        <img src="{{asset('table.jpg')}}" alt="John">
                        <hr>
                        <p >رقم الطاولة {{$table->id}}</p>
                        <p>سعة الطاولة {{$table->capacity}} شخص</p>
                        <p>سعر حجز الطاولة${{$table->fee}} </p>
                        <p><a href="/home/main/tables/reserve/{{$table->id}}/{{$reservation_date}}/"><button class="w3-button btn btn-danger w3-block">اختر</button></a></p>
                    </div>
                </div>
            @endforeach
            @endif
        </div>
   @endif
@endsection

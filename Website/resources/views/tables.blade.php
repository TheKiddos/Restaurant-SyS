@extends('home_general')

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
                <figure class="figure mx-auto d-inline-block">
                    <img class="figure-img img-responsive img-fluid rounded mx-auto d-inline-block" src="{{asset('table.jpg')}}" alt="John">
                    <br><br><br><br><br>
                    <figcaption class="figure-caption mx-auto d-inline-block text-center">
                        <hr>
                        <p >number table: {{$table->id}}</p>
                        <p>capacity table :{{$table->capacity}} person</p>
                        <p>price reservation table :{{$table->fee}} $</p>
                        <p><a href="/home/main/tables/reserve/{{$table->id}}/{{$reservation_date}}/"><button class="w3-button btn btn-danger w3-block">Select</button></a></p>
                    </figcaption>
                    <br>
                </figure>
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
                    <figure class="figure mx-auto d-inline-block">
                        <img class="figure-img img-responsive img-fluid rounded mx-auto d-inline-block" src="{{asset('table.jpg')}}" alt="John">
                        <br><br><br><br><br>
                        <figcaption class="figure-caption mx-auto d-inline-block text-center">
                            <hr>
                            <p >{{$table->id}}: رقم الطاولة</p>
                            <p>{{$table->capacity}}: سعة الطاولة</p>
                            <p>{{$table->fee}}: ثمن الجلوس على الطاولة</p>
                            <p><a href="/home/main/tables/reserve/{{$table->id}}/{{$reservation_date}}/"><button class="w3-button btn btn-danger w3-block">اختيار الطاولة</button></a></p>
                        </figcaption>
                        <br>
                    </figure>
            @endforeach
            @endif
        </div>
   @endif
@endsection

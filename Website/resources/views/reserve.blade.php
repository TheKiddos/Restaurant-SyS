@extends('homegernal')

@section('content')
    @if(app()->getLocale()=='en')
        <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
        <div class="container-fluid " id="boxx">
            <h1>Reserve:</h1>
            <form action="" class="was-validated"  method="POST">
                @csrf
                <div class="form-group autocomplete"  >
                    <input id="myInput" type="text" class="  form-control" name="idtable" value="{{$table_id}}" hidden>
                </div>
                <div class="form-group autocomplete"  >
                    <input id="myInput" type="text" class="  form-control" name="date" value="{{$reservation_date}}" hidden>
                </div>
                <div class="form-group autocomplete"  >
                    <label for="time">Time reservation:</label>
                    <input type="time" id="appt" class="form-control"  name="time"
                          style="font-size: 15px" required>
                </div>
                <button type="submit" class="btn btn-danger" > Reserve</button>
            </form>
            <br><br>
        </div>


    @else
        <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
        <div class="container-fluid " id="boxx">
            <h1>Reserve:</h1>
            <form action="" class="was-validated"  method="POST">
                @csrf
                <div class="form-group autocomplete"  >
                    <input id="myInput" type="text" class="  form-control" name="idtable" value="{{$table_id}}" hidden>
                </div>
                <div class="form-group autocomplete"  >
                    <input id="myInput" type="text" class="  form-control" name="date" value="{{$reservation_date}}" hidden>
                </div>
                <div class="form-group autocomplete"  >
                    <label for="city">وقت الحجز:</label>
                    <input type="time" id="appt" class="form-control"  name="time"
                           style="font-size: 15px" required>
                </div>
                <button type="submit" class="btn btn-danger" > احجز</button>
            </form>
            <br><br>
        </div>
    @endif
@endsection

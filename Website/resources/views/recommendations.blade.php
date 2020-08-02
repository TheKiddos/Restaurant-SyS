<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <title>{{ config('app.name', 'Laravel') }}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" href="{{ asset('cssmenu.css') }}" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
    <div class=container-fluid">
        <div style="margin-left:30%;padding:1px 16px;height:1000px;">
            <h1 style="text-align: center;font-size: 50px"><strong>Recommendations</strong></h1>

            @foreach($items as $item)
                <div class="gallery">
                    <img src="{{ substr($item->image, strrpos($item->image, '/')) }}" alt="{{$item->name}}" width="600" height="400" >
                    <div class="desc">
                        <h2>{{$item->name}}</h2>
                        <h3>Price: {{$item->price}}</h3>
                        <p>{{$item->description}}</p>
                        <td><a href="/home/main/rate/{{$item->id}}"><button class="btn btn-warning btn-sm"><span>&#11088;</span></button></a></td>
                    </div>
                </div>
            @endforeach
        </div>
    </div>
</body>
</html>

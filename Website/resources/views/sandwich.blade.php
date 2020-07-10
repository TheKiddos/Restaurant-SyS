<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <title>www.HungerS7.com</title>
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
    <ul class="ul">
        <li><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home">Home</a></li>
        <hr>
        <li><img src="{{ asset('food11.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/breakfast">Breakfast</a></li>
        <hr>
        <li><img src="{{ asset('food10.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/burgers">Burgers</a></li>
        <hr>
        <li><img src="{{ asset('food12.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/sandwich">Chicken & sandwiches</a></li>
        <hr>
        <li><img src="{{ asset('food13.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/combo">Combo Meal</a></li>
        <hr>
        <li><img src="{{ asset('food14.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/dessert">  Desserts & shakes</a></li>
        <hr>
        <li><img src="{{ asset('food5.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu/happy"> Happy Meal</a></li>
        <hr>
        <li><img src="{{ asset('food15.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="#about">Salads</a></li>
        <hr>
        <li><img src="{{ asset('food9.jpg') }}" style="border-radius: 50px;width: 50px;height: 50px"><a style="text-decoration: none" href="http://127.0.0.1:8000/en/home/main/menu">Beverages</a></li>

    </ul>

    <div style="margin-left:30%;padding:1px 16px;height:1000px;">
        <h1 style="text-align: center;font-size: 50px"><strong>Chicken & sandwiches</strong></h1>

        <div class="gallery">
            <a target="_blank" href="http://127.0.0.1:8000/en/home/main/menu/SausageMcGriddles">
                <img src="{{ asset('food24.jpg') }}" alt="Northern Lights" width="600" height="400" >
            </a>
            <div class="desc">Sausage McGriddles®</div>
        </div>

        <div class="gallery">
            <a target="_blank" href="http://127.0.0.1:8000/en/home/main/menu/EggMcMuffin">
                <img src="{{ asset('food25.jpg') }}" alt="Mountains" width="600" height="400" >
            </a>
            <div class="desc">Egg McMuffin®</div>
        </div>

        <div class="gallery">
            <a target="_blank" href="http://127.0.0.1:8000/en/home/main/menu/SausageMcMuffin">
                <img src="{{ asset('food28.jpg') }}" alt="Mountains" width="600" height="400" >
            </a>
            <div class="desc"> Sausage McMuffin®</div>
        </div>

        <div class="gallery">
            <a target="_blank" href="http://127.0.0.1:8000/en/home/main/menu/SausageBiscuit">
                <img src="{{ asset('food27.jpg') }}" alt="Mountains" width="600" height="400" >
            </a>
            <div class="desc">Sausage Biscuit with Egg</div>
        </div>

        <div class="gallery">
            <a target="_blank" href="http://127.0.0.1:8000/en/home/main/menu/Hotcakes">
                <img src="{{ asset('food28.jpg') }}" alt="Mountains" width="600" height="400" >
            </a>
            <div class="desc">Hotcakes </div>
        </div>


    </div>
</div>
</body>
</html>



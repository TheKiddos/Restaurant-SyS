@extends('homegernal')

   @section('content')
        @if(app()->getLocale()=='en')
            <strong id="address">Exclusive Deals</strong>
            <br><br><br><br><br>
            <img src="{{ asset('food46.jpeg') }}" alt="Mountains" width="1200" height="600" id="image">
            <br><br><br><br><br>
            <strong style="font-size: 40px; position: relative;left: 125px">Discover delicious HungerS7 deals in our App for <a href="#b">iOS</a>  and <a href="#d">Android</a>.</strong>
            <br>

        @else

            <strong id="address">Exclusive Deals</strong>
            <br><br><br><br><br>
            <img src="{{ asset('food46.jpeg') }}" alt="Mountains" width="1200" height="600" id="image">
            <br><br><br><br><br>
            <strong style="font-size: 40px; position: relative;left: 125px">Discover delicious HungerS7 deals in our App for <a href="#b">iOS</a>  and <a href="#d">Android</a>.</strong>
            <br>



        @endif
    @endsection

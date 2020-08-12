@extends('homegernal')

   @section('content')
        @if(app()->getLocale()=='en')
            <strong class="h1 text-center mx-auto d-block">Exclusive Deals</strong>
            <br><br><br><br><br>
            <figure class="figure mx-auto d-block">
                <img class="figure-img img-responsive rounded mx-auto d-block" src="{{ asset('food46.jpeg') }}" alt="Mountains">
                <br><br><br><br><br>
                <figcaption class="figure-caption mx-auto d-block text-center"><strong>Discover delicious {{ config('app.name', 'Laravel') }} deals in our App for <a href="#b">iOS</a>  and <a href="#d">Android</a></strong></figcaption>
                <br>
            </figure>

        @else

            <strong class="h1 text-center mx-auto d-block">Exclusive Deals</strong>
            <br><br><br><br><br>
            <figure class="figure mx-auto d-block">
                <img class="figure-img img-responsive rounded mx-auto d-block" src="{{ asset('food46.jpeg') }}" alt="Mountains">
                <br><br><br><br><br>
                <figcaption class="figure-caption mx-auto d-block text-center"><strong>Discover delicious {{ config('app.name', 'Laravel') }} deals in our App for <a href="#b">iOS</a>  and <a href="#d">Android</a></strong></figcaption>
                <br>
            </figure>
        @endif
    @endsection

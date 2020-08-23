<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <title>{{ config('app.name', 'Laravel') }}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <link type="text/css" href="{{ asset('csshomegernal.css') }}" rel="stylesheet">
    <link type="text/css" href="{{ asset('csshome.css') }}" rel="stylesheet">
    <link type="text/css" href="{{ asset('csssitepage.css') }}" rel="stylesheet">
    <link type="text/css" href="{{ asset('cssabout.css') }}" rel="stylesheet">
    <link type="text/css" href="{{ asset('csstable.css') }}" rel="stylesheet">
    <script src='{{asset('java.js')}}'></script>
</head>
<body>
    @if(app()->getLocale()=='en')
        <nav id="navbar" class="navbar navbar-expand-lg navbar-dark fixed-top">
            <div class="container">
                <a id="header" class="navbar-brand" href="/home/main"><img id="logo" src="{{asset('icon.png')}}"
                                                                           width="64px" height="64px"><span
                        style="margin-left: 0.5em">Digital Restaurant System</span></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-nav"
                        aria-controls="main-nav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="fa fa-bars"></span>
                </button>

                <div class="collapse navbar-collapse" id="main-nav">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a href="/home/main" class="nav-link fa fa-home fa-2x align-content-center"></a></li>
                        <li class="nav-item"><a href="/home/main/about" class="nav-link">About</a></li>
                        <li class="nav-item"><a href="/home/main/menu" class="nav-link">Menu</a></li>
                        <li class="nav-item"><a href="/home/main/deals" class="nav-link">Deals</a></li>
                        <li class="nav-item"><a href="/home/sitechange" class="nav-link">Change Location</a></li>
                        <li class="nav-item">
                            <div class="panel-group">
                                <a class='nav-link' data-target="#collapselanguage" data-toggle="collapse" href="#collapselanguage">Language<span
                                        class="caret"></span></a>
                            </div>
                            <div id="collapselanguage" class="collapse">
                                <ul>
                                    @foreach(LaravelLocalization::getSupportedLocales() as $localeCode => $properties)
                                        <li>
                                            <a class="nav-link" style="font-size: 12px" rel="alternate"
                                               hreflang="{{ $localeCode }}"
                                               href="{{ LaravelLocalization::getLocalizedURL($localeCode, null, [], true) }}">
                                                {{ $properties['native'] }}
                                            </a>
                                        </li>
                                    @endforeach
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class='nav-link' href="{{ route('logout') }}"
                               onclick="event.preventDefault();
                                          document.getElementById('logout-form').submit();">
                                Logout
                            </a>
                            <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                                @csrf
                            </form>
                        </li>
                        <li class="nav-item"><a class='nav-link' href="/home/main/comment">Contact Us</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <main class="py-4" style="margin-top: 75px">
            @yield('content')
        </main>

        <!-- Footer -->
        <footer class="w3-container w3-padding-32 w3-dark">
            <div class="w3-row-padding">
                <div class="w3-container w3-padding-64 w3-center  w3-xlarge">
                    <i class="fa fa-facebook-official w3-hover-opacity"></i>
                    <i class="fa fa-instagram w3-hover-opacity"></i>
                    <i class="fa fa-snapchat w3-hover-opacity"></i>
                    <i class="fa fa-pinterest-p w3-hover-opacity"></i>
                    <i class="fa fa-twitter w3-hover-opacity"></i>
                    <i class="fa fa-linkedin w3-hover-opacity"></i>
                    <p class="w3-medium">Powered By <a href="/home"
                                                       target="_blank">{{ config('app.name', 'Laravel') }}</a></p>
                </div>
            </div>
        </footer>
    @else
        <nav id="navbar" class="navbar navbar-expand-lg navbar-dark fixed-top">
            <div class="container">
                <a id="header" class="navbar-brand" href="/home/main"><img id="logo" src="{{asset('icon.png')}}"
                                                                           width="64px" height="64px"><span
                        style="margin-left: 0.5em">نظام المطعم الرقمي</span></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-nav"
                        aria-controls="main-nav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="fa fa-bars"></span>
                </button>

                <div class="collapse navbar-collapse" id="main-nav">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a href="/home/main" class="nav-link fa fa-home fa-2x align-content-center"></a></li>
                        <li class="nav-item"><a href="/home/main/about" class="nav-link">حول</a></li>
                        <li class="nav-item"><a href="/home/main/menu" class="nav-link">الوجبات</a></li>
                        <li class="nav-item"><a href="/home/main/deals" class="nav-link">العروض</a></li>
                        <li class="nav-item"><a href="/home/sitechange" class="nav-link">الموقع</a></li>
                        <li class="nav-item">
                            <div class="panel-group">
                                <a data-target="#collapselanguage" class='nav-link' data-toggle="collapse" href="#collapselanguage">اللغة<span
                                        class="caret"></span></a>
                            </div>
                            <div id="collapselanguage" class="collapse">
                                <ul>
                                    @foreach(LaravelLocalization::getSupportedLocales() as $localeCode => $properties)
                                        <li>
                                            <a class="nav-link" style="font-size: 12px" rel="alternate"
                                               hreflang="{{ $localeCode }}"
                                               href="{{ LaravelLocalization::getLocalizedURL($localeCode, null, [], true) }}">
                                                {{ $properties['native'] }}
                                            </a>
                                        </li>
                                    @endforeach
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class='nav-link' href="{{ route('logout') }}"
                               onclick="event.preventDefault();
                                          document.getElementById('logout-form').submit();">
                                تسجيل الخروج
                            </a>
                            <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                                @csrf
                            </form>
                        </li>
                        <li class="nav-item"><a class='nav-link' href="/home/main/comment">أرسل لنا</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <main class="py-4" style="margin-top: 75px">
            @yield('content')
        </main>

        <!-- Footer -->
        <footer class="w3-container w3-padding-32 w3-dark">
            <div class="w3-row-padding">
                <div class="w3-container w3-padding-64 w3-center  w3-xlarge">
                    <i class="fa fa-facebook-official w3-hover-opacity"></i>
                    <i class="fa fa-instagram w3-hover-opacity"></i>
                    <i class="fa fa-snapchat w3-hover-opacity"></i>
                    <i class="fa fa-pinterest-p w3-hover-opacity"></i>
                    <i class="fa fa-twitter w3-hover-opacity"></i>
                    <i class="fa fa-linkedin w3-hover-opacity"></i>
                    <p class="w3-medium"><a href="/home"
                                                       target="_blank">{{ config('app.name', 'Laravel') }}</a> تصميم </p>
                </div>
            </div>
        </footer>
    @endif
</body>
</html>


<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <title>{{ config('app.name', 'Laravel') }}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" href="{{ asset('csshomegernal.css') }}" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script src='{{asset('javascript.js')}}'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
<div class="container-fluid navB">
    <div class="row">
        <div class="col-sm-4">
            <img src="{{asset('food5.jpg')}}" style="width: 150px;height: 150px;position: relative;left: 140px">
        </div>
        <div class="col-sm-8">

            <ul>
                <li>
                    <div class="panel-group" style="width:140px;height: 15px;font-size: 15px">
                        <a data-toggle="collapse" href="#collapse1" style="text-decoration: none;">language<span class="caret"></span></a>
                    </div>
                    <div id="collapse1" class=" collapse" style=" color: black">
                        <ul>
                            @foreach(LaravelLocalization::getSupportedLocales() as $localeCode => $properties)
                                <li>
                                    <a style="text-decoration: none;font-size: 10px" rel="alternate" hreflang="{{ $localeCode }}" href="{{ LaravelLocalization::getLocalizedURL($localeCode, null, [], true) }}">
                                        {{ $properties['native'] }}
                                    </a>
                                </li>
                            @endforeach
                        </ul>
                    </div>
                </li>

                <li>

                    <a   style="text-decoration: none;font-size: 15px" href="{{ route('logout') }}"
                         onclick="event.preventDefault();
                                                     document.getElementById('logout-form').submit();">
                        Logout from website
                    </a>

                    <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                        @csrf
                    </form>

                </li>
                <li><a href="/home/main/comment" style="text-decoration: none;font-size: 15px">contact us<i class='far fa-comments'></i></a></li>
            </ul>
            <ul>
                <li><a href="/home/main/menu" style="text-decoration: none;font-size: 25px">Menu</a></li>
                <li><a href="/home/main/about" style="text-decoration: none;font-size: 25px">ِAbout Our Food</a></li>
                <li><a href="/home/main/deals" style="text-decoration: none;font-size: 25px">Deals & Our App</a></li>
                <li><a href="#contact" style="text-decoration: none;font-size: 25px">Trending Now</a></li>

            </ul>

        </div>
    </div>
</div>
<br><br>

    <div class="w3-light-grey">
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

    <br><br><br><br><br>
    <!-- Footer -->
    <footer class="w3-container w3-padding-32 w3-dark">
        <div class="w3-row-padding">
            <div class="w3-third">
                <h3>FOOTER</h3>
                <p>Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non fringilla.</p>
                <p>Powered by:{{ config('app.name', 'Laravel') }} </p>
            </div>

            <div class="w3-third">
                <h3>BLOG POSTS</h3>
                <ul class="w3-ul w3-hoverable">
                    <li class="w3-padding-16">
                        <img src="{{asset('food2.jpg')}}" class="w3-left w3-margin-right" style="width:50px">
                        <span class="w3-large">Lorem</span><br>
                        <span>Sed mattis nunc</span>
                    </li>
                    <li class="w3-padding-16">
                        <img src="{{asset('food2.jpg')}}" class="w3-left w3-margin-right" style="width:50px">
                        <span class="w3-large">Ipsum</span><br>
                        <span>Praes tinci sed</span>
                    </li>
                </ul>
            </div>

            <div class="w3-third">
                <h3>POPULAR TAGS</h3>
                <p>
                    <span class="w3-tag w3-black w3-margin-bottom">Travel</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">New York</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">London</span>
                    <span class="w3-tag w3-grey w3-small w3-margin-bottom">IKEA</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">NORWAY</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">DIY</span>
                    <span class="w3-tag w3-grey w3-small w3-margin-bottom">Ideas</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">Baby</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">Family</span>
                    <span class="w3-tag w3-grey w3-small w3-margin-bottom">News</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">Clothing</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">Shopping</span>
                    <span class="w3-tag w3-grey w3-small w3-margin-bottom">Sports</span> <span class="w3-tag w3-grey w3-small w3-margin-bottom">Games</span>

                </p>


            </div>
            <br><br><br><br><br><br>
            <div class="w3-container w3-padding-64 w3-center  w3-xlarge">
                <i class="fa fa-facebook-official w3-hover-opacity"></i>
                <i class="fa fa-instagram w3-hover-opacity"></i>
                <i class="fa fa-snapchat w3-hover-opacity"></i>
                <i class="fa fa-pinterest-p w3-hover-opacity"></i>
                <i class="fa fa-twitter w3-hover-opacity"></i>
                <i class="fa fa-linkedin w3-hover-opacity"></i>
                <p class="w3-medium">Powered by <a href="/home" target="_blank">{{ config('app.name', 'Laravel') }}</a></p>
            </div>
        </div>
    </footer>
</body>
</html>















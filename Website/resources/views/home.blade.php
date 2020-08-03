@extends('homegernal')

@section('content')
@if(app()->getLocale()=='en')
    <div class="container-fluid ">
        <div style="float: right;position: relative;top: -150px"><a  href="/home/main/orderpage/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Choose your order</button></a></div>
        <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
        <div style="float: right;position: relative;top: -150px;left: -20px"><a  href="/home/main/recommendations/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Recommendations</button></a></div>

        <br>   <br>
<div id="demo" class="carousel slide" data-ride="carousel">
    <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="2"></li>
        <li data-target="#demo" data-slide-to="0"class="active"></li>
    </ul>
    <div class="carousel-inner">
        <div class="carousel-item">
            <img src="{{asset('food2.jpg')}}" alt="Chicago" width="100%" height="500">
            <div class="carousel-caption">

                <p>Thank you!</p>
            </div>
        </div>
        <div class="carousel-item active">
            <img src="{{asset('food3.jpg')}}" alt="New York" width="100%" height="500">
            <div class="carousel-caption" style="color: yellow;font-size: 30px">

                <p>We love the Big Burger!</p>
            </div>
        </div>
    </div>
    <a class="carousel-control-prev" href="#demo" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#demo" data-slide="next">
        <span class="carousel-control-next-icon"></span>
    </a>
</div>
<br><br><br><br><br>
<img src="{{asset('food6.jpg')}}" width="70%" height="500">
<div class="boximage">
    <p style="font-size: 30px"><strong>Mobile Order & Pay Contact-free</strong></p>
    <p>Mobile Order & Pay is a great call, with exclusive deals on all your favorites, plus Drive Thru and curbside pickup.*</p>
    <br>
    <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning"> Order Your App From Google play</button></a>
    <br>    <br>
    <p style="font-size: 10px">*{{ config('app.name', 'Laravel') }} App download and registration required. Mobile Order & Pay available at participating Hnger Station. </p>
</div>

<div class="boximage2">
    <p style="font-size: 30px"><strong>Free Medium Fries with a $1 Minimum Purchase</strong></p>
    <p>Fridays only, with Mobile Order & Pay
        Offer valid 1x use each Friday thru 6/28/20 with minimum purchase of
        $1 (excluding tax). HngerS7 App download and registration required. Offer only available through
        Mobile Order & Pay. Mobile Order & Pay at participating  Hnger Station. © 2020  Hnger Station.
    </p>
    <br>
    <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-danger"> Learn More</button></a>
    <br>    <br>
    <p style="font-size: 10px">*{{ config('app.name', 'Laravel') }} App download and registration required. Mobile Order & Pay available at participating {{ config('app.name', 'Laravel') }} Station. </p>
</div>
<div class="underboximage1" >
    <img src="{{asset('food7.jpg')}}" alt="Chicago" width="40%" height="500">
    <img  style="float: right" src="{{asset('food8.jpg')}}" alt="Chicago" width="40%" height="500">
</div>
<div class="underboximage2" >
<p style="font-size: 30px"><strong>Nutrition Calculator</strong></p>
<p style="font-size: 15px"> Explore our nutrition facts and learn more about your favorite McDonald’s menu items.</p>
    <a href="/home/main/calculate" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning"> Get Nutrition Details</button></a>
</div>
<div class="underboximage3" >
    <p style="font-size: 30px"><strong>Championing Communities from Coast to Coast</strong></p>
    <p style="font-size: 15px"> McDonald’s has always been a home base for communities,
        and we’re proud to support them during this crisis. Learn more about how the McFamily is working
        together to make a positive impact across the country and around the world.</p>
    <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-danger"> Learn More</button></a>
</div>
    </div>
@else


    <div style="float: right;position: relative;top: -150px"><a  href="/home/main/orderpage/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Choose your order</button></a></div>
    <div style="float: right;position: relative;top: -150px;left: -10px"><a  href="/home/main/tables/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Reserve your table</button></a></div>
    <div style="float: right;position: relative;top: -150px;left: -20px"><a  href="/home/main/recommendations/" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">Recommendations</button></a></div>

    <br> <br>
    <div id="demo" class="carousel slide" data-ride="carousel">
        <ul class="carousel-indicators">
            <li data-target="#demo" data-slide-to="2"></li>
            <li data-target="#demo" data-slide-to="0"class="active"></li>
        </ul>
        <div class="carousel-inner">
            <div class="carousel-item">
                <img src="{{asset('food2.jpg')}}" alt="Chicago" width="100%" height="500">
                <div class="carousel-caption">

                    <p>شكرا على ثقتك</p>
                </div>
            </div>
            <div class="carousel-item active">
                <img src="{{asset('food3.jpg')}}" alt="New York" width="100%" height="500">
                <div class="carousel-caption" style="color: yellow;font-size: 30px">

                    <p>نحن نحب كثيراBig Burger!</p>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#demo" data-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </a>
        <a class="carousel-control-next" href="#demo" data-slide="next">
            <span class="carousel-control-next-icon"></span>
        </a>
    </div>
    <br><br><br><br><br>
    <img src="{{asset('food6.jpg')}}" width="70%" height="500">
    <div class="boximage">
        <p style="font-size: 30px"><strong> الطلب على الهاتف المحمول والدفع بدون اتصال</strong></p>

        <p>Mobile Order & Pay هي مكالمة رائعة ، مع صفقات حصرية على جميع المفضلة لديك ،
            بالإضافة إلى Drive Thru والتقاط الرصيف. *</p>
        <br>
        <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">
                اطلب تطبيقك من Google play</button></a>
        <br>    <br>
        <p style="font-size: 10px">

            * يلزم تنزيل تطبيق {{ config('app.name', 'Laravel') }} وتسجيله. خدمة الدفع والدفع عبر الهاتف المحمول متاحة في محطة {{ config('app.name', 'Laravel') }} المشاركة</p>
    </div>

    <div class="boximage2">
        <p style="font-size: 30px"><strong>
                بطاطس متوسطة مجانية مع شراء بحد أدنى 1 دولار</strong></p>
        <p>
            أيام الجمعة فقط ، مع Mobile Order & Pay
            العرض صالح 1 استخدام كل يوم جمعة حتى 6/28/20 مع الحد الأدنى للشراء
            $ 1 (باستثناء الضرائب). يلزم تنزيل تطبيق HngerS7 وتسجيله. العرض متاح فقط من خلال
            الطلب والدفع عبر الهاتف المحمول. الطلب والدفع عبر الهاتف المتحرك في محطة Hnger المشاركة. © 2020 محطة هنجر.
        </p>
        <br>
        <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-danger">اقرا المزيد</button></a>
        <br>    <br>
        <p style="font-size: 10px">*{{ config('app.name', 'Laravel') }} App download and registration required. Mobile Order & Pay available at participating {{ config('app.name', 'Laravel') }} Station. </p>
    </div>
    <div class="underboximage1" >
        <img src="{{asset('food7.jpg')}}" alt="Chicago" width="40%" height="500">
        <img  style="float: right" src="{{asset('food8.jpg')}}" alt="Chicago" width="40%" height="500">
    </div>
    <div class="underboximage2" >
        <p style="font-size: 30px"><strong>حاسبة التغذية</strong></p>
        <p style="font-size: 15px">
            استكشف حقائق التغذية لدينا وتعرف على المزيد حول عناصر قائمة ماكدونالدز المفضلة لديك.</p>
        <a href="/home/main/calculate" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-warning">
                احصل على تفاصيل التغذية</button></a>
    </div>
    <div class="underboximage3" >
        <p style="font-size: 30px"><strong>مناصرة المجتمعات من الساحل إلى الساحل</strong></p>
        <p style="font-size: 15px">

            لطالما كان {{ config('app.name', 'Laravel') }} قاعدة منزلية للمجتمعات ،
            ونحن فخورون بدعمهم خلال هذه الأزمة. تعرف على المزيد حول كيفية عمل عائلة Hfamily
            معًا لإحداث تأثير إيجابي في جميع أنحاء البلاد وحول العالم.


        </p>
        <a href="#news" style="text-decoration: none;font-size: 15px"><button type="button" class="btn btn-danger"> اقرا المزيد</button></a>
    </div>

@endif
@endsection


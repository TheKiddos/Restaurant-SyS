@extends('homegernal')

@section('content')
    @if(app()->getLocale()=='en')
<strong id="address">About Our Food</strong>
<br><br><br><br><br>
<img src="{{ asset('food43.jpg') }}" alt="Mountains" width="1200" height="600" id="image">
<br><br><br><br><br>
<strong style="font-size: 40px; position: relative;left: 100px">We’re Passionate About Our Food</strong>
    <br>
    <p style="font-size: 15px; position: relative;left: 100px">From making healthier additions to our
        Happy Meal®, to serving up fresh beef Quarter Pounder® burgers that are <br>cooked when you order,
        we’re always finding ways to show our commitment to our customers and our food.</p>
<br><br><br><br><br>
<ul class="ulabout">

    <li><img src="{{ asset('food3.jpg') }}"  width="600" height="400" >    <div class="desc"> <strong style="font-size: 20px;">Commitment to Quality</strong> <br> <p> We're dedicated to improving the way we prepare our quality food and the ingredients that go into it.</p> <br>         <button type="submit" class="btn btn-warning">Learn More</button>
        </div></li>
    <li><img src="{{ asset('food44.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;">Our Food, Your Questions</strong> <br> <p> We're transparent about our food; so when you ask, we tell.</p>   <br>       <button type="submit" class="btn btn-warning">Learn More</button>
        </div></li>
    <li><img src="{{ asset('food45.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;">What's in Your Food  </strong> <br> <p> Find out what makes our ingredients special.</p>    <br>      <button type="submit" class="btn btn-warning">Learn More</button>
        </div> </li>
    <li><img src="{{ asset('food7.jpg') }}"  width="600" height="400" >    <div class="desc"> <strong style="font-size: 20px;"> Our Food Philosophy </strong> <br> <p>We’re passionate about our food. That’s why we’re committed to always evolving what matters to you. </p>     <br>    <button type="submit" class="btn btn-warning">Learn More</button>
        </div></li>
    <li><img src="{{ asset('food17.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;"> Fresh Beef </strong> <br> <p> Our Quarter Pounder®* patty is made with 100% fresh beef and cooked right when you order.<br> It’s hot and deliciously juicy and full of flavor.
                *Weight before cooking 4 oz. At participating McDonald's.<br> Fresh Beef available at most restaurants in contiguous US. <br>Not available in Alaska, Hawaii, and US Territories.</p>     <br>      <button type="submit" class="btn btn-warning">  Learn More</button>
        </div></li>

</ul>
    @else
        <strong id="address">About Our Food</strong>
        <br><br><br><br><br>
        <img src="{{ asset('food43.jpg') }}" alt="Mountains" width="1200" height="600" id="image">
        <br><br><br><br><br>
        <strong style="font-size: 40px; position: relative;left: 100px">We’re Passionate About Our Food</strong>
        <br>
        <p style="font-size: 15px; position: relative;left: 100px">From making healthier additions to our
            Happy Meal®, to serving up fresh beef Quarter Pounder® burgers that are <br>cooked when you order,
            we’re always finding ways to show our commitment to our customers and our food.</p>
        <br><br><br><br><br>
        <ul class="ulabout">

            <li><img src="{{ asset('food3.jpg') }}"  width="600" height="400" >    <div class="desc"> <strong style="font-size: 20px;">Commitment to Quality</strong> <br> <p> We're dedicated to improving the way we prepare our quality food and the ingredients that go into it.</p> <br>         <button type="submit" class="btn btn-warning">Learn More</button>
                </div></li>
            <li><img src="{{ asset('food44.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;">Our Food, Your Questions</strong> <br> <p> We're transparent about our food; so when you ask, we tell.</p>   <br>       <button type="submit" class="btn btn-warning">Learn More</button>
                </div></li>
            <li><img src="{{ asset('food45.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;">What's in Your Food  </strong> <br> <p> Find out what makes our ingredients special.</p>    <br>      <button type="submit" class="btn btn-warning">Learn More</button>
                </div> </li>
            <li><img src="{{ asset('food7.jpg') }}"  width="600" height="400" >    <div class="desc"> <strong style="font-size: 20px;"> Our Food Philosophy </strong> <br> <p>We’re passionate about our food. That’s why we’re committed to always evolving what matters to you. </p>     <br>    <button type="submit" class="btn btn-warning">Learn More</button>
                </div></li>
            <li><img src="{{ asset('food17.jpg') }}"  width="600" height="400" >   <div class="desc"> <strong style="font-size: 20px;"> Fresh Beef </strong> <br> <p> Our Quarter Pounder®* patty is made with 100% fresh beef and cooked right when you order.<br> It’s hot and deliciously juicy and full of flavor.
                        *Weight before cooking 4 oz. At participating McDonald's.<br> Fresh Beef available at most restaurants in contiguous US. <br>Not available in Alaska, Hawaii, and US Territories.</p>     <br>      <button type="submit" class="btn btn-warning">  Learn More</button>
                </div></li>

        </ul>

    @endif
@endsection

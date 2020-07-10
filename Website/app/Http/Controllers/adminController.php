<?php

namespace App\Http\Controllers;

use App\Comment;
use App\Content;
use App\Evaluation;
use App\Order;
use App\Reservation;
use App\Userlater;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class adminController extends Controller
{
    //

    public function home()
    {
        $x=Auth::user()->email;
        return view('home',compact('x'));
    }

    public function siteadd(Request $request)
    {
        $validatedData = $request->validate([
            'city' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:10'],
            'region' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:50'],
        ]);



        $x=Userlater::where('email',Auth::user()->email)->first();
        if($x){
            if (app()->getLocale()=='ar')
            {
                return redirect('http://127.0.0.1:8000/ar/home/main');
            }
            else{
                return redirect('http://127.0.0.1:8000/en/home/main');
            }
        }
        else{

            $x=new Userlater();
            $x->email=Auth::user()->email;
            $x->city=$request->city;
            $x->region=$request->region;
            $x->save();
            if (app()->getLocale()=='ar')
            {

                return redirect('http://localhost:8000/ar/home/main');
            }
            else{
                return redirect('http://localhost:8000/en/home/main');
            }
        }

    }


        //
    public function order($x)
    {

        $site=DB::select('select * from userlaters where email=? ',[$x]);
        $be= DB::select('select * from contents,categories where contents.id=categories.idmeal and category=? ',['beverages']);
        $h= DB::select('select * from contents,categories where contents.id=categories.idmeal and  category=? ',['happy mael']);
        $br= DB::select('select * from contents,categories where contents.id=categories.idmeal and  category=? ',['breakfast ']);
        return view('orderpage',compact('h','br','site','x','be'));

    }
    //
    public function orderadd(Request $request)
    {
       /* $validatedData = $request->validate([
            'quntites' => ['integer','between:1,5'],

        ]);*/



            $x=new Order();

            $n=$request->name;
            foreach ($n as $item){
                $m[]=$item;
            }
            $name=  implode(",", $m);
            $x->name=$name;


            $q=$request->quntites;
            foreach ($q as $i){
                $v[]=$i;

            }
            $quntites= implode(",", $v);
            $x->quntites=$quntites;




            $p=$request->price;
            foreach ($p as $j){
                $h[]=$j;
            }
            $price= implode(",", $h);
            $x->price=$price;


           $x->email=Auth::user()->email;
            $x->site=$request->site;
            $x->total=$request->total;
            $x->save();
            if (app()->getLocale()=='ar')
            {
                return redirect('http://localhost:8000/ar/home/main');
            }
            else{
                return redirect('http://localhost:8000/en/home/main');
            }


    }

     //
    public function content()
    {

        return view('contentadd');
    }

    public function contentadd(Request $request)
    {
        $x = new Content();
        $x->name =$request->name;
        $x->describe =$request->describe;
        $x->image = $request->image;

        if ($request->hasFile('image'))
        {
            $file = $request->file('image');
            $extension = $file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $file->move('uploads/', $filename);
            $x->image = $filename;
        }

        $x->price = $request->price;
        $x->calories = $request->calories;
        $x->fat = $request->fat;
        $x->brotien = $request->brotien;
        $x->kerbohedrat = $request->kerbohedrat;
    //    $x->email=Auth::user()->email;
        $x->save();
        if (app()->getLocale()=='ar')
        {
            return redirect('http://127.0.0.1:8000/ar/home/content');
        }
        else{
            return redirect('http://127.0.0.1:8000/en/home/content');
        }

    }

    public function comment()
    {

        return view('comment');
    }

    public function commentadd(Request $request)
    {
        $validatedData = $request->validate([
            'name' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:15'],
            'subject' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:100'],
        ]);
        $x = new Comment();
        $x->email =Auth::user()->email;;
        $x->name =$request->name;
        $x->subject =$request->subject;
        $x->save();
        if (app()->getLocale()=='ar')
        {
            return redirect('http://127.0.0.1:8000/ar/home/main');
        }
        else{
            return redirect('http://127.0.0.1:8000/en/home/main');
        }
    }

    public function calcolate()
    {
        $d= DB::select('select * from contents where category=? ',['happy mael']);
        $s= DB::select('select * from contents where category=? ',['breakfast ']);
        return view('calcolate',compact('d','s'));

    }
    //
    public function sitechange()
    {
        return view('sitechange');
    }
    public function sitechangepsot(Request $request)
    {
        $validatedData = $request->validate([
            'city' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:10'],
            'region' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:50'],
        ]);

        $email=Auth::user()->email;
        $city=$request->city;
        $region=$request->region;
        DB::select('UPDATE userlaters SET  city=?,  region=? WHERE email=?',[$city,$region,$email]);

        if (app()->getLocale()=='ar')
        {
            return redirect('http://127.0.0.1:8000/ar/home/main');
        }
        else{
            return redirect('http://127.0.0.1:8000/en/home/main');
        }
    }
    //
    public function about()
    {
        return view('about');
    }
     //
    public function deals()
    {
        return view('deals');
    }

    //
    public function menu()
    {
        return view('menu');
    }

    public function breakfast()
    {
        return view('breakfast');
    }

    public function burgers()
    {
        return view('burgers');
    }

    public function sandwich()
    {
        return view('sandwich');
    }

    public function happy()
    {
        return view('happy');
    }

        public function combo()
    {
        return view('combo');
    }

    public function dessert()
    {
        return view('dessert');
    }

    public function salade()
    {
        return view('salade');
    }

    public function SausageMcGriddles()
    {
        return view('SausageMcGriddles');
    }

    public function EggMcMuffin()
    {
        return view('EggMcMuffin');
    }

    public function SausageMcMuffin()
    {
        return view('SausageMcMuffin');
    }

    public function SausageBiscuit()
    {
        return view('SausageBiscuit');
    }

    public function Hotcakes()
    {
        return view('Hotcakes');
    }

    public function DoubleCheeseburger()
    {
        return view('DoubleCheeseburger');
    }
    public function Bacon()
    {
        return view('Bacon');
    }

    public function CheeseDeluxe()
    {
        return view('CheeseDeluxe');
    }
    public function Cheese()
    {
        return view('Cheese');
    }

    public function Cheeseburger()
    {
        return view('Cheeseburger');
    }
    public function BigBurger()
    {
        return view('BigBurger');
    }
    public function CheeseburgerCombo()
    {
        return view('CheeseburgerCombo');
    }
    public function ArtisanMeal()
    {
        return view('ArtisanMeal');
    }
    public function ButtermilkMeal()
    {
        return view('ButtermilkMeal');
    }
    public function QuarterMeal()
    {
        return view('QuarterMeal');
    }
    public function ChocolateChip()
    {
        return view('ChocolateChip');
    }
    public function BakedApple()
    {
        return view('BakedApple');
    }
    public function Strawberry()
    {
        return view('Strawberry');
    }
    public function HotFudge()
    {
        return view('HotFudge');
    }
    public function SideSalad()
    {
        return view('SideSalad');
    }
    public function BaconGrilled()
    {
        return view('BaconGrilled');
    }
    public function BaconSalad()
    {
        return view('BaconSalad');
    }

    public function Southwest()
    {
        return view('Southwest');
    }

    public function VanillaShake()
    {
        return view('VanillaShake');
    }

    public function ChocolateShake()
    {
        return view('ChocolateShake');
    }

    public function StrawberryShake()
    {
        return view('StrawberryShake');
    }
    public function HotChocolate()
    {
        return view('HotChocolate');
    }

    public function SmallSprite()
    {
        return view('SmallSprite');
    }
    public function MindPink()
    {
        return view('MindPink');
    }
    public function rtable(Request $request,$x)
    {
        $d=$request->date;
        $m=DB::select('select * from tables where tables.id not in (select reservations.idtable from reservations where date=? )',[$d]);
        return view('rtable',compact('x','m','d'));
    }
    public function reserve($x,$d,$t)
    {
        return view('reserve',compact('x','d','t'));
    }

    public function reserveadd(Request $request)
    {
        $x = new Reservation();
        $x->email =$request->email;
        $x->idtable =$request->idtable;
        $x->date =$request->date;
        $x->time =$request->time;
        $x->save();
        if (app()->getLocale()=='ar')
        {
            return redirect('http://127.0.0.1:8000/ar/home/main');
        }
        else{
            return redirect('http://127.0.0.1:8000/en/home/main');
        }
    }
    public function evaluation($x,$m)
    {
        return view('evaluation',compact('x','m'));
    }

    public function evaluationadd(Request $request)
    {
        $x = new Evaluation();
        $x->email =$request->email;
        $x->idmeal =$request->idmeal;
        $x->evaluation =$request->evaluation;
        $x->save();
        if (app()->getLocale()=='ar')
        {
            return redirect('http://127.0.0.1:8000/ar/home/main');
        }
        else{
            return redirect('http://127.0.0.1:8000/en/home/main');
        }
    }





}

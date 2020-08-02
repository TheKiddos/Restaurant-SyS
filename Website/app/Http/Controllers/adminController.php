<?php

namespace App\Http\Controllers;

use App\Comment;
use App\Item;
use App\Order;
use App\Rating;
use App\Reservation;
use App\Type;
use App\Userlater;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class adminController extends Controller
{
    //

    public function home()
    {
        return view('home');
    }

    public function siteadd(Request $request)
    {
        $validatedData = $request->validate([
            'city' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:10'],
            'region' => ['regex:/^([a-zA-Z]+)(\s[a-zA-Z]+)*$/','max:50'],
        ]);

        $user_site = Userlater::where('email',Auth::user()->email)->first();
        if ( !$user_site ) {
            $user_site = new Userlater();
            $user_site->email = Auth::user()->email;
            $user_site->city = $request->city;
            $user_site->region = $request->region;
            $user_site->save();
        }
        return redirect('/home/main');
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
        return redirect('/home/main');
    }

    public function calculate()
    {
        return view('calculate')->with('items', Item::all());
    }

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

        return redirect('/home/main');
    }

    public function about()
    {
        return view('about');
    }

    public function deals()
    {
        return view('deals');
    }

    public function menu()
    {
        return view('menu')->with('types', Type::all())->with('type', null)->with('items', Item::all());
    }

    public function showItemsWithType($type)
    {
        $items = DB::select( "select * from items where '".$type."' in (select types_name from items_types where items_types.item_id = items.id)");
        return view('menu')->with('types', Type::all())->with('type', $type)->with('items', $items);
    }

    public function show_free_tables(Request $request)
    {
        $user = Auth::user();
        $reservation_date = $request['date'];
        $free_tables_on_date = DB::select('select * from tables where tables.id not in (select reservations.table_id from reservations where date=? )',[$reservation_date]);
        return view('tables')->with('free_tables', $free_tables_on_date)->with('user', $user)->with('reservation_date', $reservation_date);
    }

    public function reserve($table_id, $reservation_date)
    {
        if ( $reservation_date < date('yy-m-d'))
            return redirect('/home/main');
        return view('reserve',compact('table_id','reservation_date' ));
    }

    // TODO fix timezone issues with date and time
    public function reserveadd(Request $request)
    {
        $validatedData = $request->validate([
            'date' => ['after_or_equal:today'],
        ]);

        $reservation_date = $request['date'];
        if ( $reservation_date == date("yy-m-d") ) {
            $validatedData = $request->validate([
                'time' => ['after:now'],
            ]);
        }

        $reservation = new Reservation();
        $reservation->user_id = Auth::user()->id;
        $reservation->table_id =$request['table_id'];
        $reservation->date =$request['date'];
        $reservation->service_time =$request['time'];
        $reservation->active = false;
        $reservation->reservation_fee = 10.0;
        $order = new Order();
        $order->save();
        $reservation->order_id = $order->id;
        $reservation->save();
        return redirect('/home/main');
    }

    public function evaluation($item_id)
    {
        return view('evaluation',compact('item_id'));
    }

    public function evaluationadd(Request $request)
    {
        DB::delete('delete from ratings where user_id = ? and item_id = ?',[Auth::user()->id, $request['item_id']]);
        $rating = new Rating();
        $rating['user_id'] = Auth::user()->id;
        $rating['item_id'] = $request['item_id'];
        $rating['rating'] = $request['rating'];
        $rating->save();
        return redirect('/home/main');
    }

    public function order()
    {
        $user = Auth::user();
        $site=DB::select('select * from userlaters where email=? ',[$user->email]);
        $items = Item::all();
        return view('orderpage',compact('items','site','user'));
    }

    public function orderadd(Request $request)
    {
        /*
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
        $x->save();*/
        return redirect('/home/main');
    }

    public function recommendForUser() {
        $user_id = Auth::user()[ 'id' ];

        $recommendedItems = DB::select( 'SELECT item_id from restaurant.recommendations WHERE user_id = ?', [ $user_id ] );
        return view( 'recommendations' )->with( 'items', $recommendedItems );
    }
}

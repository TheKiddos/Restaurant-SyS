<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Providers\RouteServiceProvider;
use App\User;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use Illuminate\Support\Facades\Auth;
use Laravel\Socialite\Facades\Socialite;

class LoginController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles authenticating users for the application and
    | redirecting them to your home screen. The controller uses a trait
    | to conveniently provide its functionality to your applications.
    |
    */

    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = RouteServiceProvider::MAIN;

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest')->except('logout');
    }
    /**
     * Redirect the user to the GitHub authentication page.
     *
     * @return \Illuminate\Http\Response
     */
    public function redirectToProvider()
    {
        return Socialite::driver('facebook')->stateless()->redirect();
    }
    /**
     * Obtain the user information from GitHub.
     *
     * @return string
     */

    public function handleProviderCallback()
    {
        try {

            $user = Socialite::driver('facebook')->stateless()->user();
            //add user
            $x=User::where('email',$user->email)->first();
            if($x){
                Auth::login($x);
            }else{
                $y=new User();
                $y->name=$user->name;
                $y->email=$user->email;
                $y->password=bcrypt($user->id);
                $y->save();
                Auth::login($user);

            }
            return redirect()->to('home');

        }
        catch (\TypeError $exception){
            return redirect()->to('login');
        }





    }
}

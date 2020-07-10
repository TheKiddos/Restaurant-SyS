<?php

use Illuminate\Support\Facades\Route;
use Mcamara\LaravelLocalization\Facades\LaravelLocalization;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::group(
    [
        'prefix' => LaravelLocalization::setLocale(),
        'middleware' => [ 'localeSessionRedirect', 'localizationRedirect', 'localeViewPath' ]
    ], function(){

    Route::get('/', function () {
        return view('welcome');
    });

    Auth::routes();

    Route::get('/home', 'HomeController@index')->name('home');
    Route::post('/home', 'adminController@siteadd');
    Route::get('login/facebook', 'Auth\LoginController@redirectToProvider');
    Route::get('login/facebook/callback', 'Auth\LoginController@handleProviderCallback');
    Route::get('/home/content', 'adminController@content');
    Route::post('/home/content', 'adminController@contentadd');
    Route::get('/home/main', 'adminController@home');
    Route::get('/home/main/menu', 'adminController@menu');
    Route::get('/home/main/menu/SausageMcGriddles', 'adminController@SausageMcGriddles');
    Route::get('/home/main/menu/EggMcMuffin', 'adminController@EggMcMuffin');
    Route::get('/home/main/menu/SausageMcMuffin', 'adminController@SausageMcMuffin');
    Route::get('/home/main/menu/SausageBiscuit', 'adminController@SausageBiscuit');
    Route::get('/home/main/menu/Hotcakes', 'adminController@Hotcakes');
    Route::get('/home/main/menu/DoubleCheeseburger', 'adminController@DoubleCheeseburger');
    Route::get('/home/main/menu/Bacon', 'adminController@Bacon');
    Route::get('/home/main/menu/CheeseDeluxe', 'adminController@CheeseDeluxe');
    Route::get('/home/main/menu/Cheese', 'adminController@Cheese');
    Route::get('/home/main/menu/Cheeseburger', 'adminController@Cheeseburger');
    Route::get('/home/main/menu/BigBurger', 'adminController@BigBurger');
    Route::get('/home/main/menu/Strawberry', 'adminController@Strawberry');
    Route::get('/home/main/menu/HotChocolate', 'adminController@HotChocolate');
    Route::get('/home/main/menu/SmallSprite', 'adminController@SmallSprite');
    Route::get('/home/main/menu/MindPink', 'adminController@MindPink');
    Route::get('/home/main/menu/HotFudge', 'adminController@HotFudge');
    Route::get('/home/main/menu/SideSalad', 'adminController@SideSalad');
    Route::get('/home/main/menu/BaconGrilled ', 'adminController@BaconGrilled');
    Route::get('/home/main/menu/BaconSalad', 'adminController@BaconSalad');
    Route::get('/home/main/menu/Southwest', 'adminController@Southwest');
    Route::get('/home/main/menu/VanillaShake', 'adminController@VanillaShake');
    Route::get('/home/main/menu/ChocolateShake', 'adminController@ChocolateShake');
    Route::get('/home/main/menu/StrawberryShake', 'adminController@StrawberryShake');
    Route::get('/home/main/menu/HotChocolate', 'adminController@HotChocolate');
    Route::get('/home/main/menu/CheeseburgerCombo', 'adminController@CheeseburgerCombo');
    Route::get('/home/main/menu/ArtisanMeal', 'adminController@ArtisanMeal');
    Route::get('/home/main/menu/ButtermilkMeal', 'adminController@ButtermilkMeal');
    Route::get('/home/main/menu/QuarterMeal', 'adminController@QuarterMeal');
    Route::get('/home/main/menu/ChocolateChip', 'adminController@ChocolateChip');
    Route::get('/home/main/menu/BakedApple', 'adminController@BakedApple');
    Route::get('/home/main/menu/breakfast', 'adminController@breakfast');
    Route::get('/home/main/menu/burgers', 'adminController@burgers');
    Route::get('/home/main/menu/sandwich', 'adminController@sandwich');
    Route::get('/home/main/menu/happy', 'adminController@happy');
    Route::get('/home/main/menu/combo', 'adminController@combo');
    Route::get('/home/main/menu/dessert', 'adminController@dessert');
    Route::get('/home/main/menu/salade', 'adminController@salade');
    Route::get('/home/main/calcolate', 'adminController@calcolate');
    Route::get('/home/main/comment', 'adminController@comment');
    Route::post('/home/main/comment', 'adminController@commentadd');
    Route::get('/home/main/orderpage/{id}', 'adminController@order');
    Route::post('/home/main/orderpage/{id}', 'adminController@orderadd');
    Route::get('/home/sitechange', 'adminController@sitechange');
    Route::post('/home/sitechange', 'adminController@sitechangepsot');
    Route::get('/home/main/about', 'adminController@about');
    Route::get('/home/main/deals', 'adminController@deals');
    Route::get('/home/main/rtable/{id}', 'adminController@rtable');
    Route::post('/home/main/rtable/{id}', 'adminController@rtable');
    Route::get('/home/main/rtable/reserve/{id}/{d}/{t}', 'adminController@reserve');
    Route::post('/home/main/rtable/reserve/{id}/{d}/{t}', 'adminController@reserveadd');
    Route::get('/home/main/orderpage/{id}/{m}', 'adminController@evaluation');
    Route::post('/home/main/orderpage/{id}/{m}', 'adminController@evaluationadd');

});


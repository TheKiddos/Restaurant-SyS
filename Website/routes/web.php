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
// TODO fix images in items
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
    Route::get('/home/main', 'adminController@home');
    Route::get('/home/main/menu', 'adminController@menu');
    Route::get('home/main/menu/{type}', 'adminController@showItemsWithType');
    Route::get('/home/main/calculate', 'adminController@calculate');
    Route::get('/home/main/comment', 'adminController@comment');
    Route::post('/home/main/comment', 'adminController@commentadd');
    Route::get('/home/main/about', 'adminController@about');
    Route::get('/home/main/deals', 'adminController@deals');
    Route::get('/home/sitechange', 'adminController@sitechange');
    Route::post('/home/sitechange', 'adminController@sitechangepsot');
    Route::get('/home/main/tables/', 'adminController@show_free_tables');
    Route::post('/home/main/tables/', 'adminController@show_free_tables');
    Route::get('/home/main/tables/reserve/{table_id}/{reservation_date}/', 'adminController@reserve');
    Route::post('/home/main/tables/reserve/{table_id}/{reservation_date}/', 'adminController@reserveadd');
    Route::get('/home/main/orderpage/', 'adminController@order');
    Route::get('/home/main/rate/{item_id}', 'adminController@evaluation');
    Route::post('/home/main/rate/{item_id}', 'adminController@evaluationadd');

    Route::post('/home/main/orderpage/', 'adminController@orderadd');
});


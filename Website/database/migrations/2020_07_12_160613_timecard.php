<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class Timecard extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('timecard', function (Blueprint $table) {
            $table->date('date');
            $table->bigInteger('hourly_classification_id');
            $table->time('time_worked');
            $table->foreign('hourly_classification_id')->references('id')->on('hourly_classification');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('timecard');
    }
}

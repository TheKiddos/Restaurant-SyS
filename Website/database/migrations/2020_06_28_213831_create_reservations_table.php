<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateReservationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('reservations', function (Blueprint $table) {
            $table->bigInteger('user_id');
            $table->foreign('user_id')->references('id')->on('users');
            $table->date('date')->nullable(false);
            $table->time('service_time');
            $table->boolean('active')->nullable(false);
            $table->double('reservation_fee')->nullable(false);
            $table->bigInteger('order_id');
            $table->foreign('order_id')->references('id')->on('orders');
            $table->bigInteger('table_id');
            $table->foreign('table_id')->references('id')->on('tables');
            $table->primary(['user_id', 'date']);
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
        Schema::dropIfExists('reservations');
    }
}

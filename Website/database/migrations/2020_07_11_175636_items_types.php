<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class ItemsTypes extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('items_types', function (Blueprint $table) {
            $table->bigInteger('item_id');
            $table->foreign('item_id')->references('id')->on('items');
            $table->string('types_name')->nullable(false);
            $table->primary(['item_id', 'types_name']);
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
        Schema::dropIfExists('items_types');
    }
}

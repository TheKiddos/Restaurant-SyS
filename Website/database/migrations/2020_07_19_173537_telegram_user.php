<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class TelegramUser extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('telegram_users', function (Blueprint $table) {
            $table->integer("id")->primary();
            $table->string('email')->nullable();
            $table->string('last_command')->nullable();
            $table->integer( 'verification_code')->nullable();
            $table->boolean( 'is_verified')->default(false);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('telegram_users');
    }
}

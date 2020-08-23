@extends('home_general')

@section('content')
    @if(app()->getLocale()=='en')
    <div class="container-fluid box">
        <h1>Content Us:</h1>
        <form action="" class="was-validated"  method="POST">
            @csrf
            <div class="form-group autocomplete"  >
                <label for="name">Name:</label>
                <input id="myInput" type="text" class=" @error('name') is-invalid @enderror form-control" name="name" placeholder="inter name" required>
                @error('name')
                <div class="mess">{{ $message }}</div>
                @enderror
                <div class="valid-feedback">Valid.</div>
                <div class="invalid-feedback">Please fill out this field.</div>
            </div>
            <br>
            <div class="form-group autocomplete">
                <label for="subject">Subject:</label>
                <input  type="text" class=" @error('subject') is-invalid @enderror form-control"  placeholder="Enter subject" name="subject" required>
                @error('subject')
                <div class="mess">{{ $message }}</div>
                @enderror
                <div class="valid-feedback">Valid.</div>
                <div class="invalid-feedback">Please fill out this field.</div>
            </div>
            <br>

            <button type="submit" class="btn btn-warning">Sent message</button>

        </form>
    </div>
@else
        <div class="container-fluid box">
            <h1>توصل معنا</h1>
            <form action="" class="was-validated"  method="POST">
                @csrf
                <div class="form-group autocomplete"  >
                    <label for="name">الاسم</label>
                    <input id="myInput" type="text" class=" @error('name') is-invalid @enderror form-control" name="name" placeholder="ادخل الاسم" required>

                    @error('name')
                    <div class="mess">{{ $message }}</div>
                    @enderror
                    <div class="valid-feedback">مملوء</div>
                    <div class="invalid-feedback">رجاءا املا الحقل</div>
                </div>
                <br>
                <div class="form-group autocomplete">
                    <label for="subject">الموضوع</label>
                    <input  type="text" class=" @error('subject') is-invalid @enderror form-control"  placeholder="ادخل الموضوع" name="subject" required>
                    @error('subject')
                    <div class="mess">{{ $message }}</div>
                    @enderror
                    <div class="valid-feedback">مملوء</div>
                    <div class="invalid-feedback">رجاءا املا الحقل</div>
                </div>
                <br>

                <button type="submit" class="btn btn-warning">ارسل الرسالة</button>

            </form>
        </div>

@endif

@endsection

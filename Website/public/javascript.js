$(document).ready(function () {
    //function add
    $('.add').on('click',function (e) {
        e.preventDefault();
        var name= $(this).data('name');
        var id= $(this).data('id');
        var price= $(this).data('price');
        var calories= $(this).data('calories');
        var fat= $(this).data('fat');
        var brotien= $(this).data('brotien');
        var kerbohedrat= $(this).data('kerbohedrat');
        $(this).removeClass('btn-primary').addClass('btn-default disabled');

        var html=`<tr>
                  <td>${name}</td>
                  <input type="hidden" name="name[]" value="${name}">
                 <td> <input type="number" name="quntites[]"  data-kerbohedrat="${kerbohedrat}"  data-brotien="${brotien}" data-fat="${fat}" data-calories="${calories}" data-price="${price}" class="input" min="1" ></td>
                  <td class="priceT ">${price}</td>
                    <td class="calories ">${calories}</td>
                    <td class="fat ">${fat}</td>
                    <td class="brotien ">${brotien}</td>
                    <td class=" kerbohedrat">${kerbohedrat}</td>
                   <input type="hidden" name="price[]" value="${price}">
                   <input type="hidden" class="totall" name="total" value="0">
                  <td><button class="btn btn-danger btn-sm del" data-id="${id}" >-</button></td>
                  </tr>`;
        $('.addt').append(html);
        calculate();
//function remove
        $('.del').click(function (e) {
            e.preventDefault();
            var id= $(this).data('id');
            $('#'+id).removeClass('btn-default disabled').addClass('btn-primary');
            $(this).closest("tr").remove();
            calculate();

        });
//function change
        $('.input').change(function () {
            var i=parseInt($(this).val());
            var p= $(this).data('price');
            $(this).closest('tr').find('.priceT').html(p*i);
            calculate();

        });
          $('.input').change(function () {
              var i = parseInt($(this).val());
              var c = $(this).data('calories');
              $(this).closest('tr').find('.calories').html(c * i);
             calculate();
          });

        $('.input').change(function () {
            var i = parseInt($(this).val());
            var f = $(this).data('fat');
            $(this).closest('tr').find('.fat').html(f * i);
            calculate();
        });

        $('.input').change(function () {
            var i = parseInt($(this).val());
            var b= $(this).data('brotien');
            $(this).closest('tr').find('.brotien').html(b * i);
            calculate();
        });

        $('.input').change(function () {
            var i = parseInt($(this).val());
            var k= $(this).data('kerbohedrat');
            $(this).closest('tr').find('.kerbohedrat').html(k* i);
            calculate();
        });

    });
});
function calculate() {
    var p=0;
    $('.addt .priceT').each(function () {
        p=p+parseInt($(this).html());

    });
    var c=0;
    $('.addt .calories').each(function () {
        c=c+parseInt($(this).html());

    });
    var f=0;
    $('.addt .fat').each(function () {
        f=f+parseInt($(this).html());

    });
    var b=0;
    $('.addt .brotien').each(function () {
        b=b+parseInt($(this).html());

    });
    var k=0;
    $('.addt .kerbohedrat').each(function () {
        k=k+parseInt($(this).html());

    });
        $('.totalprice').html(p);
   $('.totalcalories').html(c);
    $('.totalfat').html(f);
    $('.totalbrotien').html(b);
    $('.totalkerbohedrat').html(k);
    $('.totall').val(p);
}

$(document).ready(function () {
    calculate();
    //function add
    $('.add').on('click',function (e) {
        e.preventDefault();
        var name= $(this).data('name');
        var id= $(this).data('id');
        var price= $(this).data('price');
        $(this).removeClass('btn-primary').addClass('btn-default disabled');

        var html=`<tr>
                 <td>${id}</td>
                 <input type="hidden" name="id[]" value="${id}">
                  <td>${name}</td>
                  <input type="hidden" name="name[]" value="${name}">
                 <td> <input type="number" name="quntites[]" data-price="${price}" class="input" min="1" ></td>
                  <td class="priceT ">${price}</td>
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
    });
});
function calculate() {
    var p=0;
    $('.addt .priceT').each(function () {
        p=p+parseInt($(this).html());

    });
    if (p<=0){
        $('.but').removeClass('btn-primary').addClass('btn-default hidden');

    }else{

        $('.but').removeClass('btn-default hidden').addClass('btn-primary ');

    }
    $('.total').html(p);
    $('.totall').val(p);
}

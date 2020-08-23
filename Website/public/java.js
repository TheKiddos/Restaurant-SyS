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

$(document).scroll(function () {
    pageScrolledDown() ? shrinkNavBar() : enlargeNavBar();
})

function pageScrolledDown() {
    return document.body.scrollTop > 80 || document.documentElement.scrollTop > 80;
}

function shrinkNavBar() {
    setNavBarStyle("3px 0px", "#343a40", "15px", "32px")

}

function enlargeNavBar() {
    setNavBarStyle("8px 5px", "rgba(52, 58, 64, 0.7)", "19px", "64px");
}

function setNavBarStyle(padding, color, titleFontSize, logoSize) {
    document.getElementById("navbar").style.padding = padding;
    //document.getElementById("navbar").style.backgroundColor = color;
    document.getElementById("header").style.fontSize = titleFontSize;
    document.getElementById("logo").style.width = logoSize;
    document.getElementById("logo").style.height = logoSize;
}

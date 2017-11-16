function addBook() {
    var form = new FormData();
    form.append("isbn", $('#isbn').val());
    var area = $('#select1 option:selected').text();
    var room = $('#select2 option:selected').text();
    var shelf = $('#select3 option:selected').text();
    var layer = $('#select4 option:selected').text();

    var loaction = area+'-'+room+'-'+shelf+'-'+layer;

    var id_array=new Array();
    $('input[name="type"]:checked').each(function(){
        id_array.push($(this).val());//向数组中添加元素
    });
    var type=id_array.join(',');//将数组元素连接起来以构建一个字符串


    var bstatus = $("input[name='bstatus']:checked").val();

    form.append("bStatus",bstatus);
    form.append("type",type);

    form.append("location",loaction);
    $.ajax({
         url: '/admin/addBook',
         type: "post",
         data: form,
         processData: false,
         contentType: false,
         dataType:"json",
         success: function (data) {
             if (data['status']==1){
                 alert("Add OK");
                 window.location.href="/admin/toAddBook";
             }
             else {
                 alert("Add error!");
             }


         },
         error: function () {
             alert("Add error!");
         }
    });
    return;

}
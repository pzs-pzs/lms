function addBook() {
    var form = new FormData();
    form.append("isbn", $('#isbn').val());
    var area = $('#select1 option:selected').text();
    var room = $('#select2 option:selected').text();
    var shelf = $('#select3 option:selected').text();
    var layer = $('#select4 option:selected').text();
    var loaction = area+'-'+room+'-'+shelf+'-'+layer;
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
             }
             else {
                 alert("add error!");
             }


         },
         error: function () {
             alert("add error!");
         }
    });
    return;

}
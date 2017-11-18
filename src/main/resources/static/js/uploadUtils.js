function addBook() {
    var form = new FormData();
    form.append("isbn", $('#isbn').val());
    var area = $('#select1 option:selected').text();
    var room = $('#select2 option:selected').text();
    var shelf = $('#select3 option:selected').text();
    var layer = $('#select4 option:selected').text();

    var location = area+'-'+room+'-'+shelf+'-'+layer;

    var id_array=new Array();
    $('input[name="type"]:checked').each(function(){
        id_array.push($(this).val());//向数组中添加元素
    });
    var type=id_array.join(',');//将数组元素连接起来以构建一个字符串


    var bstatus = $("input[name='bstatus']:checked").val();

    form.append("bStatus",bstatus);
    form.append("type",type);

    form.append("location",location);
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

var inputEditor = $("input.datagrid-editable-input");//單元格input元素

//input生成后，自動獲取聚焦
inputEditor.focus();

//input失去焦點，撤銷編輯操作，恢復原來的值，不保存
inputEditor.bind("blur",function(evt){
    reject();
});

//按鍵按下，如果是回車鍵，則保存編輯內容
inputEditor.bind("keypress",function(evt){
    var keyCode = evt.keyCode;
    if(keyCode == 13){  //回车事件
//                alert(evt.keyCode);
        $(this).unbind("blur");
        accept();

    }
});

/*
* input獲取焦點后，綁定blur事件
* 在input已獲取焦點的前提下，點擊文檔任何位置，取消編輯內容
* 如果是回車鍵按下，則取消綁定blur，保存編輯內容
* */

inputEditor.bind("focus",function(evt){

    $(this).bind("blur",function(evt){
        reject();
    });

    $(document).bind("click",function(){
        reject();
    });

    $(this).bind("keypress",function(){
        var keyCode = evt.keyCode;
        if(keyCode == 13){  //回车事件
//                alert(evt.keyCode);
            $(this).unbind("blur");
            accept();
        }
    });

});

function OnTextChanged() {

    if (event.keyCode == 13) {//判断是否为回车键，Event是window对象的一个属性，是全局的。
        event.keyCode = 0;//屏蔽回车键
        event.returnValue = false;

    }
}

$(document).ready(function(){

    $('#isbn').focus();
})
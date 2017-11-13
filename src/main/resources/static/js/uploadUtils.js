
function addBook() {
    var flag=true;
        flag=false;
        var imgs = [];
        var booktype = [];
        $("img").filter('.upload').each(
            function (key,value) {
                imgs[key]=$(value)[0].src;
            }
        );
    var elements=$("input[type='checkbox']:checked");
    var j=0;
    for(var i=0;i<elements.length;i++){
        var element=elements[i];
          booktype[j]=element.value;
          j++;
    }
        var form = new FormData();
        form.append("name",$("#name").val());
        form.append("authorName",$("#authorName").val());
        form.append("isbn",$("#isbn").val());
        form.append("remarks",$("#remark").val());
        form.append("publishTime",$("#publishTime").val())
        form.append("imgs",imgs);
        form.append("quantity",$("#quantity").val());
        form.append("location",$("#location").val());
        form.append("booktype",booktype);
        $.ajax({
            type:'POST',
            url:'/admin/addBook',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                window.location.href="toAddBook";
            },error:function(){
                alert("上传失败");
            }
        })
 
}

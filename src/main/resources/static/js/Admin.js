function addAdmin() {
    var form = new FormData();
    form.append("username",$("#username").val());
    form.append("password",$("#password").val());
    form.append("email",$("#email").val());
    form.append("num",$("#num").val());
    $.ajax({
            type:'POST',
            url:'/admin/addAdmin',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                //alert(result.message);
                window.setTimeout("window.location.href='/admin/toAddAdmin'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Add administrator failed");
                toastr.options.timeOut = 500;
                //alert("Add administrator failed");
            }




        }
    )
}

function addStudent() {



    var form = new FormData();
    form.append("username",$("#username").val());
    form.append("password",$("#password").val());
    form.append("email",$("#email").val());
    form.append("num",$("#num").val());




    $.ajax({
            type:'POST',
            url:'/admin/addStudent',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toAddStudent'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Add administrator failed");
                toastr.options.timeOut = 500;
            }




        }
    )
}

function borrowBook() {
    var form = new FormData();
    var userId =$("#userId").val();
    form.append("userId",$("#userId").val());
    form.append("bookId",$("#bookId").val());
    $.ajax({
            type:'POST',
            url:'/admin/BorrowBook',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                if(result.message=="You should pay arrearage"){

                    window.location.href="/admin/toArrearagePay?userId="+userId;

                }else {
                    toastr.options.positionClass ='toast-top-center';
                    toastr.success(result.message);
                    toastr.options.timeOut = 500;
                    window.setTimeout("window.location.href='/admin/toBorrowBook'",1000);


                }

            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Borrow Book failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function ReturnBook() {
    var form = new FormData();
    var bookId =$("#bookId").val();
    form.append("bookId",$("#bookId").val());
    $.ajax({
            type:'POST',
            url:'/admin/ReturnBook',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                if(result.message=="You should pay arrearage"){
                    window.location.href="/admin/toArrearagePay?bookId="+bookId;

                }else {
                    toastr.options.positionClass ='toast-top-center';
                    toastr.success(result.message);
                    toastr.options.timeOut = 500;
                    window.setTimeout("window.location.href='/admin/toBorrowBook'",1000);


                }
            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("ReturnBook Book failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function payMoney(userId) {
    var fine =$('#arrearage').text();
    var con = confirm("Would you pay ¥"+fine+"?");
    var form = new FormData();
    form.append("userId",userId);
    if(con==true){
        $.ajax({
                type:'POST',
                url:'/admin/PayMoney',
                data:form,
                dataType:'json',
                traditional: true,
                processData: false,
                contentType: false,
                success:function (result) {
                    toastr.options.positionClass ='toast-top-center';
                    toastr.success(result.message);
                    toastr.options.timeOut = 500;
                    window.setTimeout("window.location.href='/admin/toBorrowBook'",1000);


                },error:function(){
                    toastr.options.positionClass ='toast-top-center';
                    toastr.error("Pay money failed");
                    toastr.options.timeOut = 500;
                    //alert();
                }

            }
        )
    }
}

function modifyAdmin(i) {
    var form = new FormData();
    form.append("id",$("#id"+i).val());
    form.append("email",$("#email"+i).val());
    form.append("phone",$("#phone"+i).val());



    var email = $("#email"+i);
    var phone = $("#phone"+i);

    var isTrue = true;

    reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    reg2=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

    if(email.val()===""){
        email.addClass("errorC");
        email.next().html("Please input your email");
        email.next().css("visibility","visible");
        isTrue = false;

    }else if(email.val().match(reg) === null){
        email.addClass("errorC");
        email.next().html("The format of email is wrong!");
        email.next().css("visibility","visible");
        isTrue = false;
    }

    if(phone.val()===""){
        phone.addClass("errorC");
        phone.next().html("Please input your phone num!");
        phone.next().css("visibility","visible");
        return false;
    }else if(phone.val().length<11)
    {
        phone.addClass("errorC");
        phone.next().html("The length of phone num is wrong!");
        phone.next().css("visibility","visible");
        isTrue = false;
    } else if(phone.val().match(reg2) === null){
        phone.addClass("errorC");
        phone.next().html("The format of phone num is wrong!");
        phone.next().css("visibility","visible");
        isTrue = false;
    }


    if(isTrue===false) return isTrue;


    $.ajax({
            type:'POST',
            url:'/admin/modifyAdmin',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toManageAdmin'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Modify AdminInfo failed");
                toastr.options.timeOut = 500;

            }

        }
    )
}

function modifyUser(i) {
    var form = new FormData();
    form.append("id",$("#id"+i).val());
    form.append("email",$("#email"+i).val());
    form.append("phone",$("#phone"+i).val());

    var email = $("#email"+i);
    var phone = $("#phone"+i);

    var isTrue = true;

    reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    reg2=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

    if(email.val()===""){
        email.addClass("errorC");
        email.next().html("Please input your email");
        email.next().css("visibility","visible");
        isTrue = false;

    }else if(email.val().match(reg) === null){
        email.addClass("errorC");
        email.next().html("The format of email is wrong!");
        email.next().css("visibility","visible");
        isTrue = false;
    }

    if(phone.val()===""){
        phone.addClass("errorC");
        phone.next().html("Please input your phone num!");
        phone.next().css("visibility","visible");
        return false;
    }else if(phone.val().length<11)
    {
        phone.addClass("errorC");
        phone.next().html("The length of phone num is wrong!");
        phone.next().css("visibility","visible");
        isTrue = false;
    } else if(phone.val().match(reg2) === null){
        phone.addClass("errorC");
        phone.next().html("The format of phone num is wrong!");
        phone.next().css("visibility","visible");
        isTrue = false;
    }


    if(isTrue===false) return isTrue;
    $.ajax({
            type:'POST',
            url:'/admin/modifyAdmin',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toManageUser'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Modify AdminInfo failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function modifySelfInfo() {
    var form = new FormData();
    form.append("email",$("#email").val());
    form.append("phone",$("#phone").val());
    $.ajax({
            type:'POST',
            url:'/admin/modifySelfInfo',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/adminInfo'",1000);

            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Modify Info failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function deleteAdmin(id) {
    var form = new FormData();
    form.append("id",id);
    var con = confirm("Are you sure delete this?");
    if(con==false){
        return;
    }
    $.ajax({
            type:'POST',
            url:'/admin/deleteUser',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toManageAdmin'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Deleted failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function startAdmin(id) {
    var form = new FormData();
    form.append("id",id);
    var con = confirm("Are you sure start this?");
    if(con==false){
        return;
    }
    $.ajax({
            type:'POST',
            url:'/admin/startUser',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toManageAdmin'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Start failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function deleteUser(id) {
    var form = new FormData();
    form.append("id",id);
    var con = confirm("Are you sure delete this?");
    if(con==false){
        return;
    }
    $.ajax({
            type:'POST',
            url:'/admin/deleteUser',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/toManageUser'",1000);

            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Deleted failed");
                toastr.options.timeOut = 500;

            }

        }
    )
}

function AllBookInfo(name) {
    var form = new FormData();
    form.append("bookname",name);
    $.ajax({
            type:'POST',
            url:'/admin/modifySelfInfo',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;
                window.setTimeout("window.location.href='/admin/adminInfo'",1000);


            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Modify Info failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}

function appendBookInfo(id,bookname) {
    var form = new FormData();
    form.append("bookname",bookname);
    $.ajax({
            type:'POST',
            url:'/admin/SpecifyBookList',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (bookList) {

                $("#table"+id+" tr:not(:first)").html("");
                for(var i = 0; i < bookList.specifyBookList.length; i++){
                    var a = bookList.specifyBookList[i];
                    var borrowerrrr = bookList.borrower[i];
                        $("#table"+id+" tbody").append('<tr>\
                            <td>'+a.id+'</td>\
                            <td>'+a.name+'</td>\
                            <td>'+a.isbn+'</td>\
                            <td>'+a.authorName+'</td>\
                            <td>'+a.status+'</td>\
                            <td>'+borrowerrrr+'</td>\
                            <td>'+'<a id='+'\'mytda\' class='+'\'btn btn-primary\' onclick='+'\'deleteBook('+a.id+')\'>\
                            <span class='+'\'glyphicon  glyphicon-trash\'></span>\
                            </a></tr>');
              }
                $("#myModel"+id).modal("show");
            }
            ,error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Modify Info failed");
                toastr.options.timeOut = 500;

            }

        }
    )
}

function deleteBook(id) {
    var form = new FormData();
    form.append("bookId",id);
    var con = confirm("Are you sure delete this?");
    if(con==false){
        return;
    }
    $.ajax({
            type:'POST',
            url:'/admin/Deletebook',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {

                toastr.options.positionClass ='toast-top-center';
                toastr.success(result.message);
                toastr.options.timeOut = 500;

                window.setTimeout("window.location.href='/admin/bookStatusList'",1000);

            },error:function(){
                toastr.options.positionClass ='toast-top-center';
                toastr.error("Delete book failed");
                toastr.options.timeOut = 500;
            }

        }
    )
}


function checkemail(id) {
    /*密码输入框失去焦点*/
    var email = $("#email"+id);


        reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

        if(email.val() === "email"||email.val()===""){
            email.addClass("errorC");
            email.next().html("Please input your email!");
            email.next().css("visibility","visible");

        }else if(email.val().match(reg) === null){
            email.addClass("errorC");
            email.next().html("The format of email is wrong!");
            email.next().css("visibility","visible");
        }else
        {
            email.addClass("checkedN");
            email.removeClass("errorC");
            email.next().empty();
        }
    

}

function checkphone(id) {
    //手机号栏失去焦点
    var phone=$("#phone"+id);
        reg=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

        if( phone.val()===""|| phone.val()==="Please input your phone!")
        {
            phone.addClass("errorC");
            phone.next().html("Please input your phone num!");
            phone.next().css("visibility","visible");
        }
        else if(phone.val().length<11)
        {
            phone.addClass("errorC");
            phone.next().html("The length of phone num is wrong!");
            phone.next().css("visibility","visible");
        } else if(phone.val().match(reg) === null){
            phone.addClass("errorC");
            phone.next().html("The format of phone num is wrong!");
            phone.next().css("visibility","visible");
        } else
        {
            phone.addClass("checkedN");
            phone.removeClass("errorC");
            phone.next().empty();
        }

    
}
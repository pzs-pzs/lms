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
                alert(result.message);
                window.location.href="/admin/toAddAdmin";
            },error:function(){
                alert("Add administrator failed");
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
                alert(result.message);
                window.location.href="/admin/toAddStudent";
            },error:function(){
                alert("Add administrator failed");
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
                    var con = confirm("Would you pay arrearage?");
                    if(con==true){
                        alert("give me your money");
                        window.location.href="/admin/toArrearagePay?userId="+userId;
                    }
                }else {
                    alert(result.message);
                    window.location.href="/admin/toBorrowBook";
                }

            },error:function(){
                alert("Borrow Book failed");
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
                    var con = confirm("Would you pay arrearage?");
                    if(con==true){
                        alert("give me your money");
                        window.location.href="/admin/toArrearagePay?bookId="+bookId;
                    }
                }else {
                    alert(result.message);
                    window.location.href="/admin/toBorrowBook";
                }
            },error:function(){
                alert("ReturnBook Book failed");
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
                    alert(result.message);
                    window.location.href="/admin/toBorrowBook";
                },error:function(){
                    alert("Pay money failed");
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
    $.ajax({
            type:'POST',
            url:'/admin/modifyAdmin',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                alert(result.message);
                window.location.href="/admin/toManageAdmin";
            },error:function(){
                alert("Modify AdminInfo failed");
            }

        }
    )
}

function modifyUser(i) {
    var form = new FormData();
    form.append("id",$("#id"+i).val());
    form.append("email",$("#email"+i).val());
    form.append("phone",$("#phone"+i).val());
    $.ajax({
            type:'POST',
            url:'/admin/modifyAdmin',
            data:form,
            dataType:'json',
            traditional: true,
            processData: false,
            contentType: false,
            success:function (result) {
                alert(result.message);
                window.location.href="/admin/toManageUser";
            },error:function(){
                alert("Modify AdminInfo failed");
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
                alert(result.message);
                window.location.href="/admin/adminInfo";
            },error:function(){
                alert("Modify Info failed");
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
                alert(result.message);
                window.location.href="/admin/toManageAdmin";
            },error:function(){
                alert("Deleted failed");
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
                alert(result.message);
                window.location.href="/admin/toManageAdmin";
            },error:function(){
                alert("Start failed");
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
                alert(result.message);
                window.location.href="/admin/toManageUser";
            },error:function(){
                alert("Deleted failed");
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
                alert(result.message);
                window.location.href="/admin/adminInfo";
            },error:function(){
                alert("Modify Info failed");
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
                alert("Modify Info failed");
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
                alert(result.message);
                window.location.href="/admin/bookStatusList";
            },error:function(){
                alert("Delete book failed");
            }

        }
    )
}
/**
 * Created by FelixWang on 2017/11/19.
 */
function textFocus(el) {
    if (el.defaultValue == el.value) { el.value = ''; el.style.color = '#333'; }
}
function textBlur(el) {
    if (el.value == '') { el.value = el.defaultValue; el.style.color = '#999'; }
}

$(function(){
    //ISBN检查
    (function isbn() {

        $(".reg-box .isbn").blur(function () {
            if($(this).val()===""){
                $(this).addClass("errorC");
                $(this).next().html("Please input the ISBN of book!");
                $(this).next().css("visibility","visible");
            }else if($(this).val().length != 13&&$(this).val().length != 10){
                $(this).addClass("errorC");
                $(this).next().html("The ISBN isn't correct!");
                $(this).next().css("visibility","visible");
            }else {
                $(this).addClass("checkedN");
                $(this).removeClass("errorC");
                $(this).next().empty();
            }
        });
    })();


    //添加图书界面
    (function register(){


        //手机号栏失去焦点
        $(".reg-box .phone").blur(function(){

            var isHide = $("#confirmmm").hasClass("hide");

            if(!isHide){
                reg=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

                if( $(this).val()===""|| $(this).val()=="Please input your phone!")
                {
                    $(this).addClass("errorC");
                    $(this).next().html("Please input your phone num!");
                    $(this).next().css("visibility","visible");
                }
                else if($(this).val().length<11)
                {
                    $(this).addClass("errorC");
                    $(this).next().html("The length of phone num is wrong!");
                    $(this).next().css("visibility","visible");
                } else if($(this).val().match(reg) === null){
                    $(this).addClass("errorC");
                    $(this).next().html("The format of phone num is wrong!");
                    $(this).next().css("visibility","visible");
                } else
                {
                    $(this).addClass("checkedN");
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                }
            }

        });



    })();

    //--------------------------------------
    //登录页面的提示文字
    //账户输入框失去焦点
    (function login_validate(){
        $(".reg-box .account").blur(function(){

            if( $(this).val()===""|| $(this).val()=="account")
            {
                $(this).addClass("errorC");
                $(this).next().html("Please input your username!");
                $(this).next().css("visibility","visible");
            }
            else if($(".reg-box .account").val().length != 11)
            {
                $(this).addClass("errorC");
                $(this).next().html("The account isn't correct!");
                $(this).next().css("visibility","visible");
            }
            else
            {
                $(this).addClass("checkedN");
                $(this).removeClass("errorC");
                $(this).next().empty();
            }
        });
        /*密码输入框失去焦点*/
        $(".reg-box .admin_pwd").blur(function(){
            reg=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,12}$/;

            if($(this).val() == "password"||$(this).val()==""){
                $(this).addClass("errorC");
                $(this).next().html("Please input your password!");
                $(this).next().css("visibility","visible");

            }else if($(".admin_pwd").val().match(reg) === null)
            {
                $(this).addClass("errorC");
                $(this).next().html("The password isn't correct!");
                $(this).next().css("visibility","visible");
            }else
            {
                $(this).addClass("checkedN");
                $(this).removeClass("errorC");
                $(this).next().empty();
            }
        });

        /*邮箱输入框失去焦点*/
        $(".reg-box .email").blur(function(){

            var isHide = $("#confirmmm").hasClass("hide");
            if(!isHide){
                reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

                if($(this).val() == "email"||$(this).val()==""){
                    $(this).addClass("errorC");
                    $(this).next().html("Please input your email!");
                    $(this).next().css("visibility","visible");

                }else if($(this).val().match(reg) === null){
                    $(this).addClass("errorC");
                    $(this).next().html("The format of email is wrong!");
                    $(this).next().css("visibility","visible");
                }else
                {
                    $(this).addClass("checkedN");
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                }
            }
        });


    })();
});

$(function () {
    $(".reg-box .admin_submit").click(function () {
        var b = $(".reg-box #account");
        var a = $(".reg-box .admin_pwd");

        var isTrue = true;
        reg=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,12}$/;

        if(b.val().length != 11)
        {
            b.addClass("errorC");
            b.next().html("The account isn't correct!");
            b.next().css("visibility","visible");
            isTrue = false;
        }else if(a.val().match(reg) === null)
        {
            a.addClass("errorC");
            a.next().html("The password isn't correct!");
            a.next().css("visibility","visible");
            isTrue = false;
        }

        if(b.val()===""||b.val()==="account"){
            b.addClass("errorC");
            b.next().html("Account mustn't be null!");
            b.next().css("visibility","visible");
            isTrue = false;
        }else if(a.val() === "password"||a.val()===""){
            a.addClass("errorC");
            a.next().html("Password mustn't be null!");
            a.next().css("visibility","visible");
            isTrue = false;
        }
        
        return isTrue;
    })
});

$(function () {
    var flag = true;
    $(".reg-box .add_user").click(function () {
        if(flag){
            var username = $(".reg-box #username");
            var pwd = $(".reg-box #password");
            var email = $(".reg-box #email");
            var phone = $(".reg-box #num");

            var isTrue = true;

            reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            reg2=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)
            reg3=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,12}$/;
            if(username.val()===""){
                username.addClass("errorC");
                username.next().html("Please input your username!");
                username.next().css("visibility","visible");
                isTrue = false;
            }else if(username.val().length != 11)
            {
                username.addClass("errorC");
                username.next().html("The account isn't correct!");
                username.next().css("visibility","visible");
                isTrue = false;
            }

            if(pwd.val()===""){
                pwd.addClass("errorC");
                pwd.next().html("Please input your password!");
                pwd.next().css("visibility","visible");
                isTrue = false;

            }else if(pwd.val().match(reg3) === null)
            {
                pwd.addClass("errorC");
                pwd.next().html("The password isn't correct!");
                pwd.next().css("visibility","visible");

                isTrue = false;

            }

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
                isTrue = false;
            } else if(phone.val().length != 11)
            {
                alert(phone.length);
                phone.addClass("errorC");
                phone.next().html("The length of phone num is wrong!");
                phone.next().css("visibility","visible");
                isTrue = false;

            }else if(phone.val().match(reg2) === null){
                phone.addClass("errorC");
                phone.next().html("The format of phone num is wrong!");
                phone.next().css("visibility","visible");
                isTrue = false;

            }
            if(isTrue){
                flag = false;
                addStudent();
            }else {
                return isTrue;
            }
        }
    })
});

$(function () {
    var flag = true;
    $(".reg-box .add_admin").click(function () {

        if(flag){
            var username = $(".reg-box #username");
            var pwd = $(".reg-box #password");
            var email = $(".reg-box #email");
            var phone = $(".reg-box #num");

            var isTrue = true;

            reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            reg2=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)
            reg3=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,12}$/;
            if(username.val()===""){
                username.addClass("errorC");
                username.next().html("Please input your username!");
                username.next().css("visibility","visible");
                isTrue = false;
            }else if(username.val().length != 11)
            {
                username.addClass("errorC");
                username.next().html("The account isn't correct!");
                username.next().css("visibility","visible");
                isTrue = false;
            }

            if(pwd.val()===""){
                pwd.addClass("errorC");
                pwd.next().html("Please input your password!");
                pwd.next().css("visibility","visible");
                isTrue = false;

            }else if(pwd.val().match(reg3) === null)
            {
                pwd.addClass("errorC");
                pwd.next().html("The password isn't correct!");
                pwd.next().css("visibility","visible");

                isTrue = false;

            }

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
                isTrue = false;
            } else if(phone.val().length != 11)
            {
                phone.addClass("errorC");
                phone.next().html("The length of phone num is wrong!");
                phone.next().css("visibility","visible");
                isTrue = false;

            }else if(phone.val().match(reg2) === null){
                phone.addClass("errorC");
                phone.next().html("The format of phone num is wrong!");
                phone.next().css("visibility","visible");
                isTrue = false;

            }

            if(isTrue){
                flag = false;
                addAdmin();
            }else {
                return isTrue;
            }

        }
    })
});

$(function () {
    var flag = true;
    $(".reg-box .add_book").click(function () {
        var isbn = $(".reg-box .isbn");

        var isTrue = true;
        if (flag){
            if(isbn.val()===""){
                isbn.addClass("errorC");
                isbn.next().html("Please input the ISBN of book!");
                isbn.next().css("visibility","visible");
                isTrue = false;
            }else if(isbn.val().length != 13&&isbn.val().length != 10){
                isbn.addClass("errorC");
                isbn.next().html("The ISBN isn't correct!");
                isbn.next().css("visibility","visible");
                isTrue = false;
            }

            if(isTrue){
                flag = false;
                addBook();
            }else {
                return false;
            }
        }


    })
});

$(function () {
    $(".reg-box .edit_info").click(function () {
        var a = $(".reg-box .phone");
        var b = $(".reg-box .email");

        var isTrue = true;

        reg=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)
        reg2=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if( a.val()===""|| a=="Please input your phone!")
        {
            a.addClass("errorC");
            a.next().html("Please input your phone num!");
            a.next().css("visibility","visible");
            isTrue = false;
        }else if(a.val().match(reg) === null){
            a.addClass("errorC");
            a.next().html("The format of phone num is wrong!");
            a.next().css("visibility","visible");
            isTrue = false;
        }else if(a.val().length != 11)
        {
            a.addClass("errorC");
            a.next().html("The length of phone num is wrong!");
            a.next().css("visibility","visible");
            isTrue = false;

        }
        if(b.val() == "email"||b.val()=="") {
            b.addClass("errorC");
            b.next().html("Please input your email!");
            b.next().css("visibility", "visible");
            isTrue = false;

        }else if(b.val().match(reg2) === null){
            b.addClass("errorC");
            b.next().html("The format of email is wrong!");
            b.next().css("visibility","visible");
            isTrue = false;
        }


        if(isTrue){
            modifySelfInfo();
        }else {
            return isTrue;
        }



    })
});


/*清除提示信息*/
function emptyRegister(){
    $(".reg-box .phone,.reg-box .phonekey,.reg-box .password,.reg-box .email").removeClass("errorC");;
    $(".reg-box .error1,.reg-box .error2,.reg-box .error3,.reg-box .error4").empty();
}
function emptyLogin(){
    $(".reg-box .account,.reg-box .admin_pwd,.reg-box .photokey").removeClass("errorC");;
    $(".reg-box .error5,.reg-box .error6,.reg-box .error7").empty();
}
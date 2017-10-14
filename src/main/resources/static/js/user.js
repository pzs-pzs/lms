/**
 * Created by henry on 2017/8/25.
 */

$(function(){

    $('#btn-login').click(function(e){

        $.ajax({
            type: "POST",
            url: "/GMS/login",
            dataType:'json',
            data: $('#login').serialize(),
            success: function(data) {
                result = data;
                if (result['success'] == 2) {
                    alert("验证码错误")
                }
                if (result['success'] == 1) {
                    alert("登录成功")
                    window.location.href = "/GMS/index"
                }
                if(result['success'] == 0){
                    alert("用户名密码错误！")
                }
            }
        });
    });
});

$(function () {
    $('#btn-register').click(function (e) {
        if (document.getElementById('password').value.length < 8 || document.getElementById('password').value.length > 16) {
            alert('密码长度需要为8-16位');
            return;
        }
        if (document.getElementById('password').value != document.getElementById('password-second').value) {
            alert('密码不一致，请重新输入');
            return;
        }

        $.ajax({
            type : "POST",
            url : "/GMS/register",
            dataType : 'json',
            data : $('#register').serialize() ,
            success : function (data) {
                result = data;
                if(result['success'] ==  1){
                    alert("注册成功！")
                    $.ajax({
                        type: "POST",
                        url: "/GMS/login",
                        dataType:'json',
                        data: $('#register').serialize(),
                        success: function(data) {
                            result = data;
                            if (result['success'] == 1) {
                                alert("登录成功")
                                window.location.href = "/GMS/index"
                            }
                            if(result['success'] == 0){
                                alert("用户名密码错误！")
                            }
                        }
                    });
                }

                else{
                    alert("用户已存在，请选择其他用户名进行注册！")
                }
            }

        })
    })
})




function setCookie(sName, sValue) {
    var sCookie = sName + "=" + encodeURIComponent(sValue)+";";
    document.cookie = sCookie;
}

function getCookie(sName) {

    var sRE = "(?:; )?" + sName + "=([^;]*);?";
    var oRE = new RegExp(sRE);

    if (oRE.test(document.cookie)) {
        return decodeURIComponent(RegExp["$1"]);
    } else {
        return null;
    }

}




function clientSetCookies(){
    //setCookie("ClientCookie1", "ClientTest1", new Date(Date.parse("May 4, 2009")), "/CookieTest", "http://localhost:6581/CookieApplication", true);
    setCookie("ClientCookie1", "ClientTest1", new Date(Date.parse("May 4, 2009")), null, null, false);
    setCookie("ClientCookie2", "ClientTest2", new Date(Date.parse("May 4, 2009")));
    setCookie("ClientCookie3", "ClientTest3");

    var sCookie = "The cookies created by client are:<br />{<br />&nbsp;&nbsp;Client Cookie1: " + getCookie("ClientCookie1")
        + "<br />&nbsp;&nbsp;Client Cookie2: " + getCookie("ClientCookie2")
        + "<br />&nbsp;&nbsp;Client Cookie3: " + getCookie("ClientCookie3")
        + "<br />}";

    document.getElementById('<% =divClient.ClientID%>').innerHTML = sCookie;
    //deleteCookie("cookie1");
    //deleteCookie("cookie2");
    //deleteCookie("cookie3");
}


//get cookies which created by server
function clientGetCookies(){
    var sCookie = "The cookies read by client and created by server are:<br />" +
        "{<br />&nbsp;&nbsp;Server Cookie1: " + getCookie("ServerCookie1")
        + "<br />&nbsp;&nbsp;Server Cookie2: " + getCookie("ServerCookie2")
        + "<br />&nbsp;&nbsp;Server Cookie3: " + getCookie("ServerCookie3")
        + "<br />}";

    document.getElementById('<% =divClient.ClientID%>').innerHTML = sCookie;
}
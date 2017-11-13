function borrow(id) {
    var f = 1;
    $.ajax({
        type: 'GET',
        url: '/web/isLogin',
        dataType: "json",
        async: false,
        success: function (data) {
            if(data['error']==2){
                f = 0;
                window.location = '/web/login';
                return;
            }


        },
        error: function () {
            return;
        }
    });
    if(f == 0){
        return;
    }
    var msg = "Are you sure borrow this book?";
    if(confirm(msg)){
        $.ajax({
            type: 'GET',
            url: '/web/borrowBook?id=' + id,
            dataType: "json",
            success: function (data) {
                if(data['error']==0){
                    alert("Ordered success!");
                    return;
                }
                alert("Quantity is not enough,Ordered failed!");
            },
            error: function () {
                alert("Ordered failed!");
            }
        });
    }

}

function change(a,b) {

}
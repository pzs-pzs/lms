function borrow(id) {
    var f = 1;
    $.ajax({
        type: 'GET',
        url: '/web/borrowBook?id=' + id,
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
    var msg = "亲，确定预约借阅这本书籍么?";
    if(confirm(msg)){
        $.ajax({
            type: 'GET',
            url: '/web/borrowBook?id=' + id,
            dataType: "json",
            success: function (data) {
                if(data['error']==0){
                    alert("预约成功!");
                    return;
                }
                alert("预约失败!");
            },
            error: function () {
                alert("预约失败!");
            }
        });
    }

}
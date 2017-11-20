/**
 * Created by FelixWang on 2017/11/19.
 */

var checkSubmitFlg = false;

function checkSubmit(){

    if(checkSubmitFlg == true){

        toastr.options.positionClass ='toast-top-center';
        toastr.error("error");
        toastr.options.timeOut = 500;
        return false;
    }

    checkSubmitFlg ==true;
    return true;

}
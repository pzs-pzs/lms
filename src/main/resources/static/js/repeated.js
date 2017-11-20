/**
 * Created by FelixWang on 2017/11/19.
 */

var checkSubmitFlg = false;

function checkSubmit(){

    if(checkSubmitFlg ==true){
        return false;
    }

    checkSubmitFlg ==true;
    return true;

}
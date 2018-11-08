/**
 * Created by on 2018/11/7.
 */
$(function () {
    //某一类是否命中
    if($('#identity .detection-num').html() > 0) {
        $('.identity .pass-rate-l .pass-rate-desc span').html('大幅降低');
        $('.identity .pass-rate-r .pass-rate-desc span').html('大幅降低');
        $('.identity.detection-record-contain.hit').css('display','block');
        $('.identity.detection-record-contain.noHit').css('display','none');
    }else {
        $('.identity.detection-record-contain.noHit').css('display','block');
        $('.identity.detection-record-contain.hit').css('display','none');
    }
    ///计算命中 手机号、身份证号 total 为命身份证号/总数
    var identityTotal = 0;
    $('.identity .total-hit').each(function () {
        identityTotal = $(this).find('.species-id-item').attr('data-id-num')/($(this).find('.species-tel-item').attr('data-tel-num')*1+$(this).find('.species-id-item').attr('data-id-num')*1)*100;
        $(this).find('.inner-pipe').css('width',identityTotal+'%');
    });
    //其他命中计算 不清楚规则
    $('.identity .hit-species').each(function () {
        var rate = $(this).attr('data-num')?$(this).attr('data-num'):0;
        if(rate>=10) {
            $(this).find('.pipe').css('width','100%');
        }else {
            $(this).find('.pipe').css('width',rate/10*100+'%');
        }
    });

})
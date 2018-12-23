/**
 * Created by on 2018/11/7.
 */
$(function () {
    //某一类是否命中
    if($('#borrowing .detection-num').html() > 0) {
        $('.borrowing .pass-rate-l .pass-rate-desc span').html('大幅降低');
        $('.borrowing .pass-rate-r .pass-rate-desc span').html('大幅降低');
        $('.borrowing.detection-record-contain.hit').css('display','block');
        $('.borrowing.detection-record-contain.noHit').css('display','none');
    }else {
        $('.borrowing.detection-record-contain.noHit').css('display','block');
        $('.borrowing.detection-record-contain.hit').css('display','none');
    }
//计算命中 手机号、身份证号 total 为命身份证号/总数
    var borrowingTotal = 0;
    $('.borrowing .total-hit').each(function () {
        borrowingTotal = $(this).find('.species-id-item').attr('data-id-num')/($(this).find('.species-tel-item').attr('data-tel-num')*1+$(this).find('.species-id-item').attr('data-id-num')*1)*100;
        $(this).find('.inner-pipe').css('width',borrowingTotal+'%');
    });
    $('.borrowing .hit-species').each(function () {
        var rate = $(this).attr('data-num')?$(this).attr('data-num'):0;
        if($(this).attr('data-num')>=5) {
            $(this).find('.pipe').removeClass('colorY2').addClass('colorY1');
        }else {
            $(this).find('.pipe').removeClass('colorY1').addClass('colorY2');
        }
        if(rate>=10) {
            $(this).find('.pipe').css('width','100%');
        }else {
            $(this).find('.pipe').css('width',rate/10*100+'%');
        }
    });

//点击时间
    $('body').on('click','.time-item',function () {
        if($(this).hasClass('active')) {
            return false;
        }else {
            $('.time-item').removeClass('active')
            $(this).parent().siblings('.detection-species-box').removeClass('active');
            $(this).addClass('active');
            var detectionIndex = $(this).attr('data-index');
            $(this).parent().siblings('.detection-species-box').filter('.'+detectionIndex).addClass('active');
        }
    });
})

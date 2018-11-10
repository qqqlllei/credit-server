/**
 * Created by on 2018/11/7.
 */
// var totalScore = Math.floor(Math.random() * (100 - 0)) + 0;

$(function () {
    var htmlSize = '';
    var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
    htmlSize = oWidth/750*100;
    $('.circle-rate-box').circleProgress({
        value:totalScore/100, //进度条进度
        size:parseInt(htmlSize*1.88), //直径
        thickness:10, //圆环宽度
        startAngle: Math.PI / 6 * 1, //起点
        fill:{
            gradient: [['#fffb00',.05],["#23efcf",.2],['#fffb00',.7] ,["#fe8300",.9]]  //填充色渐变
//                color:'#ff5c11' //填充色
        },
        emptyFill:'#fff'  //空圆弧的颜色

    }).on('circle-animation-progress', function(event, progress) {
        //进度条显示内容
        $(this).find('strong').html(parseInt(totalScore * progress));
    });
    //分数描述
    if(totalScore<=100 && totalScore>=80) {
        $('#score-desc').html('<i class="crown-icon"/>'+'哇！您的人品爆发');
        $('#sub-score-desc').html('贷款申请易通过，且额度较高哦～');
        $('#credit-rating').attr('src','/image/you.png');
    }else if(totalScore<=79 && totalScore>=60) {
        $('#score-desc').html('<i class="crown-icon"/>'+'还不错！');
        $('#sub-score-desc').html('您的贷款申请较易通过，且额度不低哦～');
        $('#credit-rating').attr('src','/image/liang.png');

    }else if(totalScore<=59 && totalScore>=40) {
        $('#score-desc').html('<i class="crown-icon"/>'+'啊哦！');
        $('#sub-score-desc').html('您的贷款申请通过率一般，且额度不高哦～请仔细阅读报告负面信息，以便提高信用');
        $('#credit-rating').attr('src','/image/zhong.png');

    }else if(totalScore<40) {
        $('#score-desc').html('<i class="crown-icon"/>'+'Opps！');
        $('#sub-score-desc').html('您的贷款申请通过率较低，且额度不高哦～请仔细阅读报告负面信息，以便提高信用');
        $('#credit-rating').attr('src','/image/cha.png');

    }
    //黑名单类是否命中
    if($('#blacklist .detection-num').html() > 0) {
        $('.blacklist .pass-rate-l .pass-rate-desc span').html('大幅降低');
        $('.blacklist .pass-rate-r .pass-rate-desc span').html('大幅降低');
        $('.blacklist.detection-record-contain.hit').css('display','block');
        $('.blacklist.detection-record-contain.noHit').css('display','none');
    }else {
        $('.blacklist.detection-record-contain.noHit').css('display','block');
        $('.blacklist.detection-record-contain.hit').css('display','none');
    }
    //所有大类是否都命中
    var allNoneHitFlag = true;
    $('.detection-num').each(function () {
        if($(this).html() > 0) {
            allNoneHitFlag = false;
            return false;
        }
    });
    if(!allNoneHitFlag) {
        $('.haveHit').css('display','block');
        $('.detection-info.allNoHit').css('display','none');
    }else {
        $('.haveHit').css('display','none');
        $('.detection-info.allNoHit').css('display','block');
        $('.detection-record-contain.noHit').css('display','block');
        $('.detection-record-contain.hit').css('display','none');
    }

    //查看更多
    $('body').on('click','.more-btn',function () {
        if($(this).hasClass('active')) {
            $(this).removeClass('active');
            if($(this).parent().siblings('.detection-record-detail.borrowing-record').length > 0) {
                $(this).parent().siblings('.detection-record-detail.borrowing-record').css('display','none');
            }
            $(this).parent().siblings('.detection-record-detail').find('.detection-species-box').css('display','none');
        }else {
            $(this).addClass('active');
            if($(this).parent().siblings('.detection-record-detail.borrowing-record').length > 0) {
                $(this).parent().siblings('.detection-record-detail.borrowing-record').css('display','block');
                $(this).parent().siblings('.detection-record-detail').find('.detection-species-box.active').css('display','block');
            }else {
                $(this).parent().siblings('.detection-record-detail').find('.detection-species-box').css('display','block');
            }
        }
    });

    //其他命中计算 不清楚规则
    $('.hit-species').each(function () {
        var rate = $(this).attr('data-num')?$(this).attr('data-num'):0;
        if(rate>=10) {
            $(this).find('.pipe').css('width','100%');
        }else {
            $(this).find('.pipe').css('width',rate/10*100+'%');
        }
    });

    //命中详情
    $('body').on('click','.hit-item-title',function () {
        if($(this).hasClass('active')) {
            $(this).removeClass('active');
            $(this).siblings('.hit-item-info').slideUp("slow");

        }else {
            $('.hit-item-title').removeClass('active');
            $(this).addClass('active');
            $('.hit-item-info').each(function () {
                $(this).css('display','none');
            });
            $(this).siblings('.hit-item-info').slideDown("slow");

        }
    });

    //检测结果tab切换
    $('body').on('click','.detection-item',function () {
        if($(this).hasClass('active')) {
            return false;
        }else {
            $('.detection-item').removeClass('active');
            $('.detection-record-box').removeClass('active');
            $(this).addClass('active');
            var detectionIndex = $(this).attr('data-index');
            $('.detection-record-b').find('.'+detectionIndex).addClass('active');
        }
    });
    //分享 弹框
    $('body').on('click','#share',function () {
        $('.share-dialog-shade').fadeIn('slow');
    });
    //点击分享
    //实例化clipboard
    var shareLink = window.location.href;
    $('#copyLink').val(shareLink);
    var clipboard = new ClipboardJS('#copyLinkBtn');
    $('body').on('click','#copyLinkBtn',function () {

        window.location.href = 'weixin://';
        clipboard.on('success', function(e) {
            console.log(e);
            e.clearSelection();
        });

        clipboard.on('error', function(e) {
            console.log(e);
        });
    });
    //取消分享
    $('body').on('click','#cancel-share',function () {
        $('.share-dialog-shade').fadeOut('slow');
    });


    $('#queryButton').click(function(){
        window.location.href=path+'/query'
    });
})
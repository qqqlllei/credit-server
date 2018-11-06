
    /*1、通过截取身份证得到年龄和性别*/
    function showBirthday(val) {
        var birthdayValue;
        var age;
        var sex;
        var json = {};
        var today = new Date();
        if (15 == val.length) { //15位身份证号码
            birthdayValue = val.charAt(6) + val.charAt(7);
            if (parseInt(birthdayValue) < 10) {
                birthdayValue = '20' + birthdayValue;
            }
            else {
                birthdayValue = '19' + birthdayValue;
            }
            if (parseInt(val.charAt(14) / 2) * 2 != val.charAt(14)){
               sex = '1';
            }else{
               sex = '0';
            }
            age =today.getFullYear()- birthdayValue;
            birthdayValue += '-' + val.charAt(8) + val.charAt(9)+ '-' + val.charAt(10) + val.charAt(11);
        }
        if (18 == val.length) { //18位身份证号码
            birthdayValue = val.charAt(6) + val.charAt(7) + val.charAt(8) + val.charAt(9);
            if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16)){
               sex = '1';
            } else{
               sex = '2';
            }
            age = today.getFullYear()- birthdayValue;
            birthdayValue += '-' + val.charAt(10) + val.charAt(11)+ '-' + val.charAt(12) + val.charAt(13);
        }
        json['birthday'] = birthdayValue;
        json['age'] = age;
        json['sex'] = sex;
        return json;
        //return sex;
    }

    // radio checkbox 赋值给隐藏域以能够符合check验证
    function cValue(cName) {
        $('input[name="'+cName+'"]').each(function(index, el) {
            if($(this)[0].checked) {
                $('#'+cName).val('1');
            }
            $(this).change(function(event) {
                if($(this)[0].checked) {
                    $('#'+cName).val('1');
                }else {
                    $('#'+cName).val('');
                }
            });
        });
    }

    // checkbox  赋值给隐藏域以能够符合check验证
    function checkboxValue(cName) {
        $('input[name="'+cName+'"]').each(function(index, el) {
            if($(this)[0].checked) {
                $('#'+cName).val('1');
            }
            $(this).change(function(event) {
                var flag = true;
                if($(this)[0].checked) {
                    $('#'+cName).val('1');
                }else {
                    $('input[name="'+cName+'"]').each(function(index, el) {
                        if($(this)[0].checked) {
                            flag = true;
                            return false;
                        }else {
                            flag = false;
                        }
                    });
                }
                if(!flag) {
                    $('#' + cName).val('');
                }
            });
        });
    }
    // 选择非无下拉框隐藏项回显时显示
    function tShow(idSel, targetSel) {
        if($('#' + idSel).children('input').attr('value') == 1) {
            $('#' + targetSel).parent().parent().removeClass('hideimportant');
            if(targetSel == 'metro') {
                $('#' + targetSel).parent().siblings().children('label').addClass('required');
            }
        }else {
            $('#' + targetSel).parent().parent().addClass('hideimportant');
            if(targetSel == 'metro') {
                $('#' + targetSel).parent().siblings().children('label').removeClass('required');
            }
        }
    }



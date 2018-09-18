$(function() {
    $.validator.addMethod("userName", function(value, element) {
        var userName = /^[a-zA-Z0-9\_\-]{5,30}$/;
        return this.optional(element) || (userName.test(value));
    }, "");
    $.validator.addMethod("userPass", function(value, element) {
        var userPass = /^[A-Za-z0-9\!\#\$\%\^\&\*\.\~]{5,30}$/;
        return this.optional(element) || (userPass.test(value));
    }, "");
    $.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "");

    // 添加、修改产品表单验证
    $('#product-create-form,#product-edit-form').validate({
        rules: {
            productNumber: {required: true},
            productName: {required: true},
            productClass: {required: true},
            productStatus: {required: true}
        },
        messages: {
            productNumber: {required: '编号不能为空'},
            productName: {required: '名称不能为空'},
            productClass: {required: '类别不能为空'},
            productStatus: {required: '状态不能为空'}
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.next());
        }
    });

    //登录表单验证
    $('#login-form').validate({
        rules: {
            userName: {required: true},
            passWord: {required: true}
        },
        messages: {
            userName: {required: '用户名不能为空'},
            passWord: {required: '密码不能为空'}
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.next());
        },
        submitHandler: function(form) {
            $('#passWord').val($.md5($('#passWord').val()));
            $(form).ajaxSubmit();
        }
    });
});
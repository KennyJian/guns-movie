/**
 * 初始化影院后台详情对话框
 */
var FieldTInfoDlg = {
    fieldTInfoData : {},
    validateFields: {
            cinemaId: {
                validators: {
                    notEmpty: {
                        message: '影院编号不能为空'
                    }
                }
            },
            filmId: {
                validators: {
                    notEmpty: {
                        message: '电影编号不能为空'
                    }
                }
            },
            beginTime: {
                validators: {
                    notEmpty: {
                        message: '开始时间不能为空'
                    },
                    regexp:{
                        regexp: /^[0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}$/,
                        message: '请填写正确的时间类型,如:18:00'
                    }
                }
            },
            endTime: {
                validators: {
                    notEmpty: {
                        message: '结束时间不能为空'
                    }
                }
            },
            hallId: {
                validators: {
                    notEmpty: {
                        message: '放映厅类型编号不能为空'
                    }
                }
            },
            hallName: {
                validators: {
                    notEmpty: {
                        message: '放映厅名称不能为空'
                    }
                }
            },
            price: {
                validators: {
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '只能为整数'
                    },
                    notEmpty: {
                        message: '票价不能为空'
                    }
                }
            },
            beginData: {
                validators: {
                    notEmpty: {
                        message: '开始日期不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
FieldTInfoDlg.validate = function () {
    $('#FieldTInfoForm').data("bootstrapValidator").resetForm();
    $('#FieldTInfoForm').bootstrapValidator('validate');
    return $("#FieldTInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
FieldTInfoDlg.clearData = function() {
    this.fieldTInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FieldTInfoDlg.set = function(key, val) {
    this.fieldTInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FieldTInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FieldTInfoDlg.close = function() {
    parent.layer.close(window.parent.FieldT.layerIndex);
}

/**
 * 收集数据
 */
FieldTInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('cinemaId')
    .set('filmId')
    .set('beginTime')
    .set('endTime')
    .set('hallId')
    .set('hallName')
    .set('price')
    .set('beginData');
}

/**
 * 提交添加
 */
FieldTInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/field/add", function(data){
        Feng.success("添加成功!");
        window.parent.FieldT.table.refresh();
        FieldTInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fieldTInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FieldTInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/field/update", function(data){
        Feng.success("修改成功!");
        window.parent.FieldT.table.refresh();
        FieldTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fieldTInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("FieldTInfoForm", FieldTInfoDlg.validateFields);
});

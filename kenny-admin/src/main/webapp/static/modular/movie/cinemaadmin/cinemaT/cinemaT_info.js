/**
 * 初始化影院后台详情对话框
 */
var CinemaTInfoDlg = {
    cinemaTInfoData : {},
    validateFields: {
            cinemaName: {
                validators: {
                    notEmpty: {
                        message: '影院名称不能为空'
                    }
                }
            },
            cinemaPhone: {
                validators: {
                    notEmpty: {
                        message: '影院电话不能为空'
                    }
                }
            },
            areaId: {
                validators: {
                    notEmpty: {
                        message: '地域编号不能为空'
                    }
                }
            },
            hallIds: {
                validators: {
                    notEmpty: {
                        message: '影厅类型不能为空'
                    }
                }
            },
            cinemaAddress: {
                validators: {
                    notEmpty: {
                        message: '影院地址不能为空'
                    }
                }
            },
            minimumPrice: {
                validators: {
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '只能为整数'
                    },
                    notEmpty: {
                        message: '最低票价不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
CinemaTInfoDlg.validate = function () {
    $('#CinemaTInfoForm').data("bootstrapValidator").resetForm();
    $('#CinemaTInfoForm').bootstrapValidator('validate');
    return $("#CinemaTInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
CinemaTInfoDlg.clearData = function() {
    this.cinemaTInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CinemaTInfoDlg.set = function(key, val) {
    this.cinemaTInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CinemaTInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CinemaTInfoDlg.close = function() {
    parent.layer.close(window.parent.CinemaT.layerIndex);
}

/**
 * 收集数据
 */
CinemaTInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('cinemaName')
    .set('cinemaPhone')
    .set('brandId')
    .set('areaId')
    .set('hallIds')
    .set('imgAddress')
    .set('cinemaAddress')
    .set('minimumPrice');
}

/**
 * 收集新增影厅数据
 */
CinemaTInfoDlg.collectHallData = function() {
    this
        .set('uuid')
        .set('hallId');
}

/**
 * 提交添加
 */
CinemaTInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/admin/add", function(data){
        Feng.success("添加成功!");
        window.parent.CinemaT.table.refresh();
        CinemaTInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cinemaTInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CinemaTInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/admin/update", function(data){
        Feng.success("修改成功!");
        window.parent.CinemaT.table.refresh();
        CinemaTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cinemaTInfoData);
    ajax.start();
}

/**
 * 提交新增影厅
 */
CinemaTInfoDlg.editSubmitAddHall = function() {

    this.clearData();
    this.collectHallData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/admin/addHall", function(data){
        Feng.success("修改成功!");
        window.parent.CinemaT.table.refresh();
        CinemaTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cinemaTInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("CinemaTInfoForm", CinemaTInfoDlg.validateFields);
});

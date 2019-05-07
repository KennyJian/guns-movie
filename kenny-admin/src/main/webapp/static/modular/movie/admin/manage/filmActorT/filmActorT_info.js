/**
 * 初始化影院后台详情对话框
 */
var FilmActorTInfoDlg = {
    filmActorTInfoData : {},
    validateFields: {
        roleName: {
            validators: {
                notEmpty: {
                    message: '角色名不能为空'
                }
            }
        },
        actorImg: {
            validators: {
                notEmpty: {
                    message: '演员图片不能为空'
                }
            }
        }
    }
};

/**
 * 验证数据是否为空
 */
FilmActorTInfoDlg.validate = function () {
    $('#FilmActorTInfoForm').data("bootstrapValidator").resetForm();
    $('#FilmActorTInfoForm').bootstrapValidator('validate');
    return $("#FilmActorTInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
FilmActorTInfoDlg.clearData = function() {
    this.filmActorTInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FilmActorTInfoDlg.set = function(key, val) {
    this.filmActorTInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FilmActorTInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FilmActorTInfoDlg.close = function() {
    parent.layer.close(window.parent.FilmActorT.layerIndex);
}

/**
 * 收集数据
 */
FilmActorTInfoDlg.collectData = function() {
    this
    .set('id')
    .set('roleName')
    .set('actorImg');
}

/**
 * 提交添加
 */
FilmActorTInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/filmActorT/add", function(data){
        Feng.success("添加成功!");
        window.parent.FilmActorT.table.refresh();
        FilmActorTInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmActorTInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FilmActorTInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manageActor/update", function(data){
        Feng.success("修改成功!");
        window.parent.FilmActorT.table.refresh();
        FilmActorTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmActorTInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("FilmActorTInfoForm", FilmActorTInfoDlg.validateFields);
});

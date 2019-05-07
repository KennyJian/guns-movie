/**
 * 初始化影院后台详情对话框
 */
var FilmTInfoDlg = {
    filmTInfoData : {},
    validateFields: {
            filmName: {
                validators: {
                    notEmpty: {
                        message: '影片名称不能为空'
                    }
                }
            },
            filmEnName: {
                validators: {
                    notEmpty: {
                        message: '影片英文名不能为空'
                    }
                }
            },
            filmLength: {
                validators: {
                    notEmpty: {
                        message: '影片长度不能为空'
                    }
                }
            },
            filmLanguage: {
                validators: {
                    notEmpty: {
                        message: '影片语言不能为空'
                    }
                }
            },
            filmType: {
                validators: {
                    notEmpty: {
                        message: '影片类型不能为空'
                    }
                }
            },
            filmSource: {
                validators: {
                    notEmpty: {
                        message: '影片片源不能为空'
                    }
                }
            },
            filmArea: {
                validators: {
                    notEmpty: {
                        message: '影片区域不能为空'
                    }
                }
            },
            filmDate: {
                validators: {
                    notEmpty: {
                        message: '影片上映年代不能为空'
                    }
                }
            },
            filmTime: {
                validators: {
                    notEmpty: {
                        message: '上映时间不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
FilmTInfoDlg.validate = function () {
    $('#FilmTInfoForm').data("bootstrapValidator").resetForm();
    $('#FilmTInfoForm').bootstrapValidator('validate');
    return $("#FilmTInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
FilmTInfoDlg.clearData = function() {
    this.filmTInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FilmTInfoDlg.set = function(key, val) {
    this.filmTInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FilmTInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FilmTInfoDlg.close = function() {
    parent.layer.close(window.parent.FilmT.layerIndex);
}

/**
 * 收集数据
 */
FilmTInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('filmName')
    .set('filmType')
    .set('imgAddress')
    .set('filmScore')
    .set('filmPresalenum')
    .set('filmBoxOffice')
    .set('filmSource')
    .set('filmCats')
    .set('filmArea')
    .set('filmDate')
    .set('filmTime')
    .set('filmStatus');
}

/**
 * 收集数据
 */
FilmTInfoDlg.collectAddData = function() {
    this
        .set('filmName')
        .set('filmEnName')
        .set('filmLength')
        .set('filmLanguage')
        .set('filmType')
        .set('filmSource')
        .set('filmArea')
        .set('filmDate')
        .set('filmTime');
}

/**
 * 收集故事数据
 */
FilmTInfoDlg.collectStoryData = function() {
    this
        .set('uuid')
        .set('story');
}

/**
 * 收集添加电影标签数据
 */
FilmTInfoDlg.collectCatsData = function() {
    this
        .set('uuid')
        .set('filmNowCats');
}

/**
 * 收集修改电影图片数据
 */
FilmTInfoDlg.collectImgData = function() {
    this
        .set('uuid')
        .set('img01')
        .set('img02')
        .set('img03')
        .set('img04')
        .set('img05');
}

/**
 * 收集修改导演信息数据
 */
FilmTInfoDlg.collectDirectorData = function() {
    this
        .set('uuid')
        .set('directorName')
        .set('directorImgUrl');
}

/**
 * 收集提交演员信息数据
 */
FilmTInfoDlg.collectActorData = function() {
    this
        .set('uuid')
        .set('actorName')
        .set('actorRole')
        .set('actorImgUrl');
}

/**
 * 提交添加
 */
FilmTInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectAddData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/add", function(data){
        Feng.success("添加成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FilmTInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/update", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交修改故事简介
 */
FilmTInfoDlg.editSubmitStory = function() {

    this.clearData();
    this.collectStoryData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/updateStory", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交添加电影标签
 */
FilmTInfoDlg.editSubmitCat = function() {

    this.clearData();
    this.collectCatsData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/updateCat", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交添加电影标签
 */
FilmTInfoDlg.editSubmitImg = function() {

    this.clearData();
    this.collectImgData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/addFilmImg", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交修改导演信息
 */
FilmTInfoDlg.editSubmitDirectorInfo = function() {

    this.clearData();
    this.collectDirectorData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/addFilmDirector", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

/**
 * 提交新增演员信息
 */
FilmTInfoDlg.editSubmitActor = function() {

    this.clearData();
    this.collectActorData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admin/manage/addFilmActor", function(data){
        Feng.success("修改成功!");
        window.parent.FilmT.table.refresh();
        FilmTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.filmTInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("FilmTInfoForm", FilmTInfoDlg.validateFields);
});

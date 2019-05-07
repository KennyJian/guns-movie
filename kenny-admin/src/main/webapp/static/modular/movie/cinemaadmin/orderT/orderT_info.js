/**
 * 初始化影院后台详情对话框
 */
var OrderTInfoDlg = {
    orderTInfoData : {},
    validateFields: {
            cinemaId: {
                validators: {
                    notEmpty: {
                        message: '影院编号不能为空'
                    }
                }
            },
            fieldId: {
                validators: {
                    notEmpty: {
                        message: '放映场次编号不能为空'
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
            seatsIds: {
                validators: {
                    notEmpty: {
                        message: '已售座位编号不能为空'
                    }
                }
            },
            seatsName: {
                validators: {
                    notEmpty: {
                        message: '已售座位名称不能为空'
                    }
                }
            },
            filmPrice: {
                validators: {
                    notEmpty: {
                        message: '影片售价不能为空'
                    }
                }
            },
            orderPrice: {
                validators: {
                    notEmpty: {
                        message: '订单总金额不能为空'
                    }
                }
            },
            orderTime: {
                validators: {
                    notEmpty: {
                        message: '下单时间不能为空'
                    }
                }
            },
            orderUser: {
                validators: {
                    notEmpty: {
                        message: '下单人不能为空'
                    }
                }
            },
            orderStatus: {
                validators: {
                    notEmpty: {
                        message: '0-待支付 1-已支付 2-已关闭不能为空'
                    }
                }
            }
    }
};

/**
 * 验证数据是否为空
 */
OrderTInfoDlg.validate = function () {
    $('#OrderTInfoForm').data("bootstrapValidator").resetForm();
    $('#OrderTInfoForm').bootstrapValidator('validate');
    return $("#OrderTInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
OrderTInfoDlg.clearData = function() {
    this.orderTInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderTInfoDlg.set = function(key, val) {
    this.orderTInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderTInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrderTInfoDlg.close = function() {
    parent.layer.close(window.parent.OrderT.layerIndex);
}

/**
 * 收集数据
 */
OrderTInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('cinemaId')
    .set('fieldId')
    .set('filmId')
    .set('seatsIds')
    .set('seatsName')
    .set('filmPrice')
    .set('orderPrice')
    .set('orderTime')
    .set('orderUser')
    .set('orderStatus');
}

/**
 * 提交添加
 */
OrderTInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/order/add", function(data){
        Feng.success("添加成功!");
        window.parent.OrderT.table.refresh();
        OrderTInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderTInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrderTInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cinema/order/update", function(data){
        Feng.success("修改成功!");
        window.parent.OrderT.table.refresh();
        OrderTInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderTInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("OrderTInfoForm", OrderTInfoDlg.validateFields);
});

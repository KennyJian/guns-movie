/**
 * 影院后台管理初始化
 */
var OrderT = {
    id: "OrderTTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderT.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
            {title: '影院名字', field: 'cinemaName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '放映场次编号', field: 'fieldId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '电影名字', field: 'filmName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '座位名称', field: 'seatsName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单金额', field: 'orderPrice', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '订单时间', field: 'orderTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '下单用户', field: 'orderName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '下单状态', field: 'orderStatus', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
OrderT.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OrderT.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加影院后台
 */
OrderT.openAddOrderT = function () {
    var index = layer.open({
        type: 2,
        title: '添加影院后台',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cinema/order/orderT_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看影院后台详情
 */
OrderT.openOrderTDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cinema/order/orderT_update/' + OrderT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除影院后台
 */
OrderT.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cinema/order/delete", function (data) {
            Feng.success("删除成功!");
            OrderT.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderTId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询场次收益
 */
OrderT.getMoneyById = function () {
    if (this.check()) {
        var fieldId=this.seItem.fieldId;
        var ajax = new $ax(Feng.ctxPath + "/cinema/order/searchMoney", function (data) {
            // Feng.info("场次编号为"+fieldId+"的总收益为:"+data.message+"元");
            Feng.info("总收益为:"+data.message+"元");
        }, function (data) {
            Feng.error("查询失败");
        });
        ajax.set("fieldId",fieldId);
        ajax.start();
    }
};

/**
 * 查询影院后台列表
 */
OrderT.search = function () {
    var queryData = {};
        queryData['cinemaName'] = $("#cinemaName").val();
        queryData['filmName'] = $("#filmName").val();
        queryData['orderTime'] = $("#orderTime").val();
        queryData['orderStatus'] = $("#orderStatus").val();
    OrderT.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = OrderT.initColumn();
    var table = new BSTable(OrderT.id, "/cinema/order/list", defaultColunms);
    table.setPaginationType("client");
    OrderT.table = table.init();
});

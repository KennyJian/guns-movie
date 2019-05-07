/**
 * 影院后台管理初始化
 */
var FieldT = {
    id: "FieldTTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FieldT.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
            {title: '影院名字', field: 'cinemaName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '电影名字', field: 'filmName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '开始时间', field: 'beginTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '放映厅类型', field: 'hallType', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '放映厅名称', field: 'hallName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '票价', field: 'price', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '开始日期', field: 'beginData', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
FieldT.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FieldT.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加影院后台
 */
FieldT.openAddFieldT = function () {
    var index = layer.open({
        type: 2,
        title: '添加影院后台',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cinema/field/fieldT_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看影院后台详情
 */
FieldT.openFieldTDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cinema/field/fieldT_update/' + FieldT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除影院后台
 */
FieldT.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cinema/field/delete", function (data) {
            Feng.success("删除成功!");
            FieldT.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("fieldTId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询影院后台列表
 */
FieldT.search = function () {
    var queryData = {};
        queryData['cinemaName'] = $("#cinemaName").val();
        queryData['filmName'] = $("#filmName").val();
        queryData['beginData'] = $("#beginData").val();
    FieldT.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FieldT.initColumn();
    var table = new BSTable(FieldT.id, "/cinema/field/list", defaultColunms);
    table.setPaginationType("client");
    FieldT.table = table.init();
});

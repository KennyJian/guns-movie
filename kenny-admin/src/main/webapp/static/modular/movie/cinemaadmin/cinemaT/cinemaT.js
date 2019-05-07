/**
 * 影院后台管理初始化
 */
var CinemaT = {
    id: "CinemaTTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CinemaT.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
            {title: '影院名称', field: 'cinemaName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影院电话', field: 'cinemaPhone', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '地域编号', field: 'areaId', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '包含的影厅类型', field: 'hallIds', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影院地址', field: 'cinemaAddress', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '最低票价', field: 'minimumPrice', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
CinemaT.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CinemaT.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加影院后台
 */
CinemaT.openAddCinemaT = function () {
    var index = layer.open({
        type: 2,
        title: '添加影院后台',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cinema/admin/cinemaT_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看影院后台详情
 */
CinemaT.openCinemaTDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cinema/admin/cinemaT_update/' + CinemaT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开添加影厅
 */
CinemaT.openCinemaTHallDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cinema/admin/addHall/' + CinemaT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除影院后台
 */
CinemaT.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cinema/admin/delete", function (data) {
            Feng.success("删除成功!");
            CinemaT.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cinemaTId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询影院后台列表
 */
CinemaT.search = function () {
    var queryData = {};
        queryData['cinemaName'] = $("#cinemaName").val();
    CinemaT.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CinemaT.initColumn();
    var table = new BSTable(CinemaT.id, "/cinema/admin/list", defaultColunms);
    table.setPaginationType("client");
    CinemaT.table = table.init();
});

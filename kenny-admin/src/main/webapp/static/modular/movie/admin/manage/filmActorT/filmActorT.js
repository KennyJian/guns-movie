/**
 * 影院后台管理初始化
 */
var FilmActorT = {
    id: "FilmActorTTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FilmActorT.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
            {title: '影片名', field: 'filmName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '演员名', field: 'actorName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '角色名', field: 'roleName', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
FilmActorT.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FilmActorT.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加影院后台
 */
FilmActorT.openAddFilmActorT = function () {
    var index = layer.open({
        type: 2,
        title: '添加影院后台',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/filmActorT/filmActorT_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看影院后台详情
 */
FilmActorT.openFilmActorTDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manageActor/filmActorT_update/' + FilmActorT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除影院后台
 */
FilmActorT.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/admin/manageActor/delete", function (data) {
            Feng.success("删除成功!");
            FilmActorT.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("filmActorTId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询影院后台列表
 */
FilmActorT.search = function () {
    var queryData = {};
        queryData['filmName'] = $("#filmName").val();
        queryData['actorName'] = $("#actorName").val();
        queryData['roleName'] = $("#roleName").val();
    FilmActorT.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FilmActorT.initColumn();
    var table = new BSTable(FilmActorT.id, "/admin/manageActor/list", defaultColunms);
    table.setPaginationType("client");
    FilmActorT.table = table.init();
});

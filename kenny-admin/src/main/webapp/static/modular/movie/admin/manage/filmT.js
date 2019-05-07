/**
 * 影院后台管理初始化
 */
var FilmT = {
    id: "FilmTTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FilmT.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
            {title: '影片名称', field: 'filmName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片英文名称', field: 'filmEnName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片长度', field: 'filmLength', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片语言', field: 'filmLanguage', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '片源类型', field: 'filmType', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片片源', field: 'filmSource', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片区域', field: 'filmArea', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片上映年代', field: 'filmDate', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '影片上映时间', field: 'filmTime', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
FilmT.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FilmT.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加影院后台
 */
FilmT.openAddFilmT = function () {
    var index = layer.open({
        type: 2,
        title: '添加电影',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/admin/manage/filmT_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看影院后台详情
 */
FilmT.openFilmTDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '影院后台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/filmT_update/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看电影简介详情
 */
FilmT.openFilmTStoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电影简介详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/updateStory/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开电影标签页面
 */
FilmT.openFilmTCatsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电影标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/updateCat/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开电影图片页面
 */
FilmT.openFilmTImgDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电影标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/addImg/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开电影导演页面
 */
FilmT.openFilmTDirectorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电影标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/updateDirector/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开添加演员页面
 */
FilmT.openFilmTActorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电影标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/manage/addActor/' + FilmT.seItem.uuid
        });
        this.layerIndex = index;
    }
};
/**
 * 删除影院后台
 */
FilmT.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/admin/manage/delete", function (data) {
            Feng.success("删除成功!");
            FilmT.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("filmTId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询影院后台列表
 */
FilmT.search = function () {
    var queryData = {};
        queryData['filmName'] = $("#filmName").val();
        queryData['filmType'] = $("#filmType").val();
        queryData['filmSource'] = $("#filmSource").val();
        queryData['filmArea'] = $("#filmArea").val();
        queryData['filmDate'] = $("#filmDate").val();
    FilmT.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FilmT.initColumn();
    var table = new BSTable(FilmT.id, "/admin/manage/list", defaultColunms);
    table.setPaginationType("client");
    FilmT.table = table.init();
});

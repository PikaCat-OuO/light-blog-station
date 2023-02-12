layui.use("table", function () {

    // 指定当前页的位置
    layui.$("#main-page").addClass("layui-this");

    layui.table.render({
        elem: '#hot-articles'
        , url: '/get-hot-open-articles' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "最新公开博客"
        , initSort: {
            field: 'modifyTime',
            type: 'desc',
        }
        , autoSort: false // 开启服务器端排序
        , text: {none: "暂无任何公开博客"}
        , skin: "line"
        , cols: [[ //表头
            {
                field: 'title',
                title: '标题',
                style: 'cursor: pointer;'
            }
            , {
                field: 'category',
                title: '分类',
                style: 'cursor: pointer;',
                width: 180
            }
            , {
                field: 'createTime',
                title: '创建时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                sort: true,
                width: 200
            }
            , {
                field: 'modifyTime',
                title: '最后修改时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.modifyTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                sort: true,
                width: 200
            }
        ]]
    });

    // 触发行单击事件
    layui.table.on('row(hot-articles-filter)', function (obj) {
        window.open('/article/' + obj.data.articleUid + '/')
    });

    // 触发排序事件
    layui.table.on('sort(hot-articles-filter)', function (obj) {
        layui.table.reload('hot-articles', {
            initSort: obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。
            , where: { // 请求参数
                field: obj.field // 排序字段
                , order: obj.type // 排序方式
            }
        });
    });

})

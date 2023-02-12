layui.use("table", function () {

    // 指定当前页的位置
    layui.$("#my-blog").addClass("layui-this");

    layui.table.render({
        elem: '#hot-articles'
        , url: '/my-articles/get-user-articles' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "我的博客"
        , initSort: {
            field: 'modifyTime',
            type: 'desc',
        }
        , autoSort: false // 开启服务器端排序
        , text: {none: "暂无任何博客"}
        , skin: "line"
        , cols: [[ // 表头
            {
                field: 'select',
                title: '选择',
                type: 'checkbox'
            }
            , {
                field: 'title',
                title: '标题',
                style: 'cursor: pointer;',
                event: 'click'
            }
            , {
                field: 'keywords',
                title: '关键词',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: 'category',
                title: '分类',
                style: 'cursor: pointer;',
                event: 'click',
                width: 180
            }
            , {
                field: 'open',
                title: '是否公开',
                style: 'cursor: pointer;',
                templet: "<div>{{d.open ? '是' : '否'}}</div>",
                event: 'click',
                hide: true
            }
            , {
                field: 'createTime',
                title: '创建时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                event: 'click',
                sort: true,
                width: 200
            }
            , {
                field: 'modifyTime',
                title: '最后修改时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.modifyTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                event: 'click',
                sort: true,
                width: 200
            }
            , {
                field: '操作',
                title: '相关操作',
                toolbar: '#column-options',
                width: 120
            }
        ]]
        , toolbar: '#table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(hot-articles-filter)', function (obj) {
        let checkStatus = layui.table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'newArticle':
                window.open('/my-articles/new-article');
                break;
            case 'deleteSelected':
                if (checkStatus.data.length === 0) {
                    layui.layer.msg("请先选择需要删除的博客");
                } else {
                    layui.layer.confirm("确认删除选中的博客吗？", {
                        btn: ["确认删除", "取消"],
                        title: '删除博客'
                    }, function (index) {
                        checkStatus.data.forEach(articleInfo => {
                            layui.$.get({
                                url: '/my-articles/delete-article/' + articleInfo.articleUid
                            })
                        })
                        layui.table.reload('hot-articles')
                        layui.layer.close(index)
                    });
                }
                break;
        }
    });

    // 监听单元格事件
    layui.table.on('tool(hot-articles-filter)', function (obj) {
        switch (obj.event) {
            case 'checkbox':
                break;
            case 'edit':
                window.open('/my-articles/edit-article/' + obj.data.articleUid);
                break;
            case 'delete':
                layui.layer.confirm("确认删除这个博客吗？", {
                    btn: ["确认删除", "取消"],
                    title: '确认'
                }, function (index) {
                    layui.$.get({
                        url: '/my-articles/delete-article/' + obj.data.articleUid
                    })
                    obj.del()
                    layui.layer.close(index)
                });
                break;
            default:
                window.open('/article/' + obj.data.articleUid + '/');
        }
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

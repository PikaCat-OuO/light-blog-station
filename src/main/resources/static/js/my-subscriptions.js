layui.use("table", function () {

    // 指定当前页的位置
    layui.$("#my-subscriptions").addClass("layui-this");

    layui.table.render({
        elem: '#subscriptions'
        , url: '/my-subscriptions/get-subscriptions' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "我的关注"
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
                field: 'username',
                title: '作者',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
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
                field: 'createTime',
                title: '创建时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                event: 'click',
                width: 200
            }
            , {
                field: 'modifyTime',
                title: '最后修改时间',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.modifyTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>",
                event: 'click',
                width: 200
            }
            , {
                field: '操作',
                title: '相关操作',
                toolbar: '#column-options',
                width: 90
            }
        ]]
        , toolbar: '#table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(subscriptions-filter)', function (obj) {
        let checkStatus = layui.table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'deleteSelected':
                if (checkStatus.data.length === 0) {
                    layui.layer.msg("请先选择需要取消关注的博客");
                } else {
                    layui.layer.confirm("确认取消关注选中的博客吗？", {
                        btn: ["确认取消关注", "取消"],
                        title: '取消关注博客'
                    }, function (index) {
                        checkStatus.data.forEach(articleInfo => {
                            layui.$.get({
                                url: '/article/' + articleInfo.articleUid + '/unsubscribe'
                            })
                        })
                        layui.table.reload('subscriptions')
                        layui.layer.close(index)
                    });
                }
                break;
        }
    });

    // 监听单元格事件
    layui.table.on('tool(subscriptions-filter)', function (obj) {
        switch (obj.event) {
            case 'checkbox':
                break;
            case 'delete':
                layui.layer.confirm("确认取消关注这个博客吗？", {
                    btn: ["确认取消关注", "取消"],
                    title: '确认'
                }, function (index) {
                    layui.$.get({
                        url: '/article/' + obj.data.articleUid + '/unsubscribe'
                    })
                    obj.del()
                    layui.layer.close(index)
                });
                break;
            default:
                window.open('/article/' + obj.data.articleUid + '/');
        }
    });

})

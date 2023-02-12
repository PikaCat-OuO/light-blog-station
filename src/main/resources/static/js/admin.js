layui.use("table", function () {

    // 指定当前页的位置
    layui.$("#admin").addClass("layui-this");

    // 博客管理表
    layui.table.render({
        elem: '#blog-manage'
        , url: '/admin/get-all-articles' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "所有的博客"
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
                toolbar: '#blog-column-options',
                width: 120
            }
        ]]
        , toolbar: '#blog-table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(blog-manage-filter)', function (obj) {
        if (obj.event === "deleteSelected") {
            let checkStatus = layui.table.checkStatus(obj.config.id);
            if (checkStatus.data.length === 0) {
                layui.layer.msg("请先选择需要删除的博客");
            } else {
                layui.layer.confirm("确认删除选中的博客吗？", {
                    btn: ["确认删除", "取消"],
                    title: '删除博客'
                }, function (index) {
                    checkStatus.data.forEach(articleInfo => {
                        layui.$.get({
                            url: '/admin/delete-article/' + articleInfo.articleUid
                        })
                    })
                    layui.table.reload('blog-manage')
                    layui.layer.close(index)
                });
            }
        }
    });

    // 监听单元格事件
    layui.table.on('tool(blog-manage-filter)', function (obj) {
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
                        url: '/admin/delete-article/' + obj.data.articleUid
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
    layui.table.on('sort(blog-manage-filter)', function (obj) {
        layui.table.reload('blog-manage', {
            initSort: obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。
            , where: { // 请求参数
                field: obj.field // 排序字段
                , order: obj.type // 排序方式
            }
        });
    });


    // 用户管理表
    layui.table.render({
        elem: '#user-manage'
        , url: '/admin/get-all-users' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "所有的用户"
        , initSort: {
            field: 'birthday',
            type: 'asc',
        }
        , autoSort: false // 开启服务器端排序
        , text: {none: "暂无任何用户"}
        , skin: "line"
        , cols: [[ // 表头
            {
                field: 'select',
                title: '选择',
                type: 'checkbox'
            }
            , {
                field: 'username',
                title: '用户名',
                style: 'cursor: pointer;',
                event: 'click'
            }
            , {
                field: 'realname',
                title: '真实姓名',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: 'gender',
                title: '性别',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: 'birthday',
                title: '出生日期',
                style: 'cursor: pointer;',
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年MM月dd日')}}</div>",
                event: 'click',
                sort: true,
                hide: true
            }
            , {
                field: 'telephone',
                title: '电话号码',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: 'address',
                title: '家庭住址',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: 'description',
                title: '个人简介',
                style: 'cursor: pointer;',
                event: 'click',
                hide: true
            }
            , {
                field: '操作',
                title: '相关操作',
                toolbar: '#user-column-options',
                width: 130
            }
        ]]
        , toolbar: '#user-table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(user-manage-filter)', function (obj) {
        if (obj.event === "deleteSelected") {
            let checkStatus = layui.table.checkStatus(obj.config.id);
            if (checkStatus.data.length === 0) {
                layui.layer.msg("请先选择需要删除的用户");
            } else {
                layui.layer.confirm("确认删除选中的用户吗？", {
                    btn: ["确认删除", "取消"],
                    title: '删除用户'
                }, function (index) {
                    checkStatus.data.forEach(userInfo => {
                        layui.$.get({
                            url: '/admin/delete-user/' + userInfo.username
                        })
                    })
                    layui.table.reload('user-manage')
                    layui.layer.close(index)
                });
            }
        }
    });

    // 监听单元格事件
    layui.table.on('tool(user-manage-filter)', function (obj) {
        switch (obj.event) {
            case 'checkbox':
                break;
            case 'edit':
                window.open('/user-page/' + obj.data.username + '/edit-user');
                break;
            case 'delete':
                layui.layer.confirm("确认删除这个用户吗？", {
                    btn: ["确认删除", "取消"],
                    title: '确认'
                }, function (index) {
                    layui.$.get({
                        url: '/admin/delete-user/' + obj.data.username
                    })
                    obj.del()
                    layui.layer.close(index)
                });
                break;
            default:
                window.open('/user-page/' + obj.data.username + '/');
        }
    });

    // 触发排序事件
    layui.table.on('sort(user-manage-filter)', function (obj) {
        layui.table.reload('user-manage', {
            initSort: obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。
            , where: { // 请求参数
                field: obj.field // 排序字段
                , order: obj.type // 排序方式
            }
        });
    });


    // 分类管理表
    layui.table.render({
        elem: '#category-manage'
        , url: '/admin/get-all-category' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "所有的分类"
        , autoSort: false // 开启服务器端排序
        , text: {none: "暂无任何分类"}
        , skin: "line"
        , cols: [[ // 表头
            {
                field: 'select',
                title: '选择',
                type: 'checkbox'
            }
            , {
                field: 'name',
                title: '分类名',
                style: 'cursor: pointer;',
                event: 'click'
            }
            , {
                field: '操作',
                title: '相关操作',
                toolbar: '#category-column-options',
                width: 130
            }
        ]]
        , toolbar: '#category-table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(category-manage-filter)', function (obj) {
        if (obj.event === "deleteSelected") {
            let checkStatus = layui.table.checkStatus(obj.config.id);
            if (checkStatus.data.length === 0) {
                layui.layer.msg("请先选择需要删除的分类");
            } else {
                layui.layer.confirm("确认删除选中的分类吗？", {
                    btn: ["确认删除", "取消"],
                    title: '删除用户'
                }, function (index) {
                    checkStatus.data.forEach(category => {
                        layui.$.get({
                            url: '/admin/delete-category/' + category.name
                        })
                    })
                    layui.table.reload('category-manage')
                    layui.layer.close(index)
                });
            }
        }
    });

    // 监听单元格事件
    layui.table.on('tool(category-manage-filter)', function (obj) {
        switch (obj.event) {
            case 'checkbox':
                break;
            case 'edit':
                window.open('/admin/edit-category/' + obj.data.name);
                break;
            case 'delete':
                layui.layer.confirm("确认删除这个分类吗？", {
                    btn: ["确认删除", "取消"],
                    title: '确认'
                }, function (index) {
                    layui.$.get({
                        url: '/admin/delete-category/' + obj.data.name
                    })
                    obj.del()
                    layui.layer.close(index)
                });
                break;
        }
    });


    // 管理员管理表
    layui.table.render({
        elem: '#admin-manage'
        , url: '/super-admin/get-all-admins' // 数据接口
        , method: 'GET'
        , page: true // 开启分页
        , title: "所有的管理员"
        , autoSort: false // 开启服务器端排序
        , text: {none: "暂无任何管理员"}
        , skin: "line"
        , cols: [[ // 表头
            {
                field: 'select',
                title: '选择',
                type: 'checkbox'
            }
            , {
                field: 'username',
                title: '用户名',
                style: 'cursor: pointer;',
                event: 'click'
            }
            , {
                field: '操作',
                title: '相关操作',
                toolbar: '#admin-column-options',
                width: 130
            }
        ]]
        , toolbar: '#admin-table-options'
    });

    // 监听头部工具栏事件
    layui.table.on('toolbar(admin-manage-filter)', function (obj) {
        if (obj.event === "newAdmin") {
            window.open('/super-admin/add-admin');
            return;
        }
        if (obj.event === "deleteSelected") {
            let checkStatus = layui.table.checkStatus(obj.config.id);
            if (checkStatus.data.length === 0) {
                layui.layer.msg("请先选择需要删除的管理员");
            } else {
                layui.layer.confirm("确认删除选中的管理员吗？", {
                    btn: ["确认删除", "取消"],
                    title: '删除用户'
                }, function (index) {
                    checkStatus.data.forEach(admin => {
                        layui.$.get({
                            url: '/super-admin/delete-admin/' + admin.username
                        })
                    })
                    layui.table.reload('admin-manage')
                    layui.layer.close(index)
                });
            }
        }
    });

    // 监听单元格事件
    layui.table.on('tool(admin-manage-filter)', function (obj) {
        switch (obj.event) {
            case 'checkbox':
                break;
            case 'edit':
                window.open('/super-admin/edit-admin/' + obj.data.username);
                break;
            case 'delete':
                layui.layer.confirm("确认删除这个管理员吗？", {
                    btn: ["确认删除", "取消"],
                    title: '确认'
                }, function (index) {
                    layui.$.get({
                        url: '/super-admin/delete-admin/' + obj.data.username
                    });
                    obj.del();
                    layui.layer.close(index);
                });
                break;
        }
    });

})

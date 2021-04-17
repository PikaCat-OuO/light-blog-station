layui.extend({
    tinymce: '/layui/mods/tinymce/tinymce'
}).use('tinymce', function () {
    layui.tinymce.render({
        elem: "#content"
        , plugins: 'autoresize image'
        , readonly: true
        , toolbar: false
        , menubar: false
        , statusbar: false
    });
});

function removeComment(commentUid) {
    window.location.href = "#"
    layui.layer.confirm("确认删除该评论吗？", {
        btn: ["确认删除", "取消"],
        title: '删除评论'
    }, function (index) {
        window.location.href = "remove-comment/" + commentUid;
    });
}

function commentWrap(comments) {
    let commentsWrap = [];
    layui.each(comments, function (index, item) {
        let removeButton = item.removable ? `&nbsp;<a class="layui-icon layui-icon-delete" href="javascript:removeComment(` + item.commentUid + `)"></a>` : '';
        commentsWrap.push('<div class="layui-card">\n' +
            '<div class="layui-card-header comment-header">\n' +
            `<a class="comment-username" href="/user-page/` + item.username + `/"><i class="layui-icon layui-icon-reply-fill"></i>&nbsp;` + item.username + `</a>\n` +
            removeButton +
            '<p class="comment-time">' + new Date(item.time).toLocaleString() + '</p>\n' +
            '</div>\n' +
            '<div class="layui-card-body comment-content">\n' +
            '<p class="comment-detail">' + item.comment + '</p>\n' +
            '</div>\n' +
            '</div>');
    });
    return commentsWrap;
}

layui.use('flow', function () {
    layui.flow.load({
        elem: '#comment-box' //指定列表容器
        , isAuto: false
        , end: "没有更多评论了"
        , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
            //jQuery的Ajax，请求下一页数据
            layui.$.get('get-comments/' + page, function (res) {
                let comments = commentWrap(res.comments);
                //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                next(comments.join(''), page < res.pages);
            });
        }
    });
});


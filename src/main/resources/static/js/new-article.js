layui.extend({
    tinymce: '/layui/mods/tinymce/tinymce'
}).use('tinymce', function () {
    layui.tinymce.render({
        elem: "#content"
        , plugins: 'autoresize ax_wordlimit image'
        , statusbar: false
        , ax_wordlimit_num: 10000
        , ax_wordlimit_callback: function (editor, txt, num) {
            layui.layer.msg('当前字数：' + txt.length + '，限制字数：' + num);
        }
    });
});
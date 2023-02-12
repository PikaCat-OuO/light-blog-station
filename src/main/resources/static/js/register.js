let timer = setTimeout(() => {
}, 0);

layui.use('laydate', function () {
    layui.laydate.render({
        elem: '#birthday'
    })
})

function usernameCheck(username) {
    clearTimeout(timer);
    if (username.length >= 1 && username.length <= 20) {
        timer = setTimeout(() => {
            layui.$.get("/register/" + username, function (isOk) {
                layui.$("#tips").html(isOk ? "<p id='no'><i class='layui-icon layui-icon-about'></i>&nbsp;该用户名已被注册！</p>" :
                    "<p id='ok'><i class='layui-icon layui-icon-about'></i>&nbsp;该用户名可以注册！</p>");
                layui.$('#submit-button').attr("disabled", isOk);
            });
        }, 500);
    } else {
        layui.$('#submit-button').attr("disabled", false);
        layui.$("#tips").html("");
    }
}
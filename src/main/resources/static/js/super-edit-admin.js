let timer = setTimeout(() => {
}, 0);

function usernameCheck(username) {
    clearTimeout(timer);
    if (username.length >= 1 && username.length <= 20) {
        timer = setTimeout(() => {
            layui.$.get("/super-admin/duplicate-check" + username, function (isOk) {
                layui.$("#tips").html(isOk ? "<p id='no'><i class='layui-icon layui-icon-about'></i>&nbsp;该用户名已被占用！</p>" :
                    "<p id='ok'><i class='layui-icon layui-icon-about'></i>&nbsp;可以修改为该用户名！</p>");
                layui.$('#submit-button').attr("disabled", isOk);
            });
        }, 500);
    } else {
        layui.$('#submit-button').attr("disabled", false);
        layui.$("#tips").html("");
    }
}
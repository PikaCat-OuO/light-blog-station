let timer = setTimeout(() => {
}, 0);

function categoryCheck(category) {

    layui.$("#contribute-category").addClass("layui-this");

    clearTimeout(timer);
    if (category.length >= 1 && category.length <= 10) {
        timer = setTimeout(() => {
            layui.$.get("/contribute-category/" + category, function (isOk) {
                layui.$("#tips").html(isOk ? "<p id='no'><i class='layui-icon layui-icon-about'></i>&nbsp;该分类已存在！</p>" :
                    "<p id='ok'><i class='layui-icon layui-icon-about'></i>&nbsp;该分类尚未存在，您可以贡献该分类！</p>");
                layui.$('#submit-button').attr("disabled", isOk);
            });
        }, 500);
    } else {
        layui.$('#submit-button').attr("disabled", false);
        layui.$("#tips").html("");
    }
}
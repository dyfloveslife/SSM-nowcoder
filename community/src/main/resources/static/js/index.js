$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    // 点击“发布”按钮的时候，隐藏提示框
    $("#publishModal").modal("hide");

    // 发送 AJAX 之前，需要将 CSRF 令牌设置到请求的消息头中
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function (e, xhr, options) {
	// 	xhr.setRequestHeader(header, token);
    // });

    // 在显示之前，需要将数据提交给服务器，服务器响应数据过来的之后，才能展示“发布成功”提示框
    // 使用 id 选择器，选择对应的 id
    // 获取标题和内容
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();

    // 发送异步请求(POST)
    $.post(
        CONTEXT_PATH + "/discuss/add",
        {"title": title, "content": content},
        function (data) {
            data = $.parseJSON(data);
            // 在提示框中显示返回消息
            $("#hintBody").text(data.msg);
            // 提示“发布成功”提示框
            $("#hintModal").modal("show");
            // 2 秒后，自动隐藏提示框
            setTimeout(function () {
                $("#hintModal").modal("hide");
                // 发布成功的时候刷新页面
                if (data.code == 0) {
                    window.location.reload();
                }
            }, 2000);
        }
    );
}
function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        // 处理返回的数据
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
            // 通过 btn 得到下级的子节点，如 <b></b> 或者 <i></i>
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':'赞');
            } else {
                alert(data.msg);
            }
        }
    );
}
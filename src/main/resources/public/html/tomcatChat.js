/**
 * web socket 绑定
 */
var ws = null;

function webSocketBind() {
    /**主流浏览器现在都支持 H5 d的 webSocket 通信，但建议还是要判断*/
    if ("WebSocket" in window) {
        /**创建 web socket 实例
         * 如果连接失败，浏览器控制台报错，连接失败
         * 前缀 ws:// 协议必须写死
         * */
        ws = new WebSocket("ws://127.0.0.1:8317/websocket/tomcat/chat.action");

        /**onopen：服务器连接成功后，自动触发*/
        ws.onopen = function () {
            /** Web Socket 已连接上，使用 send() 方法发送数据*/
            //ws.send("connect success...");
            console.log("服务器连接成功，并发送数据到后台...");
        };

        /**服务器发送数据后，自动触发此方法，客户端进行获取数据，使用 evt.data 获取数据*/
        ws.onmessage = function (evt) {
            var received_msg = evt.data;
            console.log("接收到服务器数据：" + received_msg);
            showServerMessage(received_msg);
        };

        /**客户端与服务器数据传输错误时触发*/
        ws.onerror = function (evt) {
            console.log("客户端 与 服务器 数据传输错误...");
        };

        /**web Socket 连接关闭时触发*/
        ws.onclose = function () {
            console.log("web socket 连接关闭...");
        };
    } else {
        alert("您的浏览器不支持 WebSocket!");
    }
}

/**
 * 显示服务器发送的消息
 * @param message
 */
var showServerMessage = function (message) {
    if (message != undefined && message.trim() != "") {
        /**
         * scrollHeight：div 区域内文档的高度，只能 DOM 操作，JQuery 没有提供相应的方法
         * @type {string}
         */
        var messageShow = "<p id='server'>" + message + "</p>";
        $(".receive").append(messageShow + "<br/>");
        $("#msgContent").val("");

        var scrollHeight = $(".receive")[0].scrollHeight;
        $(".receive").scrollTop(scrollHeight - $(".receive").height());
    }
};

/**
 * 显示客户端的消息
 * @param message
 */
var showClientMessage = function (message) {
    if (message != undefined && message.trim() != "") {
        /**
         * scrollHeight：div 区域内文档的高度，只能 DOM 操作，JQuery 没有提供相应的方法
         * @type {string}
         */
        var messageShow = "<p id='client'>" + message + "</p>";
        $(".receive").append(messageShow + "<br/>");
        $("#msgContent").val("");

        var scrollHeight = $(".receive")[0].scrollHeight;
        $(".receive").scrollTop(scrollHeight - $(".receive").height());
    }
};

$(function () {
    /**初始化后清空消息发送区域*/
    $("#msgContent").val("");

    /**
     * 为 消息 发送按钮绑定事件
     */
    $("#sendButton").click(function () {
        var message = $("#msgContent").val();
        if (message != undefined && message.trim() != "") {
            /**
             * 往服务器发送消息
             */
            ws.send(message.trim());
            showClientMessage(message);//屏幕显示客户端发送的信息
        }
    });

    /**
     * 绑定键盘敲击事件 —— 用于按 回车键 发送消息
     */
    $(window).keydown(function (event) {
        if (event.keyCode === 13) {
            var message = $("#msgContent").val();
            if (message != undefined && message.trim() != "") {
                /**
                 * 往服务器发送消息
                 */
                ws.send(message.trim());
                showClientMessage(message);//屏幕显示客户端发送的信息
            }
        }
    });
    /**
     * 绑定 webSocket，连接 服务器
     */
    webSocketBind();
});
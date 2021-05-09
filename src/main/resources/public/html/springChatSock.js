/**
 * web socket 绑定
 */
var sockjs = null;

function webSocketBind() {
    /**创建 SockJS 实例，直接使用 http 地址
     * 除了创建对象与原生的 webSocket 方式有所区别，其它的 方法都是一样的.*/
    sockjs = new SockJS('http://127.0.0.1:8317/websocket/spring/chatSock.action');

    //onopen：服务器连接成功后，自动触发
    sockjs.onopen = function () {
        /** Web Socket 已连接上，使用 send() 方法发送数据*/
        //sockjs.send("connect success...");
        console.log("服务器连接成功，并发送数据到后台...");
    };

    /**服务器发送数据后，自动触发此方法，客户端进行获取数据，使用 evt.data 获取数据*/
    sockjs.onmessage = function (evt) {
        var received_msg = evt.data;
        console.log("接收到服务器数据：" + received_msg);
        showServerMessage(received_msg);
    };

    /**客户端与服务器数据传输错误时触发*/
    sockjs.onerror = function (evt) {
        console.log("客户端 与 服务器 数据传输错误...");
    };

    /**web Socket 连接关闭时触发*/
    sockjs.onclose = function () {
        console.log("web socket 连接关闭...");
    };
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
            sockjs.send(message.trim());
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
                sockjs.send(message.trim());
                showClientMessage(message);//屏幕显示客户端发送的信息
            }
        }
    });
    /**
     * 绑定 webSocket，连接 服务器
     */
    webSocketBind();
});
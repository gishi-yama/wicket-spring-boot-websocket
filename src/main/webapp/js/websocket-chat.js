var ws = null;
var username = null;
if (window.addEventListener) { // for W3C DOM
	window.addEventListener("load", init, false);
} else if (window.attachEvent) { // for IE
	window.attachEvent("onload", init);
} else {
	window.onload = init;
}

function init() {
	showLoginButton();
}

/* サーバ・エンドポイントとの接続処理 */
function connectServerEndpoint() {
	ws = new Wicket.WebSocket();
	console.log(ws);
	// 接続が完了し画面切り替え（Chatのメッセージ部分を表示）
	showChatMessage();

	ws.open = function(jqEvent) {
		username = document.getElementById("name").value;
		sendMessage(username + " さんが参加しました。")
	};

	username = document.getElementById("name").value;
	sendMessage(username + " さんが参加しました。")

	Wicket.Event.subscribe('/websocket/message', function(jqEvent, message) {
		writeToScreen(message);
	});

	Wicket.Event.subscribe('/websocket/error', function(jqEvent) {
		console.log("WebSocket Error : " + jqEvent);
	});

	Wicket.Event.subscribe('/websocket/closed', function(jqEvent) {
		closeServerEndpoint();
	});
}

/* サーバ・エンドポイントとの切断時の処理 */
function closeServerEndpoint() {
	sendMessage(username + " さんが退出しました。")
	setTimeout(function() {
		ws.close(4001, "Close connection from client");
	}, 1000);
	showLoginButton();
}

/* サーバ・エンドポイントにメッセージ送信 */
function sendMessage(message) {
	Wicket.WebSocket.send(message);
}

/* チャット・メッセージの送信 */
function submitMessage() {
	msg = document.getElementById("inputMessage").value;
	sendMessage(username + " : " + msg);
}

/* テーブルにメッセージの書き込み */
function writeToScreen(message) {
	var element = document.createElement('div');
	element.className = "message";
	element.textContent = message;
	element.style.backgroundColor = "white";
	// メッセージの挿入位置（最新情報を戦闘に記載）
	var objBody = document.getElementById("insertpos");
	objBody.insertBefore(element, objBody.firstChild);
	// Body要素にdivエレメントを追加
}

/* ログインボタンの表示(未切断の時に表示) */
function showLoginButton() {
	document.getElementById("login").style.display = "block";
	document.getElementById("chat").style.display = "none";
}

/* ログアウトボタンの表示(ログイン後に表示) */
function showChatMessage() {
	document.getElementById("login").style.display = "none";
	document.getElementById("chat").style.display = "block";
}
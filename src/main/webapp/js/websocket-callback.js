window.onload = function() {

	setInterval(function() {
		Wicket.WebSocket.send("test");
	}, 5000)

	Wicket.Event.subscribe('/websocket/message', function(jqEvent, message) {
		var newLi = document.createElement('li');
		newLi.innerHTML = message;
		var target = document.getElementById('logs');
		target.appendChild(newLi);
	});
}
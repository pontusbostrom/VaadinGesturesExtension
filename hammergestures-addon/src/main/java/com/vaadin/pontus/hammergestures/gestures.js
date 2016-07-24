
//Detect touch device, method taken from Stackoverflow
if(!('ontouchstart' in window || 'onmsgesturechange' in window)){
	TouchEmulator();
}

window.com_vaadin_pontus_hammergestures_HammerExtension = function() {

	var hammer;
	var pan = null;
	var rotate = null;
	var swipe = null;
	var pinch = null;
	
	this.extend = function() {
		var connectorId = this.getParentId();
		if(connectorId == null){
			return;
		}
		var element = this.getElement(connectorId);
		hammer = new Hammer.Manager(element);
	}
	
	this.addRecognizer = function(recognizer, handler, events, recognizeWith){
		hookUpMultipleRecognizers(recognizer, recognizeWith);
		hammer.on(events, function(ev) {
			var evdata = eventToJson(ev);
			handler(evdata);
		});
	}
	
	this.addPanRecognizer = function(){
		if (arguments.length < 2) {
			console.log('addPanRecognizer called with too few arguments!');
			return;
		}
		var options = {};
		
		if(arguments.length > 2) {
			options = JSON.parse(arguments[2]);
		}
		pan = new Hammer.Pan(options);
		hammer.add(pan);
		this.addRecognizer(pan, this.panEventHandler, arguments[0], JSON.parse(arguments[1]));
	}
	
	this.addRotateRecognizer = function(){
		if (arguments.length < 2) {
			console.log('addRotateRecognizer called with too few arguments!');
			return;
		}
		var options = {};
		if(arguments.length > 2) {
			options = JSON.parse(arguments[2]);
		}
		
		rotate = new Hammer.Rotate(options);
		hammer.add(rotate);
		this.addRecognizer(rotate, this.rotateEventHandler, arguments[0], JSON.parse(arguments[1]));
	}
	
	this.addSwipeRecognizer = function(){
		if (arguments.length < 2) {
			console.log('addSwipeRecognizer called with too few arguments!');
			return;
		}
		var options = {};
		if(arguments.length > 2) {
			options = JSON.parse(arguments[2]);
		}
		swipe = new Hammer.Swipe(options);
		hammer.add(swipe);
		this.addRecognizer(swipe, this.swipeEventHandler, arguments[0], JSON.parse(arguments[1]));
	}
	
	this.addPinchRecognizer = function(){
		if (arguments.length < 2) {
			console.log('addPinchRecognizer called with too few arguments!');
			return;
		}
		var options = {};
		if(arguments.length > 2) {
			options = JSON.parse(arguments[2]);
		}
		pinch = new Hammer.Pinch(options);
		hammer.add(pinch);
		this.addRecognizer(pinch, this.pinchEventHandler, arguments[0], JSON.parse(arguments[1]));
	}
	
	this.removeRecognizers = function(rs){
		rs.map(function(r){
			hammer.remove(r);
		});
	}
	
	function eventToJson(ev){
		var evdata = {
				"type": ev.type,
				"deltaX": JSON.stringify(ev.deltaX),
				"deltaY": JSON.stringify(ev.deltaY),
				"deltaTime": JSON.stringify(ev.deltaTime),
				"distance": JSON.stringify(ev.distance),
				"angle": JSON.stringify(ev.angle),
				"velocityX": JSON.stringify(ev.velocityX),
				"velocityY": JSON.stringify(ev.velocityY),
				"direction": JSON.stringify(ev.direction),
				"rotation": JSON.stringify(ev.rotation),
				"isFirst": JSON.stringify(ev.isFirst),
				"isFinal": JSON.stringify(ev.isFinal)
		};
		return evdata;
	}
	
	function hookUpMultipleRecognizers(r, others){
		if(others.length !== 0){
			r.recognizeWith(others.map(function(v){
				return hammer.get(v);
			}));
		}
	}

}
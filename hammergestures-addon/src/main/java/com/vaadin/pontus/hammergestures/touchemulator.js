
window.com_vaadin_pontus_hammergestures_TouchEmulatorLoader = function(){
	
	this.extend = function() {
		//Detect touch device, method taken from Stackoverflow
		if(!('ontouchstart' in window || 'onmsgesturechange' in window)){
		    TouchEmulator();
		}
	}
};
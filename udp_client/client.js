var stdio = require('stdio')
var keypress = require('keypress')
var dgram = require('dgram');

var PORT;
var HOST;
var client = dgram.createSocket('udp4');
var sendDatagram = function(message) {
	client.send(message, 0, message.length, PORT, HOST, function(err, bytes) {
		if (err) throw err;
		console.log('UDP message sent to ' + HOST +':'+ PORT);
	});
}

stdio.question('Proszę podać adres IP Twojego Andromote', function(err, ip) {
	HOST = ip;
	stdio.question('Proszę podać port serwera UDP na Twoim Andromote', function(err, port) {
		PORT = port;
		console.log('Klient skonfigurowany i gotowy do użytku');
		readKeystrokes();
	});
});

var readKeystrokes = function() {
	keypress(process.stdin);
	process.stdin.on('keypress', function(character, key) {
		console.log(key.name);
		if(key) {
			if(key.name == 'up') {
				sendDatagram(new Buffer('MOVE_FORWARD'));
			}
			if(key.name == 'left') {
				sendDatagram(new Buffer('MOVE_LEFT'));
			}
			if(key.name == 'right') {
				sendDatagram(new Buffer('MOVE_RIGHT'));
			}
			if(key.name == 'down') {
				sendDatagram(new Buffer('MOVE_BACKWARD'));
			}
			if(key.name == 'space') {
				sendDatagram(new Buffer('STOP'));
			}
			if(key.ctrl && key.name == 'c') { 
				client.close();
				process.exit(); 
			}
		}
	});

	process.stdin.setRawMode(true);
	process.stdin.resume();
};

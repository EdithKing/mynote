function Greeter(lang){
	this.language = lang;
	this.greet = function(){
		switch(this.language){
			case "en":return "Hello";
			case "ch":return "你好";
			default : return "No speaka that language";
		}
	}
}


exports.hello_world = function(){
	console.log('hello world module');
}

exports.saybye = function(){
	console.log('bye bye!!!');
}

exports.create_greenter = function(lang){
	return new Greeter(lang);
}
var mm = require('./mymodule.js');
mm.hello_world();
mm.saybye();

var greeter1 = mm.create_greenter("ch");
var greeter2 = mm.create_greenter("en");
console.log(greeter1.greet());
console.log(greeter2.greet());

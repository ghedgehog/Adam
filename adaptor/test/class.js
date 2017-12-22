/**************************父类Base的定义******************************/
function Base() {
  if (!(this instanceof Base)) {
    return new Base();
  }
  //Base类成员变量
  this.className = "Base";          
}

//base类成员函数
Base.prototype.printClassName = function(){
   console.log(this.className);
}

var base = Base(); //不使用new操作符，直接进行函数调用，自动调用new操作符
console.log(base.className);
base.printClassName();
/**************************父类Base的定义******************************/

/**************************继承Base类******************************/
//不建议使用util.inherits,使用ES6的class和extends
/* const util = require('util');
util.inherits(Child, Base);//继承父类成员函数
//子类构造函数
function Child() {
  if (!(this instanceof Child)) {
    return new Child();
  }
  Base.call(this);//继承父类成员变量
  this._className = "Child";//更新Base类成员变量
}
var child = Child();
console.log("child class test:",child.className); */

//ES6(class和extends)
class Child extends Base {
  child_write(){
    console.log("this is child method!");
  };
  Base.call(this);
  this._className = "aaaaaaaaaa";
};
var child = new Child();
console.log(child.className);
child.printClassName();
child.child_write();
/**************************继承Base类******************************/
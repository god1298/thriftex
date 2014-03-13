namespace java com.ximalaya.thrift.test
namespace rb thrift.test

struct Foo {
	1: required i64 id,
  	2: optional i32 count,
  	3: string name,
  	4: bool deleted
}

exception FooException {
	1: i32 type,
  	2: string message
}

service TestService {

   void maketimeout(),

   i32 put(1:Foo foo) throws (1:FooException fe),
   
   string get(1:string name)

}
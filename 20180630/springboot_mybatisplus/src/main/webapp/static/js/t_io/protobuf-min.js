(function(global,undefined){"use strict";(function prelude(modules,cache,entries){function $require(name){var $module=cache[name];if(!$module)
modules[name][0].call($module=cache[name]={exports:{}},$require,$module,$module.exports);return $module.exports;}
var protobuf=global.protobuf=$require(entries[0]);if(typeof define==="function"&&define.amd)
define(["long"],function(Long){protobuf.util.Long=Long;protobuf.configure();return protobuf;});if(typeof module==="object"&&module&&module.exports)
module.exports=protobuf;})({1:[function(require,module,exports){"use strict";module.exports=asPromise;function asPromise(fn,ctx){var params=[];for(var i=2;i<arguments.length;)
params.push(arguments[i++]);var pending=true;return new Promise(function asPromiseExecutor(resolve,reject){params.push(function asPromiseCallback(err){if(pending){pending=false;if(err)
reject(err);else{var args=[];for(var i=1;i<arguments.length;)
args.push(arguments[i++]);resolve.apply(null,args);}}});try{fn.apply(ctx||this,params);}catch(err){if(pending){pending=false;reject(err);}}});}},{}],2:[function(require,module,exports){"use strict";var base64=exports;base64.length=function length(string){var p=string.length;if(!p)
return 0;var n=0;while(--p%4>1&&string.charAt(p)==="=")
++n;return Math.ceil(string.length*3)/4-n;};var b64=new Array(64);var s64=new Array(123);for(var i=0;i<64;)
s64[b64[i]=i<26?i+65:i<52?i+71:i<62?i-4:i-59|43]=i++;base64.encode=function encode(buffer,start,end){var string=[];var i=0,j=0,t;while(start<end){var b=buffer[start++];switch(j){case 0:string[i++]=b64[b>>2];t=(b&3)<<4;j=1;break;case 1:string[i++]=b64[t|b>>4];t=(b&15)<<2;j=2;break;case 2:string[i++]=b64[t|b>>6];string[i++]=b64[b&63];j=0;break;}}
if(j){string[i++]=b64[t];string[i]=61;if(j===1)
string[i+1]=61;}
return String.fromCharCode.apply(String,string);};var invalidEncoding="invalid encoding";base64.decode=function decode(string,buffer,offset){var start=offset;var j=0,t;for(var i=0;i<string.length;){var c=string.charCodeAt(i++);if(c===61&&j>1)
break;if((c=s64[c])===undefined)
throw Error(invalidEncoding);switch(j){case 0:t=c;j=1;break;case 1:buffer[offset++]=t<<2|(c&48)>>4;t=c;j=2;break;case 2:buffer[offset++]=(t&15)<<4|(c&60)>>2;t=c;j=3;break;case 3:buffer[offset++]=(t&3)<<6|c;j=0;break;}}
if(j===1)
throw Error(invalidEncoding);return offset-start;};base64.test=function test(string){return/^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$/.test(string);};},{}],3:[function(require,module,exports){"use strict";module.exports=codegen;var blockOpenRe=/[{[]$/,blockCloseRe=/^[}\]]/,casingRe=/:$/,branchRe=/^\s*(?:if|}?else if|while|for)\b|\b(?:else)\s*$/,breakRe=/\b(?:break|continue)(?: \w+)?;?$|^\s*return\b/;function codegen(){var params=[],src=[],indent=1,inCase=false;for(var i=0;i<arguments.length;)
params.push(arguments[i++]);function gen(){var args=[],i=0;for(;i<arguments.length;)
args.push(arguments[i++]);var line=sprintf.apply(null,args);var level=indent;if(src.length){var prev=src[src.length-1];if(blockOpenRe.test(prev))
level=++indent;else if(branchRe.test(prev))
++level;if(casingRe.test(prev)&&!casingRe.test(line)){level=++indent;inCase=true;}else if(inCase&&breakRe.test(prev)){level=--indent;inCase=false;}
if(blockCloseRe.test(line))
level=--indent;}
for(i=0;i<level;++i)
line="\t"+line;src.push(line);return gen;}
function str(name){return"function"+(name?" "+name.replace(/[^\w_$]/g,"_"):"")+"("+params.join(",")+") {\n"+src.join("\n")+"\n}";}
gen.str=str;function eof(name,scope){if(typeof name==="object"){scope=name;name=undefined;}
var source=gen.str(name);if(codegen.verbose)
console.log("--- codegen ---\n"+source.replace(/^/mg,"> ").replace(/\t/g,"  "));var keys=Object.keys(scope||(scope={}));return Function.apply(null,keys.concat("return "+source)).apply(null,keys.map(function(key){return scope[key];}));}
gen.eof=eof;return gen;}
function sprintf(format){var args=[],i=1;for(;i<arguments.length;)
args.push(arguments[i++]);i=0;format=format.replace(/%([dfjs])/g,function($0,$1){switch($1){case"d":return Math.floor(args[i++]);case"f":return Number(args[i++]);case"j":return JSON.stringify(args[i++]);default:return args[i++];}});if(i!==args.length)
throw Error("argument count mismatch");return format;}
codegen.sprintf=sprintf;codegen.supported=false;try{codegen.supported=codegen("a","b")("return a-b").eof()(2,1)===1;}catch(e){}
codegen.verbose=false;},{}],4:[function(require,module,exports){"use strict";module.exports=EventEmitter;function EventEmitter(){this._listeners={};}
EventEmitter.prototype.on=function on(evt,fn,ctx){(this._listeners[evt]||(this._listeners[evt]=[])).push({fn:fn,ctx:ctx||this});return this;};EventEmitter.prototype.off=function off(evt,fn){if(evt===undefined)
this._listeners={};else{if(fn===undefined)
this._listeners[evt]=[];else{var listeners=this._listeners[evt];for(var i=0;i<listeners.length;)
if(listeners[i].fn===fn)
listeners.splice(i,1);else
++i;}}
return this;};EventEmitter.prototype.emit=function emit(evt){var listeners=this._listeners[evt];if(listeners){var args=[],i=1;for(;i<arguments.length;)
args.push(arguments[i++]);for(i=0;i<listeners.length;)
listeners[i].fn.apply(listeners[i++].ctx,args);}
return this;};},{}],5:[function(require,module,exports){"use strict";module.exports=fetch;var asPromise=require(1),inquire=require(6);var fs=inquire("fs");function fetch(filename,options,callback){if(typeof options==="function"){callback=options;options={};}else if(!options)
options={};if(!callback)
return asPromise(fetch,this,filename,options);if(!options.xhr&&fs&&fs.readFile)
return fs.readFile(filename,function fetchReadFileCallback(err,contents){return err&&typeof XMLHttpRequest!=="undefined"?fetch.xhr(filename,options,callback):err?callback(err):callback(null,options.binary?contents:contents.toString("utf8"));});return fetch.xhr(filename,options,callback);}
fetch.xhr=function fetch_xhr(filename,options,callback){var xhr=new XMLHttpRequest();xhr.onreadystatechange=function fetchOnReadyStateChange(){if(xhr.readyState!==4)
return undefined;if(xhr.status!==0&&xhr.status!==200)
return callback(Error("status "+xhr.status));if(options.binary){var buffer=xhr.response;if(!buffer){buffer=[];for(var i=0;i<xhr.responseText.length;++i)
buffer.push(xhr.responseText.charCodeAt(i)&255);}
return callback(null,typeof Uint8Array!=="undefined"?new Uint8Array(buffer):buffer);}
return callback(null,xhr.responseText);};if(options.binary){if("overrideMimeType"in xhr)
xhr.overrideMimeType("text/plain; charset=x-user-defined");xhr.responseType="arraybuffer";}
xhr.open("GET",filename);xhr.send();};},{"1":1,"6":6}],6:[function(require,module,exports){"use strict";module.exports=inquire;function inquire(moduleName){try{var mod=eval("quire".replace(/^/,"re"))(moduleName);if(mod&&(mod.length||Object.keys(mod).length))
return mod;}catch(e){}
return null;}},{}],7:[function(require,module,exports){"use strict";var path=exports;var isAbsolute=path.isAbsolute=function isAbsolute(path){return/^(?:\/|\w+:)/.test(path);};var normalize=path.normalize=function normalize(path){path=path.replace(/\\/g,"/").replace(/\/{2,}/g,"/");var parts=path.split("/"),absolute=isAbsolute(path),prefix="";if(absolute)
prefix=parts.shift()+"/";for(var i=0;i<parts.length;){if(parts[i]===".."){if(i>0&&parts[i-1]!=="..")
parts.splice(--i,2);else if(absolute)
parts.splice(i,1);else
++i;}else if(parts[i]===".")
parts.splice(i,1);else
++i;}
return prefix+parts.join("/");};path.resolve=function resolve(originPath,includePath,alreadyNormalized){if(!alreadyNormalized)
includePath=normalize(includePath);if(isAbsolute(includePath))
return includePath;if(!alreadyNormalized)
originPath=normalize(originPath);return(originPath=originPath.replace(/(?:\/|^)[^/]+$/,"")).length?normalize(originPath+"/"+includePath):includePath;};},{}],8:[function(require,module,exports){"use strict";module.exports=pool;function pool(alloc,slice,size){var SIZE=size||8192;var MAX=SIZE>>>1;var slab=null;var offset=SIZE;return function pool_alloc(size){if(size<1||size>MAX)
return alloc(size);if(offset+size>SIZE){slab=alloc(SIZE);offset=0;}
var buf=slice.call(slab,offset,offset+=size);if(offset&7)
offset=(offset|7)+1;return buf;};}},{}],9:[function(require,module,exports){"use strict";var utf8=exports;utf8.length=function utf8_length(string){var len=0,c=0;for(var i=0;i<string.length;++i){c=string.charCodeAt(i);if(c<128)
len+=1;else if(c<2048)
len+=2;else if((c&0xFC00)===0xD800&&(string.charCodeAt(i+1)&0xFC00)===0xDC00){++i;len+=4;}else
len+=3;}
return len;};utf8.read=function utf8_read(buffer,start,end){var len=end-start;if(len<1)
return"";var parts=null,chunk=[],i=0,t;while(start<end){t=buffer[start++];if(t<128)
chunk[i++]=t;else if(t>191&&t<224)
chunk[i++]=(t&31)<<6|buffer[start++]&63;else if(t>239&&t<365){t=((t&7)<<18|(buffer[start++]&63)<<12|(buffer[start++]&63)<<6|buffer[start++]&63)-0x10000;chunk[i++]=0xD800+(t>>10);chunk[i++]=0xDC00+(t&1023);}else
chunk[i++]=(t&15)<<12|(buffer[start++]&63)<<6|buffer[start++]&63;if(i>8191){(parts||(parts=[])).push(String.fromCharCode.apply(String,chunk));i=0;}}
if(parts){if(i)
parts.push(String.fromCharCode.apply(String,chunk.slice(0,i)));return parts.join("");}
return String.fromCharCode.apply(String,chunk.slice(0,i));};utf8.write=function utf8_write(string,buffer,offset){var start=offset,c1,c2;for(var i=0;i<string.length;++i){c1=string.charCodeAt(i);if(c1<128){buffer[offset++]=c1;}else if(c1<2048){buffer[offset++]=c1>>6|192;buffer[offset++]=c1&63|128;}else if((c1&0xFC00)===0xD800&&((c2=string.charCodeAt(i+1))&0xFC00)===0xDC00){c1=0x10000+((c1&0x03FF)<<10)+(c2&0x03FF);++i;buffer[offset++]=c1>>18|240;buffer[offset++]=c1>>12&63|128;buffer[offset++]=c1>>6&63|128;buffer[offset++]=c1&63|128;}else{buffer[offset++]=c1>>12|224;buffer[offset++]=c1>>6&63|128;buffer[offset++]=c1&63|128;}}
return offset-start;};},{}],10:[function(require,module,exports){"use strict";module.exports=Class;var Message=require(21),util=require(36);var Type;function Class(type,ctor){if(!Type)
Type=require(34);if(!(type instanceof Type))
throw TypeError("type must be a Type");if(ctor){if(typeof ctor!=="function")
throw TypeError("ctor must be a function");}else
ctor=util.codegen("p")("return c.call(this,p)").eof(type.name,{c:Message});ctor.constructor=Class;(ctor.prototype=new Message()).constructor=ctor;util.merge(ctor,Message,true);ctor.$type=type;ctor.prototype.$type=type;var i=0;for(;i<type.fieldsArray.length;++i){ctor.prototype[type._fieldsArray[i].name]=Array.isArray(type._fieldsArray[i].resolve().defaultValue)?util.emptyArray:util.isObject(type._fieldsArray[i].defaultValue)&&!type._fieldsArray[i].long?util.emptyObject:type._fieldsArray[i].defaultValue;}
var ctorProperties={};for(i=0;i<type.oneofsArray.length;++i)
ctorProperties[type._oneofsArray[i].resolve().name]={get:util.oneOfGetter(type._oneofsArray[i].oneof),set:util.oneOfSetter(type._oneofsArray[i].oneof)};if(i)
Object.defineProperties(ctor.prototype,ctorProperties);type.ctor=ctor;return ctor.prototype;}
Class.create=Class;Class.prototype=Message;},{"21":21,"34":34,"36":36}],11:[function(require,module,exports){"use strict";module.exports=common;function common(name,json){if(!/\/|\./.test(name)){name="google/protobuf/"+name+".proto";json={nested:{google:{nested:{protobuf:{nested:json}}}}};}
common[name]=json;}
common("any",{Any:{fields:{type_url:{type:"string",id:1},value:{type:"bytes",id:2}}}});var timeType;common("duration",{Duration:timeType={fields:{seconds:{type:"int64",id:1},nanos:{type:"int32",id:2}}}});common("timestamp",{Timestamp:timeType});common("empty",{Empty:{fields:{}}});common("struct",{Struct:{fields:{fields:{keyType:"string",type:"Value",id:1}}},Value:{oneofs:{kind:{oneof:["nullValue","numberValue","stringValue","boolValue","structValue","listValue"]}},fields:{nullValue:{type:"NullValue",id:1},numberValue:{type:"double",id:2},stringValue:{type:"string",id:3},boolValue:{type:"bool",id:4},structValue:{type:"Struct",id:5},listValue:{type:"ListValue",id:6}}},NullValue:{values:{NULL_VALUE:0}},ListValue:{fields:{values:{rule:"repeated",type:"Value",id:1}}}});common("wrappers",{DoubleValue:{fields:{value:{type:"double",id:1}}},FloatValue:{fields:{value:{type:"float",id:1}}},Int64Value:{fields:{value:{type:"int64",id:1}}},UInt64Value:{fields:{value:{type:"uint64",id:1}}},Int32Value:{fields:{value:{type:"int32",id:1}}},UInt32Value:{fields:{value:{type:"uint32",id:1}}},BoolValue:{fields:{value:{type:"bool",id:1}}},StringValue:{fields:{value:{type:"string",id:1}}},BytesValue:{fields:{value:{type:"bytes",id:1}}}});},{}],12:[function(require,module,exports){"use strict";var converter=exports;var Enum=require(15),util=require(36);function genValuePartial_fromObject(gen,field,fieldIndex,prop){if(field.resolvedType){if(field.resolvedType instanceof Enum){gen
("switch(d%s){",prop);for(var values=field.resolvedType.values,keys=Object.keys(values),i=0;i<keys.length;++i){if(field.repeated&&values[keys[i]]===field.typeDefault)gen
("default:");gen
("case%j:",keys[i])
("case %j:",values[keys[i]])
("m%s=%j",prop,values[keys[i]])
("break");}gen
("}");}else gen
("if(typeof d%s!==\"object\")",prop)
("throw TypeError(%j)",field.fullName+": object expected")
("m%s=types[%d].fromObject(d%s)",prop,fieldIndex,prop);}else{var isUnsigned=false;switch(field.type){case"double":case"float":gen
("m%s=Number(d%s)",prop,prop);break;case"uint32":case"fixed32":gen
("m%s=d%s>>>0",prop,prop);break;case"int32":case"sint32":case"sfixed32":gen
("m%s=d%s|0",prop,prop);break;case"uint64":isUnsigned=true;case"int64":case"sint64":case"fixed64":case"sfixed64":gen
("if(util.Long)")
("(m%s=util.Long.fromValue(d%s)).unsigned=%j",prop,prop,isUnsigned)
("else if(typeof d%s===\"string\")",prop)
("m%s=parseInt(d%s,10)",prop,prop)
("else if(typeof d%s===\"number\")",prop)
("m%s=d%s",prop,prop)
("else if(typeof d%s===\"object\")",prop)
("m%s=new util.LongBits(d%s.low,d%s.high).toNumber(%s)",prop,prop,prop,isUnsigned?"true":"");break;case"bytes":gen
("if(typeof d%s===\"string\")",prop)
("util.base64.decode(d%s,m%s=util.newBuffer(util.base64.length(d%s)),0)",prop,prop,prop)
("else if(d%s.length)",prop)
("m%s=d%s",prop,prop);break;case"string":gen
("m%s=String(d%s)",prop,prop);break;case"bool":gen
("m%s=Boolean(d%s)",prop,prop);break;}}
return gen;}
converter.fromObject=function fromObject(mtype){var fields=mtype.fieldsArray;var gen=util.codegen("d")
("if(d instanceof this.ctor)")
("return d");if(!fields.length)return gen
("return new this.ctor");gen
("var m=new this.ctor");for(var i=0;i<fields.length;++i){var field=fields[i].resolve(),prop=util.safeProp(field.name);if(field.map){gen
("if(d%s){",prop)
("if(typeof d%s!==\"object\")",prop)
("throw TypeError(%j)",field.fullName+": object expected")
("m%s={}",prop)
("for(var ks=Object.keys(d%s),i=0;i<ks.length;++i){",prop);genValuePartial_fromObject(gen,field,i,prop+"[ks[i]]")
("}")
("}");}else if(field.repeated){gen
("if(d%s){",prop)
("if(!Array.isArray(d%s))",prop)
("throw TypeError(%j)",field.fullName+": array expected")
("m%s=[]",prop)
("for(var i=0;i<d%s.length;++i){",prop);genValuePartial_fromObject(gen,field,i,prop+"[i]")
("}")
("}");}else{if(!(field.resolvedType instanceof Enum))gen
("if(d%s!==undefined&&d%s!==null){",prop,prop);genValuePartial_fromObject(gen,field,i,prop);if(!(field.resolvedType instanceof Enum))gen
("}");}}return gen
("return m");};function genValuePartial_toObject(gen,field,fieldIndex,prop){if(field.resolvedType){if(field.resolvedType instanceof Enum)gen
("d%s=o.enums===String?types[%d].values[m%s]:m%s",prop,fieldIndex,prop,prop);else gen
("d%s=types[%d].toObject(m%s,o)",prop,fieldIndex,prop);}else{var isUnsigned=false;switch(field.type){case"uint64":isUnsigned=true;case"int64":case"sint64":case"fixed64":case"sfixed64":gen
("if(typeof m%s===\"number\")",prop)
("d%s=o.longs===String?String(m%s):m%s",prop,prop,prop)
("else")
("d%s=o.longs===String?util.Long.prototype.toString.call(m%s):o.longs===Number?new util.LongBits(m%s.low,m%s.high).toNumber(%s):m%s",prop,prop,prop,prop,isUnsigned?"true":"",prop);break;case"bytes":gen
("d%s=o.bytes===String?util.base64.encode(m%s,0,m%s.length):o.bytes===Array?Array.prototype.slice.call(m%s):m%s",prop,prop,prop,prop,prop);break;default:gen
("d%s=m%s",prop,prop);break;}}
return gen;}
converter.toObject=function toObject(mtype){var fields=mtype.fieldsArray;if(!fields.length)
return util.codegen()("return {}");var gen=util.codegen("m","o")
("if(!o)")
("o={}")
("var d={}");var repeatedFields=[],mapFields=[],otherFields=[],i=0;for(;i<fields.length;++i)
if(fields[i].resolve().repeated)
repeatedFields.push(fields[i]);else if(fields[i].map)
mapFields.push(fields[i]);else
otherFields.push(fields[i]);if(repeatedFields.length){gen
("if(o.arrays||o.defaults){");for(i=0;i<repeatedFields.length;++i)gen
("d%s=[]",util.safeProp(repeatedFields[i].name));gen
("}");}
if(mapFields.length){gen
("if(o.objects||o.defaults){");for(i=0;i<mapFields.length;++i)gen
("d%s={}",util.safeProp(mapFields[i].name));gen
("}");}
if(otherFields.length){gen
("if(o.defaults){");for(i=0,field;i<otherFields.length;++i){var field=otherFields[i],prop=util.safeProp(field.name);if(field.resolvedType instanceof Enum)gen
("d%s=o.enums===String?%j:%j",prop,field.resolvedType.valuesById[field.typeDefault],field.typeDefault);else if(field.long)gen
("if(util.Long){")
("var n=new util.Long(%d,%d,%j)",field.typeDefault.low,field.typeDefault.high,field.typeDefault.unsigned)
("d%s=o.longs===String?n.toString():o.longs===Number?n.toNumber():n",prop)
("}else")
("d%s=o.longs===String?%j:%d",prop,field.typeDefault.toString(),field.typeDefault.toNumber());else if(field.bytes)gen
("d%s=o.bytes===String?%j:%s",prop,String.fromCharCode.apply(String,field.typeDefault),"["+Array.prototype.slice.call(field.typeDefault).join(",")+"]");else gen
("d%s=%j",prop,field.typeDefault);}gen
("}");}
for(i=0,field;i<fields.length;++i){var field=fields[i],prop=util.safeProp(field.name);gen
("if(m%s!==undefined&&m%s!==null&&m.hasOwnProperty(%j)){",prop,prop,field.name);if(field.map){gen
("d%s={}",prop)
("for(var ks2=Object.keys(m%s),j=0;j<ks2.length;++j){",prop);genValuePartial_toObject(gen,field,i,prop+"[ks2[j]]")
("}");}else if(field.repeated){gen
("d%s=[]",prop)
("for(var j=0;j<m%s.length;++j){",prop);genValuePartial_toObject(gen,field,i,prop+"[j]")
("}");}else
genValuePartial_toObject(gen,field,i,prop);gen
("}");}
return gen
("return d");};},{"15":15,"36":36}],13:[function(require,module,exports){"use strict";module.exports=decoder;decoder.compat=true;var Enum=require(15),types=require(35),util=require(36);function decoder(mtype){var gen=util.codegen("r","l")
("if(!(r instanceof Reader))")
("r=Reader.create(r)")
("var c=l===undefined?r.len:r.pos+l,m=new this.ctor")
("while(r.pos<c){")
("var t=r.uint32()");if(mtype.group)gen
("if((t&7)===4)")
("break");gen
("switch(t>>>3){");for(var i=0;i<mtype.fieldsArray.length;++i){var field=mtype._fieldsArray[i].resolve(),type=field.resolvedType instanceof Enum?"uint32":field.type,ref="m"+util.safeProp(field.name);gen
("case %d:",field.id);if(field.map){gen
("r.skip().pos++")
("if(%s===util.emptyObject)",ref)
("%s={}",ref)
("var k=r.%s()",field.keyType)
("r.pos++");if(types.basic[type]===undefined)gen
("%s[typeof k===\"object\"?util.longToHash(k):k]=types[%d].decode(r,r.uint32())",ref,i);else gen
("%s[typeof k===\"object\"?util.longToHash(k):k]=r.%s()",ref,type);}else if(field.repeated){gen
("if(!(%s&&%s.length))",ref,ref)
("%s=[]",ref);if((decoder.compat||field.packed)&&types.packed[type]!==undefined)gen
("if((t&7)===2){")
("var c2=r.uint32()+r.pos")
("while(r.pos<c2)")
("%s.push(r.%s())",ref,type)
("}else");if(types.basic[type]===undefined)gen(field.resolvedType.group?"%s.push(types[%d].decode(r))":"%s.push(types[%d].decode(r,r.uint32()))",ref,i);else gen
("%s.push(r.%s())",ref,type);}else if(types.basic[type]===undefined)gen(field.resolvedType.group?"%s=types[%d].decode(r)":"%s=types[%d].decode(r,r.uint32())",ref,i);else gen
("%s=r.%s()",ref,type);gen
("break");}return gen
("default:")
("r.skipType(t&7)")
("break")
("}")
("}")
("return m");}},{"15":15,"35":35,"36":36}],14:[function(require,module,exports){"use strict";module.exports=encoder;var Enum=require(15),types=require(35),util=require(36);function genTypePartial(gen,field,fieldIndex,ref){return field.resolvedType.group?gen("types[%d].encode(%s,w.uint32(%d)).uint32(%d)",fieldIndex,ref,(field.id<<3|3)>>>0,(field.id<<3|4)>>>0):gen("types[%d].encode(%s,w.uint32(%d).fork()).ldelim()",fieldIndex,ref,(field.id<<3|2)>>>0);}
function encoder(mtype){var gen=util.codegen("m","w")
("if(!w)")
("w=Writer.create()");var i,ref;for(var i=0;i<mtype.fieldsArray.length;++i){var field=mtype._fieldsArray[i].resolve();if(field.partOf)
continue;var type=field.resolvedType instanceof Enum?"uint32":field.type,wireType=types.basic[type];ref="m"+util.safeProp(field.name);if(field.map){gen
("if(%s&&m.hasOwnProperty(%j)){",ref,field.name)
("for(var ks=Object.keys(%s),i=0;i<ks.length;++i){",ref)
("w.uint32(%d).fork().uint32(%d).%s(ks[i])",(field.id<<3|2)>>>0,8|types.mapKey[field.keyType],field.keyType);if(wireType===undefined)gen
("types[%d].encode(%s[ks[i]],w.uint32(18).fork()).ldelim().ldelim()",i,ref);else gen
(".uint32(%d).%s(%s[ks[i]]).ldelim()",16|wireType,type,ref);gen
("}")
("}");}else if(field.repeated){if(field.packed&&types.packed[type]!==undefined){gen
("if(%s&&%s.length&&m.hasOwnProperty(%j)){",ref,ref,field.name)
("w.uint32(%d).fork()",(field.id<<3|2)>>>0)
("for(var i=0;i<%s.length;++i)",ref)
("w.%s(%s[i])",type,ref)
("w.ldelim()")
("}");}else{gen
("if(%s!==undefined&&m.hasOwnProperty(%j)){",ref,field.name)
("for(var i=0;i<%s.length;++i)",ref);if(wireType===undefined)
genTypePartial(gen,field,i,ref+"[i]");else gen
("w.uint32(%d).%s(%s[i])",(field.id<<3|wireType)>>>0,type,ref);gen
("}");}}else{if(!field.required){if(field.long)gen
("if(%s!==undefined&&%s!==null&&m.hasOwnProperty(%j))",ref,ref,field.name);else if(field.bytes||field.resolvedType&&!(field.resolvedType instanceof Enum))gen
("if(%s&&m.hasOwnProperty(%j))",ref,field.name);else gen
("if(%s!==undefined&&m.hasOwnProperty(%j))",ref,field.name);}
if(wireType===undefined)
genTypePartial(gen,field,i,ref);else gen
("w.uint32(%d).%s(%s)",(field.id<<3|wireType)>>>0,type,ref);}}
for(var i=0;i<mtype.oneofsArray.length;++i){var oneof=mtype._oneofsArray[i];gen
("switch(%s){","m"+util.safeProp(oneof.name));for(var j=0;j<oneof.fieldsArray.length;++j){var field=oneof.fieldsArray[j],type=field.resolvedType instanceof Enum?"uint32":field.type,wireType=types.basic[type];ref="m"+util.safeProp(field.name);gen
("case%j:",field.name);if(wireType===undefined)
genTypePartial(gen,field,mtype._fieldsArray.indexOf(field),ref);else gen
("w.uint32(%d).%s(%s)",(field.id<<3|wireType)>>>0,type,ref);gen
("break");}gen
("}");}
return gen
("return w");}},{"15":15,"35":35,"36":36}],15:[function(require,module,exports){"use strict";module.exports=Enum;var ReflectionObject=require(24);((Enum.prototype=Object.create(ReflectionObject.prototype)).constructor=Enum).className="Enum";var util=require(36);function Enum(name,values,options){ReflectionObject.call(this,name,options);if(values&&typeof values!=="object")
throw TypeError("values must be an object");this.valuesById={};this.values=Object.create(this.valuesById);this.comments={};if(values)
for(var keys=Object.keys(values),i=0;i<keys.length;++i)
this.valuesById[this.values[keys[i]]=values[keys[i]]]=keys[i];}
Enum.fromJSON=function fromJSON(name,json){return new Enum(name,json.values,json.options);};Enum.prototype.toJSON=function toJSON(){return{options:this.options,values:this.values};};Enum.prototype.add=function(name,id,comment){if(!util.isString(name))
throw TypeError("name must be a string");if(!util.isInteger(id))
throw TypeError("id must be an integer");if(this.values[name]!==undefined)
throw Error("duplicate name");if(this.valuesById[id]!==undefined){if(!(this.options&&this.options.allow_alias))
throw Error("duplicate id");this.values[name]=id;}else
this.valuesById[this.values[name]=id]=name;this.comments[name]=comment||null;return this;};Enum.prototype.remove=function(name){if(!util.isString(name))
throw TypeError("name must be a string");var val=this.values[name];if(val===undefined)
throw Error("name does not exist");delete this.valuesById[val];delete this.values[name];delete this.comments[name];return this;};},{"24":24,"36":36}],16:[function(require,module,exports){"use strict";module.exports=Field;var ReflectionObject=require(24);((Field.prototype=Object.create(ReflectionObject.prototype)).constructor=Field).className="Field";var Enum=require(15),types=require(35),util=require(36);var Type;function Field(name,id,type,rule,extend,options){if(util.isObject(rule)){options=rule;rule=extend=undefined;}else if(util.isObject(extend)){options=extend;extend=undefined;}
ReflectionObject.call(this,name,options);if(!util.isInteger(id)||id<0)
throw TypeError("id must be a non-negative integer");if(!util.isString(type))
throw TypeError("type must be a string");if(rule!==undefined&&!/^required|optional|repeated$/.test(rule=rule.toString().toLowerCase()))
throw TypeError("rule must be a string rule");if(extend!==undefined&&!util.isString(extend))
throw TypeError("extend must be a string");this.rule=rule&&rule!=="optional"?rule:undefined;this.type=type;this.id=id;this.extend=extend||undefined;this.required=rule==="required";this.optional=!this.required;this.repeated=rule==="repeated";this.map=false;this.message=null;this.partOf=null;this.typeDefault=null;this.defaultValue=null;this.long=util.Long?types.long[type]!==undefined:false;this.bytes=type==="bytes";this.resolvedType=null;this.extensionField=null;this.declaringField=null;this._packed=null;}
Object.defineProperty(Field.prototype,"packed",{get:function(){if(this._packed===null)
this._packed=this.getOption("packed")!==false;return this._packed;}});Field.prototype.setOption=function setOption(name,value,ifNotSet){if(name==="packed")
this._packed=null;return ReflectionObject.prototype.setOption.call(this,name,value,ifNotSet);};Field.fromJSON=function fromJSON(name,json){return new Field(name,json.id,json.type,json.rule,json.extend,json.options);};Field.prototype.toJSON=function toJSON(){return{rule:this.rule!=="optional"&&this.rule||undefined,type:this.type,id:this.id,extend:this.extend,options:this.options};};Field.prototype.resolve=function resolve(){if(this.resolved)
return this;if((this.typeDefault=types.defaults[this.type])===undefined){if(!Type)
Type=require(34);var scope=this.declaringField?this.declaringField.parent:this.parent;if(this.resolvedType=scope.lookup(this.type,Type))
this.typeDefault=null;else if(this.resolvedType=scope.lookup(this.type,Enum))
this.typeDefault=this.resolvedType.values[Object.keys(this.resolvedType.values)[0]];else
throw Error("unresolvable field type: "+this.type+" in "+scope);}
if(this.options&&this.options["default"]!==undefined){this.typeDefault=this.options["default"];if(this.resolvedType instanceof Enum&&typeof this.typeDefault==="string")
this.typeDefault=this.resolvedType.values[this.typeDefault];}
if(this.options&&this.options.packed!==undefined&&this.resolvedType&&!(this.resolvedType instanceof Enum))
delete this.options.packed;if(this.long){this.typeDefault=util.Long.fromNumber(this.typeDefault,this.type.charAt(0)==="u");if(Object.freeze)
Object.freeze(this.typeDefault);}else if(this.bytes&&typeof this.typeDefault==="string"){var buf;if(util.base64.test(this.typeDefault))
util.base64.decode(this.typeDefault,buf=util.newBuffer(util.base64.length(this.typeDefault)),0);else
util.utf8.write(this.typeDefault,buf=util.newBuffer(util.utf8.length(this.typeDefault)),0);this.typeDefault=buf;}
if(this.map)
this.defaultValue=util.emptyObject;else if(this.repeated)
this.defaultValue=util.emptyArray;else
this.defaultValue=this.typeDefault;return ReflectionObject.prototype.resolve.call(this);};},{"15":15,"24":24,"34":34,"35":35,"36":36}],17:[function(require,module,exports){"use strict";var protobuf=module.exports=require(18);protobuf.build="light";function load(filename,root,callback){if(typeof root==="function"){callback=root;root=new protobuf.Root();}else if(!root)
root=new protobuf.Root();return root.load(filename,callback);}
protobuf.load=load;function loadSync(filename,root){if(!root)
root=new protobuf.Root();return root.loadSync(filename);}
protobuf.loadSync=loadSync;protobuf.encoder=require(14);protobuf.decoder=require(13);protobuf.verifier=require(39);protobuf.converter=require(12);protobuf.ReflectionObject=require(24);protobuf.Namespace=require(23);protobuf.Root=require(29);protobuf.Enum=require(15);protobuf.Type=require(34);protobuf.Field=require(16);protobuf.OneOf=require(25);protobuf.MapField=require(20);protobuf.Service=require(32);protobuf.Method=require(22);protobuf.Class=require(10);protobuf.Message=require(21);protobuf.types=require(35);protobuf.util=require(36);protobuf.ReflectionObject._configure(protobuf.Root);protobuf.Namespace._configure(protobuf.Type,protobuf.Service);protobuf.Root._configure(protobuf.Type);},{"10":10,"12":12,"13":13,"14":14,"15":15,"16":16,"18":18,"20":20,"21":21,"22":22,"23":23,"24":24,"25":25,"29":29,"32":32,"34":34,"35":35,"36":36,"39":39}],18:[function(require,module,exports){"use strict";var protobuf=exports;protobuf.build="minimal";protobuf.roots={};protobuf.Writer=require(40);protobuf.BufferWriter=require(41);protobuf.Reader=require(27);protobuf.BufferReader=require(28);protobuf.util=require(38);protobuf.rpc=require(30);protobuf.configure=configure;function configure(){protobuf.Reader._configure(protobuf.BufferReader);protobuf.util._configure();}
protobuf.Writer._configure(protobuf.BufferWriter);configure();},{"27":27,"28":28,"30":30,"38":38,"40":40,"41":41}],19:[function(require,module,exports){"use strict";var protobuf=module.exports=require(17);protobuf.build="full";protobuf.tokenize=require(33);protobuf.parse=require(26);protobuf.common=require(11);protobuf.Root._configure(protobuf.Type,protobuf.parse,protobuf.common);},{"11":11,"17":17,"26":26,"33":33}],20:[function(require,module,exports){"use strict";module.exports=MapField;var Field=require(16);((MapField.prototype=Object.create(Field.prototype)).constructor=MapField).className="MapField";var types=require(35),util=require(36);function MapField(name,id,keyType,type,options){Field.call(this,name,id,type,options);if(!util.isString(keyType))
throw TypeError("keyType must be a string");this.keyType=keyType;this.resolvedKeyType=null;this.map=true;}
MapField.fromJSON=function fromJSON(name,json){return new MapField(name,json.id,json.keyType,json.type,json.options);};MapField.prototype.toJSON=function toJSON(){return{keyType:this.keyType,type:this.type,id:this.id,extend:this.extend,options:this.options};};MapField.prototype.resolve=function resolve(){if(this.resolved)
return this;if(types.mapKey[this.keyType]===undefined)
throw Error("invalid key type: "+this.keyType);return Field.prototype.resolve.call(this);};},{"16":16,"35":35,"36":36}],21:[function(require,module,exports){"use strict";module.exports=Message;var util=require(36);function Message(properties){if(properties)
for(var keys=Object.keys(properties),i=0;i<keys.length;++i)
this[keys[i]]=properties[keys[i]];}
Message.encode=function encode(message,writer){return this.$type.encode(message,writer);};Message.encodeDelimited=function encodeDelimited(message,writer){return this.$type.encodeDelimited(message,writer);};Message.decode=function decode(reader){return this.$type.decode(reader);};Message.decodeDelimited=function decodeDelimited(reader){return this.$type.decodeDelimited(reader);};Message.verify=function verify(message){return this.$type.verify(message);};Message.fromObject=function fromObject(object){return this.$type.fromObject(object);};Message.from=Message.fromObject;Message.toObject=function toObject(message,options){return this.$type.toObject(message,options);};Message.prototype.toObject=function toObject(options){return this.$type.toObject(this,options);};Message.prototype.toJSON=function toJSON(){return this.$type.toObject(this,util.toJSONOptions);};},{"36":36}],22:[function(require,module,exports){"use strict";module.exports=Method;var ReflectionObject=require(24);((Method.prototype=Object.create(ReflectionObject.prototype)).constructor=Method).className="Method";var util=require(36);function Method(name,type,requestType,responseType,requestStream,responseStream,options){if(util.isObject(requestStream)){options=requestStream;requestStream=responseStream=undefined;}else if(util.isObject(responseStream)){options=responseStream;responseStream=undefined;}
if(!(type===undefined||util.isString(type)))
throw TypeError("type must be a string");if(!util.isString(requestType))
throw TypeError("requestType must be a string");if(!util.isString(responseType))
throw TypeError("responseType must be a string");ReflectionObject.call(this,name,options);this.type=type||"rpc";this.requestType=requestType;this.requestStream=requestStream?true:undefined;this.responseType=responseType;this.responseStream=responseStream?true:undefined;this.resolvedRequestType=null;this.resolvedResponseType=null;}
Method.fromJSON=function fromJSON(name,json){return new Method(name,json.type,json.requestType,json.responseType,json.requestStream,json.responseStream,json.options);};Method.prototype.toJSON=function toJSON(){return{type:this.type!=="rpc"&&this.type||undefined,requestType:this.requestType,requestStream:this.requestStream,responseType:this.responseType,responseStream:this.responseStream,options:this.options};};Method.prototype.resolve=function resolve(){if(this.resolved)
return this;this.resolvedRequestType=this.parent.lookupType(this.requestType);this.resolvedResponseType=this.parent.lookupType(this.responseType);return ReflectionObject.prototype.resolve.call(this);};},{"24":24,"36":36}],23:[function(require,module,exports){"use strict";module.exports=Namespace;var ReflectionObject=require(24);((Namespace.prototype=Object.create(ReflectionObject.prototype)).constructor=Namespace).className="Namespace";var Enum=require(15),Field=require(16),util=require(36);var Type,Service;Namespace.fromJSON=function fromJSON(name,json){return new Namespace(name,json.options).addJSON(json.nested);};function arrayToJSON(array){if(!(array&&array.length))
return undefined;var obj={};for(var i=0;i<array.length;++i)
obj[array[i].name]=array[i].toJSON();return obj;}
Namespace.arrayToJSON=arrayToJSON;function Namespace(name,options){ReflectionObject.call(this,name,options);this.nested=undefined;this._nestedArray=null;}
function clearCache(namespace){namespace._nestedArray=null;return namespace;}
Object.defineProperty(Namespace.prototype,"nestedArray",{get:function(){return this._nestedArray||(this._nestedArray=util.toArray(this.nested));}});Namespace.prototype.toJSON=function toJSON(){return{options:this.options,nested:arrayToJSON(this.nestedArray)};};Namespace.prototype.addJSON=function addJSON(nestedJson){var ns=this;if(nestedJson){for(var names=Object.keys(nestedJson),i=0,nested;i<names.length;++i){nested=nestedJson[names[i]];ns.add((nested.fields!==undefined?Type.fromJSON:nested.values!==undefined?Enum.fromJSON:nested.methods!==undefined?Service.fromJSON:nested.id!==undefined?Field.fromJSON:Namespace.fromJSON)(names[i],nested));}}
return this;};Namespace.prototype.get=function get(name){return this.nested&&this.nested[name]||null;};Namespace.prototype.getEnum=function getEnum(name){if(this.nested&&this.nested[name]instanceof Enum)
return this.nested[name].values;throw Error("no such enum");};Namespace.prototype.add=function add(object){if(!(object instanceof Field&&object.extend!==undefined||object instanceof Type||object instanceof Enum||object instanceof Service||object instanceof Namespace))
throw TypeError("object must be a valid nested object");if(!this.nested)
this.nested={};else{var prev=this.get(object.name);if(prev){if(prev instanceof Namespace&&object instanceof Namespace&&!(prev instanceof Type||prev instanceof Service)){var nested=prev.nestedArray;for(var i=0;i<nested.length;++i)
object.add(nested[i]);this.remove(prev);if(!this.nested)
this.nested={};object.setOptions(prev.options,true);}else
throw Error("duplicate name '"+object.name+"' in "+this);}}
this.nested[object.name]=object;object.onAdd(this);return clearCache(this);};Namespace.prototype.remove=function remove(object){if(!(object instanceof ReflectionObject))
throw TypeError("object must be a ReflectionObject");if(object.parent!==this)
throw Error(object+" is not a member of "+this);delete this.nested[object.name];if(!Object.keys(this.nested).length)
this.nested=undefined;object.onRemove(this);return clearCache(this);};Namespace.prototype.define=function define(path,json){if(util.isString(path))
path=path.split(".");else if(!Array.isArray(path))
throw TypeError("illegal path");if(path&&path.length&&path[0]==="")
throw Error("path must be relative");var ptr=this;while(path.length>0){var part=path.shift();if(ptr.nested&&ptr.nested[part]){ptr=ptr.nested[part];if(!(ptr instanceof Namespace))
throw Error("path conflicts with non-namespace objects");}else
ptr.add(ptr=new Namespace(part));}
if(json)
ptr.addJSON(json);return ptr;};Namespace.prototype.resolveAll=function resolveAll(){var nested=this.nestedArray,i=0;while(i<nested.length)
if(nested[i]instanceof Namespace)
nested[i++].resolveAll();else
nested[i++].resolve();return this.resolve();};Namespace.prototype.lookup=function lookup(path,filterType,parentAlreadyChecked){if(typeof filterType==="boolean"){parentAlreadyChecked=filterType;filterType=undefined;}
if(util.isString(path)&&path.length){if(path===".")
return this.root;path=path.split(".");}else if(!path.length)
return this;if(path[0]==="")
return this.root.lookup(path.slice(1),filterType);var found=this.get(path[0]);if(found){if(path.length===1){if(!filterType||found instanceof filterType)
return found;}else if(found instanceof Namespace&&(found=found.lookup(path.slice(1),filterType,true)))
return found;}
if(this.parent===null||parentAlreadyChecked)
return null;return this.parent.lookup(path,filterType);};Namespace.prototype.lookupType=function lookupType(path){var found=this.lookup(path,Type);if(!found)
throw Error("no such type");return found;};Namespace.prototype.lookupService=function lookupService(path){var found=this.lookup(path,Service);if(!found)
throw Error("no such service");return found;};Namespace.prototype.lookupEnum=function lookupEnum(path){var found=this.lookup(path,Enum);if(!found)
throw Error("no such enum");return found.values;};Namespace._configure=function(Type_,Service_){Type=Type_;Service=Service_;};},{"15":15,"16":16,"24":24,"36":36}],24:[function(require,module,exports){"use strict";module.exports=ReflectionObject;ReflectionObject.className="ReflectionObject";var util=require(36);var Root;function ReflectionObject(name,options){if(!util.isString(name))
throw TypeError("name must be a string");if(options&&!util.isObject(options))
throw TypeError("options must be an object");this.options=options;this.name=name;this.parent=null;this.resolved=false;this.comment=null;this.filename=null;}
Object.defineProperties(ReflectionObject.prototype,{root:{get:function(){var ptr=this;while(ptr.parent!==null)
ptr=ptr.parent;return ptr;}},fullName:{get:function(){var path=[this.name],ptr=this.parent;while(ptr){path.unshift(ptr.name);ptr=ptr.parent;}
return path.join(".");}}});ReflectionObject.prototype.toJSON=function toJSON(){throw Error();};ReflectionObject.prototype.onAdd=function onAdd(parent){if(this.parent&&this.parent!==parent)
this.parent.remove(this);this.parent=parent;this.resolved=false;var root=parent.root;if(root instanceof Root)
root._handleAdd(this);};ReflectionObject.prototype.onRemove=function onRemove(parent){var root=parent.root;if(root instanceof Root)
root._handleRemove(this);this.parent=null;this.resolved=false;};ReflectionObject.prototype.resolve=function resolve(){if(this.resolved)
return this;if(this.root instanceof Root)
this.resolved=true;return this;};ReflectionObject.prototype.getOption=function getOption(name){if(this.options)
return this.options[name];return undefined;};ReflectionObject.prototype.setOption=function setOption(name,value,ifNotSet){if(!ifNotSet||!this.options||this.options[name]===undefined)
(this.options||(this.options={}))[name]=value;return this;};ReflectionObject.prototype.setOptions=function setOptions(options,ifNotSet){if(options)
for(var keys=Object.keys(options),i=0;i<keys.length;++i)
this.setOption(keys[i],options[keys[i]],ifNotSet);return this;};ReflectionObject.prototype.toString=function toString(){var className=this.constructor.className,fullName=this.fullName;if(fullName.length)
return className+" "+fullName;return className;};ReflectionObject._configure=function(Root_){Root=Root_;};},{"36":36}],25:[function(require,module,exports){"use strict";module.exports=OneOf;var ReflectionObject=require(24);((OneOf.prototype=Object.create(ReflectionObject.prototype)).constructor=OneOf).className="OneOf";var Field=require(16);function OneOf(name,fieldNames,options){if(!Array.isArray(fieldNames)){options=fieldNames;fieldNames=undefined;}
ReflectionObject.call(this,name,options);if(!(fieldNames===undefined||Array.isArray(fieldNames)))
throw TypeError("fieldNames must be an Array");this.oneof=fieldNames||[];this.fieldsArray=[];}
OneOf.fromJSON=function fromJSON(name,json){return new OneOf(name,json.oneof,json.options);};OneOf.prototype.toJSON=function toJSON(){return{oneof:this.oneof,options:this.options};};function addFieldsToParent(oneof){if(oneof.parent)
for(var i=0;i<oneof.fieldsArray.length;++i)
if(!oneof.fieldsArray[i].parent)
oneof.parent.add(oneof.fieldsArray[i]);}
OneOf.prototype.add=function add(field){if(!(field instanceof Field))
throw TypeError("field must be a Field");if(field.parent&&field.parent!==this.parent)
field.parent.remove(field);this.oneof.push(field.name);this.fieldsArray.push(field);field.partOf=this;addFieldsToParent(this);return this;};OneOf.prototype.remove=function remove(field){if(!(field instanceof Field))
throw TypeError("field must be a Field");var index=this.fieldsArray.indexOf(field);if(index<0)
throw Error(field+" is not a member of "+this);this.fieldsArray.splice(index,1);index=this.oneof.indexOf(field.name);if(index>-1)
this.oneof.splice(index,1);field.partOf=null;return this;};OneOf.prototype.onAdd=function onAdd(parent){ReflectionObject.prototype.onAdd.call(this,parent);var self=this;for(var i=0;i<this.oneof.length;++i){var field=parent.get(this.oneof[i]);if(field&&!field.partOf){field.partOf=self;self.fieldsArray.push(field);}}
addFieldsToParent(this);};OneOf.prototype.onRemove=function onRemove(parent){for(var i=0,field;i<this.fieldsArray.length;++i)
if((field=this.fieldsArray[i]).parent)
field.parent.remove(field);ReflectionObject.prototype.onRemove.call(this,parent);};},{"16":16,"24":24}],26:[function(require,module,exports){"use strict";module.exports=parse;parse.filename=null;parse.defaults={keepCase:false};var tokenize=require(33),Root=require(29),Type=require(34),Field=require(16),MapField=require(20),OneOf=require(25),Enum=require(15),Service=require(32),Method=require(22),types=require(35),util=require(36);function isName(token){return/^[a-zA-Z_][a-zA-Z_0-9]*$/.test(token);}
function isTypeRef(token){return/^(?:\.?[a-zA-Z_][a-zA-Z_0-9]*)+$/.test(token);}
function isFqTypeRef(token){return/^(?:\.[a-zA-Z][a-zA-Z_0-9]*)+$/.test(token);}
function lower(token){return token===null?null:token.toLowerCase();}
function camelCase(str){return str.substring(0,1)
+str.substring(1).replace(/_([a-z])(?=[a-z]|$)/g,function($0,$1){return $1.toUpperCase();});}
function parse(source,root,options){if(!(root instanceof Root)){options=root;root=new Root();}
if(!options)
options=parse.defaults;var tn=tokenize(source),next=tn.next,push=tn.push,peek=tn.peek,skip=tn.skip,cmnt=tn.cmnt;var head=true,pkg,imports,weakImports,syntax,isProto3=false;var ptr=root;var applyCase=options.keepCase?function(name){return name;}:camelCase;function illegal(token,name,insideTryCatch){var filename=parse.filename;if(!insideTryCatch)
parse.filename=null;return Error("illegal "+(name||"token")+" '"+token+"' ("+(filename?filename+", ":"")+"line "+tn.line()+")");}
function readString(){var values=[],token;do{if((token=next())!=="\""&&token!=="'")
throw illegal(token);values.push(next());skip(token);token=peek();}while(token==="\""||token==="'");return values.join("");}
function readValue(acceptTypeRef){var token=next();switch(lower(token)){case"'":case"\"":push(token);return readString();case"true":return true;case"false":return false;}
try{return parseNumber(token,true);}catch(e){if(acceptTypeRef&&isTypeRef(token))
return token;throw illegal(token,"value");}}
function readRanges(target,acceptStrings){var token,start;do{if(acceptStrings&&((token=peek())==="\""||token==="'"))
target.push(readString());else
target.push([start=parseId(next()),skip("to",true)?parseId(next()):start]);}while(skip(",",true));skip(";");}
function parseNumber(token,insideTryCatch){var sign=1;if(token.charAt(0)==="-"){sign=-1;token=token.substring(1);}
var tokenLower=lower(token);switch(tokenLower){case"inf":return sign*Infinity;case"nan":return NaN;case"0":return 0;}
if(/^[1-9][0-9]*$/.test(token))
return sign*parseInt(token,10);if(/^0[x][0-9a-f]+$/.test(tokenLower))
return sign*parseInt(token,16);if(/^0[0-7]+$/.test(token))
return sign*parseInt(token,8);if(/^(?!e)[0-9]*(?:\.[0-9]*)?(?:[e][+-]?[0-9]+)?$/.test(tokenLower))
return sign*parseFloat(token);throw illegal(token,"number",insideTryCatch);}
function parseId(token,acceptNegative){var tokenLower=lower(token);switch(tokenLower){case"max":return 536870911;case"0":return 0;}
if(token.charAt(0)==="-"&&!acceptNegative)
throw illegal(token,"id");if(/^-?[1-9][0-9]*$/.test(token))
return parseInt(token,10);if(/^-?0[x][0-9a-f]+$/.test(tokenLower))
return parseInt(token,16);if(/^-?0[0-7]+$/.test(token))
return parseInt(token,8);throw illegal(token,"id");}
function parsePackage(){if(pkg!==undefined)
throw illegal("package");pkg=next();if(!isTypeRef(pkg))
throw illegal(pkg,"name");ptr=ptr.define(pkg);skip(";");}
function parseImport(){var token=peek();var whichImports;switch(token){case"weak":whichImports=weakImports||(weakImports=[]);next();break;case"public":next();default:whichImports=imports||(imports=[]);break;}
token=readString();skip(";");whichImports.push(token);}
function parseSyntax(){skip("=");syntax=lower(readString());isProto3=syntax==="proto3";if(!isProto3&&syntax!=="proto2")
throw illegal(syntax,"syntax");skip(";");}
function parseCommon(parent,token){switch(token){case"option":parseOption(parent,token);skip(";");return true;case"message":parseType(parent,token);return true;case"enum":parseEnum(parent,token);return true;case"service":parseService(parent,token);return true;case"extend":parseExtension(parent,token);return true;}
return false;}
function parseType(parent,token){var name=next();if(!isName(name))
throw illegal(name,"type name");var type=new Type(name);type.comment=cmnt();type.filename=parse.filename;if(skip("{",true)){while((token=next())!=="}"){var tokenLower=lower(token);if(parseCommon(type,token))
continue;switch(tokenLower){case"map":parseMapField(type,tokenLower);break;case"required":case"optional":case"repeated":parseField(type,tokenLower);break;case"oneof":parseOneOf(type,tokenLower);break;case"extensions":readRanges(type.extensions||(type.extensions=[]));break;case"reserved":readRanges(type.reserved||(type.reserved=[]),true);break;default:if(!isProto3||!isTypeRef(token))
throw illegal(token);push(token);parseField(type,"optional");break;}}
skip(";",true);}else
skip(";");parent.add(type);}
function parseField(parent,rule,extend){var type=next();if(type==="group"){parseGroup(parent,rule);return;}
if(!isTypeRef(type))
throw illegal(type,"type");var name=next();if(!isName(name))
throw illegal(name,"name");name=applyCase(name);skip("=");var field=new Field(name,parseId(next()),type,rule,extend),trailingLine=tn.line();field.comment=cmnt();field.filename=parse.filename;parseInlineOptions(field);if(!field.comment)
field.comment=cmnt(trailingLine);if(!isProto3&&field.repeated)
field.setOption("packed",false,true);parent.add(field);}
function parseGroup(parent,rule){var name=next();if(!isName(name))
throw illegal(name,"name");var fieldName=util.lcFirst(name);if(name===fieldName)
name=util.ucFirst(name);skip("=");var id=parseId(next());var type=new Type(name);type.group=true;type.comment=cmnt();var field=new Field(fieldName,id,name,rule);type.filename=field.filename=parse.filename;skip("{");while((token=next())!=="}"){switch(token=lower(token)){case"option":parseOption(type,token);skip(";");break;case"required":case"optional":case"repeated":parseField(type,token);break;default:throw illegal(token);}}
skip(";",true);parent.add(type).add(field);}
function parseMapField(parent){skip("<");var keyType=next();if(types.mapKey[keyType]===undefined)
throw illegal(keyType,"type");skip(",");var valueType=next();if(!isTypeRef(valueType))
throw illegal(valueType,"type");skip(">");var name=next();if(!isName(name))
throw illegal(name,"name");name=applyCase(name);skip("=");var field=new MapField(name,parseId(next()),keyType,valueType),trailingLine=tn.line();field.comment=cmnt();field.filename=parse.filename;parseInlineOptions(field);if(!field.comment)
field.comment=cmnt(trailingLine);parent.add(field);}
function parseOneOf(parent,token){var name=next();if(!isName(name))
throw illegal(name,"name");name=applyCase(name);var oneof=new OneOf(name),trailingLine=tn.line();oneof.comment=cmnt();oneof.filename=parse.filename;if(skip("{",true)){while((token=next())!=="}"){if(token==="option"){parseOption(oneof,token);skip(";");}else{push(token);parseField(oneof,"optional");}}
skip(";",true);}else{skip(";");if(!oneof.comment)
oneof.comment=cmnt(trailingLine);}
parent.add(oneof);}
function parseEnum(parent,token){var name=next();if(!isName(name))
throw illegal(name,"name");var enm=new Enum(name);enm.comment=cmnt();enm.filename=parse.filename;if(skip("{",true)){while((token=next())!=="}"){if(lower(token)==="option"){parseOption(enm,token);skip(";");}else
parseEnumValue(enm,token);}
skip(";",true);}else
skip(";");parent.add(enm);}
function parseEnumValue(parent,token){if(!isName(token))
throw illegal(token,"name");var name=token;skip("=");var value=parseId(next(),true),trailingLine=tn.line();parent.add(name,value,cmnt());parseInlineOptions({});if(!parent.comments[name])
parent.comments[name]=cmnt(trailingLine);}
function parseOption(parent,token){var custom=skip("(",true);var name=next();if(!isTypeRef(name))
throw illegal(name,"name");if(custom){skip(")");name="("+name+")";token=peek();if(isFqTypeRef(token)){name+=token;next();}}
skip("=");parseOptionValue(parent,name);}
function parseOptionValue(parent,name){if(skip("{",true)){do{if(!isName(token=next()))
throw illegal(token,"name");if(peek()==="{")
parseOptionValue(parent,name+"."+token);else{skip(":");setOption(parent,name+"."+token,readValue(true));}}while(!skip("}",true));}else
setOption(parent,name,readValue(true));}
function setOption(parent,name,value){if(parent.setOption)
parent.setOption(name,value);}
function parseInlineOptions(parent){if(skip("[",true)){do{parseOption(parent,"option");}while(skip(",",true));skip("]");}
skip(";");return parent;}
function parseService(parent,token){token=next();if(!isName(token))
throw illegal(token,"service name");var name=token;var service=new Service(name);service.comment=cmnt();service.filename=parse.filename;if(skip("{",true)){while((token=next())!=="}"){var tokenLower=lower(token);switch(tokenLower){case"option":parseOption(service,tokenLower);skip(";");break;case"rpc":parseMethod(service,tokenLower);break;default:throw illegal(token);}}
skip(";",true);}else
skip(";");parent.add(service);}
function parseMethod(parent,token){var type=token;var name=next();if(!isName(name))
throw illegal(name,"name");var requestType,requestStream,responseType,responseStream;skip("(");if(skip("stream",true))
requestStream=true;if(!isTypeRef(token=next()))
throw illegal(token);requestType=token;skip(")");skip("returns");skip("(");if(skip("stream",true))
responseStream=true;if(!isTypeRef(token=next()))
throw illegal(token);responseType=token;skip(")");var method=new Method(name,type,requestType,responseType,requestStream,responseStream),trailingLine=tn.line();method.comment=cmnt();method.filename=parse.filename;if(skip("{",true)){while((token=next())!=="}"){var tokenLower=lower(token);switch(tokenLower){case"option":parseOption(method,tokenLower);skip(";");break;default:throw illegal(token);}}
skip(";",true);}else{skip(";");if(!method.comment)
method.comment=cmnt(trailingLine);}
parent.add(method);}
function parseExtension(parent,token){var reference=next();if(!isTypeRef(reference))
throw illegal(reference,"reference");if(skip("{",true)){while((token=next())!=="}"){var tokenLower=lower(token);switch(tokenLower){case"required":case"repeated":case"optional":parseField(parent,tokenLower,reference);break;default:if(!isProto3||!isTypeRef(token))
throw illegal(token);push(token);parseField(parent,"optional",reference);break;}}
skip(";",true);}else
skip(";");}
var token;while((token=next())!==null){var tokenLower=lower(token);switch(tokenLower){case"package":if(!head)
throw illegal(token);parsePackage();break;case"import":if(!head)
throw illegal(token);parseImport();break;case"syntax":if(!head)
throw illegal(token);parseSyntax();break;case"option":if(!head)
throw illegal(token);parseOption(ptr,token);skip(";");break;default:if(parseCommon(ptr,token)){head=false;continue;}
throw illegal(token);}}
parse.filename=null;return{"package":pkg,"imports":imports,weakImports:weakImports,syntax:syntax,root:root};}},{"15":15,"16":16,"20":20,"22":22,"25":25,"29":29,"32":32,"33":33,"34":34,"35":35,"36":36}],27:[function(require,module,exports){"use strict";module.exports=Reader;var util=require(38);var BufferReader;var LongBits=util.LongBits,utf8=util.utf8;function indexOutOfRange(reader,writeLength){return RangeError("index out of range: "+reader.pos+" + "+(writeLength||1)+" > "+reader.len);}
function Reader(buffer){this.buf=buffer;this.pos=0;this.len=buffer.length;}
Reader.create=util.Buffer?function create_buffer_setup(buffer){return(Reader.create=function create_buffer(buffer){return util.Buffer.isBuffer(buffer)?new BufferReader(buffer):new Reader(buffer);})(buffer);}:function create_array(buffer){return new Reader(buffer);};Reader.prototype._slice=util.Array.prototype.subarray||util.Array.prototype.slice;Reader.prototype.uint32=(function read_uint32_setup(){var value=4294967295;return function read_uint32(){value=(this.buf[this.pos]&127)>>>0;if(this.buf[this.pos++]<128)return value;value=(value|(this.buf[this.pos]&127)<<7)>>>0;if(this.buf[this.pos++]<128)return value;value=(value|(this.buf[this.pos]&127)<<14)>>>0;if(this.buf[this.pos++]<128)return value;value=(value|(this.buf[this.pos]&127)<<21)>>>0;if(this.buf[this.pos++]<128)return value;value=(value|(this.buf[this.pos]&15)<<28)>>>0;if(this.buf[this.pos++]<128)return value;if((this.pos+=5)>this.len){this.pos=this.len;throw indexOutOfRange(this,10);}
return value;};})();Reader.prototype.int32=function read_int32(){return this.uint32()|0;};Reader.prototype.sint32=function read_sint32(){var value=this.uint32();return value>>>1^-(value&1)|0;};function readLongVarint(){var bits=new LongBits(0>>>0,0>>>0);var i=0;if(this.len-this.pos>4){for(;i<4;++i){bits.lo=(bits.lo|(this.buf[this.pos]&127)<<i*7)>>>0;if(this.buf[this.pos++]<128)
return bits;}
bits.lo=(bits.lo|(this.buf[this.pos]&127)<<28)>>>0;bits.hi=(bits.hi|(this.buf[this.pos]&127)>>4)>>>0;if(this.buf[this.pos++]<128)
return bits;i=0;}else{for(;i<3;++i){if(this.pos>=this.len)
throw indexOutOfRange(this);bits.lo=(bits.lo|(this.buf[this.pos]&127)<<i*7)>>>0;if(this.buf[this.pos++]<128)
return bits;}
bits.lo=(bits.lo|(this.buf[this.pos++]&127)<<i*7)>>>0;return bits;}
if(this.len-this.pos>4){for(;i<5;++i){bits.hi=(bits.hi|(this.buf[this.pos]&127)<<i*7+3)>>>0;if(this.buf[this.pos++]<128)
return bits;}}else{for(;i<5;++i){if(this.pos>=this.len)
throw indexOutOfRange(this);bits.hi=(bits.hi|(this.buf[this.pos]&127)<<i*7+3)>>>0;if(this.buf[this.pos++]<128)
return bits;}}
throw Error("invalid varint encoding");}
function read_int64_long(){return readLongVarint.call(this).toLong();}
function read_int64_number(){return readLongVarint.call(this).toNumber();}
function read_uint64_long(){return readLongVarint.call(this).toLong(true);}
function read_uint64_number(){return readLongVarint.call(this).toNumber(true);}
function read_sint64_long(){return readLongVarint.call(this).zzDecode().toLong();}
function read_sint64_number(){return readLongVarint.call(this).zzDecode().toNumber();}
Reader.prototype.bool=function read_bool(){return this.uint32()!==0;};function readFixed32(buf,end){return(buf[end-4]|buf[end-3]<<8|buf[end-2]<<16|buf[end-1]<<24)>>>0;}
Reader.prototype.fixed32=function read_fixed32(){if(this.pos+4>this.len)
throw indexOutOfRange(this,4);return readFixed32(this.buf,this.pos+=4);};Reader.prototype.sfixed32=function read_sfixed32(){var value=this.fixed32();return value>>>1^-(value&1);};function readFixed64(){if(this.pos+8>this.len)
throw indexOutOfRange(this,8);return new LongBits(readFixed32(this.buf,this.pos+=4),readFixed32(this.buf,this.pos+=4));}
function read_fixed64_long(){return readFixed64.call(this).toLong(true);}
function read_fixed64_number(){return readFixed64.call(this).toNumber(true);}
function read_sfixed64_long(){return readFixed64.call(this).zzDecode().toLong();}
function read_sfixed64_number(){return readFixed64.call(this).zzDecode().toNumber();}
var readFloat=typeof Float32Array!=="undefined"?(function(){var f32=new Float32Array(1),f8b=new Uint8Array(f32.buffer);f32[0]=-0;return f8b[3]?function readFloat_f32(buf,pos){f8b[0]=buf[pos];f8b[1]=buf[pos+1];f8b[2]=buf[pos+2];f8b[3]=buf[pos+3];return f32[0];}:function readFloat_f32_le(buf,pos){f8b[3]=buf[pos];f8b[2]=buf[pos+1];f8b[1]=buf[pos+2];f8b[0]=buf[pos+3];return f32[0];};})():function readFloat_ieee754(buf,pos){var uint=readFixed32(buf,pos+4),sign=(uint>>31)*2+1,exponent=uint>>>23&255,mantissa=uint&8388607;return exponent===255?mantissa?NaN:sign*Infinity:exponent===0?sign*1.401298464324817e-45*mantissa:sign*Math.pow(2,exponent-150)*(mantissa+8388608);};Reader.prototype.float=function read_float(){if(this.pos+4>this.len)
throw indexOutOfRange(this,4);var value=readFloat(this.buf,this.pos);this.pos+=4;return value;};var readDouble=typeof Float64Array!=="undefined"?(function(){var f64=new Float64Array(1),f8b=new Uint8Array(f64.buffer);f64[0]=-0;return f8b[7]?function readDouble_f64(buf,pos){f8b[0]=buf[pos];f8b[1]=buf[pos+1];f8b[2]=buf[pos+2];f8b[3]=buf[pos+3];f8b[4]=buf[pos+4];f8b[5]=buf[pos+5];f8b[6]=buf[pos+6];f8b[7]=buf[pos+7];return f64[0];}:function readDouble_f64_le(buf,pos){f8b[7]=buf[pos];f8b[6]=buf[pos+1];f8b[5]=buf[pos+2];f8b[4]=buf[pos+3];f8b[3]=buf[pos+4];f8b[2]=buf[pos+5];f8b[1]=buf[pos+6];f8b[0]=buf[pos+7];return f64[0];};})():function readDouble_ieee754(buf,pos){var lo=readFixed32(buf,pos+4),hi=readFixed32(buf,pos+8);var sign=(hi>>31)*2+1,exponent=hi>>>20&2047,mantissa=4294967296*(hi&1048575)+lo;return exponent===2047?mantissa?NaN:sign*Infinity:exponent===0?sign*5e-324*mantissa:sign*Math.pow(2,exponent-1075)*(mantissa+4503599627370496);};Reader.prototype.double=function read_double(){if(this.pos+8>this.len)
throw indexOutOfRange(this,4);var value=readDouble(this.buf,this.pos);this.pos+=8;return value;};Reader.prototype.bytes=function read_bytes(){var length=this.uint32(),start=this.pos,end=this.pos+length;if(end>this.len)
throw indexOutOfRange(this,length);this.pos+=length;return start===end?new this.buf.constructor(0):this._slice.call(this.buf,start,end);};Reader.prototype.string=function read_string(){var bytes=this.bytes();return utf8.read(bytes,0,bytes.length);};Reader.prototype.skip=function skip(length){if(typeof length==="number"){if(this.pos+length>this.len)
throw indexOutOfRange(this,length);this.pos+=length;}else{do{if(this.pos>=this.len)
throw indexOutOfRange(this);}while(this.buf[this.pos++]&128);}
return this;};Reader.prototype.skipType=function(wireType){switch(wireType){case 0:this.skip();break;case 1:this.skip(8);break;case 2:this.skip(this.uint32());break;case 3:do{if((wireType=this.uint32()&7)===4)
break;this.skipType(wireType);}while(true);break;case 5:this.skip(4);break;default:throw Error("invalid wire type "+wireType+" at offset "+this.pos);}
return this;};Reader._configure=function(BufferReader_){BufferReader=BufferReader_;if(util.Long){Reader.prototype.int64=read_int64_long;Reader.prototype.uint64=read_uint64_long;Reader.prototype.sint64=read_sint64_long;Reader.prototype.fixed64=read_fixed64_long;Reader.prototype.sfixed64=read_sfixed64_long;}else{Reader.prototype.int64=read_int64_number;Reader.prototype.uint64=read_uint64_number;Reader.prototype.sint64=read_sint64_number;Reader.prototype.fixed64=read_fixed64_number;Reader.prototype.sfixed64=read_sfixed64_number;}};},{"38":38}],28:[function(require,module,exports){"use strict";module.exports=BufferReader;var Reader=require(27);(BufferReader.prototype=Object.create(Reader.prototype)).constructor=BufferReader;var util=require(38);function BufferReader(buffer){Reader.call(this,buffer);}
if(util.Buffer)
BufferReader.prototype._slice=util.Buffer.prototype.slice;BufferReader.prototype.string=function read_string_buffer(){var len=this.uint32();return this.buf.utf8Slice(this.pos,this.pos=Math.min(this.pos+len,this.len));};},{"27":27,"38":38}],29:[function(require,module,exports){"use strict";module.exports=Root;var Namespace=require(23);((Root.prototype=Object.create(Namespace.prototype)).constructor=Root).className="Root";var Field=require(16),Enum=require(15),util=require(36);var Type,parse,common;function Root(options){Namespace.call(this,"",options);this.deferred=[];this.files=[];}
Root.fromJSON=function fromJSON(json,root){if(!root)
root=new Root();if(json.options)
root.setOptions(json.options);return root.addJSON(json.nested);};Root.prototype.resolvePath=util.path.resolve;function SYNC(){}
Root.prototype.load=function load(filename,options,callback){if(typeof options==="function"){callback=options;options=undefined;}
var self=this;if(!callback)
return util.asPromise(load,self,filename);var sync=callback===SYNC;function finish(err,root){if(!callback)
return;var cb=callback;callback=null;if(sync)
throw err;cb(err,root);}
function process(filename,source){try{if(util.isString(source)&&source.charAt(0)==="{")
source=JSON.parse(source);if(!util.isString(source))
self.setOptions(source.options).addJSON(source.nested);else{parse.filename=filename;var parsed=parse(source,self,options),resolved,i=0;if(parsed.imports)
for(;i<parsed.imports.length;++i)
if(resolved=self.resolvePath(filename,parsed.imports[i]))
fetch(resolved);if(parsed.weakImports)
for(i=0;i<parsed.weakImports.length;++i)
if(resolved=self.resolvePath(filename,parsed.weakImports[i]))
fetch(resolved,true);}}catch(err){finish(err);}
if(!sync&&!queued)
finish(null,self);}
function fetch(filename,weak){var idx=filename.lastIndexOf("google/protobuf/");if(idx>-1){var altname=filename.substring(idx);if(altname in common)
filename=altname;}
if(self.files.indexOf(filename)>-1)
return;self.files.push(filename);if(filename in common){if(sync)
process(filename,common[filename]);else{++queued;setTimeout(function(){--queued;process(filename,common[filename]);});}
return;}
if(sync){var source;try{source=util.fs.readFileSync(filename).toString("utf8");}catch(err){if(!weak)
finish(err);return;}
process(filename,source);}else{++queued;util.fetch(filename,function(err,source){--queued;if(!callback)
return;if(err){if(!weak)
finish(err);else if(!queued)
finish(null,self);return;}
process(filename,source);});}}
var queued=0;if(util.isString(filename))
filename=[filename];for(var i=0,resolved;i<filename.length;++i)
if(resolved=self.resolvePath("",filename[i]))
fetch(resolved);if(sync)
return self;if(!queued)
finish(null,self);return undefined;};Root.prototype.loadSync=function loadSync(filename,options){if(!util.isNode)
throw Error("not supported");return this.load(filename,options,SYNC);};Root.prototype.resolveAll=function resolveAll(){if(this.deferred.length)
throw Error("unresolvable extensions: "+this.deferred.map(function(field){return"'extend "+field.extend+"' in "+field.parent.fullName;}).join(", "));return Namespace.prototype.resolveAll.call(this);};var exposeRe=/^[A-Z]/;function tryHandleExtension(root,field){var extendedType=field.parent.lookup(field.extend);if(extendedType){var sisterField=new Field(field.fullName,field.id,field.type,field.rule,undefined,field.options);sisterField.declaringField=field;field.extensionField=sisterField;extendedType.add(sisterField);return true;}
return false;}
Root.prototype._handleAdd=function _handleAdd(object){if(object instanceof Field){if(object.extend!==undefined&&!object.extensionField)
if(!tryHandleExtension(this,object))
this.deferred.push(object);}else if(object instanceof Enum){if(exposeRe.test(object.name))
object.parent[object.name]=object.values;}else{if(object instanceof Type)
for(var i=0;i<this.deferred.length;)
if(tryHandleExtension(this,this.deferred[i]))
this.deferred.splice(i,1);else
++i;for(var j=0;j<object.nestedArray.length;++j)
this._handleAdd(object._nestedArray[j]);if(exposeRe.test(object.name))
object.parent[object.name]=object;}};Root.prototype._handleRemove=function _handleRemove(object){if(object instanceof Field){if(object.extend!==undefined){if(object.extensionField){object.extensionField.parent.remove(object.extensionField);object.extensionField=null;}else{var index=this.deferred.indexOf(object);if(index>-1)
this.deferred.splice(index,1);}}}else if(object instanceof Enum){if(exposeRe.test(object.name))
delete object.parent[object.name];}else if(object instanceof Namespace){for(var i=0;i<object.nestedArray.length;++i)
this._handleRemove(object._nestedArray[i]);if(exposeRe.test(object.name))
delete object.parent[object.name];}};Root._configure=function(Type_,parse_,common_){Type=Type_;parse=parse_;common=common_;};},{"15":15,"16":16,"23":23,"36":36}],30:[function(require,module,exports){"use strict";var rpc=exports;rpc.Service=require(31);},{"31":31}],31:[function(require,module,exports){"use strict";module.exports=Service;var util=require(38);(Service.prototype=Object.create(util.EventEmitter.prototype)).constructor=Service;function Service(rpcImpl,requestDelimited,responseDelimited){if(typeof rpcImpl!=="function")
throw TypeError("rpcImpl must be a function");util.EventEmitter.call(this);this.rpcImpl=rpcImpl;this.requestDelimited=Boolean(requestDelimited);this.responseDelimited=Boolean(responseDelimited);}
Service.prototype.rpcCall=function rpcCall(method,requestCtor,responseCtor,request,callback){if(!request)
throw TypeError("request must be specified");var self=this;if(!callback)
return util.asPromise(rpcCall,self,method,requestCtor,responseCtor,request);if(!self.rpcImpl){setTimeout(function(){callback(Error("already ended"));},0);return undefined;}
try{return self.rpcImpl(method,requestCtor[self.requestDelimited?"encodeDelimited":"encode"](request).finish(),function rpcCallback(err,response){if(err){self.emit("error",err,method);return callback(err);}
if(response===null){self.end(true);return undefined;}
if(!(response instanceof responseCtor)){try{response=responseCtor[self.responseDelimited?"decodeDelimited":"decode"](response);}catch(err){self.emit("error",err,method);return callback(err);}}
self.emit("data",response,method);return callback(null,response);});}catch(err){self.emit("error",err,method);setTimeout(function(){callback(err);},0);return undefined;}};Service.prototype.end=function end(endedByRPC){if(this.rpcImpl){if(!endedByRPC)
this.rpcImpl(null,null,null);this.rpcImpl=null;this.emit("end").off();}
return this;};},{"38":38}],32:[function(require,module,exports){"use strict";module.exports=Service;var Namespace=require(23);((Service.prototype=Object.create(Namespace.prototype)).constructor=Service).className="Service";var Method=require(22),util=require(36),rpc=require(30);function Service(name,options){Namespace.call(this,name,options);this.methods={};this._methodsArray=null;}
Service.fromJSON=function fromJSON(name,json){var service=new Service(name,json.options);if(json.methods)
for(var names=Object.keys(json.methods),i=0;i<names.length;++i)
service.add(Method.fromJSON(names[i],json.methods[names[i]]));return service;};Object.defineProperty(Service.prototype,"methodsArray",{get:function(){return this._methodsArray||(this._methodsArray=util.toArray(this.methods));}});function clearCache(service){service._methodsArray=null;return service;}
Service.prototype.toJSON=function toJSON(){var inherited=Namespace.prototype.toJSON.call(this);return{options:inherited&&inherited.options||undefined,methods:Namespace.arrayToJSON(this.methodsArray)||{},nested:inherited&&inherited.nested||undefined};};Service.prototype.get=function get(name){return this.methods[name]||Namespace.prototype.get.call(this,name);};Service.prototype.resolveAll=function resolveAll(){var methods=this.methodsArray;for(var i=0;i<methods.length;++i)
methods[i].resolve();return Namespace.prototype.resolve.call(this);};Service.prototype.add=function add(object){if(this.get(object.name))
throw Error("duplicate name '"+object.name+"' in "+this);if(object instanceof Method){this.methods[object.name]=object;object.parent=this;return clearCache(this);}
return Namespace.prototype.add.call(this,object);};Service.prototype.remove=function remove(object){if(object instanceof Method){if(this.methods[object.name]!==object)
throw Error(object+" is not a member of "+this);delete this.methods[object.name];object.parent=null;return clearCache(this);}
return Namespace.prototype.remove.call(this,object);};Service.prototype.create=function create(rpcImpl,requestDelimited,responseDelimited){var rpcService=new rpc.Service(rpcImpl,requestDelimited,responseDelimited);for(var i=0;i<this.methodsArray.length;++i){rpcService[util.lcFirst(this._methodsArray[i].resolve().name)]=util.codegen("r","c")("return this.rpcCall(m,q,s,r,c)").eof(util.lcFirst(this._methodsArray[i].name),{m:this._methodsArray[i],q:this._methodsArray[i].resolvedRequestType.ctor,s:this._methodsArray[i].resolvedResponseType.ctor});}
return rpcService;};},{"22":22,"23":23,"30":30,"36":36}],33:[function(require,module,exports){"use strict";module.exports=tokenize;var delimRe=/[\s{}=;:[\],'"()<>]/g,stringDoubleRe=/(?:"([^"\\]*(?:\\.[^"\\]*)*)")/g,stringSingleRe=/(?:'([^'\\]*(?:\\.[^'\\]*)*)')/g;function unescape(str){return str.replace(/\\(.?)/g,function($0,$1){switch($1){case"\\":case"":return $1;default:return unescape.map[$1]||"";}});}
unescape.map={"0":"\0","r":"\r","n":"\n","t":"\t"};tokenize.unescape=unescape;function tokenize(source){source=source.toString();var offset=0,length=source.length,line=1,commentType=null,commentText=null,commentLine=0;var stack=[];var stringDelim=null;function illegal(subject){return Error("illegal "+subject+" (line "+line+")");}
function readString(){var re=stringDelim==="'"?stringSingleRe:stringDoubleRe;re.lastIndex=offset-1;var match=re.exec(source);if(!match)
throw illegal("string");offset=re.lastIndex;push(stringDelim);stringDelim=null;return unescape(match[1]);}
function charAt(pos){return source.charAt(pos);}
function setComment(start,end){commentType=source.charAt(start++);commentLine=line;var lines=source.substring(start,end).split(/\n/g);for(var i=0;i<lines.length;++i)
lines[i]=lines[i].replace(/^ *[*/]+ */,"").trim();commentText=lines.join("\n").trim();}
function next(){if(stack.length>0)
return stack.shift();if(stringDelim)
return readString();var repeat,prev,curr,start,isComment;do{if(offset===length)
return null;repeat=false;while(/\s/.test(curr=charAt(offset))){if(curr==="\n")
++line;if(++offset===length)
return null;}
if(charAt(offset)==="/"){if(++offset===length)
throw illegal("comment");if(charAt(offset)==="/"){isComment=charAt(start=offset+1)==="/";while(charAt(++offset)!=="\n")
if(offset===length)
return null;++offset;if(isComment)
setComment(start,offset-1);++line;repeat=true;}else if((curr=charAt(offset))==="*"){isComment=charAt(start=offset+1)==="*";do{if(curr==="\n")
++line;if(++offset===length)
throw illegal("comment");prev=curr;curr=charAt(offset);}while(prev!=="*"||curr!=="/");++offset;if(isComment)
setComment(start,offset-2);repeat=true;}else
return"/";}}while(repeat);var end=offset;delimRe.lastIndex=0;var delim=delimRe.test(charAt(end++));if(!delim)
while(end<length&&!delimRe.test(charAt(end)))
++end;var token=source.substring(offset,offset=end);if(token==="\""||token==="'")
stringDelim=token;return token;}
function push(token){stack.push(token);}
function peek(){if(!stack.length){var token=next();if(token===null)
return null;push(token);}
return stack[0];}
function skip(expected,optional){var actual=peek(),equals=actual===expected;if(equals){next();return true;}
if(!optional)
throw illegal("token '"+actual+"', '"+expected+"' expected");return false;}
return{next:next,peek:peek,push:push,skip:skip,line:function(){return line;},cmnt:function(trailingLine){var ret;if(trailingLine===undefined)
ret=commentLine===line-1&&commentText||null;else{if(!commentText)
peek();ret=commentLine===trailingLine&&commentType==="/"&&commentText||null;}
if(ret){commentType=commentText=null;commentLine=0;}
return ret;}};}},{}],34:[function(require,module,exports){"use strict";module.exports=Type;var Namespace=require(23);((Type.prototype=Object.create(Namespace.prototype)).constructor=Type).className="Type";var Enum=require(15),OneOf=require(25),Field=require(16),MapField=require(20),Service=require(32),Class=require(10),Message=require(21),Reader=require(27),Writer=require(40),util=require(36),encoder=require(14),decoder=require(13),verifier=require(39),converter=require(12);Type.fromJSON=function fromJSON(name,json){var type=new Type(name,json.options);type.extensions=json.extensions;type.reserved=json.reserved;var names=Object.keys(json.fields),i=0;for(;i<names.length;++i)
type.add((typeof json.fields[names[i]].keyType!=="undefined"?MapField.fromJSON:Field.fromJSON)(names[i],json.fields[names[i]]));if(json.oneofs)
for(names=Object.keys(json.oneofs),i=0;i<names.length;++i)
type.add(OneOf.fromJSON(names[i],json.oneofs[names[i]]));if(json.nested)
for(names=Object.keys(json.nested),i=0;i<names.length;++i){var nested=json.nested[names[i]];type.add((nested.id!==undefined?Field.fromJSON:nested.fields!==undefined?Type.fromJSON:nested.values!==undefined?Enum.fromJSON:nested.methods!==undefined?Service.fromJSON:Namespace.fromJSON)(names[i],nested));}
if(json.extensions&&json.extensions.length)
type.extensions=json.extensions;if(json.reserved&&json.reserved.length)
type.reserved=json.reserved;if(json.group)
type.group=true;return type;};function Type(name,options){Namespace.call(this,name,options);this.fields={};this.oneofs=undefined;this.extensions=undefined;this.reserved=undefined;this.group=undefined;this._fieldsById=null;this._fieldsArray=null;this._oneofsArray=null;this._ctor=null;}
Object.defineProperties(Type.prototype,{fieldsById:{get:function(){if(this._fieldsById)
return this._fieldsById;this._fieldsById={};for(var names=Object.keys(this.fields),i=0;i<names.length;++i){var field=this.fields[names[i]],id=field.id;if(this._fieldsById[id])
throw Error("duplicate id "+id+" in "+this);this._fieldsById[id]=field;}
return this._fieldsById;}},fieldsArray:{get:function(){return this._fieldsArray||(this._fieldsArray=util.toArray(this.fields));}},oneofsArray:{get:function(){return this._oneofsArray||(this._oneofsArray=util.toArray(this.oneofs));}},ctor:{get:function(){return this._ctor||(this._ctor=Class(this).constructor);},set:function(ctor){if(ctor&&!(ctor.prototype instanceof Message))
throw TypeError("ctor must be a Message constructor");if(!ctor.from)
ctor.from=Message.from;this._ctor=ctor;}}});function clearCache(type){type._fieldsById=type._fieldsArray=type._oneofsArray=type._ctor=null;delete type.encode;delete type.decode;delete type.verify;return type;}
Type.prototype.toJSON=function toJSON(){var inherited=Namespace.prototype.toJSON.call(this);return{options:inherited&&inherited.options||undefined,oneofs:Namespace.arrayToJSON(this.oneofsArray),fields:Namespace.arrayToJSON(this.fieldsArray.filter(function(obj){return!obj.declaringField;}))||{},extensions:this.extensions&&this.extensions.length?this.extensions:undefined,reserved:this.reserved&&this.reserved.length?this.reserved:undefined,group:this.group||undefined,nested:inherited&&inherited.nested||undefined};};Type.prototype.resolveAll=function resolveAll(){var fields=this.fieldsArray,i=0;while(i<fields.length)
fields[i++].resolve();var oneofs=this.oneofsArray;i=0;while(i<oneofs.length)
oneofs[i++].resolve();return Namespace.prototype.resolve.call(this);};Type.prototype.get=function get(name){return this.fields[name]||this.oneofs&&this.oneofs[name]||this.nested&&this.nested[name]||null;};Type.prototype.add=function add(object){if(this.get(object.name))
throw Error("duplicate name '"+object.name+"' in "+this);if(object instanceof Field&&object.extend===undefined){if(this._fieldsById?this._fieldsById[object.id]:this.fieldsById[object.id])
throw Error("duplicate id "+object.id+" in "+this);if(this.isReservedId(object.id))
throw Error("id "+object.id+" is reserved in "+this);if(this.isReservedName(object.name))
throw Error("name '"+object.name+"' is reserved in "+this);if(object.parent)
object.parent.remove(object);this.fields[object.name]=object;object.message=this;object.onAdd(this);return clearCache(this);}
if(object instanceof OneOf){if(!this.oneofs)
this.oneofs={};this.oneofs[object.name]=object;object.onAdd(this);return clearCache(this);}
return Namespace.prototype.add.call(this,object);};Type.prototype.remove=function remove(object){if(object instanceof Field&&object.extend===undefined){if(!this.fields||this.fields[object.name]!==object)
throw Error(object+" is not a member of "+this);delete this.fields[object.name];object.parent=null;object.onRemove(this);return clearCache(this);}
if(object instanceof OneOf){if(!this.oneofs||this.oneofs[object.name]!==object)
throw Error(object+" is not a member of "+this);delete this.oneofs[object.name];object.parent=null;object.onRemove(this);return clearCache(this);}
return Namespace.prototype.remove.call(this,object);};Type.prototype.isReservedId=function isReservedId(id){if(this.reserved)
for(var i=0;i<this.reserved.length;++i)
if(typeof this.reserved[i]!=="string"&&this.reserved[i][0]<=id&&this.reserved[i][1]>=id)
return true;return false;};Type.prototype.isReservedName=function isReservedName(name){if(this.reserved)
for(var i=0;i<this.reserved.length;++i)
if(this.reserved[i]===name)
return true;return false;};Type.prototype.create=function create(properties){return new this.ctor(properties);};Type.prototype.setup=function setup(){var fullName=this.fullName,types=[];for(var i=0;i<this.fieldsArray.length;++i)
types.push(this._fieldsArray[i].resolve().resolvedType);this.encode=encoder(this).eof(fullName+"$encode",{Writer:Writer,types:types,util:util});this.decode=decoder(this).eof(fullName+"$decode",{Reader:Reader,types:types,util:util});this.verify=verifier(this).eof(fullName+"$verify",{types:types,util:util});this.fromObject=this.from=converter.fromObject(this).eof(fullName+"$fromObject",{types:types,util:util});this.toObject=converter.toObject(this).eof(fullName+"$toObject",{types:types,util:util});return this;};Type.prototype.encode=function encode_setup(message,writer){return this.setup().encode(message,writer);};Type.prototype.encodeDelimited=function encodeDelimited(message,writer){return this.encode(message,writer&&writer.len?writer.fork():writer).ldelim();};Type.prototype.decode=function decode_setup(reader,length){return this.setup().decode(reader,length);};Type.prototype.decodeDelimited=function decodeDelimited(reader){if(!(reader instanceof Reader))
reader=Reader.create(reader);return this.decode(reader,reader.uint32());};Type.prototype.verify=function verify_setup(message){return this.setup().verify(message);};Type.prototype.fromObject=function fromObject(object){return this.setup().fromObject(object);};Type.prototype.from=Type.prototype.fromObject;Type.prototype.toObject=function toObject(message,options){return this.setup().toObject(message,options);};},{"10":10,"12":12,"13":13,"14":14,"15":15,"16":16,"20":20,"21":21,"23":23,"25":25,"27":27,"32":32,"36":36,"39":39,"40":40}],35:[function(require,module,exports){"use strict";var types=exports;var util=require(36);var s=["double","float","int32","uint32","sint32","fixed32","sfixed32","int64","uint64","sint64","fixed64","sfixed64","bool","string","bytes"];function bake(values,offset){var i=0,o={};offset|=0;while(i<values.length)o[s[i+offset]]=values[i++];return o;}
types.basic=bake([1,5,0,0,0,5,5,0,0,0,1,1,0,2,2]);types.defaults=bake([0,0,0,0,0,0,0,0,0,0,0,0,false,"",util.emptyArray,null]);types.long=bake([0,0,0,1,1],7);types.mapKey=bake([0,0,0,5,5,0,0,0,1,1,0,2],2);types.packed=bake([1,5,0,0,0,5,5,0,0,0,1,1,0]);},{"36":36}],36:[function(require,module,exports){"use strict";var util=module.exports=require(38);util.codegen=require(3);util.fetch=require(5);util.path=require(7);util.fs=util.inquire("fs");util.toArray=function toArray(object){var array=[];if(object)
for(var keys=Object.keys(object),i=0;i<keys.length;++i)
array.push(object[keys[i]]);return array;};util.safeProp=function safeProp(prop){return"[\""+prop.replace(/\\/g,"\\\\").replace(/"/g,"\\\"")+"\"]";};util.ucFirst=function ucFirst(str){return str.charAt(0).toUpperCase()+str.substring(1);};},{"3":3,"38":38,"5":5,"7":7}],37:[function(require,module,exports){"use strict";module.exports=LongBits;var util=require(38);function LongBits(lo,hi){this.lo=lo;this.hi=hi;}
var zero=LongBits.zero=new LongBits(0,0);zero.toNumber=function(){return 0;};zero.zzEncode=zero.zzDecode=function(){return this;};zero.length=function(){return 1;};var zeroHash=LongBits.zeroHash="\0\0\0\0\0\0\0\0";LongBits.fromNumber=function fromNumber(value){if(value===0)
return zero;var sign=value<0;if(sign)
value=-value;var lo=value>>>0,hi=(value-lo)/4294967296>>>0;if(sign){hi=~hi>>>0;lo=~lo>>>0;if(++lo>4294967295){lo=0;if(++hi>4294967295)
hi=0;}}
return new LongBits(lo,hi);};LongBits.from=function from(value){if(typeof value==="number")
return LongBits.fromNumber(value);if(util.isString(value)){if(util.Long)
value=util.Long.fromString(value);else
return LongBits.fromNumber(parseInt(value,10));}
return value.low||value.high?new LongBits(value.low>>>0,value.high>>>0):zero;};LongBits.prototype.toNumber=function toNumber(unsigned){if(!unsigned&&this.hi>>>31){var lo=~this.lo+1>>>0,hi=~this.hi>>>0;if(!lo)
hi=hi+1>>>0;return-(lo+hi*4294967296);}
return this.lo+this.hi*4294967296;};LongBits.prototype.toLong=function toLong(unsigned){return util.Long?new util.Long(this.lo|0,this.hi|0,Boolean(unsigned)):{low:this.lo|0,high:this.hi|0,unsigned:Boolean(unsigned)};};var charCodeAt=String.prototype.charCodeAt;LongBits.fromHash=function fromHash(hash){if(hash===zeroHash)
return zero;return new LongBits((charCodeAt.call(hash,0)|charCodeAt.call(hash,1)<<8|charCodeAt.call(hash,2)<<16|charCodeAt.call(hash,3)<<24)>>>0,(charCodeAt.call(hash,4)|charCodeAt.call(hash,5)<<8|charCodeAt.call(hash,6)<<16|charCodeAt.call(hash,7)<<24)>>>0);};LongBits.prototype.toHash=function toHash(){return String.fromCharCode(this.lo&255,this.lo>>>8&255,this.lo>>>16&255,this.lo>>>24,this.hi&255,this.hi>>>8&255,this.hi>>>16&255,this.hi>>>24);};LongBits.prototype.zzEncode=function zzEncode(){var mask=this.hi>>31;this.hi=((this.hi<<1|this.lo>>>31)^mask)>>>0;this.lo=(this.lo<<1^mask)>>>0;return this;};LongBits.prototype.zzDecode=function zzDecode(){var mask=-(this.lo&1);this.lo=((this.lo>>>1|this.hi<<31)^mask)>>>0;this.hi=(this.hi>>>1^mask)>>>0;return this;};LongBits.prototype.length=function length(){var part0=this.lo,part1=(this.lo>>>28|this.hi<<4)>>>0,part2=this.hi>>>24;return part2===0?part1===0?part0<16384?part0<128?1:2:part0<2097152?3:4:part1<16384?part1<128?5:6:part1<2097152?7:8:part2<128?9:10;};},{"38":38}],38:[function(require,module,exports){"use strict";var util=exports;util.asPromise=require(1);util.base64=require(2);util.EventEmitter=require(4);util.inquire=require(6);util.utf8=require(9);util.pool=require(8);util.LongBits=require(37);util.emptyArray=Object.freeze?Object.freeze([]):[];util.emptyObject=Object.freeze?Object.freeze({}):{};util.isNode=Boolean(global.process&&global.process.versions&&global.process.versions.node);util.isInteger=Number.isInteger||function isInteger(value){return typeof value==="number"&&isFinite(value)&&Math.floor(value)===value;};util.isString=function isString(value){return typeof value==="string"||value instanceof String;};util.isObject=function isObject(value){return value&&typeof value==="object";};util.Buffer=(function(){try{var Buffer=util.inquire("buffer").Buffer;return Buffer.prototype.utf8Write?Buffer:null;}catch(e){return null;}})();util._Buffer_from=null;util._Buffer_allocUnsafe=null;util.newBuffer=function newBuffer(sizeOrArray){return typeof sizeOrArray==="number"?util.Buffer?util._Buffer_allocUnsafe(sizeOrArray):new util.Array(sizeOrArray):util.Buffer?util._Buffer_from(sizeOrArray):typeof Uint8Array==="undefined"?sizeOrArray:new Uint8Array(sizeOrArray);};util.Array=typeof Uint8Array!=="undefined"?Uint8Array:Array;util.Long=global.dcodeIO&&global.dcodeIO.Long||util.inquire("long");util.longToHash=function longToHash(value){return value?util.LongBits.from(value).toHash():util.LongBits.zeroHash;};util.longFromHash=function longFromHash(hash,unsigned){var bits=util.LongBits.fromHash(hash);if(util.Long)
return util.Long.fromBits(bits.lo,bits.hi,unsigned);return bits.toNumber(Boolean(unsigned));};util.merge=function merge(dst,src,ifNotSet){for(var keys=Object.keys(src),i=0;i<keys.length;++i)
if(dst[keys[i]]===undefined||!ifNotSet)
dst[keys[i]]=src[keys[i]];return dst;};util.lcFirst=function lcFirst(str){return str.charAt(0).toLowerCase()+str.substring(1);};util.oneOfGetter=function getOneOf(fieldNames){var fieldMap={};for(var i=0;i<fieldNames.length;++i)
fieldMap[fieldNames[i]]=1;return function(){for(var keys=Object.keys(this),i=keys.length-1;i>-1;--i)
if(fieldMap[keys[i]]===1&&this[keys[i]]!==undefined&&this[keys[i]]!==null)
return keys[i];};};util.oneOfSetter=function setOneOf(fieldNames){return function(name){for(var i=0;i<fieldNames.length;++i)
if(fieldNames[i]!==name)
delete this[fieldNames[i]];};};util.lazyResolve=function lazyResolve(root,lazyTypes){for(var i=0;i<lazyTypes.length;++i){for(var keys=Object.keys(lazyTypes[i]),j=0;j<keys.length;++j){var path=lazyTypes[i][keys[j]].split("."),ptr=root;while(path.length)
ptr=ptr[path.shift()];lazyTypes[i][keys[j]]=ptr;}}};util.toJSONOptions={longs:String,enums:String,bytes:String};util._configure=function(){var Buffer=util.Buffer;if(!Buffer){util._Buffer_from=util._Buffer_allocUnsafe=null;return;}
util._Buffer_from=Buffer.from!==Uint8Array.from&&Buffer.from||function Buffer_from(value,encoding){return new Buffer(value,encoding);};util._Buffer_allocUnsafe=Buffer.allocUnsafe||function Buffer_allocUnsafe(size){return new Buffer(size);};};},{"1":1,"2":2,"37":37,"4":4,"6":6,"8":8,"9":9}],39:[function(require,module,exports){"use strict";module.exports=verifier;var Enum=require(15),util=require(36);function invalid(field,expected){return field.name+": "+expected+(field.repeated&&expected!=="array"?"[]":field.map&&expected!=="object"?"{k:"+field.keyType+"}":"")+" expected";}
function genVerifyValue(gen,field,fieldIndex,ref){if(field.resolvedType){if(field.resolvedType instanceof Enum){gen
("switch(%s){",ref)
("default:")
("return%j",invalid(field,"enum value"));for(var keys=Object.keys(field.resolvedType.values),j=0;j<keys.length;++j)gen
("case %d:",field.resolvedType.values[keys[j]]);gen
("break")
("}");}else gen
("var e=types[%d].verify(%s);",fieldIndex,ref)
("if(e)")
("return%j+e",field.name+".");}else{switch(field.type){case"int32":case"uint32":case"sint32":case"fixed32":case"sfixed32":gen
("if(!util.isInteger(%s))",ref)
("return%j",invalid(field,"integer"));break;case"int64":case"uint64":case"sint64":case"fixed64":case"sfixed64":gen
("if(!util.isInteger(%s)&&!(%s&&util.isInteger(%s.low)&&util.isInteger(%s.high)))",ref,ref,ref,ref)
("return%j",invalid(field,"integer|Long"));break;case"float":case"double":gen
("if(typeof %s!==\"number\")",ref)
("return%j",invalid(field,"number"));break;case"bool":gen
("if(typeof %s!==\"boolean\")",ref)
("return%j",invalid(field,"boolean"));break;case"string":gen
("if(!util.isString(%s))",ref)
("return%j",invalid(field,"string"));break;case"bytes":gen
("if(!(%s&&typeof %s.length===\"number\"||util.isString(%s)))",ref,ref,ref)
("return%j",invalid(field,"buffer"));break;}}
return gen;}
function genVerifyKey(gen,field,ref){switch(field.keyType){case"int32":case"uint32":case"sint32":case"fixed32":case"sfixed32":gen
("if(!/^-?(?:0|[1-9][0-9]*)$/.test(%s))",ref)
("return%j",invalid(field,"integer key"));break;case"int64":case"uint64":case"sint64":case"fixed64":case"sfixed64":gen
("if(!/^(?:[\\x00-\\xff]{8}|-?(?:0|[1-9][0-9]*))$/.test(%s))",ref)
("return%j",invalid(field,"integer|Long key"));break;case"bool":gen
("if(!/^true|false|0|1$/.test(%s))",ref)
("return%j",invalid(field,"boolean key"));break;}
return gen;}
function verifier(mtype){var gen=util.codegen("m")
("if(typeof m!==\"object\"||m===null)")
("return%j","object expected");for(var i=0;i<mtype.fieldsArray.length;++i){var field=mtype._fieldsArray[i].resolve(),ref="m"+util.safeProp(field.name);if(field.map){gen
("if(%s!==undefined){",ref)
("if(!util.isObject(%s))",ref)
("return%j",invalid(field,"object"))
("var k=Object.keys(%s)",ref)
("for(var i=0;i<k.length;++i){");genVerifyKey(gen,field,"k[i]");genVerifyValue(gen,field,i,ref+"[k[i]]")
("}")
("}");}else if(field.repeated){gen
("if(%s!==undefined){",ref)
("if(!Array.isArray(%s))",ref)
("return%j",invalid(field,"array"))
("for(var i=0;i<%s.length;++i){",ref);genVerifyValue(gen,field,i,ref+"[i]")
("}")
("}");}else{if(!field.required){if(field.resolvedType&&!(field.resolvedType instanceof Enum))gen
("if(%s!==undefined&&%s!==null){",ref,ref);else gen
("if(%s!==undefined){",ref);}
genVerifyValue(gen,field,i,ref);if(!field.required)gen
("}");}}return gen
("return null");}},{"15":15,"36":36}],40:[function(require,module,exports){"use strict";module.exports=Writer;var util=require(38);var BufferWriter;var LongBits=util.LongBits,base64=util.base64,utf8=util.utf8;function Op(fn,len,val){this.fn=fn;this.len=len;this.next=undefined;this.val=val;}
function noop(){}
function State(writer){this.head=writer.head;this.tail=writer.tail;this.len=writer.len;this.next=writer.states;}
function Writer(){this.len=0;this.head=new Op(noop,0,0);this.tail=this.head;this.states=null;}
Writer.create=util.Buffer?function create_buffer_setup(){return(Writer.create=function create_buffer(){return new BufferWriter();})();}:function create_array(){return new Writer();};Writer.alloc=function alloc(size){return new util.Array(size);};if(util.Array!==Array)
Writer.alloc=util.pool(Writer.alloc,util.Array.prototype.subarray);Writer.prototype.push=function push(fn,len,val){this.tail=this.tail.next=new Op(fn,len,val);this.len+=len;return this;};function writeByte(val,buf,pos){buf[pos]=val&255;}
function writeVarint32(val,buf,pos){while(val>127){buf[pos++]=val&127|128;val>>>=7;}
buf[pos]=val;}
function VarintOp(len,val){this.len=len;this.next=undefined;this.val=val;}
VarintOp.prototype=Object.create(Op.prototype);VarintOp.prototype.fn=writeVarint32;Writer.prototype.uint32=function write_uint32(value){this.len+=(this.tail=this.tail.next=new VarintOp((value=value>>>0)<128?1:value<16384?2:value<2097152?3:value<268435456?4:5,value)).len;return this;};Writer.prototype.int32=function write_int32(value){return value<0?this.push(writeVarint64,10,LongBits.fromNumber(value)):this.uint32(value);};Writer.prototype.sint32=function write_sint32(value){return this.uint32((value<<1^value>>31)>>>0);};function writeVarint64(val,buf,pos){while(val.hi){buf[pos++]=val.lo&127|128;val.lo=(val.lo>>>7|val.hi<<25)>>>0;val.hi>>>=7;}
while(val.lo>127){buf[pos++]=val.lo&127|128;val.lo=val.lo>>>7;}
buf[pos++]=val.lo;}
Writer.prototype.uint64=function write_uint64(value){var bits=LongBits.from(value);return this.push(writeVarint64,bits.length(),bits);};Writer.prototype.int64=Writer.prototype.uint64;Writer.prototype.sint64=function write_sint64(value){var bits=LongBits.from(value).zzEncode();return this.push(writeVarint64,bits.length(),bits);};Writer.prototype.bool=function write_bool(value){return this.push(writeByte,1,value?1:0);};function writeFixed32(val,buf,pos){buf[pos++]=val&255;buf[pos++]=val>>>8&255;buf[pos++]=val>>>16&255;buf[pos]=val>>>24;}
Writer.prototype.fixed32=function write_fixed32(value){return this.push(writeFixed32,4,value>>>0);};Writer.prototype.sfixed32=function write_sfixed32(value){return this.push(writeFixed32,4,value<<1^value>>31);};Writer.prototype.fixed64=function write_fixed64(value){var bits=LongBits.from(value);return this.push(writeFixed32,4,bits.lo).push(writeFixed32,4,bits.hi);};Writer.prototype.sfixed64=function write_sfixed64(value){var bits=LongBits.from(value).zzEncode();return this.push(writeFixed32,4,bits.lo).push(writeFixed32,4,bits.hi);};var writeFloat=typeof Float32Array!=="undefined"?(function(){var f32=new Float32Array(1),f8b=new Uint8Array(f32.buffer);f32[0]=-0;return f8b[3]?function writeFloat_f32(val,buf,pos){f32[0]=val;buf[pos++]=f8b[0];buf[pos++]=f8b[1];buf[pos++]=f8b[2];buf[pos]=f8b[3];}:function writeFloat_f32_le(val,buf,pos){f32[0]=val;buf[pos++]=f8b[3];buf[pos++]=f8b[2];buf[pos++]=f8b[1];buf[pos]=f8b[0];};})():function writeFloat_ieee754(value,buf,pos){var sign=value<0?1:0;if(sign)
value=-value;if(value===0)
writeFixed32(1/value>0?0:2147483648,buf,pos);else if(isNaN(value))
writeFixed32(2147483647,buf,pos);else if(value>3.4028234663852886e+38)
writeFixed32((sign<<31|2139095040)>>>0,buf,pos);else if(value<1.1754943508222875e-38)
writeFixed32((sign<<31|Math.round(value/1.401298464324817e-45))>>>0,buf,pos);else{var exponent=Math.floor(Math.log(value)/Math.LN2),mantissa=Math.round(value*Math.pow(2,-exponent)*8388608)&8388607;writeFixed32((sign<<31|exponent+127<<23|mantissa)>>>0,buf,pos);}};Writer.prototype.float=function write_float(value){return this.push(writeFloat,4,value);};var writeDouble=typeof Float64Array!=="undefined"?(function(){var f64=new Float64Array(1),f8b=new Uint8Array(f64.buffer);f64[0]=-0;return f8b[7]?function writeDouble_f64(val,buf,pos){f64[0]=val;buf[pos++]=f8b[0];buf[pos++]=f8b[1];buf[pos++]=f8b[2];buf[pos++]=f8b[3];buf[pos++]=f8b[4];buf[pos++]=f8b[5];buf[pos++]=f8b[6];buf[pos]=f8b[7];}:function writeDouble_f64_le(val,buf,pos){f64[0]=val;buf[pos++]=f8b[7];buf[pos++]=f8b[6];buf[pos++]=f8b[5];buf[pos++]=f8b[4];buf[pos++]=f8b[3];buf[pos++]=f8b[2];buf[pos++]=f8b[1];buf[pos]=f8b[0];};})():function writeDouble_ieee754(value,buf,pos){var sign=value<0?1:0;if(sign)
value=-value;if(value===0){writeFixed32(0,buf,pos);writeFixed32(1/value>0?0:2147483648,buf,pos+4);}else if(isNaN(value)){writeFixed32(4294967295,buf,pos);writeFixed32(2147483647,buf,pos+4);}else if(value>1.7976931348623157e+308){writeFixed32(0,buf,pos);writeFixed32((sign<<31|2146435072)>>>0,buf,pos+4);}else{var mantissa;if(value<2.2250738585072014e-308){mantissa=value/5e-324;writeFixed32(mantissa>>>0,buf,pos);writeFixed32((sign<<31|mantissa/4294967296)>>>0,buf,pos+4);}else{var exponent=Math.floor(Math.log(value)/Math.LN2);if(exponent===1024)
exponent=1023;mantissa=value*Math.pow(2,-exponent);writeFixed32(mantissa*4503599627370496>>>0,buf,pos);writeFixed32((sign<<31|exponent+1023<<20|mantissa*1048576&1048575)>>>0,buf,pos+4);}}};Writer.prototype.double=function write_double(value){return this.push(writeDouble,8,value);};var writeBytes=util.Array.prototype.set?function writeBytes_set(val,buf,pos){buf.set(val,pos);}:function writeBytes_for(val,buf,pos){for(var i=0;i<val.length;++i)
buf[pos+i]=val[i];};Writer.prototype.bytes=function write_bytes(value){var len=value.length>>>0;if(!len)
return this.push(writeByte,1,0);if(util.isString(value)){var buf=Writer.alloc(len=base64.length(value));base64.decode(value,buf,0);value=buf;}
return this.uint32(len).push(writeBytes,len,value);};Writer.prototype.string=function write_string(value){var len=utf8.length(value);return len?this.uint32(len).push(utf8.write,len,value):this.push(writeByte,1,0);};Writer.prototype.fork=function fork(){this.states=new State(this);this.head=this.tail=new Op(noop,0,0);this.len=0;return this;};Writer.prototype.reset=function reset(){if(this.states){this.head=this.states.head;this.tail=this.states.tail;this.len=this.states.len;this.states=this.states.next;}else{this.head=this.tail=new Op(noop,0,0);this.len=0;}
return this;};Writer.prototype.ldelim=function ldelim(){var head=this.head,tail=this.tail,len=this.len;this.reset().uint32(len);if(len){this.tail.next=head.next;this.tail=tail;this.len+=len;}
return this;};Writer.prototype.finish=function finish(){var head=this.head.next,buf=this.constructor.alloc(this.len),pos=0;while(head){head.fn(head.val,buf,pos);pos+=head.len;head=head.next;}
return buf;};Writer._configure=function(BufferWriter_){BufferWriter=BufferWriter_;};},{"38":38}],41:[function(require,module,exports){"use strict";module.exports=BufferWriter;var Writer=require(40);(BufferWriter.prototype=Object.create(Writer.prototype)).constructor=BufferWriter;var util=require(38);var Buffer=util.Buffer;function BufferWriter(){Writer.call(this);}
BufferWriter.alloc=function alloc_buffer(size){return(BufferWriter.alloc=util._Buffer_allocUnsafe)(size);};var writeBytesBuffer=Buffer&&Buffer.prototype instanceof Uint8Array&&Buffer.prototype.set.name==="set"?function writeBytesBuffer_set(val,buf,pos){buf.set(val,pos);}:function writeBytesBuffer_copy(val,buf,pos){if(val.copy)
val.copy(buf,pos,0,val.length);else for(var i=0;i<val.length;)
buf[pos++]=val[i++];};BufferWriter.prototype.bytes=function write_bytes_buffer(value){if(util.isString(value))
value=util._Buffer_from(value,"base64");var len=value.length>>>0;this.uint32(len);if(len)
this.push(writeBytesBuffer,len,value);return this;};function writeStringBuffer(val,buf,pos){if(val.length<40)
util.utf8.write(val,buf,pos);else
buf.utf8Write(val,pos);}
BufferWriter.prototype.string=function write_string_buffer(value){var len=Buffer.byteLength(value);this.uint32(len);if(len)
this.push(writeStringBuffer,len,value);return this;};},{"38":38,"40":40}]},{},[19])})(typeof window==="object"&&window||typeof self==="object"&&self||this);
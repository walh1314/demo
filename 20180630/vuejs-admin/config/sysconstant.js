
const protocolStr = "http:";

const getProtocolStr= function(protocolStr){
    if(protocolStr == "https:"){
        return "https";
    } else {
        return "http";
    }
}

const protocolStart = getProtocolStr(protocolStr);
// if(protocolStr == "https:"){
//     protocolStart = "https"
// }
const PORT = 8000
const CONTEXT_PATH = "/lamp"

const IP = "127.0.0.1"

const WEBSOCKET_SERVICE = protocolStart+ "://"+IP+":"+PORT+CONTEXT_PATH+"/websocket"

export{WEBSOCKET_SERVICE}

package com.foxconn.lamp.thirdparty.starlogs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetLogByUserRequest
{
	private Long memberCode;
	
	public GetLogByUserRequest(){
		
	}
	public GetLogByUserRequest(Long memberCode){
		this.memberCode = memberCode;
	}
}

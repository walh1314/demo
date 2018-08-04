package com.foxconn.lamp.thirdparty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="thirdparty.am")
public class AmProperties
{
	private String url;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
//	public static void main(String[] args)
//	{
//		Map<String, Object> headers = new HashMap<>();
//		headers.put("Cookie", "ASP.NET_SessionId=ki1jhf553lybzt55vhxoi0up; EmpAgent=-; CNSBGHRM_05=A90CEB0C2ED8291AB27ED7CF08CB827BB666179F6A21B4AE99BE4F8404C98B3A02861599640AA81BC67E90E51B24F72D7D53CA8EBA8121AF7F91297834273A1ABBF07E639410EEDEE7708A651FFEB137527770F1A9D0D6E23042D1CBB08F268ACB0A34BCD2F7526CD395F0C27F0AAAAACEA8EDE1");
//		HttpEntity httpEntity = HttpClientUtil.httpGet("http://10.167.192.105/Duty/frmCardDetail.aspx?"
//				+ "PlantNo=001&BU=&EmpNo=F1319488&OrganNo=GE66500811%20&Dates=2018/07/19%2000:00"
//				+ "&Datee=2018/07/20%2000:00&IsTaiWan=N&IsDock=N&IsAccredit=N&Place="
//				, headers);
//		try
//		{
//			//httpEntity.writeTo(System.out);
//			InputStream input = httpEntity.getContent();
//			StringBuffer buffer = new StringBuffer();
//			byte[] bytes = new byte[1024];
//			while(input.read() != -1){
//				input.read(bytes);
//				buffer.append(new String(bytes,StandardCharsets.UTF_8));
//			}
//			//System.out.println(buffer);
//			input.close();
//			System.out.println(buffer.indexOf("<font color=\"#003399\">進門卡</font>"));
//			
//			System.out.println(buffer.lastIndexOf("<font color=\"#003399\">出門卡</font>"));
//			//String content = buffer.toString()
//			System.out.println();
//			//httpEntity.getContent().read()
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}

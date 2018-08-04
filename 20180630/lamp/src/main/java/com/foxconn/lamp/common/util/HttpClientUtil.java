package com.foxconn.lamp.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author liupingan
 *
 */
@Slf4j
public class HttpClientUtil
{
	private static PoolingHttpClientConnectionManager clientConnectionManager = null;
	private static CloseableHttpClient httpClient = null;
	private static RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
	private final static Object syncLock = new Object();
	static
	{
		//采用绕过验证的方式处理https请求  
		SSLContext sslcontext = null;
	    try
		{
			sslcontext = createIgnoreVerifySSL();
		} catch (KeyManagementException e)
		{
			log.error(e.getMessage());
		} catch (NoSuchAlgorithmException e)
		{
			log.error(e.getMessage());
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
		clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		clientConnectionManager.setMaxTotal(50);
		clientConnectionManager.setDefaultMaxPerRoute(25);
	}
	
	/** 
	* 绕过验证 
	*   
	* @return 
	* @throws NoSuchAlgorithmException  
	* @throws KeyManagementException  
	*/  
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	        SSLContext sc = SSLContext.getInstance("SSLv3");  

	        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	        X509TrustManager trustManager = new X509TrustManager() {  
	            @Override  
	            public void checkClientTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  

	            @Override  
	            public void checkServerTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  

	            @Override  
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	                return null;  
	            }  
	        };  

	        sc.init(null, new TrustManager[] { trustManager }, null);  
	        return sc;  
	    }
	public static CloseableHttpClient getHttpClient()
	{
		if (httpClient == null)
		{
			synchronized (syncLock)
			{
				if (httpClient == null)
				{
					BasicCookieStore cookieStore = new BasicCookieStore();
					BasicClientCookie cookie = new BasicClientCookie("sessionID", "######");
					cookie.setDomain("#####");
					cookie.setPath("/");
					cookieStore.addCookie(cookie);
					httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager)
							.setDefaultCookieStore(cookieStore).setDefaultRequestConfig(config).build();
				}
			}
		}
		return httpClient;
	}

	@SuppressWarnings("finally")
	public static HttpEntity httpGet(String url, Map<String, Object> headers)
	{
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest httpGet = new HttpGet(url);
		if (headers != null && !headers.isEmpty())
		{
			httpGet = setHeaders(headers, httpGet);
		}
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try
		{
			response = httpClient.execute((HttpGet) httpGet);
			entity = response.getEntity();
		} catch (Exception e)
		{
			log.error(e.getMessage());
		} finally
		{
			return entity;
		}
	}

	/**
	 * 使用表单键值对传参
	 */
	public static HttpEntity httpPost(String url, Map<String, Object> headers, List<NameValuePair> data)
	{
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest request = new HttpPost(url);
		if (headers != null && !headers.isEmpty())
		{
			request = setHeaders(headers, request);
		}
		CloseableHttpResponse response = null;
		UrlEncodedFormEntity uefEntity;
		try
		{
			HttpPost httpPost = (HttpPost) request;
			uefEntity = new UrlEncodedFormEntity(data, StandardCharsets.UTF_8);
			httpPost.setEntity(uefEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return entity;
		} catch (IOException e)
		{
			log.error(e.getMessage());

		}
		return null;
	}

	/**
	 * 使用表单键值对传参
	 */
	public static HttpEntity httpPost(String url, Map<String, Object> headers, String data)
	{
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest request = new HttpPost(url);
		if (headers != null && !headers.isEmpty())
		{
			request = setHeaders(headers, request);
		}
		CloseableHttpResponse response = null;
		try
		{
			HttpPost httpPost = (HttpPost) request;
			StringEntity stringEntity = new StringEntity(data, StandardCharsets.UTF_8);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return entity;
		} catch (IOException e)
		{
			log.error(e.getMessage());

		}
		return null;
	}

	/**
	 * 设置请求头信息
	 * 
	 * @param headers
	 * @param request
	 * @return
	 */
	private static HttpRequest setHeaders(Map<String, Object> headers, HttpRequest request)
	{
		for (Map.Entry<String, Object> entry : headers.entrySet())
		{
			request.addHeader((String) entry.getKey(), (String) entry.getValue());
			/*
			 * if (!entry.getKey().equals("Cookie")) {
			 * request.addHeader((String) entry.getKey(), (String)
			 * entry.getValue()); } else { Map<String, Object> Cookies =
			 * (Map<String, Object>) entry.getValue(); for (Map.Entry<String,
			 * Object> entry1 : Cookies.entrySet()) { request.addHeader(new
			 * BasicHeader("Cookie", (String) entry1.getValue())); } }
			 */
		}
		return request;
	}
	//
	// public static void main(String[] args)
	// {
	// try
	// {
	// KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	// InputStream instream = new FileInputStream("D:/a.keystore");
	// keyStore.load(instream, "123456".toCharArray());
	// instream.close();
	// SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore,
	// new TrustSelfSignedStrategy())
	// .build();
	// SSLConnectionSocketFactory sslsf = new
	// SSLConnectionSocketFactory(sslContext);
	// CloseableHttpClient httpClient =
	// HttpClients.custom().setSSLSocketFactory(sslsf).build();
	// /*
	// * List<NameValuePair> formParams = new ArrayList<NameValuePair>();
	// * formParams.add(new BasicNameValuePair("username", "test"));
	// * HttpPost post = new
	// * HttpPost("https://localhost:8443/servlet/servlet/LoginServlet");
	// * UrlEncodedFormEntity entity = new
	// * UrlEncodedFormEntity(formParams); post.setEntity(entity);
	// * CloseableHttpResponse respones = httpClient.execute(post);
	// * EntityUtils.toString(respones.getEntity());
	// */
	// HttpPost request = new
	// HttpPost("https://localhost:8443/servlet/servlet/LoginServlet");
	// List<BasicNameValuePair> formParams = new
	// ArrayList<BasicNameValuePair>();
	// formParams.add(new BasicNameValuePair("username", "中国"));
	// HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
	// request.setEntity(entity);
	// HttpResponse response = httpClient.execute(request);
	// // 打印请求信息
	// System.out.println(request.getRequestLine());
	// // 打印响应信息
	// System.out.println(response.getStatusLine());
	// response.getEntity().writeTo(System.out);
	// } catch (ClientProtocolException e)
	// {
	// log.error(e.getMessage());
	// } catch (IOException e)
	// {
	// log.error(e.getMessage());
	// } catch (KeyStoreException e)
	// {
	// log.error(e.getMessage());
	// } catch (NoSuchAlgorithmException e)
	// {
	// log.error(e.getMessage());
	// } catch (CertificateException e)
	// {
	// log.error(e.getMessage());
	// } catch (KeyManagementException e)
	// {
	// log.error(e.getMessage());
	// }
	// }
}

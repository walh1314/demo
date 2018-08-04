package com.foxconn.lamp.mqtt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientMQTT
{

	public static final String HOST = "ssl://192.168.1.10:1883";
	public static final String TOPIC = "d62307d5d3d8cb6407547dc9d08b2024";
	private static final String clientid = "large_image_data_tricolor_lamp_camera";
	private MqttClient client;
	private MqttConnectOptions options;
	private String userName = "Foxconn.dm.core-4a6ac9ccdd5bbe45cde3dd4e384d2253";
	private String passWord = "Foxconn.dm.core-4a6ac9ccdd5bbe45cde3dd4e384d2253";

	/*
	 * private String userName = "admin"; private String passWord = "admin";
	 */

	private ScheduledExecutorService scheduler;


	public void init()
	{
		options = new MqttConnectOptions();
		options.setCleanSession(true);
		// options.setUserName(mqttClientProperties.getUserName());
		// options.setPassword(mqttClientProperties.getPassWord().toCharArray());

		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		SSLSocketFactory factory;
		try
		{
			/*ClassLoader classLoader = getClass().getClassLoader();
			factory = getSSLSocktet(classLoader.getResource("mqtt/crt/ca.crt").getPath(), 
					classLoader.getResource("mqtt/crt/client.crt").getPath(), classLoader.getResource("mqtt/crt/client.pem").getPath(),"");*/
			factory = createSSLSocket();
			options.setSocketFactory(factory);
		} catch (Exception e)
		{
			log.error(e.getMessage());
		}
		// client = new MqttClient(mqttClientProperties.getHost(),
		// mqttClientProperties.getClientid(), new MemoryPersistence());
		// mqttClientProperties
		// log.info(mqttClientProperties.getUserName());
	}

	
	private  SSLSocketFactory createSSLSocket() {
        //
        SSLSocketFactory sslSocketFactory;	
        try {
        	ClassLoader classLoader = ClientMQTT.class.getClassLoader();
            InputStream caFile = classLoader.getResourceAsStream("mqtt/crt/ca.crt");

            InputStream pcks12File = classLoader.getResourceAsStream("mqtt/crt/client.pfx");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(caFile);
            if (certificates.isEmpty()) {
                throw new IllegalArgumentException("expected non-empty set of trusted certificates");
            }

            // Put the certificates author to key store.
            char[] password = "".toCharArray(); // Any password will work.
            KeyStore caKeyStore = newEmptyKeyStore(password);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                caKeyStore.setCertificateEntry(certificateAlias, certificate);
            }

            // Create the certificates of client
            KeyStore clientkeyStore = KeyStore.getInstance("PKCS12");
            clientkeyStore.load(pcks12File, password);
            caFile.close();
            pcks12File.close();
            // Building SSL context for future use
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(clientkeyStore, password);
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(caKeyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            //
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(keyManagers, trustManagers, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sslSocketFactory;
    }

	private SSLSocketFactory getSSLSocktet(String caPath, String crtPath, String keyPath, String password)
			throws Exception
	{
		// CA certificate is used to authenticate server
		CertificateFactory cAf = CertificateFactory.getInstance("X.509");
		FileInputStream caIn = new FileInputStream(caPath);
		X509Certificate ca = (X509Certificate) cAf.generateCertificate(caIn);
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", ca);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream crtIn = new FileInputStream(crtPath);
		X509Certificate caCert = (X509Certificate) cf.generateCertificate(crtIn);

		crtIn.close();
		// client key and certificates are sent to server so it can authenticate
		// us
		KeyStore ks = KeyStore.getInstance("PKCS12");
		// ks.load(caIn,password.toCharArray());
		ks.load(null, password == null ? null : password.toCharArray());
		/*ks.setCertificateEntry("certificate", caCert);
		ks.setKeyEntry("private-key", getPrivateKey(keyPath),password == null ? null : password.toCharArray(),
				new java.security.cert.Certificate[]
				{ caCert });*/
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
		kmf.init(ks,password == null ? null :password.toCharArray());
		// keyIn.close();

		// finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1");

		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return context.getSocketFactory();
	}
	
	 private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
	        try {
	            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	            keyStore.load(null, password); // 'null' creates an empty key store.
	            return keyStore;
	        } catch (IOException e) {
	            throw new AssertionError(e);
	        }
	    }

	public PrivateKey getPrivateKey(String path) throws Exception
	{

		org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
		byte[] buffer = base64.decode(getPem(path));

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

	}
	
	private String getPem(String path) throws Exception{
        FileInputStream fin=new FileInputStream(path);
        BufferedReader br= new BufferedReader(new InputStreamReader(fin));  
        String readLine= null;  
        StringBuilder sb= new StringBuilder();  
        while((readLine= br.readLine())!=null){  
            if(readLine.charAt(0)=='-'){  
                continue;  
            }else{  
                sb.append(readLine);  
                sb.append('\r');  
            }  
        }  
        fin.close();
        return sb.toString();
    }


	private void start()
	{
		try
		{
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(HOST, clientid, new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			// 设置连接的用户名
			options.setUserName(userName);
			// 设置连接的密码
			options.setPassword(passWord.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			// 设置回调
			client.setCallback(new PushCallback());
			MqttTopic topic = client.getTopic(TOPIC);
			// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
			options.setWill(topic, "close".getBytes(), 1, true);

			SSLSocketFactory factory;
			try
			{
				ClassLoader classLoader = getClass().getClassLoader();
				factory = getSSLSocktet(classLoader.getResource("mqtt/crt/ca.crt").getPath(), 
						classLoader.getResource("mqtt/crt/client.crt").getPath(), classLoader.getResource("mqtt/crt/client.pem").getPath(),"");
				options.setSocketFactory(factory);
			} catch (Exception e)
			{
				e.getStackTrace();
				log.error(e.getMessage());
			}
			client.connect(options);
			// 订阅消息
			int[] Qos =
			{ 1 };
			String[] topic1 =
			{ TOPIC };
			client.subscribe(topic1, Qos);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MqttException
	{
		ClientMQTT client = new ClientMQTT();
		client.start();
	}
}
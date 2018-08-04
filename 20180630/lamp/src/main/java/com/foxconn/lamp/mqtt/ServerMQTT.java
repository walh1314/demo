package com.foxconn.lamp.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title:Server Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 * 
 * @author yueli 2017年9月1日下午17:41:10
 */

@Slf4j
public class ServerMQTT
{

	// tcp://MQTT安装的服务器地址:MQTT定义的端口号
	public static final String HOST = "ssl://192.168.1.10:1883";
	// 定义一个主题
	public static final String TOPIC = "1f8dc50d57eb9bcea327158cc5a69f59";
	// 定义MQTT的ID，可以在MQTT服务配置中指定
	private static final String clientid = "large_image_data_tricolor_lamp_camera";

	private MqttClient client;
	private MqttTopic topic11;
//	private String userName = "mosquitto";
//	private String passWord = "mosquitto";

	 private String userName = "Foxconn.dm.core-4a6ac9ccdd5bbe45cde3dd4e384d2253";
	 private String passWord =  "Foxconn.dm.core-4a6ac9ccdd5bbe45cde3dd4e384d2253";

	private MqttMessage message;

	/**
	 * 构造函数
	 * 
	 * @throws MqttException
	 */
	public ServerMQTT() throws MqttException
	{
		// MemoryPersistence设置clientid的保存形式，默认为以内存保存
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}

	/**
	 * 用来连接服务器
	 */
	private void connect()
	{
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		SSLSocketFactory factory;
		try
		{
			factory = createSSLSocket();
			options.setSocketFactory(factory);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.getMessage());
		}
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		try
		{
			client.setCallback(new PushCallback());
			client.connect(options);

			topic11 = client.getTopic(TOPIC);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, password); // 'null' creates an empty key store.
			return keyStore;
		} catch (IOException e)
		{
			throw new AssertionError(e);
		}
	}

	private SSLSocketFactory createSSLSocket()
	{
		//
		SSLSocketFactory sslSocketFactory;
		try
		{
			ClassLoader classLoader = ServerMQTT.class.getClassLoader();
			InputStream caFile = classLoader.getResourceAsStream("mqtt/crt/ca.crt");

			InputStream pcks12File = classLoader.getResourceAsStream("mqtt/crt/client.pfx");
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(caFile);
			if (certificates.isEmpty())
			{
				throw new IllegalArgumentException("expected non-empty set of trusted certificates");
			}

			// Put the certificates author to key store.
			char[] password = "".toCharArray(); // Any password will work.
			KeyStore caKeyStore = newEmptyKeyStore(password);
			int index = 0;
			for (Certificate certificate : certificates)
			{
				String certificateAlias = Integer.toString(index++);
				caKeyStore.setCertificateEntry(certificateAlias, certificate);
			}

			// Create the certificates of client
			KeyStore clientkeyStore = KeyStore.getInstance("PKCS12");
			clientkeyStore.load(pcks12File, password);
			caFile.close();
			pcks12File.close();
			// Building SSL context for future use
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(clientkeyStore, password);
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(caKeyStore);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager))
			{
				throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
			}
			//
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(keyManagers, trustManagers, null);
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (GeneralSecurityException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return sslSocketFactory;
	}

	/**
	 * @param topic
	 * @param message
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 */
	public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException
	{
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
		System.out.println("message is published completely! " + token.isComplete());
	}

	/**
	 * 启动入口
	 * 
	 * @param args
	 * @throws MqttException
	 */
	public static void main(String[] args) throws MqttException
	{
		ServerMQTT server = new ServerMQTT();

		server.message = new MqttMessage();
		server.message.setQos(1);
		server.message.setRetained(true);
		
		String sendMsg = "{\"memberCode\":1526889244934386, "
				+ "\"sourceTopic\":\"large_image_data_tricolor_lamp_camera_revice\", "
				+ "\"targetTopic\":\"d62307d5d3d8cb6407547dc9d08b2024\","
				+ " \"commandCode\":121,"
				+ "\"data”: {\"MSG\": { \"TYPE\": \"REQ\", \"ACT\": \"GET\", "
				+ "\"CMD\": \"ANDON_STATE\"} },\"status\":0}";
		
		sendMsg = "{\"intent\":\"com.foxconn.ipcam.settings\","
				+ "\"sourceTopic\":\"large_image_data_tricolor_lamp_camera_revice\","
				+ "\"commandCode\":121,"
				+ "\"data\":\"{\\\"MSG\\\":{\\\"TYPE\\\":\\\"REQ\\\","
				+ "\\\"ACT\\\":\\\"GET\\\",\\\"CMD\\\":\\\"ANDON_STATE\\\"}}\","
				+ "\"status\":0}";
		
		server.message.setPayload(sendMsg.getBytes());
		server.publish(server.topic11, server.message);
		System.out.println(server.message.isRetained() + "------ratained状态");
		//server.client.close(true);
		//server.client.close();
	}
}
package com.foxconn.lamp.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.foxconn.lamp.camera.taskexecutor.CameraMqttTask;
import com.foxconn.lamp.camera.taskexecutor.ThreadPoolCameraTaskExecutor;
import com.foxconn.lamp.mqtt.config.MqttProperties;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MqttInboundConfiguration
{
	@Autowired
	private MqttProperties mqttProperties;

	
	@Autowired
	private ThreadPoolCameraTaskExecutor cameraTaskExecutor;
	
	@Autowired
	private CameraMqttTask cameraMqttTask;
	
	@Bean
	public MessageChannel mqttInputChannel()
	{
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound()
	{
		String[] inboundTopics = mqttProperties.getInbound().getTopics().split(",");
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
				mqttProperties.getInbound().getUrl(), mqttProperties.getInbound().getClientId(), mqttClientFactory(),
				inboundTopics);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	public MqttPahoClientFactory mqttClientFactory()
	{
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setUserName(mqttProperties.getInbound().getUsername());
		mqttConnectOptions.setPassword(mqttProperties.getInbound().getPassword().toCharArray());
		String[] serverURIs = mqttProperties.getOutbound().getUrl().split(",");
		mqttConnectOptions.setServerURIs(serverURIs);
		mqttConnectOptions.setKeepAliveInterval(10);
		SSLSocketFactory sslSocketFactory;
		try
		{
			sslSocketFactory = this.createSSLSocket();
			mqttConnectOptions.setSocketFactory(sslSocketFactory);
		} catch (Exception e)
		{
			log.error(e.getMessage());
		}
		factory.setConnectionOptions(mqttConnectOptions);
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler handler()
	{
		return new MessageHandler()
		{

			@Override
			public void handleMessage(Message<?> message) throws MessagingException
			{
				log.info("=============================start===============");
				String responseMessage = (String) message.getPayload();
				if (MqttConstant.MQTT_CLOSE.equals(responseMessage))
				{
					return;
				}
				String[] inboundTopics = mqttProperties.getInbound().getTopics().split(",");
				Integer executorCount = 0;
				Integer maxPoolSize = 0;
				for (int i = 0; i < inboundTopics.length; i++)
				{
					
					
					if (!message.getHeaders().get((MqttConstant.MQTT_RECEIVED_TOPIC)).equals(inboundTopics[i]))
					{
						continue;
					}
					
					cameraMqttTask.setData(responseMessage);
					
					cameraTaskExecutor.execute(cameraMqttTask);//开启多线程处理
					
					executorCount = cameraTaskExecutor.getActiveCount();
					maxPoolSize = cameraTaskExecutor.getMaxPoolSize();
					if((executorCount *100 /maxPoolSize) > 90){
						try
						{
							TimeUnit.SECONDS.sleep(5);
						} catch (InterruptedException e)
						{
							log.info("cameraTaskExecutor sleep error",e);
						}
					}
					
					
				}
				log.info("=============================end===============");
			}

		};
	}

	private SSLSocketFactory createSSLSocket()
	{
		//
		SSLSocketFactory sslSocketFactory;
		try
		{
			ClassLoader classLoader = MqttInboundConfiguration.class.getClassLoader();
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
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		return sslSocketFactory;
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
}

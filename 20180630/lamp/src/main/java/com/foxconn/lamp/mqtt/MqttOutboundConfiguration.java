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

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.foxconn.lamp.mqtt.config.MqttProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MqttOutboundConfiguration
{
	@Autowired
	private MqttProperties mqttProperties;

	@Bean
	public MqttPahoClientFactory mqttClientFactory()
	{

		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		// factory.setServerURIs("tcp://192.168.10.100:1883",
		// "tcp://host2:1883");
		/*
		 * factory.setServerURIs(serverURIs); factory.setCleanSession(false);
		 * factory.setSocketFactory(createSSLSocket());
		 */
		factory.setConnectionOptions(getMqttConnectOptions());
		// factory.setUserName("username");
		// factory.setPassword("password");
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound()
	{
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getOutbound().getClientId(),
				mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(mqttProperties.getOutbound().getTopic());
		return messageHandler;
	}

	@Bean
	public MessageChannel mqttOutboundChannel()
	{
		return new DirectChannel();
	}

	public MqttConnectOptions getMqttConnectOptions()
	{
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setUserName(mqttProperties.getOutbound().getUsername());
		mqttConnectOptions.setPassword(mqttProperties.getOutbound().getPassword().toCharArray());
		String[] serverURIs = mqttProperties.getOutbound().getUrl().split(",");
		mqttConnectOptions.setServerURIs(serverURIs);
		mqttConnectOptions.setKeepAliveInterval(10);
		SSLSocketFactory factory;
		try
		{
			factory = this.createSSLSocket();
			mqttConnectOptions.setSocketFactory(factory);
		} catch (Exception e)
		{
			log.error(e.getMessage());
		}
		return mqttConnectOptions;
	}

	private SSLSocketFactory createSSLSocket()
	{
		//
		SSLSocketFactory sslSocketFactory;
		try
		{
			ClassLoader classLoader = MqttOutboundConfiguration.class.getClassLoader();
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

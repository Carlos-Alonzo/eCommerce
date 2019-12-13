package com.example.demo.logging;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LogSendRequest
{
	private static final String URL= "https://api.scp.splunk.com/ecomm/ingest/v1beta2/events";
	private static final String TOKEN = "Bearer IACxaMDBcOPj7kMmcBfgG-EDLWyEehiDdjnTPdJLj7VGFu-PzrSxFKSIIwHXLzkEoQ11jLA8Ex5wm3f_qzOEjsXavl-VI-XUNyNzol_DfbFQIIUAw638lXQf1EKnylhYWmq3xmeEAnFML_FI3qp8T3c7uACNuLpcgivUJdd7wWlfnKQWYMjENw3Ol3xbYKjoqYhnX8XvGyVeWmVNpIZF-zbMRnyAilq0tdph2HH8WUsEAC_hr8R2z326b4N05b1GIuq9Zp2jE-dJ5SorvqRmi5JW-Qi8OOqcNVzUWUpmFb-cV1ngPBwhNT6u8XkOS3coqF5y68GzYtTW8kT1JCMoHMptwcdpax1jorS4NXRmmRXIlLLjUJZAngPUKYK_5i3pBsHPthAniaOSL5Fhj3wONgXBMPuf6Q_i9GYvjXmSaGmtWN_o5j6WgdSegeYm3AP-LVVjsGxvmVdszNhId2eY3JsR4i4CRutLhbQaMbdfRDraH8roCxOQhflm0kLdEsJhVFg1uSM8LRx6AR-JuQOJ-qDGBN0oJQztxH3hS_ybT3-y9VGi5_pL_9nKxdIyH0dFyLrNif4jeoazl5my3Xp8vf5_Y6hCSBVo-VKmo6KTOCV8HYdZQjrt4w9qskNpBGgb13PIalNpmN1TkmYd35FJkdAWgsXYVjTXHj9xwgPkxdw2EF20zbV3np8Ysq1Z7eWgLWFDfez05IqITmIvcvXhwC0o2dbrGgU8coPo2qQoZbWzo3_09gIh7ZEhxTic6_zkTD6eIAr7MV8jJXXuupHZJqvi2bkRBx7731v8-UfwHMonjvRYc-xRbwa1mt47J5OMVmGv8b7lWQ6cGP5-NSrj3PnqTR5BHhOkQYZ7LGwMrcphwseiK7p9ynv__UtymEPZZTXx7ZE84xlL-Rh5ctZJJBjaNdwELNTade30sMW2naONhPNzRjTJlyRKAY-zfSfi3Fs94hSD1hv7Eb_EzkhAXJ5kzWlzCBih1yMqaryJrU9elfPNSWscdzPeL3R2t69cTYDifM-4UD1UjGeN-EbJ1UtAaJ4VuzJk8qS8bUj3BJkAhhN7LkkGM-AzvCQ6DeQg1-8EDj_jIVi8KAxMzHViNylBZzViprlUaAng4ElYAQp8tEtkdsTHJEkq8eupVUts5RZDI5Mj0BMi-gLjA ";
	private CloseableHttpClient httpClient;
	private HttpPost httpPost;

	public LogSendRequest()
	{
		httpClient = HttpClients.createDefault();
		httpPost = new HttpPost(URL);
		httpPost.addHeader("Authorization", TOKEN);
		httpPost.addHeader("Content-Type", "application/json");
	}
	public LogSendRequest(String body) throws IOException
	{
		httpClient = HttpClients.createDefault();
		httpPost = new HttpPost(URL);
		httpPost.addHeader("Authorization", TOKEN);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity("[{\"body\":\"" + body +"\"}]"));
	}

	public void  setBody(String body) throws UnsupportedEncodingException
	{
		httpPost.setEntity(new StringEntity("[{\"body\":\"" + body +"\"}]"));
	}
	public HttpResponse executePost() throws IOException
	{
		return httpClient.execute(httpPost);
	}

}

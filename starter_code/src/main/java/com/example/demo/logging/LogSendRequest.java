package com.example.demo.logging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogSendRequest
{
	private static final String URL= "https://api.scp.splunk.com/ecomm/ingest/v1beta2/events";
	private static final String TOKEN = "Bearer IACuj_MNUYk6PST-zc2Ws7ya2Qi2uYcKhSEqxRcAxrUOGH5vWyKySiu44ujVuA5WEeGSTXXRZeN_2yZzJSt7GdBC8aM5yf3VccbkHUPrnHKcJMElITV2OUFpZx6WiSiTBgZjcqaQrfhU55_jrUnMAjFIhNAO-OV_qGTh-n_aCUHQEfZTotby3uzoCMyqBcWEXwpk0PuGHSGgYvS5CH-C4qWiTFZKnKBBxyhJT1Jl8OWgv7GrH0fr6GQ9Jz0914zM_6D6QwMn6JbvR6eOSFCN3sq_QTL6ISesj-277xSpEo2emsns1tWunvA4Gx4ExPeg9W65LCbRRORNSslxBuXIVeQqru9BGfz4V1fJ_rVnM6UcrxgFOf2hR-nckNva5buTAIMReYQli4RI3oy7TNUjUuvJva7FpjB8XEnJfDY3FwmUp082xW9mDNhIRXFV52zBHqRvH7c_hFvmnZuOVLFV1z9kAN1b_0mJ8ET3s23jmn2_kBlku5ITlKuCWXLOUVICwdZnvRD3KkdfXm69Mh0mZc5G2C7Rl3DsN6im9hQjTLPDD7uxbk-sNALfV5zrYMdAU15xVTP3tycpemjGV0tqBRmNBu8PaxxIOit9g8_4dhmRAeDoW_ONke95nqD7-n0B412qbfSqYrG6wO6tU35OnKzpt5Kru_IUBN8P6fz6VzFD60T2ssuTdnfB7js0knoXe5AXpItqZYlQVZvX8gJbX6pzucuV4r3k05OMndol3mZA3JavyRwJNvAvNTFeREsWOTGOcynxzluTS6V0YRUrCl2j1DJfY8Ho-aruGBMcyRaLx5_gJ8SwlUeyCpROHxiZXlZz-Zr1kfjBwjVBMNhAmLtYPH7e2ghGOoGWhI2lxzDi0UBasL-NBDTDSZ1KaSIf7MulWQVlUX1uCBH6NxUmBZ4Dzv1lXedrIN5VnjUE8bdLDFvM_DyCWcft4-TMM9EXdMV0iYEHNsqSrk1We8evFn88wCi-0Ydtxf9OZc1ifp7E4Xvnz0xYUvaIAP-VaxZjXL30GVX8-czQxeokKGQfjQRJ5VXQ7Qd-_OG9yqKqKBflFC5MNaEB732WagZuK8KB4EfdZFqdi2bRXIz7Hn1MrIk176KS9rpOxnub3x2xEj2qlANRJbuFSyaxp_orPPxYQlaF2DyFbxxX5_sSQ";
	private static final String[] Fields = {"id", "host", "source", "source_type", "body"};
	private List<NameValuePair> requestBody;
	private HttpClient httpClient;
	private HttpPost httpPost;
//	private HttpResponse;

//	public void LogSendRequest() throws UnsupportedEncodingException, UnknownHostException
//	{
//		requestBody = new ArrayList<>();
//		requestBody.add(new BasicNameValuePair("id", new Timestamp(System.currentTimeMillis()).toString()));
//		requestBody.add(new BasicNameValuePair("host", InetAddress.getLocalHost().toString()));
//		requestBody.add(new BasicNameValuePair("source_type", "app.class name"));
//
//		httpClient = HttpClients.createDefault();
//		httpPost = new HttpPost(URL);
//		httpPost.setEntity(new UrlEncodedFormEntity(requestBody, "UTF-8"));
//	}


	public LogSendRequest(String body, String source) throws UnknownHostException, UnsupportedEncodingException
	{
		requestBody = new ArrayList<>();
		requestBody.add(new BasicNameValuePair("id", new Timestamp(System.currentTimeMillis()).toString()));
		requestBody.add(new BasicNameValuePair("host", InetAddress.getLocalHost().toString()));
		requestBody.add(new BasicNameValuePair("source", source));
		requestBody.add(new BasicNameValuePair("body", body));
		requestBody.add(new BasicNameValuePair("source_type", "app.class name"));
		httpClient = HttpClients.createDefault();
		httpPost = new HttpPost(URL);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", TOKEN);
		httpPost.setEntity(new Entity(requestBody, "UTF-8"));
	}

	public void setBodyString(String bodyString)
	{
		requestBody.add(new BasicNameValuePair("body", bodyString));
	}

	public void setSourceString(String sourceString)
	{
		requestBody.add(new BasicNameValuePair("body", sourceString));
	}


	public HttpResponse executePost() throws IOException
	{
		return httpClient.execute(httpPost);
	}

}

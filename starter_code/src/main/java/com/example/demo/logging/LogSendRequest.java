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
	private static final String TOKEN = "Bearer IACkaDkKSw1-8CMg8C2D8PXHAVXBwZZ5FRiD1LUdB6Q2g7rEA0D1qcoPwb65iNJq3EnvQ2-c0LDTUsS5BwjpL0cswc93KCbDYncrsFlJ7sY1BORE2fTw2yHjKiJNo8NwHX2Ao-1QtSgW9CEpohoeMzc9e9lCWCmnvTYf2fWkG4trHo9NF3mCQPHKX6hIzJ2nPQGGkSy8RHQJj3VfAsyv1wXHppy1nIItNKh4ne9hnEnfsGcpdUiYpM0U1OApFUP4yuwbaFN4QW_rcao60xQodAm0bOTk0p6zGUNW8lSd4S8RLd5bYbqM0OBmhUZaXT63eIUyW-pVD1ClEuX4lal1iSh0Gc4mXrLUyQFuP7ew5zDQtK2LGN26KP45fH14PAtn0nMt4f_fBsUjnSfn3Tdo4T5zfxCrJgqldxLfYeBEn8ykKLQVjzNKV2iIKW7WZoufdDMMj3Hvd3i2s7ww90tTLAijebSeCwDJ3pMptCEV7twwTtq7Sp8podbRRcjX8eR-otNrQCIvmlSUF-Mi5Xz00mbhMChPSC4fL8UZiTekP984iTBNnja_u_vOiDW3plrHWVxkZN-tMcY1EmgJfRdIZa1v9qNlYlVtzYOuXWx0Z12gbLYeh9PyuaCZQJWhd-lJU4vbJ6B5DT9s5PocP1FaBbynbA0qCFavT1cuWiEqjcGn3tU7bffsOpC7xISL-HOQaGNl55gWnNjJWNajs-YEeGTpiUdD9DYfqr97aI6dScAfJy1BS6wFMEochfLtVS03wMDINQP3XY3V5hBQixdiB6DxiECbdyCdTCINcneX4wOy_TeGi-cuMRROWr-gnrZbBXAsGe2szz6EZA-W9A1cFYcGeTHqb_fdZvmIhIFnI1jh7tEv64yFh5zX_76nE35zqieS9egmaiCeRm3QTDxvmDD7UDtCmrQekNAy4rOv8wjnLHYPYFPKIi20ojH5K_ZswPtMND2uv_hV9CdND2jJutb30Fki-51XndjNC73E5MGHrYMWNrtvcvjW9UfJKS1pvX0pvCH2sRuTcWwQklWrNwrhXjoNBwDqcKBAGT4vmiA8Ws0nksXkEfO0Hrx5Sf2dyHGDLVrOZjKaghCaEgQP8krMLm6RiZI-J8_D7W0cLhvMtE96GHC8wSaT1HoZ1fKi0kxWYC_pMxivR0AIA";
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

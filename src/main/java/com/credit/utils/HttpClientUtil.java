package com.credit.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUtil {
    private static final Log Logger = LogFactory.getLog(HttpClientUtil.class);

	public static ThreadLocal<HttpClientContext> LOCAL = new ThreadLocal<HttpClientContext>();

	private static final String APPLICATION_JSON = "application/json; charset=utf-8";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";


	/**
	 * 默认连接超时时间
	 */
	private  static int DEFAULT_CONNECTION_TIME_OUT = 10000;
	/**
	 *
	 */
	private  static int DEFAULT_SOCKET_TIME_OUT = 60000;

	/***
	 * 默认字符编码
	 */
	private final static String DEFAULT_CHARSET_UTF_8 = "UTF-8";

	public static HttpClient getHttpClient(){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// 连接池最大生成连接数200
		cm.setMaxTotal(200);
		// 默认设置route最大连接数为20
		cm.setDefaultMaxPerRoute(20);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIME_OUT)
				.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT).build();// 设置请求和传输超时时间
		HttpClientBuilder builder = HttpClients.custom();
		HttpClient httpClient =  builder.setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}


	public static String unEncodingPost(String param, String url){
		String res = "";
		HttpResponse response ;
		HttpPost post = null;
		HttpClient httpClient = getHttpClient();
		try {
			HttpContext context = LOCAL.get();
			post = new HttpPost(url);
			post.addHeader(HTTP.CONTENT_TYPE,APPLICATION_JSON);
			StringEntity se = new StringEntity(param);
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			post.setEntity(se);
			response = httpClient.execute(post, context);
			Logger.debug("info-->" + post+"======url -->"+post);
			res = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET_UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return res;
	}

	public static String post(String param, String url) {
		String res = "";
		HttpResponse response ;
		HttpPost post = null;
		HttpClient httpClient = getHttpClient();
		try {
			HttpContext context = LOCAL.get();
			post = new HttpPost(url);
			post.addHeader(HTTP.CONTENT_TYPE,APPLICATION_JSON);
			String encoderJson = URLEncoder.encode(param, DEFAULT_CHARSET_UTF_8);
			StringEntity se = new StringEntity(encoderJson);
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			post.setEntity(se);
			response = httpClient.execute(post, context);
			Logger.debug("Headers-->" + Arrays.toString(response.getAllHeaders()));
			res = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET_UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return res;
	}



	/**
	 * 自定义编码格式跳转
	 *
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String formForward(String url, Map<String, String> params, String charset)
	{
		StringBuffer formHtml = new StringBuffer();
		formHtml.append("<html>");
		String head = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + charset
				+ "\" pageEncoding=\"" + charset + "\" />";
		formHtml.append(head);
		formHtml.append("<title>loading</title>");
		formHtml.append("<style type=\"text/css\">");
		formHtml.append("body{margin:200px auto;font-family: \"宋体\", Arial;font-size: 12px;color: #369;text-align: center;}");
		formHtml.append("#1{height:auto; width:78px; margin:0 auto;}");
		formHtml.append("#2{height:auto; width:153px; margin:0 auto;}");
		formHtml.append("vertical-align: bottom;}");
		formHtml.append("</style>");
		formHtml.append("</head>");
		formHtml.append("<body OnLoad=\"OnLoadEvent();\" >");
		formHtml.append("<div id=\"3\">");
		formHtml.append("Loading...");
		formHtml.append("</div>");

		formHtml.append("<form name=\"forwardForm\" action=\"").append(url).append("\" method=\"POST\">");
		System.out.println("form表单跳转url:"+url);
		Iterator<String> keyIterator = params.keySet().iterator();
		while (keyIterator.hasNext())
		{
			String key = keyIterator.next();
			formHtml.append("  <input type=\"hidden\" name=\"").append(key).append("\" value=\"")
					.append(params.get(key)).append("\"/>");
			System.out.println("form表单跳转参数：" + key + "=" + params.get(key));
		}
		formHtml.append("</form>");
		formHtml.append("<SCRIPT LANGUAGE=\"Javascript\">");
		formHtml.append("  function OnLoadEvent(){");
		formHtml.append("    document.forwardForm.submit();");
		formHtml.append("  }");
		formHtml.append("</SCRIPT>");
		formHtml.append("</body>");
		formHtml.append("</html>");

		return formHtml.toString();
	}
	
}
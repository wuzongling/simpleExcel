package indi.wzl.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @DateTime 2015年1月7日 下午4:51:03
 * @Desc http 模拟请求工具类
 */
public class HttpUtil {

	public final static String CT_TYPE_XML = "text/xml";

	public final static String CT_TYPE_JSON = "application/json";

	public final static String CT_TYPE_HTML = "text/html";

	/**
	 * @DateTime 2015年1月7日 下午4:52:30
	 * @Desc post请求
	 * @param requestUrl
	 * @param data
	 * @param contentType
	 * @param timeout
	 * @return String
	 */
	public static String httpPostData(String requestUrl, String data,
			String contentType, int timeout) throws IOException {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(requestUrl);
		requestUrl = m.replaceAll("");
		URL url = new URL(requestUrl);
		HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
		httpconn.setConnectTimeout(timeout);
		httpconn.setReadTimeout(timeout);
		httpconn.setDoInput(true);
		httpconn.setDoOutput(true);
		httpconn.setRequestProperty("Accept", "text/plain, */*");
		// httpconn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		httpconn.setRequestProperty("Content-Type", contentType
				+ "; charset=UTF-8");
		// post
		OutputStreamWriter out = new OutputStreamWriter(
				httpconn.getOutputStream(), "UTF-8"); // utf-8编码
		out.append(data);
		out.flush();
		out.close();
		// 得到返回结果
		InputStream inPs = httpconn.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(inPs,
				"UTF-8"));
		String tempLine = rd.readLine();
		StringBuffer tempStr = new StringBuffer();
		while (tempLine != null) {
			tempStr.append(tempLine);
			tempLine = rd.readLine();
		}
		return tempStr.toString();
	}

	/**
	 * @DateTime 2015年1月7日 下午4:53:31
	 * @Desc http get 模拟请求
	 * @param requestUrl
	 * @param timeout
	 * @return String
	 */
	public static String httpGetData(String requestUrl, int timeout)
			throws IOException {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(requestUrl);
		requestUrl = m.replaceAll("");
		URL url = new URL(requestUrl);
		HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
		httpconn.setConnectTimeout(timeout);
		httpconn.setReadTimeout(timeout);
		httpconn.setDoInput(true);
		httpconn.setDoOutput(true);
		httpconn.setRequestMethod("GET");
		httpconn.setRequestProperty("Accept", "text/plain, */*");
		httpconn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
		InputStream inPs = httpconn.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(inPs,
				"UTF-8"));
		String tempLine = rd.readLine();
		StringBuffer tempStr = new StringBuffer();
		while (tempLine != null) {
			tempStr.append(tempLine);
			tempLine = rd.readLine();
		}
		return tempStr.toString();
	}

	static String boundary = "--------------7d226f700d0";
	static String prefix = "--";
	static String newLine = "\r\n";

	/**
	 * @DateTime 2015年1月7日 下午4:54:02
	 * @Desc http文件上传模拟请求
	 * @param urlStr
	 * @param fileName
	 * @param in
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("static-access")
	public static String upload(String urlStr, String fileName, InputStream in)
			throws Exception {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(urlStr);
		urlStr = m.replaceAll("");
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpconn = null;
			httpconn = (HttpURLConnection) url.openConnection();
			httpconn.setConnectTimeout(50000);
			httpconn.setReadTimeout(100000);
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);
			httpconn.setUseCaches(false);
			Thread.currentThread().sleep(50); // 延时10 Ms

			httpconn.setRequestProperty("Content-type",
					"multipart/form-data;boundary=" + boundary);

			AssemblyHttp(httpconn.getOutputStream(), fileName, in);

			InputStream ins = httpconn.getInputStream();
			byte[] b = readBuffer(ins);
			System.out.println(new String(b));
			return new String(b);
		} catch (MalformedURLException e) {
			System.out.println(" URL 地址解析错误 ");
		} catch (IOException e) {
			System.out.println(" URL连接打开错误 ");
		}
		return "";

	}

	private static void AssemblyHttp(OutputStream out, String fileName,
			InputStream in) {
		StringBuffer params = new StringBuffer();
		// 编写分隔符
		params.append(prefix + boundary + newLine);
		// 键值说明
		params.append("Content-Disposition: form-data; name=\"username\"");
		// 如果内容不是文件,不用申明文件类型
		params.append(newLine + newLine);
		// 内容
		params.append("bcpmai");
		params.append(newLine);
		// 第二条数据 分隔符
		params.append(prefix + boundary + newLine);
		// 键值说明
		params.append("Content-Disposition: form-data; name=\"file\"; filename=\""
				+ fileName + "\"");
		params.append(newLine);
		// 键值类型
		params.append("Content-Type: image/pjpeg");
		params.append(newLine + newLine);

		try {
			out.write(params.toString().getBytes());
			// 第二条数据内容
			out.write(readBuffer(in));
			out.write(newLine.getBytes());
			// 协议内容结尾
			out.write((prefix + boundary + prefix + newLine).getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(" 没有找到文件 ");
		} catch (IOException e) {
			System.out.println(" 文件IO错误 ");
		}
	}

	public static byte[] readBuffer(InputStream ins) throws IOException {
		byte b[] = new byte[1024];
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int len = 0;
		while ((len = ins.read(b)) != -1) {
			stream.write(b, 0, len);
		}
		return stream.toByteArray();
	}

	/**
	 * @DateTime 2015年1月7日 下午4:54:34
	 * @Desc http文件下载模拟请求
	 * @param urlStr
	 * @return
	 * @throws IOException
	 *             InputStream
	 */
	@SuppressWarnings("static-access")
	public static InputStream download(String urlStr) throws IOException {

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(urlStr);
		urlStr = m.replaceAll("");
		URL url = new URL(urlStr);
		HttpURLConnection httpconn = null;
		httpconn = (HttpURLConnection) url.openConnection();
		httpconn.setConnectTimeout(500000);
		httpconn.setReadTimeout(100000);
		httpconn.setDoInput(true);
		httpconn.setDoOutput(true);
		httpconn.setUseCaches(false);
		try {
			Thread.currentThread().sleep(50); // 延时50 Ms
		} catch (InterruptedException e) {
		}
		
		long length = httpconn.getContentLengthLong();
		System.out.println("文件大小：" + length + " bytes");
	
		InputStream in = new BufferedInputStream(httpconn.getInputStream());

		return in;
	}

}

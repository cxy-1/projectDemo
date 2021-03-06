package Util.Arithmetic.a0.ES;

import com.sugon.searchfores.utils.elasticsearch.entity.EsBeanValue;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * es highrest client
 * @author xyl
 */
@Configuration
public class EsHRestClientFactory {

	@Resource
	private EsBeanValue esBeanValue;

	private static Logger LOG = LoggerFactory.getLogger(EsHRestClientFactory.class);

	public static final int CONNECT_TIMEOUT_MILLIS = 600000;
	public static final int SOCKET_TIMEOUT_MILLIS = 600000;
	public static final int CONNECTION_REQUEST_TIMEOUT_MILLIS = 600000;
	public static final int MAX_CONN_PER_ROUTE = 200;
	public static final int MAX_CONN_TOTAL = 1000;

	private RestClientBuilder builder;
	private RestHighLevelClient restHighLevelClient;

	@PostConstruct
	public void init(){
		String[] esIps = esBeanValue.address.split(",");
		int port = esBeanValue.httpPort;
		HttpHost[] httpHostArray = new HttpHost[esIps.length];
		for (int i = 0; i < esIps.length; i++) {
			HttpHost httpHost = new HttpHost(esIps[i], port, "http");
			httpHostArray[i] = httpHost;
		}
		builder = RestClient.builder(httpHostArray);
		setConnectTimeOutConfig();
		setMutiConnectConfig();
		builder.setMaxRetryTimeoutMillis(CONNECT_TIMEOUT_MILLIS);
		restHighLevelClient = new RestHighLevelClient(builder);
		System.out.println("init factory");
	}

	/**
	 * 配置连接时间延时
	 */
	public void setConnectTimeOutConfig(){
		builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			@Override
			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
				builder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
				builder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
				builder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
				return builder;
			}
		});
	}

	/**
	 * 使用异步httpclient时设置并发连接数
	 */
	public void setMutiConnectConfig(){
		builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT_MILLIS).setSocketTimeout(CONNECT_TIMEOUT_MILLIS).setConnectionRequestTimeout(CONNECT_TIMEOUT_MILLIS);
				httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
				httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
				httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
				return httpClientBuilder;
			}
		});
	}

	public RestHighLevelClient getRhlClient(){
		return restHighLevelClient;
	}

	public void close() {
		if (restHighLevelClient != null) {
			try {
				restHighLevelClient.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
		System.out.println("close rest client");
	}

	@Bean
	@Scope("singleton")
	public RestHighLevelClient RestHightLevelClient(){
		return getRhlClient();
	}

}c
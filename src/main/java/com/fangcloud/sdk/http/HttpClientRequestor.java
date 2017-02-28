// package com.fangcloud.sdk.http;
//
// import com.fangcloud.sdk.YfySdkConstant;
// import org.apache.http.HttpHost;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpDelete;
// import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.client.methods.HttpPut;
// import org.apache.http.client.methods.HttpRequestBase;
// import org.apache.http.entity.InputStreamEntity;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// public class HttpClientRequestor extends HttpRequestor {
//     private CloseableHttpClient httpClient;
//
//     @Override
//     public Response doGet(String url, Iterable<Header> headers) throws IOException {
//         HttpGet httpGet = new HttpGet(url);
//         setHeaders(httpGet, headers);
//         CloseableHttpResponse response = httpClient.execute(httpGet);
//         return toResponse(response);
//     }
//
//     @Override
//     public Uploader startPost(String method, String url, Iterable<Header> headers) throws IOException {
//         HttpEntityEnclosingRequestBase entityRequest;
//         switch (method) {
//             case YfySdkConstant.POST_METHOD:
//                 entityRequest = new HttpPost(url);
//                 break;
//             case YfySdkConstant.PUT_METHOD:
//                 entityRequest = new HttpPut(url);
//                 break;
//             case YfySdkConstant.DELETE_METHOD:
//                 entityRequest = new HttpDelete(url);
//                 break;
//             default:
//                 throw new RuntimeException("unknown http method:" + method);
//         }
//         setHeaders(entityRequest, headers);
//         entityRequest.setEntity(new InputStreamEntity());
//         return null;
//     }
//
//     private void setHeaders(HttpRequestBase httpRequest, Iterable<Header> headers) {
//         for (Header header : headers) {
//             httpRequest.setHeader(header.getKey(), header.getValue());
//         }
//     }
//
//     private Response toResponse(CloseableHttpResponse response) throws IOException {
//         Map<String, List<String>> headers = new HashMap<>();
//         for (final org.apache.http.Header header : response.getAllHeaders()) {
//             headers.put(header.getName(), new ArrayList<String>() {{
//                 header.getValue();
//             }});
//         }
//         return new Response(response.getStatusLine().getStatusCode(), response.getEntity().getContent(), headers);
//     }
//
//     public class Builder {
//         private HttpClientBuilder builder;
//
//         public Builder() {
//             this.builder = HttpClients.custom();
//         }
//
//         public Builder withProxy(String host, int port) {
//             this.builder.setProxy(new HttpHost(host, port));
//             return this;
//         }
//
//         public Builder withConnectionManager(int maxConnection) {
//             PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//             connectionManager.setMaxTotal(maxConnection);
//             this.builder.setConnectionManager(connectionManager);
//             return this;
//         }
//
//         public void build() {
//             httpClient = this.builder.build();
//         }
//     }
// }

package cn.qmulin.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * @description:
 * @author: xys
 * @date: 2022/1/22 23:49
 */

public class ESClient {
    public static void main(String[] args) throws IOException {
        //创建es客户端
        RestClientBuilder localhost = RestClient.builder(new HttpHost("localhost", 9200));
        RestHighLevelClient esClient=new RestHighLevelClient(localhost);

        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        //获取请求相应结果
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引操作:"+acknowledged);

        //关闭es客户端
        esClient.close();
    }
}

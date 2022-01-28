package cn.qmulin.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @description:
 * @author: xys
 * @date: 2022/1/22 23:49
 */
@Slf4j
@SpringBootTest
public class ESIndexTest {
    private RestHighLevelClient esClient;
    @Before
    public void connect(){
        //创建es客户端
        RestClientBuilder localhost = RestClient.builder(new HttpHost("localhost", 9200));
        esClient=new RestHighLevelClient(localhost);
    }

    @Test
    public void indexCreate() throws IOException{
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        //获取请求相应结果
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引操作:"+acknowledged);
    }
    @Test
    public void indexSearch() throws IOException {
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);
        log.info("alias {}",response.getAliases());
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());
    }
    @Test
    public void indexDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @After
    public void close(){
        try {
            esClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

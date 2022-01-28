package cn.qmulin.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;

/**
 * @description: 批量操作数据
 * @author: xys
 * @date: 2022/1/23 16:28
 */
@SpringBootTest
public class DocBatchTest {
    private RestHighLevelClient esClient;

    @Before
    public void connect() {
        //创建es客户端
        RestClientBuilder localhost = RestClient.builder(new HttpHost("localhost", 9200));
        esClient = new RestHighLevelClient(localhost);
    }

    @Test
    public void createDoc() throws IOException {
        //批量新增
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "age", "10", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "age", "30", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu1", "age", "40", "sex", "男"));
        request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "wangwu2", "age", "20", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "wangwu3", "age", "50", "sex", "男"));
        request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "wangwu4", "age", "20", "sex", "男"));
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(responses.getTook());
        System.out.println(Arrays.toString(responses.getItems()));
    }

    @Test
    public void deleteDoc() throws IOException {
        //批量删除
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(responses.getTook());
        System.out.println(Arrays.toString(responses.getItems()));
    }

    @After
    public void close() {
        try {
            esClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

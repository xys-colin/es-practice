package cn.qmulin.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @description:
 * @author: xys
 * @date: 2022/1/23 16:04
 */
@SpringBootTest
public class DocTest {
    private RestHighLevelClient esClient;
    @Before
    public void connect(){
        //创建es客户端
        RestClientBuilder localhost = RestClient.builder(new HttpHost("localhost", 9200));
        esClient=new RestHighLevelClient(localhost);
    }

    @Test
    public void createDoc() throws IOException {
        //请求对象
        IndexRequest request = new IndexRequest();
        //设置索引及唯一标识id
        request.index("user").id("1001");
        User user = new User("张三", "男", 39);
        String jsonString = JSONObject.toJSONString(user);
        // 添加文档数据，数据格式为 JSON 格式
        request.source(jsonString, XContentType.JSON);
        // 客户端发送请求，获取响应对象
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        //3.打印结果信息
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id:" + response.getId());
        System.out.println("_result:" + response.getResult());

    }

    @Test
    public void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON,"sex","女");
        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    @Test
    public void getDoc() throws IOException {
        GetRequest request=new GetRequest();
        request.index("user").id("1001");
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void deleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest().index("user").id("1001");
        //客户端发送请求，获取响应对象
        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println(response.toString());
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

package cn.qmulin.es.dao;

import cn.qmulin.es.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: xys
 * @date: 2022/1/24 23:44
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long> {
}

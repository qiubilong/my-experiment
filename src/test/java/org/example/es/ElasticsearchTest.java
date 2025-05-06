package org.example.es;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.web.dao.es.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class ElasticsearchTest {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Test
    public void testCreateIndex(){
        //创建索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("employee_index"));
        if (indexOperations.exists()) {
            log.info("索引已经存在");
        }else {
            //创建索引
            indexOperations.create();
        }
    }
    @Test
    public void testDeleteIndex(){
        //删除索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("employee_index"));
        indexOperations.delete();
    }


    @Test
    public void testInsertBatch(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(2L,"张三",1,25,"广州天河公园","java developer"));
        employees.add(new Employee(3L,"李四",1,28,"广州荔湾大厦","java assistant"));
        employees.add(new Employee(4L,"小红",0,26,"广州白云山公园","php developer"));

        List<IndexQuery> queries = new ArrayList<>();
        for (Employee employee : employees) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(employee.getId()+"");
            String json = JSONObject.toJSONString(employee);
            indexQuery.setSource(json);
            queries.add(indexQuery);
        }
        //bulk批量插入
        elasticsearchRestTemplate.bulkIndex(queries,Employee.class);
    }

    @Test
    public void testQueryDocument(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //查询
        builder.withQuery(QueryBuilders.matchQuery("address","公园"));
        // 设置分页信息
        builder.withPageable(PageRequest.of(0, 5));
        // 设置排序
        builder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));

        SearchHits<Employee> search = elasticsearchRestTemplate.search(builder.build(), Employee.class);
        List<SearchHit<Employee>> searchHits = search.getSearchHits();
        for (SearchHit hit: searchHits){
            log.info("返回结果："+hit.toString());
        }

    }
}

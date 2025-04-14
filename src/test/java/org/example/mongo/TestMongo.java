package org.example.mongo;

import com.google.common.collect.Lists;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.mongo.City;
import org.example.web.dao.mongo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * @author chenxuegui
 * @since 2025/2/25
 */
@SpringBootTest
@Slf4j
public class TestMongo {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test //人口超过1000万的州
    public void testAggregation1(){
        //$group
        GroupOperation groupOperation = Aggregation.group("state").sum("pop").as("totalPop");
        //$match
        MatchOperation matchOperation = Aggregation.match(Criteria.where("totalPop").gte(10*1000*1000));

        // 按顺序组合每一个聚合步骤
        TypedAggregation<City> typedAggregation = Aggregation.newAggregation(City.class, groupOperation, matchOperation);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }

    }

    @Test //各州平均人口
    public void testAggregation2(){
        //$group -- 州城市分组求和
        GroupOperation groupOperation = Aggregation.group("state","city").sum("pop").as("cityPop");
        //$group -- 州-求平均
        GroupOperation groupOperation2 = Aggregation.group("_id.state").avg("cityPop").as("avgCityPop");
        //$sort - 降序
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"avgCityPop");

        // 按顺序组合每一个聚合步骤
        TypedAggregation<City> typedAggregation = Aggregation.newAggregation(City.class, groupOperation, groupOperation2,sortOperation);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }
    }

    @Test
    public void testAggregation(){
        //$group -- 州城市分组求和
        GroupOperation groupOperation = Aggregation.group("state","city").sum("pop").as("pop");

        //$sort - 降序
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC,"pop");

        //$group
        GroupOperation groupOperation2 = Aggregation
                .group("_id.state")
                .last("_id.city").as("biggestCity") //最大
                .last("pop").as("biggestPop")
                .first("_id.city").as("smallestCity")//最小
                .first("pop").as("smallestPop");

        //$project - 别名
        ProjectionOperation projectionOperation = Aggregation
                .project("state","biggestCity","smallestCity")
                .and("_id").as("state")
                .andExpression("{ name: \"$biggestCity\",  pop: \"$biggestPop\" }").as("biggestCity")
                .andExpression("{ name: \"$smallestCity\", pop: \"$smallestPop\" }").as("smallestCity")
                .andExclude("_id");

        //$sort
        SortOperation sortOperation2 = Aggregation.sort(Sort.Direction.ASC,"state");


        // 按顺序组合每一个聚合步骤
        TypedAggregation<City> typedAggregation = Aggregation.newAggregation(City.class, groupOperation, sortOperation, groupOperation2,
                projectionOperation,sortOperation2);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }

    }


    @Test
    public void testInsert(){
        Date now = new Date();
        Employee employee = new Employee(1, "小明", 30,10000.00, now);

        //mongoTemplate.insert(Employee.class).one(employee);

        //添加文档
        // sava:  _id存在时更新数据
        //mongoTemplate.save(employee);
        // insert： _id存在抛出异常   支持批量操作
        mongoTemplate.insert(employee);

        List<Employee> list = Arrays.asList(
                new Employee(2, "张三", 21,5000.00, now),
                new Employee(3, "李四", 26,8000.00, now),
                new Employee(4, "王五",22, 8000.00, now),
                new Employee(5, "张龙",28, 6000.00, now),
                new Employee(6, "赵虎",24, 7000.00, now),
                new Employee(7, "赵六",28, 12000.00, now));
        //插入多条数据
        Collection<Employee> insert = mongoTemplate.insert(list, Employee.class);
    }

    @Test
    public void testFind(){

        System.out.println("==========查询所有文档===========");
        //查询所有文档
        List<Employee> list = mongoTemplate.findAll(Employee.class);
        list.forEach(System.out::println);

         System.out.println("==========根据_id查询===========");
        //根据_id查询
        Employee e = mongoTemplate.findById(1, Employee.class);
        System.out.println(e);


        System.out.println("==========findOne返回第一个文档===========");
        //如果查询结果是多个，返回其中第一个文档对象
        Employee one = mongoTemplate.findOne(new Query(), Employee.class);
        System.out.println(one);


        System.out.println("==========条件查询===========");
        //new Query() 表示没有条件
        //查询薪资大于等于8000的员工
        //Query query = new Query(Criteria.where("salary").gte(8000));
        //查询薪资大于4000小于10000的员工
        //Query query = new Query(Criteria.where("salary").gt(4000).lt(10000));
        //正则查询（模糊查询）  java中正则不需要有//
        //Query query = new Query(Criteria.where("name").regex("张"));

        //and  or  多条件查询
        Criteria criteria = new Criteria();
        //and  查询年龄大于25&薪资大于8000的员工
        //criteria.andOperator(Criteria.where("age").gt(25),Criteria.where("salary").gt(8000));
        //or 查询姓名是张三 或者 薪资大于8000的员工
        criteria.orOperator(Criteria.where("name").is("张三"),Criteria.where("salary").gt(8000));


        Query query = new Query(Criteria.where("id").in(Lists.newArrayList(1,2,3)));
        //sort排序
        //query.with(Sort.by(Sort.Order.desc("salary")));
        //skip limit 分页  skip用于指定跳过记录数，limit则用于限定返回结果数量。
        query.with(Sort.by(Sort.Order.desc("salary")))
                .skip(0)  //指定跳过记录数
                .limit(4);  //每页显示记录数


        //查询结果
        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        employees.forEach(System.out::println);


        //使用json字符串方式查询
        //等值查询
        //String json = "{name:'张三'}";
        //多条件查询
        String json = "{$or:[{age:{$gt:25}},{salary:{$gte:8000}}]}";
        Query queryJson = new BasicQuery(json);

        //查询结果
        mongoTemplate.find(queryJson, Employee.class).forEach(System.out::println);
    }



    @Test
    public void testUpdate(){

        //query设置查询条件
        Query query = new Query(Criteria.where("salary").gte(12000));

        System.out.println("==========更新前===========");
        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        employees.forEach(System.out::println);

        Update update = new Update();
        //设置更新属性
        update.set("salary",13000);

        //updateFirst() 只更新满足条件的第一条记录
        //UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Employee.class);
        //updateMulti() 更新所有满足条件的记录
        //UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Employee.class);

        //upsert() 没有符合条件的记录则插入数据
        //update.setOnInsert("id",11);  //指定_id
        UpdateResult updateResult = mongoTemplate.upsert(query, update, Employee.class);
        //返回修改的记录数
        System.out.println(updateResult.getModifiedCount());

        System.out.println("==========更新后===========");
        employees = mongoTemplate.find(query, Employee.class);
        employees.forEach(System.out::println);
    }

    @Test
    public void testDelete(){

        //删除所有文档
        //mongoTemplate.remove(new Query(),Employee.class);

        //条件删除
        Query query = new Query(Criteria.where("salary").gte(10000));
        mongoTemplate.remove(query,Employee.class);
    }
}

package com.github.arugal.example.es.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author: zhangwei
 * @date: 13:19/2019-03-19
 */
@Data
@NoArgsConstructor
@Document(indexName = "explore", type = "repositorie", shards = 1, replicas = 0, refreshInterval = "-1")
public class Repositorie implements java.io.Serializable{


    private static final long serialVersionUID = -2370659495719272818L;

    /**
     * repositorie id 数据库自增
     */
    @Id
    private Integer id;

    /**
     * 语言编号
     */
    @Field
    private Short languageCode;

    /**
     * 所有者
     */
    @Field(type = FieldType.Keyword)
    private String owner;

    /**
     * 仓库名称
     */
    @Field
    private String name;

    /**
     * 描述
     */
    @Field
    private String describe = "";

    /**
     * 首次更新时间
     */
    @Field(format = DateFormat.year_month_day)
    private Date firstExploreTime;

    /**
     * 最后更新时间
     */
    @Field(format = DateFormat.year_month_day)
    private Date lastExploreTime;

}

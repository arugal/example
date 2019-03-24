package com.github.arugal.example.es.esdao;

import com.github.arugal.example.es.entity.Repositorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 13:23/2019-03-19
 */
@Component
public interface RepositorieESDao extends ElasticsearchRepository<Repositorie, Integer> {


}

package org.flow.boot.elasticsearch.repository;

import org.flow.boot.elasticsearch.entity.GoodsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo, Long> {

}

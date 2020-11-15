package com.sugon.analysis.repository.mongodb;

import com.sugon.analysis.domain.data.subset.CompanyNewTreeSubset;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @program: convert->CompanyNewTreeDao
 * @description:
 * @author: gaowh
 * @create: 2019-09-24 17:33
 **/
public interface CompanyNewTreeDao extends MongoRepository<CompanyNewTreeSubset, Long> {
}

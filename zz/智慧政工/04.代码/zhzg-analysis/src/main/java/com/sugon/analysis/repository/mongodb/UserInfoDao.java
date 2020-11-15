package com.sugon.analysis.repository.mongodb;


import com.sugon.analysis.domain.data.subset.UserInfoSubset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInfoDao extends MongoRepository<UserInfoSubset, Long> {
    /**
     * ≤È—Ø»À‘±
     * @param idNumber
     * @return
     */
    UserInfoSubset findByIdNumber(String idNumber);

}

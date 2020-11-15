package com.sugon.humanface_opencv.domain.repository;

import com.sugon.humanface_opencv.domain.entity.THumanFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 17:15 2018/11/7
 */
public interface THumanFaceRepository extends JpaRepository<THumanFace,String> {

    List<THumanFace> findByIdNum(String idNum);

    List<THumanFace> findByLabel(Integer label);

    @Query(value = "select max(label) from t_human_face",nativeQuery=true)
    Integer getMaxLabel();

}

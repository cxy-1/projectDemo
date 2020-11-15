package com.sugon.humanface_opencv.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 17:12 2018/11/7
 */
@Entity
@Getter
@Setter
@Table(name = "t_human_info")
public class THumanInfo {

    @Id
    private Integer id;
    private String  idNum;
    private String  name;
    private String  sex;
    private Integer age;

}

package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/9/25
 */
@Data
@Entity
@Table(name = "SYS_USER",schema = "NJGAZNL")
public class SysUserDo {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "DELSTATUS")
    private String delStatus;

    @Column(name = "CHINANAME")
    private String chinaName;

    @Column(name = "LOGINNAME")
    private String loginName;

    @Column(name = "BIRTH")
    private String birth;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "PASSWORD")
    private String passWord;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "MOBILE")
    private String moblie;

    @Column(name = "PHONEDEPT")
    private String phoneDept;

    @Column(name = "PHONEHOME")
    private String phoneHome;

    @Column(name = "PERSONROLES")
    private String personRoles;

    @Column(name = "SORTINDEX")
    private String sortIndex;

    @Column(name = "IDENTITYNUM", columnDefinition="nvarchar(255)")
    private String identitynum;

    @Column(name = "PERMSTRING")
    private String permString;

    @Column(name = "SIGNIMAGEPATH")
    private String signimaGepath;

    @Column(name = "NAMEINDEPT")
    private String nameIndept;

    @Column(name = "DEPTID")
    private String deptId;

    @Column(name = "UNITID")
    private String unitId;

    @Column(name = "USERINDEX")
    private String userIndex;

    @Column(name = "JWPTID")
    private String jwptId;

    @Column(name = "JH")
    private String jh;

    @Column(name = "YHLX")
    private String yhlx;

    @Column(name = "ZWJB")
    private String zwjb;

    @Column(name = "JWPT_LOGINID")
    private String jwptLoginId;

    @Column(name = "MANAGEROLES")
    private String manageRoles;

    @Column(name = "PINYIN")
    private String pinYin;

//    @Column(name = "YHZT")
//    private int yhzt;

    @Column(name = "POST_TYPE")
    private int postType;


}

package com.sugon.analysis.platform.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description:
 * @author: liuwei
 * @create: 2020-06-08 14:41
 **/
@Entity
@Table(name = "BE_THE_BEST_INFO", schema = "ZHZG", catalog = "")
public class BeTheBestInfoEntity {
    private String id;
    private String idNo;
    private String personName;
    private String policeNo;
    private String deptName;
    private String post;
    private String sex;
    private String delstatus;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String active;

    @Basic
    @Column(name = "ACTIVE", nullable = true, length = 50)
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Basic
    @Column(name = "DELSTATUS", nullable = true, length = 50)
    public String getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(String delstatus) {
        this.delstatus = delstatus;
    }

    @Id
    @Column(name = "ID", nullable = false, length = 50)
    @GeneratedValue(generator="system-guid")//¹ØÁªsystem-uuid
    @GenericGenerator(name="system-guid", strategy = "guid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ID_NO", nullable = true, length = 50)
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idno) {
        this.idNo = idno;
    }

    @Basic
    @Column(name = "PERSON_NAME", nullable = true, length = 100)
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Basic
    @Column(name = "POLICE_NO", nullable = true, length = 20)
    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    @Basic
    @Column(name = "DEPT_NAME", nullable = true, length = 255)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "POST", nullable = true, length = 255)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Basic
    @Column(name = "SEX", nullable = true, length = 5)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeTheBestInfoEntity that = (BeTheBestInfoEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idNo != null ? !idNo.equals(that.idNo) : that.idNo != null) return false;
        if (personName != null ? !personName.equals(that.personName) : that.personName != null) return false;
        if (policeNo != null ? !policeNo.equals(that.policeNo) : that.policeNo != null) return false;
        if (deptName != null ? !deptName.equals(that.deptName) : that.deptName != null) return false;
        if (post != null ? !post.equals(that.post) : that.post != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idNo != null ? idNo.hashCode() : 0);
        result = 31 * result + (personName != null ? personName.hashCode() : 0);
        result = 31 * result + (policeNo != null ? policeNo.hashCode() : 0);
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}

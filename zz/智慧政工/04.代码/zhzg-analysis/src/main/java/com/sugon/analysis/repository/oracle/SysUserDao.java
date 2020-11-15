package com.sugon.analysis.repository.oracle;


import com.sugon.analysis.domain.data.object.oracle.SysUserDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/9/25
 */
public interface SysUserDao extends JpaRepository<SysUserDo, Long> {
    /**
     * 根据身份证号查询用户信息
     * @param idNumber
     * @param flag
     * @return
     */
    List<SysUserDo> findAllByIdentitynumAndDelStatusAndPostType(String idNumber, String flag,int postType);

    /**
     * 根据身份证和两个状态位判断人员信息
     * @param idNo
     * @param delStatus
     * @param postType
     * @return
     */
    SysUserDo findByIdentitynumAndDelStatusAndPostType(String idNo,String delStatus,int postType);

    /**
     * 根据单位（分局层面）查询
     * @param postType
     * @param delStatus
     *  @param deptIds
     * @return
     */
    List<SysUserDo> findAllByDelStatusAndPostTypeAndDeptIdIn(String delStatus,int postType,List<String> deptIds);

    /**
     * 根据两个状态位查询
     * @param postType
     * @param delStatus
     * @return
     */
    List<SysUserDo> findAllByDelStatusAndPostType(String delStatus,int postType);

}

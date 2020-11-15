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
     * �������֤�Ų�ѯ�û���Ϣ
     * @param idNumber
     * @param flag
     * @return
     */
    List<SysUserDo> findAllByIdentitynumAndDelStatusAndPostType(String idNumber, String flag,int postType);

    /**
     * �������֤������״̬λ�ж���Ա��Ϣ
     * @param idNo
     * @param delStatus
     * @param postType
     * @return
     */
    SysUserDo findByIdentitynumAndDelStatusAndPostType(String idNo,String delStatus,int postType);

    /**
     * ���ݵ�λ���־ֲ��棩��ѯ
     * @param postType
     * @param delStatus
     *  @param deptIds
     * @return
     */
    List<SysUserDo> findAllByDelStatusAndPostTypeAndDeptIdIn(String delStatus,int postType,List<String> deptIds);

    /**
     * ��������״̬λ��ѯ
     * @param postType
     * @param delStatus
     * @return
     */
    List<SysUserDo> findAllByDelStatusAndPostType(String delStatus,int postType);

}

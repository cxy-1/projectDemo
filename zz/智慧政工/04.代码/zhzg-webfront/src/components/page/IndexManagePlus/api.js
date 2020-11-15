import { $fetch } from "./apiAxios";

const baseDir = './../src/components/page/IndexManagePlus/demoData/';
// const baseURLHead = 'http://localhost:13000/zhzg';
const baseURLHead = '/zhzg';

const interfaces = {
    rygk: {
        // unitData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/user-mgmt/orgmanage/unittreeAuto' },
        unitData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/statistics/orgmanage/unittreeAuto' },
        nl: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getAgeDetails' },
        ly: { localUrl: baseDir + 'rygkData_ly.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getSourceDetails' },
        mz: { localUrl: baseDir + 'rygkData_mz.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getNationDetails' },
        jx: { localUrl: baseDir + 'rygkData_jx.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getPliRankDetails' },
        xb: { localUrl: baseDir + 'rygkData_xb.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getSexDetails' },
        zw: { localUrl: baseDir + 'rygkData_zw.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getDutyDetails' },
        xlxw: { localUrl: baseDir + 'rygkData_xlxw.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getEducationDetails' },
        zzmm: { localUrl: baseDir + 'rygkData_zzmm.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getPartyDetails' },
        getalld: { localUrl: baseDir + 'rygkData_getalld.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getAllDetails' },
        zj: { localUrl: baseDir + 'rygkData_zj.json', remoteUrl: baseURLHead + '/statistics/PersonDetailInformation/getJobGradeDetails' },
    },
    qjgk: {
        unitData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/statistics/orgmanage/unittreeAuto' },
        getYear: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getFormYear' },
        getData: { localUrl: baseDir + 'rygkData_nls.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getPleseOffList' },//请销假概况
        getPleseOffAll: { localUrl: baseDir + 'rygkData_nlss.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getPleseOffAll' },
    },
    cgjgk: {
        unitData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/statistics/orgmanage/unittreeAuto' },
        getYear: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getAbroadYear' },
        getData: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getAbroadListByDest' },//出国境概况-出国境目的地
        getAbroadList: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getAbroadListByMonth' },//出国境概况-出国境次数
        getCountry: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getGoAbroadCountCount' },//出国境概况-出国境次数
        getOutBoundAll: { localUrl: baseDir + 'rygkData_nlse.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getOutBoundAll' },//请销假概况
    },
    dwgk: {
        // unitGrryData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/user-mgmt/orgmanage/getDeptRewordTree?aboutSelf=false' },
        // unitJtryData: { localUrl: baseDir + 'unitData.json', remoteUrl: baseURLHead + '/user-mgmt/orgmanage/getPersonRewordTree' },
        getRewardTree: { localUrl: baseDir + 'getRewardTree.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getRewardTree' },
        getGrryData: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getPersonalRewardList' },//出国境概况-出国境目的地
        // getJtryData: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getGroupRewardList' },//出国境概况-出国境目的地
        getCollectiveHonorDetail: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getCollectiveHonorDetail' },
        getJxxhData: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getKPIList' },//出国境概况-出国境目的地
        getYear: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getKPIYearMenu' },
        getKhdw: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getKPIGroupMenu' },//出国境概况-出国境次数
        getJd: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/mangagerData/getKPIQuarterMenu' },//出国境概况-出国境次数
        getPersonRewardTwo: { localUrl: baseDir + 'rygkData_nl.json', remoteUrl: baseURLHead + '/statistics/managerDetail/getPersonRewardTwo' },//个人荣誉
    }
};

export default {
    get_rygk_unitdata: (data) => { // 人员概况-年龄
        return $fetch('post', interfaces.rygk.unitData, data);
    },
    get_rygk_nl: (data) => { // 人员概况-年龄
        console.log(data)
        return $fetch('post', interfaces.rygk.nl, data);
    },
    get_rygk_ly: (data) => { // 人员概况-来源
        return $fetch('post', interfaces.rygk.ly, data);
    },
    get_rygk_mz: (data) => { // 人员概况-名族
        return $fetch('post', interfaces.rygk.mz, data);
    },
    get_rygk_jx: (data) => { // 人员概况-警衔
        return $fetch('post', interfaces.rygk.jx, data);
    },
    get_rygk_xb: (data) => { // 人员概况-性别
        return $fetch('post', interfaces.rygk.xb, data);
    },
    get_rygk_zw: (data) => { // 人员概况-职位
        return $fetch('post', interfaces.rygk.zw, data);
    },
    get_rygk_zj: (data) => { // 人员概况-职位
        return $fetch('post', interfaces.rygk.zj, data);
    },
    get_rygk_xlxw: (data) => { // 人员概况-学历学位
        return $fetch('post', interfaces.rygk.xlxw, data);
    },
    get_rygk_zzmm: (data) => { // 人员概况-政治面貌
        return $fetch('post', interfaces.rygk.zzmm, data);
    },
    get_rygk_getalld: (data) => { // 人员概况-政治面貌
        return $fetch('post', interfaces.rygk.getalld, data);
    },
    // 请假
    get_qjgk_unitdata: (data) => { // 
        return $fetch('post', interfaces.qjgk.unitData, data);
    },
    get_qjgk_data: (data) => { // 
        return $fetch('post', interfaces.qjgk.getData, data);
    },
    get_cgjgk_BoundAll: (data) => { // 
        return $fetch('post', interfaces.cgjgk.getOutBoundAll, data);
    },
    get_qjgk_year: (data) => { // 
        return $fetch('post', interfaces.qjgk.getYear, data);
    },
    // 出国境
    get_cgjgk_unitdata: (data) => { // 
        return $fetch('post', interfaces.cgjgk.unitData, data);
    },
    get_cgjgk_data: (data) => { // 
        return $fetch('post', interfaces.cgjgk.getData, data);
    },
    get_cgjgk_year: (data) => { // 
        return $fetch('post', interfaces.cgjgk.getYear, data);
    },
    get_cgjgk_abroad: (data) => { // 
        return $fetch('post', interfaces.cgjgk.getAbroadList, data);
    },
    get_cgjgk_country: (data) => { // 
        return $fetch('post', interfaces.cgjgk.getCountry, data);
    },
    get_qjgk_PleseOffAll: (data) => { // 
        return $fetch('post', interfaces.qjgk.getPleseOffAll, data);
    },
    // 单位概况
    // get_dwgk_unit_grry_data: (data) => { // 
    //     return $fetch('get', interfaces.dwgk.unitGrryData, data);
    // },
    // get_dwgk_unit_jtry_data: (data) => { // 
    //     return $fetch('get', interfaces.dwgk.unitJtryData, data);
    // },
    get_dwgk_reward_tree_data: (data) => { // 
        return $fetch('get', interfaces.dwgk.getRewardTree, data);
    },
    get_dwgk_grry_data: (data) => { // 
        return $fetch('post', interfaces.dwgk.getGrryData, data);
    },
    // get_dwgk_jtry_data: (data) => { // 
    //     return $fetch('post', interfaces.dwgk.getJtryData, data);
    // },
    get_dwgk_collective_data: (data) => { // 
        return $fetch('post', interfaces.dwgk.getCollectiveHonorDetail, data);
    },
    get_dwgk_person_data: (data) => { // 
        return $fetch('post', interfaces.dwgk.getPersonRewardTwo, data);
    },
    get_dwgk_jxkh_data: (data) => { // 
        return $fetch('post', interfaces.dwgk.getJxxhData, data);
    },
    // 绩效考核
    get_dwgk_year: (data) => { // 
        return $fetch('get', interfaces.dwgk.getYear, data);
    },
    get_dwgk_Khdw: (data) => { // 
        return $fetch('post', interfaces.dwgk.getKhdw, data);
    },
    get_dwgk_Jd: (data) => { // 
        return $fetch('get', interfaces.dwgk.getJd, data);
    },
}

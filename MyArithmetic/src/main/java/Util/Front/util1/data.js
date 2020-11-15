/*
 * @Author: wangzhongjie
 * @Date: 2020-06-04 14:14:08
 * @LastEditors: wangzhongjie
 * @LastEditTime: 2020-08-26 13:57:44
 * @Description:
 * @Email: UvDream@163.com
 */
export const typeTo = data => {
  let obj = {
    "1": "date",
    "2": "nearbyday",
    "3": "like",
    "4": "not like",
    "5": "start",
    "6": "end",
    "7": "startun",
    "8": "endun",
    "9": "null",
    "10": "notnull",
    "11": "=",
    "12": "<",
    "13": ">",
    "14": "<=",
    "15": ">=",
    "16": "!=",
    "17": "alphabet",
    "18": "number",
    "19": "strlength",
    "20": "regexp"
  };
  return obj[data];
};
export const toType = data => {
  let obj = {
    date: "1",
    nearbyday: "2",
    like: "3",
    "not like": "4",
    start: "5",
    end: "6",
    startun: "7",
    endun: "8",
    null: "9",
    notnull: "10",
    "=": "11",
    "<": "12",
    ">": "13",
    "<=": "14",
    ">=": "15",
    "!=": "16",
    alphabet: "17",
    number: "18",
    strlength: "19",
    regexp: "20"
  };
  return obj[data];
};

export const timeTo = date => {
  var d = new Date(date);
  var resDate = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
  return resDate;
};

/**
 * @description: 根据to寻找上一个节点内容
 * @param {type}
 * @return:
 */
export const findParent = (data, id) => {
  // //console.log("这是工具方法", data.lineList[0]);
  let obj = {};
  data.lineList.forEach(element => {
    if (element.to == id) {
      data.nodeList.forEach(item => {
        if (item.id == element.from) {
          obj = item;
        }
      });
    }
  });
  return obj;
};
export const canLink = (parent, child) => {
  if (
    parent == "data_distinct" ||
    parent == "data_orderBy" ||
    parent == "data-filterData" ||
    parent == "data_join_1" ||
    parent == "data_join_2" ||
    parent == "data_join_3" ||
    parent == "data_join_4" ||
    parent == "data_cardclean" ||
    parent == "data_phoneclean" ||
    parent == "data_carclean"
  ) {
    if (
      child == "data_distinct" ||
      child == "data_orderBy" ||
      child == "data-filterData" ||
      child == "data_join_1" ||
      child == "data_join_2" ||
      child == "data_join_3" ||
      child == "data_join_4" ||
      child == "data_cardclean" ||
      child == "data_phoneclean" ||
      child == "data_carclean"
    ) {
      return false;
    }
  }
  if (
    (parent == "data_dataSource" && child == "data_dataSource") ||
    (parent == "data_tempData" && child == "data_tempData") ||
    (parent == "data_dataSource" && child == "data_tempData") ||
    (parent == "data_tempData" && child == "data_dataSource")
  ) {
    return "table";
  }
  return true;
};
/**
 * @description: 节点
 * @param {type}
 * @return:
 */
export const isNode = type => {
  // 去重
  if (type == "data_distinct") return true;
  // 排序
  if (type == "data_orderBy") return true;
  // 过滤
  if (type == "data-filterData") return true;
  // 关联交集
  if (type == "data_join_1") return true;
  // 关联补集
  if (type == "data_join_2") return true;
  // 关联左关联
  if (type == "data_join_3") return true;
  // 关联并集
  if (type == "data_join_4") return true;
  // 身份证
  if (type == "data_cardclean") return true;
  // 手机号
  if (type == "data_phoneclean") return true;
  // 车牌号
  if (type == "data_carclean") return true;
  if (type == "data_join") return true;
  return false;
};

export const singleNode = type => {
  // 去重
  if (type == "data_distinct") return true;
  // 排序
  if (type == "data_orderBy") return true;
  // 过滤
  if (type == "data-filterData") return true;
  // 关联交集
  if (type == "data_join_1") return false;
  // 关联补集
  if (type == "data_join_2") return false;
  // 关联左关联
  if (type == "data_join_3") return false;
  // 关联并集
  if (type == "data_join_4") return false;
  // 身份证
  if (type == "data_cardclean") return true;
  // 手机号
  if (type == "data_phoneclean") return true;
  // 车牌号
  if (type == "data_carclean") return true;
  if (type == "data_join") return false;
};
export const linkNode = type => {
  if (type == "data_join_1") return true;
  if (type == "data_join_2") return true;
  if (type == "data_join_3") return true;
  if (type == "data_join_4") return true;
  return false;
};
export const isType = (arr, id) => {
  let status = false;
  arr.forEach(element => {
    if (element.id == id && element.type == "data_dataSource") {
      console.log("寻找", element);
      status = true;
    }
  });
  return status;
};

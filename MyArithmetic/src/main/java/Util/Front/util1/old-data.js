/*
 * @Author: wangzhongjie
 * @Date: 2020-05-21 14:07:38
 * @LastEditors: wangzhongjie
 * @LastEditTime: 2020-09-14 14:22:09
 * @Description:兼容老数据
 * @Email: UvDream@163.com
 */
import lodash from "lodash";

import { makeUUID } from "./until";
/**
 * @description: 老数据转换
 * @param {type}
 * @return:
 */
export const dataTo = data => {
  data = JSON.parse(data);
  // console.log("老数据", data);
  let obj = {
    nodeList: [],
    lineList: []
  };
  //   处理nodeList
  for (let i in data.nodes) {
    let node = {
      id: "",
      name: "",
      left: 0,
      top: 0,
      type: "",
      data: {
        list: [],
        fieldList: []
      }
    };
    let item = data.nodes[i];
    node.id = item.id;
    node.pid = item.pid;
    node.name = item.name;
    node.type = item.category;
    if (item.x) {
      node.left = parseInt(item.x) + "px";
      node.top = parseInt(item.y) + "px";
    } else {
      node.left = parseInt(item.left) + "px";
      node.top = parseInt(item.top) + "px";
    }
    // console.log("节点信息", item);
    node.data.list = item.data.list;
    node.data.tableName = item.data.tableName;
    node.data.distincts = item.data.distincts;
    node.data.orderBys = item.data.orderBys;
    node.data.conditionsArr = item.data.conditionsArr;
    node.data.totalCount = item.data.totalCount;
    node.data.modifytime = item.data.modifytime;
    node.data.join = item.data.join;
    // 处理一下fieldList
    let obj1 = {};
    for (let i in item.data.fieldList) {
      obj1 = item.data.fieldList[i];
      node.data.fieldList.push(obj1);
    }
    obj.nodeList.push(node);
  }

  // 处理lineList
  for (let i in data.lines) {
    let line = { from: "", to: "" };
    let item = data.lines[i];
    line.from = item.from;
    line.to = item.to;
    obj.lineList.push(line);
  }
  return obj;
};
/**
 * @description: 新数据转换
 * @param {type}
 * @return:
 */
export const newData = obj => {
  let newObj = {
    nodes: {},
    lines: {}
  };
  let arr = lodash.cloneDeep(obj);

  // 节点信息
  arr.nodeList.forEach(element => {
    if (element.type.split("_")[1] == "join") {
      let a = element.type.split("_");
      element.category = a[0] + "_" + a[1];
    } else {
      element.category = element.type;
    }
    newObj.nodes[element.id] = element;
    let data = element.data.fieldList;
    newObj.nodes[element.id].data.fieldList = {};
    newObj.nodes[element.id].data.showType = element.data.showType;
    newObj.nodes[element.id].data.tableName = element.data.tableName;
    if (data && data.length > 0) {
      data.forEach(item => {
        if (element.type == "data_dataSource") {
          newObj.nodes[element.id].data.fieldList[
            element.id + "_" + item.id
          ] = item;
        } else {
          newObj.nodes[element.id].data.fieldList[
            item.tid + "_" + item.id
          ] = item;
        }
      });
    }
  });
  // console.log(newObj, "传给后台的数据");
  arr.lineList.forEach(element => {
    let id = makeUUID();
    element.id = id;
    newObj.lines[id] = element;
  });
  return newObj;
};

/*
 * @Author: wangzhongjie
 * @Date: 2020-05-12 15:32:32
 * @LastEditors: wangzhongjie
 * @LastEditTime: 2020-08-11 11:51:48
 * @Description:
 * @Email: UvDream@163.com
 */
export const forEach = (arr, fn) => {
  if (!arr.length || !fn) return;
  let i = -1;
  let len = arr.length;
  while (++i < len) {
    let item = arr[i];
    fn(item, i, arr);
  }
};

/**
 * @param {Array} arr1
 * @param {Array} arr2
 * @description 得到两个数组的交集, 两个数组的元素为数值或字符串
 */
export const getIntersection = (arr1, arr2) => {
  let len = Math.min(arr1.length, arr2.length);
  let i = -1;
  let res = [];
  while (++i < len) {
    const item = arr2[i];
    if (arr1.indexOf(item) > -1) res.push(item);
  }
  return res;
};

/**
 * @param {Array} arr1
 * @param {Array} arr2
 * @description 得到两个数组的并集, 两个数组的元素为数值或字符串
 */
export const getUnion = (arr1, arr2) => {
  return Array.from(new Set([...arr1, ...arr2]));
};

/**
 * @param {Array} target 目标数组
 * @param {Array} arr 需要查询的数组
 * @description 判断要查询的数组是否至少有一个元素包含在目标数组中
 */
export const hasOneOf = (targetarr, arr) => {
  return targetarr.some(_ => arr.indexOf(_) > -1);
};

/**
 * @param {String|Number} value 要验证的字符串或数值
 * @param {*} validList 用来验证的列表
 */
export function oneOf(value, validList) {
  for (let i = 0; i < validList.length; i++) {
    if (value === validList[i]) {
      return true;
    }
  }
  return false;
}

/**
 * @returns {String} 当前浏览器名称
 */
export const getExplorer = () => {
  const ua = window.navigator.userAgent;
  const isExplorer = exp => {
    return ua.indexOf(exp) > -1;
  };
  if (isExplorer("MSIE")) return "IE";
  else if (isExplorer("Firefox")) return "Firefox";
  else if (isExplorer("Chrome")) return "Chrome";
  else if (isExplorer("Opera")) return "Opera";
  else if (isExplorer("Safari")) return "Safari";
};

/**
 * @description 绑定事件 on(element, event, handler)
 */
export const on = (function() {
  if (document.addEventListener) {
    return function(element, event, handler) {
      if (element && event && handler) {
        element.addEventListener(event, handler, false);
      }
    };
  } else {
    return function(element, event, handler) {
      if (element && event && handler) {
        element.attachEvent("on" + event, handler);
      }
    };
  }
})();

/**
 * @description 解绑事件 off(element, event, handler)
 */
export const off = (function() {
  if (document.removeEventListener) {
    return function(element, event, handler) {
      if (element && event) {
        element.removeEventListener(event, handler, false);
      }
    };
  } else {
    return function(element, event, handler) {
      if (element && event) {
        element.detachEvent("on" + event, handler);
      }
    };
  }
})();

/**
 * 判断一个对象是否存在key，如果传入第二个参数key，则是判断这个obj对象是否存在key这个属性
 * 如果没有传入key这个参数，则判断obj对象是否有键值对
 */
export const hasKey = (obj, key) => {
  if (key) return key in obj;
  else {
    let keysArr = Object.keys(obj);
    return keysArr.length;
  }
};
/**
 * @description: 数组去重
 * @param {type} arr
 * @return: arr
 */
export const noRepeatArr = arr => {
  return [...new Set(arr)];
};
export const noRepeatArrObj = arr => {
  let obj = {};
  arr = arr.reduce(function(item, next) {
    obj[next.name] ? "" : (obj[next.name] = true && item.push(next));
    return item;
  }, []);
  return arr;
};
/**
 * @description: 查找数组最大值
 * @param {type} arr
 * @return: num
 */
export const arrMax = arr => {
  return Math.max(...arr);
};
/**
 * @description: 查找数组最小
 * @param {type} arr
 * @return: num
 */
export const arrMin = arr => {
  return Math.min(...arr);
};
/**
 * @description: 检查数组某个元素出现次数
 * @param {type} arr,value
 * @return: num
 */
export const countElement = (arr, element) => {
  return arr.reduce((a, v) => (v === element ? a + 1 : a + 0), 0);
};
/**
 * @description: 获取滚动条位置
 * @param {type} event
 * @return: top
 */
export const getScrollPosition = el => {
  return {
    x: el.pageXOffset !== undefined ? el.pageXOffset : el.scrollLeft,
    y: el.pageYOffset !== undefined ? el.pageYOffset : el.scrollTop
  };
};
/**
 * @description: 获取url参数
 * @param {type} url
 * @return:参数
 */
export const getURLParameters = url => {
  return url
    .match(/([^?=&]+)(=([^&]*))/g)
    .reduce(
      (a, v) => (
        (a[v.slice(0, v.indexOf("="))] = v.slice(v.indexOf("=") + 1)), a
      ),
      {}
    );
};
/**
 * @description: cookie增
 * @param {type}
 * @return:
 */
export const setCookie = (key, value, expiredays) => {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + expiredays);
  document.cookie =
    key +
    "=" +
    escape(value) +
    (expiredays == null ? "" : ";expires=" + exdate.toGMTString());
};
/**
 * @description: cookie删
 * @param {type}
 * @return:
 */
export const delCookie = name => {
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval = getCookie(name);
  if (cval != null) {
    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
  }
};
/**
 * @description:cookie查
 * @param {type}
 * @return:
 */
export const getCookie = name => {
  var arr,
    reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
  if ((arr = document.cookie.match(reg))) {
    return arr[2];
  } else {
    return null;
  }
};
/**
 * @description: 进入全屏
 * @param {type} document.documentElement,document.getElementById("id")
 */
export const launchFullScreen = element => {
  if (element.requestFullscreen) {
    element.requestFullscreen();
  } else if (element.mozRequestFullScreen) {
    element.mozRequestFullScreen();
  } else if (element.msRequestFullscreen) {
    element.msRequestFullscreen();
  } else if (element.webkitRequestFullscreen) {
    element.webkitRequestFullScreen();
  }
};
/**
 * @description: 退出全屏
 */
export const exitFullScreen = () => {
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else if (document.msExitFullscreen) {
    document.msExitFullscreen();
  } else if (document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if (document.webkitExitFullscreen) {
    document.webkitExitFullscreen();
  }
};
/**
 * @description: 生成uuid
 * @return: uuid
 */
export const makeUUID = () => {
  return Math.random()
    .toString(36)
    .substr(3, 10);
};
/**
 * @description: 深拷贝
 * @param {type}:obj
 * @return:obj
 */
export const deepClone = obj => {
  let objClone = Array.isArray(obj) ? [] : {};
  if (obj && typeof obj === "object") {
    for (let key in obj) {
      if (obj[key] && typeof obj[key] === "object") {
        objClone[key] = deepClone(obj[key]);
      } else {
        objClone[key] = obj[key];
      }
    }
  }
  return objClone;
};

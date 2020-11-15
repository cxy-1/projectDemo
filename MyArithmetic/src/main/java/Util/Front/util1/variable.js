/*
 * @Author: wangzhongjie
 * @Date: 2020-05-14 09:27:02
 * @LastEditors: wangzhongjie
 * @LastEditTime: 2020-06-29 14:27:46
 * @Description:主题色变量
 * @Email: UvDream@163.com
 */

// 字体变量
const baseSize = {
  "--font-size-large-x": "22px",
  "--font-size-large": "18px",
  "--font-size-medium": "14px",
  "--font-size-medium-x": "16px",
  "--font-size-small-s": "10px",
  "--font-size-small": "12px"
};

//浅色
export const lightTheme = {
  // 主题色
  "--themeColor": "#007CFF",
  // 背景色
  "--bgColor": " #fff",
  "--grayColor": "#f0f4f9ff",
  // 文字颜色
  "--textColor": "#666",
  // 深色背景
  "--deepBgColor": "#EBEFF4",
  // table 头部
  "--tableHead": "#f8f8f9",
  ...baseSize
};

// 深色
export const darkTheme = {
  "--themeColor": "#007CFF",
  "--bgColor": "#212B3D",
  "--grayColor": "#f0f4f9ff",
  "--textColor": " #fff",
  "--deepBgColor": "#131724",
  "--tableHead": "#212B3D",
  ...baseSize
};

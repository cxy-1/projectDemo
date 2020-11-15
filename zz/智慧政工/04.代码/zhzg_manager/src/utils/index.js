// 获取url中的传参
const getUrlParams = hash => {
  const result = {};
  hash.replace(/\?(.*)/g, function($1, $2) {
    $2.split('&').map(val => {
      let arr = val.split('=');
      result[arr[0]] = decodeURI(arr[1]);
    });
  });

  return result;
};

// 下载文件通用方法
const downloadFile = (file, type = 'text/plain', filename = 'download') => {
  let blob = new Blob([file], {
    type: `${type};charset=utf-8`
  });

  let downloadElement = document.createElement('a');
  let href = window.URL.createObjectURL(blob); //创建下载的链接
  downloadElement.href = href;
  downloadElement.download = filename; //下载后文件名
  document.body.appendChild(downloadElement);
  downloadElement.click(); //点击下载
  document.body.removeChild(downloadElement); //下载完成移除元素
  window.URL.revokeObjectURL(href); //释放掉blob对象
};

// 将base64的文件下载到本地
const downloadFileByUrl = (href, filename = 'download', prefix = '.png') => {
  const a = document.createElement('a');
  a.href = href;
  a.download = filename + prefix;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
};

export { getUrlParams, downloadFile, downloadFileByUrl };

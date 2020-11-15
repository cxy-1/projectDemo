import axios from 'axios';

const service = axios.create({
  // 联调
  baseURL:
    process.env.NODE_ENV === 'production'
      ? `http://10.33.72.236:8085/`
      // ? `http://50.32.9.139:8085/`
      // ? `http://192.168.8.11:8085/`
      : '/api/',
  headers: {
    get: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    post: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  },
  // 是否跨站点访问控制请求
  withCredentials: true,
  timeout: 30000,
  transformRequest: [data => JSON.stringify(data)],
  // status参数没写
  validateStatus() {
    // 使用async-await，处理reject情况较为繁琐，所以全部返回resolve，在业务代码中处理异常
    return true;
  },
  transformResponse: [
    data => {
      if (typeof data === 'string' && data.startsWith('{')) {
        data = JSON.parse(data);
      }
      return data;
    }
  ]
});

// 策略模式生产状态码
const statusStrategy = status => {
  const statusEnum = {
    400: '请求错误(400)',
    401: '未授权，请重新登录(401)',
    403: '拒绝访问(403)',
    404: '请求出错(404)',
    408: '请求超时(408)',
    500: '服务器错误(500)',
    501: '服务未实现(501)',
    502: '网络错误(502)',
    503: '服务不可用(503)',
    504: '网络超时(504)',
    505: 'HTTP版本不受支持(505)',
    0: `连接出错(${status})!`
  };
  const message = statusEnum[status] || statusEnum[0];
  return `${message}，请检查网络或联系管理员！`;
};

// 请求拦截器
service.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    // 错误抛到业务代码
    error.data = {};
    error.data.msg = '服务器异常，请联系管理员！';
    return Promise.resolve(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    const status = response.status;
    let msg = '';
    if (status < 200 || status >= 300) {
      // 处理http错误，抛到业务代码
      msg = statusStrategy(status);
      if (typeof response.data === 'string') {
        response.data = { msg };
      } else {
        response.data.msg = msg;
      }
    }
    return response.data;
  },
  error => {
    // 错误抛到业务代码
    error.data = {};
    error.data.msg = '请求超时或服务器异常，请检查网络或联系管理员！';
    return Promise.resolve(error);
  }
);
// // get请求
// const get = (url, params, config) => service.get(url, { ...config, params });

// // post请求
// const post = (url, params, config) => service.post(url, params, config);

// // put请求
// const put = (url, params, config) => service.put(url, params, config);

// // delete请求
// const del = (url, params, config) => service.delete(url, { ...config, params });

export default service;

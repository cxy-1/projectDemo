// const $fetchUser = function(method, url, params) {
//     return new Promise((resolve, reject) => {
//         $axiosUser({
//             method: method,
//             url: url.remoteUrl,
//             data: params,
//             headers: {
//                 'contentType': 'application/json',
//                 'token': localStorage.getItem("setToken")
//             }
//         }).then(res => {
//             resolve(res.data);
//         }).catch(err => {
//             reject(err);
//         });
//     });
// };

import axios from 'axios'

export const localUrl = 'http://localhost:8085/static/'; // 本地调试地址
// export const remoteUrl = 'http://10.33.72.237:13000'; // 远端地址
// export const remoteUrl = 'http://192.168.8.197:8188'; // 远端地址
export const remoteUrl = 'http://10.33.66.86:13000'; // 远端地址
// export const remoteUrl = 'http://192.168.8.11:13000'; // 远端地址
// export const remoteUrl = 'http://lw101192.xicp.net:31151'; // 远端地址

const isPublish = true;

let $axios = axios.create({
    baseURL: isPublish ? remoteUrl : localUrl
});

export const $fetch = function (method, url, data) {
    return new Promise((resolve, reject) => {
        let param = "?";
        if (typeof data === "object") {
            for (let key in data) {
                param += `${key}=${data[key]}&`;
            }
        }
        param = param.substring(0, param.length - 1);
        param = /get/i.test(method) ? param : "";
        $axios({
            method: isPublish ? method : 'get',
            url: (isPublish ? url.remoteUrl : url.localUrl) + param,
            data: data,
            headers: {
                'contentType': 'application/json',
                'token': localStorage.getItem('setToken')
            }
        }).then(res => {
            resolve(res.data);
        }).catch(err => {
            reject(err);
        });
    });
};

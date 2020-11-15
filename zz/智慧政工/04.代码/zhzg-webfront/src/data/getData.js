import axios from 'axios'

export const localUrl = 'http://localhost:8085/static/'; // 本地调试地址
// export const remoteUrl = 'http://localhost:9090'; // 远端地址
export const remoteUrl = 'http://10.33.66.86:9090'; // 远端地址

const isPublish = true;

let $axios = axios.create({
    baseURL: isPublish ? remoteUrl : localUrl
});

export const $fetch = function (method, url, data) {
    return new Promise((resolve, reject) => {
        $axios({
            method: isPublish ? method : 'get',
            url: isPublish ? url.remoteUrl : url.localUrl,
            data: data,
            headers: {
                token: sessionStorage.getItem('token')
            }
        }).then(res => {
            let body = res.data;
            if (body.code === 200 || body.code === 1006) {
                resolve(body);
            } else if (body.code === 1005) {
                this.$router.push({path: '/login'}); // 重定向到登陆页面
            } else {
                reject(body);
            }
        }).catch(err => {
            reject(err);
        });
    });
};

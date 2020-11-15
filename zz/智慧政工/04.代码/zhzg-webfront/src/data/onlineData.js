import {$fetch} from "./getData";

const interfaces = {
    initPage: {localUrl: 'onlineTable.json', remoteUrl: 'onlineTable.do'},
    // initFolder: {localUrl: 'onlineTableFolder.json', remoteUrl: 'onlineTableFolder.do'},
    download: {localUrl: 'downloadFile.json', remoteUrl: 'downloadFile.do'},
    getPdf: {localUrl: 'getPdf.json', remoteUrl: 'preview.do'}
};

export default {
    initPage: (data) => { // 初始化页面
        return $fetch('post', interfaces.initPage, data);
    },
    getPdf: (data) => {
        return $fetch('post', interfaces.getPdf, data);
    },
    download: (data) => { // 初始化页面
        return $fetch('post', interfaces.download, data);
    }
}

import {$fetch} from "./getData";

const interfaces = {
    initPage: {localUrl: 'initPage.json', remoteUrl: 'regBoxData.do'},
    regPreview: {localUrl: 'regPreview.json', remoteUrl: 'regPreviewData.do'}//预览
};

export default { 
    initPage: (data) => { // 初始化页面
        return $fetch('post', interfaces.initPage, data);
    },
    regPreview: (data) => { // 预览
        return $fetch('post', interfaces.regPreview, data);
    }
}

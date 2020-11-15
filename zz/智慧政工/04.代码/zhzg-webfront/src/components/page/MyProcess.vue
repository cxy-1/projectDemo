<template>
    <div class="wdlc" style="height:100%" ref="wdlcOption">
        <div class="personal-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col :span="5">
                            <el-card class="card2 menu-list">
                                <div class="personal-info" ref="menuList">
                                    <div class="per-img">
                                        <!--头像个人设置  -->
                                        <!-- <router-link to="/grsz"> -->
                                        <img width="110px" height="140px" @click="selfVisible = true" :src="imgIp" alt="个人头像" style="cursor:pointer;" />
                                        <!-- </router-link> -->
                                        <p class="per-name">{{username}}</p>
                                        <p>{{telPhone==''?telPhone:'暂无数据'}}</p>
                                    </div>
                                    <div class="info-list" style="overflow:auto">
                                        <el-menu default-active="/wdlc" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
                                            <el-menu-item index="/wdlc" key="/wdlc">
                                                <router-link to="/wdlc">我的流程</router-link>
                                            </el-menu-item>
                                            <!-- <el-menu-item index="/txxx" key="/txxx">
                                                                <router-link to="/txxx">通讯信息</router-link>
                                                            </el-menu-item> -->
                                            <!-- <el-menu-item index="/spsq" key="/spsq">
                                                                <router-link to="/spsq">审批授权</router-link>
                                                            </el-menu-item> -->
                                            <el-menu-item index="/yyfk" key="/yyfk">
                                                <router-link to="/yyfk">应用反馈</router-link>
                                            </el-menu-item>
                                            <!-- <el-menu-item index="/wdpxxx" key="/wdpxxx">
                                                                <router-link to="/wdpxxx">我的培训学习</router-link>
                                                            </el-menu-item> -->
                                            <el-menu-item index="/xxtzsz" key="/xxtzsz">
                                                <router-link to="/xxtzsz">消息通知设置</router-link>
                                            </el-menu-item>
                                        </el-menu>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="19">
                            <el-card class="card2" style="margin-left:4px">
                                <div class="right_card">
                                    <router-view>
                                    </router-view>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!-- 个人设置弹窗 -->
        <el-dialog title="头像更换" class="txgh" :visible.sync="selfVisible" width="466px">
            <div class="changeImg">
                <div class="item_bock head_p">
                    <div class="head_img" >
                        <img :src="imgIp" />
                    </div>
                    <el-upload ref="my-upload" class="upload-demo" :limit="1" :auto-upload="false" :before-upload="beforeAvatarUpload" :on-success="handleSuccess" :action="acts" multiple>
                        <el-button size="mini" class="imgBtn" @click="loadBtn=!loadBtn" :loading="loadBtn">选择头像图片</el-button>
                    </el-upload>

                     <!-- <div class="setting_right" @click.stop="uploadHeadImg" v-show="false">
                        <div class="caption">选择头像图片</div>
                    </div>
                    <input type="file" accept="image/*" @change="handleFile" class="hiddenInput" />-->
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="selfVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveImg" :disabled="disBtn">确 定</el-button>
            </span>
        </el-dialog>
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    data() {
        return {
            telPhone:localStorage.getItem('loginPhone'),
            selfVisible: false,
            topImg:true,
            cimgIp:'',
            loadBtn:false,
            disBtn:true,
            acts:this.global.serverPath + '/zhzg/personalCenter/updateUserPotoByPersonId?personId='+localStorage.getItem('loginpersonId')+'&pathUrl=download/userImage',
            username: localStorage.getItem('loginUser'),
            // imgIp:'http://10.33.66.86:9999/download/userImage/'+localStorage.getItem("loginpersonId")+".jpg",
            imgIp: 'http://10.33.66.86:9999/download/userImage/'+localStorage.getItem("loginpersonId")+".jpg"
        }
    },
    created() {
    },
    methods: {
        // 图片上传之前
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';//上传格式限制
            const isLt2M = file.size / 1024 / 1024 < 2;
            console.log(764373647)
            if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        handlePictureCardPreview(file) {
            this.topImg=false;
            console.log(file)
            // this.dialogVisible = true;
        },
        // 将头像显示在弹窗
        handleFile: function(e) {
            console.log('0000000')
            let $target = e.target || e.srcElement
            let file = $target.files[0];
            var reader = new FileReader()
            reader.onload = (data) => {
                let res = data.target || data.srcElement
                this.imgIp = res.result
            }
            reader.readAsDataURL(file)
        },
        // 打开图片上传
        uploadHeadImg: function() {
            console.log(648348734)
            this.$el.querySelector('.hiddenInput').click()
        },
        // 文件上传成功钩子
        handleSuccess(response, file, fileList) {
            console.log(response.fileName);
            // this.imgIp ='http://10.33.66.86:9999/download/userImage/'+response.fileName;
            this.cimgIp ='http://39.100.56.78:9999/download/userImage/'+response.fileName;
            console.log('上传头像成功');
            this.disBtn=false;
            this.loadBtn=false;
        },
        // 确定更改照片
        saveImg(){
            this.selfVisible=false;
            this.imgIp=this.cimgIp;
        },

        // 导航1 open
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        //导航1 close
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },
    mounted() {
        this.$refs.menuList.style.height = this.$refs.wdlcOption.offsetHeight - 90 + 'px';//滚动条高度
        $('.myProcess').height(this.$refs.wdlcOption.offsetHeight - 160);
        $('.right_card').height(this.$refs.wdlcOption.offsetHeight - 80);
        $('.info-list').height(this.$refs.wdlcOption.offsetHeight - 80 - 170);
    },
    computed: {

    }
}

</script>
<style scoped>
.item_bock {
 width: 300px;
 padding:0px 24px 0px 38px;
 background: #fff;
}
.myProcess{
    overflow-y:auto;
}
.head_img img{
 width:146px;
 height:176px;
/* //  border-radius:100px */
}

.hiddenInput{
 display: none;
}
.caption {
    cursor: pointer;
 color: #409EFF;
 font-size: 16px;
 margin-top:20px;
 /* height: 37px; */
}
.changeImg {
    text-align: center;
}





/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/

.info-list::-webkit-scrollbar {
    width: 6px;
    /*滚动条宽度*/
    height: 6px;
    /*滚动条高度*/
}





/*定义滚动条轨道 内阴影+圆角*/

.info-list::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px #F5F5F5;
    border-radius: 4px;
    /*滚动条的背景区域的圆角*/
    background-color: #F5F5F5;
    /*滚动条的背景颜色*/
}





/*定义滑块 内阴影+圆角*/

.info-list::-webkit-scrollbar-thumb {
    border-radius: 4px;
    /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px #BBC3D2;
    background-color: #BBC3D2;
    /*滚动条的背景颜色*/
}

.card2 {
    margin: 0 20px;
}

.info-list .el-menu {
    border: none
}
</style>
<style>
.item_bock .el-upload-list{
    display:none;
}
.txgh .el-upload--text{
    border:none!important;
    height:48px;
    width:200px;
}
.txgh .imgBtn{
    font-size:14px;
    color:#1890FF;
    padding:10px 20px;
    margin-top:10px;
}
.txgh .el-dialog__body {
    padding: 10px 20px!important;
}

.info-list .el-menu-item a {
    color: #000000;
    opacity: 0.65;
    display: inline-block;
    width: 100%;
    height: 100%;
}

.info-list .el-menu-item.is-active a {
    color: #1890FF;
    opacity: 1;
}

.info-list .el-menu-item.is-active {
    background: #E6F7FF;
}

.personal-option .menu-list .el-card__body {
    padding: 20px 0;
}

.personal-option {
    padding-top: 16px;
}

.personal-option .el-card__body {
    /* padding:0; */
}

.per-img {
    text-align: center;
    color: #000000;
    font-family: PingFangSC-Medium;
    margin-bottom: 20px;
}

.per-img .per-name {
    font-size: 16px;
    opacity: 0.85;
    margin: 6px auto;
}

.per-img p:last-child {
    opacity: 0.85;
    font-size: 14px;
}

.personal-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.personal-info .el-form-item__label {
    padding: 0;
}

.personal-info .el-input__inner {
    border: none;
    padding: 0;
}

.personal-box .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
    line-height: 32px;
}

.personal-box .el-form-item__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    letter-spacing: 0;
}

.personal-box .el-select {
    width: 76%;
}

.personal-box .el-input__inner {
    width: 98%;
}

.personal-box .el-select .el-input__inner {
    width: 100%;
}

.personal-box .el-upload--text {
    width: 76%;
    height: 28px;
    border: none;
    text-align: left;
}

.personal-box .el-upload-dragger {
    border: none;
    height: 100%;
    text-align: left;
    padding-left: 3px;
}

.personal-box .el-upload__tip {
    margin-top: 0;
}

.personal-box .el-upload-dragger {
    width: 100%;
}

.personal-box .el-upload-dragger .el-upload__text {
    text-align: left;
}
</style>


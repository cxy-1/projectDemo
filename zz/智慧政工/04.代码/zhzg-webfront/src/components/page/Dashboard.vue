<template>
    <div class="cjgl">
        <div class="bm-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-card shadow="" class="mgb20">
                        <div class="crumbs">
                            <el-breadcrumb separator="/">
                                <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                <el-breadcrumb-item>出国（境）申请</el-breadcrumb-item>
                            </el-breadcrumb>
                        </div>
                    </el-card>
                    <el-card shadow="" class="mgb20" style="margin:20px">
                        <div class="bmxy-box">
                            <p class="bmxy-tit">出国（境）人员行前保密及安全教育谈话记录（因私）</p>
                            <p class="bmxy-info">1、因私出国（境）民警应按照批准的事项出行，不得擅自更改日期、线路、目的地，不得擅自延长在国（境）外的驻留时间，确需变更的应重新报批，违反者将视情给予通报批评，造成不良后果的按有关规定追究责任；行程结束后，在10日内将出国（境）证件交回本单位政工部门；经批准并已办妥因私出国（境）审批的民警，因故逾期未能出国（境）的，应将证件、函件在逾期后10日内上交组织集中保管。</p>
                            <p class="bmxy-info">2、民警因私出国旅游应采取随有资质的旅行社团体出游的方式进行，以确保民警在境外的人身安全和旅途顺利；赴港澳台旅游可采取随团旅游或自由行方式进行。</p>
                            <p class="bmxy-info">3、在国（境）外，应严格遵守《保密法》和《人民警察保密守则》等保密纪律和外事纪律的要求，不得随意向国（境）外组织、人员透露自己的身份、工作性质及内容；不得在非保密场所谈论涉密事项、阅处涉密资料、使用无加密措施的通信工具和手段传递涉密信息；不得携带各种涉密载体（包括纸质文件和电磁介质等），自觉维护国家的安全和利益。</p>
                            <p class="bmxy-info">4、在国（境）外，应保持高度的政治敏锐性，不得参与非经批准的任何活动；遇有不明身份的人主动接触时，不得透露警务工作情况，更不得随意谈论涉及我国政治、军事、科技、经济等国家秘密及其他未公开的事项。</p>
                            <p class="bmxy-info">5、在国（境）外，应树立自我保护意识，不得随意接收物品、信件，不随意签名，以防发生法律问题；公共场所一般不过问他人丢弃的不明物品（含钱包），以防爆炸等危险事件的发生；个人证照、钱物、行李等要妥善保管。一旦发生问题应及时向我国驻外使（领）馆、办事机构或警方求助，并向市局报告。</p>
                            <p class="bmxy-info">6、在国（境）外，应遵守当地的法律、法令、风俗习惯，不得收听、收看、阅读、购买涉及反动、黄色内容的广播、电影、录相、影碟、书报刊；不得出入不正当的娱乐场所；不得从事走私和非法外汇交易；不得赌博、酗酒。</p>
                            <p class="bmxy-info last-info">本人已知晓上述要求，保证在国（境）外期间履行保密责任，遵守外事纪律和保密纪律，不泄露国家秘密和警务工作秘密，注意自身安全，回国后十日内向组织上交本人护照（通行证）。</p>
                        </div>
                        <div class="timeOut">{{timeOut}}S</div>
                        <div class="agreeBmxy" style="text-align:center">
                            <el-checkbox :checked="checked" @change="checkBox()">我已认真阅读，并承诺严格遵守上述规定</el-checkbox>
                            <!-- <span>上述行前保密及安全教育谈话记录我已认真阅读</span> -->
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    name: 'dashboard',
    data() {
        return {
            // name: localStorage.getItem('ms_startTime'),
            timeOut: 20,
            checked: false
        }
    },
    created() {
        this.sendMessage();//开始倒计时
    },
    methods: {
       
        // 点击选择复选框
        checkBox() {
            this.checked = !this.checked;
            if (this.checked && this.timeOut == 0) {
                this.$router.push('/dashboard2')//显示下一步
            }
        },
        // 倒计时显示
        sendMessage() {
            // if (this.bmOp) {//如果显示的第一步
                this.getSecond(20);
            // }
            return;
        },
        //倒计时显示
        getSecond(wait) {
            var self = this;
            self.timeOut = wait;
            if (wait > 0) {
                wait--;
                setTimeout(function() {
                    self.getSecond(wait);
                }, 1000);
            } else if (self.checked && wait == 0) {
                self.$router.push('/dashboard2')
                //显示下一步
            }
        }
    },
    computed: {
        // role() {
        //     return this.name === 'admin' ? '超级管理员' : '普通用户';
        // }
    }
}

</script>
<style scoped>
.agreeBmxy {
    margin: 16px auto;
}

.timeOut {
    width: 54px;
    height: 54px;
    border-radius: 27px;
    background: #D4E2F0;
    font-family: PingFangSC-Regular;
    font-size: 18px;
    margin: 10px auto;
    display: flex;
    justify-content: center;
    /* 相对父元素水平居中 */
    align-items: center;
    color: rgba(24, 144, 255, 0.85);
}

.bmxy-info {
    font-family: PingFangSC-Regular;
    font-size: 16px;
    color: rgba(0, 0, 0, 1);
    text-align: justify;
    margin: 25px;
    text-indent: 2em;
    line-height: 25px;
}

.last-info {
    text-indent: 46px;
}

.bmxy-tit {
    text-align: center;
    font-family: PingFangSC-Medium;
    font-size: 18px;
    font-weight: 900;
    color: rgba(0, 0, 0, 0.85);
    text-align: center;
}
.el-row {
    margin-bottom: 20px;
}
.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.agreeBmxy .el-checkbox__label {
    font-family: PingFangSC-Medium;
    font-size: 16px;
    color: rgba(0, 0, 0, 1);
    text-align: center;
}

</style>


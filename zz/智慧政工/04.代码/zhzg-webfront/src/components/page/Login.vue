<template>
    <el-container>
        <el-main>
            <div class="right-info">
                <i class="el-icon-sold-out" style="font-size:16px"></i>
                <span @click="downBrowser()">浏览器安装包</span>&emsp;
                <i class="el-icon-sold-out" style="font-size:16px"></i>
                <span @click="downDzqz()">电子签章安装包</span>&emsp;
                <i class="el-icon-document" style="font-size:16px"></i>
                <span>关于本系统</span>&emsp;
                <i class="el-icon-view" style="font-size:16px"></i>
                <span @click="downHelp()">使用帮助</span>&emsp;
            </div>
            <input v-show="pos" type="button" pos="pos0" class="posTest" value="电子签章">
            <div class="ms-login">
                <img src="static/img/login_logo.png" class="login-logo" alt="" style="margin:0 auto;display:block;margin-bottom:60px">
                <img src="static/img/szzs.png" class="szzs-btn" alt="" style="margin:6% auto;height:80px;display:none;">
                <el-tabs v-model="activeName2" type="card" @tab-click="handleClick" style="display:block">
                     <!-- <el-tab-pane label="数字证书登录" name="first">
                        <img src="static/img/szzs.png" class="szzs-btn" alt="" style="margin:6% auto;height:80px;display:block;">
                    </el-tab-pane> -->
                    <el-tab-pane label="普通登录" name="twice">
                        <el-form :model="loginForm" status-icon :label-position="'right'" :rules="loginRule" ref="loginForm" label-width="66px" class="demo-loginForm">
                            <el-form-item label="用户名" prop="username">
                                <el-input v-model="loginForm.username" placeholder="username" auto-complete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password" style="margin-bottom:12px">
                                <el-input type="password" placeholder="password" v-model="loginForm.password" @keyup.enter.native="submitForm('loginForm')"></el-input>
                            </el-form-item>
                        </el-form>
                    </el-tab-pane>
                </el-tabs>
                <div class="bottom-box">
                    <el-button type= "primary" @click="submitForm('loginForm')" class="login-btn">登 录</el-button>
                    <!--<hr style="margin:0 auto;width:84%;border-color:#aaa">
                     <div class="bot-info">
                        <a :href="'#'">关于本系统</a>&emsp;&emsp;&emsp;
                        <a :href="'#'">使用帮助</a>
                    </div>-->
                </div>
            </div>
        </el-main>
        <el-footer>&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </el-container>
</template>

<script>
import crypto from "crypto";
import { communication } from "../../data/getAxios";
var aa = require("../../../src/Global");
console.log(aa.default.serverPath8);
export default {
  data: function() {
    return {
      loginTypeNum: "",
      pos: false,
      personIdCon: "",
      loginRule: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入正确密码", trigger: "blur" }
          // { type: 'number', message: '必须为数字值' }
        ]
      },
      loginForm: {
        username: "",
        password: ""
      },
      activeName2: "twice"
    };
  },
  methods: {
    // 浏览器下载
    downBrowser() {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr(
        "action",
        this.global.serverPath6 + "/download/browser.zip"
      );
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    // 电子签章下载
    downDzqz() {
      console.log("电子签章下载");
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr(
        "action",
        this.global.serverPath6 + "/download/iSignatureDZQZ.zip"
      );
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    // 下载操作手册使用帮助
    downHelp() {
      window.open(this.global.serverPath12 + "/download/operationManual");
      // var self = this;
      // const _obj={
      // 	url:self.global.serverPath8+'/user-mgmt/download/operationManual',
      // 	isGet:true
      // };
      // communication(_obj).then((res) => {
      //     console.log(33333)
      // }, function(res) {
      // 	self.$message.error('请求失败')
      // });
    },
    //sha256加密
    setSha(vals) {
      let sha256 = require("js-sha256").sha256; //这里用的是require方法，所以没用import
      return sha256(vals);
    },
    submitForm(formName) {
      var self = this;
      // if (self.loginTypeNum == 1) {
      self.$refs[formName].validate(valid => {
        if (valid) {
          const _obj = {
            url: self.global.serverPath8 + "/user-mgmt/login",
            postData: {
              policeNo: self.loginForm.username,
              password: self.setSha(self.loginForm.password)
            },
            isToken: false
          };
          communication(_obj)
            .then(res => {
              localStorage.setItem("setToken", "");
              console.log(res);
              if (res.code == 0) {
                localStorage.setItem("setToken", res.data.token);
                localStorage.setItem("userId", res.data.user.id);
                localStorage.setItem("loginUser", res.data.user.userName);
                localStorage.setItem("loginpersonId", res.data.user.personId);
                localStorage.setItem("loginDeptId", res.data.user.deptId);
                localStorage.setItem("loginCompanyId", res.data.user.companyId);
                localStorage.setItem("loginPhone", res.data.user.phone);
                localStorage.setItem("idCard", res.data.user.idNumber);
                localStorage.setItem("policeNo", res.data.user.policeNo);
                if (res.data.user.policeNo == "999999") {
                  self.$router.push("/menuManager");
                } else {
                  self.$router.push("/home2");
                }
              } else {
                if (res.code != 0) {
                  self.$message.warning(res.message);
                }
              }
            })
            .catch(err => {
              console.log(err);
            });
        }
      });
      // }
      //  else {
      //   var posid = $(".posTest").attr("pos");
      //   var signatureCreator = Signature.create();
      //   console.log(signatureCreator);
      //   signatureCreator.run({
      //     position: posid, //设置盖章定位dom的ID，必须设置
      //     okCall: function(fn, image) {
      //       //点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
      //       console.log("盖章ID：" + this.getSignatureid());
      //       console.log("盖章数据：" + this.getSignatureData());
      //       var imgCon = $(".kg-img img").attr("src");
      //       console.log("imgCon+" + imgCon);
      //       self.$http
      //         .post(
      //           self.global.serverPath8 + '/user-mgmt/credential/login',
      //           {
      //             // self.$http.post(self.global.serverPath8 + '/user-mgmt/credential/login', {
      //             // "uKey":'eyJ0aW1lc3RhbXAiOnsidGltZSI6MTU2MDA3MjIzOTY1Miwic2lnbnRpbWUiOiIyMDE5LzYvOSDkuIvljYg1OjIzOjU5KOacrOWcsOaXtumXtCkifSwiYXBwbmFtZSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDYuMSkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzczLjAuMzY4My44NiBTYWZhcmkvNTM3LjM2IiwibW92ZWFibGUiOnRydWUsImtleXNuIjoi6ZmI6IyC6IOcIDMyMTE4MjE5ODkwMjE0MDUxNSIsIm9yZ25hbWUiOiLmlL/lt6Xnu7zlkIjlpIQiLCJ1c2VybmFtZSI6IumZiOiMguiDnCIsInNlYWwiOnsiZW5kYXRhIjoiIiwiaGVpZ2h0IjoiMC44MCIsImltZ2RhdGEiOiJITjRQRklnbTIrbzVBRXdZMFNsalpmZXRXaEJjUm54NnBxYkRUPUNPdmFpR1g3eTl1a3MxL1ZNUThyVTNLekpkTFMvPWd3UD1xRzBOeEh3QUhIUFRVd01oQ0NlQmhDaD1DaGFDaGhhQ2hDaENoMStPQTFBc2hDUnNoMUExQUM2OTNKdUhISEhISEhISEhISEhISDRtck5IRkhISFhINUhISEhINDdISThISEhqSlJBPXJQYmFEcUlJWk9lSHZEQ1NhT0NiaUdDazhJWTRxZnB2Uk5GNHlRS3R0ZFROMGFsQTVndkpUSHMzMHdWVG9FVk13MGVmMnhaZVRFRz1YY0dKZElLSEJzK29wWlU3LzlTVVd1Zjc9RDlDZ00vbmJSRE1GdkpVV04wVzlFQzc9blFGOEhtbG1ENCtyYk9YcGJ0QXpZcTA5SD1BSHR2VkhSYUg3SUJvU09sMklXVUhDRXY3d0JvPU0wcnVHTzhXdnZpN1dhY1dOeEdCYkFVeDlYcld5TjBsKzdjWU5zdHFZWldlVHlxby8vWmM1Vlc4V1hUd2pCcFpkdWhYeVhTdlVmZUc0L3YzMk1GUHR2WjdJUXdoSjFQdytIaXdHWnluaVlIY2xTN2VxRWlRNHFTRGVyRzBQRkJVbG8xQlhlRVBCSzJreFkvZWZJYityUmxZYkVnakpZNmNhVG9mQXZSU2poNGFscFRGbGVOS01CczhnRm1TK1dycXV3bGFFMEtIRytjelVFYlRUQkM9NFBHdmRnV0VsMG1Fa2pYUmtyMWMrdWs0SEhFZ0VaNGhTMFhIU1U9Rk1mL0o4SlhCNGF6UnJIWCtvM05BZnZzTnRmZnJ6dWlEL2tIZkhleXhUS3h3L3YvbjYwL3NVWSs5R3NWc3ZUZ2wybFdFU3NGd2FXSGNhc0o5ZnR6KzY1b051VytuYkZJWHNFZ0ZmbFB5YVY9dWQrdzZTRFJBc2JNYUpud1hVTnl5RldVbzh6eTRBQz1TK0JzS0tHTzJPMmx0aC9sTjR3NjJxMmIxOTAyTVRESy9OWm9yRzJDWkNqRGJRMj1pS2t3VVJYPVZLPTl2QmNVZ29CT2ZqVmFTSnBXSThqcHEwdUdzcHhzOXVla2ovK0F3c01WSWRuTlBKYTJyN2pkUWVscFBaeDZ4bHQ4aXpYRU5idXZJMT1ib1preGZaQk40Z3IvVFg5Zz1GUE54MjNTUDVwSnp2cGhvSWYvbE5EVFJoaWJZeGxnSkFXWDBEak1RbGdENDQydEliZXVGTUlJdjIrY2FXdDFEQXcyRW5GUDBnelZuUzBSVThXMS9VN3RxZ0RiOGdyVmF0Zm9VSWxEODg3bFJab3FPRXdBNEZBM0NDUTBxRU5wU2xwUU5oOGxaeGF4dHFoQ3h4dlo1cVRhTWtxaEswMFVDbGhsPTFobmxDcGpKS2tLVStBa2ZmclZyS1NocG49bWVUNEJTcHo3VmtwbXVvV1I9dHdieGZOcEVaRmNyMFBpWXp5b0NaRmlwRSs0PVArNz1nPWlmRno9UjIwRFl4cnF3cTIrYj00ak1LOVhPT09kdHFGSy8zSHlHTVoyVnZVV2xGR2JESjhER05iWjhueUt3R2VUb01qVHJiOGZSWGdGMWFxWmlIUTdDRlpVQ1JsTzRYMk5IeWVFUk9Tb1Fvb0Mwdj10eUZadDFJYWZCPWpJK1dnZk4rdkFGb2piQUlGbEpWSTJ1YTMzWXAvQkRxN1pNK2xGYWV0T29aVkczd3RUNUdIcEg2T3dNUlYvOHQxZTJ3NmM5dUcvUWZiNDYyM3ROVEgzQ3d0djJ1Y0cyejBHNHJIeG41T0dpZzZlY2hCc01rQ1EyQVo1SFJJNVM4cnA9Q2tzL1lDdE5ZZ1hLc1FDR2o5UFI2VEZZelFOWGlBK0pGQ29LQWJkd2I3bjhJRmsrYmxueWJtclRkanFBUitrUHdRd2RmOStoRVI3d1lSOWt5djluK2FCNlJXeFgrbmpXcDZaQ1FNT0gxa0c2bGRIUS8yRVljT289cGpUdXJKdSt0TnJvbm1hY0o3Umo1N00wMEJHK3JBb2Nla0krM1NwT0RLczhyRGhDVlcxc0ZJNk82RWFBUUsyQWdxb2s5a1dOZG9NcWVzSDRaT1JxNUhXeS8xOWNXU0NtWXcrZmhGVGNueXFWeVdQV1V0WDVHNmlWbXdYODZoNkdxYUU4c3JPWGtzWXM9U2dJQlUvdEk4NnRuSGFVMz1KcEEvM2o3bWdYQ3hIUnpkWTNTdW1UQmg5R2NRa3dRajByNENTa0JWYWZsbXhaNmQwd1JuRG9UQTk3SVNaZnhIL1UyV1B4OVY4ZzdBM0IzZVg5d1ZHUGUvQlIxblVJWlZSY09ZSE92K3NpNnU3Y0RtRGJzNWRUcTBSNEhPcEJta2hxdjZmSGJwa1BBQTdyc3A4ZDdHdGh6b1R2Q3NFYzQ9ej1FZ2V3anM3PUJuYmt1Y2wwTm9qakYrZDJrME56eUcwbytKSTNKUTNVPVNTd05pdlhXajQyamFIUkoraVA1QTR1a2JjSkp1QjNXbGpnNWdBbHhJeVMvdWo0Q1ROeitUcmdrdmJxUGtmbDdvTi9xbGs5RG9vNT1WSHdBTnVhd2c3Tk81SG0vMT16akZmck49UGYyMDk4YXBTK1JIdWs3dWM5VGVGPTJHSFkrbXJaTXlJekkvKzJ0a0U2RDkyWkpIaVE5TmE3L1Q2SVdVU0dNcHhiWmlZbDRjQmlxWVhiRUZ2RUdGNGp0U293bFluSUFxWnJQcjkybE5HZ1loUmZxN2JrZ0FGZ2FiN1haL2FTPThWdTAweklGSjJESVp6Q0d2cmRYZkYraVNUcnNQUm1UVEFzUGxNdFRhZURXa0lIL2VSWjlCdm8yNUovNHJrVzNybzBWbkJoTStnPTJBT0lPajFheGhqbW9xK0UvcURDd2c0a0FoYmw0VGpRa2Z6RHdaK1dFbkhsPWh5VHc3S3krcVRNbE5mZ080MUFvc3YvNFpvMGpPZVI1eVh0MlpPMWx2SnZhaFpPd2YvMC9UcEpDc1VPdlNYYjJaMVFiRWdSK0VXdURnNkdqdlgyRGkyaE5pZmd0YmlTN0E5ZWFad1ZXd0JFb1ZoOFU3QmFXNXh2SEZoT2ZHQ0daQ1Y4RUIvbU5vN0JzUHZiWGhUVkcySlNIZjVlVWYwZnZDd2Fjckp2d1d6NVZHVHI0bz0zUWJid25LOWU5bTEvbFdINkdxUGdWPTVOL0dsK2JJOFhtUFJsbFhXcHpwSGNCND1DMW9HQnNUNS9YaHF4R01SL2w3R3d4TmxrdjBLN3Q9aDJNR0JXemNmPWppVmk5bGlDVnhFL2Vjd3hvWDdPeWZlc01ReDk4QmFhY0NvaXM3L0pFSEhIMyIsImltZ2V4dCI6Ii5naWYiLCJpbWd0YWciOiIyIiwic2lnbm5hbWUiOiLpmYjojILog5wgMzIxMTgyMTk4OTAyMTQwNTE1Iiwic2lnbnNuIjoiSE40UEZJZ20yK281QUV3WTBTbGpaZmV0V2hCY1JueDZwcWJEVD1DT3ZhaUdYN3k5dWtzMS9WTVE4clUzS3pKZEwiLCJzdGFydGRhdGEiOiJudWxsLG51bGw7IiwidXNlcm5hbWUiOiLpmYjojILog5wiLCJ3aWR0aCI6IjEuNDYifSwicHJvdGVjdGVkRGF0YSI6W10sInNlYWxUeXBlIjoiY2xpZW50IiwidmVyIjp7Im5hbWUiOiIxLjAuMjAiLCJjb2RlIjoxMDB9LCJkYXRlVGltZSI6eyJpc0NoZWNrIjpmYWxzZSwiZm9udERhdGUiOnsiZm9udEZvcm1hdCI6Inl5eXktTS1kIiwiZm9udEZhbWlseSI6IuWui+S9kyIsImZvbnRTaXplIjoxMiwiZm9udENvbG9yIjoiIzAwMDAwMCIsImZvbnRQb3NpdGlvbiI6WyJjZW50ZXIiLCJtaWRkbGUiLCJpbnNpZGUiLCIiLCIiXX19LCJwb3NpdGlvbiI6eyJwb3MwIjp7fX0sInRpbWVpZCI6MTU2MDA3MjI0MDAxNX0=',
      //             // "uKeyImg":'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'
      //             uKey: this.getSignatureData(),
      //             uKeyImg: imgCon
      //           }
      //         )
      //         .then(
      //           function(res) {
      //             localStorage.setItem("setToken", "");
      //             if (res.body.code == 0) {
      //               localStorage.setItem("setToken", res.body.data.token);
      //               localStorage.setItem(
      //                 "loginUser",
      //                 res.body.data.user.userName
      //               );
      //               localStorage.setItem(
      //                 "loginpersonId",
      //                 res.body.data.user.personId
      //               );
      //               localStorage.setItem(
      //                 "loginDeptId",
      //                 res.body.data.user.deptId
      //               );
      //               localStorage.setItem(
      //                 "loginCompanyId",
      //                 res.body.data.user.companyId
      //               );
      //               localStorage.setItem(
      //                 "loginPhone",
      //                 res.body.data.user.phone
      //               );
      //               localStorage.setItem("idCard", res.body.data.user.idNumber);
      //               localStorage.setItem(
      //                 "policeNo",
      //                 res.body.data.user.policeNo
      //               );
      //               self.$router.push("/home2");
      //             } else {
      //               self.$message.error(res.body.message);
      //             }
      //           },
      //           function(res) {
      //             self.$message.error("请求失败");
      //           }
      //         );
      //     },
      //     cancelCall: function() {
      //       //点击取消后的回调方法
      //       console.log("取消！");
      //     }
      //   });
      // }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClick(tab, event) {
      this.loginTypeNum = tab.index;
      console.log(this.loginTypeNum);
    },
    //下载‘提交’后的申请表
    downWorld() {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr(
        "action",
        "http://10.33.66.86:9999/download/goAbroad/zhzg.zip"
      ); //待修改0
      $(document.body).append($eleForm);
      //提交表单，实现下载
      $eleForm.submit();
    }
  },
  mounted() {
    //页面加载完执行
    $(".el-container").css({
      "background-image":
        "url(" + require("../../../static/img/login_bg.png") + ")",
      "background-size": "100% 100%"
    });
    // $('.ms-login').css({'background-image':'url(' + require('../../../static/img/login_bg2.png') + ')','background-size':'100% 100%'})
  }
};
</script>
<style scoped>
#pane-first .downLoad {
  width: 160px;
  color: #409eff;
  display: inline-block;
  float: left;
  font-size: 12px;
}
#pane-first .downLoad:hover {
  cursor: pointer;
}
.right-info {
  position: absolute;
  color: #ddd;
  top: 8px;
  right: 10px;
  font-size: 12px;
}

.right-info i {
  position: relative;
  top: 2px;
}

.right-info span {
  cursor: pointer;
}

.el-header,
.el-footer {
  font-size: 12px;
  color: #bbb;
  text-align: center;
  line-height: 20px;
}

.el-form {
  padding: 10px 40px;
}

.bot-info {
  font-size: 14px;
  width: 50%;
  text-align: center;
  margin: 7% auto;
  margin-bottom: 0;
}

.szzs-btn {
  cursor: pointer;
}

.bot-info > a {
  color: #ccc;
}

.use-help,
.el-dropdown {
  height: 100%;
  line-height: 60px;
  color: #fff;
  font-size: 18px;
  float: right;
  width: 140px;
  text-align: center;
  cursor: pointer;
}

.bottom-box {
  width: 100%;
  position: absolute;
  bottom: 110px !important;
}

.el-container {
  width: 100%;
  height: 100%;
  /* background: url(../../../static/img/login_bg.png) no-repeat center;  */
  /* background-size: 100% 100%;  */
}

.el-input > input {
  background: transparent;
  border-radius: 16px;
}

.forget-pass {
  color: #409eff;
  font-size: 12px;
}

.ms-login {
  position: absolute;
  top: 43%;
  left: 50%;
  margin-left: -240px;
  width: 490px;
  height: 620px;
  margin-top: -300px;
  /* background: url(../../../static/img/login_bg2.png) no-repeat center;   */
  /* background-size: 100% 100%;   */
}

.ms-logo {
  height: 60px;
  line-height: 60px;
  float: left;
  font-size: 26px;
  color: #fff;
}

.login-btn {
  width: 84%;
  background: #638bb3;
  border-radius: 16px;
  display: block;
  margin: 8% auto;
  margin-top: 0;
  border-color: #638bb3;
}

.ms-login .el-form-item {
  margin-bottom: 30px !important;
}
</style>
<style>
/*在获取到的样式里加上能限制范围的父层选择器，不然就变成全局样式  */
.ui-dialog {
  *zoom: 1;
  _float: left;
  position: relative;
  background-color: #fff;
  border: 1px solid #999;
  border-radius: 6px;
  outline: 0;
  background-clip: padding-box;
  font-family: Helvetica, arial, sans-serif;
  font-size: 14px;
  line-height: 1.428571429;
  color: #333;
  opacity: 0;
  -webkit-transform: scale(0);
  transform: scale(0);
  -webkit-transition: -webkit-transform 0.15s ease-in-out,
    opacity 0.15s ease-in-out;
  transition: transform 0.15s ease-in-out, opacity 0.15s ease-in-out;
}
.ui-popup-show .ui-dialog {
  opacity: 1;
  -webkit-transform: scale(1);
  transform: scale(1);
}
.ui-popup-focus .ui-dialog {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
}
.ui-popup-modal .ui-dialog {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1), 0 0 256px rgba(255, 255, 255, 0.3);
}
.ui-dialog-grid {
  width: auto;
  margin: 0;
  border: 0 none;
  border-collapse: collapse;
  border-spacing: 0;
  background: transparent;
}
.ui-dialog-header,
.ui-dialog-body,
.ui-dialog-footer {
  padding: 0;
  border: 0 none;
  text-align: left;
  background: transparent;
}
.ui-dialog-header {
  white-space: nowrap;
  border-bottom: 1px solid #e5e5e5;
}
.ui-dialog-close {
  position: relative;
  _position: absolute;
  float: right;
  top: 13px;
  right: 13px;
  _height: 26px;
  padding: 0 4px;
  font-size: 21px;
  font-weight: bold;
  line-height: 1;
  color: #000;
  text-shadow: 0 1px 0 #fff;
  opacity: 0.2;
  filter: alpha(opacity=20);
  cursor: pointer;
  background: transparent;
  _background: #fff;
  border: 0;
  -webkit-appearance: none;
}
.ui-dialog-close:hover,
.ui-dialog-close:focus {
  color: #000000;
  text-decoration: none;
  cursor: pointer;
  outline: 0;
  opacity: 0.5;
  filter: alpha(opacity=50);
}
.ui-dialog-title {
  margin: 0;
  line-height: 1.428571429;
  min-height: 16.428571429px;
  padding: 15px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-weight: bold;
  cursor: default;
}
.ui-dialog-body {
  padding: 20px;
  text-align: center;
}
.ui-dialog-content {
  display: inline-block;
  position: relative;
  vertical-align: middle;
  *zoom: 1;
  *display: inline;
  text-align: left;
}
.ui-dialog-footer {
  padding: 0 20px 20px 20px;
}
.ui-dialog-statusbar {
  float: left;
  margin-right: 20px;
  padding: 6px 0;
  line-height: 1.428571429;
  font-size: 14px;
  color: #888;
  white-space: nowrap;
}
.ui-dialog-statusbar label:hover {
  color: #333;
}
.ui-dialog-statusbar input,
.ui-dialog-statusbar .label {
  vertical-align: middle;
}
.ui-dialog-button {
  float: right;
  white-space: nowrap;
}
.ui-dialog-footer button + button {
  margin-bottom: 0;
  margin-left: 5px;
}
.ui-dialog-footer button {
  width: auto;
  overflow: visible;
  display: inline-block;
  padding: 6px 12px;
  _margin-left: 5px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;
}

.ui-dialog-footer button:focus {
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}

.ui-dialog-footer button:hover,
.ui-dialog-footer button:focus {
  color: #333333;
  text-decoration: none;
}

.ui-dialog-footer button:active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
  box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
}
.ui-dialog-footer button[disabled] {
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.65;
  filter: alpha(opacity=65);
  -webkit-box-shadow: none;
  box-shadow: none;
}

.ui-dialog-footer button {
  color: #333333;
  background-color: #ffffff;
  border-color: #cccccc;
}

.ui-dialog-footer button:hover,
.ui-dialog-footer button:focus,
.ui-dialog-footer button:active {
  color: #333333;
  background-color: #ebebeb;
  border-color: #adadad;
}

.ui-dialog-footer button:active {
  background-image: none;
}

.ui-dialog-footer button[disabled],
.ui-dialog-footer button[disabled]:hover,
.ui-dialog-footer button[disabled]:focus,
.ui-dialog-footer button[disabled]:active {
  background-color: #ffffff;
  border-color: #cccccc;
}

.ui-dialog-footer button.ui-dialog-autofocus {
  color: #ffffff;
  background-color: #428bca;
  border-color: #357ebd;
}

.ui-dialog-footer button.ui-dialog-autofocus:hover,
.ui-dialog-footer button.ui-dialog-autofocus:focus,
.ui-dialog-footer button.ui-dialog-autofocus:active {
  color: #ffffff;
  background-color: #3276b1;
  border-color: #285e8e;
}

.ui-dialog-footer button.ui-dialog-autofocus:active {
  background-image: none;
}
.ui-popup-top-left .ui-dialog,
.ui-popup-top .ui-dialog,
.ui-popup-top-right .ui-dialog {
  top: -8px;
}
.ui-popup-bottom-left .ui-dialog,
.ui-popup-bottom .ui-dialog,
.ui-popup-bottom-right .ui-dialog {
  top: 8px;
}
.ui-popup-left-top .ui-dialog,
.ui-popup-left .ui-dialog,
.ui-popup-left-bottom .ui-dialog {
  left: -8px;
}
.ui-popup-right-top .ui-dialog,
.ui-popup-right .ui-dialog,
.ui-popup-right-bottom .ui-dialog {
  left: 8px;
}

.ui-dialog-arrow-a,
.ui-dialog-arrow-b {
  position: absolute;
  display: none;
  width: 0;
  height: 0;
  overflow: hidden;
  /* _color:#FF3FFF;
    _filter:chroma(color=#FF3FFF); */
  border: 8px dashed transparent;
}
.ui-popup-follow .ui-dialog-arrow-a,
.ui-popup-follow .ui-dialog-arrow-b {
  display: block;
}
.ui-popup-top-left .ui-dialog-arrow-a,
.ui-popup-top .ui-dialog-arrow-a,
.ui-popup-top-right .ui-dialog-arrow-a {
  bottom: -16px;
  border-top: 8px solid #7c7c7c;
}
.ui-popup-top-left .ui-dialog-arrow-b,
.ui-popup-top .ui-dialog-arrow-b,
.ui-popup-top-right .ui-dialog-arrow-b {
  bottom: -15px;
  border-top: 8px solid #fff;
}
.ui-popup-top-left .ui-dialog-arrow-a,
.ui-popup-top-left .ui-dialog-arrow-b {
  left: 15px;
}
.ui-popup-top .ui-dialog-arrow-a,
.ui-popup-top .ui-dialog-arrow-b {
  left: 50%;
  margin-left: -8px;
}
.ui-popup-top-right .ui-dialog-arrow-a,
.ui-popup-top-right .ui-dialog-arrow-b {
  right: 15px;
}
.ui-popup-bottom-left .ui-dialog-arrow-a,
.ui-popup-bottom .ui-dialog-arrow-a,
.ui-popup-bottom-right .ui-dialog-arrow-a {
  top: -16px;
  border-bottom: 8px solid #7c7c7c;
}
.ui-popup-bottom-left .ui-dialog-arrow-b,
.ui-popup-bottom .ui-dialog-arrow-b,
.ui-popup-bottom-right .ui-dialog-arrow-b {
  top: -15px;
  border-bottom: 8px solid #fff;
}
.ui-popup-bottom-left .ui-dialog-arrow-a,
.ui-popup-bottom-left .ui-dialog-arrow-b {
  left: 15px;
}
.ui-popup-bottom .ui-dialog-arrow-a,
.ui-popup-bottom .ui-dialog-arrow-b {
  margin-left: -8px;
  left: 50%;
}
.ui-popup-bottom-right .ui-dialog-arrow-a,
.ui-popup-bottom-right .ui-dialog-arrow-b {
  right: 15px;
}
.ui-popup-left-top .ui-dialog-arrow-a,
.ui-popup-left .ui-dialog-arrow-a,
.ui-popup-left-bottom .ui-dialog-arrow-a {
  right: -16px;
  border-left: 8px solid #7c7c7c;
}
.ui-popup-left-top .ui-dialog-arrow-b,
.ui-popup-left .ui-dialog-arrow-b,
.ui-popup-left-bottom .ui-dialog-arrow-b {
  right: -15px;
  border-left: 8px solid #fff;
}
.ui-popup-left-top .ui-dialog-arrow-a,
.ui-popup-left-top .ui-dialog-arrow-b {
  top: 15px;
}
.ui-popup-left .ui-dialog-arrow-a,
.ui-popup-left .ui-dialog-arrow-b {
  margin-top: -8px;
  top: 50%;
}
.ui-popup-left-bottom .ui-dialog-arrow-a,
.ui-popup-left-bottom .ui-dialog-arrow-b {
  bottom: 15px;
}
.ui-popup-right-top .ui-dialog-arrow-a,
.ui-popup-right .ui-dialog-arrow-a,
.ui-popup-right-bottom .ui-dialog-arrow-a {
  left: -16px;
  border-right: 8px solid #7c7c7c;
}
.ui-popup-right-top .ui-dialog-arrow-b,
.ui-popup-right .ui-dialog-arrow-b,
.ui-popup-right-bottom .ui-dialog-arrow-b {
  left: -15px;
  border-right: 8px solid #fff;
}
.ui-popup-right-top .ui-dialog-arrow-a,
.ui-popup-right-top .ui-dialog-arrow-b {
  top: 15px;
}
.ui-popup-right .ui-dialog-arrow-a,
.ui-popup-right .ui-dialog-arrow-b {
  margin-top: -8px;
  top: 50%;
}
.ui-popup-right-bottom .ui-dialog-arrow-a,
.ui-popup-right-bottom .ui-dialog-arrow-b {
  bottom: 15px;
}

@-webkit-keyframes ui-dialog-loading {
  0% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
  }
}
@keyframes ui-dialog-loading {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.ui-dialog-loading {
  vertical-align: middle;
  position: relative;
  display: block;
  *zoom: 1;
  *display: inline;
  overflow: hidden;
  width: 32px;
  height: 32px;
  top: 50%;
  margin: -16px auto 0 auto;
  font-size: 0;
  text-indent: -999em;
  color: #666;
}
.ui-dialog-loading {
  width: 100%\9;
  text-indent: 0\9;
  line-height: 32px\9;
  text-align: center\9;
  font-size: 12px\9;
}

.ui-dialog-loading::after {
  position: absolute;
  content: "";
  width: 3px;
  height: 3px;
  margin: 14.5px 0 0 14.5px;
  border-radius: 100%;
  box-shadow: 0 -10px 0 1px #ccc, 10px 0px #ccc, 0 10px #ccc, -10px 0 #ccc,
    -7px -7px 0 0.5px #ccc, 7px -7px 0 1.5px #ccc, 7px 7px #ccc, -7px 7px #ccc;
  -webkit-transform: rotate(360deg);
  -webkit-animation: ui-dialog-loading 1.5s infinite linear;
  transform: rotate(360deg);
  animation: ui-dialog-loading 1.5s infinite linear;
  display: none;
}
.kg-show {
  display: block;
}

.kg-visble {
  visibility: hidden;
}

.kg-hide {
  display: none;
}

.kg-default {
  position: absolute;
  z-index: 89;
}

.display-landscape {
  position: static;
  overflow: auto;
}

.kg-landscape-container {
  position: relative;
}

.kg-landscape-container .kg-landscape {
  position: relative;
  display: inline-block;
  *display: inline;
  vertical-align: middle;
}

.kg-invalid {
  position: absolute;
  top: 33%;
  left: 0px;
  height: 33%;
  width: 100%;
  z-index: 90;
}

.kg-invalid .kg-invalid-item {
  width: 100%;
  position: relative;
}

.kg-shade {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.kg-date {
  position: absolute;
}

.kg-date-content {
  position: relative;
}

.kg-date-tmb {
  position: absolute;
}

.kg-date-lcr {
  min-width: 110px;
}
.kg-img-div:hover {
  cursor: pointer;
}

.kg-img-div .kg-img-icons {
  display: none;
  text-decoration: none;
  margin: 0px;
  padding: 0px;
  position: absolute;
  top: 0px;
  right: 0;
  z-index: 92;
  background-color: #d5d5d5;
  opacity: 0.85;
  filter: alpha(opacity=85);
  text-align: center;
  vertical-align: middle;
  border: 1px #adadad solid;
}

.kg-img-div:hover .kg-img-icons {
  display: block;
}

.kg-img-div .kg-img-icons .kg-img-icon {
  cursor: pointer;
  display: block;
  float: left;
  margin: 5px 3px 3px 3px;
}

.kg-img-div .kg-img-icons .kg-img-icon i {
  display: inline-block;
  width: 20px;
  height: 20px;
}

.kg-select {
  width: 100%;
  padding-top: 4px;
  margin-bottom: 0;
  margin-top: 3px;
  border: 1px solid #cccccc;
}

.kg-color-ul {
  padding: 0;
  margin: 0;
  list-style: none;
  width: 148px;
  border: 1px solid #cccccc;
  display: none;
}
.kg-color-ul li {
  background-color: #ffffff;
}
.kg-color-ul li a {
  display: inline-block;
  width: 148px;
  height: 16px;
  text-decoration: none;
}
.kg-color-div {
  width: 150px;
  height: 100px;
  overflow-y: scroll;
  overflow-x: hidden;
  position: absolute;
  z-index: 999999;
  display: none;
}

.kg-select-color {
  width: 100%;
  height: 21px;
  margin-top: 4px;
  border: 1px solid #cccccc;
}
.kg-group {
  display: inline-block;
  *display: inline;
  zoom: 1;
  margin-bottom: 0;
  vertical-align: middle;
  text-align: center;
  margin: 0 auto;
  width: 100%;
}
.kg-group label {
  display: inline-block;
  max-width: 30%;
  text-align: right;
  margin-bottom: 5px;
  font-weight: 700;
}
.kg-group .kg-control {
  display: inline-block;
  width: 135px;
  vertical-align: middle;
  height: 20px;
  padding: 6px 12px 6px 0px;
  font-size: 14px;
  line-height: 25px;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}
.kg-switcher {
  width: 180px;
  height: 180px;
  padding: 10px 45px 25px 45px;
  margin: 0px auto;
  position: relative;
}

.kg-container {
  margin: 0 auto;
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 100%;
  color: #fff;
  text-align: center;
  backface-visibility: hidden;
  z-index: 1;
  border: 1px solid #dddddd;
}

.arrow-right {
  position: absolute;
  right: 8px;
  top: 50%;
  margin-top: -15px;
  text-decoration: none;
  display: block;
  z-index: 10;
}

.arrow-left {
  position: absolute;
  left: 8px;
  top: 50%;
  margin-top: -15px;
  text-decoration: none;
  display: block;
  z-index: 10;
}

.arrow-left .kg-icon,
.arrow-right .kg-icon {
  display: inline-block;
  width: 20px;
  height: 20px;
}
.kg-wrapper {
  position: relative;
  width: 100%;
  box-sizing: content-box;
}

.kg-slide {
  float: left;
  width: 172px;
  height: 172px;
  position: relative;
  margin-left: 11px;
  margin-top: 13px;
}

.kg-image {
  width: 158px;
  height: 158px;
  overflow: hidden;
  text-align: center;
  display: table;
  float: left;
  margin: 10px;
  position: relative;
  table-layout: fixed;
}

.kg-image:hover {
  border: 1px solid #beceeb;
}

.kg-image a.kg-img {
  display: table-cell;
  vertical-align: middle;
  width: 150px;
  height: 150px;
  max-width: 150px;
  max-height: 150px;
}

.kg-image .kg-img img {
  border: 0;
  margin: 0 auto;
  max-width: 150px;
  max-height: 150px;
}

.kg-slide div.title {
  position: absolute;
  text-align: center;
  display: none;
}

.kg-img-checked a.kg-img {
  border: 5px solid #0088cc;
}

.kg-pagin {
  position: absolute;
  text-align: center;
  bottom: 0px;
  text-align: center;
  width: 100%;
  left: 0;
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  -o-transition: 0.3s;
  transition: 0.3s;
  -webkit-transform: translate3d(0, 0, 0);
  -ms-transform: translate3d(0, 0, 0);
  -o-transform: translate3d(0, 0, 0);
  transform: translate3d(0, 0, 0);
  z-index: 10;
}

.kg-pagin .kg-guide {
  text-align: center;
  width: auto;
}
.kg-pagin .kg-guide span {
  display: inline-block;
  width: 8px;
  height: 8px;
  margin: 0 2px;
  background: #cccccc;
  cursor: pointer;
  font-size: 14px;
  border-radius: 100%;
}

.kg-guide span.active {
  opacity: 1;
  background: #ec3535;
}

.kg-title .kg-icon {
  display: inline-block;
  width: 48px;
  height: 48px;
  vertical-align: -18px;
  margin-left: 10px;
}
.kg-loading {
  text-align: center;
  padding: 20px 30px 15px 30px;
}
.kg-alert {
  min-width: 250px;
  padding-left: 20px;
  padding-right: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
}

.kg-alert .kg-alert-icon,
.kg-alert .kg-alert-msg {
  float: left;
  height: 100%;
  vertical-align: middle;
}

.kg-alert .kg-alert-msg {
  vertical-align: middle;
  display: table-cell;
  width: 200px;
}
.success,
.danger {
  font-weight: bold;
}

.kg-title.danger .kg-icon {
  color: #e75033;
  text-decoration: none;
}

.clearfix:after {
  content: ".";
  display: block;
  height: 0;
  visibility: hidden;
  clear: both;
}
.clearfix {
  *zoom: 1;
}

.kg-content {
  padding: 10px;
  border-top: none;
}

/**
标签tab css
*/
.kg-tab {
}

.kg-tab .kg-nav {
  padding: 0;
  margin: 0;
  list-style: none;
  border-bottom: 1px solid #ddd;
}

.kg-nav > li {
  float: left;
  margin-bottom: -1px;
  position: relative;
  display: block;
  list-style: none;
}

.kg-nav > li > a {
  position: relative;
  display: block;
  padding: 8px 15px;
  color: #353535;
  -webkit-transition: all 0.2s cubic-bezier(0.175, 0.885, 0.32, 1);
  transition: all 0.2s cubic-bezier(0.175, 0.885, 0.32, 1);
  margin-right: 2px;
  text-decoration: none;
  line-height: 1.53846154;
  border: 1px solid transparent;
  border-radius: 4px 4px 0 0;
}

.kg-nav > li.active > a,
.kg-nav > li.active > a:focus,
.kg-nav > li.active > a:hover {
  color: grey;
  cursor: default;
  background-color: #fff;
  border: 1px solid #ddd;
  border-bottom-color: transparent;
}

.kg-tab-content {
  height: 230px;
  overflow: auto;
  border: 1px #ddd solid;
  border-top: none;
}

.kg-tab-content .kg-tab-pane {
  display: none;
}

.kg-tab-content > .active {
  display: block;
}

.kg-meta {
  padding: 10px 10px 0px 10px;
}

.kg-meta .kg-item {
  margin-top: 3px;
}

.kg-meta .kg-label {
  width: 16%;
  float: left;
  display: inline-block;
  text-align: right;
}
.kg-meta .kg-value {
  width: 84%;
  float: left;
  display: inline-block;
  text-align: left;
  word-break: break-all;
}

.kg-sm {
  position: relative;
  min-height: 1px;
  float: left;
}
.kg-sm-12 {
  width: 100%;
}
.kg-sm-11 {
  width: 91.66666667%;
}
.kg-sm-10 {
  width: 83.33333333%;
}
.kg-sm-9 {
  width: 75%;
}
.kg-sm-8 {
  width: 66.66666667%;
}
.kg-sm-7 {
  width: 58.33333333%;
}
.kg-sm-6 {
  width: 50%;
}
.kg-sm-5 {
  width: 41.66666667%;
}
.kg-sm-4 {
  width: 33.33333333%;
}
.kg-sm-3 {
  width: 25%;
}
.kg-sm-2 {
  width: 16.66666667%;
}

.kg-form {
  margin-bottom: 5px;
  margin-top: 5px;
}

.kg-form .form-item .kg-label {
  display: inline-block;
  max-width: 100%;
  margin-bottom: 5px;
  padding-top: 7px;
  margin-bottom: 0;
  text-align: right;
}

.kg-form-static {
  margin: 0 0 10px;
  min-height: 34px;
  padding-top: 7px;
  margin-bottom: 0;
}

.form-control {
  display: block;
  padding: 6px 0px;
  font-size: 14px;
  width: 100%;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}
.kg-dialog * {
  box-sizing: content-box;
  -moz-box-sizing: content-box;
  -ms-box-sizing: content-box;
  -webkit-box-sizing: content-box;
}

.kg-dialog-info {
  width: 670px;
  padding-bottom: 20px;
}

.kg-dialog-password {
  width: 300px;
  font-size: 14px;
  padding-left: 15px;
  padding-right: 15px;
}
.kg-dialog-Date {
  width: 300px;
  height: 160px;
  font-size: 14px;
  display: block;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}

.kg-button {
  width: auto;
  overflow: visible;
  display: inline-block;
  padding: 6px 12px;
  _margin-left: 5px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;

  color: #ffffff;
  background-color: #c62929;
  border-color: #d00a0a;
}

.ui-dialog-body {
  padding: 10px;
}
.ui-dialog-button {
  float: none;
  text-align: center;
  white-space: nowrap;
}

.ui-dialog-footer button {
  letter-spacing: 5px;
  text-indent: 5px;
  text-align: center;
}

.ui-dialog-footer button.ui-dialog-autofocus {
  background-color: #c62929;
  border-color: #ca2121;
}
.ui-dialog-footer button.ui-dialog-autofocus:hover,
.ui-dialog-footer button.ui-dialog-autofocus:focus,
.ui-dialog-footer button.ui-dialog-autofocus:active {
  color: #ffffff;
  background-color: #c62929;
  border-color: #d00a0a;
}
.i-addDate {
  display: none;
}
#kg-switcher > a {
  display: none;
}
.kg-pagin {
  display: none;
}
.ui-dialog-button > button:first-child {
  display: none;
}
.ui-dialog-autofocus {
  border-color: #409eff !important;
  background: #409eff !important;
}
.ms-login .el-form-item__label {
  color: #eee;
}

.ms-login .el-input__inner {
  background: transparent;
  border-radius: 16px;
  color: #eee;
}

.ms-login .el-tabs__item.is-active {
  color: #fff;
}

.ms-login .el-tabs__item {
  color: #5b6979;
  font-size: 16px;
}

.ms-login .el-tabs--card > .el-tabs__header {
  border: none;
  width: 80%;
  margin: 4% auto;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
  border-bottom: 2px solid #3b82ff;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__nav {
  border: none;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__item {
  border: none;
}
/*!
 * ui-dialog.css
 * Date: 2014-07-03
 * https://github.com/aui/artDialog
 * (c) 2009-2014 TangBin, http://www.planeArt.cn
 *
 * This is licensed under the GNU LGPL, version 2.1 or later.
 * For details, see: http://www.gnu.org/licenses/lgpl-2.1.html
 */
.ms-login .el-form-item__label {
  width: 70px !important;
}
.ms-login .el-form-item__content {
  margin-left: 70px !important;
}
</style>

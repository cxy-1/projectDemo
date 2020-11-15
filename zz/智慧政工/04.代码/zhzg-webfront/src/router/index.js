import Vue from 'vue';
import Router from 'vue-router';
import login from '@/components/page/Login';//公共路由入口
Vue.use(Router);

export default new Router({
    // mode: 'history',
    routes: [
        {
            path: '/login',
            name: 'login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/indexManage/:token?',
            component: resolve => require(['../components/page/IndexManage.vue'], resolve)
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '出境管理' }
                },
                {
                    path: '/addbeonleave',
                    component: resolve => require(['../components/page/beonleave3.vue'], resolve),
                    meta: { title: '年休假计划' }
                },
                {
                    path: '/dashboard2',
                    component: resolve => require(['../components/page/Dashboard2.vue'], resolve),
                    meta: { title: '出境管理' }
                },
                {
                    path: '/dashboard3',
                    component: resolve => require(['../components/page/Dashboard3.vue'], resolve),
                    meta: { title: '出境管理' }
                },
                {
                    path: '/dashboard5',
                    component: resolve => require(['../components/page/Dashboard5.vue'], resolve),
                    meta: { title: '出国境销假' }
                },
                {
                    path: '/dashboard4',
                    component: resolve => require(['../components/page/Dashboard4.vue'], resolve),
                    meta: { title: '出境管理' }
                },
                {
                    path: '/organdefend',
                    component: resolve => require(['../components/page/OrganDefend.vue'], resolve),
                    meta: { title: '组织机构维护' }
                },
                {
                    path: '/organdefend2',
                    component: resolve => require(['../components/page/OrganDefend2.vue'], resolve),
                    meta: { title: '组织机构添加部门' }
                },
                {
                    path: '/organchange',
                    component: resolve => require(['../components/page/OrganChange.vue'], resolve),
                    meta: { title: '机构变更申请' }
                },
                {
                    path: '/organapprove',
                    component: resolve => require(['../components/page/OrganApprove.vue'], resolve),
                    meta: { title: '机构变更审批' }
                },
                {
                    path: '/passportList',
                    component: resolve => require(['../components/page/passportList.vue'], resolve),
                    meta: { title: '护照列表' }
                },
                {
                    path: '/passportDetail',
                    component: resolve => require(['../components/page/passporeDetail.vue'], resolve),
                    meta: { title: '护照列表' }
                },

                {
                    path: '/organapprove2',
                    component: resolve => require(['../components/page/OrganApprove2.vue'], resolve),
                    meta: { title: '机构变更审批' }
                },
                {
                    path: '/home2',
                    component: resolve => require(['../components/page/IndexHome2.vue'], resolve),
                    meta: { title: '首页' }
                },
                {
                    path: '/table',
                    component: resolve => require(['../components/page/BaseTable.vue'], resolve),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../components/page/Tabs.vue'], resolve),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/myFiles',
                    component: resolve => require(['../components/page/MyFilesNew.vue'], resolve),
                    meta: { title: '我的档案' }
                },
                {
                    path: '/policeManage',
                    component: resolve => require(['../components/page/PoliceManage.vue'], resolve),
                    meta: { title: '警员信息' }
                },
                {
                    path: '/depart',
                    component: resolve => require(['../components/page/DepartListNew.vue'], resolve),
                    meta: { title: '出国（境）核查主页' }
                },
                {
                    path: '/depart2',
                    component: resolve => require(['../components/page/Depart.vue'], resolve),
                    meta: { title: '出国（境）核查详情' }
                },
                {
                    path: '/CheckNormal',
                    component: resolve => require(['../components/page/checkNormal.vue'], resolve),
                    meta: { title: '核查列表详情' }
                },
                {
                    path: '/DepartList',
                    component: resolve => require(['../components/page/DepartList.vue'], resolve),
                    meta: { title: '核查列表详情' }
                },
                {
                    path: '/departAdd',
                    component: resolve => require(['../components/page/DepartAdd.vue'], resolve),
                    meta: { title: '新增核查' }
                },
                {
                    path: '/departDetailsSuccess',
                    component: resolve => require(['../components/page/DepartDetailsSuccess.vue'], resolve),
                    meta: { title: '核查无异常详情' }
                },
                {
                    path: '/departDetailsErr',
                    component: resolve => require(['../components/page/DepartDetailsErr.vue'], resolve),
                    meta: { title: '核查异常详情' }
                },
                {
                    path: '/trainingMinute',
                    component: resolve => require(['../components/page/TrainingMinute.vue'], resolve),
                    meta: { title: '训历管理详情' }
                },
                {
                    path: '/myTraining',
                    component: resolve => require(['../components/page/MyTraining.vue'], resolve),
                    meta: { title: '我的训历' }
                },
                {
                    path: '/training',
                    component: resolve => require(['../components/page/Training.vue'], resolve),
                    meta: { title: '训历管理' }
                },
                {
                    path: '/teamLeave',
                    component: resolve => require(['../components/page/TeamLeave.vue'], resolve),
                    meta: { title: '集体离宁审批列表' }
                },
                {
                    path: '/teamLeaveApply',
                    component: resolve => require(['../components/page/TeamLeaveApply.vue'], resolve),
                    meta: { title: '集体离宁申请' }
                },
                {
                    path: '/departDetails',
                    component: resolve => require(['../components/page/DepartDetails.vue'], resolve),
                    meta: { title: '出国（境）核查详情' }
                },
                {
                    path: '/myArms',
                    component: resolve => require(['../components/page/MyArms.vue'], resolve),
                    meta: { title: '我的战友' }
                },
                {
                    path: '/leaveManage',
                    component: resolve => require(['../components/page/LeaveManage.vue'], resolve),
                    meta: { title: '请销假管理' }
                },
                {
                    path: '/approveManage',
                    component: resolve => require(['../components/page/ApproveManage.vue'], resolve),
                    meta: { title: '请销假审批列表' }
                },
                {
                    path: '/leaExamApproval',
                    component: resolve => require(['../components/page/LeaExamApproval.vue'], resolve),
                    meta: { title: '请销假审批' }
                },
                {
                    path: '/resumptionLeave',
                    component: resolve => require(['../components/page/ResumptionLeave.vue'], resolve),
                    meta: { title: '销假' }
                },
                {
                    path: '/resumptionQuery',
                    component: resolve => require(['../components/page/ResumptionQuery.vue'], resolve),
                    meta: { title: '请销假查询' }
                },
                {
                    path: '/DashboardQuery',
                    component: resolve => require(['../components/page/DashboardQuery.vue'], resolve),
                    meta: { title: '出国境查询' }
                },
                {
                    path: '/dashboardWarn',
                    component: resolve => require(['../components/page/DashboardWarn.vue'], resolve),
                    meta: { title: '出境提醒设置' }
                },
                {
                    path: '/Evaluation',
                    component: resolve => require(['../components/page/Evaluation.vue'], resolve),
                    meta: { title: '评价反馈' }
                },
                {
                    path: '/EvaluationExplicit',
                    component: resolve => require(['../components/page/EvaluationExplicit.vue'], resolve),
                    meta: { title: '反馈详情' }
                },
                {
                    path: '/beonleave',
                    component: resolve => require(['../components/page/beonleave.vue'], resolve),
                    meta: { title: '年休假计划' }
                },
                {
                    path: '/beonleaveApprove',
                    component: resolve => require(['../components/page/beonleaveApprove.vue'], resolve),
                    meta: { title: '年休假计划' }
                },
                {
                    path: '/approveDetail',
                    component: resolve => require(['../components/page/approveDetail.vue'], resolve),
                    meta: { title: '年休假计划' }
                },
                {
                    path: '/holidayAnalyse',
                    component: resolve => require(['../components/page/holidayAnalyse.vue'], resolve),
                    meta: { title: '年休假计划' }
                },

                {
                    path: '/user',
                    component: resolve => require(['../components/page/UserList.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/userManager',
                    component: resolve => require(['../components/page/userManager.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/menuManager',
                    component: resolve => require(['../components/page/MenuManager.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/roleManager',
                    component: resolve => require(['../components/page/RoleManager.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/companyManager',
                    component: resolve => require(['../components/page/CompanyManager.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/role',
                    component: resolve => require(['../components/page/RoleList.vue'], resolve),
                    meta: { title: '角色管理' }
                },
                {
                    path: '/rolegroup',
                    component: resolve => require(['../components/page/RoleGroupManager.vue'], resolve),
                    meta: { title: '角色管理' }
                },
                {
                    path: '/addUser',
                    component: resolve => require(['../components/page/AddUser.vue'], resolve),
                    meta: { title: '添加用户' }
                },
                {
                    path: '/demo',
                    component: resolve => require(['../components/page/menudemo.vue'], resolve),
                    meta: { title: '添加用户' }
                },
                {
                    path: '/tree',
                    component: resolve => require(['../components/page/treelazy.vue'], resolve),
                    meta: { title: '添加用户' }
                },
                {
                    path: '/roleDetail',
                    component: resolve => require(['../components/page/roleDetail.vue'], resolve),
                    meta: { title: '角色详情' }
                },
                {
                    path: '/addPower',
                    component: resolve => require(['../components/page/AddPower.vue'], resolve),
                    meta: { title: '授权' }
                },
                {
                    path: '/mainAdmin',
                    component: resolve => require(['../components/page/MainAdmin.vue'], resolve),
                    meta: { title: '主管理员更换' }
                },
                {
                    path: '/sonAdmin',
                    component: resolve => require(['../components/page/SonAdmin.vue'], resolve),
                    meta: { title: '子管理员设置' }
                },
                {
                    path: '/addSonAdmin',
                    component: resolve => require(['../components/page/AddSonAdmin.vue'], resolve),
                    meta: { title: '子管理员设置' }
                },
                {
                    path: '/addSonAdmin2',
                    component: resolve => require(['../components/page/AddSonAdmin2.vue'], resolve),
                    meta: { title: '子管理员设置' }
                },
                {
                    path: '/addSonAdmin3',
                    component: resolve => require(['../components/page/AddSonAdmin3.vue'], resolve),
                    meta: { title: '子管理员设置' }
                },
                {
                    path: '/regBox',
                    component: resolve => require(['../components/page/RegBox.vue'], resolve),
                    meta: { title: '规则宝典' }
                },
                {
                    path: '/OnlineTable',
                    component: resolve => require(['../components/page/OnlineTable.vue'], resolve),
                    meta: { title: '在线表格' }
                },
                {
                    path: '/logManage',
                    component: resolve => require(['../components/page/LogManage.vue'], resolve),
                    meta: { title: '我的档案3' }
                },
                {
                    path: '/UseReport',
                    component: resolve => require(['../components/page/UseReport.vue'], resolve),
                    meta: { title: '使用报告' }
                },
                {
                    path: '/viewManage1',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage2',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage3',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage4',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage5',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage6',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage7',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage8',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage9',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage10',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage11',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage12',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage13',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage14',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage15',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage16',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage17',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage18',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage19',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage20',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage21',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage22',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage23',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage24',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage25',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage26',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage27',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage28',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage29',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage30',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage31',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage32',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage33',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage34',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage35',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage36',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage37',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage38',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage39',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage40',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage41',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage42',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage43',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage44',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage45',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage46',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                }, {
                    path: '/viewManage47',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                }, {
                    path: '/viewManage48',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                }, {
                    path: '/viewManage49',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                }, {
                    path: '/viewManage50',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                }, {
                    path: '/viewManage51',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },

                {
                    path: '/viewManage52',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage53',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage54',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage55',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage56',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage57',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage58',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },
                {
                    path: '/viewManage59',
                    component: resolve => require(['../components/page/ViewManage.vue'], resolve),
                    meta: { title: '敬请期待' }
                },



                {
                    path: '/portArms',// 持枪证申报
                    component: resolve => require(['../components/page/PortArms.vue'], resolve),
                    meta: { title: '我的持枪证' }
                },
                {
                    path: '/portArms2',// 持枪证审批列表
                    component: resolve => require(['../components/page/PortArms2.vue'], resolve),
                    meta: { title: '持枪证审批' }
                },
                {
                    path: '/portArms3',// 持枪证审批
                    component: resolve => require(['../components/page/PortArms3.vue'], resolve),
                    meta: { title: '持枪证审批' }
                },
                {
                    path: '/portArms4',
                    component: resolve => require(['../components/page/PortArms4.vue'], resolve),
                    meta: { title: '人员持枪库' }
                },
                {
                    path: '/yyfk',
                    component: resolve => require(['../components/page/myProcess/yyfk.vue'], resolve),
                    meta: { title: '个人中心-应用反馈' }
                },
                {
                    path: '/xxtzsz',
                    component: resolve => require(['../components/page/myProcess/xxtzsz.vue'], resolve),
                    meta: { title: '个人中心-消息通知设置' }
                },
                {
                    path: '/wdlc',
                    component: resolve => require(['../components/page/myProcess/wdlc.vue'], resolve),
                },
                {
                    path: '/myprocess',// vue-schart组件
                    component: resolve => require(['../components/page/MyProcess.vue'], resolve),
                    children: [

                        {
                            path: '/grsz',
                            component: resolve => require(['../components/page/myProcess/grsz.vue'], resolve),
                            meta: { title: '个人中心-个人设置' }
                        },
                        {
                            path: '/txxx',
                            component: resolve => require(['../components/page/myProcess/txxx.vue'], resolve),
                            meta: { title: '个人中心-通讯信息' }
                        },
                        {
                            path: '/spsq',
                            component: resolve => require(['../components/page/myProcess/spsq.vue'], resolve),
                            meta: { title: '个人中心-审批授权' }
                        },

                        {
                            path: '/wdpxxx',
                            component: resolve => require(['../components/page/myProcess/wdpxxx.vue'], resolve),
                            meta: { title: '个人中心-我的培训学习' }
                        },

                        // {
                        //     path: '/dragList',
                        //     component: resolve => require(['../components/page/DragList.vue'], resolve),
                        //     meta: { title: '拖拽列表' }
                        // },
                    ]
                }


            ]
        }
        // {
        //     path: '/404',
        //     component: resolve => require(['../components/page/404.vue'], resolve)
        // },
        // {
        //     path: '/403',
        //     component: resolve => require(['../components/page/403.vue'], resolve)
        // },
        // {
        //     path: '*',
        //     redirect: '/404'
        // }
    ]
})

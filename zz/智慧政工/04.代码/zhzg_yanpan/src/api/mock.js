import * as Mock from 'mockjs';
const prefix = '/api/';

// 初始化页面
Mock.mock(/\/api\/teamBuild\/getTeamInfo[\s\S]*?/, {
  data: {
    role: ['fj', 'bm1'],
    fj: [
      {
        id: '2014111018472522300000002936',
        teamName: '玄武分局',
        isZoom: true
      },
      {
        id: '2014111018472508300000001991',
        teamName: '秦淮分局',
        isZoom: true
      },
      {
        id: '2014111018472517600000002837',
        teamName: '建邺分局',
        isZoom: true
      },
      {
        id: '2014111018472505100000001665',
        teamName: '鼓楼分局',
        isZoom: true
      },
      {
        id: '2014111018472516100000002727',
        teamName: '栖霞分局',
        isZoom: true
      },
      {
        id: '2014111018472500400000001420',
        teamName: '雨花台分局',
        isZoom: true
      },
      {
        id: '2014111018472511400000002322',
        teamName: '浦口分局',
        isZoom: true
      },
      {
        id: '2014111018472512900000002390',
        teamName: '江北新区分局',
        isZoom: true
      },
      {
        id: '2014111018472516100000002593',
        teamName: '江宁分局',
        isZoom: true
      },
      {
        id: '2014111018472517600000002930',
        teamName: '六合分局',
        isZoom: true
      },
      {
        id: '2014111018472509800000002206',
        teamName: '溧水分局',
        isZoom: true
      },
      {
        id: '2014111018472505100000001734',
        teamName: '高淳分局',
        isZoom: true
      },
      {
        id: '2014111018472497300000001265',
        teamName: '经济技术开发区分局',
        isZoom: true
      }
    ],
    bm1: [
      {
        id: '2014111018472522300000002937',
        teamName: '政治部',
        isZoom: true
      },
      {
        id: '1506241826242730000000000272',
        teamName: '警务效能监察支队（作风办）',
        isZoom: true
      },
      {
        id: '2014111018472484800000000512',
        teamName: '纪委（监察室）',
        isZoom: true
      },
      {
        id: '2014111018472497300000001246',
        teamName: '办公室',
        isZoom: true
      },
      {
        id: '2014111018472486400000000648',
        teamName: '警务保障部',
        isZoom: true
      },
      {
        id: '2014111018472522300000002938',
        teamName: '审计处',
        isZoom: true
      },
      {
        id: '2014111018472522300000002940',
        teamName: '科技信息化处',
        isZoom: true
      },
      {
        id: '1808061651531350000000009569',
        teamName: '大数据中心',
        isZoom: true
      },
      {
        id: '2014111018472497300000001250',
        teamName: '信访处',
        isZoom: true
      },
      {
        id: '2014111018472495800000001145',
        teamName: '指挥中心',
        isZoom: true
      },
      {
        id: '2014111018472484800000000531',
        teamName: '警务督察支队',
        isZoom: true
      },
      {
        id: '2014111018472480100000000351',
        teamName: '国内安全保卫支队',
        isZoom: true
      },
      {
        id: '2014111018472477000000000192',
        teamName: '防范和处理邪教问题工作支队',
        isZoom: true
      },
      {
        id: '2014111018472481700000000359',
        teamName: '单位内部安全保卫支队',
        isZoom: true
      },
      {
        id: '2014111018472484800000000550',
        teamName: '治安警察支队',
        isZoom: true
      },
      {
        id: '2014111018472478600000000217',
        teamName: '刑事警察支队（刑事侦查处）',
        isZoom: true
      },
      {
        id: '2014111018472484800000000540',
        teamName: '监所管理支队',
        isZoom: true
      },
      {
        id: '2014111018472484800000000578',
        teamName: '技术侦察支队',
        isZoom: true
      },
      {
        id: '2014111018472484800000000511',
        teamName: '交通警察支队',
        isZoom: true
      },
      {
        id: '2014111018472484800000000526',
        teamName: '出入境管理支队',
        isZoom: true
      },
      {
        id: '2014111018472480100000000330',
        teamName: '网络安全保卫支队',
        isZoom: true
      },
      {
        id: '2014111018472492600000000966',
        teamName: '法制支队',
        isZoom: true
      },
      {
        id: '2014111018472491100000000862',
        teamName: '特警支队（巡警支队）',
        isZoom: true
      },
      {
        id: '2014111018472486400000000599',
        teamName: '人口管理支队',
        isZoom: true
      },
      {
        id: '2014111018472480100000000339',
        teamName: '经济犯罪侦查支队',
        isZoom: true
      },
      {
        id: '2014111018472489500000000793',
        teamName: '禁毒支队',
        isZoom: true
      },
      {
        id: '1603211549598110000000023053',
        teamName: '食品药品和环境犯罪侦查支队',
        isZoom: true
      },
      {
        id: '1908200929504940000000000466',
        teamName: '行政审批服务处',
        isZoom: true
      },
      {
        id: '2014111018472487900000000747',
        teamName: '特勤局',
        isZoom: true
      },
      {
        id: '2014111018472484800000000517',
        teamName: '反恐怖工作支队',
        isZoom: true
      },
      {
        id: '2014111018472516100000002607',
        teamName: '水上分局',
        isZoom: true
      },
      {
        id: '2014111018472497300000001287',
        teamName: '公共交通治安分局',
        isZoom: true
      },
      {
        id: '2014111018472497300000001308',
        teamName: '地铁公安分局',
        isZoom: true
      }
    ],
    map: '南京市公安局'
  },
  code: 0,
  message: '操作成功!'
});

// 下钻接口
Mock.mock(/\/api\/teamBuild\/getChildUnit[\s\S]*?/, {
  data: {
    pcs: [
      {
        id: '2014111018472500400000001424',
        teamName: '中央门立交桥派出所（待注销）',
        isZoom: true
      },
      {
        id: '2014111018472500400000001484',
        teamName: '挹江门派出所',
        isZoom: true
      },
      {
        id: '2014111018472503600000001568',
        teamName: '华侨路派出所',
        isZoom: true
      },
      {
        id: '2014111018472505100000001615',
        teamName: '中央门派出所',
        isZoom: true
      },
      {
        id: '2014111018472500400000001468',
        teamName: '汉中门派出所',
        isZoom: true
      },
      {
        id: '2014111018472503600000001587',
        teamName: '二板桥派出所',
        isZoom: true
      },
      {
        id: '2014111018472505100000001595',
        teamName: '中山桥派出所（待注销）',
        isZoom: true
      },
      {
        id: '2014111018472503600000001519',
        teamName: '宝塔桥派出所',
        isZoom: true
      },
      {
        id: '2014111018472503600000001537',
        teamName: '宁海路派出所',
        isZoom: true
      },
      {
        id: '2014111018472503600000001553',
        teamName: '四所村派出所',
        isZoom: true
      },
      {
        id: '2014111018472505100000001643',
        teamName: '江东派出所',
        isZoom: true
      },
      {
        id: '2014111018472505100000001627',
        teamName: '鼓楼派出所',
        isZoom: true
      },
      {
        id: '2014111018472500400000001438',
        teamName: '阅江楼派出所',
        isZoom: true
      },
      {
        id: '2014111018472500400000001450',
        teamName: '湖南路派出所',
        isZoom: true
      },
      {
        id: '2014111018472503600000001499',
        teamName: '幕府山派出所',
        isZoom: true
      },
      {
        id: '2014111018472505100000001662',
        teamName: '小市派出所',
        isZoom: true
      }
    ],
    role: ['pcs', 'bm2'],
    bm2: [
      {
        id: '2014111018472486400000000665',
        teamName: '鼓楼分局国内安全保卫大队',
        isZoom: true
      },
      {
        id: '2014111018472487900000000681',
        teamName: '鼓楼分局人口管理大队',
        isZoom: true
      },
      {
        id: '2014111018472505100000001663',
        teamName: '鼓楼分局纪检监督室',
        isZoom: true
      },
      {
        id: '2014111018472505100000001664',
        teamName: '鼓楼分局指挥室',
        isZoom: true
      },
      {
        id: '2014111018472486400000000659',
        teamName: '南京市公安消防支队鼓楼区下关消防大队',
        isZoom: true
      },
      {
        id: '2014111018472486400000000660',
        teamName: '鼓楼分局单位内部安全保卫大队',
        isZoom: true
      },
      {
        id: '2014111018472486400000000661',
        teamName: '鼓楼分局驻地税公安办公室',
        isZoom: true
      },
      {
        id: '2014111018472486400000000662',
        teamName: '鼓楼分局驻国税公安办公室',
        isZoom: true
      },
      {
        id: '2014111018472486400000000663',
        teamName: '鼓楼分局网络安全保卫大队',
        isZoom: true
      },
      {
        id: '2014111018472486400000000664',
        teamName: '鼓楼分局经济犯罪侦查大队',
        isZoom: true
      },
      {
        id: '2014111018472486400000000666',
        teamName: '鼓楼分局政治处',
        isZoom: true
      },
      {
        id: '2014111018472487900000000668',
        teamName: '鼓楼分局治安警察大队',
        isZoom: true
      },
      {
        id: '2014111018472487900000000680',
        teamName: '鼓楼分局刑事警察大队',
        isZoom: true
      },
      {
        id: '2014111018472487900000000684',
        teamName: '鼓楼分局警务保障室',
        isZoom: true
      },
      {
        id: '1506251740204810000000000853',
        teamName: '鼓楼分局食品药品和环境犯罪侦查大队',
        isZoom: true
      },
      {
        id: '1506261754192670000000000158',
        teamName: '鼓楼分局虹桥警务服务站',
        isZoom: true
      },
      {
        id: '2014111018472487900000000696',
        teamName: '鼓楼分局巡特警大队',
        isZoom: true
      },
      {
        id: '2014111018472487900000000700',
        teamName: '鼓楼分局法制大队',
        isZoom: true
      },
      {
        id: '2014111018472487900000000701',
        teamName: '鼓楼区维稳办',
        isZoom: true
      },
      {
        id: '1508181543006550000000001079',
        teamName: '鼓楼分局湖南路警务服务站',
        isZoom: true
      },
      {
        id: '1508181543241690000000001089',
        teamName: '鼓楼分局三牌楼警务服务站',
        isZoom: true
      },
      {
        id: '1509031548177450000000000862',
        teamName: '鼓楼分局龙江警务服务站',
        isZoom: true
      },
      {
        id: '1509031548372770000000000868',
        teamName: '鼓楼分局和燕路警务服务站',
        isZoom: true
      },
      {
        id: '1509031548576380000000000879',
        teamName: '鼓楼分局燕江路警务服务站',
        isZoom: true
      },
      {
        id: '1509031549173850000000000888',
        teamName: '鼓楼分局大桥南路警务服务站',
        isZoom: true
      },
      {
        id: '1509031549371250000000000897',
        teamName: '鼓楼分局热河南路警务服务站',
        isZoom: true
      },
      {
        id: '2014111018472473900000000115',
        teamName: '鼓楼分局反恐怖工作大队',
        isZoom: true
      },
      {
        id: '1506261750213980000000000132',
        teamName: '鼓楼分局清凉门警务服务站',
        isZoom: true
      },
      {
        id: '1506261755026340000000000175',
        teamName: '鼓楼分局草场门警务服务站',
        isZoom: true
      },
      {
        id: '1502111340231080000000000093',
        teamName: '鼓楼分局出入境管理大队',
        isZoom: true
      },
      {
        id: '2014111018472497300000001251',
        teamName: '鼓楼分局禁毒大队',
        isZoom: true
      },
      {
        id: '2014111018472486400000000657',
        teamName: '南京市公安消防支队鼓楼区鼓楼消防大队',
        isZoom: true
      },
      {
        id: '2014111018472505100000001616',
        teamName: '省政府治安民警办公室',
        isZoom: true
      },
      {
        id: '1609181729035830000000006617',
        teamName: '鼓楼分局预审大队',
        isZoom: true
      }
    ],
    map: '溧水分局'
  },
  code: 0,
  message: '操作成功!'
});

// 浮框接口
Mock.mock(`${prefix}teamBuild/getWindowsInfo`, {
  data: {
    name: '@cname(4,10)',
    jxkh: {
      value: '@float(80, 90, 2, 2)',
      colorLevel: '@integer(1, 5)'
    },
    zhmyd: {
      value: '@float(80, 90, 2, 2)',
      colorLevel: '@integer(1, 5)'
    },
    jjaj: {
      value: '@integer(10, 50)',
      colorLevel: '@integer(1, 5)'
    },
    jtry: {
      value: '@integer(20, 80)',
      colorLevel: '@integer(1, 5)'
    }
  }
});

// 两边数据
Mock.mock(`${prefix}teamBuild/getTeamDataInfo`, {
  data: {
    data1: [
      {
        name: '鼓楼分局',
        value: '1455'
      },
      {
        name: '鼓楼分局',
        value: 28.12
      },
      {
        name: '鼓楼分局',
        value: '41.34%'
      },
      {
        name: '鼓楼分局',
        value: '410%'
      },
      {
        name: '鼓楼分局',
        value: '99.13%'
      },
      {
        name: '鼓楼分局',
        value: '0.34%'
      }
    ],
    data2: {
      jtry: {
        left: '@integer(10, 30)',
        right: '@integer(10, 30)'
      },
      zhmyd: {
        left: '@integer(91, 99)',
        right: '@integer(91, 99)'
      },
      jjaj: {
        left: '@integer(10, 30)',
        right: '@integer(10, 30)'
      },
      jxkh: {
        left: '@float(91, 99, 2, 2)',
        right: '@float(91, 99, 2, 2)'
      }
    }
  }
});

// 获取部门颜色
Mock.mock(`${prefix}teamBuild/getDeptColor`, {
  data: [
    {
      deptId: '2014111018472522300000002936',
      deptName: '玄武分局',
      colorLevel: 4
    },
    {
      deptId: '2014111018472508300000001991',
      deptName: '秦淮分局',
      colorLevel: 5
    },
    {
      deptId: '2014111018472517600000002837',
      deptName: '建邺分局',
      colorLevel: 4
    },
    {
      deptId: '2014111018472505100000001665',
      deptName: '鼓楼分局',
      colorLevel: 4
    },
    {
      deptId: '2014111018472516100000002727',
      deptName: '栖霞分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472500400000001420',
      deptName: '雨花台分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472511400000002322',
      deptName: '浦口分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472512900000002390',
      deptName: '江北新区分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472516100000002593',
      deptName: '江宁分局',
      colorLevel: 4
    },
    {
      deptId: '2014111018472517600000002930',
      deptName: '六合分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472509800000002206',
      deptName: '溧水分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472505100000001734',
      deptName: '高淳分局',
      colorLevel: 3
    },
    {
      deptId: '2014111018472497300000001265',
      deptName: '经济技术开发区分局',
      colorLevel: 3
    }
  ]
});

// 排行榜
Mock.mock(`${prefix}teamBuild/getRankList`, {
  'data|20': [
    {
      name: '@cname()',
      count: '@integer(10, 400)',
      age: '@float(30, 40, 2, 2)',
      female: '@float(30, 40, 2, 2)',
      political: '@float(0, 10, 2, 2)',
      orgHonor: '@float(30, 40, 2, 2)',
      personHonor: '@float(30, 40, 2, 2)',
      satisfaction: '@float(80, 95, 2, 2)',
      illegal: '@float(0, 1, 2, 2)'
    }
  ]
});

// 人员详情
Mock.mock(`${prefix}teamBuild/getPersonDetail`, {
  data: {
    count: '@integer(0, 2000)',
    leaderCount: '@integer(0, 2000)',
    policeCount: '@integer(0, 2000)',
    partyCount: '@integer(0, 2000)'
  }
});

// 年龄结构
Mock.mock(`${prefix}teamBuild/getAgeDetail`, {
  data: {
    young: 427,
    middle: 574,
    old: 446,
    youngPercent: '29.51%',
    middlePercent: '39.67%',
    oldPercent: '30.82%'
  }
});

// 教育培训
Mock.mock(`${prefix}teamBuild/getTrainDetail`, {
  data: {
    'chart|6': [
      {
        name: '@date("yyyy")',
        value: '@integer(0, 200)'
      }
    ],
    content1:
      '自 <strong>2012</strong> 年共参与培训 <strong>139</strong> 人次，',
    content2:
      '其中 <strong>2016</strong> 年培训人次最高，为 <strong>265</strong> 人次，参训率达 <strong>99.23%</strong>'
  }
});

// 集体荣誉
Mock.mock(`${prefix}teamBuild/getOrgHonorDetail`, {
  data: {
    'chart|6': [
      {
        name: '@cname(5)',
        value: '@integer(0, 30)'
      }
    ],
    content1: '共获得 <strong>15</strong> 项集体荣誉，',
    content2:
      '其中 <strong>4</strong> 项 <strong>集体二等功</strong>，<strong>24</strong> 项 <strong>集体三等功</strong>，',
    content3: '较全局平均集体荣誉个数<strong>高17.28%</strong>。'
  }
});

// 个人荣誉
Mock.mock(`${prefix}teamBuild/getPersonHonorDetail`, {
  data: {
    'chart|6': [
      {
        name: '@cname(5)',
        value: '@integer(0, 30)'
      }
    ],
    content1: '共获得 <strong>15</strong> 项个人荣誉，',
    content2:
      '其中 <strong>4</strong> 人 <strong>一等功</strong>，<strong>4</strong> 人 <strong>二等功</strong>，<strong>24</strong> 人 <strong>三等功</strong>，',
    content3:
      '较全局平均个人荣誉 <strong>89</strong> 项 <strong>高17.28%</strong>。'
  }
});

// 政工绩效
Mock.mock(`${prefix}teamBuild/getKPI`, {
  data: {
    'chart|6': [
      {
        name: '@cname(5)',
        value: '@integer(0, 30)'
      }
    ],
    content1: '共获得 <strong>15</strong> 项个人荣誉，',
    content2:
      '其中 <strong>4</strong> 人 <strong>一等功</strong>，<strong>4</strong> 人 <strong>二等功</strong>，<strong>24</strong> 人 <strong>三等功</strong>，',
    content3:
      '较全局平均个人荣誉 <strong>89</strong> 项 <strong>高17.28%</strong>。'
  }
});

// 违纪案件
Mock.mock(`${prefix}teamBuild/getBreakRuleCase`, {
  data: {
    chart: {
      'personal|10': [
        {
          name: '@date("yyyy")年',
          value: '@integer(1, 30)'
        }
      ],
      'dept|10': [
        {
          name: '@date("yyyy")年',
          value: '@integer(1, 30)'
        }
      ]
    },
    content1:
      '自 <strong>2016</strong> 年共涉及 <strong>2</strong> 次 <strong>黄牌警告</strong>',
    content2: '<strong>1</strong> 件 投诉举报',
    content3:
      '<strong>2</strong> 人涉及<strong>纪检案件（违规参与公款宴请和消费,违规操办婚丧喜庆事宜）</strong>'
  }
});

// 报告导出
Mock.mock(`${prefix}report/getTeamReport`, {
  data: '@image("200x100", "#4A7BF7", "Hello")'
});

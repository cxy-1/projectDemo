import echarts from 'echarts';
import 'echarts-gl';

// 渲染echarts图
const renderChart = params => {
  const dom = document.getElementById(params.id);
  // 实例存在就先销毁，这样每次echarts都会重绘
  dom.getAttribute('_echarts_instance_') &&
    echarts.getInstanceByDom(dom).dispose();
  const chart = echarts.init(dom);
  chart.clear();
  if (params.mapData || params.data.length > 0) {
    if (typeof params.cb === 'function') {
      chart.off('click');
      chart.on('click', params.cb);
    }
    chart.setOption(params.option);
  } else {
    const div = document.createElement('div');
    Object.assign(div.style, {
      width: '100%',
      height: '100%',
      position: 'relative'
    });
    const img = document.createElement('div');
    img.className = 'no-data';
    div.appendChild(img);
    dom.innerHTML = '';
    dom.appendChild(div);
  }
  return chart;
};

// 3d地图
const map3D = params => {
  echarts.registerMap('map', params.mapData);
  let data = [];
  let colorArr = [
    '#efd8e5',
    '#e0c2d2',
    '#e6a8cc',
    '#d477b4',
    '#c04992',
    '#a92a6a',
    '#8a1745',
    '#531227',
    '#2e101b'
  ];
  const scatterData = params.mapData.features.map(val => {
    let center = Array.from(val.properties.center);
    center.push(100);
    const { name } = val.properties;
    let deptId;
    params.data.map(v => {
      if (v.name === name) {
        let { colorLevel, isZoom } = v;
        let color = colorArr[colorLevel - 1];
        deptId = v.deptId;
        data.push({ name, id: deptId, isZoom, itemStyle: { color } });
      }
    });
    return { name, id: deptId, value: center };
  });

  const center = params.isSj ? [0, -30, 0] : [0, -30, 0];

  params.option = {
    geo3D: {
      zlevel: -100,
      show: false,
      map: 'map',
      boxWidth: 90,
      boxHeight: 5,
      viewControl: {
        autoRotate: false,
        distance: 220,
        alpha: 90,
        beta: 30,
        panMouseButton: 'left',
        rotateMouseButton: 'middle',
        center,
        minBeta: 0,
        maxBeta: 360
      }
    },
    series: [
      {
        show: true,
        map: 'map',
        type: 'map3D',
        boxWidth: 90,
        boxHeight: 5,
        viewControl: {
          autoRotate: false,
          distance: 220,
          alpha: 90,
          beta: 30,
          panMouseButton: 'left',
          rotateMouseButton: 'middle',
          center,
          minBeta: 0,
          maxBeta: 360
        },
        light: {
          main: {
            shadow: true
          }
        },
        label: {
          show: true,
          textStyle: {
            backgroundColor: 'transparent',
            color: '#fff',
            fontSize: 16,
            fontFamily: '微软雅黑',
            // fontWeight: 'bold'
          }
        },
        postEffect: {
          enable: false
        },
        itemStyle: {
          borderColor: '#fff',
          borderWidth: 1
        },
        data
      },
      {
        type: 'scatter3D',
        coordinateSystem: 'geo3D',
        data: scatterData,
        symbol: 'circle',
        symbolSize: 12,
        itemStyle: {
          color: '#ffc821',
          borderColor: '#fff',
          borderWidth: 1
        },
        emphasis: {
          label: {
            show: false
          },
          itemStyle: {
            color: '#aeff21'
          }
        },
        label: {
          show: false
        }
      }
    ]
  };
  renderChart(params);
};

// 年龄的饼图
const agePie = params => {
  params.option = {
    color: ['#fad669', '#ff9898', '#a3c8fd'],
    tooltip: {
      show: true
    },
    legend: {
      type: 'scroll',
      orient: 'vertical',
      left: 10,
      top: 20
    },
    series: [
      {
        type: 'pie',
        radius: '80%',
        center: ['50%', '50%'],
        data: params.data,
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
          show: true,
          position: 'inside',
          formatter: function (param) {
            return param.value + '人' + '\n' + param.percent + '%';
          }
        }
      }
    ]
  };
  renderChart(params);
};

// y轴在右侧的线图
const lineWithYRight = params => {
  const xData = [];
  const yData = [];
  params.data.map(val => {
    xData.push(val.name);
    yData.push(val.value);
  });

  params.option = {
    // tooltip: {
    //   show: true
    // },
    color: ['#3398DB'],
    tooltip: {
      trigger: 'axis',
      axisPointer: {            // 坐标轴指示器，坐标轴触发有效
        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    grid: {
      containLabel: true,
      top: 20,
      left: 20,
      right: 20,
      bottom: 20
    },
    xAxis: {
      type: 'category',
      data: xData,
      axisTick: {
        alignWithLabel: true
      }
    },
    yAxis: {
      type: 'value',
      position: 'left'
    },
    series: [
      {
        data: yData,
        type: 'bar',
        name: '培训人次',
        barWidth: '60%',
        barMinHeight: 15,
        label: {
          show: true,
          position: 'insideTop'
        },
        // itemStyle: {
        //   borderWidth: 3,
        //   color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
        //     { offset: 0, color: '#00c0ff' },
        //     { offset: 0.48, color: '#9d7fff' },
        //     { offset: 1, color: '#9d7fff' },
        //     { offset: 1, color: '#738ff4' },
        //     { offset: 1, color: '#489ee8' },
        //     { offset: 1, color: '#6ec2db' },
        //     { offset: 1, color: '#93e5ce' }
        //   ])
        // }
      }
    ]
  };
  renderChart(params);
};

// 多颜色柱状图
const colorfulBar = params => {
  const colorArr = [
    '#fad669',
    '#ff9898',
    '#6a8fbc',
    '#fe7c43',
    '#60a5ff',
    '#5ad59e',
    '#5a33e9',
    '#3398DB',
    '#8ec7ca',
    '#9dcefa'
  ];
  const xData = [];
  const yData = [];
  const transformResult = [];
  if (params.transform) {
    for (var key in params.data) {
      transformResult.push({ name: key, value: params.data[key] });
    }
    params.data = transformResult;
  }
  params.data.map((val, index) => {
    xData.push(val.name);
    yData.push({
      ...val,
      itemStyle: {
        color: colorArr[index]
      }
    });
  });
  var sum = 0;
  if (params.percent) {
    params.data.map((val, index) => {
      sum += Number(val.value);
    })
  }
  params.option = {
    color: ['#3398DB'],
    tooltip: {
      show: true
    },
    grid: {
      left: 10,
      right: 10,
      bottom: 10,
      top: 40,
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: xData,
        axisLabel: {
          show: false,
        },
        axisLine: {
          lineStyle: {
            width: 2,
            color: '#576ea9'
          }
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        axisLabel: {
          show: true
        },
        axisLine: {
          show: true
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: true
        }
      }
    ],
    series: [
      {
        type: 'bar',
        barWidth: 47,
        barMinHeight: 10,
        itemStyle: {
          barBorderRadius: [8, 8, 0, 0]
        },
        label: {
          show: true,
          position: 'top',
          formatter(param) {
            let name = '';
            for (let i = 0, len = param.data.name.length; i < len; i++) {
              name +=
                i % 4 === 3
                  ? params.newLine
                    ? param.data.name[i] + '\n'
                    : param.data.name[i]
                  : param.data.name[i];
            }
            if (sum > 0) {
              return name +':'+ param.data.value +'人'+'\n' + (Number(param.data.value) / sum * 100).toFixed(2) + "%";
            } else {
              return name + '\n' + param.data.value;
            }
          }
        },
        data: yData
      }
    ]
  };
  return renderChart(params);
};

// 线图和环图混合图
const lineAndAnnual = params => {
  const data = [];
  const xData = [''];
  const className = params.id === 'chart6' ? 'left-sec' : 'right-sec';
  let text, color1, color2;
  if (params.type === 0) {
    text = '单位涉及';
    color1 = '#609eef';
    color2 = '#7bddb1';
  } else {
    text = '个人涉及';
    color1 = '#ff3f4f';
    color2 = '#f7cf55';
  }
  params.data.map(val => {
    xData.push(val.name);
    data.push(Number(val.value));
  });
  let gap = 0;
  let total = 0;
  let maxIndex;
  let dataArray = (function () {
    let max = Math.max.apply(Math, data);
    let min = Math.min.apply(Math, data);
    gap = Math.round(max - min);
    let nd = [{ value: data[0] + gap, symbol: 'none' }];
    for (let i = 0, l = data.length; i < l; i++) {
      if (data[i] == max) {
        maxIndex = i;
      }
      total += data[i] + gap;
      nd.push(data[i] + gap);
    }
    nd.push({ value: data[data.length - 1] + gap, symbol: 'none' });
    return nd;
  })();
  const option = {
    backgroundColor: '#fff',
    tooltip: {
      formatter: function (param) {
        if (param.seriesName === '数据') {
          update(param);
        }
      }
    },
    title: {
      text,
      x: 'center'
    },
    grid: {
      show: true,
      top: 33,
      left: 58,
      right: 58,
      bottom: 10,
      backgroundColor: '#ccc',
      borderWidth: 0
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: false,
        show: false,
        data: xData
      }
    ],
    yAxis: [
      {
        type: 'value',
        boundaryGap: [0, 0.5],
        show: false
      }
    ],
    animation: false,
    series: [
      {
        name: '数据',
        type: 'line',
        symbol: 'emptyCircle',
        symbolSize: 6,
        showAllSymbol: true,
        smooth: true,
        itemStyle: {
          color: color1
        },
        areaStyle: {
          color: color1
        },
        data: dataArray
      },
      {
        name: '遮罩',
        type: 'pie',
        clickable: false,
        radius: [70, 130],
        silent: true,
        itemStyle: {
          color: '#fff'
        },
        label: { show: false },
        labelLine: { show: false },
        data: [{ value: 100, name: '直接访问' }]
      },
      {
        name: '占比',
        type: 'pie',
        clickable: false,
        clockWise: true,
        radius: [80, 95],
        silent: true,
        data: [
          {
            itemStyle: {
              color: color2
            },
            label: {
              show: false,
              position: 'inside',
              formatter: '\n{b} : {c}\n\n( {d}% )',
              textStyle: {
                fontSize: 15,
                baseline: 'top',
                color: '#1e90ff'
              }
            },
            labelLine: { show: false }
          },
          {
            name: '其他',
            tooltip: { show: false },
            itemStyle: {
              color: '#fff'
            },
            label: { show: false },
            labelLine: { show: false }
          }
        ]
      }
    ]
  };
  function changePieSeries(param) {
    let curData = param.value;
    option.series[2].startAngle = -90 + ((curData / total) * 360) / 2;
    option.series[2].data[0].name = param.name;
    option.series[2].data[0].value = curData;
    option.series[2].data[1].value = total - curData;

    for (let i = 1, l = option.series[0].data.length - 1; i < l; i++) {
      if (option.series[0].data[i].symbol) {
        option.series[0].data[i].symbol = 'emptyCircle';
        option.series[0].data[i].symbolSize = 6;
      }
    }
    option.series[0].data[param.dataIndex] = {
      name: param.name,
      value: param.value,
      symbol: 'emptyDiamond',
      symbolSize: 10
    };

    const bottomStr = `${param.name}：${param.value - gap}`;
    document.querySelector(`.${className}`).innerHTML = bottomStr;
  }
  function update(param) {
    const chart = echarts.getInstanceByDom(document.getElementById(params.id));
    changePieSeries(param);
    option.animation = true;
    chart.setOption(option);
    const bottomStr = `${param.name}：${
      typeof param.data === 'object' ? param.data.value - gap : param.data - gap
      }`;
    document.querySelector(`.${className}`).innerHTML = bottomStr;
  }
  changePieSeries({
    name: option.xAxis[0].data[maxIndex + 1],
    value: option.series[0].data[maxIndex + 1],
    dataIndex: maxIndex + 1
  });
  params.option = option;
  renderChart(params);
  if (!data || data.length === 0) {
    document.querySelector(`.${className}`).innerHTML = '';
  }
};

// 多颜色多个柱状图
const colorfulBares = params => {
  let optionse = {
    tooltip: {
      show: true,
      trigger: "axis"
    },
    grid: {
      left: 10,
      right: 50,
      bottom: 10,
      top: 60,
      containLabel: true
    },
    legend: {
      data: params.data.data
    },
    calculable: true,
    xAxis: [
      {
        type: "category",
        data: params.data.xAxisdata,
        splitLine: { show: false },
      }
    ],
    yAxis: [
      {
        type: "value",
        minInterval: 1,
        splitLine: { show: false },
        boundaryGap: [0, 0.3]
      }
    ],
    series: params.data.seriesdata
  };
  const dom = document.getElementById(params.id);
  const chart = echarts.init(dom);
  // 实例存在就先销毁，这样每次echarts都会重绘
  // dom.getAttribute('_echarts_instance_') &&
  //   echarts.getInstanceByDom(dom).dispose();
  if (params.data.seriesdata.length > 0) {
    chart.setOption(optionse);
  }
};
//// 年龄的饼图se
const agePiese = params => {
  params.option = {
    color: ["#2ec6c8", "#b7a3de", "#5ab1ef"],
    tooltip: {
      trigger: "item",
      formatter: "{b} : {c} ({d}%)"
    },
    legend: {
      // type: 'scroll',
      // left: 10,
      // top: 20
    },
    series: [
      {
        name: "",
        type: "pie",
        radius: "60%",
        center: ["50%", "50%"],
        selectedMode: "single",
        minAngle: 10,
        label: {
          // position: "inner",
          formatter: "{b}：{c}人 \n {d}% "
        },
        // labelLine: {
        //   show: false
        // },
        data: params.data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)"
          }
        }
      }
    ]
  };
  renderChart(params);
};
//环装图
const agerrujjn = params => {
  let optionse = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}人 ({d}%)'
    },
    legend: {
      left: 'center',
      top: 'top',
      // type: 'scroll',
      // orient: 'vertical',
      // left: 10,
      // top: 20,
      data: params.data.data
    },
    series: [
      {
        name: '',
        type: 'pie',
        radius: [20, 60],
        center: ['50%', '70%'],
        // roseType: 'area',
        label: {
          formatter: '{b}:{c}人 ({d}%)',
        },
        data: params.data.seriesdata
      }
    ]
  };
  const dom = document.getElementById(params.id);
  const chart = echarts.init(dom);
  // 实例存在就先销毁，这样每次echarts都会重绘
  // dom.getAttribute('_echarts_instance_') &&
  //   echarts.getInstanceByDom(dom).dispose();
  if (params.data.seriesdata.length > 0) {
    chart.setOption(optionse);
  }
};
//折线图
const lineround = params => {
  let optionse = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: params.data.data
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: params.data.xAxisdata
    },
    yAxis: {
      type: 'value',
      // axisLabel: {
      //   formatter: '{value} °C'
      // }
    },
    series: [
      {
        name: params.data.data[0],
        type: 'line',
        smooth: true,
        data: params.data.seriesdata,
        color:params.data.color,
          label: {
              normal: {
                  show: true,
                  position: 'top'
              }
          },
        markPoint: {
          data: [
            { type: 'max', name: '最大值' },
            { type: 'min', name: '最小值' }
          ]
        },
        markLine: {
          data: [
            { type: 'average', name: '平均值' }
          ]
        }
      }
    ]
  };
  const dom = document.getElementById(params.id);
  const chart = echarts.init(dom);
  // 实例存在就先销毁，这样每次echarts都会重绘
  // dom.getAttribute('_echarts_instance_') &&
  //   echarts.getInstanceByDom(dom).dispose();
  if (params.data.seriesdata.length > 0) {
    chart.setOption(optionse);
  }
};
//x轴竖起在左侧
const barleft = params => {
  let optionse = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: params.data.data
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01]
    },
    yAxis: {
      type: 'category',
      data: params.data.yAxisdata
    },
    series: [
      {
        name: '2011年',
        type: 'bar',
        data: params.data.seriesdata,
        color:params.data.color
      },
    ]
  };
  const dom = document.getElementById(params.id);
  const chart = echarts.init(dom);
  // 实例存在就先销毁，这样每次echarts都会重绘
  // dom.getAttribute('_echarts_instance_') &&
  //   echarts.getInstanceByDom(dom).dispose();
  if (params.data.seriesdata.length > 0) {
    chart.setOption(optionse);
  }
};
export {
  renderChart,
  map3D,
  agePie,
  agePiese,
  agerrujjn,
  lineWithYRight,
  colorfulBar,
  lineAndAnnual,
  colorfulBares,
  lineround,
  barleft
};

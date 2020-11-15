<template>
<!--    <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">-->
<!--        <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>-->
<!--        <el-option :value="valueTitle" :label="valueTitle">-->
<!--            <div class="tree-list">-->
<!--                <el-tree id="tree-option"-->
<!--                         ref="selectTree"-->
<!--                         :expand-on-click-node="false"-->
<!--                         :data="data"-->
<!--                         :props="defaultProps"-->
<!--                         :check-strictly="flag"-->
<!--                         node-key="id"-->
<!--                         :highlight-current="true"-->
<!--                         @node-click="handleNodeClick"-->
<!--                         check-on-click-node-->
<!--                         default-expand-all-->
<!--                         :filter-node-method="filterNode">-->
<!--                     <span class="custom-tree-node" slot-scope="{ node, data }">-->
<!--        <span>{{ node.label }}</span>-->
<!--        <span v-if="data.children">-->
<!--          <el-button-->
<!--              type="text"-->
<!--              size="mini"-->
<!--              @click="() => append(data)">-->
<!--            包含-->
<!--          </el-button>-->
<!--        </span>-->
<!--      </span>-->
<!--                </el-tree>-->
<!--            </el-option>-->
<!--        </el-select>-->
<!--        <el-select v-model="value" placeholder="请选择">-->
<!--            <el-option-->
<!--                v-for="item in optionss"-->
<!--                :key="item.value"-->
<!--                :label="item.label"-->
<!--                :value="item.value">-->
<!--            </el-option>-->
<!--        </el-select>-->
<!--        <el-steps :active="1">-->
<!--            <el-step title="步骤 1" description="这是一段很长很长很长的描述性文字">-->
<!--                <template slot-scope="data">-->
<!--                    {{data.title}}-->
<!--                </template>-->
<!--            </el-step>-->
<!--            <el-step title="步骤 2" description="这是一段很长很长很长的描述性文字"></el-step>-->
<!--            <el-step title="步骤 3" description="这段就没那么长了"></el-step>-->
<!--        </el-steps>-->
<!--    </div>-->

</template>
<script>
    export default {
        name: "el-tree-select",
        props:{
            /* 配置项 */
            // props:{
            //     type: Object,
            //     default:()=>{
            //         return {
            //             value:'id',             // ID字段名
            //             label: 'title',         // 显示名称
            //             children: 'children'    // 子级字段名
            //         }
            //     }
            // },
            /* 选项列表数据(树形结构的对象数组) */
            // options:{
            //     type: Array,
            //     default: ()=>{ return [] }
            // },
            /* 初始值 */
            value:{
                type: Number,
                default: ()=>{ return null }
            },
            /* 可清空选项 */
            clearable:{
                type:Boolean,
                default:()=>{ return true }
            },
            /* 自动收起 */
            accordion:{
                type:Boolean,
                default:()=>{ return false }
            },
        },
        data() {
            return {
                valueId:this.value,    // 初始值
                valueTitle:'',
                defaultExpandedKey:[],
                options:[{
                    label: '一级 1',
                    children: [{
                        label: '二级 1-1',
                        children: [{
                            label: '三级 1-1-1'
                        }]
                    }]
                }, {
                    label: '一级 2',
                    children: [{
                        label: '二级 2-1',
                        children: [{
                            label: '三级 2-1-1'
                        }]
                    }, {
                        label: '二级 2-2',
                        children: [{
                            label: '三级 2-2-1'
                        }]
                    }]
                }, {
                    label: '一级 3',
                    children: [{
                        label: '二级 3-1',
                        children: [{
                            label: '三级 3-1-1'
                        }]
                    }, {
                        label: '二级 3-2',
                        children: [{
                            label: '三级 3-2-1'
                        }]
                    }]
                }],
                props:{
                    children: 'children',
                    label: 'label'
                },
                optionss: [{
                    value: '选项1',
                    label: '黄金糕'
                }, {
                    value: '选项2',
                    label: '双皮奶'
                }, {
                    value: '选项3',
                    label: '蚵仔煎'
                }, {
                    value: '选项4',
                    label: '龙须面'
                }, {
                    value: '选项5',
                    label: '北京烤鸭'
                }],
                value: ''
            }
        },
        mounted(){
            this.initHandle()
        },
        methods: {
            // 初始化值
            initHandle(){
                if(this.valueId){
                    this.valueTitle = this.$refs.selectTree.getNode(this.valueId).data[this.props.label]     // 初始化显示
                    this.$refs.selectTree.setCurrentKey(this.valueId)       // 设置默认选中
                    this.defaultExpandedKey = [this.valueId]      // 设置默认展开
                }
                this.$nextTick(()=>{
                    let scrollWrap = document.querySelectorAll('.el-scrollbar .el-select-dropdown__wrap')[0]
                    let scrollBar = document.querySelectorAll('.el-scrollbar .el-scrollbar__bar')
                    scrollWrap.style.cssText = 'margin: 0px; max-height: none; overflow: hidden;'
                    scrollBar.forEach(ele => ele.style.width = 0)
                })

            },
            // 切换选项
            handleNodeClick(node){
                this.valueTitle = node[this.props.label]
                this.valueId = node[this.props.value]
                this.$emit('getValue',this.valueId)
                this.defaultExpandedKey = []
            },
            // 清除选中
            clearHandle(){
                this.valueTitle = ''
                this.valueId = null
                this.defaultExpandedKey = []
                this.clearSelected()
                this.$emit('getValue',null)
            },
            /* 清空选中样式 */
            clearSelected(){
                let allNode = document.querySelectorAll('#tree-option .el-tree-node')
                allNode.forEach((element)=>element.classList.remove('is-current'))
            }
        },
        watch: {
            value(){
                this.valueId = this.value
                this.initHandle()
            }
        },
    };
</script>
<style scoped>
    .el-scrollbar .el-scrollbar__view .el-select-dropdown__item{
        height: auto;
         /*max-height: 274px;*/
        overflow:auto;
        padding: 0;
    }
    .el-select-dropdown__item.selected{
        font-weight: normal;
    }
    ul li >>>.el-tree .el-tree-node__content{
        height:auto;
        padding: 0 20px;
    }
    .el-tree-node__label{
        font-weight: normal;
    }
    .el-tree >>>.is-current .el-tree-node__label{
        color: #409EFF;
        font-weight: 700;
    }
    .el-tree >>>.is-current .el-tree-node__children .el-tree-node__label{
        color:#606266;
        font-weight: normal;
    }
</style>
<style>
    .el-input.is-disabled .el-input__inner {
        background-color: white;
        border-color: #DCDFE6;
        color: #606266;
        cursor: not-allowed;
    }
    .upClass .el-upload-dragger{
        width:auto;
        height:auto;
        border:none;
    }
    .leaveManage .el-form-item__content{
        width:calc(100% - 145px)!important;
    }
    .el-table-add-row {
        cursor: pointer;
        width: 100%;
        text-align: center;
        height: 32px;
        line-height: 32px;
        font-family: PingFangSC-Regular;
        font-size: 14px;
        border: 1px dashed #D9D9D9;
        color: #409eff;
        margin-top: 10px;
        border-color: #b3d8ff
    }
    .table .crumbs {
        background: white;
        margin-bottom: 0;
    }
    .table .titIcon{
        margin-top:10px
    }
    .leave {
        margin:16px 20px;
        background: white;
    }
    .leaveList .leave .el-row {
        margin-bottom: 0px!important;
    }
    .leave .el-rowL:first-child {
        margin-bottom: 0;
    }
    .infoTab .el-table--border {
        border: 0
    }
    .applyNum .grid-content {
        padding:20px;
        border-bottom: 1px solid #f0f0f0;
        margin-bottom: 15px;
        font-weight: bold;
    }
    .fuzzinfoCon {
        padding:20px 20px 10px;
    }
    .fuzzinfoCon input {
        width: 208px!important;
    }
    .fuzzinfoCon select {
        width: 208px!important;
    }
    .el-upload {
        width: initial;
        height: initial;
        border: 0
    }
    .el-upload-list {
        float: left;
    }
    .el-upload {
        float: left
    }
    .el-upload-list__item:first-child {
        margin-top: 5px
    }
    .infoTab .el-table__empty-block {
        width: 100%!important
    }
    .el-upload-list__item-name {
        color: rgb(32, 160, 255)
    }
    .content .apply {
        background-position: 10px!important;
        height: 30px;
        line-height: 30px;
        padding-left: 45px;
    }
    .applyNum {
        padding: 10px 15px;
        background: white;
    }
    .applyNum .grid-content {
        margin-bottom: 0;
        border: 0;
    }
    .applyNum .grid-content:nth-of-type(2) {
        padding: 0;
    }
    .apply, .gridright {
        padding: 0;
        /*overflow: hidden;*/
        overflow-y: scroll;
    }


    .reasonLeaveBox .reasonLeaveCon{
        width:100%
    }
    .reasonLeaveBox .reasonLeaveCon input{
        width: 80%!important;
    }
    
    .sqxx-table .el-tag--info {
        background-color: transparent;
        border: none;
    }

    .el-select-dropdown__item.selected {
        font-weight: normal;
    }

    .el-select-dropdown__item.hover, .el-select-dropdown__item:hover {
        background: #ffffff;
    }
    .el-select-dropdown__list {
        list-style: none;
        /* padding: 6px 0; */
        margin: 0;
        -webkit-box-sizing: content-box;
         box-sizing: content-box;
    }
</style>

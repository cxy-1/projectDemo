<template>
    <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
        <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
        <el-option :value="valueTitle" :label="valueTitle">
            <div class="tree-list">
                <el-tree id="tree-option"
                     ref="selectTree"
                     :expand-on-click-node="false"
                     :data="selectdata"
                     :props="defaultProps"
                     :check-strictly="flag"
                     node-key="id"
                     :highlight-current="true"
                     @node-click="handleNodeClick"
                     check-on-click-node
                     default-expand-all
                     :filter-node-method="filterNode"
                >
                 <span class="custom-tree-node" slot-scope="{ node, data }">
                     <span>{{ node.label }}</span>
                     <span v-if="data.childCompany.length!=0">
                             <el-button
                              type="text"
                              size="mini"
                              @click.stop="() => append(data)">
                            包含
                        </el-button>
                    </span>
                 </span>
                </el-tree>
            </div>
        </el-option>
    </el-select>

</template>
<script>
    export default {
        data() {
            return {
                aa:null,
                flag:true,
                valueTitle:null,
                data: [{
                    id: 1,
                    label: '一级 1',
                    children: [{
                        id: 4,
                        label: '二级 1-1',
                        children: [{
                            id: 9,
                            label: '三级 1-1-1'
                        }, {
                            id: 10,
                            label: '三级 1-1-2'
                        }]
                    }]
                }, {
                    id: 2,
                    label: '一级 2',
                    children: [{
                        id: 5,
                        label: '二级 2-1'
                    }, {
                        id: 6,
                        label: '二级 2-2'
                    }]
                }, {
                    id: 3,
                    label: '一级 3',
                    children: [{
                        id: 7,
                        label: '二级 3-1'
                    }, {
                        id: 8,
                        label: '二级 3-2'
                    }]
                }],
                defaultProps: {
                    children: 'childCompany',
                    label: 'unitName'
                },
                unitId2:null,
            };
        },
        props:{
            selectdata: {
                type: Array,
            }
        },
        mounted(){
//      this.initHandle()
        },
        watch: {
            aa(val) {
                this.$refs.selectTree.filter(val);
            }
        },
        methods: {
            sendData (v) {
                this.$emit('listenToChild',v);
            },
            append(data) {
                console.log(data)
                // this.flag=false
                this.valueTitle = data.unitName+"(包含子部门)"
                this.unitId2=data.dataId
            },
            filterNode(value, data,node) {
                if(!value){
                    return true;
                }
                let level = node.level;
                let _array = [];//这里使用数组存储 只是为了存储值。
                this.getReturnNode(node,_array,value);
                let result = false;
                _array.forEach((item)=>{
                    result = result || item;
                });
                return result;
            },
            getReturnNode(node,_array,value){
                let isPass = node.data &&  node.data.label && node.data.label.indexOf(value) !== -1;
                isPass?_array.push(isPass):'';
                this.index++;
                if(!isPass && node.level!=1 && node.parent){
                    this.getReturnNode(node.parent,_array,value);
                }
            },
            handleNodeClick(node){
                this.valueTitle = node.unitName
                this.unitId2=node.unitId
                this.sendData(this.unitId2);
            },
            // 清除选中
            clearHandle(){
                this.valueTitle = ''
                // this.valueId = null
                // this.defaultExpandedKey = []
                // this.clearSelected()
            },
            /* 清空选中样式 */
            // clearSelected(){
            //     let allNode = document.querySelectorAll('#tree-option .el-tree-node')
            //     allNode.forEach((element) => element.classList.remove('is-current'))
            // }
        },
        // watch: {
        //     value: {
        //         immediate: true,
        //         handler(){
        //             this.valueId = this.value
        //         }
        //     },
        //     data: {
        //         immediate: true,
        //         handler(){
        //             this.initHandle()
        //         }
        //     },
        // },
    };
</script>
<style  scoped>
    .el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
        /*height: auto;*/
        min-height: 200px;
        max-height: 274px;
    }
    .wrapper .el-table th{
        text-align: center!important;
    }
    .el-form-item {
        margin-bottom: 12px
    }
    .el-tabs__nav-wrap,
    .el-tabs {
        width: 100%!important;
        margin-left: 0px!important;
    }
    .right-btn {
        float: right;
    }
    .handle-select {
        width: 200px;
    }
    .pagination {
        margin-bottom: 10px
    }
    .pagination2 {
        height: 32px;
        margin-bottom: 10px
    }
    .pagination2 .el-pagination {
        position: absolute;
        right: 30px;
        bottom: 10px;
    }
    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .el-footer {
        height: 20px!important;
        font-size: 12px;
        color: #333;
        line-height: 20px
    }
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }
    /*表格内容居中*/
    .el-table td, .el-table th{
        text-align: center;
    }
    .txryTable .el-table--border th,.txryTable .el-table__fixed-right-patch{
        text-align:center;
    }
    .LeaveManage .el-table__row td{
        text-align: center
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

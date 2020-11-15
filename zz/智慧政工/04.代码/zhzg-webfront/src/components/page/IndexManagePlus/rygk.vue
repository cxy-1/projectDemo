<template>
    <div>
        <el-form>
          <div class="customSelect xzdw"> 
            <el-form-item label="选择单位" :label-width="formLabelWidth">
                <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择" multiple collapse-tags>
                    <el-option :value="UnitValue">
                            <el-tree
                            style="height: 200px"
                            :data="unitOptionsRYGK"
                            show-checkbox
                            node-key="unitId"
                            ref="RYGKtree"
                            check-on-click-node
                            @check="handleNodeCheckRYGK"
                            :props="dataProps">
                            </el-tree>
                    </el-option>
                </el-select>
            </el-form-item> 
          </div>
          <div class="customSelect xznd" v-if="activeTab =='zjAttLists'">
            <el-form-item label="职级序列" :label-width="formLabelWidth">
              <el-select v-model="UnitValuezj" @change="handleNodeCheckRYGKzj">
                    <el-option
                      v-for="item in zjAttListsoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
              </el-select>
            </el-form-item> 
          </div>                      
        </el-form>
        <el-tabs class="customTabs" v-model="activeTab" @tab-click="handleClick">
            <el-tab-pane label="来源" name="lyAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto;">
                        <li v-for="(item,index) in tabList.lyAttList" style="text-indent: 10px; text-align: -webkit-center !important;" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataLy.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataLy.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>                
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataLy.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="年龄" name="nlAttList">
                <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.nlAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataNl.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataNl.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataNl.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="民族" name="mzAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.mzAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataMz.data"  :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataMz.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataMz.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="职务" name="zwAttLists">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.zwAttLists" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table  :data="tableDataZw.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataZw.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>  
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>                  
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataZw.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="职级" name="zjAttLists">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in zjAttLists" :key="index" :class="activeClasses == index?'active':''" @click="btnGetItems(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rightsee':'rightse'">
                  <div>
                    <ul class="listStyles" style="overflow:auto;float: clear;">
                        <li style="float: left;" v-for="(items,indexe) in zjAttListse" :key="indexe" :class="activeClasse == indexe?'actives':''" @click="btnGetItemss(indexe)">{{ items.name }}</li>
                    </ul>
                  </div>
                    <el-table  :data="tableDataZj.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <!-- <el-table-column prop="jobGrade" label="职级" min-width="160"></el-table-column> -->
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>  
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>                  
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataZj.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="警衔" name="jxAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.jxAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataJx.data"  :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataJx.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>     
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>             
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataJx.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="性别" name="xbAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.xbAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataXb.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataXb.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataXb.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="政治面貌" name="zzmmAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.zzmmAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataZzmm.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataZzmm.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>                   
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataZzmm.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="学历学位" name="xlxwAttList">
                <div  :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.xlxwAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div  :class="this.ifFullse ==false?'rights':'right'">
                    <el-table  :data="tableDataXlxw.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <!-- <el-table-column  v-for="col in tableDataXlxw.column" :prop="col.prop" :label="col.label" >
                        </el-table-column> -->
                        <el-table-column prop="deptName" width="140" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="姓名"></el-table-column>
                        <el-table-column prop="sex" label="性别" ></el-table-column>
                        <el-table-column prop="age" label="年龄"></el-table-column>
                        <el-table-column prop="nation" label="民族"></el-table-column>
                        <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                        <el-table-column prop="duty" label="职务" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                             <span v-else>{{props.row.duty}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="jobGrade" label="职级" min-width="120">
                          <template slot-scope="props">
                             <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                             <span v-else>{{props.row.jobGrade}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="policeRank" label="警衔"></el-table-column>
                        <el-table-column prop="education" label="学历学位" min-width="140">
                          <template slot-scope="props">
                             <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.school">{{props.row.education.slice(0,6)}}...</span>
                             <span v-else>{{props.row.education}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="school" label="毕业院校" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                             <span v-else>{{props.row.school}}</span>
                           </template>
                        </el-table-column> 
                        <el-table-column prop="sourceEntry" label="来源" min-width="160">
                          <template slot-scope="props">
                             <span v-if="props.row.sourceEntry&&props.row.sourceEntry.length>8" :title= "props.row.sourceEntry">{{props.row.sourceEntry.slice(0,8)}}...</span>
                             <span v-else>{{props.row.sourceEntry}}</span>
                           </template>
                        </el-table-column>                
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataXlxw.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>
        <div style="float: right;position: absolute;top: 90px;right: 21px;">
          <el-button type="primary"  @click="leadingout">导出</el-button>
        </div>
        <table  id="tipMessageLabel" v-show="false">
            <tr>
                <th>单位</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>民族</th>
                <th>政治面貌</th>
                <th>职务</th>
                <th>职级</th>
                <th>警衔</th>
                <th>学历学位</th>
                <th>毕业院校</th>
                <th>来源</th>
            </tr>
            <tr v-for="item in multipleSelection">
                <td>{{item.deptName}}</td>
                <td>{{item.userName}}</td>
                <td>{{item.sex}}</td>
                <td>{{item.age}}</td>
                <td>{{item.nation}}</td>
                <td>{{item.party}}</td>
                <td>{{item.duty}}</td>
                <td>{{item.jobGrade}}</td>
                <td>{{item.policeRank}}</td>
                <td>{{item.education}}</td>
                <td>{{item.school}}</td>
                <td>{{item.sourceEntry}}</td>
            </tr>
        </table>           
    </div>
</template>
<script>
import api from "./api";
// import export_json_to_excel from "../../vendor/Export2Excel"; //引入文件
export default {
  props: ["poster", "names", "unitOptionsRYGKs", "ifFullScreen"],
  data: function() {
    return {
      poster: "",
      names: "",
      UnitValuezj: "全部",
      multipleSelection: [],
      unitOptionsRYGKs: [],
      UnitValue: [],
      unitOptionsRYGK: [],
      dataProps: {
        children: "childCompany",
        label: "unitName"
      },
      value: "",
      formLabelWidth: "120px",
      // tab
      activeTab: "lyAttList",
      // ul
      tabList: {
        nlAttList: [
          { name: "51岁及以上" },
          { name: "41-50(岁)" },
          { name: "31-40(岁)" },
          { name: "30岁及以下" }
        ],
        xbAttList: [{ name: "男" }, { name: "女" }],
        xbAttListss: [{ name: "男警员" }, { name: "女警员" }],
        mzAttList: [{ name: "汉族" }, { name: "少数民族" }],
        lyAttList: [
          { name: "警察院校毕业生" },
          { name: "转业及复员军人" },
          { name: "社会招考" },
          { name: "实施公务员法机关调入" },
          { name: "其他" }
        ],
        zwAttLists: [
          { name: "正厅职" },
          { name: "副厅职" },
          { name: "副局职" },
          { name: "副局职非领导" },
          { name: "正处职" },
          { name: "副处职" },
          { name: "正科职" },
          { name: "副科职" }
        ],
        zwAttList: [
          { name: "正厅领导职" },
          { name: "副厅领导职" },
          { name: "副局领导职" },
          { name: "副局非领导职" },
          { name: "正处职" },
          { name: "副处职" },
          { name: "正科职" },
          { name: "副科职" }
        ],
        jxAttList: [
          { name: "一级警监" },
          { name: "二级警监" },
          { name: "三级警监" },
          { name: "一级警督" },
          { name: "二级警督" },
          { name: "三级警督" },
          { name: "一级警司" },
          { name: "二级警司" },
          { name: "三级警司" },
          { name: "一级警员" }
        ],
        zzmmAttList: [
          { name: "中共党员" },
          { name: "预备党员" },
          { name: "共青团员" },
          // { name: "其他党派" },
          { name: "群众" }
        ],
        xlxwAttList: [
          { name: "博士" },
          { name: "硕士" },
          { name: "本科" },
          { name: "大专" },
          { name: "高中及以下" }
        ]
      },
      zjAttLists: [
        { name: "厅级" },
        { name: "处级" },
        { name: "科级" },
        { name: "科员" }
      ],
      zjAttListsoptions: [
        {
          value: "全部",
          label: "全部"
        },
        {
          value: "综合管理序列",
          label: "综合管理序列"
        },
        {
          value: "执法勤务序列",
          label: "执法勤务序列"
        },
        {
          value: "警务技术序列",
          label: "警务技术序列"
        }
      ],
      zjAttListse: [],
      activeClass: 0,
      activeClasse: 0,
      activeClasses: 0,
      listQuery: {
        departIds: [],
        type: "警察院校毕业生",
        // pagination
        pageNo: 1,
        pageSize: 20,
        defaultPage: 1
      },
      // table
      tableDataNl: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataXb: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataMz: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataLy: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataZw: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataZj: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataJx: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataXlxw: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataZzmm: {
        data: [],
        column: [],
        totalRecord: 0
      },
      ifFullScreen: true,
      ifFullse: true
    };
  },
  watch: {
    ifFullScreen() {
      console.log(this.ifFullScreen);
      this.ifFullse = this.ifFullScreen;
    }
  },
  mounted() {
    this.ifFullse = this.ifFullScreen;
    this.listQuery.departIds = this.unitOptionsRYGKs;
    if (this.poster == "政治面貌") {
      let activeTabs = { paneName: "zzmmAttList" };
      this.handleClick(activeTabs, "even");
      if (this.names) {
        this.tabList.zzmmAttList.forEach((items, i) => {
          if (items.name == this.names) {
            this.btnGetItem(i);
          }
        });
      }
    } else if (this.poster == "学历学位") {
      let activeTabs = { paneName: "xlxwAttList" };
      this.handleClick(activeTabs, "even");
    } else if (this.poster == "性别") {
      let activeTabs = { paneName: "xbAttList" };
      this.handleClick(activeTabs, "even");
      if (this.names) {
        this.tabList.xbAttListss.forEach((items, i) => {
          if (items.name == this.names) {
            this.btnGetItem(i);
          }
        });
      }
    }
    this.setDialogTitle();
    // 调用获取单位方法，初始化单位及表格数据
    this.getUnitTree("").then(() => {
      //设置默认单位
      // this.UnitValue = [];
      this.$refs.RYGKtree.setCheckedKeys(this.unitOptionsRYGKs);
      // if (this.unitOptionsRYGK.length > 0) {
      //   this.UnitValues(this.unitOptionsRYGK);
      // }
      if (!this.unitOptionsRYGKs && this.unitOptionsRYGKs.length == 0) {
        this.setAllChecKeys();
        // 获取当前勾选的departIds数组
        this.getChecKeys();
      }
      // 传参 单位、type、pageNo、pageSize
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    });
  },
  methods: {
    // UnitValues(unitOptions) {
    //   unitOptions.forEach(itemse => {
    //     this.listQuery.departIds.forEach(items => {
    //       if (itemse.unitId == items) {
    //         this.UnitValue.push(itemse.unitName);
    //       }
    //     });
    //     if (itemse.childCompany.length > 0) {
    //       this.UnitValues(itemse.childCompany);
    //     }
    //   });
    // },
    //设置弹出框标题
    setDialogTitle() {
      this.$emit("listenChild", "人员概况");
    },
    //json对象转化成json数组对象
    stringToArray(result) {
      let arrColumn = [];
      // 格式转换{"prop":"name","label":"姓名"},
      for (let i in result.column) {
        arrColumn.push({ prop: i, label: result.column[i] });
      }
      return arrColumn;
    },
    // 获取单位
    getUnitTree(departIds) {
      return api
        .get_rygk_unitdata(departIds)
        .then(result => {
          return result.data;
        })
        .then(departIds => {
          this.unitOptionsRYGK = departIds;
        });
    },
    // 设置默认勾选根目录
    setAllChecKeys() {
      this.$refs.RYGKtree.setCheckedKeys([this.unitOptionsRYGK[0].unitId]);
    },
    // 获取勾选的目录
    getChecKeys() {
      console.log(this.$refs.RYGKtree);
      this.listQuery.departIds = this.$refs.RYGKtree.getCheckedKeys();
    },
    // 单位勾选 获取departIds
    handleNodeCheckRYGK(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      // var checkArrays = node.checkedNodes.map((a, i) => {
      //   return a.unitName;
      // });
      this.listQuery.departIds = node.checkedKeys;
      // this.UnitValue = checkArrays;
      // 查询生成表
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    //职级勾选
    handleNodeCheckRYGKzj() {
      console.log(this.UnitValuezj);
      this.activeClasses = 0;
      if (this.UnitValuezj == "综合管理序列") {
        this.zjAttListse = [{ name: "一级巡视员" }, { name: "二级巡视员" }];
      } else if (this.UnitValuezj == "执法勤务序列") {
        this.zjAttListse = [{ name: "一级警务专员" }, { name: "二级警务专员" }];
      } else if (this.UnitValuezj == "警务技术序列") {
        this.zjAttListse = [
          { name: "警务技术一级总监" },
          { name: "警务技术二级总监" }
        ];
      } else if (this.UnitValuezj == "全部") {
        this.zjAttListse = [];
      }
      this.activeClasse = 0;
      if (this.zjAttListse.length == 0) {
        this.listQuery.type = "厅级";
      } else {
        this.listQuery.type = this.zjAttListse[0].name;
      }
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // tabs板块切换
    handleClick(tab, event) {
      this.activeTab = tab.paneName; //每次切换都预设activeTab到model里
      this.resetItem(); //重置
    },
    // 类别点击切换 获取type
    btnGetItem(index) {
      this.activeClass = index;
      this.listQuery.type = this.tabList[this.activeTab][index].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 职级类别点击切换 获取type（右侧）
    btnGetItemss(index) {
      this.activeClasse = index;
      this.listQuery.type = this.zjAttListse[index].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 职级类别点击切换 获取type（左侧）
    btnGetItems(index) {
      this.activeClasses = index;
      if (this.UnitValuezj == "综合管理序列") {
        if (this.zjAttLists[index].name == "厅级") {
          this.zjAttListse = [{ name: "一级巡视员" }, { name: "二级巡视员" }];
        } else if (this.zjAttLists[index].name == "处级") {
          this.zjAttListse = [
            { name: "一级调研员" },
            { name: "二级调研员" },
            { name: "三级调研员" },
            { name: "四级调研员" }
          ];
        } else if (this.zjAttLists[index].name == "科级") {
          this.zjAttListse = [
            { name: "一级主任科员" },
            { name: "二级主任科员" },
            { name: "三级主任科员" },
            { name: "四级主任科员" }
          ];
        } else if (this.zjAttLists[index].name == "科员") {
          this.zjAttListse = [{ name: "一级科员" }, { name: "二级科员" }];
        }
      } else if (this.UnitValuezj == "执法勤务序列") {
        if (this.zjAttLists[index].name == "厅级") {
          this.zjAttListse = [
            { name: "一级警务专员" },
            { name: "二级警务专员" }
          ];
        } else if (this.zjAttLists[index].name == "处级") {
          this.zjAttListse = [
            { name: "一级高级警长" },
            { name: "二级高级警长" },
            { name: "三级高级警长" },
            { name: "四级高级警长" }
          ];
        } else if (this.zjAttLists[index].name == "科级") {
          this.zjAttListse = [
            { name: "一级警长" },
            { name: "二级警长" },
            { name: "三级警长" },
            { name: "四级警长" }
          ];
        } else if (this.zjAttLists[index].name == "科员") {
          this.zjAttListse = [{ name: "一级警员" }, { name: "二级警员" }];
        }
      } else if (this.UnitValuezj == "警务技术序列") {
        if (this.zjAttLists[index].name == "厅级") {
          this.zjAttListse = [
            { name: "警务技术一级总监" },
            { name: "警务技术二级总监" }
          ];
        } else if (this.zjAttLists[index].name == "处级") {
          this.zjAttListse = [
            { name: "警务技术一级主任" },
            { name: "警务技术二级主任" },
            { name: "警务技术三级主任" },
            { name: "警务技术四级主任" }
          ];
        } else if (this.zjAttLists[index].name == "科级") {
          this.zjAttListse = [
            { name: "警务技术一级主管" },
            { name: "警务技术一级主管" },
            { name: "警务技术一级主管" },
            { name: "警务技术一级主管" }
          ];
        } else if (this.zjAttLists[index].name == "科员") {
          this.zjAttListse = [{ name: "警务技术员" }];
        }
      } else if (this.UnitValuezj == "全部") {
        this.zjAttListse = [];
      }
      this.activeClasse = 0;
      if (this.UnitValuezj == "全部") {
        this.listQuery.type = this.zjAttLists[index].name;
      } else {
        this.listQuery.type = this.zjAttListse[0].name;
      }
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // tabs切换时候，重置 左侧item和分页、，默认选择第一行
    resetItem() {
      if (this.activeTab == "zjAttLists") {
        this.btnGetItems(0);
      } else {
        this.btnGetItem(0);
      }
    },
    // 分页部分
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.listQuery.pageSize = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.listQuery.pageNo = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 6:获取表格数据
    getTableData(departIds, type, pageNo, pageSize) {
      let data = {
        departIds: departIds,
        type: type,
        pageNo: pageNo,
        pageSize: pageSize
      };
      // 各tabs（年龄、性别、民族...）接口请求
      if (this.activeTab == "nlAttList") {
        this.tableDataNl.data = [];
        api.get_rygk_nl(data).then(result => {
          // 生成表格
          this.tableDataNl.data = result[1].data;
          this.tableDataNl.totalRecord = result[0];
        });
      } else if (this.activeTab == "xbAttList") {
        this.tableDataXb.data = [];
        api.get_rygk_xb(data).then(result => {
          this.tableDataXb.data = result[1].data;
          this.tableDataXb.totalRecord = result[0];
        });
      } else if (this.activeTab == "mzAttList") {
        this.tableDataMz.data = [];
        api.get_rygk_mz(data).then(result => {
          this.tableDataMz.data = result[1].data;
          this.tableDataMz.totalRecord = result[0];
        });
      } else if (this.activeTab == "zwAttLists") {
        this.tableDataZw.data = [];
        api.get_rygk_zw(data).then(result => {
          this.tableDataZw.data = result[1].data;
          this.tableDataZw.totalRecord = result[0];
        });
      } else if (this.activeTab == "lyAttList") {
        this.tableDataLy.data = [];
        api.get_rygk_ly(data).then(result => {
          this.tableDataLy.data = result[1].data;
          this.tableDataLy.totalRecord = result[0];
        });
      } else if (this.activeTab == "jxAttList") {
        this.tableDataJx.data = [];
        api.get_rygk_jx(data).then(result => {
          this.tableDataJx.data = result[1].data;
          this.tableDataJx.totalRecord = result[0];
        });
      } else if (this.activeTab == "zzmmAttList") {
        this.tableDataZzmm.data = [];
        api.get_rygk_zzmm(data).then(result => {
          this.tableDataZzmm.data = result[1].data;
          this.tableDataZzmm.totalRecord = result[0];
        });
      } else if (this.activeTab == "xlxwAttList") {
        this.tableDataXlxw.data = [];
        api.get_rygk_xlxw(data).then(result => {
          this.tableDataXlxw.data = result[1].data;
          this.tableDataXlxw.totalRecord = result[0];
        });
      } else if (this.activeTab == "zjAttLists") {
        this.tableDataZj.data = [];
        api.get_rygk_zj(data).then(result => {
          this.tableDataZj.totalRecord = result[0];
          let length1 = (pageNo - 1) * pageSize;
          let resultdata = result[1].data.splice(length1, pageSize);
          this.tableDataZj.data = resultdata;
        });
      }
    },
    leadingout() {
      this.multipleSelection = [];
      let data = {
        departIds: this.listQuery.departIds
      };
      api.get_rygk_getalld(data).then(result => {
        this.multipleSelection = result[0].data;
        // 生成表格
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "人员概况.xls"
          });
        }, 1000);
      });
    }
  }
};
</script>

<style scoped>
.customSelect {
  position: absolute;
  top: 17px;
  left: 100px;
}
.left {
  float: left;
  width: 150px;
  height: 80%;
  min-height: 400px;
  overflow: auto;
  border-right: 1px solid #e8e8e8;
}
.listStyle li {
  width: 100%;
  height: 43px;
  line-height: 43px;
  text-indent: 50px;
  cursor: pointer;
  text-align: -webkit-left;
}
.listStyle li.active {
  background-color: #1890ff;
  color: #ffffff;
}
.listStyles li {
  width: 24%;
  height: 25px;
  line-height: 25px;
  text-indent: 30px;
  cursor: pointer;
  text-align: -webkit-left;
  list-style: none;
}
.listStyles li.actives {
  background-color: #1890ff;
  color: #ffffff;
}
.right {
  float: left;
  width: calc(100% - 151px);
  height: 80%;
  overflow: auto;
  min-height: 400px;
}
.el-pagination {
  margin-top: 5px;
}
.righttable {
  width: 100%;
  height: 90%;
  min-height: 360px;
}
.rights {
  float: left;
  width: calc(100% - 151px);
  /* height: 80%; */
  overflow: auto;
  height: 400px;
}
.rightsee {
  float: left;
  width: calc(100% - 151px);
  /* height: 80%; */
  overflow: auto;
  height: 440px;
}
.rightse {
  float: left;
  width: calc(100% - 151px);
  height: 80%;
  overflow: auto;
  min-height: 440px;
}
.lefts {
  float: left;
  width: 150px;
  /* height: 80%; */
  height: 400px;
  overflow: auto;
  border-right: 1px solid #e8e8e8;
}
.righttables {
  width: 100%;
  /* height: 90%; */
  height: 360px;
}
.xzdw {
  top: 40px;
  left: 100px;
}
.xznd {
  top: 40px;
  left: 500px;
}
</style>
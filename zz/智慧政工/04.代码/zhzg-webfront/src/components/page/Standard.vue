<template>
	<div class="protArmsList">
		<div class="protArmsListCont">
			<div class="protArmsListHead">
				<el-breadcrumb class="crumbs" separator="/">
					<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
					<el-breadcrumb-item>证件申请</el-breadcrumb-item>
					<el-breadcrumb-item>持枪证审批</el-breadcrumb-item>
				</el-breadcrumb>
				<p class="approvehead">持枪证审批</p>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {

		}
	},
	created() {
		// this.init();
	},
	methods: {
		init() {
			var that = this;
			that.$http.get(that.global.serverPath + '/zhzg/armLicence/initQueryArmLicenceApproveByCon', {//传递参数
				params: {
					"personId": localStorage.getItem('loginpersonId'),
					"currentNum": that.cur_page,
					"pageSize": that.pagesize
				}
			}).then(function(res) {
				console.log(res)
			}, function(res) {
				self.$message.error('请求失败！')
			});
		},
		
	}
}
</script>
<style scoped>

</style>

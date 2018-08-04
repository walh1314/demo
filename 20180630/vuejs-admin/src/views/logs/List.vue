<template>
  <div class="search">
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="Action">
        <el-input v-model="formInline.action" placeholder="Action"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" stripe style="width: 100%" :highlight-current-row="true" @sort-change="sortChange" max-height="600">
      <!-- <el-table-column prop="memberCode" label="Member Code"  align="center"></el-table-column> -->
       <!-- <el-table-column type="expand"  fixed>
      <template slot-scope="props">
        <div  :formatter="formatTest(props)">{{props.row}}</div>
      </template>
    </el-table-column> -->
      <el-table-column prop="action" label="Action" width="150"  fixed align="center"></el-table-column>
      <el-table-column prop="ipInfo.ipAddress" width="120" label="Ip Address" align="center"></el-table-column>
     <el-table-column prop="targetDeviceId" width="180" label="Target Device Id" align="center"></el-table-column>
      <el-table-column prop="sourceDeviceId" width="180" label="Source Device Id" align="center"></el-table-column>
      <el-table-column prop="deviceInfo.model" width="120" label="Model" align="center"></el-table-column>
      <el-table-column prop="deviceInfo.appVersion" width="120" label="App Version" align="center"></el-table-column>
      <!-- <el-table-column prop="deviceInfo.appVersion" label="App Version" align="center"></el-table-column> -->
       <el-table-column prop="actionTime" width="160" label="Action Time" align="center" :formatter="formatDate"></el-table-column>
      <el-table-column prop="reportTime" width="160" label="Report Time" :formatter="formatDate" align="center"></el-table-column>
      <el-table-column label="Operation" width="100" fixed="right" align="center">
        <template slot-scope="scope">
          <el-popover  placement="top"  width="400" trigger="click">
              <div  :formatter="formatTest(scope)">{{scope.row}}</div>
            <el-button type="text" size="small" slot="reference">
            <i class="iconfont icon-details"></i>查看</el-button>
          </el-popover>
          <!-- <el-button @click="handleDetail(scope.row)" type="text" size="small">
            <i class="iconfont icon-details"></i>查看</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" :total="total" :background="true" @current-change="pageChange" @size-change="pageSizeChange" :current-page="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 40, 60, 100]"></el-pagination>
    </div>
  </div>
</template>

<script>
import {getLogByUser} from "../../http/logs";
export default {
  name: "LogsList",
  data() {
    return {
      formInline: {
        name: "",
        email: "",
        userId: "2018052199999",
        mobile: "0912345678"
      },
      tableData: [],
      originaltableData:[],
      currentPage: 1,
      total: 0,
      pageSize: 10
    };
  },
  mounted() {
    const query = {userId: "2018052199999",mobile: "0912345678"};
    query.pageSize = this.pageSize;
    query.currentPage = this.currentPage;
    getLogByUser(query)
      .then(res => {
        this.tableData = res.data;
        this.originaltableData = res.data;
      // this.pageSize = res.data.pageSize;
       this.total = res.data.length;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
  },
  filters:{
    
  },
  methods: {
    formatTest:function (aaa){
      console.log(aaa);
    },
     formatDate: function (row, column) {
      const data = row[column.property];
      var now = new Date(data*1000);
      var year = now.getFullYear();
      var month = this.prefixInteger(now.getMonth() + 1,2);
      var date = this.prefixInteger(now.getDate(),2);
      var hour = this.prefixInteger(now.getHours(),2);
      var minute = this.prefixInteger(now.getMinutes(),2);
      var second = this.prefixInteger(now.getSeconds(),2);
      return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
    },
    prefixInteger: function (num, n) {
        return (Array(n).join(0) + num).slice(-n);
    },
    onSubmit() {
      const keys = Object.keys(this.formInline);
      const query = {};
      keys.forEach(key => {
        this.formInline[key] &&
          (query[key] = (this.formInline[key] + "").trim());
      });
      query.pageSize = this.pageSize;
      query.currentPage = this.currentPage;
      getLogByUser(query)
        .then(res => {
          this.originaltableData = res.data;
          this.tableData = res.data;
          // this.pageSize = res.data.pageSize;
           this.total = res.data.length;
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
    formatIsAdmin(row, column, cellValue) {
      return cellValue ? "是" : "否";
    },
    formatStatus(row, column, cellValue) {
      return cellValue == "1" ? "正常" : "已禁用";
    },
    handleDetail(row) {
      // 查看详情
      this.$router.push(`/main/logs/detail/${row.id}`);
    }, 
    pageChange(currentPage) {
      this.currentPage = currentPage;
      //this.onSubmit();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      //this.onSubmit();
    },
    sortChange(column, prop, order) {
      //this.onSubmit();
    },
  }
};
</script>

<style scoped>
.search {
  width: 100%;
  height: 100%;
  text-align: left;
}
.page {
  text-align: center;
  margin-top: 20px;
}
</style>


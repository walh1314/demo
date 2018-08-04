<template>
  <div class="search">
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="设备名">
        <el-input v-model="formInline.name" placeholder="设备名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="$router.push('/main/device/edit')">新增设备</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" stripe style="width: 100%;" :highlight-current-row="true">
      <el-table-column prop="id" label="编号" sortable align="center"></el-table-column>
      <el-table-column prop="name" label="设备名称" align="center"></el-table-column>
      <el-table-column prop="code" label="设备编号" align="center"></el-table-column>
      <el-table-column prop="lanNetAddr" label="局域网地址" align="center"></el-table-column>
      <el-table-column prop="pubNetAddr" label="公网地址" align="center"></el-table-column>
      <el-table-column prop="type" label="设备分类" align="center"></el-table-column>
      <el-table-column prop="model" label="设备型号" align="center"></el-table-column>
      <el-table-column prop="exFactoryDate" label="出厂日期" align="center" :formatter="formatDate"></el-table-column>
      <el-table-column prop="status" label="设备状态" align="center" ></el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" :formatter="formatDate"></el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button @click="$router.push('/main/device/edit/' + scope.row.id)" type="text" size="small">
            <i class="iconfont icon-details"></i>查看</el-button>
          <el-button @click="$router.push('/main/device/mark/' + scope.row.id)" type="text" size="small">
            <i class="iconfont el-icon-edit"></i>标示</el-button>
          <el-button @click="handleDelete(scope.row)" type="text" size="small">
            <i class="iconfont icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" :total="tableData.length" :background="true" @current-change="pageChange" @size-change="pageSizeChange" :current-page="currentPage" :page-size="pageSize" :page-sizes="[5, 10, 20, 50, 100]"></el-pagination>
    </div>
  </div>
</template>
<script>
import { getDeviceList, deleteDeviceById } from "../../http/device";
export default {
  name: "DeviceList",
  data() {
    return {
      formInline: {
        name: "",
        email: ""
      },
      tableData: [],
      currentPage: 1,
      pageSize: 5
    };
  },
  filters:{
    
  },
  mounted() {
    const query = {};
    query.pageSize = this.pageSize;
    query.currentPage = this.currentPage;
    getDeviceList(query)
      .then(res => {
        this.tableData = res.data.rows;
        this.pageSize = res.data.pageSize;
        this.total = res.data.totalNum;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
  },
  methods: {
    onSubmit() {
      const keys = Object.keys(this.formInline);
      const query = {};
      query.pageSize = this.pageSize;
      query.currentPage = this.currentPage;
      keys.forEach(key => {
        this.formInline[key] &&
          (query[key] = (this.formInline[key] + "").trim());
      });
      getDeviceList(query)
        .then(res => {
          this.tableData = res.data.rows;
          this.pageSize = res.data.pageSize;
          this.total = res.data.totalNum;
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
    formatIsAdmin(row, column, cellValue) {
      return cellValue ? "是" : "否";
    },
    formatStatus(row, column, cellValue) {
      return cellValue === "ok" ? "正常" : "已禁用";
    },
    handleDelete(row) {   // 删除
      console.log("row:", row);
      this.$confirm("此操作将永久删除该设备, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteDeviceById(row.id)
            .then(res => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.tableData = this.tableData.filter(
                item => item.id !== row.id
              );
            })
            .catch(err => {
              this.$message.error(err.message);
            });
        })
        .catch(err => {
          console.log(err);
        });
    },
    addDevice() {
      // 添加设备
      this.$router.push("/main/device/setting");
    },
    pageChange(currentPage) {
      console.log("当前页：", currentPage);
      this.currentPage = currentPage;
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
    },
    formatDate:function (row,column) {
          const  data = row[column.property];
          console.log(data);
          var now = new Date(data);
          var year = now.getFullYear();
          var month = now.getMonth() + 1;
          var date = now.getDate();
          var hour = now.getHours();
          var minute = now.getMinutes();
          var second = now.getSeconds();
          return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
      }
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

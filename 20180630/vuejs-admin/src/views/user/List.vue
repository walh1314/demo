<template>
  <div class="search">
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="用户名">
        <el-input v-model="formInline.name" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="formInline.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="$router.push('/main/user/setting/add')">新增用户</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" stripe style="width: 100%" :highlight-current-row="true"   @sort-change="sortChange">
      <el-table-column prop="name" label="用户名"  align="center"></el-table-column>
      <el-table-column prop="zhName" label="中文名" align="center"></el-table-column>
      <el-table-column prop="enName" label="英文名" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" :formatter="formatStatus" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
      <el-table-column prop="mobile" label="手机号码" align="center"></el-table-column>
      <el-table-column prop="shortPhone" label="短号" align="center"></el-table-column>
      <el-table-column prop="desc" label="备注信息" align="center"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="handleDetail(scope.row)" type="text" size="small">
            <i class="iconfont icon-details"></i>查看</el-button>
          <el-button @click="handleDelete(scope.row)" type="text" size="small">
            <i class="iconfont icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" :total="total" :background="true"
       @current-change="pageChange" @size-change="pageSizeChange" :current-page="currentPage" 
       :page-size="pageSize" :page-sizes="[10, 20, 40, 60, 100]"></el-pagination>
    </div>
  </div>
</template>

<script>
import { getUserList, deleteUserById } from "../../http/user";
export default {
  name: "UserList",
  data() {
    return {
      formInline: {
        name: "",
        email: ""
      },
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 10
    };
  },
  mounted() {
    const query = {};
    query.pageSize = this.pageSize;
    query.currentPage = this.currentPage;
    getUserList(query)
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
      keys.forEach(key => {
        this.formInline[key] &&
          (query[key] = (this.formInline[key] + "").trim());
      });
      query.pageSize = this.pageSize;
      query.currentPage = this.currentPage;
      getUserList(query)
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
      return cellValue == "1" ? "正常" : "已禁用";
    },
    handleDetail(row) {
      // 查看详情
      this.$router.push(`/main/user/setting/${row.id}`);
    }, // 删除
    handleDelete(row) {
      console.log("row:", row);
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
          deleteUserById(row.id).then(res => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.tableData = this.tableData.filter(
                item => item.id !== row.id
              );
            }).catch(err => {
              this.$message.error(err.message);
            });
        })
        .catch(err => {
          console.log(err);
        });
    },
    addUser() {
      // 添加用户
      this.$router.push("/main/user/setting");
    },
    pageChange(currentPage) {
      this.currentPage = currentPage;
      this.onSubmit();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.onSubmit();
    },
    sortChange(column, prop, order) {
      this.onSubmit();
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


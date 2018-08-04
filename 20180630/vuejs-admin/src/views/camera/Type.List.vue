<template>
  <div class="search">
    <el-form :inline="true" :model="formInline" ref="formInline" class="form-inline">
      <el-form-item label="类型名称">
        <el-input v-model="formInline.name" placeholder="请输入类型类型名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="dialogFormVisible = true">新增类型</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="新增类型" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" >
          <el-form-item label="类型名称" :label-width="formLabelWidth">
                <el-input v-model="form.name" placeholder="请输入类型名称"></el-input>
          </el-form-item>
          <el-form-item label="类型Code" :label-width="formLabelWidth">
                <el-input v-model="form.code" placeholder="请输入类型Code"></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input type="textarea" :rows="2" placeholder="请输入备注" v-model="form.desc">
              </el-input>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <el-table :data="tableData" stripe style="width: 100%;" :highlight-current-row="true">
      <el-table-column fixed prop="name" label="类型名称" align="center"></el-table-column>
      <el-table-column prop="code" label="类型Code" align="center"></el-table-column>
      <el-table-column prop="desc" label="备注" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" :formatter="formatDate"></el-table-column>
      <el-table-column label="操作" width="300" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button @click="$router.push('/main/camera/edit/' + scope.row.id)" type="text" size="small">
            <i class="iconfont icon-details"></i>查看</el-button>
          <el-button @click="handleDelete(scope.row)" type="text" size="small">
            <i class="iconfont icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="page">
      <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" 
        :total="tableData.length" :background="true" @current-change="pageChange"
         @size-change="pageSizeChange" :current-page="currentPage" :page-size="pageSize" 
        :page-sizes="[10, 20, 40, 60, 100]"></el-pagination>
    </div>
  </div>
</template>

<script>
import { getCameraList, deleteCameraById } from "../../http/camera";
import { Message } from 'element-ui';

export default {
  name: "CameraList",
  data() {
    return {
      dialogFormVisible:false,
      formLabelWidth:"100px",
      form:{
        name:"",
        code:"",
        desc:""
      },
      formInline: {
        name: "",
        email: ""
      },
      tableData: [],
      currentPage: 1,
      pageSize: 10
    };
  },
  filters:{
    
  },
  mounted() {
    const query = {};
    query.pageSize = this.pageSize;
    query.currentPage = this.currentPage;
    getCameraList(query)
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
      getCameraList(query)
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
    handleDelete(row) {   // 删除
      console.log("row:", row);
      this.$confirm("此操作将永久删除该设备, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteCameraById(row.id)
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
    addCamera() {
      // 添加设备
      this.$router.push("/main/camera/setting");
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

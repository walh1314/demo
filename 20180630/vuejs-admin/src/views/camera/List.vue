<template>
  <div class="search">
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="设备名称">
        <el-input v-model="formInline.name" placeholder="设备名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="$router.push('/main/camera/edit')">新增设备</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchDeviceList('2018052199999','0912345678')">搜寻设备</el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="可用设备列表" :visible.sync="dialogFormVisible" width="90%" @close="closeDialog">
      <el-form :inline="true" size="mini" :model="requestAddDeviceList.formInline" class="form-inline">
        <el-form-item label="设备名称">
          <el-input v-model="requestAddDeviceList.formInline.name" placeholder="请输入设备名称"></el-input>
        </el-form-item>
        <el-form-item label="设备序号">
          <el-input v-model="requestAddDeviceList.formInline.serial" placeholder="请输入设备序号"></el-input>
        </el-form-item>
        <el-form-item label="MAC地址">
          <el-input v-model="requestAddDeviceList.formInline.macAddr" placeholder="请输入MAC地址"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status" style="text-align:left;">
        <el-select v-model="requestAddDeviceList.formInline.status" placeholder="请选择">
          <el-option v-for="item in requestAddDeviceList.statusList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearchAddDevice">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="addNewDeviceList">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="deviceListData.tableData.slice((deviceListData.currentPage-1)*deviceListData.pageSize,deviceListData.currentPage*deviceListData.pageSize)" stripe style="width: 100%;" :highlight-current-row="true" @selection-change="changeFun">
        <el-table-column type="selection" width="55" class="selection" @selection-change="changeFun"></el-table-column>
        <el-table-column fixed prop="alias" label="设备别名" align="center"></el-table-column>
        <el-table-column fixed prop="name" label="设备名称" align="center"></el-table-column>
        <el-table-column prop="serial" label="设备序号" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" align="center" :formatter="deviceStatusFormat"></el-table-column>
        <el-table-column prop="macAddr" label="MAC地址" align="center"></el-table-column>
        <el-table-column prop="manufacturer" label="设备制造商" align="center"></el-table-column>
        <el-table-column prop="deviceType.typeName" label="设备分类" align="center"></el-table-column>
        <!-- <el-table-column prop="sku" label="SKU" align="center"></el-table-column> -->
        <el-table-column prop="modelName" label="Model Name" align="center"></el-table-column>
      </el-table>
      <div class="page">
        <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" :total="deviceListData.tableData.length" :background="true" @current-change="pageDeviceChange" @size-change="pageSizeDeviceChange" :current-page="deviceListData.currentPage" :page-size="deviceListData.pageSize" :page-sizes="[10, 20, 40, 60, 100]"></el-pagination>
      </div>
    </el-dialog>
    <el-table :data="tableData" stripe style="width: 100%;" :highlight-current-row="true">
      <!-- <el-table-column prop="id" label="编号" sortable align="center"></el-table-column> -->
      <el-table-column fixed prop="name" label="设备名称" align="center"  width="130">></el-table-column>
      <!-- <el-table-column prop="deviceId" label="Device Id" align="center"></el-table-column> -->
      <el-table-column prop="serial" label="设备序号" align="center" width="130"></el-table-column>
      <!-- <el-table-column prop="lanNetAddr" label="局域网地址" align="center"></el-table-column> -->
      <el-table-column prop="macAddr" label="MAC地址" align="center"  width="130"></el-table-column>
      <!-- <el-table-column prop="pubNetAddr" label="网络地址" align="center"  width="130"></el-table-column> -->
      <!-- <el-table-column prop="type" label="设备分类" align="center"></el-table-column> -->
      <el-table-column prop="cameraType.name" label="设备分类" align="center"></el-table-column>
      <el-table-column prop="status" label="设备状态" align="center" :formatter="formatStatus"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" :formatter="formatDate"  width="160"></el-table-column>
      <el-table-column label="操作" width="300" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button @click="$router.push('/main/camera/edit/' + scope.row.id)" type="text" size="small">
            <i class="iconfont icon-details"></i>查看</el-button>
          <el-button @click="$router.push('/main/camera/mark/' + scope.row.deviceId +'/'+scope.row.topic)" type="text" size="small">
            <i class="iconfont el-icon-edit"></i>标示</el-button>
          <el-button @click="handleDelete(scope.row)" type="text" size="small">
            <i class="iconfont icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination layout="prev, pager, next, jumper, ->, sizes, total" :total="total" :background="true" @current-change="pageChange" @size-change="pageSizeChange" :current-page="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 40, 60, 100]"></el-pagination>
    </div>
  </div>
</template>

<script>
import { getCameraList, deleteCameraById } from "../../http/camera";
import { getRequireAddDeviceList, addDeviceList } from "../../http/device";
import { Message } from "element-ui";

export default {
  name: "CameraList",
  data() {
    return {
      formInline: {
        name: "",
        email: ""
      },
      dialogFormVisible: false,
      requestAddDeviceList: {
        formInline: {
          name: "",
          serial: "",
          macAddr: "",
          status:""
        },
        statusList:[
          {
          id:'',
          name:"请选择"
        },{
          id:0,
          name:"待添加"
        },{
          id:1,
          name:"已添加"
        }
        ],
        query:{
          name: "",
          serial: "",
          macAddr: "",
          status:""
        },
        tableData: [],
        currentPage: 1,
        pageSize: 10
      },
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total:0
    };
  },
  computed: {
    deviceListData: function() {
      const deviceListDataTemp = this.requestAddDeviceList.tableData;
      const result = { tableData: [],
        currentPage: 1,
        pageSize: 10};
      const query = this.requestAddDeviceList.query;
      if (deviceListDataTemp != null && deviceListDataTemp.length > 0) {
        const tableData = deviceListDataTemp.filter(function(item) {
          return (
            String(item["name"])
              .toLowerCase()
              .indexOf(query["name"].trim().toLowerCase()) > -1 &&
            String(item["serial"])
              .toLowerCase()
              .indexOf(query["serial"].trim().toLowerCase()) > -1 &&
            (query["macAddr"].trim() == "" ||
              String(item["macAddr"]).toLowerCase() ==
                query["macAddr"].trim().toLowerCase()) &&
            (String(query["status"]) == "" || String(item["status"]) == query["status"])
          );
        });
        result.tableData = tableData;
        result.total = tableData.length;

      }
      return result;
    }
  },
  filters: {},
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
    closeDialog:function(){
      //清理查询属性
      this.requestAddDeviceList.formInline= {name: "",serial: "",macAddr: "",status:""};
      this.requestAddDeviceList.query = JSON.parse(JSON.stringify(this.requestAddDeviceList.formInline));
    },
    deviceStatusFormat: function(row, column) {
      const data = row[column.property];
      return data == 1 ?'已添加':'待添加';
    },
    addNewDeviceList: function() {
      addDeviceList(this.multipleSelection)
        .then(res => {
          if (res.code == "1") {
            this.$message.info(res.msg || "处理成功");
            this.dialogFormVisible = false;
          } else {
            this.$message.error(res.msg || "系统异常，请联系管理员");
          }
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
    onSearchAddDevice: function() {
      this.requestAddDeviceList.query = JSON.parse(JSON.stringify(this.requestAddDeviceList.formInline));
    },
    changeFun: function(val) {
      this.multipleSelection = val;
    },
    searchDeviceList: function(userId, mobile) {
      this.dialogFormVisible = true;
      const query = {};
      query.userId = userId;
      query.mobile = mobile;
      getRequireAddDeviceList(query)
        .then(res => {
          if (res.code == "1") {
            this.requestAddDeviceList.tableData = res.data || [];
            this.requestAddDeviceList.total = res.data ? res.data.length : 0;
          } else {
            this.$message.error(res.msg || "系统异常，请联系管理员");
          }
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
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
    handleDelete(row) {
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
      this.currentPage = currentPage;
      this.onSubmit();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.onSubmit();
    },
    pageDeviceChange(currentPage) {
      this.deviceListData.currentPage = currentPage;
    },
    pageSizeDeviceChange(pageSize) {
      this.deviceListData.pageSize = pageSize;
    },
    formatDate: function(row, column) {
      const data = row[column.property];
      var now = new Date(data);
      var year = now.getFullYear();
      var month = now.getMonth() + 1;
      var date = now.getDate();
      var hour = now.getHours();
      var minute = now.getMinutes();
      var second = now.getSeconds();
      return (
        year +
        "-" +
        month +
        "-" +
        date +
        "   " +
        hour +
        ":" +
        minute +
        ":" +
        second
      );
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

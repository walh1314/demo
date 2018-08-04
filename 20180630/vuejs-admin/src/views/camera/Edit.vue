<template>
  <div class="form">
    <el-form :label-position="labelPosition" label-width="125px" :model="camera" :rules="rules" ref="camera">
      <el-form-item label="设备名称" prop="name">
        <el-input v-model="camera.name"></el-input>
      </el-form-item>
      <el-form-item label="设备序列号" prop="serial">
        <el-input v-model.number="camera.serial" :disabled="(camera.id == 'undefined' || camera.id == null) ? false:true"></el-input>
      </el-form-item>
      <el-form-item label="MAC地址" prop="macAddr">
        <el-input v-model="camera.macAddr"></el-input>
      </el-form-item>
      <el-form-item label="设备类别" prop="type" style="text-align:left;">
        <el-select v-model="camera.type" placeholder="请选择">
          <el-option v-for="item in cameraTypes" :key="item.id" :label="item.name" :value="item.id+''">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备制造商" prop="manufacturer">
        <el-input v-model="camera.manufacturer"></el-input>
      </el-form-item>
      <el-form-item v-if="this.$route.params.cameraId" label="设备状态" style="text-align:left;" prop="status">
        <el-radio-group v-model="camera.status">
          <el-radio :label="1">有效</el-radio>
          <el-radio :label="-1">无效</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="说明" prop="desc">
        <el-input type="textarea" :rows="4" placeholder="请输入描述内容" v-model="camera.desc">
        </el-input>
      </el-form-item>
      <el-form-item style="text-align:left;margin-top:40px;">
        <el-button type="primary" @click="saveCameraInfo('camera')">保存设置</el-button>
        <el-button @click="back" type="info">返回上级</el-button>
        <el-button v-if="!$route.params.devId" @click="clearForm('camera')" type="warning">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  getCameraDetail,
  updateCameraInfo,
  addCamera
} from "../../http/camera";
import { getCameraTypeList } from "../../http/cameraType";
import { getUserList } from "../../http/user";
import { mapState } from "vuex";
export default {
  name: "CameraEdit",
  mounted() {
    getCameraTypeList()
      .then(res => {
        this.cameraTypes = res.data;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
    // 加载设备信息
    var _this = this;
    if (!this.$route.params.cameraId) return;
    getCameraDetail(this.$route.params.cameraId)
      .then(res => {
        if (res.code != 1) {
          this.$message.error(err.message || "系统异常");
        } else {
          // console.log(res.data);
          _this.camera = res.data;
        }
      })
      .catch(err => {
        this.$message.error(err.message);
      });
  },
  data() {
    return {
      users: [],
      cameraTypes: [],
      labelPosition: "right",
      camera: {
        status: 1
      },
      rules: {
        name: [
          { required: true, message: "请输入设备名称", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur,change"
          }
        ],
        serial: [
          {
            required: true,
            message: "请输入设备序列号",
            trigger: "blur,change"
          }
        ],
        type: [
          {
            required: true,
            message: "设备类别不能为空",
            trigger: "blur, change"
          }
        ]
      }
    };
  },
  computed: {
    ...mapState["user"]
  },
  methods: {
    back() {
      this.$router.back();
    },
    handleAvatarSuccess(res, file) {
      this.camera.pic = "/" + res.url;
      // this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    saveCameraInfo(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.camera.code += "";
          if (this.camera.id) {
            // 修改保存
            updateCameraInfo(this.camera)
              .then(res => {
                this.$router.back();
              })
              .catch(err => {
                console.log(err);
                this.$message.error(err.message);
              });
          } else {
            // 新增保存
            addCamera(this.camera)
              .then(res => {
                this.$message({
                  type: "success",
                  message: "新增成功!"
                });
                this.$router.back();
              })
              .catch(err => {
                this.$message.error(err.message);
              });
          }
        }
      });
    },
    removeDevArg(devArg, index) {
      this.camera.args.splice(index, 1);
    },
    addDevArg() {
      this.camera.args.push({});
    },
    clearForm(form) {
      // this.camera.args = this.camera.args.map(item => {
      //   item.desc = "";
      //   return item;
      // });

     
      if (!this.$route.params.cameraId) { this.$refs[form].resetFields(); return; }
      var _this = this;
      getCameraDetail(this.$route.params.cameraId)
        .then(res => {
          if (res.code != 1) {
            this.$message.error(err.message || "系统异常");
          } else {
            _this.camera = res.data;
          }
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    }
  }
};
</script>

<style scoped>
.form {
  width: 52%;
  /* margin: 0 auto; */
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

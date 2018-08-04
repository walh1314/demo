<template>
  <div class="form">
    <el-form :label-position="labelPosition" label-width="125px" :model="camera" :rules="rules" ref="camera">
      <el-form-item label="设备名称" prop="name">
        <el-input v-model="camera.name"></el-input>
      </el-form-item>
      <el-form-item label="serial" prop="code">
        <el-input v-model.number="camera.serial"></el-input>
      </el-form-item>
      <el-form-item label="Device Id" prop="deviceId">
        <el-input v-model="camera.deviceId"></el-input>
      </el-form-item>
      <el-form-item label="MAC 地址" prop="macAddr">
        <el-input v-model="camera.macAddr"></el-input>
      </el-form-item>
      <el-form-item label="公网地址" prop="pubNetAddr">
        <el-input v-model="camera.pubNetAddr"></el-input>
      </el-form-item>
      <el-form-item label="局域网地址" prop="lanNetAddr">
        <el-input v-model="camera.lanNetAddr"></el-input>
      </el-form-item>
      <el-form-item label="设备类别" prop="type" style="text-align:left;">
        <el-select v-model="camera.type" placeholder="请选择">
          <el-option v-for="item in cameraTypes" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地理位置" prop="locationAddr">
        <el-input v-model="camera.locationAddr"></el-input>
      </el-form-item>
      <el-form-item label="设备状态" style="text-align:left;" prop="status">
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
        <el-button type="primary" @click="savecameraInfo('camera')">保存设置</el-button>
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
        this.devTypes = res.rows;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
    getUserList()
      .then(res => {
        this.users = res.rows;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
    // 加载设备信息
    if (!this.$route.params.cameraId) return;
    getCameraDetail(this.$route.params.cameraId)
      .then(res => {
        // getDevArgList({ cameraId: res.id })
        //   .then(argRes => {
        //     this.camera = Object.assign({}, res, { args: argRes.rows });
        //   })
        //   .catch(err => {
        //     this.$message.error(err.message);
        //   });
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
        args: [{}],
        status: "online",
        pic: "" // 如果没有改属性，那么新增页下的头像始终不会显示，因为v-if检测不到空对象中的某个属性
      },
      rules: {
        name: [
          { required: true, message: "请输入用户名称", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在 3 到 10 个字符",
            trigger: "blur,change"
          }
        ],
        code: [
          { required: true, message: "请输入设备编号", trigger: "blur,change" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: "blur,change"
          }
        ],
        type: [
          {
            required: true,
            message: "设备类别不能为空",
            trigger: "blur, change"
          }
        ],
        userId: [
          {
            required: true,
            message: "所属用户不能为空",
            trigger: "blur, change"
          }
        ],
        prodDate: [
          {
            required: true,
            message: "出场日期不能为空",
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
    savecameraInfo(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.camera.code += "";
          if (this.camera.id) {
            // 修改保存
            updatecameraInfo(this.camera)
              .then(res => {
                this.$router.back();
              })
              .catch(err => {
                console.log(err);
                this.$message.error(err.message);
              });
          } else {
            // 新增保存
            addcamera(this.camera)
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
      this.camera.args = this.camera.args.map(item => {
        item.desc = "";
        return item;
      });
      this.$refs[form].resetFields();
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

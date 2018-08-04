<template>
  <div class="form">
    <el-form :label-position="labelPosition" label-width="80px" :model="userInfo" :rules="rules" ref="userInfo">
      <el-form-item label="用户名" prop="name">
        <el-input v-model="userInfo.name" placeholder="请输入用户名"></el-input>
      </el-form-item>
            <el-form-item label="中文名" prop="zhName">
        <el-input v-model="userInfo.zhName" placeholder="请输入中文名"></el-input>
      </el-form-item>
            <el-form-item label="英文名" prop="enName">
        <el-input v-model="userInfo.enName" placeholder="请输入英文名"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userInfo.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input v-model="userInfo.mobile" placeholder="请输入手机号码"></el-input>
      </el-form-item>
      <el-form-item label="短号" prop="shortPhone">
        <el-input v-model="userInfo.shortPhone" placeholder="请输入短号"></el-input>
      </el-form-item>
      <el-form-item label="当前状态" style="text-align:left;" prop="status">
        <el-radio-group v-model="userInfo.status">
          <el-radio :label="1">正常</el-radio>
          <el-radio :label="-1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="desc">
        <el-input type="textarea" :rows="4" placeholder="请输入备注" v-model="userInfo.desc">
        </el-input>
      </el-form-item>
      <el-form-item style="text-align:left;">
        <el-button type="primary" @click="saveUserInfo('userInfo')">保存</el-button>
        <el-button @click="back" type="info">返回上级</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserDetail, updateUserInfo, addUser } from "../../http/user";
import { mapState } from "vuex";
export default {
  name: "UserSetting",
  mounted() {
    // 加载用户信息
    if (this.$route.params.userId === "add") return;
    // 这里使用this.user获取不到数据，因为mounted先执行
    getUserDetail(
      this.$route.params.userId || this.$store.state.user.userInfo.id
    ).then(res => {
        this.userInfo = res.data;
    }).catch(err => {
        console.log(err);
        this.$message({
          type: "warning",
          message: err.message
        });
      });
  },
  data() {
    const checkAge = (rule, value, callback) => {
      if(value == null) {
         callback();
         return ;
      }
    };
    return {
      labelPosition: "right",
      userInfo: {
        status: 1,
        avatar: "" // 如果没有改属性，那么新增页下的头像始终不会显示，因为v-if检测不到空对象中的某个属性
      },
      rules: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在 3 到 10 个字符",
            trigger: "blur,change"
          }
        ],
        age: [
          {
            validator: checkAge,
            trigger: "blur,change"
          }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur,change" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: "blur,change"
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
      this.userInfo.avatar = "/" + res.url;
      // this.imageUrl = URL.createObjectURL(file.raw);
    },
    saveUserInfo(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.userInfo.id) {
            // 修改保存
            updateUserInfo(this.userInfo)
              .then(res => {
                this.$router.back();
              })
              .catch(err => {
                console.log(err);
                this.$message.error(err.message);
              });
          } else {
            // 新增保存
            addUser(this.userInfo)
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
    }
  }
};
</script>

<style scoped>
.form {
  width: 50%;
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

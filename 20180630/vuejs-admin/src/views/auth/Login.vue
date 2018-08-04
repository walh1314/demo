<template>
  <div class="loginPage">
    <div class="form">
      <el-row>
        <el-col :span="24">
          <el-form label="" :label-position="labelPosition" label-width="100px" :model="ruleForm" ref="ruleForm" :rules="rules">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="ruleForm.userName" type="text" @keyup.enter.native="submitForm('ruleForm')"></el-input>
            </el-form-item>
            <el-form-item label="密码" style="color: #fff;" prop="password">
              <el-input v-model="ruleForm.password" type="password" @keyup.enter.native="submitForm('ruleForm')"></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-button type="primary" @click="submitForm('ruleForm')">登 录</el-button>
              <el-button type="info" @click="clearForm('ruleForm')">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import { login } from "../../http/user";

export default {
  name: "Login",
  data() {
    return {
      ruleForm: {
        userName: "",
        password: ""
      },
      isPass: false,
      labelPosition: "right",
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    ...mapMutations(["LOGIN"]),
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          login({
            userName: this.ruleForm.userName,
            password: this.ruleForm.password
          }).then(res => {
            debugger;
            console.log('11111111111');
              this.LOGIN(res);
              this.$router.push("/main");
            })
            .catch(err => {
              this.$message.error(err.message);
            });
        }
      });
    },
    clearForm(formName) {
      this.$refs[formName].resetFields();
      this.isPass = false;
    }
  }
};
</script>

<style scoped>
.loginPage {
  width: 100%;
  height: 100%;
  background-image: url("../../assets/bg_login.jpg");
  background-size: auto;
}
.form {
  position: absolute;
  width: 450px;
  height: 350px;
  top: 50%;
  left: 50%;
  margin-left: -255px;
  margin-top: -100px;
}
.el-button {
  margin-top: 20px;
}
.el-form-item__label {
  color: #fff;
}
</style>


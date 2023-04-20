<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" label-width="80px">
      <h3 class="login-title">用户登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input v-model="loginForm.account" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script>
import { loginApi } from "@/api/user.js";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        account: "",
        password: "",
      },
    };
  },
  methods: {
    handleSubmit() {
      if (this.loginForm.account == "" || this.loginForm.password == "") {
        this.$message({
          message: "账号或密码不能为空",
          type: "error",
          showClose: true,
        });
        return;
      }

      loginApi(this.loginForm).then((res) => {
        console.log(res);
        if (res.data.code == 200) {
          //登录成功
          localStorage.setItem("account", res.data.data.account);
          localStorage.setItem("username", res.data.data.username);
          localStorage.setItem("role", res.data.data.role);
          localStorage.setItem("token", res.data.data.token);

          this.$router.push({
            path: "/",
          });
        } else {
          //登录失败
          this.$message({
            message: res.data.msg,
            type: "error",
            showClose: true,
          });
        }
      });
    },
  },
};
</script>
  
<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 30px 20px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-title {
  font-size: 20px;
  font-weight: 500;
  text-align: center;
  margin-bottom: 30px;
}
</style>
  
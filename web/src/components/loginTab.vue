<template>
  <div class="tab">
    <div class="tab-left">
      <div v-if="username">{{ username }}</div>
    </div>
    <div class="tab-right">
      <router-link v-if="!token" to="/login">登录</router-link>
      <el-button v-if="isAdmin" type="primary" @click="gotoAdmin">进入后台</el-button>
      <el-button v-if="token" @click="logout">退出登录</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      isAdmin: false,
      token: localStorage.getItem('token'),
    };
  },
  created() {
    this.username = localStorage.getItem('username');
    this.isAdmin = localStorage.getItem('role') === '1';
  },
  methods: {
    gotoAdmin() {
      this.$router.push('/admin');
    },
    logout() {
      localStorage.clear();
      window.location.reload();
    },
  },
};
</script>

<style>
.tab {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #41b883;
  color: #fff;
}

.tab-left {
  font-size: 18px;
}

.tab-right {
  display: flex;
  align-items: center;
}

.tab-right > *:not(:last-child) {
  margin-right: 10px;
}
</style>

<template>
  <div>
    <login-tab></login-tab>
    <div class="app-intro">
      <el-container>
        <el-header class="header">
          <h1 style="margin-top: 100px;">{{ title }}</h1>

          <div class="custom-shape-divider-bottom-1681374828">
            <svg data-name="Layer 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1200 120" preserveAspectRatio="none">
              <path
                d="M0,0V46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1113-14.29,1200,52.47V0Z"
                opacity=".25" class="shape-fill"></path>
              <path
                d="M0,0V15.81C13,36.92,27.64,56.86,47.69,72.05,99.41,111.27,165,111,224.58,91.58c31.15-10.15,60.09-26.07,89.67-39.8,40.92-19,84.73-46,130.83-49.67,36.26-2.85,70.9,9.42,98.6,31.56,31.77,25.39,62.32,62,103.63,73,40.44,10.79,81.35-6.69,119.13-24.28s75.16-39,116.92-43.05c59.73-5.85,113.28,22.88,168.9,38.84,30.2,8.66,59,6.17,87.09-7.5,22.43-10.89,48-26.93,60.65-49.24V0Z"
                opacity=".5" class="shape-fill"></path>
              <path
                d="M0,0V5.63C149.93,59,314.09,71.32,475.83,42.57c43-7.64,84.23-20.12,127.61-26.46,59-8.63,112.48,12.24,165.56,35.4C827.93,77.22,886,95.24,951.2,90c86.53-7,172.46-45.71,248.8-84.81V0Z"
                class="shape-fill"></path>
            </svg>
          </div>

        </el-header>
        <el-main class="main">
          <el-row>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" class="feature">
              <i class="el-icon-s-promotion"></i>
              <h2>灵活性</h2>
              <p>问卷系统可以根据不同的需要和目的灵活定制和设计问卷，包括问卷的结构、题目类型、选项内容等。此外，
                问卷系统还可以根据不同的受众群体设置不同的问卷语言和调查方式，以确保问卷的可行性和有效性。</p>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" class="feature">
              <i class="el-icon-picture"></i>
              <h2>可视化</h2>
              <p>问卷系统可以提供直观、易于理解的图表和统计数据，帮助用户快速了解调查结果和趋势。通过可视化的方式
                呈现问卷数据，用户可以更容易地发现问题和机会，并做出相应的决策和行动。</p>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" class="feature">
              <i class="el-icon-s-check"></i>
              <h2>数据安全性</h2>
              <p>问卷系统可以通过各种技术手段保护问卷数据的安全性和隐私性，包括数据加密、访问控制、备份和恢复等。
                这样可以确保问卷数据不会被未经授权的人员访问、修改或泄露，从而保障用户的数据安全。</p>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" class="feature">
              <i class="el-icon-s-claim"></i>
              <h2>自动化</h2>
              <p>问卷系统可以实现问卷调查的自动化，包括问卷的分发、回收、统计和报告等。通过自动化的方式，问卷系统
                可以大大减少用户的工作量，提高调查效率和准确性，同时还可以节省成本和时间。</p>
            </el-col>
          </el-row>
        </el-main>
        <el-footer class="footer">
          <p>{{ copyright }}</p>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>
  
  
  
<script>

import LoginTab from '../components/loginTab.vue';

export default {
  name: "HelloWorld",
  components: {
    LoginTab,
  },
  data() {
    return {
      title: "基于Spring Boot的通用问卷调查系统",
      copyright: "© 2023 问卷系统"
    };
  },
  methods: {

  },
  created() {
    //sessionStorage.setItem("authError", "true");
    if (sessionStorage.getItem("authError") == "true") {
      sessionStorage.setItem("authError", "false");
      this.$notify({
        title: '提示',
        message: '登录过期，请重新登陆',
        duration: 0
      });
    }
  }
};
</script>

<style scoped>
.app-intro {
  background-color: #f2f2f2;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.header {
  color: #fff;
  background-color: #409eff;
  text-align: center;
  font-size: 24px;
  padding: 20px;
  height: 450px !important;
  position: relative;
}

.main {
  padding: 20px;
}

.feature {
  text-align: center;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
}

.feature i {
  font-size: 60px;
  color: #409eff;
}

.feature h2 {
  font-size: 20px;
  margin: 10px 0;
}

.footer {
  text-align: center;
  color: #999;
}

.custom-shape-divider-bottom-1681374828 {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  overflow: hidden;
  line-height: 0;
  transform: rotate(180deg);
}

.custom-shape-divider-bottom-1681374828 svg {
  position: relative;
  display: block;
  width: calc(100% + 1.3px);
  height: 150px;
}

.custom-shape-divider-bottom-1681374828 .shape-fill {
  fill: #F2F2F2;
}
</style>
<template>
  <div class="dashboard">
    <div class="home-card">
      <div class="home-card-body">
        <h3 class="home-card-title">问卷数量</h3>
        <p class="home-card-text">{{ questionnaireCount }}</p>
      </div>
    </div>
    <div class="home-card">
      <div class="home-card-body">
        <h3 class="home-card-title">问题数量</h3>
        <p class="home-card-text">{{ questionCount }}</p>
      </div>
    </div>
    <div class="home-card">
      <div class="home-card-body">
        <h3 class="home-card-title">回收数量</h3>
        <p class="home-card-text">{{ responseCount }}</p>
      </div>
    </div>
  </div>
</template>
  
<script>
import { getStatisticsDataApi } from '@/api/questionnaire.js';

export default {
  data() {
    return {
      questionnaireCount: 0,
      questionCount: 0,
      responseCount: 0
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      // 从后端获取数据并赋值给对应的变量
      getStatisticsDataApi().then(res => {
        this.questionnaireCount = res.data.data.questionnaireNum
        this.questionCount = res.data.data.questionNum
        this.responseCount = res.data.data.answerNum
      })
    }
  }
}
</script>
  
<style>
.dashboard {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.home-card {
  width: 30%;
  margin-bottom: 20px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.home-card-body {
  padding: 20px;
  text-align: center;
}

.home-card-title {
  margin-bottom: 10px;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.home-card-text {
  font-size: 36px;
  font-weight: 600;
  color: #409EFF;
}</style>
  
<template>
  <div class="questionnaire-list">
    <el-row>
      <el-col v-for="(item, index) in surveys" :key="index" :span="6">
        <el-card class="card">
          <div class="card-content">
            <h3 class="card-title">{{ item.title }}</h3>
            <p class="card-desc">{{ item.description }}</p>
            <p class="card-time">{{ transTimeFormat(item.startTime) }}</p>
            <el-button class="card-button" type="primary" size="medium"
              @click="startQuestionnaire(item.id)">编辑</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
  
<script>
export default {
  props: {
    questionnaires: {
      type: Array,
      required: true
    }
  },
  methods: {
    startQuestionnaire(id) {
      this.$emit('start-questionnaire', id)
    },
    transTimeFormat(time) {
      if(time === null)
        return '未开始'
      else{
        // 2020-05-01T00:00:00.000Z
        // 2020-05-01 00:00:00
        let date = new Date(time)
        let year = date.getFullYear()
        let month = date.getMonth() + 1
        let day = date.getDate()
        let hour = date.getHours()
        let minute = date.getMinutes()
        let second = date.getSeconds()
        return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second
      }
      
    }

  },
  data() {
    return {
      surveys: this.questionnaires,
    }
  },
  watch: {
    questionnaires: {
      handler: function (newVal, oldVal) {
        this.surveys = newVal
      },
      deep: true
    }
  },
  

}
</script>
  
<style scoped>
.questionnaire-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  /* 每列最小280px，自动填满 */
  grid-gap: 20px;
  /* 卡片之间的间距 */
}

.card {
  margin-bottom: 20px;
  width: 100%;
}

.card-content {
  text-align: center;
}

.card-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.card-desc {
  margin-bottom: 10px;
}

.card-time {
  margin-bottom: 20px;
}

.card-button {
  margin-bottom: 10px;
}
</style>
  
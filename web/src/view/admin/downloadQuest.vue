<template>
    <div class="outer-container">
        <div class="wrapper">
            <div class="top-bar">
                <el-select v-model="selectedQuestionnaire" placeholder="请选择" @change="selectQuestionnaire"
                    style="width: 400px;">
                    <el-option v-for="(item, index) in questionnaireList" :key="index"
                        :label="item.title + '(id:' + item.id + ')'" :value="item.id">
                    </el-option>
                </el-select>
                <el-button type="primary" @click="refresh">刷新</el-button>
                <el-button type="primary" @click="download">导出到Excel</el-button>
            </div>
            <div class="card-container">

                <el-card shadow="always" class="card">
                    <h2>{{ questionnaireTitle }}</h2>
                    <h4>{{ questionnaireDescription }}</h4>
                </el-card>

                <el-divider></el-divider>

                <el-card v-for="(question, index) in questions" :key="index" shadow="always" class="card">

                    <h3>{{ question.questionContext }}</h3>
                    <el-divider></el-divider>
                    <div v-for="(option, index) in question.report" :key="index">
                        <h4>{{ option.optionContext }}</h4>
                        <el-progress :percentage="option.percentage"></el-progress>
                    </div>
                </el-card>

            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { listQuestionnaireApi, getQuestionnaireReportApi } from "@/api/questionnaire.js";

export default {
    data() {
        return {
            selectedQuestionnaire: "",
            questionnaireList: [],
            questionnaireTitle: "",
            questionnaireDescription: "选择一张问卷",
            questions: [],

        };
    },
    mounted() {
        this.fetchQuestionnaires();
    },
    methods: {
        // 获取问卷列表
        fetchQuestionnaires() {
            listQuestionnaireApi().then((res) => {
                if (res.data.code == 200) {
                    this.questionnaireList = res.data.data;
                } else {
                    this.$message({
                        message: "获取问卷列表失败",
                        type: "error",
                        showClose: true,
                    });
                }
            });
        },
        // 选择问卷
        selectQuestionnaire() {
            console.log(this.selectedQuestionnaire);
            getQuestionnaireReportApi(this.selectedQuestionnaire).then((res) => {
                if (res.data.code == 200) {
                    console.log(res.data.data);
                    //从selectedQuestionnaire获取问卷标题和描述
                    this.questionnaireTitle = this.questionnaireList.find((item) => item.id == this.selectedQuestionnaire).title;
                    this.questionnaireDescription = this.questionnaireList.find((item) => item.id == this.selectedQuestionnaire).description;
                    this.questions = res.data.data;
                    //反序列化
                    for (let i = 0; i < this.questions.length; i++) {
                        this.questions[i].report = JSON.parse(this.questions[i].report);
                    }
                    //计算百分比
                    for (let i = 0; i < this.questions.length; i++) {
                        let sum = 0;
                        for (let j = 0; j < this.questions[i].report.length; j++) {
                            sum += this.questions[i].report[j].optionCount;
                        }
                        for (let j = 0; j < this.questions[i].report.length; j++) {
                            let percentage = this.questions[i].report[j].optionCount / sum * 100;
                            this.questions[i].report[j].percentage = sum == 0 ? 0 : parseFloat(percentage.toFixed(2));
                        }
                    }

                } else {
                    this.$message({
                        message: "获取问卷报告失败",
                        type: "error",
                        showClose: true,
                    });
                }
            });
        },
        // 刷新
        refresh() {
            this.fetchQuestionnaires();
            this.selectQuestionnaire();
        },
        // 导出到Excel
        download() {
            if (this.selectedQuestionnaire == "") {
                this.$message({
                    message: "请选择问卷",
                    type: "error",
                    showClose: true,
                });
                return;
            }
            //获取当前域名
            let baseUrl = window.location.href.split('#')[0];
            let url = "http://localhost:8880/api/v1/questionnaire/downloadQuestionnaire/" + this.selectedQuestionnaire;

            const token = localStorage.getItem('token'); // 从localStorage中获取token

            axios({
                url: url,
                method: 'GET',
                responseType: 'blob', // 设置响应类型为 blob
                headers: {
                    'Authorization': `Bearer ${token}`, // 在请求头中携带 token
                },
            }).then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', this.selectedQuestionnaire + '_导出记录.xlsx'); // 设置文件名
                document.body.appendChild(link);
                link.click();
            });

        }
    }
}

</script>

<style>
/* 外层container */
.outer-container {
    width: 100%;
    max-height: calc(100vh - 60px);
    background-color: rgb(247, 247, 247);
    overflow-y: auto;
    overflow-x: hidden;
}

/* 内层wrapper */
.wrapper {
    background-color: rgb(247, 247, 247);
    padding: 20px;
}

/* 顶部搜索栏 */
.top-bar {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 20px;
}

/* 卡片容器 */
.card-container {
    width: 800px;
    margin: 0 auto;
}

/* 卡片 */
.card {
    margin-bottom: 20px;
}
</style>
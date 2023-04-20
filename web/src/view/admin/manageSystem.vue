<template>
    <el-table :data="questionnaires" style="width: 100%">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="startTime" label="开始时间" :formatter="formatStartTime"></el-table-column>
        <el-table-column prop="endTime" label="结束时间" :formatter="formatEndTime"></el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button type="danger" size="mini" @click="deleteQuestionnaire(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>
  
<script>
import { listQuestionnaireApi ,deleteQuestionnaireApi} from "@/api/questionnaire.js";

export default {
    data() {
        return {
            questionnaires: [],
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
                    this.questionnaires = res.data.data;
                } else {
                    this.$message({
                        message: "获取问卷列表失败",
                        type: "error",
                        showClose: true,
                    });
                }
            });
        },
        // 删除问卷
        deleteQuestionnaire(questionnaireId) {
            console.log(questionnaireId);
            deleteQuestionnaireApi(questionnaireId).then((res) => {
                if (res.data.code == 200) {
                    this.$message({
                        message: "删除成功",
                        type: "success",
                        showClose: true,
                    });
                    this.fetchQuestionnaires();
                } else {
                    this.$message({
                        message: "删除失败",
                        type: "error",
                        showClose: true,
                    });
                }
            });
        },
        // 格式化开始时间
        formatStartTime(time) {
            if (time.startTime == null) return "未设置";
            let date = new Date(time.startTime);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds();
            return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        },
        // 格式化结束时间
        formatEndTime(time) {
            if (time.endTime == null) return "未设置";
            let date = new Date(time.endTime);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds();
            return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        },
    },
};
</script>
  
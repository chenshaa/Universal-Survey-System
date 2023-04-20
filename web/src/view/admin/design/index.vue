<template>
    <div style="height: 100%; overflow-y: auto;">
        <div id="context">
            <el-empty description="创建一张新问卷">
                <el-button type="primary" @click="creratNew">创建</el-button>
            </el-empty>
            <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
            <h3>或者，从已经创建的问卷开始</h3>
            <QuestionnaireList :questionnaires="surveys" @start-questionnaire="startQuestionnaire($event)">
            </QuestionnaireList>
        </div>
    </div>
</template>

<script>
import QuestionnaireList from '@/components/QuestionnaireList.vue';
import { listQuestionnaireApi } from "@/api/questionnaire.js";

export default {
    name: 'Empty',
    data() {
        return {
            surveys: [],
        };
    },
    methods: {
        creratNew() {
            this.$router.push("/admin/designQuest/design/" + "new");
        },
        startQuestionnaire(id) {
            this.$router.push("/admin/designQuest/design/" + id);
        }

    },
    components: {
        QuestionnaireList
    },
    created() {
        let that = this;
        listQuestionnaireApi().then((res) => {
            if (res.data.code == 200) {
                that.surveys = res.data.data;
            } else {
                this.$message({
                    message: "获取问卷列表失败",
                    type: "error",
                    showClose: true,
                });
            }
        });
    }
};
</script>

<style scoped>
#context {
    overflow-x: hidden;
}
</style>
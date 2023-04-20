<template >
    <div class="background">
        <div class="questionnaire-wrapper" :style="wrapperStyle" v-loading="pageStatue==-1">
            <!--问卷box-->
            <Questionnaire v-if="pageStatue == 1" class="questionnaire" :_title="title" :_description="description"
                :_questions="questions" :_edit="edit" @submit="submitHandler(questions)" />
            <!--问卷完成box-->
            <el-empty v-if="pageStatue == 2" description=" " :image="require('@/assets/success.png')" :image-size="300">
                <h3 class="des">问卷已提交</h3>
            </el-empty>
            <!--问卷不存在box-->
            <el-empty v-if="pageStatue == 0" description=" " >
                <h3 class="des">问卷不存在</h3>
                <h3 class="des">或者，登陆后重试</h3>
                <el-button type="primary" @click="loginButton">登录</el-button>
            </el-empty>
        </div>
    </div>
</template>
  
<script>
import Questionnaire from '@/components/Questionnaire.vue';

import { getQuestionnaireDetailApi, uploadAnswerApi } from '@/api/questionnaire.js';

export default {
    data() {
        return {
            isDesktop: false,
            title: "未命名问卷",
            description: "这是一个问卷描述",
            questions: [],
            id: this.$route.params.id,
            edit: false,
            pageStatue: -1,
        };
    },
    mounted() {
        this.checkDevice();
        window.addEventListener("resize", this.checkDevice);

        //获取问卷详情
        getQuestionnaireDetailApi(this.id).then((res) => {
            console.log(res);
            if(res.data.code == 200){
                this.pageStatue = 1;
            }else{
                this.pageStatue = 0;
            }
            this.title = res.data.data.title;
            this.description = res.data.data.description;
            this.questions = JSON.parse(res.data.data.questionList);
            //对option进行还原
            for (let i = 0; i < this.questions.length; i++) {
                this.questions[i].options = JSON.parse(this.questions[i].options);
                this.questions[i].userChoise = null
                if (this.questions[i].questionType == "2") {
                    if (this.questions[i].userChoise == null) {
                        this.questions[i].userChoise = [];
                    } else {
                        this.questions[i].userChoise = JSON.parse(this.questions[i].userChoise);
                    }
                }
            }
            console.log(this.questions);
        });

    },
    beforeDestroy() {
        window.removeEventListener("resize", this.checkDevice);
    },
    methods: {
        checkDevice() {
            this.isDesktop = window.innerWidth >= 768; // 768为移动设备和电脑的分界点
        },
        submitHandler(questions) {
            //必填项检查
            for (let i = 0; i < questions.length; i++) {
                if (questions[i].isRequired == "1") {
                    if (questions[i].userChoise === null || questions[i].userChoise === "") {
                        this.$message({
                            message: "请填写必填项: " + (i + 1) + "、" + questions[i].content,
                            type: "error",
                            showClose: true,
                        });
                        return;
                    }

                    //多选题检查
                    if (questions[i].questionType == "2") {
                        if (questions[i].userChoise.length == 0) {
                            this.$message({
                                message: "请填写必填项: " + (i + 1) + "、" + questions[i].content,
                                type: "error",
                                showClose: true,
                            });
                            return;
                        }
                    }
                }
            }
            //提交
            uploadAnswerApi({
                questionnaireId: this.id,
                optionContext: JSON.stringify(questions),
            }).then((res) => {
                console.log(res);
                if (res.data.code == 200) {
                    this.$message({
                        message: "提交成功",
                        type: "success",
                        showClose: true,
                    });
                    this.pageStatue = 2;
                } else {
                    this.$message({
                        message: "提交失败",
                        type: "error",
                        showClose: true,
                    });
                }
            });
        },
        loginButton() {
            this.$router.push("/login");
        },
    },
    components: {
        Questionnaire,
    },
    computed: {
        wrapperStyle() {
            return {
                maxWidth: "800px",
                margin: "0 auto",
                width: "100%",
            };
        },
    },
};
</script>
  
<style scoped>
.questionnaire-wrapper {
    overflow: auto;
    padding: 20px;
    box-sizing: border-box;
    min-height: 100vh;
}

.background {
    background-color: #f2f2f2;
}

.des {
    font-size: 20px;
    color: #666;
    font-weight: 500;
}
</style>
  
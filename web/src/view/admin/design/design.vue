<template>
    <div id="mainDiv">
        <el-header class="page-header">
            <div class="left">
                <el-button type="text" icon="el-icon-arrow-left"></el-button>
                <div class="title">编辑问卷</div>
            </div>
            <div class="right">
                <el-button type="primary" size="small" @click="saveQuestionnaire">保存</el-button>
            </div>
        </el-header>

        <el-row style="height: 100%; overflow: hidden; ">
            <el-col :span="5">
                <!---侧面板-->
                <el-collapse v-model="activeNames">
                    <el-collapse-item v-for="(group, index) in groups" :key="index" :title="group.title"
                        :name="'group-' + index">
                        <div class="group-wrapper">
                            <div v-for="(item, itemIndex) in group.items" :key="itemIndex" class="item"
                                @click="pannelClick(item.type)">
                                <div class="icon-wrapper">
                                    <SvgIcon :icon-class="'icon-' + item.type" />
                                </div>
                                <div class="text">{{ item.text }}</div>
                            </div>
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
            <el-col :span="19" style="height: 100%;">
                <div class="mainContainer">
                    <Questionnaire class="questionnaire" :_title="title" :_description="description" :_questions="questions"
                        :_edit="edit" @updateQuestions="updateQuestions($event)" @updateTitle="updateTitle($event)"
                        @updateDescription="updateDescription($event)" />
                </div>
            </el-col>
        </el-row>

    </div>
</template>

<script>
import SvgIcon from "@/components/SvgIcon";
import Questionnaire from "@/components/Questionnaire";

import { updateQuestionnaireApi, getQuestionnaireDetailApi } from "@/api/questionnaire.js";

export default {
    name: "design",
    components: {
        SvgIcon,
        Questionnaire
    },
    data() {
        return {
            groups: [
                {
                    title: '基础组件',
                    name: 'group-0',
                    items: [
                        { type: 'singleChoice', text: '单选' },
                        { type: 'multiChoice', text: '多选' },
                        { type: 'fillBlanks', text: '填空' },
                        { type: 'dropDown', text: '下拉' },
                        { type: 'textArea', text: '文本域' },
                    ]
                },
                {
                    title: '扩展组件',
                    name: 'group-1',
                    items: [
                        { iconClass: 'icon-user', text: 'User' },
                        { iconClass: 'icon-photo', text: 'Photos' },
                        { iconClass: 'icon-star', text: 'Favorites' },
                        { iconClass: 'icon-notebook', text: 'Notes' }
                    ]
                }
            ],
            activeNames: ['group-0'],
            title: "未命名问卷",
            description: "这是一个问卷描述",
            questions: [],
            id: "",
            edit: true,
        };
    },
    methods: {
        updateQuestions(questions) {
            this.questions = questions;
            //console.log("updateQuestions");
            //console.log(questions);
        },
        pannelClick(type) {
            if (type == "singleChoice") {
                this.questions.push({
                    questionType: "1",
                    content: "单选题",
                    isRequired: false,
                    options: ["默认选项"],
                    userChoise: "",
                });
            } else if (type == "multiChoice") {
                this.questions.push({
                    questionType: "2",
                    content: "多选题",
                    isRequired: false,
                    options: ["默认选项"],
                    userChoise: [],
                });
            } else if (type == "fillBlanks") {
                this.questions.push({
                    questionType: "3",
                    content: "填空题",
                    isRequired: false,
                    userChoise: "",
                });
            } else if (type == "dropDown") {
                this.questions.push({
                    questionType: "4",
                    content: "下拉题",
                    isRequired: false,
                    options: ["默认选项"],
                    userChoise: "",
                });
            } else if (type == "textArea") {
                this.questions.push({
                    questionType: "5",
                    content: "文本域题",
                    isRequired: false,
                    userChoise: "",
                });
            }
        },
        saveQuestionnaire() {
            updateQuestionnaireApi({
                title: this.title,
                description: this.description,
                questionList: JSON.stringify(this.questions),
                id: this.id,
            }).then((res) => {
                console.log(res);
                if (res.data.code == 200) {
                    this.id = res.data.data;
                    this.$message({
                        message: '保存成功',
                        type: 'success'
                    });
                } else {
                    this.$message.error(res.data.msg);
                }

            });
        },
        updateTitle(title) {
            this.title = title;
        },
        updateDescription(description) {
            this.description = description;
        },
    },
    created: function () {
        let that = this;
        if (this.$route.params.id == "new") {
            console.log("新建问卷");
        } else {
            console.log("编辑问卷:" + this.$route.params.id);
            this.id = this.$route.params.id;
            getQuestionnaireDetailApi(this.id).then((res) => {
                console.log(res);
                that.title = res.data.data.title;
                that.description = res.data.data.description;
                that.questions = JSON.parse(res.data.data.questionList);
                //对option进行还原
                for (let i = 0; i < that.questions.length; i++) {
                    that.questions[i].options = JSON.parse(that.questions[i].options);
                    if (that.questions[i].questionType == "2") {
                        if (that.questions[i].userChoise == null) {
                            that.questions[i].userChoise = [];
                        } else {
                            that.questions[i].userChoise = JSON.parse(that.questions[i].userChoise);
                        }
                    }
                }
                console.log(that.questions);
            });
        }

    },
};
</script>


<style scoped>
.group-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}

.item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 90px;
    height: 85px;
    margin-right: 20px;
    margin-bottom: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    cursor: pointer;
}

.icon-wrapper {
    width: 48px;
    height: 48px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.text {
    font-size: 14px;
    text-align: center;
    color: #333;
}

.mainContainer {
    width: 100%;
    height: calc(100vh - 195px);
    background-color: rgb(189, 189, 189);
    overflow-y: auto;
    overflow-x: hidden;
    padding-top: 30px;
    padding-bottom: 60px;
}

.questionnaire {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
}

.item {
    /* ... */
    transition: all 0.2s ease-in-out;
}

.text {
    transition: all 0.2s ease-in-out;
}

.item:hover {
    background-color: rgb(236, 245, 255);
    color: aqua;
}

.item:hover .text {
    color: red;
}


.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 24px;
    background-color: #fff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.left {
    display: flex;
    align-items: center;
}

.title {
    margin-left: 12px;
    font-size: 18px;
    font-weight: bold;
}

.right {
    display: flex;
    align-items: center;
}
</style>
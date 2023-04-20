<template>
    <div class="questionnaire">

        <!---tittle-->
        <div @mouseover="edit ? isHovering = -2 : null" @mouseleave="edit ? isHovering = -1 : null">
            <h2 class="title">{{ title }}</h2>
            <p class="description">{{ description }}</p>

            <!--编辑标题描述按钮-->
            <div class="hover-buttons" v-if="edit">
                <el-button-group class="hover-button" v-if="isHovering == -2">
                    <el-button size="mini" icon="el-icon-edit" @click="editTittle">编辑</el-button>
                </el-button-group>
            </div>
        </div>

        <el-divider></el-divider>
        <!---question-->
        <div class="question-list">
            <div v-for="(question, index) in questions" :key="index" class="question"
                @mouseover="edit ? isHovering = index : null" @mouseleave="edit ? isHovering = -1 : null">
                <div class="question-wrapper">
                    <span v-if="question.isRequired == '1'" class="question-required">*</span>
                    <span v-else class="question-required" style="color: #FFF;">&nbsp;</span>
                    <div class="question-index">{{ index + 1 + "." }}</div>
                    <div class="question-title">{{ question.content }}</div>
                </div>

                <!--单选-->
                <div v-if="question.questionType == '1'" class="options">
                    <div class="option" v-for="(option, optionIndex) in question.options" :key="optionIndex">
                        <input type="radio" :id="`q_${index}_o_${optionIndex}`" :value="optionIndex"
                            v-model="question.userChoise" class="ratio">
                        <label :for="`q_${index}_o_${optionIndex}`" class="option-label">{{ option }}</label>
                    </div>
                </div>
                <!--多选-->
                <div v-if="question.questionType == '2'" class="options">
                    <div class="option" v-for="(option, optionIndex) in question.options" :key="optionIndex">
                        <input type="checkbox" :id="`q_${index}_o_${optionIndex}`" :value="optionIndex"
                            v-model="question.userChoise" class="checkbox">
                        <label :for="`q_${index}_o_${optionIndex}`" class="option-label">{{ option }}</label>
                    </div>
                </div>

                <!--填空-->
                <div v-if="question.questionType == '3'" class="options">
                    <el-input v-model="question.userChoise" class="option" placeholder="请输入内容"></el-input>
                </div>

                <!--下拉单选-->
                <div v-if="question.questionType == '4'" class="options">
                    <el-select v-model="question.userChoise" placeholder="请选择">
                        <el-option v-for="(option, optionIndex) in question.options" :key="optionIndex" :label="option"
                            :value="optionIndex"></el-option>
                    </el-select>
                </div>

                <!--文本域-->
                <div v-if="question.questionType == '5'" class="options">
                    <el-input type="textarea" v-model="question.userChoise" class="option" placeholder="请输入内容"></el-input>
                </div>

                <!---控制按钮-->
                <div class="hover-buttons" v-if="onEdit != index">

                    <el-button-group class="hover-button" v-if="isHovering == index">
                        <el-button size="mini" icon="el-icon-upload2" @click="hover2First(index)">移至最前</el-button>
                        <el-button size="mini" icon="el-icon-download" @click="hover2Last(index)">移至最后</el-button>
                    </el-button-group>

                    <el-button-group class="hover-button" v-if="isHovering == index">
                        <el-button size="mini" icon="el-icon-arrow-up" @click="hoverUp(index)">上移</el-button>
                        <el-button size="mini" icon="el-icon-arrow-down" @click="hoverDown(index)">下移</el-button>
                    </el-button-group>

                    <el-button-group class="hover-button" v-if="isHovering == index">
                        <el-button size="mini" icon="el-icon-edit" @click="hoverEdit(index)">编辑</el-button>
                        <el-button size="mini" icon="el-icon-document-copy" @click="hoverCopy(index)">复制</el-button>
                        <el-button size="mini" icon="el-icon-delete" @click="hoverDelete(index)">删除</el-button>
                    </el-button-group>

                </div>

                <!--编辑器-->
                <div class="editor-wrapper" v-if="onEdit == index">
                    <el-card class="editor-card">
                        <div class="editor-content">
                            <div class="textarea-wrapper">
                                <el-input type="textarea" :rows="5" placeholder="请输入文本" :value="getItemContent(index)"
                                    @input="updateItemContent(index, $event)"></el-input>
                            </div>
                            <div class="options-wrapper">

                                <el-table :data="tableData" class="editor-table" border>

                                    <el-table-column prop="option" label="选项">
                                        <template slot-scope="{ row }">
                                            <el-input :value="getItemOption(index, row.option)"
                                                @input="updateItemOption(index, row.option, $event)"></el-input>
                                        </template>
                                    </el-table-column>

                                    <el-table-column label="添加删除">
                                        <template slot-scope="{ row }">
                                            <el-button-group>
                                                <el-button icon="el-icon-plus"
                                                    @click="editor_add(index, row.option)"></el-button>
                                                <el-button icon="el-icon-minus"
                                                    @click="editor_delete(index, row.option)"></el-button>
                                            </el-button-group>
                                        </template>
                                    </el-table-column>


                                    <el-table-column label="上移下移">
                                        <template slot-scope="{ row }">
                                            <el-button-group>
                                                <el-button icon="el-icon-arrow-up"
                                                    @click="editor_up(index, row.option)"></el-button>
                                                <el-button icon="el-icon-arrow-down"
                                                    @click="editor_down(index, row.option)"></el-button>
                                            </el-button-group>
                                        </template>
                                    </el-table-column>

                                </el-table>

                            </div>

                            <div class="editor-options-wrapper">
                                <el-checkbox :value="getItemIsRequired(index)"
                                    @input="updateItemIsRequired(index, $event)">是否必填</el-checkbox>
                            </div>

                            <div class="submit-button-wrapper">
                                <el-button type="primary" class="submit-button" @click="editor_Submit">
                                    确认编辑
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

            </div>
        </div>

        <!---submit-->
        <div class="submit-container">
            <el-button type="primary" @click="SubmitButton">提交</el-button>
        </div>

        <!---编辑标题dialog-->
        <el-dialog :visible.sync="editTittleDialogVisible" title="编辑">
            <el-form label-width="100px">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="editTittleForm.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input type="textarea" v-model="editTittleForm.description" placeholder="请输入描述"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editTittleDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="editTittleSubmit">确定</el-button>
            </span>
        </el-dialog>

    </div>
</template>
  
<script>
export default {
    name: "Questionnaire",
    computed: {
        getItemContent() {
            return (itemIndex) => this.questions[itemIndex].content;
        },
        getItemOption() {
            return (itemIndex, optionItem) => this.questions[itemIndex].options[optionItem];
        },
        getItemIsRequired() {
            return (itemIndex) => this.questions[itemIndex].isRequired;
        }
    },
    methods: {
        //---------update方法---------
        updateItemContent(index, value) {
            //this.questions[index].content = value;
            //使用set方法强制更新
            this.$set(this.questions[index], 'content', value);
        },
        updateItemOption(itemIndex, optionItem, value) {
            //this.questions[itemIndex].options[optionItem] = value;
            //使用set方法强制更新
            this.$set(this.questions[itemIndex].options, optionItem, value);
            //强制更新
            this.tableData = this.questions[itemIndex].options.map((option, index) => ({ option: index }));
        },
        updateItemIsRequired(index, value) {
            //this.questions[index].isRequired = value;
            //使用set方法强制更新
            this.$set(this.questions[index], 'isRequired', value);
        },

        //---------hover方法---------
        hoverEdit(id) {
            //编辑器显示
            this.onEdit = id;
            //map(option => ({ option }));
            this.tableData = this.questions[id].options.map((option, index) => ({ option: index }));
            console.log(this.tableData);
        },
        hoverCopy(itemIndex) {
            //在index后插入一项
            this.questions.splice(itemIndex + 1, 0, this.questions[itemIndex]);
        },
        hoverDelete(itemIndex) {
            //删除index项
            this.questions.splice(itemIndex, 1);
        },
        hoverUp(itemIndex) {
            //上移
            if (itemIndex == 0) {
                this.$message({
                    message: '已经是第一项',
                    type: 'warning'
                });
                return;
            }
            this.questions.splice(itemIndex - 1, 0, this.questions[itemIndex]);
            this.questions.splice(itemIndex + 1, 1);
        },
        hoverDown(itemIndex) {
            //下移
            if (itemIndex == this.questions.length - 1) {
                this.$message({
                    message: '已经是最后一项',
                    type: 'warning'
                });
                return;
            }
            this.questions.splice(itemIndex + 2, 0, this.questions[itemIndex]);
            this.questions.splice(itemIndex, 1);
        },
        hover2First(itemIndex) {
            if (itemIndex == 0) {
                this.$message({
                    message: '已经是第一项',
                    type: 'warning'
                });
                return;
            }
            //移动到第一项
            this.questions.splice(0, 0, this.questions[itemIndex]);
            this.questions.splice(itemIndex + 1, 1);
        },
        hover2Last(itemIndex) {
            if (itemIndex == this.questions.length - 1) {
                this.$message({
                    message: '已经是最后一项',
                    type: 'warning'
                });
                return;
            }
            //移动到最后一项
            this.questions.splice(this.questions.length, 0, this.questions[itemIndex]);
            this.questions.splice(itemIndex, 1);
        },

        //---------editor方法---------
        editor_add(itemIndex, optionIndex) {
            //questions[itemIndex].options插入项目
            this.questions[itemIndex].options.splice(optionIndex + 1, 0, '新选项');
            //tableData刷新
            this.tableData = this.questions[itemIndex].options.map((option, index) => ({ option: index }));
        },
        editor_delete(itemIndex, optionIndex) {
            if (this.questions[itemIndex].options.length == 1) {
                this.$message({
                    message: '至少保留一个选项',
                    type: 'warning'
                });
                return;
            }
            //questions[itemIndex].options删除项目
            this.questions[itemIndex].options.splice(optionIndex, 1);
            //tableData刷新
            this.tableData = this.questions[itemIndex].options.map((option, index) => ({ option: index }));
        },
        editor_up(itemIndex, optionIndex) {
            if (optionIndex == 0) {
                this.$message({
                    message: '已经是第一个选项',
                    type: 'warning'
                });
                return;
            }
            //questions[itemIndex].options交换项目
            let temp = this.questions[itemIndex].options[optionIndex];
            this.questions[itemIndex].options[optionIndex] = this.questions[itemIndex].options[optionIndex - 1];
            this.questions[itemIndex].options[optionIndex - 1] = temp;
            //tableData刷新
            this.tableData = this.questions[itemIndex].options.map((option, index) => ({ option: index }));
        },
        editor_down(itemIndex, optionIndex) {
            if (optionIndex == this.questions[itemIndex].options.length - 1) {
                this.$message({
                    message: '已经是最后一个选项',
                    type: 'warning'
                });
                return;
            }
            //questions[itemIndex].options交换项目
            let temp = this.questions[itemIndex].options[optionIndex];
            this.questions[itemIndex].options[optionIndex] = this.questions[itemIndex].options[optionIndex + 1];
            this.questions[itemIndex].options[optionIndex + 1] = temp;
            //tableData刷新
            this.tableData = this.questions[itemIndex].options.map((option, index) => ({ option: index }));
        },
        editor_Submit() {
            //关闭窗口
            this.onEdit = -1;
        },

        //---------题目编辑窗口---------
        editTittle() {
            this.editTittleForm.title = this.title;
            this.editTittleForm.description = this.description;
            this.editTittleDialogVisible = true;
        },
        editTittleSubmit() {
            this.title = this.editTittleForm.title;
            this.description = this.editTittleForm.description;
            this.editTittleDialogVisible = false;
        },
        //---------提交按钮---------
        SubmitButton() {
            this.$emit('submit', this.questions);
        }
    },
    props: {
        _questions: {
            type: Array,
            default: () => []
        },
        _title: {
            type: String,
            default: ''
        },
        _description: {
            type: String,
            default: ''
        },
        _edit: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            isHovering: -1,//鼠标悬停的题目
            onEdit: -1,//正在编辑的题目
            tableData: [], // 表格数据
            questions: this._questions,
            title: this._title,
            description: this._description,
            edit: this._edit,
            editTittleDialogVisible: false,
            editTittleForm: {
                title: this._title,
                description: this._description
            },
        }
    },
    watch: {
        'questions': {
            handler(newVal, oldVal) {
                this.$emit('updateQuestions', newVal);
            },
            deep: true
        },
        'title': {
            handler(newVal, oldVal) {
                this.$emit('updateTitle', newVal);
            },
        },
        'description': {
            handler(newVal, oldVal) {
                this.$emit('updateDescription', newVal);
            },
        },
        '_title': {
            handler(newVal, oldVal) {
                this.title = newVal;
            },
        },
        '_description': {
            handler(newVal, oldVal) {
                this.description = newVal;
            },
        },
        '_questions': {
            handler(newVal, oldVal) {
                this.questions = newVal;
            },
        },
    }
};
</script>
  
<style scoped>
/* 问卷 */
.questionnaire {
    max-width: 800px;
    margin: 0 auto;
    background-color: #fff;
    padding: 100px 80px;

}

/* 问卷标题 */
.title {
    font-size: 32px;
    margin-bottom: 20px;
    text-align: center;
}

/* 问卷描述 */
.description {
    font-size: 18px;
    margin-bottom: 40px;
}


/* -------问卷题目--------- */
/* 题目部分wrapper */
.question-wrapper {
    display: flex;
    align-items: center;
    /* 垂直居中对齐 */
    gap: 10px;
    /* 间距 */
    margin-bottom: 20px;
}

/* 题目 */
.question {
    margin-bottom: 40px;
}

/* 题目序号 */
.question-index {
    font-size: 24px;
}

/* 题目必答 */
.question-required {
    color: red;
    margin: 0;
}

/* 答题选项 */
.option {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
}

/* 答题选项输入 */
.option input {
    margin-right: 8px;
}

/* 答题选项label */
.option-label {
    font-size: 16px;
    color: #333;
}

/* 答题编辑hover */
.hover-buttons {
    height: 50px;
    /* 占据高度 */
}

.hover-button {
    float: right;
    margin-left: 10px;
    /* 按钮向右对齐 */
}

/* -------编辑器--------- */
/* 编辑器card */
.editor-card {
    width: 100%;
    border: 1px solid #909399;
}

/* 编辑器保存按钮wrapper */
.submit-button-wrapper {
    text-align: center;
    margin-top: 20px;
}

/* 编辑器保存按钮 */
.submit-button {
    width: 100%;
}


/* -------编辑按钮--------- */
.submit-container {
    text-align: center;
}
</style>
  
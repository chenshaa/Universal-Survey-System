<template>
    <div>
        <div class="toolbar">
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
        </div>
        <el-table :data="users" style="width: 100%">
            <el-table-column prop="username" label="姓名"></el-table-column>
            <el-table-column prop="account" label="账号"></el-table-column>
            <el-table-column prop="role" label="角色">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.role === '用户' ? 'success' : 'warning'" disable-transitions>
                        {{ scope.row.role }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="ban" label="状态">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.ban === '正常' ? 'success' : 'danger'" disable-transitions>
                        {{ scope.row.ban }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="text" icon="el-icon-circle-close"
                        @click="handleDelete(scope.$index, scope.row)">禁用</el-button>
                    <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index)">编辑</el-button>
                    <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.$index)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div>
            <!---添加用户dialog-->
            <el-dialog title="用户信息" :visible.sync="addUserFormVisible" width="30%">
                <el-form label-width="80px" size="small">

                    <el-form-item label="姓名">
                        <el-input v-model="addUserForm.username" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="账号">
                        <el-input v-model="addUserForm.account" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="密码">
                        <el-input v-model="addUserForm.password" autocomplete="off" show-password></el-input>
                    </el-form-item>

                    <el-form-item label="确认密码">
                        <el-input v-model="addUserForm.password2" autocomplete="off" show-password></el-input>
                    </el-form-item>

                    <el-form-item label="角色">
                        <el-select v-model="addUserForm.role" placeholder="请选择角色" style="width: 100%">
                            <el-option :label="'用户'" :value="0"></el-option>
                            <el-option :label="'管理员'" :value="1"></el-option>
                        </el-select>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="addUserFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addUser">确 定</el-button>
                </div>
            </el-dialog>
        </div>

        <!--编辑用户dialog-->
        <div>
            <el-dialog title="编辑用户信息" :visible.sync="editUserFormVisible" width="50%">
                <el-form label-width="80px" size="small">

                    <el-form-item label="姓名">
                        <el-input v-model="editUserForm.username" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="账号">
                        <el-input v-model="editUserForm.account" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="密码">
                        <el-input v-model="editUserForm.password" autocomplete="off" show-password></el-input>
                    </el-form-item>

                    <el-form-item label="确认密码">
                        <el-input v-model="editUserForm.password2" autocomplete="off" show-password></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="editUserFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="editUser">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
  
<script>

import { listUserApi, addUserApi, deleteUserApi ,updateUserApi} from '@/api/user.js';

export default {
    data() {
        return {
            users: [],
            rawUsers: [],
            addUserFormVisible: false,
            addUserForm: {
                username: '',
                account: '',
                password: '',
                password2: '',
                role: '',
            },
            editUserFormVisible: false,
            editUserForm: {
                username: '',
                account: '',
                password: '',
                password2: '',
                role: '',
            },
        }
    },
    methods: {
        handleAdd() {
            // 置空
            this.addUserForm = {
                username: '',
                account: '',
                password: '',
                password2: '',
                role: '',
            }
            this.addUserFormVisible = true;
        },
        handleEdit(index) {
            // 处理编辑操作
            this.editUserForm = this.rawUsers[index];
            this.editUserFormVisible = true;
        },
        handleDelete(index) {
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteUserApi(this.users[index].id).then(res => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.users = res.data.data;
                    this.rawUsers = res.data.data;
                    this.FormatDate();
                }).catch(err => {
                    this.$message({
                        type: 'error',
                        message: '删除失败!'
                    });
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        FormatDate() {
            for (let i = 0; i < this.users.length; i++) {
                if (this.users[i].ban == 0 || this.users[i].ban == null) {
                    this.users[i].ban = "正常";
                } else {
                    this.users[i].ban = "禁用";
                }

                if (this.users[i].role == 0) {
                    this.users[i].role = "用户";
                } else {
                    this.users[i].role = "管理员";
                }
            }
        },
        addUser() {
            console.log(this.addUserForm.role);
            if (this.addUserForm.username == '' || this.addUserForm.account == '' || this.addUserForm.password == '' || this.addUserForm.password2 == '' || this.addUserForm.role === '') {
                this.$message({
                    message: '请填写完整信息',
                    type: 'error'
                });
                return;
            }
            if (this.addUserForm.password != this.addUserForm.password2) {
                this.$message({
                    message: '两次密码不一致',
                    type: 'error'
                });
                return;
            }

            // 添加用户
            addUserApi(this.addUserForm).then(res => {
                if (res.data.code == 200) {
                    this.$message({
                        message: '添加成功',
                        type: 'success'
                    });
                    this.users = res.data.data;
                    this.rawUsers = res.data.data;
                    this.FormatDate();
                    this.addUserFormVisible = false;
                } else {
                    this.$message({
                        message: res.data.msg,
                        type: 'error'
                    });
                }
            })
        },
        editUser() {
            // 编辑用户
            updateUserApi(this.editUserForm).then(res => {
                if (res.data.code == 200) {
                    this.$message({
                        message: '编辑成功',
                        type: 'success'
                    });
                    this.users = res.data.data;
                    this.rawUsers = res.data.data;
                    this.FormatDate();
                    this.editUserFormVisible = false;
                } else {
                    this.$message({
                        message: res.data.msg,
                        type: 'error'
                    });
                }
            })
        },
    },
    created() {
        listUserApi().then(res => {
            this.users = res.data.data;
            this.rawUsers = res.data.data;
            this.FormatDate();
        })
    }
}
</script>
  
<style>
.toolbar {
    margin-top: 30px;
    margin-bottom: 20px;
}
</style>
  
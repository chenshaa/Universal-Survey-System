<template>
    <el-container style="height: 100vh">

        <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%); overflow: hidden;">
            <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
        </el-aside>

        <el-container class="content">
            <el-header style="border-bottom: 1px solid #ccc;">
                <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user" />
            </el-header>

            <el-main style="padding-right: 0;padding-bottom: 0; padding-top: 0;">
                <div class="router-view-wrapper">
                    <router-view name="adminView" class="router-view" />
                </div>
            </el-main>

        </el-container>
    </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
    name: 'Home',
    data() {
        return {
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            user: {}
        }
    },
    components: {
        Aside,
        Header
    },
    created() {
        // 从后台获取最新的User数据

    },
    methods: {
        collapse() { // 点击收缩按钮触发
            this.isCollapse = !this.isCollapse
            if (this.isCollapse) { // 收缩
                this.sideWidth = 64
                this.collapseBtnClass = 'el-icon-s-unfold'
                this.logoTextShow = false
            } else { // 展开
                this.sideWidth = 200
                this.collapseBtnClass = 'el-icon-s-fold'
                this.logoTextShow = true
            }
        },
        getUser() {
            // let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
            // if (username) {
            //     // 从后台获取User数据
            //     this.request.get("/user/username/" + username).then(res => {
            //         // 重新赋值后台的最新User数据
            //         this.user = res.data
            //     })
            // }
        }
    }
}
</script>

<style>
.router-view-wrapper {
    height: 100%;
    overflow: hidden;

}

.content {
    height: 100vh;
    max-height: 100vh;
}
</style>
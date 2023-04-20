<template>
    <el-menu :default-openeds="opens" style="min-height: 100%; overflow: hidden" background-color="rgb(48, 65, 86)"
        text-color="#fff" active-text-color="#ffd04b" :collapse-transition="false" :collapse="isCollapse" router>
        <div style="height: 60px; line-height: 60px; text-align: center ; ">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
            <b style="color: white; margin-left: 5px" v-show="logoTextShow">后台管理系统</b>
        </div>
        <div v-for="item in menus" :key="item.id">
            <div v-if="item.children == null">
                <el-menu-item :index="item.path">
                    <i :class="item.icon"></i>
                    <span slot="title">{{ item.name }}</span>
                </el-menu-item>
            </div>
            <div v-else>
                <el-submenu :index="item.id + ''">
                    <template slot="title">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.name }}</span>
                    </template>
                    <div v-for="subItem in item.children" :key="subItem.id">
                        <el-menu-item :index="subItem.path">
                            <i :class="subItem.icon"></i>
                            <span slot="title">{{ subItem.name }}</span>
                        </el-menu-item>
                    </div>
                </el-submenu>
            </div>
        </div>
    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    },
    data() {
        return {
            menus: [{
                "id": 0,
                "name": "主页",
                "path": "/admin",
                "icon": "el-icon-house",
                "children": null
            },
            {
                "id": 1,
                "name": "问卷管理",
                "path": "/admin/manageQuest",
                "icon": "el-icon-user",
                "children": [{
                    "id": 2,
                    "name": "设计问卷",
                    "path": "/admin/designQuest",
                    "icon": "el-icon-date",
                    "children": null,
                },
                {
                    "id": 3,
                    "name": "发送问卷",
                    "path": "/admin/sentQuest",
                    "icon": "el-icon-s-custom",
                    "children": null,
                },
                {
                    "id": 4,
                    "name": "统计＆下载",
                    "path": "/admin/downloadQuest",
                    "icon": "el-icon-circle-plus-outline",
                    "children": null,
                }
                ]
            },
            {
                "id": 5,
                "name": "用户管理",
                "path": "/admin/manageUser",
                "icon": "el-icon-tickets",
                "children": null
            },
            {
                "id": 6,
                "name": "系统管理",
                "path": '/admin/manageSystem',
                "icon": "el-icon-edit-outline",
                "children": null
            }

            ],
            opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id +
                '') : [],
        }
    },
}
</script>

<style>
.el-menu-item.is-active {
    background-color: rgb(38, 52, 69) !important;
}

.el-menu-item:hover {
    background-color: rgb(38, 52, 69) !important;
}

.el-submenu__title:hover {
    background-color: rgb(38, 52, 69) !important;
}

/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
    visibility: hidden;
}
</style>

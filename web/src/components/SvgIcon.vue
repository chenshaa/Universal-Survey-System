<template>
    <div>
        <!--单选-->
        <div v-if="iconClass == 'icon-singleChoice'" class="iconShow">
            <svg t="1680611014186" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="8000" width="48" height="48">
                <path
                    d="M512 853.333333c-187.733333 0-341.333333-153.6-341.333333-341.333333s153.6-341.333333 341.333333-341.333333 341.333333 153.6 341.333333 341.333333-153.6 341.333333-341.333333 341.333333z m0-85.333333c140.8 0 256-115.2 256-256s-115.2-256-256-256-256 115.2-256 256 115.2 256 256 256z m0-170.666667c-46.933333 0-85.333333-38.4-85.333333-85.333333s38.4-85.333333 85.333333-85.333333 85.333333 38.4 85.333333 85.333333-38.4 85.333333-85.333333 85.333333z"
                    fill="#707070" p-id="8001"></path>
            </svg>
        </div>
        <!--多选-->
        <div v-if="iconClass == 'icon-multiChoice'" class="iconShow">
            <svg t="1680858458510" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="1128" width="48" height="48">
                <path
                    d="M490.666667 507.733333L597.333333 401.066667l59.733334 59.733333-166.4 166.4-119.466667-119.466667 59.733333-59.733333 59.733334 59.733333zM213.333333 213.333333h597.333334v597.333334H213.333333V213.333333z m85.333334 85.333334v426.666666h426.666666V298.666667H298.666667z"
                    fill="#707070" p-id="1129"></path>
            </svg>
        </div>
        <!--填空-->
        <div v-if="iconClass == 'icon-fillBlanks'" class="iconShow">
            <svg t="1680858737788" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="8459" width="48" height="48">
                <path
                    d="M341.333333 682.666667H213.333333V341.333333h128v341.333334z m85.333334 0V341.333333h384v341.333334h-384z m469.333333 85.333333V256H128v512h768z"
                    fill="#707070" p-id="8460"></path>
            </svg>
        </div>
        <!--下拉-->
        <div v-if="iconClass == 'icon-dropDown'" class="iconShow">
            <svg t="1681022611140" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="24197" width="48" height="48">
                <path
                    d="M768 341.333333V298.666667H256v42.666666h512z m85.333333 0v85.333334h-42.666666v384H213.333333v-384H170.666667V213.333333h682.666666v128zM298.666667 426.666667v298.666666h426.666666v-298.666666H298.666667z m128 42.666666h170.666666v85.333334h-170.666666v-85.333334z"
                    fill="#707070" p-id="24198"></path>
            </svg>
        </div>
        <!--文本域-->
        <div v-if="iconClass == 'icon-textArea'" class="iconShow">
            <svg t="1681022730286" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="24455" width="48" height="48">
                <path
                    d="M554.666667 469.333333v42.666667h42.666666v-42.666667h-42.666666z m-85.333334 0h-42.666666v42.666667h42.666666v-42.666667z m85.333334-85.333333h128v213.333333h-128v85.333334h-85.333334v-85.333334H341.333333V384h128V341.333333h85.333334v42.666667z m298.666666-170.666667v597.333334H170.666667V213.333333h682.666666zM256 298.666667v426.666666h512V298.666667H256z"
                    fill="#707070" p-id="24456"></path>
            </svg>
        </div>
    </div>
</template>

<script>
export default {
    name: "SvgIcon",
    props: {
        iconClass: {
            type: String,
            required: true
        }
    },
    computed: {

    }
}
</script>

<style></style>
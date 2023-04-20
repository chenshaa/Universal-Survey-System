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
            </div>
            <div class="card-container">

                <!---开始问卷box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == 0">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681213625918" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="7978" width="32" height="32">
                                    <path
                                        d="M689.066667 85.333333h-520.533334c-28.16 0-51.2 23.04-51.2 51.2v750.933334c0 28.16 23.04 51.2 51.2 51.2h520.533334c28.16 0 51.2-23.04 51.2-51.2V136.533333c0-28.16-23.04-51.2-51.2-51.2z m-366.933334 426.666667h-102.4c-18.773333 0-34.133333-15.36-34.133333-34.133333s15.36-34.133333 34.133333-34.133334h102.4c18.773333 0 34.133333 15.36 34.133334 34.133334s-15.36 34.133333-34.133334 34.133333z m154.026667-119.466667h-256c-18.773333 0-34.133333-15.36-34.133333-34.133333s15.36-34.133333 34.133333-34.133333h256c18.773333 0 34.133333 15.36 34.133333 34.133333s-15.36 34.133333-34.133333 34.133333z m136.533333-119.466666h-392.533333c-18.773333 0-34.133333-15.36-34.133333-34.133334s15.36-34.133333 34.133333-34.133333h392.533333c18.773333 0 34.133333 15.36 34.133334 34.133333s-15.36 34.133333-34.133334 34.133334z"
                                        fill="#4AA3FF" p-id="7979"></path>
                                    <path
                                        d="M855.466667 358.4h-115.2v68.266667h98.133333v443.733333h-98.133333v17.066667c0 28.16-23.04 51.2-51.2 51.2h166.4c28.16 0 51.2-23.04 51.2-51.2V409.6c0-28.16-23.04-51.2-51.2-51.2z"
                                        fill="#4AA3FF" p-id="7980"></path>
                                </svg>

                                <span class="cardTitle">此问卷处于草稿状态，如果您的问卷准备就绪，您可以</span>
                            </div>

                            <el-date-picker v-model="startDate" type="datetime" placeholder="开始日期"
                                style="margin-right: 10px;"></el-date-picker>

                            <el-date-picker v-model="endDate" type="datetime" placeholder="结束日期"
                                style="margin-right: 10px;"></el-date-picker>
                            <el-button type="primary" @click="startQuestionnaire">发布问卷</el-button>
                        </div>
                    </div>
                </el-card>

                <!----二维码扫描box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == 1">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681213625918" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="7978" width="32" height="32">
                                    <path
                                        d="M689.066667 85.333333h-520.533334c-28.16 0-51.2 23.04-51.2 51.2v750.933334c0 28.16 23.04 51.2 51.2 51.2h520.533334c28.16 0 51.2-23.04 51.2-51.2V136.533333c0-28.16-23.04-51.2-51.2-51.2z m-366.933334 426.666667h-102.4c-18.773333 0-34.133333-15.36-34.133333-34.133333s15.36-34.133333 34.133333-34.133334h102.4c18.773333 0 34.133333 15.36 34.133334 34.133334s-15.36 34.133333-34.133334 34.133333z m154.026667-119.466667h-256c-18.773333 0-34.133333-15.36-34.133333-34.133333s15.36-34.133333 34.133333-34.133333h256c18.773333 0 34.133333 15.36 34.133333 34.133333s-15.36 34.133333-34.133333 34.133333z m136.533333-119.466666h-392.533333c-18.773333 0-34.133333-15.36-34.133333-34.133334s15.36-34.133333 34.133333-34.133333h392.533333c18.773333 0 34.133333 15.36 34.133334 34.133333s-15.36 34.133333-34.133334 34.133334z"
                                        fill="#4AA3FF" p-id="7979"></path>
                                    <path
                                        d="M855.466667 358.4h-115.2v68.266667h98.133333v443.733333h-98.133333v17.066667c0 28.16-23.04 51.2-51.2 51.2h166.4c28.16 0 51.2-23.04 51.2-51.2V409.6c0-28.16-23.04-51.2-51.2-51.2z"
                                        fill="#4AA3FF" p-id="7980"></path>
                                </svg>

                                <span class="cardTitle">问卷已发布，您可以通过链接或二维码分享问卷</span>
                            </div>

                            <div class="url-qrcode">
                                <div class="url-editor">
                                    <el-input v-model="QRlink" placeholder="请输入网址"></el-input>
                                    <el-button @click="copyUrl" type="primary">复制</el-button>
                                </div>
                                <div class="qrcode">
                                    <img :src="QRImgUrl">
                                </div>
                            </div>

                        </div>
                    </div>
                </el-card>

                <!----选择用户box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == 0 || questionnaireStatus == 1">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681202208922" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="5212" width="32" height="32">
                                    <path
                                        d="M845.824 220.34432c-10.07616-9.29792-23.26528-13.94688-36.39296-13.94688-1.55648 0-2.33472 0-3.87072 0-0.77824 0-9.29792 0.77824-22.46656 0.77824-22.46656 0-65.04448-1.55648-106.06592-11.61216-52.67456-12.36992-113.84832-70.4512-131.66592-82.08384-8.4992-5.4272-19.3536-8.51968-29.42976-8.51968-10.05568 0-20.11136 3.09248-29.40928 8.51968-2.31424 1.55648-65.80224 67.35872-127.75424 82.08384-41.04192 10.05568-85.17632 11.61216-106.84416 11.61216-13.94688 0-22.44608-0.77824-23.22432-0.77824-0.77824 0-2.33472 0-3.09248 0-13.94688 0-26.33728 4.64896-36.39296 13.94688-10.83392 9.29792-17.01888 23.22432-17.01888 37.1712l0 128.512c0 473.88672 321.31072 535.01952 334.47936 537.35424 3.09248 0.77824 6.20544 0.77824 9.27744 0.77824 3.09248 0 6.22592 0 9.3184-0.77824 13.9264-2.33472 336.7936-63.488 336.7936-537.35424l0-128.512C862.06464 243.56864 855.83872 229.64224 845.824 220.34432L845.824 220.34432zM726.56896 403.8656 504.34048 619.08992c-1.55648 2.33472-3.09248 4.66944-5.4272 6.22592-6.20544 6.18496-14.70464 9.27744-23.22432 9.27744-8.54016 0-17.03936-3.09248-24.00256-9.27744-1.55648-1.55648-3.85024-3.87072-4.64896-6.22592l-119.23456-115.36384c-12.3904-12.3904-12.3904-31.72352 0-44.11392 12.36992-12.41088 33.28-12.41088 45.6704 0l102.21568 98.32448 205.18912-198.20544c13.18912-12.3904 33.28-12.3904 45.6704 0C739.71712 371.34336 739.71712 391.4752 726.56896 403.8656L726.56896 403.8656z"
                                        p-id="5213" fill="#4AA3FF"></path>
                                </svg>

                                <span class="cardTitle">或者，进行作答人员设置</span>

                                <el-switch v-model="anonymousSwitch" active-text="记名问卷" inactive-text="匿名问卷"
                                    @change="setAnonyMous()" style="padding-left: 20px;">
                                </el-switch>
                            </div>

                            <div>
                                <el-transfer v-model="selectedUsers" :data="userData" :props="{
                                    key: 'id',
                                    label: 'username'
                                }" :titles="['未选用户', '已选用户']" :button-texts="['从问卷移除', '添加到问卷']" filterable
                                    filter-placeholder="搜索用户" :filter-method="filterMethod" />
                            </div>

                            <div class="buttonCenter">
                                <el-button type="primary" @click="setUsers">确认修改</el-button>
                            </div>
                        </div>
                    </div>
                </el-card>

                <!---结束问卷box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == 1">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681737750364" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="1116" width="32" height="32">
                                    <path
                                        d="M876.407224 954.162564H185.412484a46.694369 46.694369 0 0 1-46.899168-46.557836c0-25.66825 21.026119-46.489569 46.899168-46.489569h491.929272V0H138.513316C99.737875 0 68.266962 31.266112 68.266962 69.836753v837.767975A116.735922 116.735922 0 0 0 185.412484 1023.999317h690.99474a35.020777 35.020777 0 0 0 35.15731-34.884243 35.020777 35.020777 0 0 0-35.15731-34.95251z"
                                        fill="#4AA3FF" p-id="1117"></path>
                                    <path
                                        d="M834.081919 0H730.862787v843.297571h103.219132c42.803171 0 77.482615-30.651713 77.482615-68.403154V68.334888C911.564534 30.651713 876.88509 0 834.081919 0z"
                                        fill="#4AA3FF" p-id="1118"></path>
                                </svg>

                                <span class="cardTitle">设置结束时间以结束一张问卷</span>
                            </div>

                            <el-date-picker v-model="startDate" type="datetime" placeholder="开始日期"
                                style="margin-right: 10px;"></el-date-picker>

                            <el-date-picker v-model="endDate" type="datetime" placeholder="结束日期"
                                style="margin-right: 10px;"></el-date-picker>
                            <el-button type="primary" @click="endQuestionnaire">结束问卷</el-button>
                        </div>
                    </div>
                </el-card>

                <!---初始化box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == -1">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681284576589" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="866" width="32" height="32">
                                    <path
                                        d="M690.3808 489.8816l-239.4112 261.802667a43.758933 43.758933 0 0 1-32.290133 14.199466H418.474667a43.690667 43.690667 0 0 1-32.290134-14.5408L269.312 620.407467a43.690667 43.690667 0 1 1 65.058133-58.094934l84.650667 94.890667L626.005333 430.967467a43.690667 43.690667 0 0 1 64.443734 58.914133M872.584533 63.965867h-87.108266c-0.136533 0 0 27.989333 0 44.100266V194.56a87.517867 87.517867 0 0 1-87.04 87.790933H261.666133a87.517867 87.517867 0 0 1-87.108266-87.790933V108.1344c0-16.1792-0.2048-29.354667-0.2048-44.100267h-87.381334C38.912 64.034133 0 102.946133 0 150.9376v786.158933C0 985.019733 38.912 1024 86.903467 1024H873.130667a87.04 87.04 0 0 0 86.903466-86.903467V150.9376A87.381333 87.381333 0 0 0 872.516267 64.034133"
                                        fill="#4AA3FF" p-id="867"></path>
                                    <path
                                        d="M695.637333 31.9488A58.709333 58.709333 0 0 0 644.369067 0H315.733333c-21.981867 0-40.96 12.9024-51.336533 31.880533a67.720533 67.720533 0 0 0-8.3968 32.426667v63.2832c0 35.498667 26.760533 64.443733 59.733333 64.443733h328.635734c32.904533 0 59.5968-28.8768 59.5968-64.443733V64.3072a67.037867 67.037867 0 0 0-8.328534-32.3584"
                                        fill="#4AA3FF" p-id="868"></path>
                                </svg>

                                <span class="cardTitle">选择一个问卷以开始设置</span>
                            </div>
                        </div>
                    </div>
                </el-card>

                <!---问卷结束box-->
                <el-card shadow="always" class="card" v-if="questionnaireStatus == 2">
                    <div class="card-body">
                        <div>
                            <div style="padding-bottom: 10px;">
                                <svg t="1681284576589" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="866" width="32" height="32">
                                    <path
                                        d="M690.3808 489.8816l-239.4112 261.802667a43.758933 43.758933 0 0 1-32.290133 14.199466H418.474667a43.690667 43.690667 0 0 1-32.290134-14.5408L269.312 620.407467a43.690667 43.690667 0 1 1 65.058133-58.094934l84.650667 94.890667L626.005333 430.967467a43.690667 43.690667 0 0 1 64.443734 58.914133M872.584533 63.965867h-87.108266c-0.136533 0 0 27.989333 0 44.100266V194.56a87.517867 87.517867 0 0 1-87.04 87.790933H261.666133a87.517867 87.517867 0 0 1-87.108266-87.790933V108.1344c0-16.1792-0.2048-29.354667-0.2048-44.100267h-87.381334C38.912 64.034133 0 102.946133 0 150.9376v786.158933C0 985.019733 38.912 1024 86.903467 1024H873.130667a87.04 87.04 0 0 0 86.903466-86.903467V150.9376A87.381333 87.381333 0 0 0 872.516267 64.034133"
                                        fill="#4AA3FF" p-id="867"></path>
                                    <path
                                        d="M695.637333 31.9488A58.709333 58.709333 0 0 0 644.369067 0H315.733333c-21.981867 0-40.96 12.9024-51.336533 31.880533a67.720533 67.720533 0 0 0-8.3968 32.426667v63.2832c0 35.498667 26.760533 64.443733 59.733333 64.443733h328.635734c32.904533 0 59.5968-28.8768 59.5968-64.443733V64.3072a67.037867 67.037867 0 0 0-8.328534-32.3584"
                                        fill="#4AA3FF" p-id="868"></path>
                                </svg>

                                <span class="cardTitle">选择一个问卷以开始设置</span>
                            </div>
                        </div>
                    </div>
                </el-card>

            </div>
        </div>
    </div>
</template>
  
<script>
let pinyin = require('js-pinyin');
import QRCode from 'qrcode'


import { listQuestionnaireApi, setQuestionnaireTimeApi, setQuestionnaireAnonymousApi } from "@/api/questionnaire.js";
import { listUserApi } from "@/api/user.js";
import { listPaperApi, updatePaperApi } from "@/api/paper.js";

export default {
    data() {
        return {
            selectedQuestionnaire: "",
            questionnaireList: [],
            startDate: "",
            endDate: "",
            selectedUsers: [],
            userData: [],
            anonymousSwitch: false,
            QRImgUrl: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAYAAACtWK6eAAAAAXNSR0IArs4c6QAAC+tJREFUeF7tnet6HKkORSfv/9A5X+LM6e5JV8ESm6LarPktKLQvksCO58fPnz9//uN/IiACbxH4oUFUhggcI6BBVIcInCCgQZSHCGgQNSACNQTsIDXcXLUJAhpkE6JNs4aABqnh5qpNENAgmxBtmjUENEgNN1dtgoAG2YRo06whoEFquLlqEwQ0yCZEm2YNAQ1Sw81VmyCgQTYh2jRrCGiQGm6u2gQBDbIJ0aZZQ0CD1HBz1SYIaJBNiDbNGgIapIabqzZBQINsQrRp1hDQIDXcXLUJAhpkE6JNs4aABqnh5qpNENAgmxBtmjUELjHIjx8/aqe7eNXR39A7Oj+NP0qn8rf7UmdK5XAxVf//XAU7clYN8oQWFQuN1yBEmn2xGqQPp0gUFTyN1yARml420SB5TA93pIKn8RokT6YGyWOqQQbGygvp6PqUBumCKRNEOwKNt4NkeHre5VsbZHZyR3TQF6DUPpXv0hdAatpUfF76rztWsEucaekrlgb5ovAMBw3yhZEGSdi9c48U2HQfGn8mDDqu0W/T+E7oy2GrzmMH6biwOmI9EPj0rk8dqkE0yFvNrKrYqWJEjXD43Sv+F2zfFWyaF413xHrItoJdwiS37CD0Ynq3eZySmcq3deEnglmVw91e1TRIx4iVEgslnwj639jUHSGVM82BYpTK96NGrFRFTYGdEgs9DxWXHaSC2PkaO4gdJHJJv1tRS1lFg2gQDXLiJg2iQTSIBnlFgN4paLum+6fGk7Nz0svsqhzoPY3mhbm8489BUoJZBfYqcWkQKv92vCNWx4jVhnGsQ6UKggahTLXjNYgG8Q7iHWSswrfrzNj+dpAHfqvG4iOO7SB2EDuIHWSswh9Wl4O/95WsgrS7pF51Vj00JLGjnf9dvB1koIOkRHTFvyikYknlRr+rQToESavmEQmzwU6JSIN4B3mrYSrgu1UjDfJg5FOKGtWQI1ZHR/MOUq/wVJC0aKbuXL5iPSFAK78G0SDU6Cg+JUj00bOnu9DrU+ruU8mLVk7KAY2v5EDWrDrPLUcsAlwlloJN52s6JlRy0CBfqFEcKNYapOMOokHaIxYVHo2nRY3u/1F3kFRys+8OjlizmXrsr0Guwxr/GUs7iB1kqjxXud8OUq/An85ZStBL7yCpJFL70Mv17PizSygVcCo+hXVqn299SU+BlNpntuDp/hqkzawGaWMUi6ACnh2vQdrUapA2RrGI2YKn+2uQNrUapI1RLIIKeHa8BmlTq0HaGMUiZgue7q9B2tR+C4O00/zMiNTLUIVk+u3PRHj9qS955l2f5pwTUJHS+LNTJ/eag8732FWDDPBIRUrjNcgAOaGlGmQASCp4Gq9BBsgJLdUgA0BSwdN4DTJATmipBhkAkgqexmuQAXJCSy8xCBUGjadYzP7tXPqcW3nFojmn4lPc0H1ofCzfT/rr7ikhaZC6fFJCpfvQ+HqGryvtIB1IpjrCKpI7UuwOSeVA96Hx3Qk1AjVIB5Ia5AFSSqh0HxrfQWtXiAbpgEmDaJAOmdRDqPtpPD2ZdxCKmAapIzawcrZQj45GOwLd5yi+Yny6hmI6QN/LUoopjU9xQPO9ZMSigqFgUFGsIoeK/RcOdA3Fggomxc0qDmi+GuQJMSou+uxMxa5B2nKmHLR3fI3QIBqEauZtPO0INJ52rkhSvzr4FT8odMT6QsAO8lCCBumwcGqkuds+tCCcjQnUVBSLDpq6Qqjgabwd5AmBFHhdzA4E0XPOnpcrXYoaiuZM4aX7z8b0liMWBYmSkIqn55xNpgZJMfvYR4MMYKpBnoR08P9cofDeDVMNQhkcGAXtIG2wNUjHsyoFqQ37nAh6Tg3S5uFumNpB2pwdRtyNTO8gA2QeLL3EIPSlhKa5Sqj0CZbmdRY/G1P6rErPc0U3TeCtQQZQ1CD1S7oG6bhrDGjzZakdJIVkex+KNe1E7RNcG2EHGcDbDmIHGZBPHTz6UVrVUu1dg9Q5TnFAtULj7SAUsY7R8Qry6aV4IM2hcdYRawD52RWYimh2J5qd7xWvXleY/10eq7C7pIMcETc7aQ1SH4HuVvlna+VQo3f89yCpKqVBNMjAgPN7qR3kCUFHrLacUsWr/aXXCDtIh1BToNLxIUVOah+Kw+9KOPm3bStnImtWYWcH6TBmipzUPkRY/8ZqkApqjlhDT5h03NAgNZGedUDKAT3BR3WQ2QK7Y5WlOdMc6L2LjqdYkHAU1CBPCFOxzCZntlgqlVODUNbP4+0gHQakkCerGi0KGoSypUG6EaPisoM8EEgVBcpB6rtHXNpB7CBvtbFKqKu+q0E6+gglxw5iB+mQVTuECo+2zdScTl90Zp9z5SW9zWpfRIp7ynHf6dpRl4xYKZAO2+DB02BK8ClyKvvQNbOxbkvqNSJ1HooDPefSESsFkgZpjzSzsabCS51HgzwhP3t0oWDT+JSRHbEeSKY4wAa/4tfdU1UkJTwKNo1PnVODaJC3WrKD1IUxuxjhChz61ZFUkcLnv6KD4EMtAvVu4jrDbbZgKBaUY1oE6f6p+EtesehhKTmzX6uOzr+SZA1CVVWL1yBPuKWMWaOCrdIgDK9qtAbRIG+1Q4sFFeDK7kvOqkE0iAY5cYwG0SAaZLVBaLtOtV/6XdJ6r4pd9QCRyo9yOftuRfO6pINQoVJQj5Km36XgXRGvQb5QTmmCcqZBKGIXx2sQDfKX5FLVwg7ygHYVFpRLR6yOCkxBdcR6IEA7TgcdQyGUSw3SATcFVYNokA5ZlUIuuYOUTnajRbOrWmX8oUVkVQ70nLTYpfY//O4df1nxRt74fZRV4jrDgQpjVQ70nBrkburvOM8qcWmQ9iNDyoB2kA4jrKpejlhtcmYXKQ3S5uAwYjY5GqRNzmwONEibAw3ygRh9ixGrUiEHuCovpT9DSJFzhs/sb6Rynl3hZ++/tINokHPPapB2TdMgbYymR6SqKT2oBmkjpkHaGE2P0CD1Z9XZAp69vyNWh700iAb5r0wu+VUT7yDeQTrq02nIlh0k9UJDwU+BTfepFAra1SgWKQ4oFvScs/e/5YiVImcV2JQ0DUKZqo989S+9rlw6YmmQNo12kC+MaDFqI9sXoUGecKKGpaTZQfpE+S6KYl3/kh0kVo0oaRqkLluKdf1LGkSDDHTNw8ss/L98UQFrkCfEKpX2HeB0fk+NWEfk0/3PZm8qsFXxlZwdsf4gQAVMSab7UzKpken+GsRXrLeap8KjFTvVruk5NQgtcRpEgzQ0Q01Yl+CclZWi4IjliNWtRg3yBVWq63cD/yfwlj8HSYnCOwiVQz7eDtKBKXX/pxjkiifPFHarigX9Lr1PdshvKMQO8gTfHaudBvkiKMUNdYsG0SBvNUMFmTKyHaRDkI5Y9efNlFA1yJ/HgSv+9GiKNNoe6fxLReEdJG9kO4gdBPk8VVxmF4vUOTXIBxokNfJdQX5KqNRQyPUnl26KdarrH04DjlgPaGaLQoPksdYgtDQNdCgNkhcwLQp2kAEBU69QwdN4eh4qlsr+jlgV1I7X+HOQCw2rQfIdyhFroCDQjkDjB472sjRJsh0kxcrXPrfsINkU/96NiujwhWPyPzP9TVDoG3S2T3GQNH/qTGQfDdIxYmkQIqnXWA3SgV2qCnZ8qiskdZ7UPmeHTn3DDtIljb+C7CB2kJpyOlfZQTqASlXBjk91haTOk9rHDtJF25IgO4gdZKrw7CAd8K6afzuO9hKy6pmXnvNXPBUe7XY0fvZDRuo8FOulHYQedna8BnkgnBLk3fahGtIgHSPWHTugHeSLOIqDBqEIaJC3iN2t8qfOQ+VhB9EgGuTENRpEg2iQ1Qahbc14EbgLApd0kLsk6zlEgCKgQShixm+FgAbZim6TpQhoEIqY8VshoEG2ottkKQIahCJm/FYIaJCt6DZZioAGoYgZvxUCGmQruk2WIqBBKGLGb4WABtmKbpOlCGgQipjxWyGgQbai22QpAhqEImb8VghokK3oNlmKgAahiBm/FQIaZCu6TZYioEEoYsZvhYAG2Ypuk6UIaBCKmPFbIaBBtqLbZCkCGoQiZvxWCGiQreg2WYrA/wDM4SSKNARksQAAAABJRU5ErkJggg==',
            QRlink: '',
            questionnaireStatus: -1,//问卷状态 -1未选中 0未开始 1进行中 2已结束
        };
    },
    created() {
        //获取问卷列表
        listQuestionnaireApi().then(res => {
            this.questionnaireList = res.data.data;
        });
        //获取用户列表
        listUserApi().then(res => {
            this.userData = res.data.data;
        });
    },
    methods: {
        //开始问卷
        startQuestionnaire() {
            let qIndex = this.questionnaireList.findIndex(item => item.id == this.selectedQuestionnaire);
            if (qIndex != -1) {
                if (this.questionnaireList[qIndex].startTime == null) {
                    if (this.startDate == null) {
                        this.startDate = new Date();
                    }

                    setQuestionnaireTimeApi(
                        {
                            questionnaireId: this.selectedQuestionnaire,
                            startTime: this.startDate,
                            endTime: this.endDate
                        }
                    ).then(res => {
                        if (res.data.code == 200) {
                            this.questionnaireStatus = 1;
                            this.$message({
                                message: '开始成功',
                                type: 'success'
                            });
                        } else {
                            this.$message({
                                message: '开始失败',
                                type: 'error'
                            });
                        }
                    });
                } else {
                    this.$message({
                        message: '问卷已开始',
                        type: 'warning'
                    });
                }
            }


        },
        //穿梭框过滤方法
        filterMethod(query, item) {
            // 将查询条件转换为拼音
            const queryPinyin = pinyin.getFullChars(query).toLowerCase();
            // 将用户名转换为拼音
            const namePinyin = pinyin.getFullChars(item.username).toLowerCase();
            // 判断查询条件是否为用户名的子串
            if (namePinyin.indexOf(queryPinyin.toLowerCase()) > -1) {
                return true;
            }
            return false;
        },
        //刷新按钮
        refresh() {
            console.log(this.selectedUsers);
        },
        //下拉框被选择
        selectQuestionnaire() {
            //设置问卷的状态
            let qIndex = this.questionnaireList.findIndex(item => item.id == this.selectedQuestionnaire);
            if (qIndex != -1) {
                if (this.questionnaireList[qIndex].startTime == null) {
                    this.questionnaireStatus = 0;
                } else if (this.questionnaireList[qIndex].endTime == null) {
                    this.questionnaireStatus = 1;
                } else {
                    this.questionnaireStatus = 2;
                }
            }
            //设置时间
            this.startDate = this.questionnaireList[qIndex].startTime;
            this.endDate = this.questionnaireList[qIndex].endTime;

            //查询问卷添加的用户数据
            this.selectedUsers = [];
            listPaperApi(this.selectedQuestionnaire).then(res => {
                for (let i = 0; i < res.data.data.length; i++) {
                    this.selectedUsers.push(res.data.data[i].respondentId);
                }
                console.log(this.selectedUsers)
            });

            //设置问卷匿名状态
            this.anonymousSwitch = this.questionnaireList[qIndex].anonymousEnabled;
        },
        //复制链接按钮
        async copyUrl() {
            try {
                await navigator.clipboard.writeText(this.QRlink);
                this.$message({
                    message: '复制成功',
                    type: 'success'
                });
            } catch (err) {
                this.$message({
                    message: '复制失败,请使用Ctrl+C复制',
                    type: 'error'
                });
            }
        },
        //设置用户按钮
        setUsers() {
            updatePaperApi(this.selectedQuestionnaire, this.selectedUsers).then(res => {
                if (res.data.code == 200) {
                    this.selectedUsers = [];
                    for (let i = 0; i < res.data.data.length; i++) {
                        this.selectedUsers.push(res.data.data[i].respondentId);
                    }

                    this.$message({
                        message: '设置成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: '设置失败',
                        type: 'error'
                    });
                }
            });
        },
        //结束问卷
        endQuestionnaire() {
            let qIndex = this.questionnaireList.findIndex(item => item.id == this.selectedQuestionnaire);
            if (qIndex != -1) {
                if (this.questionnaireList[qIndex].endTime == null) {
                    if (this.endDate == null) {
                        this.endDate = new Date();
                    }
                    setQuestionnaireTimeApi(
                        {
                            questionnaireId: this.selectedQuestionnaire,
                            startTime: this.startDate,
                            endTime: this.endDate
                        }
                    ).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: '结束成功',
                                type: 'success'
                            });
                        } else {
                            this.$message({
                                message: '结束失败',
                                type: 'error'
                            });
                        }
                    });
                } else {
                    this.$message({
                        message: '问卷已结束',
                        type: 'warning'
                    });
                }
            }
        },
        //设置匿名
        setAnonyMous() {
            setQuestionnaireAnonymousApi(this.selectedQuestionnaire, this.anonymousSwitch == true ? 1 : 0).then(res => {
                if (res.data.code == 200) {
                    this.$message({
                        message: '设置成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: '设置失败',
                        type: 'error'
                    });
                }
            });
        },

    },
    watch: {
        selectedQuestionnaire: function () {
            //根据当前页面url获取网站域名
            let baseUrl = window.location.href.split('#')[0];
            this.QRlink = baseUrl + '#/survey/' + this.selectedQuestionnaire;
            QRCode.toDataURL(this.QRlink, {
                width: 200,
                height: 200
            }).then(url => {
                this.QRImgUrl = url;
            })
        }
    }

};
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

/* 卡片body */
.card-body {
    padding: 10px;
}

/* 卡片图标 */
.icon {
    padding-right: 10px;
}

/* 卡片标题 */
.cardTitle {
    font-size: 18px;
    font-weight: 500;
    color: #333;
}

.url-qrcode {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.url-editor {
    display: flex;
    align-items: center;
    margin-right: 20px;
}

.qrcode img {
    width: 200px;
    height: 200px;
}

.buttonCenter {
    display: flex;
    justify-content: center;
    margin-top: 10px;
}
</style>
  
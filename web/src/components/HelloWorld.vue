<template>
  <router-link to="/login">Go to About Page</router-link>
  <div class="introduction">
    <header>
      <nav>
        <a href="#" class="logo">Vue.js</a>
        <el-menu :default-active="activeMenu" class="nav-links" mode="horizontal" @select="handleSelect">
          <el-menu-item index="what-is-vue">What is Vue.js?</el-menu-item>
          <el-menu-item index="why-use-vue">Why Use Vue.js?</el-menu-item>
          <el-menu-item index="getting-started">Getting Started</el-menu-item>
        </el-menu>
      </nav>
    </header>
    <main>
      <section class="section-what-is-vue">
        <h2 class="section-title">What is Vue.js?</h2>
        <p>Vue.js is a progressive JavaScript framework used for building user interfaces. It is designed to be incrementally adoptable and can be easily integrated into other libraries or existing projects. Vue.js was created by Evan You and released in February 2014.</p>
        <img src="@/assets/logo.png" alt="Vue.js logo">
      </section>
      <section class="section-why-use-vue">
        <h2 class="section-title">Why Use Vue.js?</h2>
        <ul>
          <li>Vue.js is easy to learn and use, even for beginners.</li>
          <li>Vue.js is fast and efficient, thanks to its virtual DOM implementation.</li>
          <li>Vue.js is flexible and can be used for building a wide variety of applications.</li>
          <li>Vue.js has a large and active community, with lots of resources and support available.</li>
        </ul>
      </section>
      <section class="section-getting-started">
        <h2 class="section-title">Getting Started with Vue.js</h2>
        <p>To get started with Vue.js, you can download it from the official website or include it in your project using a package manager like npm or Yarn. Once you have Vue.js installed, you can start building your application using Vue components and templates. There are also many resources available online to help you learn Vue.js, including documentation, tutorials, and forums.</p>
      </section>
    </main>
    <footer>
      <p>&copy; 2023 Vue.js Introduction. All rights reserved.</p>
    </footer>
  </div>
</template>



<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      activeMenu: 'what-is-vue'
    }
  },
  methods: {
    handleSelect(index) {
      this.activeMenu = index
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.introduction {
  font-family: sans-serif;
  color: #333;
  max-width: 960px;
  margin: 0 auto;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1em 0;
}

.logo {
  font-size: 2em;
  font-weight: bold;
  color: #41b883;
  text-decoration: none;
}

.el-menu {
  margin: 0;
border: none;
background-color: transparent;
}

.el-menu-item {
color: #333;
font-size: 1.2em;
margin: 0 1em;
}

.el-menu-item:hover {
color: #41b883;
}

.active-menu {
color: #41b883 !important;
}

.main {
padding: 2em 0;
}

.section-title {
font-size: 2em;
font-weight: bold;
margin-bottom: 1em;
}

.section-what-is-vue img {
width: 200px;
display: block;
margin: 1em auto;
}

.section-why-use-vue ul {
list-style: none;
padding-left: 1em;
}

.section-why-use-vue li {
margin-bottom: 1em;
}

.footer {
background-color: #41b883;
color: #fff;
text-align: center;
padding: 1em 0;
}

.footer p {
margin: 0;
font-size: 0.8em;
}
</style>

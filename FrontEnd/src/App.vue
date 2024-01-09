<template>
  
        <nav>
        <router-link to="/">Home</router-link> |
        <router-link to="/about">Operations</router-link>
        </nav>
      <router-view/>
      
</template>

<style>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100%;
  font-size: 1.5rem;
  color: #2c3e50;
}
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
.el-container {
  height: 100%;
  width: 100%;
}
.common-layout {
  height: 100%;
  width: 100%;
  background-color: #ffffff;
}
.home-footer {
  text-align: center;
  color: #999;
  font-size: 12px;
  padding: 30px 0;
}


</style>

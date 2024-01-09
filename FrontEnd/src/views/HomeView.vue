<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside width="600px" class="aside-container" margin-left="20%">
          <!-- <el-text class="mx-1" type="primary">PATH 17 PG</el-text> -->
          <div class="logo" style="margin-top: 10%; float: left;">
              <img src="../assets/PATH.png" alt="logo"/>
            </div>
        </el-aside>
        <el-container>
          <el-main class="main-container" width="100%">
            <div style="float: left; margin-left: 50%; margin-top: 20%;">
              <el-text class="mx-1" type="primary" tag = "b" size = "large" color = "black">Zhuang Tang a1814657</el-text>
              <el-divider />
              <el-text class="mx-1" type="primary" tag = "b" size = "large">Tran Minh Hoang Nguyen a1865399</el-text>
              <el-divider />
              <el-text class="mx-1" type="primary" tag = "b" size = "large">Vinh Dat Vuong a1876299</el-text>
            </div>
            
          </el-main>
          
        </el-container>
      </el-container>
    </el-container>
    <el-footer class="home-footer">Copyright 2023. university of Adelaide PATH 17PG</el-footer>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  name: 'HomeView',
  components: {
  },
})
export default class HomeView extends Vue {
  
}
</script>

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
.home-footer {
  text-align: center;
  color: #999;
  font-size: 12px;
  padding: 30px 0;
  margin-top: 400px;
}
.logo {
  margin-top: 10%;
  margin-left: 30%;
}

</style>


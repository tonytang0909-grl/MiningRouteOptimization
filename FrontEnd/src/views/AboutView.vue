<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside  class="aside-container">
          <el-radio-group  style="margin-bottom: 20px">
  </el-radio-group>
  <el-menu
    default-active="1"
    class="el-menu-vertical-demo"
    @click="handleOpen"
  >
  <el-menu-item index="1" @click="flag1 = true, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false , flag9 = false, flag10 = false, flag11 = false, flag12 = false" >
      <el-icon><Coin /></el-icon>
      <template #title >Creating Device</template>
    </el-menu-item>
    <el-menu-item index="2"  @click="flag1 = false, flag2 = true , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = false , flag11 = false, flag12 = false">
      <el-icon><Bicycle /></el-icon>
      <template #title >Creating Connection</template>
    </el-menu-item>
    <el-menu-item index="3"  @click="flag1 = false, flag2 = false , flag3 = true , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = false , flag11 = false, flag12 = false">
      <el-icon><setting /></el-icon>
      <template #title >Update Device Status</template>
    </el-menu-item>
    <el-menu-item index="9"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = true, flag10 = false , flag11 = false, flag12 = false">
      <el-icon><setting /></el-icon>
      <template #title >Update Edge Cost</template>
    </el-menu-item>
    <el-menu-item index="4"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = true , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = false , flag11 = false, flag12 = false">
      <el-icon><List /></el-icon>
      <template #title >Connections</template>
    </el-menu-item>
    <el-menu-item index="5"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = true , flag6 = false, flag7 = false , flag8 = false, flag9 = false, flag10 = false, flag11 = false, flag12 = false">
      <el-icon><List /></el-icon>
      <template #title >Device List</template>
    </el-menu-item>
    <el-menu-item index="6"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = true, flag7 = false, flag8 = false, flag9 = false, flag10 = false, flag11 = false, flag12 = false">
      <el-icon><document /></el-icon>
      <template #title >Shortest Path(One to One)</template>
    </el-menu-item>
    <el-menu-item index="10"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = true, flag11 = false, flag12 = false">
      <el-icon><document /></el-icon>
      <template #title >Shortest Path(One to Many)</template>
    </el-menu-item>
    <el-menu-item index="7"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = true, flag8 = false, flag9 = false, flag10 = false, flag11 = false, flag12 = false">
      <el-icon><document /></el-icon>
      <template #title >Delete Device</template>
    </el-menu-item>
    <el-menu-item index="12"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = false, flag11 = false, flag12 = true">
      <el-icon><document /></el-icon>
      <template #title >Delete Edge</template>
    </el-menu-item>
    <el-menu-item index="8"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = true, flag9 = false, flag10 = false, flag11 = false, flag12 = false">
      <el-icon><DocumentAdd /></el-icon>
      <template #title >Batch Upload</template>
    </el-menu-item>
    <el-menu-item index="11"  @click="flag1 = false, flag2 = false , flag3 = false , flag4 = false , flag5 = false , flag6 = false, flag7 = false, flag8 = false, flag9 = false, flag10 = false, flag11 = true, flag12 = false">
      <el-icon><View /></el-icon>
      <template #title >Visualization</template>
    </el-menu-item>
  </el-menu>

    </el-aside>
        <el-container >
          <el-main class="main-container" >
            <div class="createDevice" v-if="flag1">
              <!-- <p style="margin-left: -20%;" >Create Device</p> -->
              <!-- <p></p> -->
              <InserNode style="margin-top: 3%;"/>
            </div>
            <div class="createConnection" v-if="flag2">
              <!-- <p style="margin-left: -20%;">Create Connection</p> -->
              <InsertConnection style="margin-top: 3%"/>
            </div>
            <div class="updateStatus" v-if="flag3">
              <!-- <p style="margin-left: -20%;">Update</p> -->
              <UpdateStatus style="margin-top: 3%"/>
            </div>
            <div class="updateEdgeCost" v-if="flag9">
              <!-- <p style="margin-left: -20%;">Update</p> -->
              <UpdateEdgeCost style="margin-top: 3%"/>
            </div>
            <div class="getConnections" v-if="flag4">
              <!-- <p style="margin-bottom: 80px; margin-left: -20%; margin-top: 50px;">Get Connections</p> -->
              <GetConnections />
            </div>
            <div class="getDevices" v-if="flag5">
              <!-- <p style="margin-bottom: 80px; margin-left: -20%; margin-top: 50px;">Get Devices</p> -->
              <GetDecives />
            </div>
            <div class="getShotestPath" v-if="flag6">
              <!-- <p style="margin-bottom: 80px; margin-left: -20%; margin-top: 50px;">Get Path</p> -->
              <GetShortestPath/>
            </div>
            <div class="getShotestPathOneToMany" v-if="flag10">
              <!-- <p style="margin-bottom: 80px; margin-left: -20%; margin-top: 50px;">Get Path</p> -->
              <ShortestPathsOneToMany/>
            </div>
            <div class="DeleteDevice" v-if="flag7">
              <!-- <p>Delete Device</p> -->
              <DeleteDevice style="margin-top: 3%"/>
            </div>
            <div class="DeleteEdge" v-if="flag12">
              <!-- <p>Delete Device</p> -->
              <DeleteEdge style="margin-top: 3%"/>
            </div>
            <div class="BatchUpload" v-if="flag8">
              <!-- <p style="margin-left: -20%;">Batch Upload</p> -->
              <BatchUpload  style="margin-top: 3%;"/>
            </div>
            <div class="Visualization" v-if="flag11">
              <!-- <p style="margin-left: -20%;">Batch Upload</p> -->
              <Visualization style="margin-top: 3%;"/>
            </div>
          </el-main>
          <!-- <el-footer class="home-footer">Footer</el-footer> -->
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts">
import InserNode from '@/components/InserNode.vue';
import InsertConnection from '@/components/InsertConnection.vue';
import UpdateStatus from '@/components/UpdateStatus.vue';
import BatchUpload from '@/components/BatchUpload.vue';
import GetDecives from '@/components/GetDecives.vue';
import DeleteDevice from '@/components/DeleteDevice.vue';
import GetConnections from '@/components/GetConnections.vue';
import GetShortestPath from '@/components/GetShortestPath.vue';
import UpdateEdgeCost from '@/components/UpdateEdgeCost.vue';
import ShortestPathsOneToMany from '@/components/ShortestPathsOneToMany.vue';
import Visualization from '@/components/Visualization.vue';
import DeleteEdge from '@/components/DeleteEdge.vue';
import { ref } from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'

const isCollapse = ref(true)

export default {
  name: 'AboutView',
  props: {
  },
  data() {
    return {
      flag1: true,
      flag2: false,
      flag3: false,
      flag4: false,
      flag5: false,
      flag6: false,
      flag7: false,
      flag8: false,
      flag9: false,
      flag10: false,
      flag11: false,
      flag12: false,
      isCollapse,
      icons: {
        Document,
        IconMenu,
        Location,
        Setting,
      },
    }
  },
  components: {
    InserNode,
    InsertConnection,
    UpdateStatus,
    BatchUpload,
    GetDecives,
    DeleteDevice,
    GetConnections,
    GetShortestPath,
    UpdateEdgeCost,
    ShortestPathsOneToMany,
    Visualization,
    DeleteEdge,
},
  methods: {
    handleOpen(key: string, keyPath: string) {
      
    },
    handleClose(key: string, keyPath: string) {
      console.log(key, keyPath)
    },
    createDevice() {
      // this.flag1 = false
      console.log('create device')
    },
    createConnection() {
      console.log('create connection')
    },
    updateStatus() {
      console.log('update status')
    },
    getConnections() {
      console.log('get connections')
    },
    getDevices() {
      console.log('get devices')
    },
    getPath() {
      console.log('get path')
    },

  },
};
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
  min-height: 400px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) .el-menu-item-group__title {
  height: 30px;
  line-height: 30px;
  padding: 0 20px;
  color: #909399;
  font-size: 12px;
  background-color: #f5f7fa;
}
</style>

<template>
    <el-button type="success" @click="getDevices" style="margin-bottom: 2%;">Get Device List</el-button>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="Id" width="180" />
        <el-table-column prop="name" label="Name" width="180" />
        <el-table-column prop="status" label="status"  width="180"/>
        <el-table-column prop="type" label="type"  width="180"/>
        <el-table-column prop="cost" label="cost"  />
  </el-table>
</template>





<script lang="ts" setup>
import { ref } from 'vue'
import axios from 'axios'
const tableData = ref([])
const getDevices = () => {
    axios.get('/node').then((response) => {
        console.log(response)
        tableData.value = response.data
    }, (error) => {
        console.log(error)
    })
    console.log('get devices!')
}

</script>
<style>


</style>
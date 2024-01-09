<template>
    <el-button type="success" @click="getEdges" style="margin-bottom: 2%;">Get Connection List</el-button>
    <el-table :data="tableData" style="width: 800">
    <el-table-column prop="id" label="id" width="200"/>  
    <el-table-column prop="src" label="Source" width="200"/>
    <el-table-column prop="dest" label="Destination"  width="200"/>
    <el-table-column prop="cost" label="Cost" />
  </el-table>
</template>





<script lang="ts" setup>
import { ref } from 'vue'
import axios from 'axios'
const tableData = ref([])
const getEdges = () => {
    axios.get('/edges').then((response) => {
        console.log(response)
        tableData.value = response.data
    }, (error) => {
        console.log(error)
    })
    console.log('get edges!')
}

</script>
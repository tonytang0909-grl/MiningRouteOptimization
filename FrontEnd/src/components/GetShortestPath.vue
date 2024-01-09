<template>
<el-form :model="form" label-width="150px">
    <el-button type="success" @click="getEdges">Get Shortest Path</el-button>
    <el-form-item label="Source Device">
        <el-input v-model="form.src" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Target Device">
        <el-input v-model="form.dest" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="K Paths">
        <el-input v-model="form.k" style="width: 195px"/>
      </el-form-item>
    <el-table :data="tableData" style="width: 100%;">
    <el-table-column prop="nodes" label="Path" width="1800" />
    <el-table-column prop="cost" label="Cost"  />
  </el-table>
</el-form>
</template>





<script lang="ts" setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
const tableData = ref([])
const form = reactive({
    src: '',
    dest: '',
    k: '',
})
const getEdges = () => {
    axios.get('/oneOnOneShortPath', {params:{src: form.src, dest: form.dest, k: form.k}})
    .then((response) => {
        console.log(response)
        const transformedData = response.data.map(item => {
            return {
                ...item, // Spread the other properties (like cost)
                // Transform the nodes array
                nodes: item.nodes.map((node, index) => index === 0 ? node : '-> ' + node)
            };
        });
        console.log(transformedData)
        tableData.value = transformedData
    }, (error) => {
        console.log(error)
    })
    
    console.log('get edges!')
}

</script>

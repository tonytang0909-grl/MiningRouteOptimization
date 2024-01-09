<template>
    <el-form :model="form" label-width="150px">
    <el-form-item label="Source Device">
        <el-input v-model="form.source" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Destination Device">
        <el-input v-model="form.destination" style="width: 195px"/>
      </el-form-item>

      <!-- <el-form-item label="Device name">
        <el-input v-model="form.name" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Cost">
        <el-input v-model="form.cost" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Status">
        <el-select v-model="form.status" placeholder="Device Status">
          <el-option label="Available" value="available" />
          <el-option label="InUse" value="inuse" />
          <el-option label="Faulty" value="faulty" />
        </el-select> -->
      <!-- </el-form-item> -->
      <!-- <el-form-item label="Device Type">
        <el-select v-model="form.type" placeholder="Device Type">
          <el-option label="Source" value="source" />
          <el-option label="Destination" value="destination" />
          <el-option label="Normal" value="Normal" />
        </el-select>
      </el-form-item> -->
      
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Delete</el-button>
        <el-button >Cancel</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script lang="ts" setup>
  import { reactive } from 'vue'
  import axios from 'axios'
  import qs from 'qs'
import { formContextKey } from 'element-plus';
import { ElMessage } from 'element-plus'
  // do not use same name with ref
  const form = reactive({
    source: '',
    destination: '',
  })
//   const  updateNode = () => {
//     axios.get('/updateEdge').then((response) => {
//       console.log(response)
//     }, (error) => {
//       console.log(error)
//     })
//   }
  const onSubmit = () => {
    console.log(form.source)
    console.log(form.destination)
    let postData = qs.stringify({
      name: form.source,
      status: form.destination,
    })
    console.log(postData)
    axios.postForm('/deleteEdge', {source: form.source, destination: form.destination
    }).then((response) => {
      console.log(response.data)
      if (response.data == "Success") {
        ElMessage({
          message: 'Delete Success',
          type: 'success'
        })
        console.log(response)
        form.source = ''
        form.destination = ''
      } else {
        ElMessage({
          message: 'Delete Failed',
          type: 'error'
        })
      }
    }, (error) => {
      console.log(error)
    })
  }
  </script>
  

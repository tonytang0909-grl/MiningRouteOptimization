<template>
    <el-form :model="form" label-width="150px">
      <el-form-item label="Device name">
        <el-input v-model="form.name" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Cost">
        <el-input v-model="form.cost" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Status">
        <el-select v-model="form.status" placeholder="Device Status">
          <el-option label="Available" value="Available" />
          <el-option label="InUse" value="Inuse" />
          <el-option label="Faulty" value="Faulty" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="Device Type">
        <el-select v-model="form.type" placeholder="Device Type">
          <el-option label="Source" value="source" />
          <el-option label="Destination" value="destination" />
          <el-option label="Normal" value="Normal" />
        </el-select>
      </el-form-item> -->
      
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Update</el-button>
        <el-button @click="updateNode">Cancel</el-button>
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
    name: '',
    status: '',
    cost: '',
  })
  const  updateNode = () => {
    axios.get('/updateNode').then((response) => {
      console.log(response)
    }, (error) => {
      console.log(error)
    })
  }
  const onSubmit = () => {
    console.log(form.name)
    console.log(form.status)
    let postData = qs.stringify({
      name: form.name,
      status: form.status,
      cost: form.cost,
    })
    console.log(postData)
    axios.postForm('/updateNode', {name: form.name, status: form.status, cost: form.cost
    }).then((response) => {
      console.log(response.data)
      if (response.data == "Success") {
        ElMessage({
          message: 'Update Success',
          type: 'success'
        })
        console.log(response)
        form.name = ''
        form.status = ''
        form.cost = ''
      } else {
        ElMessage({
          message: 'Update Failed',
          type: 'error'
        })
      }
    }, (error) => {
      console.log(error)
    })
  }
  </script>
  

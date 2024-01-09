<template>
    <el-form :model="form" label-width="150px">
      <el-form-item label="Source Device">
        <el-input v-model="form.source" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Destination Device">
        <el-input v-model="form.desc" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Cost">
        <el-input v-model="form.cost" style="width: 195px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script lang="ts" setup>
  import axios from 'axios';
import { reactive } from 'vue'
import { ElMessage } from 'element-plus';
  
  // do not use same name with ref
  const form = reactive({
    source: '',
    desc: '',
    cost: '',
  })
  
  const onSubmit = () => {
    axios.postForm('/insertEdge', {src: form.source, dest: form.desc, cost: form.cost
    }).then((response) => {
      console.log(response)
      if (response.data == "Success") {
        ElMessage({
          message: 'Create Success',
          type: 'success'
        })
        
      } else {
        ElMessage({
          message: 'Create Failed',
          type: 'error'
        })
      }
      form.source = ''
      form.desc = ''
      form.cost = ''
    }, (error) => {
      console.log(error)
    })
    console.log('submit!')
  }
  </script>
  

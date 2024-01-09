<template>
    <el-form :model="form" label-width="150px">
      <el-form-item label="Device name">
        <el-input v-model="form.name" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Cost">
        <el-input v-model="form.cost" style="width: 195px"/>
      </el-form-item>
      <el-form-item label="Device Type">
        <el-select v-model="form.type" placeholder="Device Type">
          <el-option label="source" value="source" />
          <el-option label="detination" value="destination" />
          <el-option label="normal" value="normal" />
        </el-select>
      </el-form-item>
      <el-form-item label="Device Status">
        <el-select v-model="form.status" placeholder="Device Status">
          <el-option label="available" value="available" />
          <el-option label="inUse" value="inuse" />
          <el-option label="faulty" value="faulty" />
        </el-select>
      </el-form-item>

      
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script lang="ts" setup>
  import { reactive } from 'vue'
  import qs from 'qs'
  import { ElMessage } from 'element-plus';
  import axios from 'axios'
  // do not use same name with ref
  const form = reactive({
    name: '',
    type: '',
    status: '',
    cost: '',
  })
  
  let postData = qs.stringify({
    'name': form.name,
    'cost': form.cost,
    'type': form.type,
    'status': form.status,
  })
  const onSubmit = () => {
    axios.postForm('/insertNode', {name: form.name, type: form.type, status: form.status, cost: form.cost
    }).then((response) => {
      if (response.status == 200) {
        ElMessage({
          message: 'Create Success',
          type: 'success'
        })
        form.name = ''
        form.cost = ''
        form.type = ''
        form.status = ''
      }
      console.log(response)
    }, (error) => {
      ElMessage({
          message: 'Create Failed',
          type: 'error'
        })
      console.log(error)
    })
  }
  </script>
  

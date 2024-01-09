<template>
    <el-form :model="form" label-width="150px">
      <el-form-item label="Device name">
        <el-input v-model="form.name" style="width: 195px"/>
      </el-form-item>

      
      <el-form-item>
        <el-popconfirm
          width="220"
          confirm-button-text="OK"
          cancel-button-text="No, Thanks"
          :icon="InfoFilled"
          icon-color="#626AEF"
          title="Are you sure to delete this Device?"
          @confirm="deleteDevice"
        >
        <template #reference>
          <el-button>Delete</el-button>
        </template>
      </el-popconfirm>
      
      </el-form-item>
    </el-form>
  </template>
  
  <script lang="ts" setup>
  import { InfoFilled } from '@element-plus/icons-vue'
  import { reactive } from 'vue'
  import axios from 'axios'
  import { ElMessage } from 'element-plus';
  // do not use same name with ref
  const form = reactive({
    name: '',
  })
  
  

  const deleteDevice = () => {
    console.log(form.name)
    axios.get('/deleteNode', {params: {
      name: form.name,
    }}).then((response) => {
      form.name = ''
      console.log(response)
      if (response.data == "Success") {
        ElMessage({
          message: 'Delete Success',
          type: 'success'
        })
      }
      else {
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
  

<template>
    <el-upload
      class="upload-demo"
      drag
      action="http://localhost:8080/uploadFileTest"
      multiple
      :on-success="handleSuccess"
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">
        Drop file here or <em>click to upload</em>
      </div>
      <template #tip>
        <div class="el-upload__tip">
          xlsx files with a size less than 1MB
        </div>
      </template>
    </el-upload>
  </template>
  
  <script setup lang="ts">
  import { UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
import type { UploadProps } from 'element-plus'

  const handleSuccess: UploadProps['onSuccess'] = () => {
    console.log('Upload Success')
    ElMessage({
      message: 'Upload Success',
      type: 'success'
    })
  }
  </script>
  
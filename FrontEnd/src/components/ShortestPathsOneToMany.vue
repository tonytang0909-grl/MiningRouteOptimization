<template>
    <el-form
      ref="formRef"
      :model="dynamicValidateForm"
      label-width="120px"
      class="demo-dynamic"
    >
      <el-form-item
        prop="souce"
        label="Source"
        :rules="[
          {
            required: true,
            message: 'Please input source device',
            trigger: 'blur',
          },
          {
            type: 'source',
            message: 'Please input correct source device',
            trigger: ['blur', 'change'],
          },
        ]"
      >
        <el-input v-model="dynamicValidateForm.source" />
      </el-form-item>
      <el-form-item label="Top K">
        <el-input v-model="dynamicValidateForm.k" style="width: 195px"/>
      </el-form-item>
      <el-form-item
        v-for="(destination, index) in dynamicValidateForm.destinations"
        :label="'Destination' + index"
        :prop="'destinations.' + index + '.value'"
        :rules="{
          required: true,
          message: 'destination can not be null',
          trigger: 'blur',
        }"
      >
        <el-input v-model="destination.value" />
        <el-button class="mt-2" @click.prevent="removeDomain(destination)"
          >Delete</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()">Submit</el-button>
        <el-button @click="addDomain">New Destination</el-button>
        <el-button @click="resetForm(formRef)">Reset</el-button>
      </el-form-item>
    </el-form>
    

    <el-table :data="tableData" :border="true" style="width: 100%">
      <!-- <el-table-column label="#" prop="id"></el-table-column> -->
      <el-table-column label="#">
        <template #default="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="Overall Cost" prop="cost"></el-table-column>

      <el-table-column type="expand">
        <template #default="props">
          <div m="4">
            <h3>Details</h3>
            <el-table :data="props.row.paths" :border="true">
              <el-table-column label="Paths" prop="nodes">
                <template #default="scope">
                  <span v-for="(node, i) in scope.row.nodes" :key="i" :class="getNodeClass(node, props.$index)">
                    <!-- Prepend '->' to every node except the first one -->
                    <template v-if="i !== 0"> -> </template>{{ node }}
                  </span>
                </template>
              </el-table-column>

              <el-table-column label="subCost" prop="cost" />
            </el-table>
          </div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="id" prop="id" /> -->
      <!-- <el-table-column label="Source" prop="source" />
      <el-table-column label="Destination" prop="destinations" /> -->
      <!-- <el-table-column label="Total Cost" prop="cost" /> -->
    </el-table>
    
  </template>
  
  <script lang="ts" setup>
  import { reactive, ref } from 'vue'
  import type { FormInstance } from 'element-plus'
  import axios from 'axios';
  import GetMultiDestPaths from './GetMultiDestPaths.vue'
  // import { id } from 'element-plus/es/locale';
  const formRef = ref<FormInstance>()
  // const tableData = {}
  const tableData = ref([])
 
  
  const dynamicValidateForm = reactive<{
    destinations: DestinationItem[]
    source: string
    k: number
  }>({
    destinations: [
    ],
    source: '',
    k: 0,
  })
  
  interface DestinationItem {
    value: string
  }
  
  const getNodeClass = (node: string, index: number) => {
  return isCommonNode(node, index) ? 'bold-italic' : '';
};





const isCommonNode = (node: string, index: number) => {
  // Hard-coded common nodes for each index in the tableData array
  const commonNodes = [
    ['A', 'C'], // For the first entry (index 0)
    ['A', 'C'], // For the second entry (index 1)
    ['A']       // For the third entry (index 2)
  ];

  return commonNodes[index]?.includes(node) || false;
};








  const removeDomain = (item: DestinationItem) => {
    const index = dynamicValidateForm.destinations.indexOf(item)
    if (index !== -1) {
      dynamicValidateForm.destinations.splice(index, 1)
    }
  }
  
  const addDomain = () => {
    dynamicValidateForm.destinations.push({
        value: '',
    })
  }
  
  const submitForm = () => {
    //console.log(dynamicValidateForm.source)
    var postData = ''
    console.log(dynamicValidateForm.destinations)
    //console.log(Array.from(dynamicValidateForm.destinations))
    dynamicValidateForm.destinations.forEach((item) => {
        postData += item.value + ','
    })
    //console.log(postData)
    //console.log(JSON.stringify(dynamicValidateForm.destinations.map((item) => item.value)))
    
    axios.postForm('/shortPaths', {source: dynamicValidateForm.source, destinations: postData, k: dynamicValidateForm.k}).then((response) => {
        // console.log(response)
        // console.log(response.data[0])
        console.log(response.data)
        tableData.value = response.data
        //tableData.value = response.data
        //data.results = response.data
    }, (error) => {
        console.log(error)
    })

    // axios.get('/shortPaths', {params:{source: dynamicValidateForm.source, destinations: dynamicValidateForm.destinations.map((item) => item.value), k: dynamicValidateForm.k}}).then((response) => {
    //     console.log(response)
    // }, (error) => {
    //     console.log(error)
    // })
  }
//   const submitForm = (formEl: FormInstance | undefined) => {
//     if (!formEl) return
//     formEl.validate((valid) => {
//       if (valid) {
//         console.log('submit!')
//       } else {
//         console.log('error submit!')
//         return false
//       }
//     })
//   }
  
  const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
  }
  </script>

  
  <style>
  .el-form-item {
    width: 500px;
  }
  .el-table {
    margin-top: 50px;
  }

  .bold-italic {
    font-weight: bold;
    font-style: italic;
  }
    </style>
  
<template>
    <div>
      <!-- Use the transformed data with v-network-graph component -->
      <v-network-graph :nodes="nodes" :edges="transformedEdges" />
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        transformedEdges: [],  // initialized as an array
        nodes: []  // initialized as an array
      };
    },
    methods: {
      async fetchNodes() {
        try {
          const response = await axios.get('/node');
          this.nodes = response.data;
        } catch (error) {
          console.error("Error fetching nodes:", error);
        }
      },
      async fetchEdges() {
        try {
          const response = await axios.get('/edges');
          console.log(response.data);
            // this.transformedEdges = response.data;

  
          this.transformedEdges = response.data.map(edge => ({
                source: edge.src,
                target: edge.dest
            }));
          console.log(this.transformedEdges);
        } catch (error) {
          console.error("Error fetching edges:", error);
        }
      }
    },
    async mounted() {
      await this.fetchNodes();
      await this.fetchEdges();
    }
  };
  </script>
  
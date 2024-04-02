<script setup>
import { ref, watchEffect } from 'vue';
import Chart from 'chart.js/auto';

const props = defineProps({
  completeCount : Number,
  incompleteCount : Number
});

const chartRef = ref(null);
let myChart = null;

watchEffect(() => {
  if (myChart) {
    myChart.destroy();
  }
  createChart();
});

function createChart() {
  if (chartRef.value) {
    const ctx = chartRef.value.getContext('2d');
    myChart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['완료', '미완료'],
        datasets: [{
          label: '문장수',
          data: [props.completeCount, props.incompleteCount],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)'
          ],
          borderColor: [
            'rgba(255,99,132,1)',
            'rgba(54, 162, 235, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      }
    });
  }
}
</script>

<template>
  <div class="chart-container">
    <canvas ref="chartRef"></canvas>
  </div>
</template>

<style>
.chart-container {
  padding: 10px;
  transform: translateY(20px);
  animation: cardAppear 0.5s forwards ease-out;
}
@keyframes cardAppear {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
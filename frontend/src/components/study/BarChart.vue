<script setup>
import { ref, watchEffect } from 'vue';
import Chart from 'chart.js/auto';

const props = defineProps({
    averageScore: Number,
    minScore : Number,
    maxScore : Number,
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
      type: 'bar',
      data: {
        labels: ['평균점수', '최저점수', '최고점수'],
        datasets: [{
          label: '발음 점수',
          data: [props.averageScore, props.minScore, props.maxScore],
          backgroundColor: [
              'rgba(153, 102, 255, 0.2)',
              'rgba(54, 162, 235, 0.2)',
            'rgba(255, 99, 132, 0.2)',
          ],
          borderColor: [
              'rgba(153, 102, 255, 1)',
              'rgba(54, 162, 235, 1)',
            'rgba(255,99,132,1)',
          ],
          borderWidth: 1
        }]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            max: 100,
          }
        }
      }
    });
  }
}
</script>

<template>
  <div class="bar-chart-container">
    <canvas ref="chartRef"></canvas>
  </div>
</template>

<style>
.bar-chart-container {
  margin: 5%;
  height: 30%;
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
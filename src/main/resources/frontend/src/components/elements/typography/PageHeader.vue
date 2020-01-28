<template>
  <span :class="{ block }"><slot></slot></span>
</template>


<script>
export default {
  props: ['block']
}
</script>
<style scoped>

span {
  font-size: 32px;
  font-weight: bold;
  display: block;
  margin-bottom: 15px;
}

</style>
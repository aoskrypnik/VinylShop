<template>
  <span><slot></slot></span>
</template>

<style scoped>

span {
  font-size: 38px;
  font-weight: 800;
  line-height: 50px;
}

@media(min-width: 992px) {
  span {
    font-size: 50px;
    line-height: 60px;
  }
}

@media(min-width: 1200px) {
  span {
    font-size: 60px;
    line-height: 70px;
  }
}

</style>
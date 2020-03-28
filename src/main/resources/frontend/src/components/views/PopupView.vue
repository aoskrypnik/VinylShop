<template>
  <div class="popupRoot">
    <div class="veil" @click="close">

    </div>
    <div class="body">
      <div class="content">
        <popup-content :type="type" :properties="properties" @close="close"></popup-content>
      </div>
    </div>
  </div>
</template>

<script>
  import PopupContent from "./PopupContent";
  export default {
    name: "PopupView",
    components: {PopupContent},
    props: ['type', 'properties'],
    methods: {
      close() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>

  .popupRoot {
    position: fixed;
    top: 0;
    left: 0;
    height: 100vh;
    width: 100vw;
  }

  .veil {
    position: absolute;
    background: rgba(255, 255, 255, 0.5);
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
  }

  .body {
    border-radius: 20px;
    background: black;
    max-width: calc(100vw - 20px);
    max-height: calc(100vh - 100px);
    top: 50%;
    left: 50%;
    position: absolute;
    transform: translate(-50%, -50%);
    padding: 40px 0;
  }

  .content {
    overflow-y: scroll;
    padding: 0 40px;
    max-height: calc(100vh - 180px);
  }

</style>
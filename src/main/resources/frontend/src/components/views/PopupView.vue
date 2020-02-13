<template>
  <div>
    <div class="veil" @click="close">
      <div class="content">
        <popup-content :type="type" :properties="properties"></popup-content>
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

  .veil {
    position: fixed;
    background: rgba(255, 255, 255, 0.5);
    height: 100vh;
    width: 100vw;
    top: 0;
    left: 0;
  }

  .content {
    background: black;
    border-radius: 20px;
    max-width: calc(100vw - 20px);
    max-height: calc(100vh - 100px);
    overflow-y: scroll;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px 40px;
  }

  .content > * {
    margin-bottom: 40px;
  }

</style>
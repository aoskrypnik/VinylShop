<template>
  <div>
    <h1 class="header mb-4">{{$store.getters.getAppLocale('welcome')}}, {{username}}</h1>
    <h2 class="sectionHeader">{{$store.getters.getAppLocale('quickActions')}}</h2>
    <div class="buttonContainer s20 mb-4">
      <square-button
              v-for="action in getQuickActions"
              :key="action.label"

              :icon="action.icon"
              :modifier="action.modifier"

              @click="$router.push(action.route)"
      >{{$store.getters.getAppLocale(action.label)}}</square-button>
    </div>
    <h2 class="sectionHeader">{{$store.getters.getAppLocale('findHome')}}</h2>
    <div class="buttonContainer s10">
      <black-button
              v-for="schema in getAvailableSchemas"
              :key="schema"

              large="true"
              @click="$router.push({
              name: 'list',
              params: {
                schema
              }
            })"
      >
        {{getSchemasLocales[schema]}}
      </black-button>
    </div>
  </div>
</template>

<script>
  import { mapState, mapGetters } from 'vuex'

  import SquareButton from "@/components/elements/buttons/SquareButton";
  import BlackButton from "@/components/elements/buttons/BlackButton";

  export default {
    name: "HomeView",
    components: {BlackButton, SquareButton},
    computed: {
      ...mapState(['username']),
      ...mapGetters(['getQuickActions', 'getAvailableSchemas', 'getSchemasLocales'])
    }
  }
</script>

<style scoped>

  .sectionHeader {
    font-weight: bold;
    margin-bottom: 15px;
  }

  .header {
    font-weight: bold;
  }

  .buttonContainer {
    display: flex;
    align-content: flex-start;
    width: 100%;
    flex-wrap: wrap;
  }

  .buttonContainer.s20 > * {
     margin-right: 20px;
  }

  .buttonContainer.s10 > * {
    margin-right: 10px;
    margin-bottom: 10px;
  }

</style>
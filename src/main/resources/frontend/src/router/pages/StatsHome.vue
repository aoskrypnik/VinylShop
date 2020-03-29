<template>
  <div>
    <router-link :to="{ name: 'home' }" class="grayLink">{{$store.getters.getAppLocale('backToHome')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('statsAction')}}</page-header>
    <large-icon-button class="mb-3"
            icon="user"
            @click="$router.push({name: 'salesmen-stats'})"
    >
      {{$store.getters.getAppLocale('bySalesmen')}}
    </large-icon-button>
    <large-icon-button class="mb-3"
            icon="calendar"
            @click="$router.push({name: 'month-stats'})"
    >
      {{$store.getters.getAppLocale('byMonths')}}
    </large-icon-button>
    <large-icon-button class="mb-3"
                       icon="calendar"
                       @click="$router.push({name: 'specific-dates-stats'})"
    >
      {{$store.getters.getAppLocale('byDates')}}
    </large-icon-button>
  </div>
</template>

<script>
  import PageHeader from "@/components/elements/typography/PageHeader";
  import LargeIconButton from "@/components/elements/buttons/LargeIconButton";
  export default {
    name: "StatsHome",
    components: {LargeIconButton, PageHeader}
  }
</script>

<style scoped>

</style>
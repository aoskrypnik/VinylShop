<template>
  <div class="loginContainer">
    <div class="vertMidCont">
      <div class="row vertMidEl">
          <x-header class="col-md-6">Автоматизована інформаційна система</x-header>
          <div class="col-md-6 loginArea">
            <black-input placeholder="Логін" :large="true"></black-input>
            <black-input placeholder="Пароль" password="true" :large="true"></black-input>
            <black-button large="true">Вхід</black-button>
          </div>
      </div>
    </div>
  </div>  
</template>

<script>
import XHeader from '../elements/typography/XHeader'
import BlackButton from '../elements/buttons/BlackButton'
import BlackInput from '../elements/inputs/BlackInput'

export default {
  components: {
    XHeader,
    BlackButton,
    BlackInput
  }
}
</script>

<style scoped>
.loginContainer {
  color: white;
}

.vertMidCont {
  position: relative;
  height: calc(100vh - 70px);
}

.vertMidEl {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.loginArea {
  margin-top: 20px;
}
</style>
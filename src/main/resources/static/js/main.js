
Vue.component('item-row', {
    props: ['item'],
    template:
    '<tr>' +
        '<td class="align-middle"><span class="cursor" onclick="copy(this)">{{ item.firstService.name }}</span></td>' +
        '<td class="align-middle text-center">{{ item.firstService.price }}$<br>[{{ item.firstService.amount}}/{{ item.firstService.max}}]</td>' +
        '<td class="align-middle text-center">{{ item.secondService.price }}$<br>[{{ item.secondService.amount}}/{{ item.secondService.max }}]</td>' +
        '<td class="align-middle text-center">{{ item.firstToSecondProfit }}%</td>' +
        '<td class="align-middle text-center">{{ item.secondToFirstProfit }}%</td>' +
    '</tr>'
});

Vue.component('items-list',{
    props: ['items', 'apiUrl', 'gameId', 'params'],
    template:
    '<tbody>' +
        '<item-row v-for="item in items" :key="item.id ":item="item" />' +
    '</tbody>',
    created: function() {
        this.getTemp();
    },
    mounted: function(){
        setInterval(() => {
            this.getTemp();
        }, 3000)
    },
    methods: {
        getTemp: function() {
            axios
                .get(this.apiUrl + this.gameId, {params:this.params})
                .then(result => app.items = result.data)
        }
    }
});

const app = new Vue({
  el: '#items',
  template: '<items-list :items="items" :apiUrl="apiUrl" :gameId="gameId" :params="params" />',
  data: {
    items: [],
    apiUrl: 'http://localhost:8080/api/trade/getComparedWithFullParams/',
    gameId: 730,
    params: {
        firstMarket: 'lootfarm',
        secondMarket: 'tradeit',
        minPrice: 0.0,
        maxPrice: 10000.0,
        isOverStocked: false,
        sortOrder: 'none',

        firstServiceMinCount: 0,
        firstServiceMaxCount: 10000,
        secondServiceMinCount: 0,
        secondServiceMaxCount: 10000,

        firstToSecondMinPerCent: -1000.0,
        firstToSecondMaxPerCent: 1000.0,
        secondToFirstMinPerCent: -1000.0,
        secondToFirstMaxPerCent: 1000.0,

        itemName: ''
    }
  }
});
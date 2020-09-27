Vue.component('item-row', {
    props: ['item'],
    template:
    '<tr>' +
        '<td class="align-middle"><span class="cursor" onclick="copy(this)">{{ item.name }}</span></td>' +
        '<td class="align-middle text-center">{{ item.firstPrice }}$<br>[{{ item.firstAmount}}/{{ item.firstMax}}]</td>' +
        '<td class="align-middle text-center">{{ item.secondPrice }}$<br>[{{ item.secondAmount}}/{{ item.secondMax }}]</td>' +
        '<td class="align-middle text-center">{{ item.secondToFirstPercent }}%</td>' +
        '<td class="align-middle text-center">{{ item.firstToSecondPercent }}%</td>' +
    '</tr>'
});

Vue.component('items-list',{
    props: ['items', 'apiUrl', 'gameId', 'params'],
    template:
    '<tbody>' +
        '<item-row v-for="item in items" :key="item.id ":item="item" />' +
    '</tbody>',
    created: function() {
        this.getRequest();
    },
    mounted: function(){
        setInterval(() => {
            this.getRequest();
        }, 3000)
    },
    methods: {
        getRequest: function() {
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
        itemName: '',

        firstServiceMinCount: 0,
        firstServiceMaxCount: 10000,
        secondServiceMinCount: 0,
        secondServiceMaxCount: 10000,

        firstToSecondMinPerCent: -1000.0,
        firstToSecondMaxPerCent: 1000.0,
        secondToFirstMinPerCent: -1000.0,
        secondToFirstMaxPerCent: 1000.0,

        itemSize: 25,
        timeFromLastUpdate: '24:00:00'
    }
  }
});
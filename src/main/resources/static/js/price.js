Vue.component('item-row', {
    props: ['item', 'markets'],
    template:
    '<tr>' +
        '<td class="align-middle"><span v-for="market in markets" v-if="item.marketId == market.id">{{ market.name.toUpperCase() }}</span></td>' +
        '<td class="align-middle text-center">{{ item.price }}$<br>[{{ item.amount }}/{{ item.max }}] <i class="fas fa-exclamation-circle fa-xs text-danger" v-if="item.amount >= item.max"></i></td>' +
        '<td class="align-middle text-center">[{{ item.updateDateTime.dayOfMonth }}-{{ item.updateDateTime.monthValue }}-{{ item.updateDateTime.year }}]<br>[{{ item.updateDateTime.hour }}:{{ item.updateDateTime.minute }}:{{ item.updateDateTime.second }}]</td>' +
    '</tr>'
});

Vue.component('items-list',{
    props: ['items', 'apiUrl', 'params', 'markets'],
    template:
    '<tbody>' +
        '<item-row v-for="item in items" :key="item.id " :item="item" :markets="markets"/>' +
    '</tbody>',
    created: function() {
        this.getRequest();
        this.getMarkets();
    },
    mounted: function(){
        setInterval(() => {
            this.getRequest();
        }, 3000)
    },
    methods: {
        getRequest: function() {
            axios
                .get(this.apiUrl, {params:this.params})
                .then(result => app.items = result.data)
        },
        getMarkets: function() {
            axios
            .get('http://localhost:8080/api/trade/getMarkets')
            .then(result => app.markets = result.data)
        }
    }
});

const app = new Vue({
  el: '#items',
  template: '<items-list :items="items" :apiUrl="apiUrl" :params="params" :markets="markets"/>',
  data: {
    items: [],
    markets: [],
    apiUrl: 'http://localhost:8080/api/trade/getByName',
    params: {
        name: ''
    }
  }
});
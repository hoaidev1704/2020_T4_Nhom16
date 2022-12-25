<template>
  <a-table
    class="ant-table-striped"
    size="middle"
    :columns="columns"
    :data-source="dataTable"
  >
    <template #id="{ text }">
      <a>{{ text }}</a>
    </template>
    <template
      #customFilterDropdown="{
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
        column,
      }"
    >
      <div style="padding: 8px">
        <a-input
          ref="searchInput"
          :placeholder="`Search ${column.dataIndex}`"
          :value="selectedKeys[0]"
          style="width: 188px; margin-bottom: 8px; display: block"
          @change=" (e : any) => setSelectedKeys(e.target.value ? [e.target.value] : [])"
          @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
        />
        <a-button
          type="primary"
          size="small"
          style="width: 90px; margin-right: 8px"
          @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          <template #icon><SearchOutlined /></template>
          Search
        </a-button>
        <a-button
          size="small"
          style="width: 90px"
          @click="handleReset(clearFilters)"
        >
          Reset
        </a-button>
      </div>
    </template>
    <template #customFilterIcon="{ filtered }">
      <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }" />
    </template>

    <template #bank="{ text }">
      <a :id="text.id">{{ text.name }}</a>
    </template>
    <template #currency="{ text }">
      <a :id="text.id">{{ text.symbol }} - {{ text.name }}</a>
    </template>
  </a-table>
  <a-button
    type="primary"
    class="text-left mt-5"
    @click="handleDowload(dataTable)"
    >Dowload <DownloadOutlined
  /></a-button>
</template>
<script lang="ts">
import { SearchOutlined, DownloadOutlined } from "@ant-design/icons-vue";
import { TableColumnType } from "ant-design-vue";
import { NumberDecimal } from "ant-design-vue/lib/input-number/src/utils/MiniDecimal";
import { log } from "console";
import { defineComponent, onMounted, reactive, ref } from "vue";
import { getDataTable } from "../../../api/TableAPI/table";

interface Bank {
  id: string;
  name: string;
}

interface Filter {
  text: string;
  value: string;
}

interface Currency {
  id: string;
  symbol: string;
  name: string;
}

interface Exchange {
  id: string;
  getDate: string;
  updateDate: string;
  url: string;
  buyCash: number;
  buyTransfer: number;
  price: NumberDecimal;
  bank: Bank;
  currency: Currency;
}

export default defineComponent({
  components: {
    SearchOutlined,
    DownloadOutlined,
  },
  setup() {
    const dataTable = ref<Exchange>();
    const state = reactive({
      searchText: "",
      searchedColumn: "",
    });
    const searchInput = ref();
    const getData = () => {
      getDataTable().then((res: any) => {
        dataTable.value = res.data as Exchange;
      });
    };
    const columns: TableColumnType<Exchange>[] = [
      { title: "Id", dataIndex: "id", slots: { customRender: "id" } },
      {
        title: "Bank Name",
        dataIndex: "bank",
        slots: { customRender: "bank" },
        customFilterDropdown: true,
        onFilter: (value, record) =>
          record.bank.name
            .toString()
            .toLowerCase()
            .includes(value.toString().toLowerCase()),
        onFilterDropdownVisibleChange: (visible) => {
          if (visible) {
            setTimeout(() => {
              searchInput.value.focus();
            }, 100);
          }
        },
      },
      {
        title: "Name Currency",
        dataIndex: "currency",
        slots: { customRender: "currency" },
      },
      { title: "Price", dataIndex: "price" },
      {
        title: "Buy Cash",
        dataIndex: "buyCash",
        sorter: (a: Exchange, b: Exchange) => a.buyCash - b.buyCash,
      },
      { title: "Buy Transfer", dataIndex: "buyTransfer" },
      { title: "Get Date", dataIndex: "getDate" },
      { title: "Update Date", dataIndex: "updateDate" },
      { title: "Url Source", dataIndex: "urlSource" },
    ];
    onMounted(() => {
      getData();
    });
    const handleDowload = (data: any) => {
      let dataDowLoad: any = [];   
      
      data =
        data.map((e: any) => {
          return {
            id: e.id,
            urlSource: e.urlSource,
            bankName: e.bank.name,
            currencyName: e.currency.name,
            price: e.price,
            buyCash: e.buyCash,
            buyTransfer: e.buyTransfer,
            getDate: e.getDate,
            updateDate: e.updateDate,
          };
        });

      const headers: any = [];

      columns.map((e: any) => {
        console.log(e.title);
        headers.push(e.title);
      });

      dataDowLoad.push(headers);

      data.map((e: any) => dataDowLoad.push(Object.values(e)));

      let csv: string = "";

      dataDowLoad.forEach(function (row: any) {
        csv += row.join(",");
        csv += "\n";
      });

      const hiddenElement = document.createElement("a");
      hiddenElement.href = "data:text/csv;charset=utf-8," + encodeURI(csv);
      hiddenElement.target = "_blank";
      hiddenElement.download = `dowload.csv`;
      hiddenElement.click();
    };
    const handleSearch = (selectedKeys: any, confirm: any, dataIndex: any) => {
      confirm();
      state.searchText = selectedKeys[0];
      state.searchedColumn = dataIndex;
    };
    const handleReset = (clearFilters: any) => {
      clearFilters({
        confirm: true,
      });
      state.searchText = "";
    };

    return {
      handleSearch,
      handleReset,
      columns,
      dataTable,
      handleDowload,
    };
  },
});
</script>
<style scoped>
.ant-table-striped :deep(.table-striped) td {
  background-color: #fafafa;
}
</style>

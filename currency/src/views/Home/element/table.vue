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
    >Dowload</a-button
  >
</template>
<script lang="ts">
import { NumberDecimal } from "ant-design-vue/lib/input-number/src/utils/MiniDecimal";
import { defineComponent, onMounted, ref } from "vue";
import { getDataTable } from "../../../api/TableAPI/table";

const columns = [
  { title: "Id", dataIndex: "id", slots: { customRender: "id" } },
  { title: "Bank Name", dataIndex: "bank", slots: { customRender: "bank" } },
  {
    title: "Name Currency",
    dataIndex: "currency",
    slots: { customRender: "currency" },
  },
  { title: "Price", dataIndex: "price" },
  { title: "Buy Cash", dataIndex: "buyCash" },
  { title: "Buy Transfer", dataIndex: "buyTransfer" },
  { title: "Get Date", dataIndex: "getDate" },
  { title: "Update Date", dataIndex: "updateDate" },
  { title: "Url Source", dataIndex: "urlSource" },
];
interface Bank {
  id: string;
  name: string;
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
  buyCash: string;
  buyTransfer: string;
  price: NumberDecimal;
  bank: Bank;
  currency: Currency;
}

export default defineComponent({
  setup() {
    const dataTable = ref<Exchange>();
    const getData = () => {
      getDataTable().then((res: any) => {
        dataTable.value = res.data as Exchange;
        console.log(res, "res");
      });
    };
    onMounted(() => {
      getData();
    });
    const handleDowload = (data: any) => {
      let csv = "";
      data.forEach(function (row: any) {
        csv += row.join(",");
        csv += "\n";
      });    
      const hiddenElement = document.createElement("a");
      hiddenElement.href = "data:text/csv;charset=utf-8," + encodeURI(csv);
      hiddenElement.target = "_blank";
      hiddenElement.download = `dowload.csv`;
      hiddenElement.click();
    };
    return {
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

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
import moment from "moment";
import { defineComponent, onMounted, ref, watch } from "vue";
import { getDataTable } from "../../../api/TableAPI/table";
import { useSearch } from "../../../composables";

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
    const dataTable = ref<any>();
    const { keySearch, dateSelect, dataProject } = useSearch();

    const getData = () => {
      getDataTable().then((res: any) => {
        dataTable.value = res.data as Exchange;
        dataProject.value = res.data;
      });
    };
    watch(
      () => keySearch.value,
      () => {
        if (keySearch.value != "") {
          dataTable.value = dataTable.value.filter((e: any) => {
            return (
              e.bank.name
                .toLowerCase()
                .includes(keySearch.value.toLowerCase()) ||
              e.currency.name
                .toLowerCase()
                .includes(keySearch.value.toLowerCase())
            );
          });
        } else {
          return (dataTable.value = dataProject.value);
        }
      }
    );
    watch(
      () => dateSelect.value,
      () => {
        if (dateSelect.value) {
          dataTable.value = dataTable.value.filter((e: any) => {
            const date = new Date(dateSelect.value);
            const getDate = new Date(e.getDate);
            return (
              moment(getDate).format("D/MM/YYYY") ===
              moment(date).format("D/MM/YYYY")
            );
          });
        } else {
          return (dataTable.value = dataProject.value);
        }
      }
    );
    onMounted(() => {
      getData();
    });
    const handleDowload = (data: any) => {
      let dataDowLoad: any = [];

      data = data.map((e: any) => {
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

      const headers = Object.keys(data[0]);

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

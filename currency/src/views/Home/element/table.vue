<template>
  <a-table
    class="ant-table-striped"
    size="middle"
    :columns="columns"
    :data-source="dataTable"
  />
</template>
<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import { getDataTable } from "../../../api/TableAPI/table";

const columns = [
  { title: "Id", dataIndex: "id" },
  { title: "Get Date", dataIndex: "getDate" },
  { title: "Update Date", dataIndex: "updateDate" },
  { title: "Url Source", dataIndex: "urlSource" },
  { title: "Buy Cash", dataIndex: "buyCash" },
  { title: "Buy Transfer", dataIndex: "buyTransfer" },
  { title: "Price", dataIndex: "price" },
  { title: "Bank Name", dataIndex: "bank" },
  { title: "Name Currency", dataIndex: "currency" },
];

export default defineComponent({
  setup() {
    const dataTable = ref([]);
    const getData = () => {
      getDataTable().then((res: any) => {
        dataTable.value = res.data;
        console.log(res, "res");
      });
    };
    onMounted(() => {
      getData();
    });
    return {
      columns,
      dataTable,
    };
  },
});
</script>
<style scoped>
.ant-table-striped :deep(.table-striped) td {
  background-color: #fafafa;
}
</style>

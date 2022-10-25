<template>
  <A-layout style="min-height: 100vh">
    <a-layout-sider
      v-model:collapsed="collapsed"
      collapsible
      class="bg-blue-500 py-3"
    >
      <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="inline"
        class="bg-transparent"
      >
        <div
          class="logo-bank text-md text-left px-4 pb-5 text-white font-normal leading-8"
        >
          Currency
        </div>
        <a-menu-item key="1" class="bg-blue-400">
          <div class="items-center flex text-white">
            <HomeOutlined />
            <span class="text-md font-normal">Currency</span>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header
        style="background: #fff; padding: 0"
        class="w-full px-4"
      >
        <div class="flex justify-between px-3 items-center mb-2">
          <a-input-search
            v-model:value="valueSearch"
            placeholder="input search text"
            enter-button
            @search="onSearch"
            class="w-1/3"
            size="large"
          />
          <a-space direction="vertical" class="w-1/2 flex">
            <a-date-picker
              class="w-2/3"
              v-model:value="startValue"
              show-time
              format="YYYY-MM-DD"
              placeholder="Date"
              @openChange="handleStartOpenChange"
              size="large"
              
            />
          </a-space>
        </div>
      </a-layout-header>
      <a-layout-content class="pt-10 pb-5 px-2">
        <Table />
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
  </A-layout>
</template>
<script lang="ts">
import moment, { Moment } from "moment";
import { HomeOutlined } from "@ant-design/icons-vue";
import { defineComponent, ref } from "vue";
import Table from "./element/table.vue";
export default defineComponent({
  components: {
    HomeOutlined,
    Table,
  },
  setup() {
    const startValue = ref<Moment | undefined>();

    const valueSearch = ref<string>("");

    const onSearch = (searchValue: string) => {
      console.log("use value", searchValue);
      console.log("or use this.value", valueSearch.value);
    };

    const handleStartOpenChange = (date: any) => {};
    const today = new Date();

    startValue.value = moment(today, "DD/MM/YYYY");
    return {
      collapsed: ref<boolean>(false),
      selectedKeys: ref<string[]>(["1"]),
      onSearch,
      valueSearch,
      startValue,
      handleStartOpenChange,
      moment,
    };
  },
});
</script>
<style lang="css" scoped></style>

import { reactive, toRefs } from "vue";

const state = reactive({
  keySearch: "",
  dateSelect: "",
  dataProject: [],
});

export default function useSearch() {
  return { ...toRefs(state) };
}

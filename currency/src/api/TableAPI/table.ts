import api from "../config";

export const getDataTable = async () => {
  return await api.post("all");
};

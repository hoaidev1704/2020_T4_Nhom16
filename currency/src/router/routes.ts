const HomePage = () => import("../views/Home");

const routes = [
  {
    path: "/",
    redirect: { name: "HomePage" },
  },
  {
    path: "/",
    name: "HomePage",
    component: HomePage,    
  },
];

export default routes;

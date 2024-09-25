import Navbar from './Navbar';
import { Outlet } from "react-router-dom";

function Layout() {
  return (
    <>
      <Navbar /> {/* Navbar will be displayed on every page */}
      <Outlet />  {/* The rest of the page content will be rendered here */}
    </>
  );
}

export default Layout;

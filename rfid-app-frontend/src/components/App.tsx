import React from 'react';
import { HashRouter, Redirect, Route, Switch} from 'react-router-dom';
import OrderPage from "../pages/OrderPage";
import ProductionPage from '../pages/ProductionPage';

export default function App() {
  return (
        <HashRouter basename='/'>
          <Switch>
              <Route path='/production'>
                  <ProductionPage />
              </Route>
              <Route path='/order'>
                  <OrderPage />
              </Route>
              <Redirect to='/order' />
          </Switch>
        </HashRouter>
  );
}

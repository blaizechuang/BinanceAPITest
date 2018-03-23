package com.dpt.ileopard.binanceapitest.binanceModule;

import com.binance.api.client.BinanceApiClientFactory;

/**
 * Created by Blaize on 2018/3/23.
 */

public class base {
    public void init() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiconst.API, apiconst.SECRET);
    }
}

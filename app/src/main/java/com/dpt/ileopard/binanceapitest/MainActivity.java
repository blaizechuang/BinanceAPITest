package com.dpt.ileopard.binanceapitest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.TradeHistoryItem;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import com.dpt.ileopard.binanceapitest.binanceModule.apiconst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BinanceApiClientFactory mFactory;
    private BinanceApiRestClient mClient;
    private BinanceApiAsyncRestClient mAsyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // Client
        //getServerTime();
        //getData();
        //getAllPrices();
        getTradeList();

        // AsyncClient
        //getBalance2();
    }

    public void init() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiconst.API, apiconst.SECRET);
        if (factory != null) {
            mClient = factory.newRestClient();
            mAsyncClient = factory.newAsyncRestClient();
        }
    }

    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void getBalance() {
        mAsyncClient.getAccount(new BinanceApiCallback<Account>() {
            @Override
            public void onResponse(Account account) {
                List<AssetBalance> balances = account.getBalances();
                for (AssetBalance balance : balances) {
                    if (!balance.getFree().equals("0.00000000")) {
                        Log.v("log1", "[" + balance.getAsset() + "]: " + balance.getFree());
                    }
                }
            }
        });
    }

    private void getServerTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Check server time
                long serverTime = mClient.getServerTime();
                Log.v("log1", "ServerTime: " + serverTime);
            }
        }).start();
    }

    private void getBalance2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AssetBalance> balances = mClient.getAccount().getBalances();
                for (AssetBalance balance : balances) {
                    if (!balance.getFree().equals("0.00000000")) {
                        Log.v("log1", "[" + balance.getAsset() + "]: " + balance.getFree());
                    }
                }
            }
        }).start();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TickerStatistics tickerStatistics = mClient.get24HrPriceStatistics("NEOBTC");
                Log.v("log1", "statistics: " + tickerStatistics.toString());
            }
        }).start();
    }

    private void getAllPrices() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TickerPrice> prices = mClient.getAllPrices();
                for (TickerPrice price : prices) {
                    Log.v("log1", "[: " + price.getSymbol() + "]: " + price.getPrice());
                }
            }
        }).start();
    }

    private void getTradeList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TradeHistoryItem> history = mClient.getTrades("BTCUSDT", 10);
                if (history != null) {
                    for (TradeHistoryItem item : history) {
                        Log.v("log1", "Time: " + getDate(item.getTime()) + ", price: " + item.getPrice() + ", num: " + item.getQty());
                    }
                }
            }
        }).start();
    }
}

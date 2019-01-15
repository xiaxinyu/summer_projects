package com.data.source.core;

import com.data.source.impl.MyPoolImpl;

public class PoolManager {
    private static class createPool {
        private static MyPoolImpl poolImpl = new MyPoolImpl();
    }
    
    public static MyPoolImpl getInstance() {
        return createPool.poolImpl;
    }
}

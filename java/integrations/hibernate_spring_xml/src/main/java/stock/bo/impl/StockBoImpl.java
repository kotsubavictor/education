package stock.bo.impl;

import stock.bo.StockBo;
import stock.dao.StockDao;
import stock.model.Stock;

import javax.transaction.Transactional;

public class StockBoImpl implements StockBo {

    private StockDao stockDao;

    public void setStockDao(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Transactional
    @Override
    public void save(Stock stock){
        stockDao.save(stock);
    }

    @Transactional
    @Override
    public void update(Stock stock){
        stockDao.update(stock);
    }

    @Transactional
    @Override
    public void delete(Stock stock){
        stockDao.delete(stock);
    }

    @Transactional
    @Override
    public Stock findByStockCode(String stockCode){
        return stockDao.findByStockCode(stockCode);
    }
}

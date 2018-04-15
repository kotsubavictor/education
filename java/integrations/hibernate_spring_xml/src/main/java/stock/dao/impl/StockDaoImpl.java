package stock.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import stock.dao.StockDao;
import stock.model.Stock;

import java.util.List;

public class StockDaoImpl implements StockDao {

    private SessionFactory sessionFactory;

    @Override
    public void save(Stock stock){
        sessionFactory.getCurrentSession().save(stock);
    }

    @Override
    public void update(Stock stock){
        sessionFactory.getCurrentSession().update(stock);
    }

    @Override
    public void delete(Stock stock){
        sessionFactory.getCurrentSession().delete(stock);
    }

    @Override
    public Stock findByStockCode(String stockCode){
        List<Stock> list = sessionFactory.getCurrentSession().createQuery(
                "from Stock where stockCode=:param", Stock.class
        ).setParameter("param", stockCode).list();
        return list.get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

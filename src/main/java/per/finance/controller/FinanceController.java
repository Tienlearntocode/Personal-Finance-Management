package per.finance.controller;

import per.finance.dao.InvestmentDAO;
import per.finance.model.Investment;
import java.util.List;

public class FinanceController {
    private final InvestmentDAO investmentDAO;

    public FinanceController() {
        this.investmentDAO = new InvestmentDAO();
    }

    public List<Investment> getAllInvestments() {
        return investmentDAO.getAllInvestments();
    }

    public boolean addInvestment(Investment investment) {
        return investmentDAO.insertInvestment(investment);
    }

    public boolean deleteInvestment(int id) {
        return investmentDAO.deleteInvestment(id);
    }
}
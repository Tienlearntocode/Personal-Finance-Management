/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    // Khởi tạo các panel thành viên
    private HomePanel homePanel;
    private ExpensePanel expensePanel;
    private IncomePanel incomePanel;
    private TransactionPanel transactionPanel;
    private InvestmentPanel investmentPanel;

    public MainFrame() {
        setTitle("Personal Finance Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Sidebar bên trái (Menu điều hướng)
        JPanel sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // 2. Vùng hiển thị chính dùng CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Khởi tạo các view của từng thành viên
        homePanel = new HomePanel();
        expensePanel = new ExpensePanel();
        incomePanel = new IncomePanel();
        transactionPanel = new TransactionPanel();
        investmentPanel = new InvestmentPanel();

        // Thêm vào CardLayout với key định danh
        mainContentPanel.add(homePanel, "HOME");
        mainContentPanel.add(expensePanel, "EXPENSE");
        mainContentPanel.add(incomePanel, "INCOME");
        mainContentPanel.add(transactionPanel, "TRANSACTION");
        mainContentPanel.add(investmentPanel, "INVESTMENT");

        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        JButton btnHome = new JButton("Home");
        JButton btnExpense = new JButton("New Expense");
        JButton btnIncome = new JButton("Income");
        JButton btnTransaction = new JButton("Transaction");
        JButton btnInvestment = new JButton("Investment");

        btnHome.addActionListener(e -> cardLayout.show(mainContentPanel, "HOME"));
        btnExpense.addActionListener(e -> cardLayout.show(mainContentPanel, "EXPENSE"));
        btnIncome.addActionListener(e -> cardLayout.show(mainContentPanel, "INCOME"));
        btnTransaction.addActionListener(e -> cardLayout.show(mainContentPanel, "TRANSACTION"));
        btnInvestment.addActionListener(e -> cardLayout.show(mainContentPanel, "INVESTMENT"));

        sidebar.add(btnHome);
        sidebar.add(btnExpense);
        sidebar.add(btnTransaction);
        sidebar.add(btnIncome);
        sidebar.add(btnInvestment);

        return sidebar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
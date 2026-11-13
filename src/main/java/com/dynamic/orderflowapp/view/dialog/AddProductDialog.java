
package com.dynamic.orderflowapp.view.dialog;

import com.dynamic.orderflowapp.config.SpringContext;
import com.dynamic.orderflowapp.dao.CategoryDAO;
import com.dynamic.orderflowapp.dao.ProductDAO;
import com.dynamic.orderflowapp.entity.Category;
import com.dynamic.orderflowapp.entity.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class AddProductDialog extends JDialog {

    private JTextField txtCode;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtStock;
    private JTextField txtUnit;
    private JTextArea txtDescription;
    private JComboBox<Category> cmbCategory;

    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final Runnable onSaved;

    private final Color NAVY = new Color(24, 39, 63);
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color BORDER = new Color(215, 222, 232);

    public AddProductDialog(JFrame parent, Runnable onSaved) {
        super(parent, "Add New Product", true);

        this.onSaved = onSaved;

        categoryDAO = SpringContext.getBean(CategoryDAO.class);
        productDAO = SpringContext.getBean(ProductDAO.class);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createForm(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        setContentPane(mainPanel);

        loadCategories();

        setSize(500, 680);
        setLocationRelativeTo(parent);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("Add New Product");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Enter product information below.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(205, 215, 230));

        header.add(title);
        header.add(Box.createVerticalStrut(7));
        header.add(subtitle);

        return header;
    }

    private JPanel createForm() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(BACKGROUND);
        form.setBorder(new EmptyBorder(20, 30, 20, 30));

        txtCode = createTextField();
        txtName = createTextField();
        txtPrice = createTextField();
        txtStock = createTextField();
        txtUnit = createTextField();

        cmbCategory = new JComboBox<>();
        styleComboBox(cmbCategory);

        txtDescription = new JTextArea(4, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setForeground(TEXT);
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane descriptionScroll = new JScrollPane(txtDescription);
        descriptionScroll.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
        descriptionScroll.setPreferredSize(new Dimension(400, 90));
        descriptionScroll.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 90)
        );

        addField(form, "Product Code", txtCode);
        addField(form, "Product Name", txtName);
        addField(form, "Category", cmbCategory);
        addField(form, "Price", txtPrice);
        addField(form, "Stock", txtStock);
        addField(form, "Unit", txtUnit);
        addField(form, "Description", descriptionScroll);

        return form;
    }

    private void addField(JPanel panel, String labelText, JComponent input) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        input.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                input instanceof JScrollPane ? 90 : 40));

        panel.add(label);
        panel.add(Box.createVerticalStrut(7));
        panel.add(input);
        panel.add(Box.createVerticalStrut(13));
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 12, 0, 12)
        ));

        return field;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(BORDER));
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 15)
        );

        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(5, 20, 5, 25));

        JButton btnCancel = createButton(
                "Cancel",
                new Color(235, 238, 243),
                TEXT
        );

        JButton btnSave = createButton(
                "Save Product",
                NAVY,
                Color.WHITE
        );

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveProduct());

        footer.add(btnCancel);
        footer.add(btnSave);

        return footer;
    }

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(125, 38));

        return button;
    }

    private void loadCategories() {
        try {
            cmbCategory.removeAllItems();

            for (Category category : categoryDAO.findAll()) {
                cmbCategory.addItem(category);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load categories: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveProduct() {
        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        String price = txtPrice.getText().trim();
        String stock = txtStock.getText().trim();
        String unit = txtUnit.getText().trim();

        Category category = (Category) cmbCategory.getSelectedItem();

        if (code.isEmpty() || name.isEmpty()
                || price.isEmpty() || stock.isEmpty()
                || unit.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all required fields!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (category == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please add and select a category first!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            BigDecimal productPrice = new BigDecimal(price);
            int productStock = Integer.parseInt(stock);

            if (productPrice.compareTo(BigDecimal.ZERO) < 0
                    || productStock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price and stock cannot be negative!",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Product product = new Product();

            product.setProductCode(code);
            product.setProductName(name);
            product.setCategory(category);
            product.setPrice(productPrice);
            product.setStock(productStock);
            product.setUnit(unit);
            product.setDescription(txtDescription.getText().trim());
            product.setStatus("ACTIVE");

            productDAO.save(product);

            JOptionPane.showMessageDialog(
                    this,
                    "Product successfully added!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (onSaved != null) {
                onSaved.run();
            }

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Price or stock format is invalid!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save product: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

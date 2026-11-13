
package com.dynamic.orderflowapp.view.dialog;

import com.dynamic.orderflowapp.config.SpringContext;
import com.dynamic.orderflowapp.dao.CategoryDAO;
import com.dynamic.orderflowapp.entity.Category;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddCategoryDialog extends JDialog {

    private JTextField txtCategory;
    private JTextArea txtDescription;

    private final CategoryDAO categoryDAO;

    private final Color NAVY = new Color(24, 39, 63);
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(120, 130, 145);

    public AddCategoryDialog(JFrame parent) {
        super(parent, "Add New Category", true);

        categoryDAO = SpringContext.getBean(CategoryDAO.class);

        setSize(480, 430);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createForm(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("Add New Category");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Create a category to organize your products.");
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
        form.setBorder(new EmptyBorder(25, 30, 20, 30));

        JLabel categoryLabel = createLabel("Category Name");

        txtCategory = new JTextField();
        styleTextField(txtCategory);
        txtCategory.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel descriptionLabel = createLabel("Description");

        txtDescription = new JTextArea(5, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setForeground(TEXT);
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(215, 222, 232)));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        scrollPane.setPreferredSize(new Dimension(400, 110));

        form.add(categoryLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(txtCategory);

        form.add(Box.createVerticalStrut(20));

        form.add(descriptionLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(scrollPane);

        return form;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(5, 20, 5, 25));

        JButton btnCancel = createButton("Cancel", new Color(235, 238, 243), TEXT);
        JButton btnSave = createButton("Save Category", NAVY, Color.WHITE);

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveCategory());

        footer.add(btnCancel);
        footer.add(btnSave);

        return footer;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 222, 232)),
                new EmptyBorder(0, 12, 0, 12)
        ));
    }

    private JButton createButton(String text, Color background, Color foreground) {
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

    private void saveCategory() {
        String name = txtCategory.getText().trim();
        String description = txtDescription.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Category name cannot be empty!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            txtCategory.requestFocus();
            return;
        }

        try {
            Category category = new Category();
            category.setCategoryName(name);
            category.setDescription(description);

            categoryDAO.save(category);

            JOptionPane.showMessageDialog(
                    this,
                    "Category successfully added!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save category: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

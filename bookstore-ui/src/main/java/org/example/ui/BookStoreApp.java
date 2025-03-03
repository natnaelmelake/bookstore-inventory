package org.example.ui;


import com.toedter.calendar.JCalendar;
import org.example.model.Book;
import org.example.service.BookService;
//import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookStoreApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DefaultListModel<String> bookListModel;

    public BookStoreApp() {
        setTitle("Book Store Inventory System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

//        // Set the look and feel to JTattoo HiFi
//        try {
//            UIManager.setLookAndFeel(new HiFiLookAndFeel());
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Adding the main menu, data input screen, and display data screen as cards
        cardPanel.add(createMainMenu(), "Main Menu");
        cardPanel.add(createDataInputScreen(), "Data Input");
        cardPanel.add(createDisplayDataScreen(), "Display Data");

        add(cardPanel);

        setVisible(true);
    }

    private JPanel createMainMenu() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton dataInputButton = new JButton("Go to Data Input");
        JButton viewDataButton = new JButton("View Stored Data");

        mainMenuPanel.add(dataInputButton);
        mainMenuPanel.add(viewDataButton);

        dataInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Data Input");
            }
        });

        viewDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Display Data");
            }
        });

        return mainMenuPanel;
    }

    private JPanel createDataInputScreen() {
        JPanel dataInputPanel = new JPanel();
        dataInputPanel.setLayout(new GridLayout(9, 2, 10, 10));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextArea descriptionTextArea = new JTextArea(5, 20);
        JComboBox<String> genreComboBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Sci-Fi", "Biography"});
        JRadioButton availableRadioButton = new JRadioButton("Available");
        JRadioButton unavailableRadioButton = new JRadioButton("Unavailable");
        ButtonGroup availabilityGroup = new ButtonGroup();
        availabilityGroup.add(availableRadioButton);
        availabilityGroup.add(unavailableRadioButton);
        JButton submitButton = new JButton("Submit");

        JCalendar publicationDateCalendar = new JCalendar();

        dataInputPanel.add(new JLabel("Title:"));
        dataInputPanel.add(titleField);
        dataInputPanel.add(new JLabel("Author:"));
        dataInputPanel.add(authorField);
        dataInputPanel.add(new JLabel("Description:"));
        dataInputPanel.add(new JScrollPane(descriptionTextArea));
        dataInputPanel.add(new JLabel("Genre:"));
        dataInputPanel.add(genreComboBox);
//        dataInputPanel.add(new JLabel("Availability:"));
//        JPanel availabilityPanel = new JPanel();
//        availabilityPanel.add(availableRadioButton);
//        availabilityPanel.add(unavailableRadioButton);
//        dataInputPanel.add(availabilityPanel);

        dataInputPanel.add(new JLabel("Publication Date:"));
        dataInputPanel.add(publicationDateCalendar);

        dataInputPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle form submission (e.g., save data, show confirmation, etc.)
                // Collect data from UI components
                String title = titleField.getText();
                String author = authorField.getText();
                String description = descriptionTextArea.getText();
                String genre = (String) genreComboBox.getSelectedItem();
                boolean isAvailable = availableRadioButton.isSelected();
                String isbn = "1234567890"; // Example ISBN, you can add a JTextField for ISBN input
                double price = 19.99; // Example price, you can add a JTextField for price input
                int stockQuantity = 10; // Example stock quantity, you can add a JTextField for stock quantity input

                // Create a Book object with the collected data

                // Call the addBook method to save the book to the database
                BookService bookService = new BookService();
                bookService.addBook(new Book(0, title, author, genre, isbn, price, stockQuantity));
                JOptionPane.showMessageDialog(dataInputPanel, "Data Submitted!");
                cardLayout.show(cardPanel, "Main Menu");
            }
        });

        return dataInputPanel;
    }


    private JPanel createDisplayDataScreen() {
        JPanel displayDataPanel = new JPanel();
        displayDataPanel.setLayout(new BorderLayout());

        // Fetch books from the database
        BookService bookService = new BookService();
        List<Book> books = bookService.getAllBooks();

        // Populate JList with book data
        bookListModel = new DefaultListModel<>();
        for (Book book : books) {
            bookListModel.addElement(book.getTitle() + " - " + book.getAuthor());
        }

        JList<String> bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        displayDataPanel.add(scrollPane, BorderLayout.CENTER);

        // Button to go back to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        displayDataPanel.add(backButton, BorderLayout.SOUTH);

        return displayDataPanel;
    }


    public static void main(String[] args) {

        new BookStoreApp();
//        BookService bookService = new BookService();
//        List<Book> books = bookService.getAllBooks();
//        for (Book book : books) {
//            System.out.println(book.getTitle() + " - " + book.getAuthor());
//        }
    }

}



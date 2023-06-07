package lab;

import lab.Readers.FileReader;
import lab.Readers.JSONReader;
import lab.Readers.XMLReader;
import lab.Readers.YAMLReader;
import lab.dao.ConnectionBuilder;
import lab.dao.DBManipulator;
import lab.dao.Excel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JFrame extends javax.swing.JFrame {

    public JFrame() throws SQLException {
        initComponents();
        this.jTable.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        FilesButton = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        CreateButton = new javax.swing.JButton();
        DropButton = new javax.swing.JButton();
        AgregCountryButton = new javax.swing.JButton();
        AgregCompanyButton = new javax.swing.JButton();
        AgregRegionButton = new javax.swing.JButton();
        AgregReactors = new javax.swing.JButton();
        DownloadButton = new javax.swing.JButton();
        jOptionPane = new JOptionPane();
        jTextField = new JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree);

        FilesButton.setText("Выбрать файлы");
        FilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    FilesButtonActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        jScrollPane2.setViewportView(jTable);

        CreateButton.setText("Create database");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    CreateButtonActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidFormatException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        DropButton.setText("Drop tables");
        DropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DropButtonActionPerformed(evt);
            }
        });

        AgregCountryButton.setText("Aggregate on country");
        AgregCountryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregCountryButtonActionPerformed(evt);
            }
        });

        AgregCompanyButton.setText("Aggregate on company");
        AgregCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregCompanyButtonActionPerformed(evt);
            }
        });

        AgregRegionButton.setText("Aggregate on regions");
        AgregRegionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregRegionButtonActionPerformed(evt);
            }
        });

        AgregReactors.setText("Aggregate on reactors");
        AgregReactors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregReactorsButtonPerformed(evt);
            }
        });

        DownloadButton.setText("Download data");
        DownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    DownloadButtonPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(256, 256, 256)
                                                .addComponent(DownloadButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 390, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(CreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(DropButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(FilesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGap(357, 357, 357)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(29, 29, 29)
                                                                                .addComponent(AgregReactors, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                                                                        .addComponent(AgregCompanyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                                                        .addComponent(AgregCountryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(AgregRegionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)))
                                                .addGap(29, 29, 29)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(AgregReactors, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(AgregCountryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(AgregCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(AgregRegionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(DropButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(FilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                                .addComponent(DownloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33))))
        );

        pack();
    }
    List<Reactor> reactors;
    private void FilesButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {//GEN-FIRST:event_FilesButtonActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("D:\\Java programs\\lab_2"));
        int response = jFileChooser.showDialog(jPanel, "Select");
        if (response == jFileChooser.getApproveButtonMnemonic()) {
            String filename = jFileChooser.getSelectedFile().getAbsolutePath();
            XMLReader xmlReader = getXmlReader(); // create start reader and set chain for readers
            FileReader filer = xmlReader.createAndRead(filename);
            if(filer == null){
                jOptionPane.setMessage("Wrong extension. Please choose another file");
                jOptionPane.createDialog("Error").setVisible(true);
            }
            DBManipulator.updateParamets(con,filename);
            jTextField.setText(filer.getDs().getSource());
            jTextField.setVisible(true);
            jTree.setModel(new DefaultTreeModel(filer.buildTree()));
        }
    }

    //кнопка создания таблиц и заполнения таблицы units и др
    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException, InvalidFormatException, SQLException {//GEN-FIRST:event_CreateButtonActionPerformed
        try{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("D:\\Java programs\\lab_2"));
            int response = jFileChooser.showDialog(jPanel, "Select");
            String filename = jFileChooser.getSelectedFile().getAbsolutePath();
            XSSFWorkbook workbook = Excel.getBook(filename);
            DBManipulator.fillDB(con, workbook);
            jOptionPane.setMessage("Tables were created");
            jOptionPane.createDialog("Success").setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    //кнопка удаления таблиц
    private void DropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DropButtonActionPerformed
        try{
            DBManipulator.dropTables(con);
            jOptionPane.setMessage("Tables were dropped");
            jOptionPane.createDialog("Success").setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    private void AgregCountryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregCountryButtonActionPerformed
        try{
            ResultSet rs = DBManipulator.doQuery(con, DBManipulator.GET_COUNTRY_ANNUAL);
            Displayer displayer  = new Displayer(rs);
            DefaultTableModel dt = new DefaultTableModel(displayer.getData(), displayer.getColNames());
            this.jTable.setModel(dt );
            this.jTable.setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    private void AgregCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregCompanyButtonActionPerformed
        try{
            ResultSet rs = DBManipulator.doQuery(con, DBManipulator.GET_COMPANY_ANNUAL);
            Displayer displayer  = new Displayer(rs);
            DefaultTableModel dt = new DefaultTableModel(displayer.getData(), displayer.getColNames());
            this.jTable.setModel(dt );
            this.jTable.setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    private void AgregRegionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregRegionButtonActionPerformed
        try{
            ResultSet rs = DBManipulator.doQuery(con, DBManipulator.GET_REGION_ANNUAL);
            Displayer displayer  = new Displayer(rs);
            DefaultTableModel dt = new DefaultTableModel(displayer.getData(), displayer.getColNames());
            this.jTable.setModel(dt );
            this.jTable.setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    private void AgregReactorsButtonPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalcButtonActionPerformed
        try{
            ResultSet rs = DBManipulator.doQuery(con, DBManipulator.GET_REACTORS_ANNUAL);
            Displayer displayer  = new Displayer(rs);
            DefaultTableModel dt = new DefaultTableModel(displayer.getData(), displayer.getColNames());
            this.jTable.setModel(dt );
            this.jTable.setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }

    private void DownloadButtonPerformed(java.awt.event.ActionEvent evt) throws SQLException {//GEN-FIRST:event_ExitButtonActionPerformed
        try{
            String home = System.getProperty("user.home");
            Excel.createExcel(con, home +"//Downloads//" + "ReactorData.xls");
            jOptionPane.setMessage("Data is downloaded");
            jOptionPane.createDialog("Success").setVisible(true);
        } catch (Exception e){
            jOptionPane.setMessage("Fail: " + e.getMessage());
            jOptionPane.createDialog("Error").setVisible(true);
        }
    }


    private void FillTree(List<Reactor> reactors) throws IllegalAccessException
    {
        DefaultMutableTreeNode head = new DefaultMutableTreeNode();
        for(Reactor r : this.reactors)
        {
            DefaultMutableTreeNode rnode = (DefaultMutableTreeNode) r.getNode();
            head.add(rnode);
        }
        jTree.setModel(new DefaultTreeModel(head));
    }

    public static XMLReader getXmlReader() {
        XMLReader xmlReader = new XMLReader();
        JSONReader jsonReader = new JSONReader();
        YAMLReader yamlReader = new YAMLReader();

        xmlReader.setNext(jsonReader);
        jsonReader.setNext(yamlReader);
        return xmlReader;
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(lab.JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(lab.JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(lab.JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(lab.JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new lab.JFrame().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(lab.JFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregCompanyButton;
    private javax.swing.JButton AgregCountryButton;
    private javax.swing.JButton AgregRegionButton;
    private javax.swing.JButton AgregReactors;
    private javax.swing.JButton CreateButton;
    private javax.swing.JButton DropButton;
    private javax.swing.JButton DownloadButton;
    private javax.swing.JToggleButton FilesButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.swing.JTree jTree;
    private JOptionPane jOptionPane;
    private JPanel jPanel;
    private JTextField jTextField;

    public Connection con = ConnectionBuilder.getConnection();
}
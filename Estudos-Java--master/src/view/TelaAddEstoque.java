/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Database;

/**
 *
 * @author Meneses
 */
public class TelaAddEstoque extends javax.swing.JFrame {
Connection conexao = null;
PreparedStatement pst = null;
ResultSet rs = null;


    public void listarMat(){
        String sql="select nome from materiaprima";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                Mat_est.addItem(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void listarProd(){
        String sql="select nome from produto where sta_prod = false";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                Nome_est.addItem(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int pegarIdMat() throws SQLException{
        String sql = "select lote from materiaprima where nome = '" + Mat_est.getSelectedItem().toString() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                return rs.getInt("lote");
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
        public int pegarIdProd() throws SQLException{
        String sql = "select id from produto where nome = '" + Nome_est.getSelectedItem().toString() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                return rs.getInt("id");
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * Creates new form TelaAddEstoque
     * @throws java.lang.ClassNotFoundException
     */
    
    
    private void adicionar() throws SQLException{
        int idM = pegarIdMat();
        int idP = pegarIdProd();
        String sql = "insert into estoque"+
                     "(quantidade, LOTE_materia, ID_produto, quantidade_materiaprima)"+
                     //? = n sei os dados q vao vim
                     "values (?,?,?,?)";
        try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            //setando(colocando) os dados no banco
            pst.setString(1, Qtd_est.getText());
            pst.setInt(2, idM);
            pst.setInt(3, idP);
            pst.setString(4, Qtd_materiaprima.getText());
            //executando o banco
            pst.executeUpdate();
            //mensagem para o usuario
            JOptionPane.showMessageDialog(null, "Estoque Cadastrado");
            AtualizarProd();
            TirarMp();
            //pst.close();
            //conexao.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void AtualizarProd() throws SQLException{
        String sql = "update produto set sta_prod = true where id = '" + pegarIdProd() + "'";
        try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void TirarMp() throws SQLException{
        String sql = "update materiaprima set quantidade = quantidade - '" + Double.parseDouble((Qtd_materiaprima.getText())) + "' where lote = '" + pegarIdMat() + "'";
        
        try {
            
            
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
            //mensagem para o usuario
            //fechando a conexao e o banco
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public TelaAddEstoque() throws ClassNotFoundException {
        conexao = Database.conector();
        initComponents();
        listarMat();
        listarProd();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Qtd_est = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Mat_est = new javax.swing.JComboBox<>();
        Nome_est = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        Qtd_materiaprima = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nome do produto");

        jLabel2.setText("Matéria-prima");

        jLabel3.setText("Quantidade");

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Concluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Nome_est.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nome_estActionPerformed(evt);
            }
        });

        jLabel4.setText("Quantidade de matéria prima");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Mat_est, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(Nome_est, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Qtd_est, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Qtd_materiaprima, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Qtd_est, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nome_est, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Mat_est, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Qtd_materiaprima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(50, 50, 50))
        );

        getAccessibleContext().setAccessibleName("Adicionar Estoque");

        setSize(new java.awt.Dimension(500, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        if(Qtd_est.getText().equals("") || Qtd_materiaprima.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Insira os dados corretamente");
        }
        else{
            try{
                pegarIdMat();
                pegarIdProd();
                String sql = "select quantidade from materiaprima where lote = '" + pegarIdMat() + "'";
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery(sql);
                while(rs.next()){
                    if((rs.getDouble("quantidade") - Double.parseDouble(Qtd_materiaprima.getText())) >= 0){
                        this.adicionar();
                        this.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Quantidade de matéria prima excedida");
                    }
                }
                
            }
            catch(SQLException ex){
                Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Nome_estActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nome_estActionPerformed
        
        //jLabel5.setText(Nome_est.getSelectedItem().toString());
    }//GEN-LAST:event_Nome_estActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAddEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAddEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAddEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAddEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaAddEstoque().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TelaAddEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Mat_est;
    private javax.swing.JComboBox<String> Nome_est;
    private javax.swing.JTextField Qtd_est;
    private javax.swing.JTextField Qtd_materiaprima;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}

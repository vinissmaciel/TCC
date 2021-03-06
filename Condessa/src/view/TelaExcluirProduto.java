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
 * @author Vinícius
 */
public class TelaExcluirProduto extends javax.swing.JFrame {
Connection conexao = null;
PreparedStatement pst = null;
ResultSet rs = null;

    public void listarProduto(){
            String sql="select nome from produto";
            try {
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery(sql);
                while(rs.next()){
                    Ex_prod.addItem(rs.getString("nome"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(TelaEditarProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public int pegarIdProduto() throws SQLException{
            String sql = "select id from produto where nome = '" + Ex_prod.getSelectedItem().toString() + "'";
            try{
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery(sql);
                while(rs.next()){
                    return rs.getInt("id");
                }

            }
            catch(SQLException ex){
                Logger.getLogger(TelaEditarProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }
        
        public void MostrarProduto() throws SQLException{
        String sql = "select * from produto where id = '" + pegarIdProduto() + "'";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                ex_nomeProd.setText(rs.getString("nome"));
                ex_uniProd.setText(rs.getString("unidade"));
                ex_precoProd.setText(""+rs.getDouble("preco"));
                if(rs.getBoolean("sta_prod")){
                    jLabel5.setText("Está no estoque, retire do estoque para excluir o produto");
                }else{
                    jLabel5.setText("Não está no estoque, excluível");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaEditarProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void DeleteProduto() throws SQLException{
        String sql = "delete from produto where id = '" + pegarIdProduto() + "'";
            try {
                pst = conexao.prepareStatement(sql);
            //executando o banco
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso");
                this.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Produto não pode ser excluído");
                Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    /**
     * Creates new form TelaExcluirProduto
     */
    public TelaExcluirProduto() throws ClassNotFoundException {
        conexao = Database.conector();
        initComponents();
        listarProduto();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Ex_prod = new javax.swing.JComboBox<>();
        ex_nomeProd = new javax.swing.JLabel();
        ex_uniProd = new javax.swing.JLabel();
        ex_precoProd = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Excluir produto");

        jLabel2.setText("Unidade (Ex: Kg, L):");

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Preço por unidade:");

        jButton2.setText("Excluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Selecione o produto");

        jLabel1.setText("Nome:");

        ex_nomeProd.setText("-");

        ex_uniProd.setText("-");

        ex_precoProd.setText("-");

        jButton3.setText("Pesquisar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Status do produto:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Ex_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ex_nomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ex_precoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ex_uniProd, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 150, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ex_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ex_nomeProd))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ex_uniProd))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ex_precoProd))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(39, 39, 39))
        );

        setSize(new java.awt.Dimension(519, 410));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a exclusão do produto?", "Atenção",JOptionPane.YES_NO_OPTION);
        if(confirmar==JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null, "Exclusão cancelada");
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if(ex_nomeProd.getText().equals("-")){
        JOptionPane.showMessageDialog(null, "Pesquise o produto");
    }else{
    try {
        DeleteProduto();
    } catch (SQLException ex) {
        Logger.getLogger(TelaExcluirProduto.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    try {
        MostrarProduto();
    } catch (SQLException ex) {
        Logger.getLogger(TelaExcluirProduto.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaExcluirProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaExcluirProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaExcluirProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaExcluirProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaExcluirProduto().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TelaExcluirProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Ex_prod;
    private javax.swing.JLabel ex_nomeProd;
    private javax.swing.JLabel ex_precoProd;
    private javax.swing.JLabel ex_uniProd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}

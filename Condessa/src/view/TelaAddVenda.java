/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import controller.Estoque;
import controller.ListaEstoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Database;



public final class TelaAddVenda extends javax.swing.JFrame {
double preco = 0;
Connection conexao = null;
PreparedStatement pst = null;
ResultSet rs = null;
ListaEstoque lista = new ListaEstoque();
//int clicou = 0;
//int click = 0;


    /**
     * Creates new form TelaAdcionarProduto
     */


    public void listarCliente(){
        String sql="select nome from cliente";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                ListarCliente.addItem(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
    public int pegarIdCliente() throws SQLException{
        String sql = "select id from cliente where nome = '" + ListarCliente.getSelectedItem().toString() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                return rs.getInt("id");
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void ListarProduto(){
        String sql="select p.nome from estoque e inner join produto p on p.id = e.ID_produto";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                listarProduto.addItem(rs.getString("p.nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int pegarIdEstoque() throws SQLException{
        String sql = "select e.id from estoque e inner join produto p on p.id = e.ID_produto where p.nome = '" + listarProduto.getSelectedItem().toString() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                return rs.getInt("e.id");
            }
        }
        catch(SQLException ex){
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    public double pegarprodutoestoque() throws SQLException{
        String sql = "select quantidade from estoque where id = '" + pegarIdEstoque() + "'";
        double a = 0;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()){
               a = rs.getDouble("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }
    
    
    
    
    public void pegarestoque() throws SQLException{
        Estoque objestoque = new Estoque();
        String sql = "select e.id, p.nome, p.preco from estoque e inner join produto p on e.ID_produto = p.id where e.id = '" + pegarIdEstoque() + "'";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()){
               objestoque.setId(rs.getInt("e.id"));
               objestoque.setNome(rs.getString("p.nome"));
               objestoque.setPreco(rs.getDouble("p.preco"));
            }
            if(Double.parseDouble(Qtd_Pro_Compra.getText()) <= pegarprodutoestoque()){
                 objestoque.setQtd_comprada(Double.parseDouble(Qtd_Pro_Compra.getText()));
                 lista.adicionar(objestoque); 
            }else{
                JOptionPane.showMessageDialog(null, "Quantidade do estoque excedida");
            }
               
        } catch (SQLException ex) {
            Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MostrarProdutos() {
      DefaultTableModel model =(DefaultTableModel) TabelaVenda.getModel();
           model.setNumRows(0);
           for (Estoque u : lista.listaestoque) {
               model.addRow(new Object[]
              {
                  u.nome,
                  u.preco,
                  u.qtd_comprada
              });
      } 
    }
    
    public int PegarIDVenda(){
       String sql = "select max(id) AS max_id from vendas;";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
           while(rs.next()){
               return rs.getInt("max_id");
           }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
    }
    
    public int PegarIDitemVenda(){
       String sql = "select max(id) AS max_id from item_venda";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
           while(rs.next()){
               return rs.getInt("max_id");
           }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
    }
    
    public void AdicionarVenda() throws SQLException{
        String sql = "insert into vendas value()";
                     //? = n sei os dados q vao vim     
        try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            //executando o banco
            pst.executeUpdate();
            
            
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
 
     public void Item_Venda() throws SQLException{
         int id_cli = pegarIdCliente();
         int id_venda = PegarIDVenda();
        String sql = "insert into item_venda"+
                     "(id_cliente,id_vendas,id_estoque,quantidade_comprada,formadepagamento,datavenda,valor_compra)"+
                     //? = n sei os dados q vao vim
                     "values (?,?,?,?,?,?,?)";
        try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            //setando(colocando) os dados no banco
            pst.setInt(1, id_cli);
            pst.setInt(2, id_venda);
            
            for(Estoque i : lista.listaestoque){
                pst.setInt(3, i.id);
            }
            
            for(Estoque i : lista.listaestoque){
                pst.setDouble(4, i.qtd_comprada);
            }
            
            pst.setString(5, Formadepagamento.getSelectedItem().toString());
            
            pst.setString(6, Data_venda.getText());
            
            pst.setInt(7, 0);

            //executando o banco
            pst.executeUpdate();
            //fechando a conexao e o banco
           
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
     
     public double Preco_vendas() throws SQLException{
         String sql = "select p.preco from estoque es inner join "
                 + "produto p on p.id = es.ID_produto where es.id = '" + pegarIdEstoque() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while(rs.next()){
                return rs.getDouble("p.preco")*Double.parseDouble(Qtd_Pro_Compra.getText());
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
     }
     
     public void AddPrecoVenda(double preco) throws SQLException{
          String sql = "update item_venda set valor_compra = '" + preco + "' where id_vendas = '" + PegarIDVenda() + "'";
        try{
            pst = conexao.prepareStatement(sql);
            //executando o banco
            pst.executeUpdate();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
     
     public void TirarEstoque() throws SQLException{
        for(Estoque i : lista.listaestoque){
            String sql = "update estoque set quantidade = quantidade - '" + i.qtd_comprada + "' where id = '" + i.id + "'";
            try{
                pst = conexao.prepareStatement(sql);
                //executando o banco
                pst.executeUpdate();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        } 
     }
     
     
     public void CancelarVenda(){
         lista.listaestoque.clear();
         String sql = "delete from vendas where id = '" + PegarIDVenda() + "'";
         try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
     }
     
     public void Cancelaritem_Venda(){
         lista.listaestoque.clear();
         String sql = "delete from item_venda where id_vendas = '" + PegarIDVenda() + "'";
         try {
            //preparando o banco pra ser usado
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
     }
     
     
    public TelaAddVenda() throws ClassNotFoundException, SQLException  {
        conexao = Database.conector();
        initComponents();
        listarCliente();
        ListarProduto();
        AdicionarVenda();
        preco = 0;
        //clicou = 0;
        //click = 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        Formadepagamento = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        ListarCliente = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaVenda = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        Qtd_Pro_Compra = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        listarProduto = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        Data_venda = new javax.swing.JFormattedTextField();
        jTextField1 = new javax.swing.JTextField();
        Valor_venda = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Adicionar venda");

        jLabel3.setText("Forma de Pagamento");

        Formadepagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cartão de Débito", "Cartão de Crédito", "Dinheiro", "Cheque" }));
        Formadepagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormadepagamentoActionPerformed(evt);
            }
        });

        jLabel5.setText("Data da Venda");

        ListarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarClienteActionPerformed(evt);
            }
        });

        jLabel6.setText("Selecione o Cliente");

        jLabel1.setText("Adicionar Produto");

        jLabel2.setText("Produto(s)");

        TabelaVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome do produto", "Preço do produto", "Quantidade para venda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelaVenda);

        jLabel4.setText("Valor da Venda");

        jButton2.setText("Concluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Qtd_Pro_Compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Qtd_Pro_CompraActionPerformed(evt);
            }
        });

        jButton1.setText("Adicionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Selecione o produto");

        listarProduto.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                listarProdutoComponentAdded(evt);
            }
        });
        listarProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listarProdutoItemStateChanged(evt);
            }
        });
        listarProduto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listarProdutoAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listarProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                listarProdutoFocusGained(evt);
            }
        });
        listarProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listarProdutoMouseClicked(evt);
            }
        });
        listarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarProdutoActionPerformed(evt);
            }
        });

        jLabel9.setText("Digite a quantidade do produto que deseja vender");

        try {
            Data_venda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        Valor_venda.setText("-");

        jLabel11.setText("Digite um novo valor da venda (caso quiser)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Formadepagamento, 0, 200, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(ListarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel1)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Valor_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2)
                                        .addComponent(Data_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(listarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Qtd_Pro_Compra, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(235, 235, 235))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Formadepagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ListarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(20, 20, 20)
                .addComponent(Data_venda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Qtd_Pro_Compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Valor_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(53, 53, 53))
        );

        setSize(new java.awt.Dimension(684, 669));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FormadepagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormadepagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FormadepagamentoActionPerformed

    private void ListarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ListarClienteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            try {
               if(jTextField1.getText().isEmpty()){
                    AddPrecoVenda(preco);
                    TirarEstoque();
                    JOptionPane.showMessageDialog(null, "Venda cadastrada");
                    this.dispose();
                }else{
                    preco = Double.parseDouble(jTextField1.getText());
                    AddPrecoVenda(preco);
                    TirarEstoque();
                    JOptionPane.showMessageDialog(null, "Venda cadastrada");
                    this.dispose();
               }  
            } catch (SQLException ex) {
                Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void listarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarProdutoActionPerformed
        //if(true){
            //listarProduto.getSelectedItem().toString();
            //clicou++;
        //}
        
    }//GEN-LAST:event_listarProdutoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int clicouu = 0;
        try {
        if (Qtd_Pro_Compra.getText().equals("") || Data_venda.getText().equals("  /  /    ") 
                ){
                JOptionPane.showMessageDialog(null, "Insira os dados corretos");
        } else if(Double.parseDouble(Qtd_Pro_Compra.getText()) <= pegarprodutoestoque()){
            pegarIdEstoque();
            pegarIdCliente();
            PegarIDitemVenda();
              //if(clicou >=2 ){
                  //JOptionPane.showMessageDialog(null, "erro 1 if");
                  //clicouu = 1;
              //}
              //else if(click >=1){
                  //JOptionPane.showMessageDialog(null, "erro 2 if");
              //}
              //else{
                  //click++;
                  pegarestoque();
                  Preco_vendas(); 
                  Valor_venda.setText(""+(preco+=Preco_vendas()));
                  Item_Venda();
                  MostrarProdutos();
              //}
        }
        else{
            JOptionPane.showMessageDialog(null, "Insira uma quantidade válida\nQuantidade do produto disponível: "+pegarprodutoestoque());
            Qtd_Pro_Compra.setText("");
        }
        
        //if(clicouu == 1){
            //JOptionPane.showMessageDialog(null, "Escolha outro produto");
        //}
    } catch (SQLException ex) {
        Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void listarProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarProduto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listarProduto1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
    }//GEN-LAST:event_jButton5ActionPerformed

    private void Qtd_Pro_CompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Qtd_Pro_CompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Qtd_Pro_CompraActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void listarProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listarProdutoMouseClicked
        
    }//GEN-LAST:event_listarProdutoMouseClicked

    private void listarProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listarProdutoItemStateChanged
       
    }//GEN-LAST:event_listarProdutoItemStateChanged

    private void listarProdutoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listarProdutoAncestorAdded
    
    }//GEN-LAST:event_listarProdutoAncestorAdded

    private void listarProdutoComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_listarProdutoComponentAdded
    
    }//GEN-LAST:event_listarProdutoComponentAdded

    private void listarProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_listarProdutoFocusGained
    
    }//GEN-LAST:event_listarProdutoFocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a venda?", "Atenção",JOptionPane.YES_NO_OPTION);
        if(confirmar==JOptionPane.YES_OPTION){
            Cancelaritem_Venda();       
            CancelarVenda();
            JOptionPane.showMessageDialog(null, "Venda cancelada");
            this.dispose();
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
            java.util.logging.Logger.getLogger(TelaAddVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAddVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAddVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAddVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaAddVenda().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(TelaAddVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Data_venda;
    private javax.swing.JComboBox<String> Formadepagamento;
    private javax.swing.JComboBox<String> ListarCliente;
    private javax.swing.JTextField Qtd_Pro_Compra;
    private javax.swing.JTable TabelaVenda;
    private javax.swing.JLabel Valor_venda;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> listarProduto;
    // End of variables declaration//GEN-END:variables
}

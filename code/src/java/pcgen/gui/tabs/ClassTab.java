/*
 * ClassTab.java
 *
 * Created on June 26, 2008, 4:20 PM
 */

package pcgen.gui.tabs;

/**
 *
 * @author  Connor Petty <cpmeister@users.sourceforge.net>
 */
public class ClassTab extends javax.swing.JPanel {
    
    /** Creates new form ClassTab */
    public ClassTab() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        flippingSplitPane1 = new pcgen.gui.util.panes.FlippingSplitPane();
        flippingSplitPane2 = new pcgen.gui.util.panes.FlippingSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();
        jTablePane1 = new pcgen.gui.util.JTablePane();
        infoPane1 = new pcgen.gui.tools.InfoPane();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        flippingSplitPane1.setDividerSize(7);
        flippingSplitPane1.setContinuousLayout(true);
        flippingSplitPane1.setOneTouchExpandable(true);

        flippingSplitPane2.setDividerSize(7);
        flippingSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Add");
        jButton1.setDefaultCapable(false);
        jButton1.setMinimumSize(new java.awt.Dimension(100, 9));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 9));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jButton1, gridBagConstraints);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        jSpinner1.setMinimumSize(new java.awt.Dimension(50, 18));
        jSpinner1.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jSpinner1, gridBagConstraints);

        jButton2.setText("Remove");
        jButton2.setDefaultCapable(false);
        jButton2.setMinimumSize(new java.awt.Dimension(100, 23));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jButton2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jTablePane1, gridBagConstraints);

        flippingSplitPane2.setBottomComponent(jPanel1);

        infoPane1.setTitle("Class Info");
        flippingSplitPane2.setLeftComponent(infoPane1);

        flippingSplitPane1.setRightComponent(flippingSplitPane2);
        flippingSplitPane1.setLeftComponent(jPanel2);

        add(flippingSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pcgen.gui.util.panes.FlippingSplitPane flippingSplitPane1;
    private pcgen.gui.util.panes.FlippingSplitPane flippingSplitPane2;
    private pcgen.gui.tools.InfoPane infoPane1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner1;
    private pcgen.gui.util.JTablePane jTablePane1;
    // End of variables declaration//GEN-END:variables
    
}

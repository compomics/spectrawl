package com.compomics.spectrawl;

import com.compomics.spectrawl.config.ApplicationContextProvider;
import com.compomics.spectrawl.gui.controller.MainController;
import com.compomics.spectrawl.gui.view.MainFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrawlStarter {

    private final static Logger LOGGER = Logger.getLogger(SpectrawlStarter.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ApplicationContextProvider.getInstance().setDefaultApplicationContext();
                    MainController mainController = ApplicationContextProvider.getInstance().getBean("mainController");
                    mainController.init();
                } catch (BeanCreationException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    String beanName = ex.getBeanName();
                    if (beanName.equals("msLimsDataSource") && ex.getCause() instanceof PropertyBatchUpdateException) {
                        JOptionPane.showMessageDialog(null, "Spectrawl was not able to connect to MSLims with the given credentials."
                                + "\n" + "The program will continue, but you'll only able to use the MGF functionality.",
                                "Spectrawl startup warning", JOptionPane.WARNING_MESSAGE);
                    }
                    ApplicationContextProvider.getInstance().setApplicationContext(new ClassPathXmlApplicationContext("spectrawl-mgf-only-context.xml"));
                    MainController mainController = ApplicationContextProvider.getInstance().getBean("mainController");
                    mainController.init();
                }
            }
        });
    }
}

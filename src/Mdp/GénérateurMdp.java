package Mdp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;

public class GénérateurMdp extends JFrame implements ActionListener {

    private static final long serialVersionUID = -8988890854116009590L;
	 private static final String ALPHABET_MAIUSCULE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_MINUSCULE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String SYMBOLES = "!@#$%^&*()_-+={}[];':\"\\|,.<>/?";
    private static final SecureRandom random = new SecureRandom();

    private JButton genererButton;
    private JTextField longueurField;
    private JCheckBox majusculesCheckbox;
    private JCheckBox minusculesCheckbox;
    private JCheckBox nombresCheckbox;
    private JCheckBox symbolesCheckbox;
    private JTextField motDePasseField;

    public GénérateurMdp() {
        super("Générateur de Mot de Passe");

        genererButton = new JButton("Générer");
        longueurField = new JTextField(5);
        majusculesCheckbox = new JCheckBox("Majuscules");
        minusculesCheckbox = new JCheckBox("Minuscules");
        nombresCheckbox = new JCheckBox("Nombres");
        symbolesCheckbox = new JCheckBox("Symboles");
        motDePasseField = new JTextField(20);
        motDePasseField.setEditable(false);
        motDePasseField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    motDePasseField.selectAll();
                }
            }
        });

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(majusculesCheckbox);
        optionsPanel.add(minusculesCheckbox);
        optionsPanel.add(nombresCheckbox);
        optionsPanel.add(symbolesCheckbox);

        JPanel contenuPanel = new JPanel();
        contenuPanel.add(new JLabel("Longueur :"));
        contenuPanel.add(longueurField);
        contenuPanel.add(genererButton);

        JPanel resultatPanel = new JPanel();
        resultatPanel.add(new JLabel("Mot de passe généré :"));
        resultatPanel.add(motDePasseField);

        add(optionsPanel, BorderLayout.NORTH);
        add(contenuPanel, BorderLayout.CENTER);
        add(resultatPanel, BorderLayout.SOUTH);

        genererButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == genererButton) {
            int longueur = 0;
            try {
                longueur = Integer.parseInt(longueurField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La longueur doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean inclureMajuscules = majusculesCheckbox.isSelected();
            boolean inclureMinuscules = minusculesCheckbox.isSelected();
            boolean inclureNombres = nombresCheckbox.isSelected();
            boolean inclureSymboles = symbolesCheckbox.isSelected();

            String motDePasse = genererMotDePasse(longueur, inclureMajuscules, inclureMinuscules, inclureNombres, inclureSymboles);
            motDePasseField.setText(motDePasse);
        }
    }

    private String genererMotDePasse(int longueur, boolean inclureMajuscules, boolean inclureMinuscules, boolean inclureNombres, boolean inclureSymboles) {
        StringBuilder motDePasse = new StringBuilder();
        String charset = "";

        if (inclureMajuscules) {
            charset += ALPHABET_MAIUSCULE;
        }
        if (inclureMinuscules) {
            charset += ALPHABET_MINUSCULE;
        }
        if (inclureNombres) {
            charset += NUMEROS;
        }
        if (inclureSymboles) {
            charset += SYMBOLES;
        }

        if (charset.isEmpty()) {
            return "Sélectionnez au moins une option.";
        }

        for (int i = 0; i < longueur; i++) { 
            int index = random.nextInt(charset.length());
            motDePasse.append(charset.charAt(index));
        }

        return motDePasse.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GénérateurMdp());
    }
}


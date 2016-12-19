package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.ExportMatrix;
import org.opencompare.api.java.io.ExportMatrixExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

public class FormGeneratorGUI extends JFrame 
{

	/**
	 * Cette classe est utilisé pour créer une interface graphique permettant de tester cette application plus facilement
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIn;
	private JLabel lblFichierHtml;
	private JTextField txtOut;

	/**
	 * Chemin d'accès au fichier d'entrée (PCM)
	 */
	private String fIn;
	/**
	 * Chemin d'accès au fichier dans lequel le formulaire sera créer
	 */
	private String fOut;

	JFileChooser jfc = new JFileChooser();
	private JLabel label;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					FormGeneratorGUI frame = new FormGeneratorGUI();
					frame.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormGeneratorGUI() 
	{
		setTitle("FormGenerator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 149);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblFichierPcm = new JLabel("FIchier PCM :");
		contentPane.add(lblFichierPcm);
		
		txtIn = new JTextField();
		txtIn.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				jfc.showOpenDialog(getParent());
				fIn = jfc.getSelectedFile().getPath();
				txtIn.setText(fIn);
			}
		});
		contentPane.add(txtIn);
		txtIn.setColumns(10);
		
		lblFichierHtml = new JLabel("Fichier HTML :");
		contentPane.add(lblFichierHtml);
		
		txtOut = new JTextField();
		txtOut.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				jfc.showOpenDialog(getParent());
				fOut = jfc.getSelectedFile().getPath();
				txtOut.setText(fOut);
			}
		});
		contentPane.add(txtOut);
		txtOut.setColumns(10);
		
		JButton btnGenererLeFormulaire = new JButton("Generer le Formulaire");
		btnGenererLeFormulaire.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				RunGenerator();
			}
		});
		
		label = new JLabel("");
		contentPane.add(label);
		contentPane.add(btnGenererLeFormulaire);
	}
	
	public void RunGenerator()
	{

		if(fIn != "" && fOut != "") // on verifie que les chemins d'accès au fichiers d'entrée et de sortie ne soient pas vide
		{
			PCMContainer pcmC = new PCMContainer();
			try
			{
				File pcmFile = new File(fIn); // on creer un Objet file avec le chemin d'accès fIn
				PCMLoader loader = new KMFJSONLoader();
				List<PCMContainer> pcmContainers = loader.load(pcmFile); // on charge le PCM
				pcmC = pcmContainers.get(0); // on recupere le premier PCMContainer du PCM
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			PCM pcm = pcmC.getPcm(); // on recupere le PCM du PCMContainer

			Analyzer a= new Analyzer();
			HTMLCreator creator = new HTMLCreator();

			ExportMatrixExporter eme = new ExportMatrixExporter();
			ExportMatrix em = eme.export(pcmC); // On recupere la matrice d'exportation du PCMContainer
			Map<String,List<String>> features = a.getTypeFeatures(em,pcm); // On analyse et recupere les types des differentes Features du PCM

			Map<String,List<String>> feats = a.getContentFeatures(em,pcm); // On recupere le contenus des cellules pour chaques Features du PCM

			Map<String,List<String>> beacon = HTMLGenerator.GenerateFrom(features); // On genere les balises HTML5 à partir des types des Features recuperes

			String text = creator.HTMLString(beacon, feats); // On creer le formulaire HTML5
			try
			{
				creator.insertTexte(text,fOut); // on enregistre le formulaire HTML5 dans le fichier situé au chemin d'accès fOut
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}

	}

}

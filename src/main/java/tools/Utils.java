/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import jpa.Proposition;
import jpa.Question;
import jpa.Reponse;
import jpa.Rubrique;
import jpa.Test;
import moteurs.TheCodeTester;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import adapter.DaoProvider;
import adapter.SessionManager;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.IPropositionDao;
import dao.IQuestionDao;
import dao.IReponseDao;
import dao.ITestDao;

/**
 * 
 * @author Loïc
 */
public class Utils {

	public static Date stringToMySQLDate(String date) {
		Date theJavaDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			theJavaDate = sdf.parse(date);
			System.out.println("theJavaDate : " + theJavaDate.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return theJavaDate;
	}

	public static void checkCookies(String login, String mp, String remember1,
			boolean rememberMe) {
		String cookieName = null;
	/*	try {
			Map<String, Object> cookies = SessionManager.getExternalContext()
					.getRequestCookieMap();
			System.out.println(" match 2");		
			if (cookies != null && cookies.size() > 0) {
				System.out.println("des cookies existent");
				for (Entry<String, Object> entry : cookies.entrySet()) {
					String cle = entry.getKey();
					Object valeur = entry.getValue();
					cookieName = cle;
					if (cookieName.equals("user")) {
						System.out.println("user name");
						login = valeur.toString();
					} else if (cookieName.equals("pwd")) {
						System.out.println("password");
						mp = valeur.toString();
					} else if (cookieName.equals("btremember")) {
						System.out.println("remendert");
						remember1 = valeur.toString();

						if (remember1.equals("false")) {
							rememberMe = false;
						} else if (remember1.equals("true")) {
							rememberMe = true;
						}
					}
				}
			} else {
				System.out.println("Cannot find any cookie");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/
		}

	public static String dateToMySQLString(Date theJavaDate) {
		String mysqlDateString = "";
		try {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			mysqlDateString = formatter.format(theJavaDate);
			System.out.println("Java Date Format: " + theJavaDate);
			System.out.println("Mysql Date Format: " + mysqlDateString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mysqlDateString;
	}

	
	
	
	public static void setCookies(String login, String mp, String remember1) {
		/*
		 *  System.out.println(login + " " + mp + " " + remember1);
		SessionManager.getExternalContext().addResponseCookie("user", login,
				null);
		SessionManager.getExternalContext().addResponseCookie("password", mp,
				null);
		SessionManager.getExternalContext().addResponseCookie("remember",
				remember1, null);
		System.out.println(" 2.5 ");
		*/
	}

	
	
	public static String getRelativePath(File file, File folder) {
		String filePath = file.getAbsolutePath();
		String folderPath = folder.getAbsolutePath();
		if (filePath.startsWith(folderPath)) {
			return filePath.substring(folderPath.length() + 1);
		} else {
			return null;
		}
	}

	public static void writeOnDisk(String pathPHP, String leCode) {
		try {
			
			Log(pathPHP);
			Log(leCode);
			
			
			// C:\Project\Workspace\CodeTester\target\CodeTester-1.0-SNAPSHOT\PHP\code.php
			
			PrintWriter fichier = new PrintWriter(new FileWriter(pathPHP),
					false);
			System.out
					.println("-------------------------------------------------------------------------------------");
			System.out.println(leCode);
			fichier.println(leCode);
			System.out
					.println("-------------------------------------------------------------------------------------");
			fichier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url);
		} catch (IOException ex) {
			Logger.getLogger(TheCodeTester.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void buildXML(String pathEcriture, String pathLecture)
			throws TransformerConfigurationException, TransformerException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("xml");
			doc.appendChild(rootElement);
			String line = "";
			String theLine = "";
			String theTrimedLine = "";
			try {
				InputStream flux = new FileInputStream(pathLecture);
				InputStreamReader lecture = new InputStreamReader(flux);
				BufferedReader buff = new BufferedReader(lecture);

				Element node = doc.createElement("Node");
				rootElement.appendChild(node);
				while ((line = buff.readLine()) != null) {
					theLine = theLine + line;
				}
				theTrimedLine = theLine.trim();
				theTrimedLine = theTrimedLine.replace(" ", "");
				buff.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			theTrimedLine = theTrimedLine.replace("<?php", "");
			theTrimedLine = theTrimedLine.replace("?>", "");
			theTrimedLine = theTrimedLine.replace("/*Saisissezvotrecode*/", "");

			// on a toute la chaine
			int i = theTrimedLine.indexOf("for");
			int index = 0;
			ArrayList<Integer> tab = new ArrayList<Integer>();
			while (i >= 0) {
				tab.add(i);
				i = theTrimedLine.indexOf("for", i + 1);
			}
			ArrayList<String[]> tab2 = new ArrayList<String[]>();
			for (Integer integer : tab) {
				String[] subtab = new String[3];
				char result = theTrimedLine.charAt(integer + 3);
				int debut = integer + 4;
				String s = theTrimedLine.substring(debut,
						theTrimedLine.length());
				int f = s.indexOf(")");
				int fin = f + debut;
				String contenu = theTrimedLine.substring((debut), fin);
				String contenu2 = contenu.replace(";", "/");
				subtab[0] = String.valueOf(debut);
				subtab[1] = String.valueOf(fin);
				subtab[2] = contenu2;
				tab2.add(subtab);
				theTrimedLine = theTrimedLine.replace(contenu, contenu2);
			}

			String[] stringParts = theTrimedLine.split(";");
			int index_tab = 0;
			for (int c = 0; c < stringParts.length; c++) {
				Element node = doc.createElement("Node");
				node.appendChild(doc.createTextNode(stringParts[c]));
				rootElement.appendChild(node);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(pathEcriture));
			// Output to console for testing
			transformer.transform(source, result);
			buildXML2(pathEcriture);

			// deuxième transformation !
			System.out.println("File saved!");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}

	public static void buildXML2(String pathXML) {
		System.out
				.println("*****************************************************************");
		int index_for = 0;
		int index_if = 0;

		try {
			String filepath = pathXML;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node rootElement = doc.getFirstChild();
			NodeList list = rootElement.getChildNodes();

			// for
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String value = node.getTextContent();
				System.out
						.println("\n*********************************************************");
				System.out.println(value);

				if (value.length() > 3 && value.contains("for")) {
					System.out.println("la ligne contient for ");
					int debut_for = value.indexOf("for");
					String svalue = value.substring(debut_for, value.length());

					int debut_cond = svalue.indexOf("(") + 1;
					int fin_cond = svalue.indexOf(")");

					Element for_ = doc.createElement("for");
					for_.setAttribute("count", String.valueOf(index_for + 1));
					node.appendChild(for_);
					Element condition = doc.createElement("condition");
					for_.appendChild(condition);

					String cond = value.substring(debut_cond, fin_cond);
					System.out.println(cond);
					String[] split = cond.split("/");

					for (int s = 0; s < split.length; s++) {
						Element element = doc.createElement("condition"
								+ (s + 1));
						element.appendChild(doc.createTextNode(split[s]));
						condition.appendChild(element);
					}

					String forToSup = value
							.substring(debut_for, (fin_cond + 1));
					value = value.replace(forToSup, "");
					index_for++;
				}

				if (value.length() > 2 && value.contains("if")) {
					System.out.println("la ligne contient if ");

					int debut_if = value.indexOf("if");

					String svalue = value.substring(debut_if, value.length());
					System.out.println("svalue : " + svalue);
					int debut_cond_if = svalue.indexOf("(") + 2;
					int fin_cond_if = svalue.indexOf(")") + 2;
					System.out.println("debut_cond_if = " + debut_cond_if
							+ " fin_cond_if=" + fin_cond_if);

					String cond = value.substring(debut_cond_if, fin_cond_if);
					System.out.println("cond = " + cond);

					Element if_ = doc.createElement("if");
					if_.setAttribute("count", String.valueOf(index_if + 1));
					node.appendChild(if_);
					Element condition = doc.createElement("condition");
					if_.appendChild(condition);

					Element element = doc.createElement("condition1");
					element.appendChild(doc.createTextNode(cond));
					condition.appendChild(element);

					index_if++;
				}

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(pathXML));
				transformer.transform(source, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String CompareXML() throws SAXException,
			ParserConfigurationException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setCoalescing(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc1 = db.parse(new File("file1.xml"));
		doc1.normalizeDocument();

		Document doc2 = db.parse(new File("file2.xml"));
		doc2.normalizeDocument();

		// tailles différentes -> 0
		// noeaud dif -> 0
		// while (doc1.hasNextLine() && doc2.hasNextLine()) {
		// lineA = file1.nextLine();
		// lineB = file2.nextLine();
		//
		// if (!lineA.equals(lineB)) {
		// System.out.print("Diff " + x++ + "\r\n");
		// System.out.print("< " + lineA + "\r\n");
		// System.out.print("> " + lineB + "\r\n");
		// }
		// }
		// Assert.assertTrue(doc1.isEqualNode(doc2));
		return "";
	}



	// on créer les réponses pour chaque test
	public static Passage createPassage(Candidat theCandidat,
			List<Test> theTests) {
		printLine("createPassage");

		DaoProvider DAO = new DaoProvider();
		
		Passage passage = new Passage();

		DateTime date = new DateTime();
		DateTime date_fin = date.plusWeeks(7);
		passage.setPassageDate(date.toDate());
		passage.setPassagedebutVal(date.toDate());
		passage.setPassagefinVal(date_fin.toDate());

		// on le passe à 1 lorsque tous les tests du passage seront effectués
		passage.setPassageEtat(0);

		passage.setCandidatid(DAO.CANDIDATDAO.find(Long.valueOf(theCandidat
				.getCandidatid())));

		Passage thePassage = DAO.PASSAGEDAO.create(passage);

		System.out.println(" >> Passage id = " + thePassage.getPassageid());

		List<Jointure> listJointure = new ArrayList<Jointure>();

		// on associe chaque test à sa jointure et chaque jointure à un passage
		// + persist la jointure
		System.out.println(theTests.size());
		for (Test test : theTests) {
			System.out.println(test.toString());

			Jointure j = new Jointure();
			j.setTestid(test); // test
			j.setPassageid(thePassage); // passage
			Jointure jPersist = DAO.JOINTUREDAO.create(j);
			System.out.println(" >> jointure id = "
					+ jPersist.getJointureid());
			listJointure.add(jPersist);

			Set<Jointure> listJointureToSet = new HashSet<Jointure>(
					listJointure);
			thePassage.setJointureCollection(listJointureToSet);

			// on associe le passage et le test à la liste de jointure
			thePassage = DAO.PASSAGEDAO.update(thePassage);

			// on récupère toutes rubriques associées à un test
			List<Object> list = Arrays.asList(test.getRubriqueCollection()
					.toArray());
			System.out.println(" >> Nbres de rubriques pr ce test = "
					+ list.size());

			for (Object o : list) {
				Rubrique rub = (Rubrique) o;

				// pour chaque rubriques on récupère sa liste questions
				List<Object> list2 = Arrays.asList(rub.getQuestionCollection()
						.toArray());
				System.out.println(" >> Pour la rubrique n°"
						+ rub.getRubriqueid() + ", Nombre de question = "
						+ list2.size());

				// question
				for (Object o2 : list2) {

					// pour chaque question on créer une réponse vide
					Question quest = (Question) o2;
					// List<Reponse> list_rep = (List<Reponse>)
					// quest.getReponseCollection();

					Collection<Reponse> listReponse = quest
							.getReponseCollection();

					Reponse rep = new Reponse();
					rep.setReponsemessage("");
					rep.setReponsetexte("");
					rep.setReponseduree(0);
					rep.setQuestionid(quest);
					rep.setPassageid(thePassage);
					Reponse reponse = DAO.REPONSEDAO.create(rep);

					listReponse.add(reponse);

					quest.setReponseCollection(listReponse);

					// merge
					DAO.QUESTIONDAO.edit(quest);
				}
			}
		}
		Utils.Log("Passage créé !");
		
		return thePassage;
	}

	public static int verifXML() {
		return ((int) (Math.random() * (1 + 1 - 0)) + 0);
	}

	public static void Enreg_rep(String questionid, int i, String isCodeCompiled) {
		String etat = "";
		if (isCodeCompiled.equals("1")) {
			etat = "Répondue";
		} else if (isCodeCompiled.equals("0")) {
			etat = "Non répondue";
		}

		System.out
				.println("******************************************************");
		System.out.println("  Enreg_rep => question " + questionid + " : " + i);
		System.out
				.println("******************************************************");

		DaoProvider DAO = new DaoProvider();

		// reponse à une (question/proposition) donnée
		Question quest = DAO.QUESTIONDAO.find(Long.valueOf(questionid));
		// Proposition prop = propositionDao.find(quest).get(0);
		Reponse rep = DAO.REPONSEDAO.find(quest);

		int id = rep.getReponseid();

		// System.out.println("reponse id: "+id);
		Reponse theReponse = DAO.REPONSEDAO.find(Long.valueOf(id));

		theReponse.setReponsemessage(String.valueOf(i));
		theReponse.setReponsetexte(etat);
		// System.out.println("the reponse: " + theReponse.toString());

		DAO.REPONSEDAO.edit(theReponse); // merge
	}

	public static List<Reponse> findReponses(Passage passage, Test test) {
		printLine("findReponse");
		DaoProvider DAO = new DaoProvider();
		List<Reponse> list = DAO.REPONSEDAO.find(passage, test);
		return list;
	}

	public static int getDureeTest(String test_id) {
		DaoProvider DAO = new DaoProvider();
		Test test = DAO.TESTDAO.find(Long.valueOf(test_id));
		return (test.getTestduree() * 60); // en sec
	}

	public static String twoDigitString(int number) {
		if (number == 0) {
			return "00";
		}
		if (number / 10 == 0) {
			return "0" + number;
		}
		return String.valueOf(number);
	}

	// on doit prendre qu'il peut y avoir plusieurs bonnes réponses
	public static int verif_ReponseQCM(String questionid,
			List<String> id_propositionChecked) {
		DaoProvider DAO = new DaoProvider();
		
		Question quest = DAO.QUESTIONDAO.find(Long.valueOf(questionid));
		List<Proposition> props = DAO.PROPOSITIONDAO.find_(quest);
		System.out.println("Nb de bonnes réponses : " + props.size());

		int retour = 1;
		for (Proposition p : props) {
			// System.out.println("prop_id : "
			// +p.getPropositionid().toString());
			for (String s : id_propositionChecked) {
				// System.out.println("      ?   == " + s);
				if (p.getPropositionid().toString().equals(s)) {
					System.out.println("OBonne réponse");
					retour = 1;
				} else {
					System.out.println("KO");
					retour = 0;
				}
			}
		}

		return retour;
	}

	public static void enreg_ReponseQCM(String questionid, int i, String etat) {
		System.out.println("enreg_ReponseQCM() => Qid:" + questionid + ", i:"
				+ i + ", état:" + etat);
		DaoProvider DAO = new DaoProvider();
		Reponse r = DAO.REPONSEDAO.find(DAO.QUESTIONDAO.find(Long.valueOf(questionid)));
		r.setReponsemessage(String.valueOf(i));
		r.setReponsetexte(etat);
		DAO.REPONSEDAO.edit(r);
	}

	public static void printLine(String s) {
		System.out
				.println("////////////////////////////////////////////////////////////");
		System.out
				.println("\n============================================================");
		System.out.println("                    " + s
				+ "                                 ");
		System.out
				.println("------------------------------------------------------------");
	}

	public static void Log(String string) {
		// TODO Auto-generated method stub
		System.out.println("\n============================================================");

		System.out.println(string);
	}
}

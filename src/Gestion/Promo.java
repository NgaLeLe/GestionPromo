package Gestion;

import java.util.*;

import Apprenant.*;

public class Promo {
	static public ArrayList<Promo> listPromo;

	private String nomPromo;
	private int dureeTotal; // nombre de jour de promotion
	private int dureeEffecture;
	private ArrayList<Apprenant> listApprenant;

//constructeur	
	public Promo() {
		super();
	}

	public Promo(String pNomPromo, int pDureeTotal, int pDureeEffecture) {
		this.nomPromo = pNomPromo;
		this.dureeTotal = pDureeTotal;
		this.dureeEffecture = pDureeEffecture;

	}

	public String getNomPromo() {
		return nomPromo;
	}

	public void setNomPromo(String nomPromo) {
		this.nomPromo = nomPromo;
	}

	public int getDureeTotal() {
		return dureeTotal;
	}

	public void setDureeTotal(int dureeTotal) {
		this.dureeTotal = dureeTotal;
	}

	public int getDureeEffecture() {
		return dureeEffecture;
	}

	public void setDureeEffecture(int pNbJourAjouter) {
		this.dureeEffecture = this.dureeEffecture + pNbJourAjouter;
	}

	public ArrayList<Apprenant> getListApprenant() {
		return listApprenant;
	}

	public void setListApprenant(ArrayList<Apprenant> listApprenant) {
		this.listApprenant = listApprenant;
	}

	@Override
	public String toString() {
		if (this.listApprenant != null) {
			return "NOM : " + this.nomPromo + "\n Nombre de jours : " + this.dureeTotal + "\n Durée effecturé : "
					+ this.dureeEffecture + "\n Liste d'apprenants \n" + this.listApprenant.toString();
		} else {
			return "NOM : " + this.nomPromo + "\n Nombre de jours : " + this.dureeTotal + "\n Durée effecturé : "
					+ this.dureeEffecture + "\n";
		}
	}

	public static void creerListPromo() {
		listPromo = new ArrayList<Promo>();
		String[] pNom = { "Dev Java JEE", "Web/Mobile", "Concepteur" };
		int[] pDureeTotal = { 120, 210, 300 };
		int[] pDureeEffec = { 35, 7, 0 };
		for (int i = 0; i < pDureeEffec.length; i++) {
			listPromo.add(new Promo(pNom[i], pDureeTotal[i], pDureeEffec[i]));
		}
		System.out.println("list promo OK");

	}

	public static void ajouterListAprrenant(int idPromo, Scanner input) {
		System.out.println("Saisir id to commencer d'ajouter: ");
		Integer pStart = Integer.parseInt(input.nextLine());
		// je parcouris à partir pStart, j'ajouter 3 stagaires, 2 alternants
		ArrayList<Apprenant> tmpListApprenant = new ArrayList<Apprenant>();
		tmpListApprenant = Apprenant.creerListe(pStart, 3, 2);
		listPromo.get(idPromo).setListApprenant(tmpListApprenant);
	}

	public static void AfficherListPromo() {
		for (int i = 0; i < listPromo.size(); i++) {
			System.out.println(listPromo.get(i).toString());
		}
	}

	public static int idPromoApprenant(String pNom, String pPrenom) {
		int idPromo = -1;
		ArrayList<Apprenant> tmpList = new ArrayList<Apprenant>();
		for (int i = 0; i < listPromo.size(); i++) {
			if (listPromo.get(i).getListApprenant() != null) {
				tmpList = listPromo.get(i).getListApprenant();
				for (Apprenant p : tmpList) {
					if (p.getNom().trim().equalsIgnoreCase(pNom.trim()) == true
							&& p.getPrenom().trim().equalsIgnoreCase(pPrenom.trim()) == true) {
						idPromo = i;
						break;
					}
				}
			}
		}
		return idPromo;
	}

	public static void ajouterMinuteRetard(String pNom, String pPrenom, int pMinute) {
		int tmpProm = idPromoApprenant(pNom, pPrenom);
		System.out.println("pNom" + pNom + "pPrenom" + pPrenom);
		if (tmpProm >= 0) {
			System.out.println("LIST" + listPromo.get(tmpProm).listApprenant);
			for (Apprenant p : listPromo.get(tmpProm).listApprenant) {
				if (p.getNom().trim().equalsIgnoreCase(pNom.trim()) == true
						&& p.getPrenom().trim().equalsIgnoreCase(pPrenom.trim()) == true) {
					p.setNnMinuteRetard(pMinute);
					return;
				}
			}
		} else {
			System.out.println("On n'a pas trouvé le promo qui a cet apprenant");
		}
	}

	public static void ajouterJourAbsence(String pNom, String pPrenom, int pDay, int pMonth) {
		int tmpProm = idPromoApprenant(pNom, pPrenom);

		if (tmpProm >= 0) {
			for (Apprenant p : listPromo.get(tmpProm).listApprenant) {
				if (p.getNom().toUpperCase().contains(pNom.toUpperCase().trim()) == true
						&& p.getPrenom().toUpperCase().contains(pPrenom.toUpperCase().trim()) == true) {
					p.setJourAbsent(pDay, pMonth);
					return;
				}
			}
		} else {
			System.out.println("On n'a pas trouvé le promo qui a cet apprenant");
		}
	}
	public static int idApprenant(int pIdPromo, String pNom, String pPrenom) {
		int idAppr = -1;
		for (Apprenant p : listPromo.get(pIdPromo).listApprenant) {
			if (p.getNom().toUpperCase().contains(pNom.toUpperCase().trim()) == true
					&& p.getPrenom().toUpperCase().contains(pPrenom.toUpperCase().trim()) == true) {
				idAppr = listPromo.get(pIdPromo).listApprenant.indexOf(p);
			}}
			return idAppr;
	}
	
public static void statusAbsence(String pNom, String pPrenom) {
	int tmpNbJour, tmpId, tmpProm = idPromoApprenant(pNom, pPrenom);
	if (tmpProm >= 0) { tmpId = idApprenant(tmpProm, pNom, pPrenom);
	if (tmpId >= 0) {
	
	tmpNbJour = listPromo.get(tmpProm).listApprenant.get(tmpId).getJourAbsent().size();
	
	if (tmpNbJour >= 0.1 * listPromo.get(tmpProm).getDureeEffecture()) {
		System.out.println("ALERT! Nombre jours d'absence est au dela 10% de jour effectué.");
	} else {System.out.println("Vous avez " + tmpNbJour + "jours d'absence.");
	}
}
}}

}
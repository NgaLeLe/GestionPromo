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
			return "---NOM PROMO : " + this.nomPromo.toUpperCase() + "\n---DUREE : " + this.dureeTotal
					+ "\n EFFECTUEE : " + this.dureeEffecture + "\n LISTE D'APPRENANT \n"
					+ this.getListApprenant().toString();
		} else {
			return "---NOM : " + this.nomPromo.toUpperCase() + "\n DUREE : " + this.dureeTotal + "\n EFFECTUEE : "
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

	// je veux récupérer l'index de promo dans lequel l'apprenant (qui a nom &
	// prénom) est/ s'incrit.
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
		int tmpIdApprenant;
		System.out.println("pNom" + pNom + "pPrenom" + pPrenom);
		if (tmpProm >= 0) {
			tmpIdApprenant = idApprenant(tmpProm, pNom, pPrenom);
			listPromo.get(tmpProm).listApprenant.get(tmpIdApprenant).setNnMinuteRetard(pMinute);
			System.out.println("Vous venez de saisir " + pMinute + "mn retard pour apprenant " + pNom + " " + pPrenom);
			return;
		} else {
			System.out.println("On n'a pas trouvé le promo qui a cet apprenant");
		}
	}

	public static void ajouterJourAbsence(String pNom, String pPrenom, int pDay, int pMonth) {
		int tmpProm = idPromoApprenant(pNom, pPrenom);
		int tmpIdApprenant;
		if (tmpProm >= 0) {
			tmpIdApprenant = idApprenant(tmpProm, pNom, pPrenom);
			listPromo.get(tmpProm).listApprenant.get(tmpIdApprenant).setJourAbsent(pDay, pMonth);
			return;
		} else {
			System.out.println("On n'a pas trouvé le promo qui a cet apprenant");
		}
	}

//chercher id d'apprenant qui appartient pNom et pPrénom dans un promo pIdPromo 
	public static int idApprenant(int pIdPromo, String pNom, String pPrenom) {
		int idAppr = -1;
		for (Apprenant p : listPromo.get(pIdPromo).listApprenant) {
			if (p.getNom().toUpperCase().contains(pNom.toUpperCase().trim()) == true
					&& p.getPrenom().toUpperCase().contains(pPrenom.toUpperCase().trim()) == true) {
				idAppr = listPromo.get(pIdPromo).listApprenant.indexOf(p);
			}
		}
		return idAppr;
	}

//afficher les absences d'un apprenant
	public static void statusAbsence(String pNom, String pPrenom) {
		int tmpNbJour, tmpIdApprenant, tmpProm = idPromoApprenant(pNom, pPrenom);
		if (tmpProm >= 0) {
			tmpIdApprenant = idApprenant(tmpProm, pNom, pPrenom);
			if (tmpIdApprenant >= 0) {
				tmpNbJour = listPromo.get(tmpProm).listApprenant.get(tmpIdApprenant).getJourAbsent().size();
				if (tmpNbJour >= 0.1 * listPromo.get(tmpProm).getDureeEffecture()) {
					System.out.println("ALERT! Nombre jours d'absence est au dela 10% de jour effectué.");
				} else {
					System.out.println("Vous avez " + tmpNbJour + "jours d'absence.");
				}
			}
		}
	}

//je vérifie les retards d'un apprenants
	public static void statusRetard(String pNom, String pPrenom) {
		int tmpNbMinute, tmpIdApprenant, tmpProm = idPromoApprenant(pNom, pPrenom);
		if (tmpProm >= 0) {
			tmpIdApprenant = idApprenant(tmpProm, pNom, pPrenom);
			if (tmpIdApprenant >= 0) {
				tmpNbMinute = listPromo.get(tmpProm).listApprenant.get(tmpIdApprenant).getNbMinuteRetard();
				if (tmpNbMinute >= 30) {
					System.out.println("ALERT! Nombre de minutes retardes cumulées est au-delà 30mn.");
				} else {
					System.out.println("Vous avez " + tmpNbMinute + " minutes retardes cumulées.");
				}
			}
		}
	}
}
package com.meeple.cloud.hivernage.db.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.service.Services;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileManager {
    private static final String CAMPING_FILENAME = "Campings.csv";
    private static final String CLIENT_FILENAME = "Clients.csv";
    private static final String SEPARATOR = ";";
    private static final String END_LINE  = "\n";

    // Importation
    public static List<Client> createAllClients(Context ctx) {
    	Client client = null;
    	try {

    		InputStream fileInputStream = new FileInputStream(new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/Documents/").append(CLIENT_FILENAME).toString()));
    		Reader inputStreamReader = new InputStreamReader(fileInputStream);
    		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    		List<String[]> data = new ArrayList();
    		int i = 0;
    		while (true) {
    			String allLine = bufferedReader.readLine();
    			if (allLine == null) {
    				break;
    			}
    			try {
    				String[] nextLine = allLine.split(SEPARATOR);
    				int size = nextLine.length;
    				if (size != 0) {
    					String debut = nextLine[0].trim();
    					if (debut.length() != 0 || size != 1) {
    						if (!debut.startsWith("#")) {
    							if (i != 0) {
    								data.add(nextLine);
    							}
    							i++;
    						}
    					}
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    		bufferedReader.close();
    		inputStreamReader.close();
    		fileInputStream.close();
    		List<Client> clients = new ArrayList();
    		Client client2 = null;
    		for (String[] oneData : data) {
    			int acompte = 0;
    			String nom = oneData[0];
    			String prenom = oneData[1];
    			if (Services.clientService.findByName(prenom, nom) == null) {
    				String adresse = oneData[2];
    				String mail = oneData[3];
    				String telephone = oneData[4];
    				String acompte_str = oneData[5];
    				if (acompte_str.trim().length() > 0) {
    					acompte = Integer.parseInt(acompte_str);
    				}
    				String observation = oneData[6];
    				String marque = oneData[8];
    				String plaque = oneData[9];
    				Gabarit g = Services.gabaritService.findGabaritByName("g" + oneData[8]);
    				String observationCar = "";
    				if (oneData.length == 17) {
    					observationCar = oneData[16];
    				}
    				Caravane caravane = new Caravane(marque, plaque, observationCar, g, null);
    				String camping_str = oneData[11];
    				if (camping_str.equals("HIVERNAGE")) {
    					int empl_x;
    					int empl_y;
    					int empl_angle;
    					String hangar_str = oneData[12];
    					try {
    						empl_x = Integer.parseInt(oneData[13]);
    					} catch (NumberFormatException e3) {
    						empl_x = 0;
    					}
    					try {
    						empl_y = Integer.parseInt(oneData[14]);
    					} catch (NumberFormatException e4) {
    						empl_y = 0;
    					}
    					try {
    						empl_angle = Integer.parseInt(oneData[15]);
    					} catch (NumberFormatException e5) {
    						empl_angle = 180;
    					}
    					Hangar hangar = Services.hangarService.findHangarByName(hangar_str);
    					if (hangar == null) {
    						hangar = new Hangar(hangar_str, 0, 0);
    						Services.hangarService.createHangar(hangar);
    					}
    					Services.caravaneService.putInHangar(caravane, new EmplacementHangar(empl_x, empl_y, (double) empl_angle, hangar));
    				} else {
    					try {
    						String emplacement = oneData[12];
    						String entree = oneData[13];
    						String sortie = oneData[14];
    						Camping camping = Services.campingService.findCampingByName(camping_str);
    						if (camping == null) {
    							camping = new Camping(camping_str, "", "", 0.0d, "");
    							Services.campingService.createCamping(camping);
    						}
    						EmplacementCamping emplacementCamping = new EmplacementCamping(camping, emplacement, caravane);
    						if (!entree.equals("null")) {
    							emplacementCamping.setEntree(new Date(Long.parseLong(entree)));
    						}
    						if (!sortie.equals("null")) {
    							emplacementCamping.setSortie(new Date(Long.parseLong(sortie)));
    						}
    						Services.caravaneService.addEmplacementCamping(caravane, emplacementCamping);
    						Services.caravaneService.putToCamping(caravane, emplacementCamping);
    					} catch (Exception e) {
    						e.printStackTrace();
    						client = client2;
    					}
    				}
    				client = new Client(nom, prenom, adresse, telephone, mail, observation, caravane);
    				if (oneData.length == 16) {
    					client.setOldClient(oneData[7].equals("0"));
    				}
    				client.addHivernage(new Hivernage(acompte));
    				Services.clientService.create(client);
    				clients.add(client);
    				client2 = client;
    			}
    		}
    		client = client2;
    		return clients;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        if (client != null) {
            Log.e("Hivernage", "Mauvaise importation du client :" + client.getFullName());
        } else {
            Log.e("Hivernage", "Mauvaise importation des clients => var null");
        }
        return null;
    }

    // Exportations
    public static void writeAllClients(Context ctx) {
        File file = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/Documents/").append(CLIENT_FILENAME).toString());
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            ArrayList<Client> all_clients = Services.clientService.getAllClient();
            fw.append("Nom;");					//  0
            fw.append("Prenom;");				//  1 
            fw.append("Adresse;");				//  2
            fw.append("Mail;");					//  3
            fw.append("Telephone;");			//  4
            fw.append("Acompte;");				//  5
            fw.append("Observation;");			//  6
            fw.append("isOldClient;");			//  7
            fw.append("Marque;");				//  8
            fw.append("Immatriculation;");		//  9
            fw.append("Gabarit;");				// 10
            fw.append("Camping/HIVERNAGE;");	// 11
            fw.append("Emplacement/Hangar;");	// 12
            fw.append("Entrée/X;");				// 13
            fw.append("Sortie/Y;");				// 14
            fw.append("-/Angle;");				// 15
            fw.append("Observation\n");			// 16
            Iterator it = all_clients.iterator();
            while (it.hasNext()) {
                appendClient(fw, (Client) it.next());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendClient(FileWriter fw, Client client) {
        try {
            fw.append(client.getNom() + SEPARATOR);
            fw.append(client.getPrenom() + SEPARATOR);
            fw.append(client.getAdresse() + SEPARATOR);
            fw.append(client.getMail() + SEPARATOR);
            fw.append(client.getTelephone() + SEPARATOR);
            fw.append(client.getCurrentAcompte() + SEPARATOR);
            fw.append(client.getObservation() + SEPARATOR);
            fw.append( ( (client.isOldClient()) ? "0":"1") + SEPARATOR );
            Caravane caravane = client.getCaravane();
            if (caravane != null) {
            	fw.append(caravane.getMarque() + SEPARATOR);
                fw.append(caravane.getPlaque() + SEPARATOR);
                if (caravane.getGabarit() != null) {
                    fw.append(new StringBuilder(String.valueOf(caravane.getGabarit().getNom().replace("g", ""))).append(SEPARATOR).toString());
                } else {
                    fw.append(" ;");
                }
                if (caravane.getCurrentEmplacementCamping() != null) {
                    EmplacementCamping emplCamp = caravane.getCurrentEmplacementCamping();
                    fw.append(new StringBuilder(String.valueOf(emplCamp.getCamping().getNom())).append(SEPARATOR).toString());
                    fw.append(emplCamp.getEmplacement() + SEPARATOR);
                    if (emplCamp.getEntree() != null) {
                        fw.append(new StringBuilder(String.valueOf(emplCamp.getEntree().getTime())).append(SEPARATOR).toString());
                    } else {
                        fw.append("null;");
                    }
                    if (emplCamp.getEntree() != null) {
                        fw.append(new StringBuilder(String.valueOf(emplCamp.getEntree().getTime())).append(SEPARATOR).toString());
                    } else {
                        fw.append("null;");
                    }
                    fw.append("-;");
                } else {
                    fw.append("HIVERNAGE;");
                    fw.append(new StringBuilder(String.valueOf(caravane.getEmplacementHangar().getHangar().getNom())).append(SEPARATOR).toString());
                    fw.append(new StringBuilder(String.valueOf(caravane.getEmplacementHangar().getPosX())).append(SEPARATOR).toString());
                    fw.append(new StringBuilder(String.valueOf(caravane.getEmplacementHangar().getPosY())).append(SEPARATOR).toString());
                    fw.append(new StringBuilder(String.valueOf(caravane.getEmplacementHangar().getAngle())).append(SEPARATOR).toString());
                }
                fw.append(caravane.getObservation() + END_LINE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Hivernage", "client bugguer : " + client.getFullName());
        }
    }

    public static List<Camping> createAllCampings(Context ctx) {
        try {
            InputStream fileInputStream = new FileInputStream(new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/Documents/").append(CAMPING_FILENAME).toString()));
            Reader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader buffreader = new BufferedReader(inputStreamReader);
            List<String[]> data = new ArrayList();
            int i = 0;
            while (true) {
                String allLine = buffreader.readLine();
                if (allLine == null) {
                    break;
                }
                String[] nextLine = allLine.split(SEPARATOR);
                int size = nextLine.length;
                if (size != 0) {
                    String debut = nextLine[0].trim();
                    if (!((debut.length() == 0 && size == 1) || debut.startsWith("#"))) {
                        if (i != 0) {
                            data.add(nextLine);
                        }
                        i++;
                    }
                }
            }
            buffreader.close();
            inputStreamReader.close();
            fileInputStream.close();
            List<Camping> campings = new ArrayList();
            for (String[] oneData : data) {
                double prix = 0.0d;
                String camping_name = oneData[0];
                if (Services.campingService.findCampingByName(camping_name) == null) {
                    String telephone = oneData[1];
                    String mail = oneData[2];
                    String prix_str = oneData[3];
                    if (prix_str.trim().length() > 0) {
                        prix = Double.parseDouble(prix_str);
                    }
                    String observation = "";
                    if (oneData.length == 5) {
                        observation = oneData[4];
                    }
                    Services.campingService.createCamping(new Camping(camping_name, mail, telephone, prix, observation));
                }
            }
            return campings;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void writeAllCampings(Context ctx) {
        File file = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/Documents/").append(CAMPING_FILENAME).toString());
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            ArrayList<Camping> all_campings = Services.campingService.getAllCampings();
            fw.append("Nom;");
            fw.append("Telephone;");
            fw.append("Mail;");
            fw.append("Prix;");
            fw.append("Observation\n");
            Iterator it = all_campings.iterator();
            while (it.hasNext()) {
                appendCamping(fw, (Camping) it.next());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendCamping(FileWriter fw, Camping camping) {
        try {
            fw.append(camping.getNom() + SEPARATOR);
            fw.append(camping.getTel() + SEPARATOR);
            fw.append(camping.getMail() + SEPARATOR);
            fw.append(camping.getPrix() + SEPARATOR);
            fw.append(camping.getObservations() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Hivernage", "camping bugguer : " + camping.getNom());
        }
    }
}

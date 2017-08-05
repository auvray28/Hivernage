package com.meeple.cloud.hivernage.view.object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

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
import android.widget.Toast;

public class FileManager {

	private final static String CAMPINGS_FILENAME = "Campings.csv";
	private final static String CLIENTS_FILENAME = "Clients.csv";

	private static File documents = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);


	public static void readCampingsCSV(Context context) {

		//Get the text file
		File file = new File(documents,  CAMPINGS_FILENAME);
		int count = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {

				if (count != 0) {

					String[] campingsLine = line.split(";");

					if (campingsLine.length == 5) {
						double accompte = 0;

						try {
							accompte = Double.parseDouble(campingsLine[3]);
						}
						catch (Exception e) {
							accompte = 0;
						}

						Camping ca = new Camping(campingsLine[0], campingsLine[2], campingsLine[1], accompte, campingsLine[4]);

						Services.campingService.createCamping(ca);
					}
					else {
						// TODO faire qqchose quand erreur import
					}
				}
				count++;
			}
			br.close();
		}
		catch (IOException e) {
			Toast.makeText(context, CAMPINGS_FILENAME + " read failed", Toast.LENGTH_SHORT).show();
		}
	}


	public static void readClientsCSV(Context context) {

		//Get the text file
		File file = new File(documents,  CLIENTS_FILENAME);
		int count = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {

				if (count != 0) {
					Client client = null;
					Caravane caravane;
					String[] clientLine = line.split(";");

					//Nom;Prenom;Adresse;Mail;Telephone;Acompte;Observation;
					// 7 prems => clients
					//Immatriculation;Gabarit;Camping/HIVERNAGE;Emplacement/Hangar;Entrée/X;Sortie/Y;-/Angle;Observation
					// 8 derniers -> caravanes + emplcaravanes
					if (clientLine.length > 7) {

						client = Services.clientService.findByName(clientLine[0], clientLine[1]);

						if (client == null) {
							int hivernage = 0;
							client = new Client(clientLine[0], clientLine[1], clientLine[2], clientLine[4], clientLine[5], clientLine[6], null);

							try {
								hivernage = Integer.parseInt(clientLine[5]);
							}
							catch (NumberFormatException e) { hivernage = 0; }

							
							client.addHivernage(new Hivernage(hivernage));

							Services.clientService.create(client);
						}
					}
					if (client != null) {
						String obs = "";
						//Immatriculation;Gabarit;Camping/HIVERNAGE;Emplacement/Hangar;Entrée/X;Sortie/Y;-/Angle;Observation

						Gabarit gabarit = Services.gabaritService.findGabaritByName("g"+clientLine[8]);
						if (gabarit == null )  gabarit = Services.gabaritService.getAllGabarits().get(0);

						if (clientLine.length > 14) obs = clientLine[14];
						
						caravane = new Caravane(clientLine[7], obs, gabarit, null);

						// Hangar
						//
						if (clientLine[9] == "HIVERNAGE") {
							int    posX   = 0;
							int    posY   = 0;
							double angle  = 0;
							Hangar hangar = Services.hangarService.findHangarByName(clientLine[10]);

							try {
								posX = Integer.parseInt(clientLine[11]);
							}
							catch (NumberFormatException e) { posX = 0; }

							try {
								posY = Integer.parseInt(clientLine[12]);
							}
							catch (NumberFormatException e) { posY = 0; }

							try {
								angle = Double.parseDouble(clientLine[13]);
							}
							catch (NumberFormatException e) { angle = 0; }

							if (hangar == null) {
								hangar = new Hangar(clientLine[8], 0, 0);
								Services.hangarService.createHangar(hangar);
							}
							EmplacementHangar emplH = new EmplacementHangar(posX, posY, angle, hangar);

							Services.caravaneService.putInHangar(caravane, emplH);		    				
						}
						// Camping
						//
						else {
							Camping camping = Services.campingService.findCampingByName(clientLine[9]);
							
							if (camping == null) {
								camping = new Camping(clientLine[9], "", "", 0, "Nouveau camping a mettre à jour");
								Services.campingService.createCamping(camping);
							}
							
							EmplacementCamping emplCamp = new EmplacementCamping(camping, clientLine[10], caravane);
							try {
								long entree = Long.parseLong(clientLine[11]);
								emplCamp.setEntree(new Date(entree));
							}
							catch (NumberFormatException e) {	}
							try {
								long sortie = Long.parseLong(clientLine[12]);
								emplCamp.setEntree(new Date(sortie));
							}
							catch (NumberFormatException e) {	}
							
							Services.caravaneService.addEmplacementCamping(caravane, emplCamp);
							Services.caravaneService.putToCamping(caravane, emplCamp);
						}
						client.setCaravane(caravane);
					}

					if (client != null) {
						Services.clientService.update(client);
					}

				}
				count++;
			}
			br.close();
		}
		catch (IOException e) {
			Toast.makeText(context, CLIENTS_FILENAME + " read failed", Toast.LENGTH_SHORT).show();
		}
	}

	public static void writeCampingsToCSV(Context context) {
		// Camping 
		//NOM;Tel;Mail;Prix;Observation;
		StringBuilder sb = new StringBuilder("");

		ArrayList<Camping> al_campings =  Services.campingService.getAllCampings();

		sb.append("NOM;Tel;Mail;Prix;Observation/n");

		for(Camping camp : al_campings) {
			sb.append(camp.getCSVString());
		}

		File campings_file = new File(documents,  CAMPINGS_FILENAME);
		
		
		
		try {
			FileWriter filewriter = new FileWriter(campings_file);

			filewriter.write(sb.toString());
			filewriter.close();

			Toast.makeText(context, CAMPINGS_FILENAME + " write success", Toast.LENGTH_SHORT).show();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
			Toast.makeText(context, CAMPINGS_FILENAME + " write failed", Toast.LENGTH_SHORT).show();
		} 
	}


	public static void writeClientsToCSV(Context context) {
		// Clients 
		//Nom;Prenom;Adresse;Mail;Telephone;Acompte;Observation;Immatriculation;Gabarit;Camping/HIVERNAGE;Emplacement/Hangar;Entrée/X;Sortie/Y;-/Angle;Observation

		StringBuilder sb = new StringBuilder("");

		ArrayList<Client> al_clients =  Services.clientService.getAllClient();

		sb.append("Nom;Prenom;Adresse;Mail;Telephone;Acompte;Observation;Immatriculation;Gabarit;Camping/HIVERNAGE;Emplacement/Hangar;Entrée/X;Sortie/Y;-/Angle;Observation/n");

		for(Client client : al_clients) {
			sb.append(client.getCSVString());
		}

		File clients_file = new File(documents,  CLIENTS_FILENAME);

		try {

			// /storage/emulated/0/Documents/clients.csv
			FileWriter filewriter = new FileWriter(clients_file);

			filewriter.write(sb.toString());
			filewriter.close();

			Toast.makeText(context, CAMPINGS_FILENAME + " write success", Toast.LENGTH_SHORT).show();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
			Toast.makeText(context, CAMPINGS_FILENAME + " write failed", Toast.LENGTH_SHORT).show();
		} 
	}
}
